package net.gdface.facelog.service;

import com.google.common.base.Preconditions;

import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisConsumer;
import gu.simplemq.redis.RedisFactory;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.service.CommonConstant;
import net.gdface.facelog.service.Dao;

class RedisLogConsumer implements CommonConstant {
	private final RedisConsumer consumer;
	private final Dao dao;
	public RedisLogConsumer(JedisPoolLazy jedisPoolLazy,Dao dao){
		this.dao = Preconditions.checkNotNull(dao);
		this.consumer = RedisFactory.getConsumer(null ==jedisPoolLazy ?JedisPoolLazy.getDefaultInstance():jedisPoolLazy);
		this.consumer.register(QUEUE_LOG.clone().setAdapter(new IMessageAdapter<LogBean>(){
			@Override
			public void onSubscribe(LogBean t) throws SmqUnsubscribeException {
				try{
					RedisLogConsumer.this.dao.daoAddLog(t);
				}catch(Exception e){
					logger.error(e.getMessage(),e);
				}
			}}));
	}
}
