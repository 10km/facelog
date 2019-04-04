package net.gdface.facelog.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gu.dtalk.engine.ItemEngine;
import gu.dtalk.engine.SampleConnector;
import gu.dtalk.engine.demo.DemoListener;
import gu.dtalk.redis.RedisConfigType;
import gu.simplemq.Channel;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisSubscriber;
import net.gdface.facelog.MQParam;
import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.Token;
import net.gdface.facelog.client.dtalk.FacelogMenu;
import net.gdface.facelog.client.dtalk.FacelogRedisConfigProvider;
import net.gdface.facelog.client.location.ConnectConfigProvider;
import net.gdface.facelog.client.location.ConnectConfigType;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.thrift.IFaceLogThriftClient;
import net.gdface.thrift.ClientFactory;
import net.gdface.utils.NetworkUtil;

import static gu.dtalk.CommonUtils.*;
import static gu.dtalk.engine.SampleConnector.*;
import static com.google.common.base.Preconditions.*;

public class DtalkDemo {
	private static final Logger logger = LoggerFactory.getLogger(DtalkDemo.class);

	private final SampleConnector connAdapter;
	private final RedisSubscriber subscriber;
	private final byte[] devMac;

	private final IFaceLogClient facelogClient;

	private final Token deviceToken;

	private DeviceBean device;
	public DtalkDemo(IFaceLogClient facelogClient, ConnectConfigProvider config) throws ServiceSecurityException {
		this.facelogClient = checkNotNull(facelogClient,"facelogClient is null");
		devMac = DEVINFO_PROVIDER.getMac();
		device = DeviceBean.builder().mac(NetworkUtil.formatMac(devMac, null)).serialNo("5432122").build();
		logger.info(device.toString(true,false));
		// 注册设备 
		device = this.facelogClient.registerDevice(device);
		logger.info("registered device {}",device.toString(true, false));
		// 申请设备令牌
		deviceToken = this.facelogClient.online(device);
		logger.info("device token = {}",deviceToken);
		// 获取redis连接参数
		Map<MQParam, String> redisParam = this.facelogClient.getRedisParameters(deviceToken);
		FacelogRedisConfigProvider.setRedisLocation(URI.create(redisParam.get(MQParam.REDIS_URI)));
		JedisPoolLazy pool = JedisPoolLazy.getInstance(RedisConfigType.CUSTOM.readRedisParam(),false);
		subscriber = RedisFactory.getSubscriber(pool);
		FacelogMenu root = new FacelogMenu(config).init().register(DemoListener.INSTANCE);
		connAdapter = new SampleConnector(pool).setItemAdapter(new ItemEngine(pool).setRoot(root));
	}
	/**
	 * 启动连接
	 * @return 
	 * @throws ServiceSecurityException 
	 */
	private DtalkDemo start() {
		logger.info("Facelog Device Demo starting(设备模拟器启动)");
		// 创建redis连接实例
		JedisPoolLazy.createDefaultInstance( RedisConfigType.CUSTOM.readRedisParam() );
		logger.info("DEVICE MAC address(设备地址): {}",NetworkUtil.formatMac(devMac, ":"));
		String connchname = getConnChannel(devMac);
		Channel<String> connch = new Channel<>(connchname, String.class)
				.setAdapter(connAdapter);
		subscriber.register(connch);
		logger.info("Connect channel registered(连接频道注册) : {} ",connchname);
		return this;
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
		try{
			ConnectConfigType type = ConnectConfigType.lookupRedisConnect();
			IFaceLogClient facelogClient = ClientFactory.builder()
					.setHostAndPort(type.getHost(), type.getPort())
					.setDecorator(RefreshTokenDecorator.makeDecoratorFunction( DeviceTokenHelper.HELPER))
					.build(IFaceLogThriftClient.class, IFaceLogClient.class);			
			new DtalkDemo(facelogClient, type).registerHelper(DeviceTokenHelper.HELPER).start();
			System.out.println("PRESS 'quit' OR 'CTRL-C' to exit");
			waitquit();
//			JedisPoolLazy.closeAll();
		}catch (Exception e) {
			//System.out.println(e.getMessage());
			e.printStackTrace();
			return ;
		}
	}

}
