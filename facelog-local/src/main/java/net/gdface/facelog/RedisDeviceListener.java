package net.gdface.facelog;

import com.google.common.base.Preconditions;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.TableListener;

/**
 * 设备表({@code fl_device})变动侦听器<br>
 * 当{@code fl_device}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisDeviceListener extends TableListener.Adapter<DeviceBean> implements ChannelConstant{

	private final RedisPublisher publisher;
	
	public RedisDeviceListener() {
		this(JedisPoolLazy.getDefaultInstance());
	}
	public RedisDeviceListener(JedisPoolLazy jedisPoolLazy) {
		this.publisher = RedisFactory.getPublisher(Preconditions.checkNotNull(jedisPoolLazy,"jedisPoolLazy is null"));
	}
	@Override
	public void afterInsert(DeviceBean bean) {
		new RedisPublishTask<Integer>(
				PUBSUB_DEVICE_INSERT, 
				bean.getId(), 
				publisher)
		.execute();
	}

	@Override
	public void afterUpdate(DeviceBean bean) {
		new RedisPublishTask<Integer>(
				PUBSUB_DEVICE_UPDATE, 
				bean.getId(), 
				publisher)
		.execute();
	}

	@Override
	public void afterDelete(DeviceBean bean) {
		new RedisPublishTask<Integer>(
				PUBSUB_DEVICE_DELETE, 
				bean.getId(), 
				publisher)
		.execute();
	}			

}
