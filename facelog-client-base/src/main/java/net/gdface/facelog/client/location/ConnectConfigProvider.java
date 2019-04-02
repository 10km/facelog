package net.gdface.facelog.client.location;

/**
 * facelog服务连接参数SPI(Service Provider Interface)读写接口
 * @author guyadong
 *
 */
public interface ConnectConfigProvider {
	/**
	 * @return 返回redis主机名,当{@link #getURI()}返回{@code null}时，不可为{@code null}
	 */
	String getHost();
	/**
	 * 保存facelog服务主机名
	 * @param host
	 */
	void setHost(String host);
	/**
	 * 
	 * @return 返回facelog服务端口号,<=0使用默认facelog服务端口6379
	 */
	int getPort();
	/**
	 * 保存facelog服务端口号
	 * @param port
	 */
	void setPort(int port);
	/**
	 * 
	 * @return 返回facelog服务超时连接参数,<=0使用默认值
	 */
	int getTimeout();
	/**
	 * 保存facelog服务超时连接参数
	 * @param timeout
	 */
	void setTimeout(int timeout);
	/**
	 * 返回当前配置的连接类型,不可为{@code null}
	 * @return
	 */
	ConnectConfigType type();
}
