package net.gdface.facelog.service;

import com.google.common.base.Preconditions;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.TableListener;

/**
 * 特征表({@code fl_feature})变动侦听器<br>
 * 当{@code fl_feature}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisFeatureListener extends TableListener.Adapter<FeatureBean> implements ChannelConstant{

	private final RedisPublisher publisher;
	
	public RedisFeatureListener() {
		this(JedisPoolLazy.getDefaultInstance());
	}
	public RedisFeatureListener(JedisPoolLazy jedisPoolLazy) {
		this.publisher = RedisFactory.getPublisher(Preconditions.checkNotNull(jedisPoolLazy,"jedisPoolLazy is null"));
	}
	@Override
	public void afterInsert(FeatureBean bean) {
		new RedisPublishTask<String>(
				PUBSUB_FEATURE_INSERT, 
				bean.getMd5(), 
				publisher)
		.execute();
	}

	@Override
	public void afterUpdate(FeatureBean bean) {
		new RedisPublishTask<String>(
				PUBSUB_FEATURE_UPDATE, 
				bean.getMd5(), 
				publisher)
		.execute();
	}

	@Override
	public void afterDelete(FeatureBean bean) {
		new RedisPublishTask<String>(
				PUBSUB_FEATURE_DELETE, 
				bean.getMd5(), 
				publisher)
		.execute();		
	}			

}
