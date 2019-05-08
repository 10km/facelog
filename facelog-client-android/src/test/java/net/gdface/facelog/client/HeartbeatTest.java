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

import gu.simplemq.Channel;
import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.JedisPoolLazy.PropName;
import gu.simplemq.redis.RedisFactory;
import net.gdface.facelog.DeviceHeadbeatPackage;
import net.gdface.facelog.MQParam;
import net.gdface.facelog.Token;
import net.gdface.facelog.device.Heartbeat;
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
	/** 设备心跳监控频道 */
	private static String monitorChannelName;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// 根据连接参数创建默认实例 
		JedisPoolLazy.createDefaultInstance( redisParam);
		// 创建服务实例
		facelogClient = ClientFactory.builder().setHostAndPort("127.0.0.1", DEFAULT_PORT).build(IFaceLogThriftClient.class, IFaceLogClient.class);
		// 申请令牌
		rootToken = facelogClient.applyRootToken("guyadong", false);
		// 从facelog service 获取心跳监控频道名 
		monitorChannelName = facelogClient.getRedisParameters(rootToken).get(MQParam.HB_MONITOR_CHANNEL);
		logger.info("monitorChannelName = {}",monitorChannelName);
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		facelogClient.releaseRootToken(rootToken);
	}
	/**
	 * 设备端发送心跳包测试
	 */
	@Test
	public void test1SendHB() {
		Heartbeat hb = Heartbeat.makeHeartbeat(12345, JedisPoolLazy.getDefaultInstance())
				/** 将设备心跳包数据发送到指定的设备心跳监控通道名,否则监控端无法收到设备心跳包 */
				.setMonitorChannel(monitorChannelName);
		/** 以默认间隔启动定时任务 */
		hb.start();
		System.out.println("Heartbeat thead start");
		/** 间隔2秒发送心跳，重新启动定时任务 */
		hb.setInterval(2, TimeUnit.SECONDS).start();
	}
	/**
	 * 管理端心跳包监控测试
	 * @throws InterruptedException 
	 */
	@Test
	public void test2HBMonitor() throws InterruptedException{
		Channel<DeviceHeadbeatPackage> hbMonitorChannel = new Channel<DeviceHeadbeatPackage>(monitorChannelName,
				new IMessageAdapter<DeviceHeadbeatPackage>(){
			@Override
			public void onSubscribe(DeviceHeadbeatPackage t) throws SmqUnsubscribeException {
				// 显示收到的心跳包
				logger.info(t.toString());
			}}){};
		// 注册，订阅设备心跳监控频道消息
		RedisFactory.getSubscriber().register(hbMonitorChannel);
		
		/** 20秒后结束测试 */
		Thread.sleep(20*1000);
	}
}
