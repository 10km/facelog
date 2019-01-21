package net.gdface.facelog.client;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.RedisFactory;
import net.gdface.facelog.CommonConstant;
import net.gdface.facelog.IFaceLog;
import net.gdface.thrift.ClientFactory;

/**
 * 数据更新频道订阅示例
 * @author guyadong
 *
 */
public class PersonInsertAdapterTest implements CommonConstant {
    public static final Logger logger = LoggerFactory.getLogger(PersonInsertAdapterTest.class);

	@Test
	public void test() {
		final IFaceLogClient serviceClient = ClientFactory.builder().setHostAndPort("127.0.0.1", DEFAULT_PORT).build(IFaceLog.class, IFaceLogClient.class);
		new SubAdapters.BasePersonInsertSubAdapter(){
			@Override
			public void onSubscribe(Integer id) throws SmqUnsubscribeException {
				logger.info("insert person ID:{}",id);
				logger.info("new recored {}",serviceClient.getPerson(id).toString(true, false));
			}			
		}.register(RedisFactory.getSubscriber());
	}
}
