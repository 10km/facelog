package net.gdface.facelog.client.location;

import java.net.UnknownHostException;

import net.gdface.facelog.CommonConstant;
import net.gdface.thrift.ClientFactory;
import net.gdface.utils.JcifsUtil;

/**
 * {@link ConnectConfigProvider}局域网配置
 * @author guyadong
 *
 */
public class DefaultLocalConnectConfigProvider implements ConnectConfigProvider,CommonConstant {
	public static final String DEFAULT_LANDFACELOGHOST = "landfaceloghost";
	public static final String DEFAULT_LANDTALKHOST = "landtalkhost";
	private static String landfaceloghost = DEFAULT_LANDFACELOGHOST;

	/**
	 * 返回局域网redis主机名
	 * @return landtalkhost
	 */
	public static String getLanfaceloghost() {
		return landfaceloghost;
	}

	/**
	 * 初始化局域网redis主机名，默认值为'landtalkhost'
	 * @param lanfaceloghost 要设置的 landtalkhost
	 */
	public static void initLanfaceloghost(String lanfaceloghost) {
		DefaultLocalConnectConfigProvider.landfaceloghost = lanfaceloghost;
	}
	@Override
	public String getHost() {
		try {
			return JcifsUtil.hostAddressOf(landfaceloghost);
		} catch (UnknownHostException e) {
			try {
				// 如果外部设置了不同的主机名,则不再尝试解析landtalkhost
				if(DEFAULT_LANDFACELOGHOST.equals(landfaceloghost)){
					// 如果LANDTALKHOST有facelog连接则用此IP地址
					String address = JcifsUtil.hostAddressOf(DEFAULT_LANDTALKHOST);
					if(ClientFactory.testConnect(address, getPort(), 0)){
						return address;
					}
				}
			} catch (UnknownHostException e1) {
				
			}
		}
		return landfaceloghost;
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
