package net.gdface.facelog;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.TableListener;

public class RedisFeatureListener extends TableListener.Adapter<FeatureBean> implements CommonConstant{

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
