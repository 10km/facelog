package net.gdface.facelog.client.location;

import net.gdface.facelog.CommonConstant;

/**
 * 公有云配置
 * @author guyadong
 *
 */
public class CloudConnectConfigProvider implements ConnectConfigProvider,CommonConstant {

	@Override
	public String getHost() {
		return "facelog.gdface.net";
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
	public final ConnectConfigType type(){
		return ConnectConfigType.CLOUD;
	}
}
