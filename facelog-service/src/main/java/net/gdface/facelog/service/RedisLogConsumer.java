package net.gdface.facelog.service;

import static com.google.common.base.Preconditions.checkNotNull;

import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisConsumer;
import gu.simplemq.redis.RedisFactory;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.service.BaseDao;

/**
 * 负责验证日志服务端统一保存的消费实现(未完成)
 * @author guyadong
 *
 */
class RedisLogConsumer implements ServiceConstant {
	private final RedisConsumer consumer;
	private final BaseDao dao;
	public RedisLogConsumer(BaseDao dao){
		this(dao,JedisPoolLazy.getDefaultInstance());
	}
	public RedisLogConsumer(BaseDao dao,JedisPoolLazy poolLazy){
		this.dao = checkNotNull(dao);
		this.consumer = RedisFactory.getConsumer(checkNotNull(poolLazy));
		this.consumer.register(ChannelConstant.QUEUE_LOG.asMutable().setAdapter(new IMessageAdapter<LogBean>(){
			@Override
			public void onSubscribe(final LogBean t) throws SmqUnsubscribeException {
				GLOBAL_EXCEUTOR.execute(new Runnable(){
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
