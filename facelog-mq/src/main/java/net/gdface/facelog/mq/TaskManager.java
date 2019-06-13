package net.gdface.facelog.mq;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Supplier;

import gu.simplemq.Channel;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisProducer;
import net.gdface.facelog.Token;

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
public class TaskManager extends BaseCmdManager {
    private final Supplier<Channel<DeviceInstruction>> taskChannelSupplier;
    private final Supplier<String> taskQueueSupplier;
    private final RedisProducer producer;
    /**
     * 构造方法
     * @param poolLazy 
     * @param redisParameters redis 服务器参数,参见 {@link IFaceLogClient#getRedisParameters(Token)}
     */
    public TaskManager(JedisPoolLazy poolLazy, Supplier<String> taskQueueSupplier) {
    	super(poolLazy);
        this.producer = RedisFactory.getProducer(poolLazy);
        RedisFactory.getConsumer(poolLazy);
        this.taskQueueSupplier = checkNotNull(taskQueueSupplier,"taskQueueSupplier is null");
        this.taskChannelSupplier = new TaskChannelSupplier();
    }

    /**
     * 发送设备命令
     * @param cmd
     * @return 收到命令的客户端数目
     */
    @Override
    protected void doSendCmd(DeviceInstruction cmd){
        producer.produce(this.taskChannelSupplier.get(), cmd);
    }
    private class TaskChannelSupplier implements Supplier<Channel<DeviceInstruction>>{

		@Override
		public Channel<DeviceInstruction> get() {
			return  new Channel<DeviceInstruction>(taskQueueSupplier.get()){};
		}    	
    }
}
