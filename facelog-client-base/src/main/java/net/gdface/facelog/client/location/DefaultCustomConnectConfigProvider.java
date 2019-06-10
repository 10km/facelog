package net.gdface.facelog.client.location;

import com.google.common.base.Strings;

import net.gdface.facelog.CommonConstant;

/**
 * {@link ConnectConfigProvider}自定义配置的默认实现
 * @author guyadong
 *
 */
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

	/**
	 * 设置facelog主机名
	 * @param host
	 * @return {@code host}不为空且与原值不相同则返回{@code true},否则返回{@code false}
	 */
	public static boolean initHost(String host){
		if(!Strings.isNullOrEmpty(host) && !DefaultCustomConnectConfigProvider.host.equals(host)){
			DefaultCustomConnectConfigProvider.host = host;
			return true;
		}
		return false;
	}
	/**
	 * 设置facelog端口号
	 * @param port
	 * @return {@code port}>0且与原值不相同则返回{@code true},否则返回{@code false}
	 */
	public static boolean initPort(int port){
		if(port >0 && port !=DefaultCustomConnectConfigProvider.port){
			DefaultCustomConnectConfigProvider.port = port;
			return true;
		}
		return false;
	}
}
