package net.gdface.facelog.client;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.JedisPoolLazy.PropName;
import net.gdface.facelog.DeviceHeartdbeatPackage;
import net.gdface.facelog.Token;
import net.gdface.facelog.hb.DeviceHeartbeatListener;
import net.gdface.facelog.thrift.IFaceLogThriftClient;
import net.gdface.thrift.ClientFactory;
import redis.clients.jedis.Protocol;

/**
 * 心跳包测试
 * @author guyadong
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HeartbeatTest implements ChannelConstant{
    public static final Logger logger = LoggerFactory.getLogger(HeartbeatTest.class);

	private static IFaceLogClient facelogClient;
	private static Token rootToken;
	/** redis 连接参数 */
	private static final Map<PropName, Object> redisParam = 
			ImmutableMap.<PropName, Object>of(
					/** redis 主机名 */PropName.host,Protocol.DEFAULT_HOST,
					/** redis 端口号 */PropName.port,Protocol.DEFAULT_PORT,
					/** redis 连接密码 */PropName.password, "hello"
					);
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// 根据连接参数创建默认实例 
		JedisPoolLazy.createDefaultInstance( redisParam);
		// 创建服务实例
		facelogClient = ClientFactory.builder()
				.setHostAndPort("127.0.0.1", DEFAULT_PORT)
				.setDecorator(RefreshTokenDecorator.makeDecoratorFunction(TokenHelperTestImpl.INSTANCE))
				.build(IFaceLogThriftClient.class, IFaceLogClient.class);
		// 申请令牌
		rootToken = facelogClient.applyRootToken("guyadong", false);
		facelogClient.setTokenHelper(TokenHelperTestImpl.INSTANCE)
			.startServiceHeartbeatListener(rootToken, true);
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		facelogClient.releaseRootToken(rootToken);
	}
	/**
	 * 设备端发送心跳包测试
	 * @throws InterruptedException 
	 */
	@Test
	public void test1SendHB() throws InterruptedException {
	
		System.out.println("Heartbeat thead start");
		try {
			facelogClient.makeHeartbeat(12345, rootToken, null)
				/** 间隔2秒发送心跳，重新启动定时任务 */
				.setInterval(2, TimeUnit.SECONDS)
				.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 管理端心跳包监控测试
	 * @throws InterruptedException 
	 */
	@Test
	public void test2HBMonitor() throws InterruptedException{
		DeviceHeartbeatListener hbAdapter = new DeviceHeartbeatListener(){
			@Override
			public void onSubscribe(DeviceHeartdbeatPackage t) throws SmqUnsubscribeException {
				// 显示收到的心跳包
				logger.info(t.toString());
			}};
		
		facelogClient.makeHeartbeatMonitor(hbAdapter, rootToken,null).start();
		/** 40秒后结束测试 */
		Thread.sleep(300*1000);
	}
	public static class TokenHelperTestImpl extends TokenHelper {
		final static TokenHelperTestImpl INSTANCE = new TokenHelperTestImpl(); 
		public TokenHelperTestImpl() {
		}

		@Override
		public String passwordOf(int id) {
			return "guyadong";
		}

		@Override
		public boolean isHashedPwd() {
			return false;
		}

	}
}
