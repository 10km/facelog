package net.gdface.facelog.message;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRedisSubscriber {
	private static final Logger logger = LoggerFactory.getLogger(TestRedisSubscriber.class);

	@Test
	public void test() {
		ISubscriber redisSubscriber = RedisSubscriber.getSubscriber(JedisPoolLazy.getDefaultInstance());
		ChannelSub<String> chat1 = new ChannelSub<String>("chat1",String.class,new IOnSubscribe<String>(){

			@Override
			public void onSubscribe(String t) throws net.gdface.facelog.message.IOnSubscribe.UnsubscribeException {
				logger.info("{}:{}","chat1",t);
			}} );
		ChannelSub<String> chat2 = new ChannelSub<String>("chat2",String.class,new IOnSubscribe<String>(){

			@Override
			public void onSubscribe(String t) throws net.gdface.facelog.message.IOnSubscribe.UnsubscribeException {
				logger.info("{}:{}","chat2",t);
			}} );
		redisSubscriber.register(chat1,chat2);
		
		redisSubscriber.unsubscribe();;
	}

}
