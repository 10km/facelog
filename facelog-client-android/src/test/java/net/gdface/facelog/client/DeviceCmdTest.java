package net.gdface.facelog.client;

import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gu.dtalk.Ack;
import gu.dtalk.IAckAdapter;
import gu.dtalk.ICmdImmediateAdapter;
import gu.dtalk.client.CmdManager;
import gu.dtalk.exception.CmdExecutionException;
import net.gdface.facelog.Token;
import net.gdface.facelog.client.dtalk.DtalkEngineForFacelog;
import net.gdface.facelog.client.dtalk.FacelogMenu;
import net.gdface.facelog.client.location.ConnectConfigType;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.thrift.IFaceLogThriftClient;
import net.gdface.thrift.ClientFactory;
import net.gdface.utils.NetworkUtil;
import static net.gdface.facelog.client.dtalk.FacelogMenu.*;

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
	private static DeviceBean device;
	private static Token deviceToken;

	private static FacelogMenu root;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		// 创建服务实例
		facelogClient = ClientFactory.builder()
				.setHostAndPort("127.0.0.1", DEFAULT_PORT)
				.setDecorator(RefreshTokenDecorator.makeDecoratorFunction( DeviceTokenHelper.HELPER))
				.build(IFaceLogThriftClient.class, IFaceLogClient.class);
		// 申请root令牌
		rootToken = facelogClient.applyRootToken("guyadong", false);
		byte[] address = new byte[]{0x20,0x20,0x20,0x20,0x20,0x20};
		device = DeviceBean.builder().mac(NetworkUtil.formatMac(address, null)).serialNo("12322333").usedSdks("MTFSDKARM512").build();

		logger.info(device.toString(true,false));
		// 注册设备 
		device = facelogClient.registerDevice(device);
		logger.info("registered device {}",device.toString(true, false));
		// 申请设备令牌
		deviceToken = facelogClient.online(device);
		logger.info("device token = {}",deviceToken);
		DeviceTokenHelper.HELPER.device = device;
		DeviceTokenHelper.HELPER.deviceToken = deviceToken;
		root = FacelogMenu.makeActiveInstance(ConnectConfigType.LOCALHOST).init();
		// 设置RESET命令执行器
		root.findCmd(pathOfCmd(CMD_RESET)).setCmdAdapter(new ICmdImmediateAdapter() {
			
			@Override
			public Object apply(Map<String, Object> input) throws CmdExecutionException {
				logger.info("DEVICE client : do device reset...(执行设备RESET)");
				return null;
			}
		});
		// 设置ISENABLE命令执行器
		root.findCmd(pathOfCmd(CMD_GET_STATUS)).setCmdAdapter(new ICmdImmediateAdapter() {
			
			@Override
			public Object apply(Map<String, Object> input) throws CmdExecutionException {
				logger.info("DEVICE client : return enable status...(返回设备enable状态)");
				return false;
			}
		});
		// 初始化 JedisPoolLazy的默认实例
		facelogClient.initRedisDefaultInstance(deviceToken);
		// 启动dtalk引擎(点对点命令由此执行)
		DtalkEngineForFacelog engine = facelogClient.initDtalkEngine(deviceToken, root).start();

		// 启动设备命令分发器(广播命令由此执行)
		facelogClient.makeCmdDispatcher(deviceToken)
			.setItemAdapter(engine.getItemAdapter())
			/** 程序退出时自动注销设备命令频道 */
			.autoUnregisterChannel();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		facelogClient.unregisterDevice(deviceToken);
		facelogClient.releaseRootToken(rootToken);
	}

	/**
	 * 模拟设备端发送设备复位(异步执行)和isEnable命令(同步执行)
	 * @throws InterruptedException 
	 */
	@Test
	public void test2SendCmd() throws InterruptedException{
		// 创建命令发送管理实例 
		CmdManager cmdManager = facelogClient.makeCmdManager(rootToken);
		
		cmdManager.targetBuilder()
			// 设置命令序列号
			.setCmdSn(facelogClient.getCmdSnSupplier(rootToken))
			// 设置命令响应通道
			.setAckChannel(facelogClient.getAckChannelSupplier(rootToken))
			// 指定设备命令执行接收目标为一组设备(id)
			.setDeviceTarget(device.getId()).autoRemove(false);
		logger.info("异步接收命令响应:");
		cmdManager.runCmd(pathOfCmd(CMD_RESET),null, new IAckAdapter.BaseAdapter<Object>(){
				@Override
				protected void doOnSubscribe(Ack<Object> t) {
					logger.info("ADMIN client : 设备命令响应 {}",t);
				}
			}); // 异步执行设备复位命令
		 /** 5 秒后结束测试 */
		 Thread.sleep(5*1000);
		 logger.info("reset异步命令响应结束");
		 
		 // 复用CmdBuilder对象同步执行 isEnable 命令
		 cmdManager.targetBuilder().resetApply();
		 List<Ack<Object>> receivedAcks = cmdManager.runCmdSync(pathOfCmd(CMD_RESET),null,false);
		 logger.info("同步接收命令响应:");
		 for(Ack<Object> ack:receivedAcks){
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
		CmdManager cmdManager = facelogClient.makeCmdManager(rootToken);
		cmdManager.targetBuilder()
			// 设置命令序列号
			.setCmdSn(facelogClient.getCmdSnSupplier(rootToken))
			// 设置命令响应通道
			.setAckChannel(facelogClient.getAckChannelSupplier(rootToken))
			// 指定设备命令执行接收目标为一组设备(id)
			.setDeviceTarget(device.getId()) ;
		List<Ack<Object>> receivedAcks = cmdManager.runCmdSync(pathOfCmd(CMD_RESET),null, false);
		logger.info("同步接收命令响应:");
		for(Ack<Object> ack:receivedAcks){
			logger.info("ADMIN client : 设备命令响应 {}",ack);
		}
		logger.info("reset同步命令响应结束");
		logger.info("测试结束");
	}
	private static class DeviceTokenHelper extends TokenHelper{
		static final DeviceTokenHelper HELPER = new DeviceTokenHelper();
		DeviceBean device;
		Token deviceToken;
		@Override
		public DeviceBean deviceBean() {
			return device;
		}

		@Override
		public void saveFreshedToken(Token token) {
			deviceToken.assignFrom(token);
		}
		
	}
}
