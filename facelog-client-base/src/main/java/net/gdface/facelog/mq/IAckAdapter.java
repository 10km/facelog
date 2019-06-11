package net.gdface.facelog.mq;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Preconditions.checkNotNull;

import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import net.gdface.facelog.mq.Ack.Status;

/**
 * 命令响应{@link Ack}处理接口<br>
 * 设备命令发送到设备端,设备端执行命令后会返回命令响应({@link Ack},也就是命令执行结果)给发送端。
 * 该接口就是命令发送端用于处理收到的命令响应的接口。<br>
 * 应用项目应根据需要实现对应命令响应的业务。建议继承{@link BaseAdapter},因为{@link BaseAdapter}实现了所有的接口方法，
 * 应用项目只需要重写{@link BaseAdapter#onSubscribe(Ack)}方法就可以了<br>
 * 因为设备命令发送功能可以支持同时向多个设备或设备组发送命令,所以一条设备命令发送会收到多个设备端的响应,
 * 虽然发送方可以知道知道收到命令的设备端数量(参见{@link CmdManager}的设备命令方法)，但系统并不能保证所有收到命令的设备都会返回命令响应,
 * 所以{@link BaseAdapter#setDuration(long)}用于指定超时参数,超过指定时间还没有收到所有命令响应,
 * 本机会产生一个虚拟的{@link Ack}消息,送给{@code IAckAdapter}接口,
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
     * 应用项目可以继承此类的方法({@link #doOnTimeout()},{@link #doOnSubscribe(Ack)},{@link #doOnZeroClient()})实现自己的业务逻辑 
     * @author guyadong
     *
     * @param <T>
     */
    public static abstract class BaseAdapter<T> implements IAckAdapter<T> {
    	/** 收到命令的client端数量,初始值-1,只有{@link #setClientNum(long)}被调用后才有效 */
        private final AtomicLong clientNum = new AtomicLong(-1L);
        /**  
         * 命令响应等待时间(毫秒),
         * 超过这个时间会自动取消频道订阅,>0有效,默认为0时无限期。 
         */
        private long duration = 0L;
        /** 收到的命令响应计数 */
        private long ackCount = 0L;
        /** 当前对象频道订阅生命期是否结束标志 */
        private final AtomicBoolean isFinished = new AtomicBoolean(Boolean.FALSE);
        /**
         * 默认构造函数<br>
         * 有效期使用默认值{@link #DEFAULT_DURATION}
         * @see #BaseAdapter(long, TimeUnit)
         */
        public BaseAdapter() {
        	this(DEFAULT_DURATION,TimeUnit.MILLISECONDS);
		}
		/**
		 * 构造函数
		 * @param duration 命令响应等待时间
		 * @param unit 时间单位
		 */
		public BaseAdapter(long duration,TimeUnit unit) {
			this.setDuration(duration, unit);
		}
		/** 处理超时情况,应用项目应重写此方法实现自己的业务逻辑 */
		protected void doOnTimeout(){}
		/** 处理接收命令的设备端数量为0的情况,应用项目应重写此方法实现自己的业务逻辑 */
		protected void doOnZeroClient(){}
		/** 执行正常响应处理业务逻辑,应用项目应重写此方法实现自己的业务逻辑 */
		protected void doOnSubscribe(Ack<T> t){}
		/** 返回{@link #clientNum}值 */
		protected final long getClientNum(){
			return clientNum.get();
		}
		/** 返回{@link #ackCount}值 */
		protected final long getAckCount() {
			return ackCount;
		}
		/** 通知等待的线程 */
		private final void doOnFinished(){
			synchronized(this){
				checkState(this.isFinished.compareAndSet(false, true),"invalid status of isFinished");
				this.notifyAll();
			}
		}
		/**
		 * 命令响应处理实现
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
				doOnFinished();
			}else{
				try{
					doOnSubscribe(t);
				}finally{
					if(++ackCount ==clientNum.get()){
						doOnFinished();
						// 所有收到命令的设备都已经响应则抛出SmqUnsubscribeException异常用于取消当前频道订阅
						throw new SmqUnsubscribeException(true);
					}
				}
			}
        }

        /**
         * 只能被调用一次,否则第二次会抛出异常,
         * 设备命令发送后，REDIS会返回收到设备命令的设备端数量,{@code clientNum}必须大等于0
         */
        @Override
        public BaseAdapter<T> setClientNum(long clientNum){
        	checkArgument(clientNum >= 0,"INVALID clientNum %s",clientNum);
        	checkState(this.clientNum.compareAndSet(-1L, clientNum),"clientNum can be set once only");    
            return this;
        }
        @Override
        public final long getDuration() {
            return duration;
        }
        @Override
		public boolean isFinished() {
			return this.isFinished.get() || ackCount == this.clientNum.get();
		}
		/**
		 * 设置超时时间(毫秒)
		 * @param duration 大于0有效
		 * @return
		 */
		public final BaseAdapter<T> setDuration(long duration) {
			this.duration = duration;
			return this;
		}
		/**
		 * 设置超时时间
		 * @param duration
		 * @param unit 时间单位
		 * @return
		 */
		public final BaseAdapter<T> setDuration(long duration,TimeUnit unit) {
		    return setDuration(TimeUnit.MILLISECONDS.convert(duration, checkNotNull(unit,"unit is null")));
		}
		/**
		 * 设置有效期时间戳(毫秒)
		 * @param expire
		 * @return
		 */
		public final BaseAdapter<T> setExpire(long expire) {
            return setDuration(expire - System.currentTimeMillis());
        }
		/**
		 * @param expire
		 * @return
		 * @see #setExpire(long)
		 */
		public final BaseAdapter<T> setExpire(Date expire) {
            return setDuration(expire.getTime() - System.currentTimeMillis());
        }
		/**
		 * 等待命令响应订阅结束,用于同步接收命令响应
		 * @throws InterruptedException
		 */
		public final void waitFinished() throws InterruptedException{
			synchronized(this){
				while(!isFinished()){
					this.wait(200);
				}
			}
		}
		/** 复位所有成员变量到初始状态,以便于对象下次复用 */
		public final synchronized BaseAdapter<T> reset(){
			this.clientNum.set(-1L);
			this.duration = 0L;
			this.ackCount = 0L;
			this.isFinished.set(false);
			return this;
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
     * 返回命令响应等待持续时间(毫秒)
     * @return
     */
    public long getDuration();
    /**
     * 返回当前响应任务是否结束
     * @return
     */
    public boolean isFinished();
}