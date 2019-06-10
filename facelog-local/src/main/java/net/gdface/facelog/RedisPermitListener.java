package net.gdface.facelog;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.Preconditions;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.PermitBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.RuntimeDaoException;

/**
 * 通行权限关联表({@code fl_permit})变动侦听器<br>
 * 当{@code fl_permit}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisPermitListener extends TableListener.Adapter<PermitBean> implements ChannelConstant{

	private final RedisPublisher publisher;
	private final BaseDao dao;
	private PermitBean beforeUpdatedBean;
	public RedisPermitListener(BaseDao dao) {
		this(dao, JedisPoolLazy.getDefaultInstance());
	}
	public RedisPermitListener(BaseDao dao, JedisPoolLazy jedisPoolLazy) {
		this.dao = checkNotNull(dao,"dao is null");
		this.publisher = RedisFactory.getPublisher(Preconditions.checkNotNull(jedisPoolLazy,"jedisPoolLazy is null"));
	}
	@Override
	public void afterInsert(PermitBean bean) {
		new RedisPublishTask<PermitBean>(
				PUBSUB_PERMIT_INSERT, 
				bean, 
				publisher)
		.execute();
	}

	@Override
	public void beforeUpdate(PermitBean bean) throws RuntimeDaoException {
		// 保留更新前的数据
		beforeUpdatedBean = dao.daoGetPermit(bean.getDeviceGroupId(), bean.getPersonGroupId()).clone();
	}
	@Override
	public void afterUpdate(PermitBean bean) throws RuntimeDaoException {
		// beforeUpdatedBean 为 null，只可能因为侦听器是被异步调用的
		checkState(beforeUpdatedBean != null,"beforeUpdatedBean must not be null");
		// 保存修改信息
		beforeUpdatedBean.setModified(bean.getModified());
		new RedisPublishTask<PermitBean>(
				PUBSUB_PERMIT_UPDATE, 
				beforeUpdatedBean, 
				publisher)
		.execute();
		beforeUpdatedBean = null;
	}
	@Override
	public void afterDelete(PermitBean bean) {
		new RedisPublishTask<PermitBean>(
				PUBSUB_PERMIT_DELETE, 
				bean, 
				publisher)
		.execute();
	}			

}
