package net.gdface.facelog.mq;

import net.gdface.facelog.Token;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import gu.simplemq.IUnregistedListener;
import gu.simplemq.Channel;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisConsumer;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisProducer;

/**
 * 
 * 设备命令发送管理模块<br>
 * 线程安全<br>
 * 发送设备命令示例:
 * <pre>
 *    String ackChannel = iFaceLogClient.applyAckChannel(myToken); // 向facelog服务申请命令响应通道
 *    long cmdSn = iFaceLogClient.applyCmdSn(myToken); // 向facelog服务申请命令序列号
 *    targetBuilder()
 *        .setCmdSn(cmdSn) // 设置命令序列号
 *        .setDeviceTarget(deviceId) // 指定目标设备ID
 *        .setAckChannel(ackChannel) // 设置命令响应通道
 *        .build()
 *        .reset(null); // 执行reset命令,立即执行
 *    // 如果同时设置命令响应处理对象,请调用 reset(Long ,IAckAdapter)
 * </pre>
 * 带{@code IAckAdapter}类型参数的方法为异步执行方法,需要应用项目提供{@code IAckAdapter}实例,
 * {@code sync}后缀的设备命令方法为同步执行方法,
 * @author guyadong
 *
 */
public class TaskManager {
    private final Supplier<Channel<DeviceInstruction>> taskChannelSupplier;
    private final Supplier<String> taskQueueSupplier;
    private final RedisProducer redisProducer;
    private final RedisConsumer consumer;
    /**
     * 构造方法
     * @param poolLazy 
     * @param redisParameters redis 服务器参数,参见 {@link IFaceLogClient#getRedisParameters(Token)}
     */
    public TaskManager(JedisPoolLazy poolLazy,
    		Supplier<String> taskQueueSupplier) {
    	checkArgument(poolLazy != null,"poolLazy is null");
        this.redisProducer = RedisFactory.getProducer(poolLazy);
        this.consumer = RedisFactory.getConsumer(poolLazy);
        this.taskQueueSupplier = checkNotNull(taskQueueSupplier,"taskQueueSupplier is null");
        this.taskChannelSupplier = new TaskChannelSupplier();
    }
    public TaskManager setExecutor(ExecutorService executor) {
    	consumer.setExecutorService(executor);
        return this;
    }
    /**
     * 发送设备命令
     * @param cmd
     * @return 收到命令的客户端数目
     */
    private void sendTask(DeviceInstruction cmd){
        checkArgument(null != cmd,"cmd is null");
        checkArgument(null != cmd.getCmdpath(),"DeviceInstruction.cmdpath field must not be null");
        checkArgument(null != cmd.getTarget() && !cmd.getTarget().isEmpty(),"DeviceInstruction.target field must not be null");
        if(null == cmd.getParameters()){
            cmd.setParameters(ImmutableMap.<String,Object>of());
        }
        redisProducer.produce(this.taskChannelSupplier.get(), cmd);
    }
    private class TaskChannelSupplier implements Supplier<Channel<DeviceInstruction>>{

		@Override
		public Channel<DeviceInstruction> get() {
			return  new Channel<DeviceInstruction>(taskQueueSupplier.get()){};
		}    	
    }
    /** 
     * 设备命令参数构建工具类,用于设置{@link DeviceInstruction}对象除{@code cmd,parameters}字段之的其他字段,可以被不同的设备命令复用
     * @author guyadong
     */
    public class TaskBuilder{       
        private Supplier<Long> cmdSnSupplier;
        private Supplier<String> ackChannelSupplier = Suppliers.ofInstance(null);
        private Long cmdSn = null;
        private String ackChannel = null;
        /** 命令发送后是否自动清除TLS变量 */
        private boolean autoRemove = true;
        private TaskBuilder(){
        }
        /**
         * 调用{@link Supplier}实例获取当前设备命令需要的序列号和响应通道
         * @return
         */
        private TaskBuilder apply(){
            if(null == cmdSn){
                cmdSn = cmdSnSupplier.get();
            }
            if(null == ackChannel){
                ackChannel = ackChannelSupplier.get();
            }
            return this;
        }
        /**
         * 将 {@link #apply()}获取的命令序列号和响应通道值清除，
         * 当需要反复使用当前builder实例发送设备命令时需要调用此方法重置状态，
         * 以便下次发送设备命令时，apply 方法重新申请新的设备命令和序列号
         * @return
         */
        public TaskBuilder resetApply(){
            cmdSn = null;
            ackChannel  = null;
            return this;
        }
        /** 指定命令序列号,参见 {@link DeviceInstruction#setCmdSn(long)} */
        public TaskBuilder setCmdSn(long cmdSn) {
            return setCmdSn(Suppliers.ofInstance(cmdSn));
        }
        /** 
         * 指定提供命令序列号的{@code Supplier}实例
         * @see IFaceLogClient#getAckChannelSupplier(Token)
         */
        public TaskBuilder setCmdSn(Supplier<Long> cmdSnSupplier) {
            this.cmdSnSupplier = checkNotNull(cmdSnSupplier);
            this.cmdSn = null;
            return this;
        }
        /** 
         * 指定命令响应通道,参见 {@link DeviceInstruction#setAckChannel(String)} */
        public TaskBuilder setAckChannel(String ackChannel){
            checkArgument(!Strings.isNullOrEmpty(ackChannel),"ackChannel is null or empty");
            return this.setAckChannel(Suppliers.ofInstance(ackChannel));
        }
        /** 
         * 指定提供命令响应通道的{@code Supplier}实例,
         * @see IFaceLogClient#getAckChannelSupplier(Token)
         */
        public TaskBuilder setAckChannel(Supplier<String> ackChannelSupplier){
            this.ackChannelSupplier = checkNotNull(ackChannelSupplier);
            this.ackChannel = null;
            return this;
        }
        /**
         * @param autoRemove 为{@code true}时,完成设备命令发送后自动清除Thread Local Storage变量{@link TaskManager#TLS_BUILDER},
         *                                    默认值为{@code true}
         */
        public TaskBuilder autoRemove(boolean autoRemove){
            this.autoRemove = autoRemove;
            return this;
        }
        /** 数据有效性验证 */
        private void validate(){
            checkState(null != cmdSnSupplier,"cmdSn is uninitialized");
        }
        /** 完成build,返回 {@link TaskManager}对象 */
        public TaskManager build(){
            validate();
            return TaskManager.this;
        }
    } 
    /** 
     * TLS变量,用于保存当前线程使用的 {@link TaskBuilder}对象<br>,
     * TLS变量在多线程高并发环境如果不显式执行{@link ThreadLocal#remove()}有资源泄漏风险,
     * 如果{@link TaskBuilder#autoRemove}为{@code true}(默认值),则调用设备命令方法发送完命令后会自动清除TLS变量,
     * 否则需要调用 {@link TaskManager#removeTlsTarget()}方法手动清除。
      */
    private static final ThreadLocal<TaskBuilder> TLS_BUILDER= new ThreadLocal<TaskBuilder>();
    public TaskBuilder targetBuilder(){
        if(null == TLS_BUILDER.get()){
            TLS_BUILDER.set(new TaskBuilder());
        }
        return TLS_BUILDER.get();
    }
    /** 
     * 清除TLS变量 {@link #TLS_BUILDER}
     * @see ThreadLocal#remove()
     */
    public TaskManager removeTlsTarget(){
        TLS_BUILDER.remove();
        return this;
    }
    /** 检查是否创建了{@link TaskBuilder}对象没有则抛出异常 */
    protected static final TaskBuilder checkTlsAvailable(){
        return checkNotNull(TLS_BUILDER.get(),
                "not defined target,please call method targetBuilder() to build parameters");
    }
    /**
     * 用于处理超时等待的{@link Ack}对象<br>
     * 向{@link Ack}对象发送超时错误{@link Ack.Status#TIMEOUT}
     * @author guyadong
     *
     * @param <T> 设备命令响应返回数据类型
     */
    private class TimeoutCleaner <T> implements IUnregistedListener<Ack<T>>{
        @Override
        public void apply(Channel<Ack<T>> input) {
            IAckAdapter<T> adapter = (IAckAdapter<T>)input.getAdapter();
            try{
                if(!adapter.isFinished()){
                    // 通知执行器命令超时
                    adapter.onSubscribe(new Ack<T>().setStatus(Ack.Status.TIMEOUT));
                }
            }catch(SmqUnsubscribeException e){
            }catch(RuntimeException e){
                e.printStackTrace();
            }
        }
    }
    /**
     * 用于同步执行设备命令的{@link IAckAdapter}实现
     * @author guyadong
     *
     * @param <T> 设备命令响应返回数据类型
     */
    private class AdapterSync<T> extends IAckAdapter.BaseAdapter<T>{
        final List<Ack<T>> acks = Collections.synchronizedList(new LinkedList<Ack<T>>());
        final AtomicBoolean timeout = new AtomicBoolean(false);
        @Override
        protected void doOnTimeout() {
            timeout.set(true);
        }

        @Override
        protected void doOnSubscribe(Ack<T> t) {
            acks.add(t);
        }
    }
    /**
     * 设备命令(异步调用)<br>
     * @param cmdpath 设备命令名(全路径)
     * @param params 设备命令参数对象, {@code 参数名(key)->参数值(value)映射},没有参数可为{@code null}
     * @return 收到命令的客户端数目
     */
    public void runTask(String cmdpath,Map<String, Object> params){
        TaskBuilder builder = checkTlsAvailable().apply();
        try{
            // 所有的命令参数封装到 Map
            params = MoreObjects.firstNonNull(params, Collections.<String, Object>emptyMap());
            DeviceInstruction deviceInstruction = new DeviceInstruction()
                    .setCmdpath(cmdpath)
                    .setCmdSn(builder.cmdSn)
                    .setAckChannel(builder.ackChannel)
                    .setParameters(params);
            sendTask(deviceInstruction);
        }finally{
            if(builder.autoRemove){
                removeTlsTarget(); 
            }
        }
    }
    /**
     * 设备命令(异步调用)<br>
     * 该方法会自动将命令响应通道名({@link TaskBuilder#setAckChannel(String)})
     * 关联命令处理对象({@code adapter})注册到REDIS订阅频道,当有收到设备命令响应时自动交由{@code adapter}处理<br>
     * 该方法要求必须指定命令响应通道,参见{@link TaskBuilder#setAckChannel(String)},{@link TaskBuilder#setAckChannel(Supplier)}
     * 
     * @param cmdpath 设备命令名(全路径)
     * @param params 设备命令参数对象, {@code 参数名(key)->参数值(value)映射},没有参数可为{@code null}
     * @param adapter 命令响应处理对象,不可为{@code null}
     */
    public void runTask(String cmdpath,Map<String, Object> params,IAckAdapter<Object> adapter){
        TaskBuilder builder = checkTlsAvailable().apply();
        checkArgument(!Strings.isNullOrEmpty(builder.ackChannel),"INVALID ackChannel");
        Channel<Ack<Object>> channel = new Channel<Ack<Object>>(builder.ackChannel){}
            .setAdapter(checkNotNull(adapter,"adapter is null"))
            .addUnregistedListener(new TimeoutCleaner<Object>());
        consumer.register( channel);
        runTask(cmdpath,params);
        adapter.setClientNum(1);
    }
    /**
     * 设备命令(同步调用)<br>
     * 
     * @param cmdpath 设备命令名(全路径)
     * @param params 设备命令参数对象, {@code 参数名(key)->参数值(value)映射},没有参数可为{@code null}
     * @param throwIfTimeout 当响应超时时，是否抛出{@link AckTimtoutException}异常
     * @return 设备端返回的所有命令响应对象
     * @throws InterruptedException
     * @throws AckTimtoutException 命令响应超时
     * @see #runTask(String, Map, IAckAdapter)
     */
    public List<Ack<Object>> runCmdSync(String cmdpath,Map<String, Object> params,boolean throwIfTimeout) 
            throws InterruptedException,AckTimtoutException{
        AdapterSync<Object> adapter = new AdapterSync<Object>();
        runTask(cmdpath,params,adapter);
        // 等待命令响应结束
        adapter.waitFinished();
        if(adapter.timeout.get() && throwIfTimeout){
            throw new AckTimtoutException();
        }
        return adapter.acks;
    }
}
