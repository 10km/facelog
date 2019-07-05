package net.gdface.facelog.client.location;

import net.gdface.facelog.CommonConstant;

/**
 * {@link ConnectConfigProvider}公有云配置
 * @author guyadong
 *
 */
public class DefaultCloudConnectConfigProvider implements ConnectConfigProvider,CommonConstant {
	public static String DEFAULT_CLOUD_HOST = "facelog.facelib.net";
	@Override
	public String getHost() {
		return DEFAULT_CLOUD_HOST;
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
