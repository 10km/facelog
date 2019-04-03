package net.gdface.facelog.client.location;
import net.gdface.facelog.CommonConstant;
/**
 * {@link RedisConfigProvider}默认实现，只用于测试
 * @author guyadong
 *
 */
public class DefaultLocalhostConnectConfigProvider implements ConnectConfigProvider,CommonConstant {

	@Override
	public String getHost() {
		return "127.0.0.1";
	}

	@Override
	public void setHost(String host) {
		
	}

	@Override
	public int getPort() {
		return DEFAULT_PORT;
	}

	@Override
	public void setPort(int port) {
		
	}

	@Override
	public int getTimeout() {
		return 0;
	}

	@Override
	public void setTimeout(int timeout) {
	}
	
	@Override
	public final ConnectConfigType type() {
		return ConnectConfigType.LOCALHOST;
	}
}
