package net.gdface.facelog.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gu.dtalk.engine.demo.DemoListener;
import net.gdface.facelog.MQParam;
import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.Token;
import net.gdface.facelog.client.dtalk.DtalkEngineForFacelog;
import net.gdface.facelog.client.dtalk.FacelogMenu;
import net.gdface.facelog.client.dtalk.FacelogRedisConfigProvider;
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
		logger.info("device token = {}",deviceToken);	
		return this;
	}
	/**
	 * 启动连接
	 * @return 
	 * @throws ServiceSecurityException 
	 */
	private DtalkDemo start() {
		// 获取redis连接参数
		Map<MQParam, String> redisParam = this.facelogClient.getRedisParameters(deviceToken);
		FacelogRedisConfigProvider.setRedisLocation(URI.create(redisParam.get(MQParam.REDIS_URI)));
		FacelogMenu root = new FacelogMenu(config).init().register(DemoListener.INSTANCE);
		engine = facelogClient.initDtalkEngine(deviceToken, root);
		engine.start();
		return this;
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
//			JedisPoolLazy.closeAll();
		}catch (Exception e) {
			//System.out.println(e.getMessage());
			e.printStackTrace();
			return ;
		}
	}

}
