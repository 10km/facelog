package net.gdface.facelog;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableListener;

public class RedisPersonListener extends TableListener.Adapter<PersonBean> implements CommonConstant{

	private final RedisPublisher publisher;
	
	public RedisPersonListener() {
		this(null);
	}
	public RedisPersonListener(JedisPoolLazy jedisPoolLazy) {
		publisher = RedisFactory.getPublisher(null ==jedisPoolLazy ?JedisPoolLazy.getDefaultInstance():jedisPoolLazy);
	}
	@Override
	public void afterInsert(PersonBean bean) {
		try{
			publisher.publish(PUBSUB_PERSON_INSERT, bean.getId());
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void afterUpdate(PersonBean bean) {
		try{
			publisher.publish(PUBSUB_PERSON_UPDATE, bean.getId());
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void afterDelete(PersonBean bean) {
		try{
			publisher.publish(PUBSUB_PERSON_DELETE, bean.getId());
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}			

}
