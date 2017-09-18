package net.gdface.facelog.message;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRedisSubscriber {
	private static final Logger logger = LoggerFactory.getLogger(TestRedisSubscriber.class);

	@Test
	public void test() {
		RedisSubscriber redisSubscriber = RedisSubscriber.getSubscriber(JedisPoolLazy.getDefaultInstance());
		Channel<String> chat1 = new Channel<String>("chat1",String.class,new IMessageAdapter<String>(){

			@Override
			public void onSubscribe(String t) throws net.gdface.facelog.message.IMessageAdapter.UnsubscribeException {
				logger.info("{}:{}","chat1",t);
			}} );
		Channel<String> chat2 = new Channel<String>("chat2",String.class,new IMessageAdapter<String>(){

			@Override
			public void onSubscribe(String t) throws net.gdface.facelog.message.IMessageAdapter.UnsubscribeException {
				logger.info("{}:{}","chat2",t);
			}} );
		Channel<String> chat3 = new Channel<String>("chat3",String.class,new IMessageAdapter<String>(){

			@Override
			public void onSubscribe(String t) throws net.gdface.facelog.message.IMessageAdapter.UnsubscribeException {
				logger.info("{}:{}","chat3",t);
			}} );
		redisSubscriber.register(chat1,chat2);
		
		redisSubscriber.register(chat3);
		redisSubscriber.unsubscribe(chat1.name);
		redisSubscriber.unsubscribe();
	}
}
