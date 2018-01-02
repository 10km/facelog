package net.gdface.facelog.client;

import java.util.List;

import org.junit.Test;

import net.gdface.facelog.client.thrift.ServiceSecurityException;
import net.gdface.facelog.client.thrift.Token;

public class CmdManagerTest implements CommonConstant{

	@Test
	public void testSendResetSync() throws ServiceSecurityException {
		IFaceLogClient serviceClient = ClientFactory.builder().setHostAndPort("127.0.0.1", DEFAULT_PORT).build();
		// 申请 root 令牌
		Token token = serviceClient.applyRootToken("12343", false);
		// 创建命令发送管理实例 
		CmdManager cmdManager = serviceClient.makeCmdManager(token);
		
		try {
			List<Ack<Void>> ackList = cmdManager.targetBuilder()
				.setAckChannel(serviceClient.getAckChannelSupplier(token)) // 设置命令响应通道
				.setDeviceTarget(125,207,122) // 指定设备命令执行接收目标为一组设备(id)
				.build()
				.resetSync(null, false); // 执行设备复位命令
			// 输出命令执行结果
			for(Ack<Void> ack:ackList){
				logger.info("ack :{}",ack);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testSendResetAsync() throws ServiceSecurityException {
		IFaceLogClient serviceClient = ClientFactory.builder().setHostAndPort("127.0.0.1", DEFAULT_PORT).build();
		// 申请 root 令牌
		Token token = serviceClient.applyRootToken("12343", false);
		// 创建命令发送管理实例 
		CmdManager cmdManager = serviceClient.makeCmdManager(token);
		
		cmdManager.targetBuilder()
			.setAckChannel(serviceClient.getAckChannelSupplier(token)) // 设置命令响应通道
			.setDeviceTarget(125,207,122) // 指定设备命令执行接收目标为一组设备(id)
			.build()
			.reset(null, new IAckAdapter.BaseAdapter<Void>(){
				@Override
				protected void doOnSubscribe(Ack<Void> t) {
					logger.info("ack :{}",t);
				}
			}); // 执行设备复位命令
	}
	@Test
	public void testCommandAdapter(){
		IFaceLogClient serviceClient = ClientFactory.builder().setHostAndPort("127.0.0.1", DEFAULT_PORT).build();
		DeviceBean deviceBean =null;
		try {
			Token token = serviceClient.online(deviceBean);
			serviceClient.makeCmdDispatcher(token)
					.getCmdAdapterContainer()
					.register(Cmd.reset, new RestAdapter());	
		} catch (ServiceSecurityException e) {
			e.printStackTrace();
		}
	}
	public class RestAdapter extends CommandAdapter{
		@Override
		public void reset(Long schedule) throws DeviceCmdException {
			logger.info("device reset...");
		}		
	}
}
