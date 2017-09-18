package net.gdface.facelog.message;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRedisConsumer {
	private static final Logger logger = LoggerFactory.getLogger(TestRedisConsumer.class);

	@Test
	public void testRedisConsumerSingle() {
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
	@Test
	public void testRedisConsumer(){
		RedisConsumer consumer = RedisConsumer.getSubscriber(JedisPoolLazy.getDefaultInstance());
		Channel<String> list1 = new Channel<String>("list1",String.class,new IMessageAdapter<String>(){

			@Override
			public void onSubscribe(String t) throws net.gdface.facelog.message.IMessageAdapter.UnsubscribeException {
				logger.info("{}:{}","list1",t);
			}} );
		Channel<String> list2 = new Channel<String>("list2",String.class,new IMessageAdapter<String>(){

			@Override
			public void onSubscribe(String t) throws net.gdface.facelog.message.IMessageAdapter.UnsubscribeException {
				logger.info("{}:{}","list2",t);
			}} );
		Channel<String> list3 = new Channel<String>("list3",String.class,new IMessageAdapter<String>(){

			@Override
			public void onSubscribe(String t) throws net.gdface.facelog.message.IMessageAdapter.UnsubscribeException {
				logger.info("{}:{}","list3",t);
			}} );
		consumer.register(list1,list2);
		consumer.register(list3);
		consumer.unregister(list1);
	}
}
