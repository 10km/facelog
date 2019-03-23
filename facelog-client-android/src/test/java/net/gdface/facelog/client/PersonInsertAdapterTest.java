package net.gdface.facelog.client;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.JedisPoolLazy.PropName;
import net.gdface.facelog.CommonConstant;
import net.gdface.facelog.thrift.IFaceLogThriftClient;
import net.gdface.thrift.ClientFactory;
import redis.clients.jedis.Protocol;

/**
 * 数据更新频道订阅示例
 * @author guyadong
 *
 */
public class PersonInsertAdapterTest implements CommonConstant {
    public static final Logger logger = LoggerFactory.getLogger(PersonInsertAdapterTest.class);
	/** redis 连接参数 */
	private static final Map<PropName, Object> redisParam = 
			ImmutableMap.<PropName, Object>of(
					/** redis 主机名 */PropName.host,Protocol.DEFAULT_HOST,
					/** redis 端口号 */PropName.port,Protocol.DEFAULT_PORT,
					/** redis 连接密码 */PropName.password, "hello"
					);
	@Test
	public void test() {
		// 根据连接参数创建默认实例 
		JedisPoolLazy.createDefaultInstance( redisParam);
		final IFaceLogClient serviceClient = ClientFactory.builder().setHostAndPort("127.0.0.1", DEFAULT_PORT).build(IFaceLogThriftClient.class,IFaceLogClient.class);
		new SubAdapters.BasePersonInsertSubAdapter(){
			@Override
			public void onSubscribe(Integer id) throws SmqUnsubscribeException {
				logger.info("insert person ID:{}",id);
				logger.info("new recored {}",serviceClient.getPerson(id).toString(true, false));
			}			
		}.register(RedisFactory.getSubscriber());
	}
}
