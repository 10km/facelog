package net.gdface.facelog.service;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.service.CommonConstant;

/**
 * 人员表({@code fl_person})变动侦听器<br>
 * 当{@code fl_person}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisPersonListener extends TableListener.Adapter<PersonBean> implements CommonConstant{

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