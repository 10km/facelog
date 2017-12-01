package net.gdface.facelog.client;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.Strings;
import com.google.common.base.Supplier;

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
     * 应用项目可以继承此类实现自己的业务逻辑 
     * @author guyadong
     *
     * @param <T>
     */
    public static class BaseAdapter<T> implements IAckAdapter<T> {
    	/** 收到命令的client端数量,初始值-1,只有{@link #setClientNum(long)}被调用后才有效 */
        protected final AtomicLong clientNum = new AtomicLong(-1L);
        /**  
         * 命令响应等待有效期时间戳(毫秒), 参见 {@link System#currentTimeMillis()},
         * 超过这个时间会自动取消频道订阅 
         */
        protected long expire = 0L;
        /** 收到的命令响应计数 */
        protected long ackCount = 0L;
        /** 订阅的命令响应频道名 */
        protected final String channel;

        /**
         * 构造函数<br>
         * 超时参数使用默认值{@link #DEFAULT_DURATION}
         * @param channel
         * @see #BaseAdapter(long, String)
         */
        public BaseAdapter(String channel) {
			this(System.currentTimeMillis() + DEFAULT_DURATION,channel);
		}

		/**
		 * 构造函数
		 * @param expire 有效期
		 * @param channel 命令响应频道名
		 */
		public BaseAdapter(long expire, String channel) {
			checkArgument(!Strings.isNullOrEmpty(channel),"channel is null or empty");
			this.channel = channel;
			this.expire = expire;
		}
		/**
		 * 构造函数
		 * @param ackChannelSupplier 参见 {@link IFaceLogClient#getAckChannelSupplier(net.gdface.facelog.client.thrift.Token)}
		 */
		public BaseAdapter(Supplier<String> ackChannelSupplier) {
			this(ackChannelSupplier.get());
		}
		/**
		 * @param expire
		 * @param ackChannelSupplier
		 */
		public BaseAdapter(long expire, Supplier<String> ackChannelSupplier) {
			this(expire,ackChannelSupplier.get());
		}
		/**
		 * 命令响应处理实现,应用项目应重写此方法实现自己的业务逻辑
		 */
		@Override
        public void onSubscribe(Ack<T> t) throws SmqUnsubscribeException {
			if(t.getStatus() == Status.TIMEOUT ){
				// 发送这个状态的本机线程会自动取消频道订阅
				if(ackCount !=clientNum.get()){
					// 超时处理
				}else{
					// 超时之前已经收到了所有响应，但因为没有及时调用setClientNum造成的延迟
					// 在设备端响应命令很快时有可能会出现这种情况
					// 或者收到命令响应的设备端为0,也会引起超时
				}
			}else if(++ackCount ==clientNum.get()){
            	// 所有收到命令的设备都已经响应则抛出SmqUnsubscribeException异常用于取消当前频道订阅
                throw new SmqUnsubscribeException(true);
            }
			// 响应处理业务逻辑 。。。
        }

        /**
         * 只能被调用一次,否则第二次会抛出异常,
         * 设备命令发送后，REDIS会返回收到设备命令的设备端数量,{@code clientNum}必须>=0
         */
        @Override
        public BaseAdapter<T> setClientNum(long clientNum){
        	checkArgument(clientNum >= 0,"INVALID clientNum %s",clientNum);
        	checkState(this.clientNum.compareAndSet(-1L, clientNum),"clientNum can be set once only");    
            return this;
        }
        @Override
        public long getExpire() {
            return expire;
        }
        @Override
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
        @Override
        public String getChannel() {
            return channel;
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
    /**
     * 设置命令响应等待有效期时间戳(毫秒), 参见 {@link System#currentTimeMillis()},
     * 超过这个时间会自动取消频道订阅 
     * @param expire
     * @return
     */
    public IAckAdapter<T> setExpire(long expire);
    
    /**
     * 订阅的命令响应频道名
     * @return
     */
    public String getChannel() ;
}