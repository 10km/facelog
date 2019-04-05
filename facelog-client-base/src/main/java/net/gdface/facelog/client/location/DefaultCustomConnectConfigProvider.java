package net.gdface.facelog.client.location;

import com.google.common.base.Strings;

import net.gdface.facelog.CommonConstant;

public class DefaultCustomConnectConfigProvider implements ConnectConfigProvider,CommonConstant {
	private static String host = "localhost";
	private static int port = DEFAULT_PORT;
	@Override
	public String getHost() {
		return host;
	}

	@Override
	public void setHost(String host) {

	}

	@Override
	public int getPort() {
		return port;
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
		return ConnectConfigType.CUSTOM;
	}

	public static boolean initHost(String host){
		if(!Strings.isNullOrEmpty(host)){
			DefaultCustomConnectConfigProvider.host = host;
			return true;
		}
		return false;
	}
	public static boolean initPort(int port){
		if(port >0){
			DefaultCustomConnectConfigProvider.port = port;
			return true;
		}
		return false;
	}
}
