package net.gdface.facelog.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.Token;
import net.gdface.facelog.client.dtalk.DtalkEngineForFacelog;
import net.gdface.facelog.client.dtalk.FacelogMenu;
import net.gdface.facelog.client.location.ConnectConfigProvider;
import net.gdface.facelog.client.location.ConnectConfigType;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.thrift.IFaceLogThriftClient;
import net.gdface.thrift.ClientFactory;
import net.gdface.utils.NetworkUtil;

import static gu.dtalk.engine.SampleConnector.*;
import static com.google.common.base.Preconditions.*;

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

		device = DeviceBean.builder().mac(NetworkUtil.formatMac(devMac, null)).serialNo("5432122").build();
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
	 * @return 
	 * @throws ServiceSecurityException 
	 */
	private void start() {
		FacelogMenu root = FacelogMenu.makeActiveInstance(config).init().register(DemoListener.INSTANCE);
		engine = facelogClient.initDtalkEngine(deviceToken, root);
		engine.start();
	}
	/**
	 * 等待程序结束
	 */
	private static void waitquit(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		try{
			while(!"quit".equalsIgnoreCase(reader.readLine())){
				return;
			}
		} catch (IOException e) {

		}finally {

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
		System.out.println("Dtalk simulator for Facelog Device is starting(facelog设备dtalk模拟器启动)");

		try{
			ConnectConfigType type = ConnectConfigType.lookupRedisConnect();
			IFaceLogClient facelogClient = ClientFactory.builder()
					.setHostAndPort(type.getHost(), type.getPort())
					.setDecorator(RefreshTokenDecorator.makeDecoratorFunction( DeviceTokenHelper.HELPER))
					.build(IFaceLogThriftClient.class, IFaceLogClient.class);
			new DtalkDemo(facelogClient, type)
					.registerHelper(DeviceTokenHelper.HELPER)
					.initDevice()
					.start();
			System.out.println("PRESS 'CTRL-C' to exit");
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
