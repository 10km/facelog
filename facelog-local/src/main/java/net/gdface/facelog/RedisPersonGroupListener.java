package net.gdface.facelog;

import com.google.common.base.Preconditions;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.PersonGroupBean;
import net.gdface.facelog.db.TableListener;

/**
 * 人员组表({@code fl_person_group})变动侦听器<br>
 * 当{@code fl_person_group}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisPersonGroupListener extends TableListener.Adapter<PersonGroupBean> implements ChannelConstant{

	private final RedisPublisher publisher;
	
	public RedisPersonGroupListener() {
		this(JedisPoolLazy.getDefaultInstance());
	}
	public RedisPersonGroupListener(JedisPoolLazy jedisPoolLazy) {
		this.publisher = RedisFactory.getPublisher(Preconditions.checkNotNull(jedisPoolLazy,"jedisPoolLazy is null"));
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
	public void afterUpdate(PersonGroupBean bean) {
		new RedisPublishTask<Integer>(
				PUBSUB_PERSONGROUP_UPDATE, 
				bean.getId(), 
				publisher)
		.execute();
	}

	@Override
	public void afterDelete(PersonGroupBean bean) {
		new RedisPublishTask<Integer>(
				PUBSUB_PERSONGROUP_DELETE, 
				bean.getId(), 
				publisher)
		.execute();
	}			

}
