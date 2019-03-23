package net.gdface.facelog;

import android.support.test.runner.AndroidJUnit4;

import com.google.common.collect.ImmutableMap;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import redis.clients.jedis.Protocol;

import net.gdface.facelog.CommonConstant;
import net.gdface.facelog.client.IFaceLogClient;
import net.gdface.facelog.client.SubAdapters;
import net.gdface.facelog.thrift.IFaceLogThriftClient;
import net.gdface.thrift.ClientFactory;

import java.util.Map;

/**
 * 数据更新频道订阅示例
 * @author guyadong
 *
 */
@RunWith(AndroidJUnit4.class)
public class PersonInsertAdapterTest implements CommonConstant {
    public static final Logger logger = LoggerFactory.getLogger(PersonInsertAdapterTest.class);
	/** redis 连接参数 */
	private static final Map<JedisPoolLazy.PropName, Object> redisParam =
			ImmutableMap.<JedisPoolLazy.PropName, Object>of(
					/** redis 主机名 */JedisPoolLazy.PropName.host,"10.0.2.2",
					/** redis 端口号 */JedisPoolLazy.PropName.port,Protocol.DEFAULT_PORT,
					/** redis 连接密码 */JedisPoolLazy.PropName.password, "hello"
			);
	@Test
	public void test() {
		// 根据连接参数创建默认实例
		JedisPoolLazy.createDefaultInstance( redisParam);
		final IFaceLogClient serviceClient = ClientFactory.builder().setHostAndPort("10.0.2.2", DEFAULT_PORT).build(IFaceLogThriftClient.class,IFaceLogClient.class);
		new SubAdapters.BasePersonInsertSubAdapter(){
			@Override
			public void onSubscribe(Integer id) throws SmqUnsubscribeException {
				logger.info("insert person ID:{}",id);
				logger.info("new recored {}",serviceClient.getPerson(id).toString(true, false));
			}			
		}.register(RedisFactory.getSubscriber());
	}
}
