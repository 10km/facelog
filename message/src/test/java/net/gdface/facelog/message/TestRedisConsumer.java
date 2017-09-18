package net.gdface.facelog.message;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestRedisConsumer {

	@Test
	public void test() {
		@SuppressWarnings("resource")
		ConsumerSingle<String> consumer = new RedisConsumerSingle<String>(String.class,JedisPoolLazy.getDefaultInstance(),"list1").setAdapter(new IMessageAdapter<String>(){
			@Override
			public void onSubscribe(String t) throws UnsubscribeException {
				System.out.println("list1:" + t);
			}});
		try{
			consumer.open();
		}finally{
			consumer.close();
		}
	}

}
