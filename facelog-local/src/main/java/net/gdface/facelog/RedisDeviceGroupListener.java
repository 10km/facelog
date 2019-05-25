package net.gdface.facelog;

import com.google.common.base.Preconditions;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.DeviceGroupBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.RuntimeDaoException;

/**
 * 设备组表({@code fl_device_group})变动侦听器<br>
 * 当{@code fl_device_group}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisDeviceGroupListener extends TableListener.Adapter<DeviceGroupBean> implements ChannelConstant{

	private final RedisPublisher publisher;
	private DeviceGroupBean beforeUpdatedBean;
	public RedisDeviceGroupListener() {
		this(JedisPoolLazy.getDefaultInstance());
	}
	public RedisDeviceGroupListener(JedisPoolLazy jedisPoolLazy) {
		this.publisher = RedisFactory.getPublisher(Preconditions.checkNotNull(jedisPoolLazy,"jedisPoolLazy is null"));
	}
	@Override
	public void afterInsert(DeviceGroupBean bean) {
		new RedisPublishTask<Integer>(
				PUBSUB_DEVICEGROUP_INSERT, 
				bean.getId(), 
				publisher)
		.execute();
	}

	@Override
	public void beforeUpdate(DeviceGroupBean bean) throws RuntimeDaoException {
		// 保留更新前的数据
		beforeUpdatedBean = bean.clone();
	}
	@Override
	public void afterUpdate(DeviceGroupBean bean) {
		new RedisPublishTask<DeviceGroupBean>(
				PUBSUB_DEVICEGROUP_UPDATE, 
				beforeUpdatedBean, 
				publisher)
		.execute();
	}

	@Override
	public void afterDelete(DeviceGroupBean bean) {
		new RedisPublishTask<DeviceGroupBean>(
				PUBSUB_DEVICEGROUP_DELETE, 
				bean, 
				publisher)
		.execute();
	}			

}
