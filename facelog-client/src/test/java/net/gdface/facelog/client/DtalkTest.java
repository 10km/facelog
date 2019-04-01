package net.gdface.facelog.client;

import java.net.URI;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.gdface.facelog.CommonConstant;
import net.gdface.facelog.MQParam;
import net.gdface.facelog.Token;
import net.gdface.facelog.client.ClientTest.TokenHelperTestImpl;
import net.gdface.facelog.client.dtalk.FacelogRedisConfigProvider;
import net.gdface.facelog.thrift.IFaceLogThriftClient;
import net.gdface.thrift.ClientFactory;

public class DtalkTest implements CommonConstant{
    public static final Logger logger = LoggerFactory.getLogger(ClientTest.class);

	private static IFaceLogClient facelogClient;
	private static Token rootToken;

	private static Map<MQParam, String> redisParam;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// docker test
//		facelogClient = ClientFactory.builder().setHostAndPort("192.168.99.100", DEFAULT_PORT).build();
//		rootToken = facelogClient.applyRootToken("root", false);
		facelogClient = ClientFactory.builder()
				.setHostAndPort("127.0.0.1", DEFAULT_PORT)
				.setDecorator(RefreshTokenDecorator.makeDecoratorFunction(new TokenHelperTestImpl()))
				.build(IFaceLogThriftClient.class, IFaceLogClient.class);
		rootToken = facelogClient.applyRootToken("guyadong", false);
		redisParam = facelogClient.getRedisParameters(rootToken);
		FacelogRedisConfigProvider.setRedisLocation(URI.create(redisParam.get(MQParam.REDIS_URI)));
	}

	@Test
	public void test() {
		
	}

}
