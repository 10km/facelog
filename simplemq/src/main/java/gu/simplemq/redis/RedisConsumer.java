package gu.simplemq.redis;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import gu.simplemq.AbstractConsumer;
import gu.simplemq.Channel;
import gu.simplemq.ChannelDispatcher;
import gu.simplemq.IMessageRegister;
import gu.simplemq.exceptions.SmqTypeException;
import redis.clients.jedis.Jedis;

/**
 * {@link AbstractConsumer}消费者模型实现,支持多个list阻塞式读取(blpop, brpop)<br>
 * 执行 {@link #subscribe(String...)}方法时会自动开启消费线程
 * @author guyadong
 *
 * @param <T>
 */
public class RedisConsumer extends AbstractConsumer implements IRedisComponent,IMessageRegister {

	private final JedisPoolLazy poolLazy;
	private final ChannelDispatcher register=new ChannelDispatcher(){
		@Override
		protected String check(String name) throws SmqTypeException {
			return RedisComponentType.Queue.check(poolLazy,name);
		}};
	/** 以秒为单位的超时参数 */
	private int timeout; 
	@Override
	public JedisPoolLazy getPoolLazy() {
		return poolLazy;
	}

	RedisConsumer(JedisPoolLazy poolLazy) {
		super();
		this.poolLazy = poolLazy;
		this.setTimeoutMills(DEFAULT_CONSUMER_CHECK_INTERVAL);
	}
	private final Runnable customRunnable = new Runnable(){
		@Override
		public void run() {
			try {
				List<String> list;
				Jedis jedis = poolLazy.apply();
				try{
					String[] keys =register.getSubscribes();
					// 订阅频道为0时关闭线程
					if(0 == keys.length){
						close();
						return;
					}
					if(isFifo){
						list = jedis.blpop(timeout, keys);
					}else{
						list = jedis.brpop(timeout, keys);
					}
				}finally{
					poolLazy.free();
				}
				if(!list.isEmpty()){
					String channel = list.get(0);
					String message = list.get(1);
					register.dispatch(channel, message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	@Override
	protected Runnable getCustomRunnable() {
		return customRunnable;
	}

	@Override
	public Set<Channel<?>> register(Channel<?>... channels) {
		Set<Channel<?>> chSet = register.register(channels);
		this.open();
		return chSet;
	}
	
	@Override
	public Set<String> unregister(String... channels) {
		return register.unregister(channels);
	}
	
	@Override
	public Set<String> unregister(Channel<?>... channels) {
		return register.unregister(channels);
	}

	@Override
	public String[] subscribe(String... channels) {
		channels = this.register.subscribe(channels);
		this.open();
		return channels;
	}

	@Override
	public String[] unsubscribe(String... channels) {
		return this.register.unsubscribe(channels);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Channel getChannel(String channel) {
		return register.getChannel(channel);
	}

	@Override
	public String[] getSubscribes() {
		return register.getSubscribes();
	}

	@Override
	public AbstractConsumer setTimeoutMills(int timeoutMills) {
		super.setTimeoutMills(timeoutMills);
		this.timeout = (int) TimeUnit.SECONDS.convert(this.timeoutMills, TimeUnit.MILLISECONDS);
		return this;
	}
	
	/**
	 * 设置超时参数(秒)
	 * @param timeout (seconds)
	 * @return 
	 * @return
	 * @see #setTimeoutMills(int)
	 */
	public RedisConsumer setTimeout(int timeout) {
		if(timeout>0){
			this.timeout = timeout;
			super.setTimeoutMills((int) TimeUnit.MILLISECONDS.convert(timeout, TimeUnit.SECONDS));
		}
		return this;
	}
}
