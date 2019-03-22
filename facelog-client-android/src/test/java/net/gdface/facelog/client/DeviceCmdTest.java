package net.gdface.facelog.client;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.JedisPoolLazy.PropName;
import net.gdface.facelog.IFaceLog;
import net.gdface.facelog.Token;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.thrift.ClientFactory;
import net.gdface.thrift.exception.ServiceRuntimeException;
import net.gdface.utils.DefaultExecutorProvider;
import net.gdface.utils.NetworkUtil;
import redis.clients.jedis.Protocol;

/**
 * 设备命令发送接收测试
 * @author guyadong
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceCmdTest implements ChannelConstant{
    public static final Logger logger = LoggerFactory.getLogger(DeviceCmdTest.class);

	private static IFaceLogClient facelogClient;
	private static Token rootToken;
	/** redis 连接参数 */
	private static Map<PropName, Object> redisParam = 
			ImmutableMap.<PropName, Object>of(
					/** redis 主机名 */PropName.host,Protocol.DEFAULT_HOST,
					/** redis 端口号 */PropName.port,Protocol.DEFAULT_PORT,
					/** redis 连接密码 */PropName.password, "hello"
					);
	private static DeviceBean device;
	private static Token deviceToken;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// 根据连接参数创建默认实例 
		JedisPoolLazy.createDefaultInstance( redisParam);
		// 创建服务实例
		facelogClient = ClientFactory.builder().setHostAndPort("127.0.0.1", DEFAULT_PORT).build(IFaceLog.class,IFaceLogClient.class);
		// 申请root令牌
		rootToken = facelogClient.applyRootToken("guyadong", false);
		byte[] address = new byte[]{0x20,0x20,0x20,0x20,0x20,0x20};
		device = DeviceBean.builder().mac(NetworkUtil.formatMac(address, null)).serialNo("12322333").build();
		logger.info(device.toString(true,false));
		// 注册设备 
		device = facelogClient.registerDevice(device);
		logger.info("registered device {}",device.toString(true, false));
		// 申请设备令牌
		deviceToken = facelogClient.online(device);
		logger.info("device token = {}",deviceToken);
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		facelogClient.unregisterDevice(device.getId(), deviceToken);
		facelogClient.releaseRootToken(rootToken);
	}
	/**
	 * reset 命令执行器
	 * @author guyadong
	 *
	 */
	public class RestAdapter extends CommandAdapter{
		@Override
		public void reset(Long schedule)  {
			logger.info("DEVICE client : do device reset...(执行设备RESET)");
		}		
	}
	/**
	 * isEnable 命令执行器
	 * @author guyadong
	 *
	 */
	public class IsEnableAdapter extends CommandAdapter{

		@Override
		public Boolean isEnable() {
			logger.info("DEVICE client : return enable status...(返回设备enable状态)");
			return false;
		}		
	}
	/**
	 * 模拟设备端响应设备命令
	 * @throws InterruptedException 
	 */
	@Test
	public void test1CommandAdapter(){		
		try {
			facelogClient.makeCmdDispatcher(deviceToken)
				/** 注册命令执行器 */
				.registerAdapter(Cmd.reset, new RestAdapter())
				.registerAdapter(Cmd.isEnable, new IsEnableAdapter())
				/** 程序退出时自动注销设备命令频道 */
				.autoUnregisterChannel();	
		} catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
			assertTrue(e.getMessage(),false);
		}
	}
	/**
	 * 模拟设备端发送设备复位(异步执行)和isEnable命令(同步执行)
	 * @throws InterruptedException 
	 */
	@Test
	public void test2SendCmd() throws InterruptedException{
		// 创建命令发送管理实例 
		CmdManager cmdManager = facelogClient.makeCmdManager(rootToken)
				.setExecutor(DefaultExecutorProvider.getGlobalExceutor())
				.setTimerExecutor(DefaultExecutorProvider.getTimerExecutor());
		
		cmdManager.targetBuilder()
			// 设置命令序列号
			.setCmdSn(facelogClient.getCmdSnSupplier(rootToken))
			// 设置命令响应通道
			.setAckChannel(facelogClient.getAckChannelSupplier(rootToken))
			// 指定设备命令执行接收目标为一组设备(id)
			.setDeviceTarget(device.getId()).autoRemove(false);
		logger.info("异步接收命令响应:");
		cmdManager.reset(null, new IAckAdapter.BaseAdapter<Void>(){
				@Override
				protected void doOnSubscribe(Ack<Void> t) {
					logger.info("ADMIN client : 设备命令响应 {}",t);
				}
			}); // 异步执行设备复位命令
		 /** 5 秒后结束测试 */
		 Thread.sleep(5*1000);
		 logger.info("reset异步命令响应结束");
		 
		 // 复用CmdBuilder对象同步执行 isEnable 命令
		 cmdManager.targetBuilder().resetApply();
		 List<Ack<Boolean>> receivedAcks = cmdManager.isEnableSync(false);
		 logger.info("同步接收命令响应:");
		 for(Ack<Boolean> ack:receivedAcks){
			 logger.info("ADMIN client : 设备命令响应 {}",ack);
		 }
		 logger.info("isEnable同步命令响应结束");
	}
	/**
	 * 模拟设备端发送设备复位(同步执行)
	 * @throws InterruptedException
	 */
	@Test
	public void test3SendCmdSync() throws InterruptedException{
		// 创建命令发送管理实例 
		CmdManager cmdManager = facelogClient.makeCmdManager(rootToken)
				.setExecutor(DefaultExecutorProvider.getGlobalExceutor())
				.setTimerExecutor(DefaultExecutorProvider.getTimerExecutor());
		cmdManager.targetBuilder()
			// 设置命令序列号
			.setCmdSn(facelogClient.getCmdSnSupplier(rootToken))
			// 设置命令响应通道
			.setAckChannel(facelogClient.getAckChannelSupplier(rootToken))
			// 指定设备命令执行接收目标为一组设备(id)
			.setDeviceTarget(device.getId()) ;
		List<Ack<Void>> receivedAcks = cmdManager.resetSync(null, false);
		logger.info("同步接收命令响应:");
		for(Ack<Void> ack:receivedAcks){
			logger.info("ADMIN client : 设备命令响应 {}",ack);
		}
		logger.info("reset同步命令响应结束");
		logger.info("测试结束");
	}
}
