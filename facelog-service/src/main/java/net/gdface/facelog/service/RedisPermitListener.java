package net.gdface.facelog.service;

import com.google.common.base.Preconditions;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.PermitBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.service.CommonConstant;

/**
 * 通行权限关联表({@code fl_permit})变动侦听器<br>
 * 当{@code fl_permit}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisPermitListener extends TableListener.Adapter<PermitBean> implements CommonConstant{

	private final RedisPublisher publisher;
	
	public RedisPermitListener() {
		this(JedisPoolLazy.getDefaultInstance());
	}
	public RedisPermitListener(JedisPoolLazy jedisPoolLazy) {
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
	public void afterDelete(PermitBean bean) {
		new RedisPublishTask<PermitBean>(
				PUBSUB_PERMIT_DELETE, 
				bean, 
				publisher)
		.execute();		
	}			

}
