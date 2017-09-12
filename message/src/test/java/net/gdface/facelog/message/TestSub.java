package net.gdface.facelog.message;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class TestSub {

	private JedisPoolLazy pool;

	@Before
	public void init() {
		pool = JedisPoolLazy.getDefaultInstance();
	}
	@Test
	public void testSubscribe(){
		Jedis jedis = pool.apply();
		try{
			jedis.subscribe(new JedisPubSub(){
				@Override
				public void onMessage(String channel, String message) {
					System.out.println(message);
				}}, "chat");
		}finally{
			pool.free();
		}
	}
}
