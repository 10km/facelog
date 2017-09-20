package gu.simplemq;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gu.simplemq.Channel;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;

public class TestRedisPublisher {
	private static final Logger logger = LoggerFactory.getLogger(TestRedisPublisher.class);

	@Test
	public void test() throws InterruptedException {
		 Channel<Date> chat1 = new Channel<Date>("chat1",Date.class);
		IPublisher publisher = RedisFactory.getPublisher(JedisPoolLazy.getDefaultInstance());
		for(int i=0;i<100;++i){
			Date date = new Date();
			publisher.publish(chat1, date);
			logger.info(date.getTime() +" : " +date.toString());
			Thread.sleep(2000);
		}
	}

}
