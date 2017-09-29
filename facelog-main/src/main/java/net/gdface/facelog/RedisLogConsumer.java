package net.gdface.facelog;

import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisConsumer;
import gu.simplemq.redis.RedisFactory;
import net.gdface.facelog.db.ILogManager;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.mysql.TableInstance;

public class RedisLogConsumer implements CommonConstant {
	private static final ILogManager logManager = (ILogManager) TableInstance.getInstance(LogBean.class);
	private final RedisConsumer consumer;
	public RedisLogConsumer() {
		this(null);
	}
	public RedisLogConsumer(JedisPoolLazy jedisPoolLazy){
		consumer = RedisFactory.getConsumer(null ==jedisPoolLazy ?JedisPoolLazy.getDefaultInstance():jedisPoolLazy);
		consumer.register(QUEUE_LOG.clone().setAdapter(new IMessageAdapter<LogBean>(){
			@Override
			public void onSubscribe(LogBean t) throws SmqUnsubscribeException {
				try{
					logManager.save(t);
				}catch(Exception e){
					logger.error(e.getMessage(),e);
				}
			}}));
	}
}
