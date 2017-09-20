package gu.simplemq;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisProducerSingle;

public class TestRedisProducer {

	@Test
	public void test() throws InterruptedException {
		RedisProducerSingle<Date> producer = new RedisProducerSingle<Date>(Date.class,JedisPoolLazy.getDefaultInstance(),"datelist");
		for(int i=0;i<100;++i){
			Date date = new Date();
			producer.produce(date);
			System.out.println(date.getTime() +" : " +date.toString());
			Thread.sleep(2000);
		}
	}

}
