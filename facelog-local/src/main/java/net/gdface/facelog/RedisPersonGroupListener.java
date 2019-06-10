package net.gdface.facelog;

import static com.google.common.base.Preconditions.*;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.PersonGroupBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.RuntimeDaoException;

/**
 * 人员组表({@code fl_person_group})变动侦听器<br>
 * 当{@code fl_person_group}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisPersonGroupListener extends TableListener.Adapter<PersonGroupBean> implements ChannelConstant{

	private final RedisPublisher publisher;
	private final BaseDao dao;
	private PersonGroupBean beforeUpdatedBean;
	public RedisPersonGroupListener(BaseDao dao) {
		this(dao, JedisPoolLazy.getDefaultInstance());
	}
	public RedisPersonGroupListener(BaseDao dao, JedisPoolLazy jedisPoolLazy) {
		this.dao = checkNotNull(dao,"dao is null");
		this.publisher = RedisFactory.getPublisher(checkNotNull(jedisPoolLazy,"jedisPoolLazy is null"));
	}
	@Override
	public void afterInsert(PersonGroupBean bean) {
		new RedisPublishTask<Integer>(
				PUBSUB_PERSONGROUP_INSERT, 
				bean.getId(), 
				publisher)
		.execute();
	}
	@Override
	public void beforeUpdate(PersonGroupBean bean) throws RuntimeDaoException {
		// 保留更新前的数据
		beforeUpdatedBean = dao.daoGetPersonGroup(bean.getId()).clone();
	}
	@Override
	public void afterUpdate(PersonGroupBean bean) {
		// beforeUpdatedBean 为 null，只可能因为侦听器是被异步调用的
		checkState(beforeUpdatedBean != null,"beforeUpdatedBean must not be null");
		// 保存修改信息
		beforeUpdatedBean.setModified(bean.getModified());
		new RedisPublishTask<PersonGroupBean>(
				PUBSUB_PERSONGROUP_UPDATE, 
				beforeUpdatedBean, 
				publisher)
		.execute();
		beforeUpdatedBean = null;
	}

	@Override
	public void afterDelete(PersonGroupBean bean) {
		new RedisPublishTask<PersonGroupBean>(
				PUBSUB_PERSONGROUP_DELETE, 
				bean, 
				publisher)
		.execute();
	}			

}
