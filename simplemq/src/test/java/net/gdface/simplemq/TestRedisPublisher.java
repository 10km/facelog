package net.gdface.simplemq;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.gdface.simplemq.Channel;
import net.gdface.simplemq.redis.JedisPoolLazy;
import net.gdface.simplemq.redis.RedisPublisher;

public class TestRedisPublisher {
	private static final Logger logger = LoggerFactory.getLogger(TestRedisPublisher.class);

	@Test
	public void test() throws InterruptedException {
		 Channel<Date> chat1 = new Channel<Date>("chat1",Date.class);
		RedisPublisher publisher = new RedisPublisher(JedisPoolLazy.getDefaultInstance());
		for(int i=0;i<100;++i){
			Date date = new Date();
			publisher.publish(chat1, date, Date.class);
			logger.info(date.getTime() +" : " +date.toString());
			Thread.sleep(2000);
		}
	}

}
