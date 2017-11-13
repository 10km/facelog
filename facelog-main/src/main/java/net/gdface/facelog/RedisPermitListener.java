package net.gdface.facelog;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.PermitBean;
import net.gdface.facelog.db.TableListener;

/**
 * 通行权限关联表({@code fl_permit})变动侦听器<br>
 * 当{@code fl_permit}记录增删改时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisPermitListener extends TableListener.Adapter<PermitBean> implements CommonConstant{

	private final RedisPublisher publisher;
	
	public RedisPermitListener() {
		this(null);
	}
	public RedisPermitListener(JedisPoolLazy jedisPoolLazy) {
		publisher = RedisFactory.getPublisher(null ==jedisPoolLazy ?JedisPoolLazy.getDefaultInstance():jedisPoolLazy);
	}
	@Override
	public void afterInsert(PermitBean bean) {
		try{
			publisher.publish(PUBSUB_PERMIT_INSERT, bean);
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void afterDelete(PermitBean bean) {
		try{
			publisher.publish(PUBSUB_PERMIT_DELETE, bean);
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}			

}
