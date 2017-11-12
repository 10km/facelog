package gu.simplemq;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gu.simplemq.Channel;
import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisSubscriber;

/**
 * @author guyadong
 *
 */
public class RedisSubscriberTest {
	private static final Logger logger = LoggerFactory.getLogger(RedisSubscriberTest.class);

	@Test
	public void test() {
		RedisSubscriber subscriber = RedisFactory.getSubscriber(JedisPoolLazy.getDefaultInstance());
		Channel<String> chat1 = new Channel<String>("chat1",String.class,new IMessageAdapter<String>(){

			@Override
			public void onSubscribe(String t) throws SmqUnsubscribeException {
				logger.info("{}:{}","chat1",t);
			}} );
		Channel<String> chat2 = new Channel<String>("chat2",String.class,new IMessageAdapter<String>(){

			@Override
			public void onSubscribe(String t) throws SmqUnsubscribeException {
				logger.info("{}:{}","chat2",t);
			}} );
		Channel<String> chat3 = new Channel<String>("chat3",String.class,new IMessageAdapter<String>(){

			@Override
			public void onSubscribe(String t) throws SmqUnsubscribeException {
				logger.info("{}:{}","chat3",t);
			}} );
		subscriber.register(chat1,chat2);
		
		subscriber.register(chat3);
		subscriber.unsubscribe(chat1.name);
		subscriber.unsubscribe();
	}
}
