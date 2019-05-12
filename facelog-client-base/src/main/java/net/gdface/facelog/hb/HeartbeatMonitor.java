package net.gdface.facelog.hb;

import com.google.common.base.Supplier;

import gu.simplemq.Channel;
import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisSubscriber;
import net.gdface.facelog.DeviceHeartdbeatPackage;
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

	public static final IMessageAdapter<DeviceHeartdbeatPackage> DEF_DEVICE_ADAPTER = new IMessageAdapter<DeviceHeartdbeatPackage>(){

		@Override
		public void onSubscribe(DeviceHeartdbeatPackage t) throws SmqUnsubscribeException {
		}};
	private IMessageAdapter<DeviceHeartdbeatPackage> hbAdapter = DEF_DEVICE_ADAPTER;
	private final Supplier<String> channelSupplier;
	private volatile String channelName ;
	private final RedisSubscriber subscriber;
	/**
	 * 构造方法
	 * @param hbAdapter 设备心跳包侦听实例
	 * @param channelSupplier 心跳包频道名的{@link Supplier}实例，用于提供当前的心跳包频道名
	 * @param jedisPoolLazy jedis连接池对象,为{@code null}使用默认实例
	 */
	public HeartbeatMonitor(IMessageAdapter<DeviceHeartdbeatPackage> hbAdapter,Supplier<String> channelSupplier,JedisPoolLazy jedisPoolLazy) {
		super();
		this.hbAdapter = MoreObjects.firstNonNull(hbAdapter,DEF_DEVICE_ADAPTER);
		this.channelSupplier = checkNotNull(channelSupplier,"channelSupplier is null");
		this.subscriber = RedisFactory.getSubscriber(MoreObjects.firstNonNull(jedisPoolLazy,JedisPoolLazy.getDefaultInstance()));
	}
	/**
	 * 构造方法
	 * @param hbAdapter 设备心跳包侦听实例
	 * @param channelSupplier 心跳包频道名的{@link Supplier}实例，用于提供当前的心跳包频道名
	 */
	public HeartbeatMonitor(IMessageAdapter<DeviceHeartdbeatPackage> hbAdapter,Supplier<String> channelSupplier){
		this(hbAdapter, channelSupplier, JedisPoolLazy.getDefaultInstance());
	}
	public HeartbeatMonitor(Supplier<String> channelSupplier){
		this(DEF_DEVICE_ADAPTER, channelSupplier);
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
			subscriber.register(new Channel<DeviceHeartdbeatPackage>(ch,hbAdapter){});
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
	/**
	 * @return 返回设备心跳包侦听实例
	 */
	public IMessageAdapter<DeviceHeartdbeatPackage> getHbAdapter() {
		return hbAdapter;
	}
	/**
	 * 设置设备心跳包侦听实例
	 * @param hbAdapter 不可为{@code null}
	 * @return
	 */
	public HeartbeatMonitor setHbAdapter(IMessageAdapter<DeviceHeartdbeatPackage> hbAdapter) {
		this.hbAdapter = checkNotNull(hbAdapter,"hbAdapter is null");
		return this;
	}
}
