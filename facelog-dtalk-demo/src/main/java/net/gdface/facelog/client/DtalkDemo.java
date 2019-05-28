package net.gdface.facelog.client;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.Token;
import net.gdface.facelog.client.dtalk.DtalkEngineForFacelog;
import net.gdface.facelog.client.dtalk.FacelogMenu;
import net.gdface.facelog.client.location.ConnectConfigProvider;
import net.gdface.facelog.client.location.ConnectConfigType;
import net.gdface.facelog.client.location.DefaultCustomConnectConfigProvider;
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

	private Token deviceToken;

	private DeviceBean device;

	private DtalkEngineForFacelog engine;

	private ConnectConfigProvider config;
	public DtalkDemo(IFaceLogClient facelogClient, ConnectConfigProvider config) throws ServiceSecurityException {
		this.facelogClient = checkNotNull(facelogClient,"facelogClient is null");
		this.config = config;
	}
	private DtalkDemo initDevice() throws ServiceSecurityException{
		devMac = DEVINFO_PROVIDER.getMac();

		device = DeviceBean.builder()
				.mac(FaceUtilits.toHex(devMac))
				.serialNo("5432122")
				.usedSdks("MTFSDKARM512")
				.build();
		logger.info(device.toString(true,false));
		// 注册设备 
		device = this.facelogClient.registerDevice(device);
		logger.info("registered device {}",device.toString(true, false));
		// 申请设备令牌
		deviceToken = this.facelogClient.online(device);
		logger.info("设备令牌 = {}",deviceToken);	
		return this;
	}
	/**
	 * 启动连接
	 */
	private void start() {
		FacelogMenu root = FacelogMenu.makeActiveInstance(config).init().register(DemoListener.INSTANCE);
		engine = facelogClient.initDtalkEngine(deviceToken, root);
		engine.start();
		// 启动设备心跳
		facelogClient.setTokenHelper(DeviceTokenHelper.HELPER)
			.startServiceHeartbeatListener(deviceToken, true)
			.makeHeartbeat(device.getId(), deviceToken, null)
			/** 间隔2秒发送心跳，重新启动定时任务 */
			.setInterval(2, TimeUnit.SECONDS)
			.start();
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
	public static void main(String []args){
		CONSOLE_CONFIG.parseCommandLine(args);
		System.out.println("Dtalk simulator for Facelog Device is starting(facelog设备dtalk模拟器启动)");
		boolean useCustom = 
				   DefaultCustomConnectConfigProvider.initHost(CONSOLE_CONFIG.getServiceHost())
				|| DefaultCustomConnectConfigProvider.initPort(CONSOLE_CONFIG.getServicePort());
		ConnectConfigType type;
		try{
			if(useCustom){
				type = ConnectConfigType.CUSTOM;
				checkArgument(type.testConnect(),"NOT CONNECT TO facelog service %s:%s",
						type.getHost(),type.getPort());
			}else{
				type = ConnectConfigType.lookupRedisConnect();
			}
			IFaceLogClient facelogClient = ClientFactory.builder()
					.setHostAndPort(type.getHost(), type.getPort())
					.setDecorator(RefreshTokenDecorator.makeDecoratorFunction( DeviceTokenHelper.HELPER))
					.build(IFaceLogThriftClient.class, IFaceLogClient.class);
			new DtalkDemo(facelogClient, type)
					.registerHelper(DeviceTokenHelper.HELPER)
					.initDevice()
					.start();
			// 如果依赖库commons-pool的版本号为2.4.2则需要调用waitquit()
			// 如果版本号高于2.4.2低于2.6.1则不需要调用，参见https://blog.csdn.net/10km/article/details/89016301
			waitquit();
		}catch (Exception e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
			return ;
		}
	}

}
