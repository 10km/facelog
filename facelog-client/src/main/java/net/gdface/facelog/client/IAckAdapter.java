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
 * 命令响应{@link Ack}处理接口
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
    	/** 收到命令的client端数量 */
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
			if(t.getStatus() == Status.TIMEOUT){
				// 超时处理
				// 发送这个状态的本机线程会自动取消频道订阅
			}else if(++ackCount ==clientNum.get()){
            	// 所有收到命令的设备都已经响应则抛出SmqUnsubscribeException异常用于取消当前频道订阅
                throw new SmqUnsubscribeException(true);
            }
        }

        /**
         * 只能被调用一次,否则第二次会抛出异常
         */
        @Override
        public BaseAdapter<T> setClientNum(long clientNum){
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