package net.gdface.facelog.service;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import static com.google.common.base.Preconditions.checkNotNull;

import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisConsumer;
import gu.simplemq.redis.RedisFactory;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.service.CommonConstant;
import net.gdface.facelog.service.Dao;

/**
 * 负责验证日志服务端统一保存的消费实现(未完成)
 * @author guyadong
 *
 */
class RedisLogConsumer implements CommonConstant {
	private final RedisConsumer consumer;
	private final Dao dao;
	private final Executor executor;
	public RedisLogConsumer(Dao dao,final Executor executor){
		this.dao = checkNotNull(dao);
		this.executor = checkNotNull(executor,"executor is null");
		this.consumer = RedisFactory.getConsumer(JedisPoolLazy.getDefaultInstance());
		this.consumer.register(QUEUE_LOG.clone().setAdapter(new IMessageAdapter<LogBean>(){
			@Override
			public void onSubscribe(final LogBean t) throws SmqUnsubscribeException {
				RedisLogConsumer.this.executor.execute(new Runnable(){
					@Override
					public void run() {
						try{
							RedisLogConsumer.this.dao.daoAddLog(t);
						}catch(Exception e){
							logger.error(e.getMessage(),e);
						}						
					}});
			}}));
	}
}
