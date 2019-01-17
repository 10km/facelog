package net.gdface.facelog;

import com.google.common.base.Preconditions;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableListener;

/**
 * 人员表({@code fl_person})变动侦听器<br>
 * 当{@code fl_person}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisPersonListener extends TableListener.Adapter<PersonBean> implements ChannelConstant{

	private final RedisPublisher publisher;
	
	public RedisPersonListener() {
		this(JedisPoolLazy.getDefaultInstance());
	}
	public RedisPersonListener(JedisPoolLazy jedisPoolLazy) {
		this.publisher = RedisFactory.getPublisher(Preconditions.checkNotNull(jedisPoolLazy,"jedisPoolLazy is null"));
	}
	@Override
	public void afterInsert(PersonBean bean) {
		new RedisPublishTask<Integer>(
				PUBSUB_PERSON_INSERT, 
				bean.getId(), 
				publisher)
		.execute();
	}

	@Override
	public void afterUpdate(PersonBean bean) {
		new RedisPublishTask<Integer>(
				PUBSUB_PERSON_UPDATE, 
				bean.getId(), 
				publisher)
		.execute();
	}

	@Override
	public void afterDelete(PersonBean bean) {
		new RedisPublishTask<Integer>(
				PUBSUB_PERSON_DELETE, 
				bean.getId(), 
				publisher)
		.execute();
	}			

}
