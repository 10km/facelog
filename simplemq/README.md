#simple Message Queue
simple Message Queue(以下简称simpleMQ)设计用来实现一个支持消息推送的中间件

simpleMQ基于redis和fastjson实现消息队列,频道订阅,K-V表，

##消息队列

消息队列被设计成producer/consumer模型

[RedisProducer.java](src/main/java/gu/simplemq/redis/RedisProducer.java )

[RedisConsumer.java](src/main/java/gu/simplemq/redis/RedisConsumer.java )

##频道订阅

频道订阅被设计成publisher/subscriber模型

[RedisPublisher.java](src/main/java/gu/simplemq/redis/RedisPublisher.java )

[RedisSubscriber.java](src/main/java/gu/simplemq/redis/RedisSubscriber.java )

##K-V表

K-V表

[RedisTable.java](src/main/java/gu/simplemq/redis/RedisTable.java )

##实例化

[RedisFactory.java](src/main/java/gu/simplemq/redis/RedisFactory.java )用于创建上述的类的实例

#调用示例

RedisConsumer示例

	public class TestRedisConsumer {
		private static final Logger logger = LoggerFactory.getLogger(TestRedisConsumer.class);

		@Test
		public void testRedisConsumer(){
			RedisConsumer consumer = RedisFactory.getConsumer(JedisPoolLazy.getDefaultInstance());
			Channel<String> list1 = new Channel<String>("list1",String.class,new IMessageAdapter<String>(){
	
				@Override
				public void onSubscribe(String t) throws SmqUnsubscribeException {
					logger.info("{}:{}","list1",t);
				}} );
			Channel<String> list2 = new Channel<String>("list2",String.class,new IMessageAdapter<String>(){
	
				@Override
				public void onSubscribe(String t) throws SmqUnsubscribeException {
					logger.info("{}:{}","list2",t);
				}} );
			Channel<String> list3 = new Channel<String>("list3",String.class,new IMessageAdapter<String>(){
	
				@Override
				public void onSubscribe(String t) throws SmqUnsubscribeException {
					logger.info("{}:{}","list3",t);
				}} );
			consumer.register(list1,list2);
			consumer.register(list3);
			consumer.unregister(list1);
		}
	}

RedisSubscriber示例


	public class TestRedisSubscriber {
		private static final Logger logger = LoggerFactory.getLogger(TestRedisSubscriber.class);
	
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
