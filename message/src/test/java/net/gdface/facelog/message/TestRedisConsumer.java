package net.gdface.facelog.message;

import static org.junit.Assert.*;

import org.junit.Test;

import net.gdface.facelog.message.Consumer.Action;
import net.gdface.facelog.message.Consumer.BreakException;

public class TestRedisConsumer {

	@Test
	public void test() {
		@SuppressWarnings("resource")
		Consumer<String> consumer = new RedisConsumer<String>(String.class,JedisPoolLazy.getDefaultInstance(),"list1").setAction(new Action<String>(){
			@Override
			public void consume(String t) throws BreakException {
				System.out.println("list1:" + t);
			}});;
		try{
			consumer.open();
		}finally{
			consumer.close();
		}
	}

}
