package net.gdface.facelog.service;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.service.CommonConstant;

/**
 * 特征表({@code fl_feature})变动侦听器<br>
 * 当{@code fl_feature}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisFeatureListener extends TableListener.Adapter<FeatureBean> implements CommonConstant{

	private final RedisPublisher publisher;
	
	public RedisFeatureListener() {
		this(null);
	}
	public RedisFeatureListener(JedisPoolLazy jedisPoolLazy) {
		publisher = RedisFactory.getPublisher(null ==jedisPoolLazy ?JedisPoolLazy.getDefaultInstance():jedisPoolLazy);
	}
	@Override
	public void afterInsert(FeatureBean bean) {
		try{
			publisher.publish(PUBSUB_FEATURE_INSERT, bean.getMd5());
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void afterUpdate(FeatureBean bean) {
		try{
			publisher.publish(PUBSUB_FEATURE_UPDATE, bean.getMd5());
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void afterDelete(FeatureBean bean) {
		try{
			publisher.publish(PUBSUB_FEATURE_DELETE, bean.getMd5());
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}			

}