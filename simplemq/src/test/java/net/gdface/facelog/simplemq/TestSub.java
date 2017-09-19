package net.gdface.facelog.simplemq;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import net.gdface.facelog.simplemq.redis.JedisPoolLazy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSub {

	private JedisPoolLazy pool;
	private JedisPubSub jedisPubSub=new JedisPubSub(){
		@Override
		public void onMessage(String channel, String message) {
			System.out.println("@"+channel +":"+message);
		}};
	@Before
	public void init() {
		pool = JedisPoolLazy.getDefaultInstance();
	}
	@Test
	public void test1Subscribe(){
		System.out.println("jedisPubSub" + jedisPubSub.toString());
		new Thread(new Runnable(){
			@Override
			public void run() {
				Jedis jedis = pool.apply();
				try{
					System.out.println("jedisPubSub" + jedisPubSub.toString());
					jedis.subscribe(jedisPubSub);
				}finally{
					pool.free();
				}				
			}}).start();
		System.out.println("jedisPubSub" + jedisPubSub.toString());
		jedisPubSub.subscribe("chat2");
		
		jedisPubSub.unsubscribe();
	}
		
	

}
