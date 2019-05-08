package net.gdface.facelog.client;

import com.google.common.base.Supplier;

import gu.simplemq.Channel;
import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisSubscriber;
import net.gdface.facelog.DeviceHeadbeatPackage;
import net.gdface.facelog.ServiceHeartbeatPackage;

import static com.google.common.base.Preconditions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;

/**
 * 心跳包频道监控
 * @author guyadong
 *
 */
public class HeartbeatMonitor implements ServiceHeartbeatListener {
    public static final Logger logger = LoggerFactory.getLogger(HeartbeatMonitor.class);

	public static final IMessageAdapter<DeviceHeadbeatPackage> DEF_ADAPTER = new IMessageAdapter<DeviceHeadbeatPackage>(){

		@Override
		public void onSubscribe(DeviceHeadbeatPackage t) throws SmqUnsubscribeException {
		}};
	private IMessageAdapter<DeviceHeadbeatPackage> hbAdapter = DEF_ADAPTER;
	private final Supplier<String> channelSupplier;
	private volatile String channelName ;
	private final RedisSubscriber subscriber;
	public HeartbeatMonitor(IMessageAdapter<DeviceHeadbeatPackage> hbAdapter,Supplier<String> channelSupplier,JedisPoolLazy jedisPoolLazy) {
		super();
		this.hbAdapter = MoreObjects.firstNonNull(hbAdapter,DEF_ADAPTER);
		this.channelSupplier = checkNotNull(channelSupplier,"channelSupplier is null");
		this.subscriber = RedisFactory.getSubscriber(checkNotNull(jedisPoolLazy,"jedisPoolLazy is null"));
	}
	public HeartbeatMonitor(IMessageAdapter<DeviceHeadbeatPackage> hbAdapter,Supplier<String> channelSupplier){
		this(hbAdapter, channelSupplier, JedisPoolLazy.getDefaultInstance());
	}
	public HeartbeatMonitor(Supplier<String> channelSupplier){
		this(DEF_ADAPTER, channelSupplier);
	}

	@Override
	public void onSubscribe(ServiceHeartbeatPackage t) throws SmqUnsubscribeException {
		start();
	}
	/**
	 * 注册心跳包频道，启动频道监控
	 */
	public void start(){
		stop();
		// 申请新的频道名
		channelName = channelSupplier.get();
		logger.info("Start Heartbeat Monitor ch:[{}]",channelName);
		subscriber.register(new Channel<DeviceHeadbeatPackage>(channelName,hbAdapter){});
	}
	/**
	 * 取消心跳包频道订阅，停止频道监控
	 */
	public void stop(){		
		if(null != channelName){
			synchronized (this) {
				if(null != channelName){
					// 注销上一个频道名
					subscriber.unregister(channelName);
					channelName = null;				
				}
			}
		}
	}
	public IMessageAdapter<DeviceHeadbeatPackage> getHbAdapter() {
		return hbAdapter;
	}
	public HeartbeatMonitor setHbAdapter(IMessageAdapter<DeviceHeadbeatPackage> hbAdapter) {
		this.hbAdapter = checkNotNull(hbAdapter,"hbAdapter is null");
		return this;
	}
}
