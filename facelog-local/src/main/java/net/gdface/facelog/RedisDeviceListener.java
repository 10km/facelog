package net.gdface.facelog;

import static com.google.common.base.Preconditions.*;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.RuntimeDaoException;

/**
 * 设备表({@code fl_device})变动侦听器<br>
 * 当{@code fl_device}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisDeviceListener extends TableListener.Adapter<DeviceBean> implements ChannelConstant{

	private final RedisPublisher publisher;
	private final BaseDao dao;
	private DeviceBean beforeUpdatedBean;
	public RedisDeviceListener(BaseDao dao) {
		this(dao, JedisPoolLazy.getDefaultInstance());
	}
	public RedisDeviceListener(BaseDao dao, JedisPoolLazy jedisPoolLazy) {
		this.dao = checkNotNull(dao,"dao is null");
		this.publisher = RedisFactory.getPublisher(checkNotNull(jedisPoolLazy,"jedisPoolLazy is null"));
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
	public void beforeUpdate(DeviceBean bean) throws RuntimeDaoException {
		// 保留更新前的数据
		beforeUpdatedBean = dao.daoGetDevice(bean.getId()).clone();
	}
	@Override
	public void afterUpdate(DeviceBean bean) {
		// beforeUpdatedBean 为 null，只可能因为侦听器是被异步调用的
		checkState(beforeUpdatedBean != null,"beforeUpdatedBean must not be null");
		// 保存修改信息
		beforeUpdatedBean.setModified(bean.getModified());
		new RedisPublishTask<DeviceBean>(
				PUBSUB_DEVICE_UPDATE, 
				beforeUpdatedBean, 
				publisher)
		.execute();
		beforeUpdatedBean = null;
	}

	@Override
	public void afterDelete(DeviceBean bean) {
		new RedisPublishTask<DeviceBean>(
				PUBSUB_DEVICE_DELETE, 
				bean, 
				publisher)
		.execute();
	}			

}
