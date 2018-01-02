package net.gdface.facelog.client;

import static org.junit.Assert.*;

import org.junit.Test;

import gu.simplemq.Channel;
import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisSubscriber;
import net.gdface.facelog.client.SubAdapters.BasePersonInsertSubAdapter;

/**
 * 数据更新频道订阅示例
 * @author guyadong
 *
 */
public class PersonInsertAdapterTest implements CommonConstant {

	@Test
	public void test() {
		final IFaceLogClient serviceClient = ClientFactory.builder().setHostAndPort("127.0.0.1", DEFAULT_PORT).build();
		new SubAdapters.BasePersonInsertSubAdapter(){
			@Override
			public void onSubscribe(Integer id) throws SmqUnsubscribeException {
				logger.info("insert person ID:{}",id);
				logger.info("new recored {}",serviceClient.getPerson(id).toString(true, false));
			}			
		}.register(RedisFactory.getSubscriber());
	}
}
