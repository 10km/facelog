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
import com.google.common.base.Objects;

/**
 * 心跳包频道监控
 * @author guyadong
 *
 */
public class HeartbeatMonitor extends BaseServiceHeartbeatListener {
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
	protected boolean doServiceOnline(ServiceHeartbeatPackage t){
		start();
		return true;
	}
	/**
	 * 注册心跳包频道，启动频道监控
	 * @return 
	 */
	public HeartbeatMonitor start(){
		String ch = channelSupplier.get();
		if(!Objects.equal(channelName, ch)){
			stop();
			// 申请新的频道名
			logger.info("Start Heartbeat Monitor ch:[{}]",ch);
			subscriber.register(new Channel<DeviceHeadbeatPackage>(ch,hbAdapter){});
			channelName = ch;
		}
		return this;
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
					logger.info("unregister last monitor channel :[{}]",channelName);
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
