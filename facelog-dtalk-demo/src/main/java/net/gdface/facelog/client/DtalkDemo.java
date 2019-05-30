package net.gdface.facelog.client;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import gu.dtalk.exception.DtalkException;
import gu.dtalk.redis.RedisConfigType;
import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.Token;
import net.gdface.facelog.client.dtalk.DtalkEngineForFacelog;
import net.gdface.facelog.client.dtalk.FacelogMenu;
import net.gdface.facelog.client.location.ConnectConfigProvider;
import net.gdface.facelog.client.location.ConnectConfigType;
import net.gdface.facelog.client.location.DefaultCustomConnectConfigProvider;
import net.gdface.facelog.client.location.FaceLogConnectException;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.thrift.IFaceLogThriftClient;
import net.gdface.thrift.ClientFactory;
import net.gdface.utils.FaceUtilits;

import static com.google.common.base.Preconditions.*;
import static net.gdface.facelog.client.DemoConfig.CONSOLE_CONFIG;
import static gu.dtalk.engine.DeviceUtils.DEVINFO_PROVIDER;

public class DtalkDemo {
	private static final Logger logger = LoggerFactory.getLogger(DtalkDemo.class);

	private byte[] devMac;

	private final IFaceLogClient facelogClient;

	private final RedisConfigType redisConfigType;

	private Token deviceToken;

	private DeviceBean device;

	private DtalkEngineForFacelog engine;

	private ConnectConfigProvider config;
	
	/**
	 * 联网状态(接入facelog)构造方法<br>
	 * @param facelogClient 
	 * @param config
	 */
	public DtalkDemo(IFaceLogClient facelogClient, ConnectConfigProvider config) {
		this.facelogClient = checkNotNull(facelogClient,"facelogClient is null");
		this.config = checkNotNull(config,"config is null");
		this.redisConfigType = null;
	}
	/**
	 * 脱网状态(非接入facelog)构造方法<br>
	 * 
	 * @param redisConfigType
	 */
	public DtalkDemo(RedisConfigType redisConfigType) {
		this.facelogClient=null;
		this.config = null;
		this.redisConfigType = checkNotNull(redisConfigType,"redisConfigType is null");
	}
	/**
	 * 初始化设备信息
	 * @return 当前对象
	 */
	private DtalkDemo initDevice(){
		devMac = DEVINFO_PROVIDER.getMac();

		device = DeviceBean.builder()
				.mac(FaceUtilits.toHex(devMac))
				.serialNo("5432122")
				.usedSdks("MTFSDKARM512")
				.build();
		logger.info(device.toString(true,false));
		return this;
	}
	/**
	 * 接入facelog状态下设备上线
	 * @return 当前对象
	 * @throws ServiceSecurityException
	 */
	private DtalkDemo onlineDeviceIfFacelogPresent() throws ServiceSecurityException{
		initDevice();
		if(facelogClient !=null){
			// 注册设备 
			device = this.facelogClient.registerDevice(device);
			logger.info("registered device {}",device.toString(true, false));
			// 申请设备令牌
			deviceToken = this.facelogClient.online(device);
			logger.info("设备令牌 = {}",deviceToken);
		}
		return this;
	}
	
	/**
	 * 启动连接
	 */
	private void start() {
		FacelogMenu root = FacelogMenu.makeActiveInstance(config).init().register(DemoListener.INSTANCE);
		if(facelogClient != null){
			logger.info("Dtalk Device connect into facelog(设备接入facelog)");
			engine = facelogClient.initDtalkEngine(deviceToken, root);
		}else{
			logger.info("Dtalk Device running without facelog(设备离线运行)");
			engine = new DtalkEngineForFacelog(root,redisConfigType);
		}
		engine.start();
		if(facelogClient !=null){
			// 启动设备心跳
			facelogClient.setTokenHelper(DeviceTokenHelper.HELPER)
			.startServiceHeartbeatListener(deviceToken, true)
			.makeHeartbeat(device.getId(), deviceToken, null)
			/** 间隔2秒发送心跳，重新启动定时任务 */
			.setInterval(2, TimeUnit.SECONDS)
			.start();
		}
	}
	/**
	 * 等待程序结束
	 */
	private static void waitquit(){
		System.out.println("PRESS 'CTRL-C' or 'quit' to exit");
		Scanner scaner = new Scanner(System.in);
		try{
			while (scaner.hasNextLine()) {
				String str = scaner.next();
				if("quit".equalsIgnoreCase(str)){
					return ;
				}
			}
		}finally{
			scaner.close();
		}
	}
	private DtalkDemo registerHelper(DeviceTokenHelper helper){
		helper.demo = this;
		return this;
	}
	private static class DeviceTokenHelper extends TokenHelper{
		static final DeviceTokenHelper HELPER = new DeviceTokenHelper();
		DtalkDemo demo;
		@Override
		public DeviceBean deviceBean() {
			return demo.device;
		}

		@Override
		public void saveFreshedToken(Token token) {
			demo.deviceToken.assignFrom(token);
		}
		
	}
	/**
	 * 模拟器启动流程:<br>
	 * 1.解析命令行参数，如果提供了facelog主机名和端口则使用指定的参数连接facelog<br>
	 * 2.如果命令行没有提供facelog主机名和端口号则启动facelog自动发现机制寻找facelog服务,如果找到facelog服务则以联网方式(接入facelog)运行<br>
	 * 3.如果没有找到faceog服务，则启动dtalk自动发现机制自动寻找redis服务，如果找到redis则以脱网方式(非接入facelog)运行<br>
	 * 4.如果没有找到redis，则报错退出，模拟器无法运行<br>
	 * @param args
	 */
	public static void main(String []args){
		CONSOLE_CONFIG.parseCommandLine(args);
		System.out.println("Dtalk simulator for Facelog Device is starting(facelog设备dtalk模拟器启动)");
		boolean useCustom = 
				   DefaultCustomConnectConfigProvider.initHost(CONSOLE_CONFIG.getServiceHost())
				|| DefaultCustomConnectConfigProvider.initPort(CONSOLE_CONFIG.getServicePort());
		ConnectConfigType type;
		try{
			if(useCustom){
				// 使用命令行提供的facelog主机/端口号连接facelog,连接失败则抛出FaceLogConnectException异常
				type = ConnectConfigType.CUSTOM;
				if(!type.testConnect()){
					throw new FaceLogConnectException(
							String.format("NOT CONNECT TO facelog service %s:%d", type.getHost(),type.getPort()));
				}
			}else{
				// 自动寻找 facelog,找不到会抛出FaceLogConnectException异常
				type = ConnectConfigType.lookupRedisConnect();
			}
			// 联网运行模式
			IFaceLogClient facelogClient = ClientFactory.builder()
					.setHostAndPort(type.getHost(), type.getPort())
					.setDecorator(RefreshTokenDecorator.makeDecoratorFunction( DeviceTokenHelper.HELPER))
					.build(IFaceLogThriftClient.class, IFaceLogClient.class);
			new DtalkDemo(facelogClient, type)
					.registerHelper(DeviceTokenHelper.HELPER)
					.onlineDeviceIfFacelogPresent()
					.start();
		}catch (FaceLogConnectException e) {
			try {
				logger.info(e.getMessage());
				// 脱网运行模式
				RedisConfigType redisConfigType = RedisConfigType.lookupRedisConnect();
				new DtalkDemo(redisConfigType)
					.initDevice()
					.start();
			} catch (DtalkException de) {
				logger.error(de.getMessage());
//				de.printStackTrace();
				return ;
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
//			e.printStackTrace();
			return ;
		}
		// 如果依赖库commons-pool的版本号为2.4.2则需要调用waitquit()
		// 如果版本号高于2.4.2低于2.6.1则不需要调用，参见https://blog.csdn.net/10km/article/details/89016301
		waitquit();
	}

}
