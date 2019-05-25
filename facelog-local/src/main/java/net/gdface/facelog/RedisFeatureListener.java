package net.gdface.facelog;

import static com.google.common.base.Preconditions.*;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.RuntimeDaoException;

/**
 * 特征表({@code fl_feature})变动侦听器<br>
 * 当{@code fl_feature}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisFeatureListener extends TableListener.Adapter<FeatureBean> implements ChannelConstant{

	private final RedisPublisher publisher;
	private FeatureBean beforeUpdatedBean;	
	public RedisFeatureListener() {
		this(JedisPoolLazy.getDefaultInstance());
	}
	public RedisFeatureListener(JedisPoolLazy jedisPoolLazy) {
		this.publisher = RedisFactory.getPublisher(checkNotNull(jedisPoolLazy,"jedisPoolLazy is null"));
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
	public void beforeUpdate(FeatureBean bean) throws RuntimeDaoException {
		// 保留更新前的数据
		beforeUpdatedBean = bean.clone();
	}
	@Override
	public void afterUpdate(FeatureBean bean) {
		// beforeUpdatedBean 为 null，只可能因为侦听器是被异步调用的
		checkState(beforeUpdatedBean != null,"beforeUpdatedBean must not be null");
		new RedisPublishTask<FeatureBean>(
				PUBSUB_FEATURE_UPDATE, 
				beforeUpdatedBean, 
				publisher)
		.execute();
	}

	@Override
	public void afterDelete(FeatureBean bean) {
		new RedisPublishTask<FeatureBean>(
				PUBSUB_FEATURE_DELETE, 
				bean, 
				publisher)
		.execute();		
	}			

}
