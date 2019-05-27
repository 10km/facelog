package net.gdface.facelog;

import static com.google.common.base.Preconditions.*;

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
	public void afterDelete(FeatureBean bean) {
		FeatureBean deleted = bean.clone();		
		// 为减少发送到redis数据体积，将特征码字段清除
		int modified = deleted.getModified();
		deleted.setFeature(null);
		deleted.setModified(modified);
		new RedisPublishTask<FeatureBean>(
				PUBSUB_FEATURE_DELETE, 
				deleted, 
				publisher)
		.execute();		
	}			

}
