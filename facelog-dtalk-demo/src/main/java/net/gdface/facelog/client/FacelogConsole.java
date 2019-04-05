package net.gdface.facelog.client;

import com.google.common.base.Strings;

import gu.dtalk.client.BaseConsole;
import gu.dtalk.client.SampleConsole;
import gu.dtalk.exception.DtalkException;
import gu.dtalk.redis.RedisConfigType;
import gu.simplemq.redis.JedisPoolLazy;
import net.gdface.facelog.client.location.ConnectConfigType;
import net.gdface.facelog.client.location.DefaultCustomConnectConfigProvider;
import net.gdface.facelog.thrift.IFaceLogThriftClient;
import net.gdface.thrift.ClientFactory;

import static net.gdface.facelog.client.ConsoleConfig.CONSOLE_CONFIG;
import static com.google.common.base.Preconditions.*;

public class FacelogConsole extends BaseConsole {

	public FacelogConsole(String devmac, RedisConfigType config) {
		super(devmac, config);
	}

	@Override
	protected boolean authorize() {
		return false;
	}
	public static void main(String []args){
		CONSOLE_CONFIG.parseCommandLine(args);
		boolean custom = 
				   DefaultCustomConnectConfigProvider.initHost(CONSOLE_CONFIG.getServiceHost())
				|| DefaultCustomConnectConfigProvider.initPort(CONSOLE_CONFIG.getServicePort());
		ConnectConfigType type;
		try {
			if(custom){
				type = ConnectConfigType.CUSTOM;
				checkArgument(type.testConnect(),"NOT CONNECT TO facelog service %s:%s",
						type.getHost(),type.getPort());
			}else{
				type = ConnectConfigType.lookupRedisConnect();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ;
		}
		IFaceLogClient facelogClient = ClientFactory.builder()
				.setHostAndPort(type.getHost(), type.getPort())
				.setDecorator(RefreshTokenDecorator.makeDecoratorFunction( new TokenHelperTestImpl()))
				.build(IFaceLogThriftClient.class, IFaceLogClient.class);		
		System.out.println("Text terminal for Facelog Device is starting(facelog设备交互字符终端启动)");
		String devmac = null;
		// 如果命令行提供了设备mac地址，则尝试解析该参数
		if(args.length > 1){
			devmac = parseMac(args[0]);
			if(devmac.isEmpty()){
				System.out.printf("ERROR:Invalid mac adress %s\n",devmac);
				return ;
			}
		}
		// 否则提示输入命令行参数
		if(Strings.isNullOrEmpty(devmac)){
			devmac = inputMac();
		}
		RedisConfigType config;
		try {
			config = RedisConfigType.lookupRedisConnect();
		} catch (DtalkException e) {
			System.out.println(e.getMessage());
			return;
		}
		logger.info("use config={}",config.toString());
		// 创建redis连接实例
		JedisPoolLazy.createDefaultInstance( config.readRedisParam() );

		SampleConsole client = new SampleConsole(devmac, config);
		client.start();

	}
	public static class TokenHelperTestImpl extends TokenHelper {

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
