package net.gdface.facelog.client;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import net.gdface.facelog.client.Ack.Status;

/**
 * 命令响应{@link Ack}处理接口<br>
 * 设备命令发送到设备端,设备端执行命令后会返回命令响应({@link Ack},也就是命令执行结果)给发送端。
 * 该接口就是命令发送端用于处理收到的命令响应的接口。<br>
 * 应用项目应根据需要实现对应命令响应的业务。建议继承{@link BaseAdapter},因为{@link BaseAdapter}实现了所有的接口方法，
 * 应用项目只需要重写{@link BaseAdapter#onSubscribe(Ack)}方法就可以了<br>
 * 因为设备命令发送功能可以支持同时向多个设备或设备组发送命令,所以一条设备命令发送会收到多个设备端的响应,
 * 虽然发送方可以知道知道收到命令的设备端数量(参见{@link CmdManager}的设备命令方法)，但系统并不能保证所有收到命令的设备都会返回命令响应,
 * 所以{@link #setExpire(long)}用于指定超时参数,超过指定时间还没有收到所有命令响应,
 * 本机的命令响应监控线程{@link AckMonitor}也会产生一个虚拟的{@link Ack}消息,送给{@code IAckAdapter}接口,
 * 参见{@link BaseAdapter#onSubscribe(Ack)},{@link Ack.Status#TIMEOUT}
 * @author guyadong
 *
 * @param <T> 设备命令响应返回数据类型
 */
public interface IAckAdapter <T> extends IMessageAdapter<Ack<T>>{
	/** 默认超时时间(毫秒) */
	public static final long DEFAULT_DURATION = 60000L;
    /**
     * 命令响应{@link Ack}处理基类,
     * 应用项目可以继承此类实现自己的业务逻辑 {@link #doOnTimeout()},{@link #doOnSubscribe(Ack)}
     * @author guyadong
     *
     * @param <T>
     */
    public static class BaseAdapter<T> implements IAckAdapter<T> {
    	/** 收到命令的client端数量,初始值-1,只有{@link #setClientNum(long)}被调用后才有效 */
        private final AtomicLong clientNum = new AtomicLong(-1L);
        /**  
         * 命令响应等待有效期时间戳(毫秒), 参见 {@link System#currentTimeMillis()},
         * 超过这个时间会自动取消频道订阅,默认为0时无限期。 
         */
        private long expire = 0L;
        /** 收到的命令响应计数 */
        private long ackCount = 0L;
        /**
		 * 构造函数
		 * @param expire 有效期时间戳
		 */
		public BaseAdapter(long expire) {
			this.expire = expire;
		}
		/** 处理超时情况 */
		protected void doOnTimeout(){}
		/** 处理接收命令的设备端数量为0的情况 */
		protected void doOnZeroClient(){}
		/** 执行正常响应处理业务逻辑 */
		protected void doOnSubscribe(Ack<T> t){}
		/** 返回{@link #clientNum}值 */
		protected final long getClientNum(){
			return clientNum.get();
		}
		/** 返回{@link #ackCount}值 */
		protected final long getAckCount() {
			return ackCount;
		}
		/**
		 * 命令响应处理实现,应用项目应重写此方法实现自己的业务逻辑
		 */
		@Override
        public final void onSubscribe(Ack<T> t) throws SmqUnsubscribeException {
			if(t.getStatus() == Status.TIMEOUT ){
				// 收到这个状态时频道已经被取消订阅
				if(clientNum.get() == 0){
					doOnZeroClient();
				}else{
					doOnTimeout();
				}
			}else{
				try{
					doOnSubscribe(t);
				}finally{
					if(++ackCount ==clientNum.get()){
						// 所有收到命令的设备都已经响应则抛出SmqUnsubscribeException异常用于取消当前频道订阅
						throw new SmqUnsubscribeException(true);
					}
				}
			}
        }

        /**
         * 只能被调用一次,否则第二次会抛出异常,
         * 设备命令发送后，REDIS会返回收到设备命令的设备端数量,{@code clientNum}必须>=0
         */
        @Override
        public final BaseAdapter<T> setClientNum(long clientNum){
        	checkArgument(clientNum >= 0,"INVALID clientNum %s",clientNum);
        	checkState(this.clientNum.compareAndSet(-1L, clientNum),"clientNum can be set once only");    
            return this;
        }
        @Override
        public final long getExpire() {
            return expire;
        }
        public BaseAdapter<T> setExpire(long expire) {
            this.expire = expire;
            return this;
        }
        /**
         * 设置超时时间
         * @param duration
         * @param unit 时间单位
         * @return
         */
        public BaseAdapter<T> setDuration(long duration,TimeUnit unit) {
            return setDuration(TimeUnit.MILLISECONDS.convert(duration, unit));
        }
        /**
         * 设置超时时间(毫秒)
         * @param duration
         * @return
         */
        public BaseAdapter<T> setDuration(long duration) {
            return setExpire(System.currentTimeMillis() +duration);
        }
	}
	/**
	 * 设置收到命令的client端数量<br>
	 * 参见{@link CmdManager}的命令发送方法,比如{@link CmdManager#reset(Long)},
	 * 用于判断是否已经收到所有设备的响应命令.
	 * @param clientNum
	 * @return
	 */
	public IAckAdapter<T> setClientNum(long clientNum);
    /**
     * 返回命令响应等待有效期时间戳(毫秒),参见 {@link #setExpire(long)}
     * @return
     */
    public long getExpire();
}