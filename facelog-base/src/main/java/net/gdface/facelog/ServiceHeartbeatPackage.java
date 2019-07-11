package net.gdface.facelog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Strings;

/**
 * 服务心跳包
 * @author guyadong
 *
 */
public class  ServiceHeartbeatPackage implements CommonConstant{
	/** 服务ID(服务每次启动会不一样，消息接收端根据此判断服务是否重启) */
	private int id;
	/** (FRAMED)服务端口 */
	private Integer port;
	/** (XHR)服务端口,如果XHR服务未启动则为{@code null} */
	private Integer xhrPort;
	/** 主机名 */
	private String host;
	/** 服务启动时间戳 ISO8601 格式 */
	private String timestamp = new SimpleDateFormat(ISO8601_FORMATTER_STR).format(new Date());
	public ServiceHeartbeatPackage(int id, Integer port, Integer xhrPort, String host) {
		super();
		this.id = id;
		this.port = port;
		this.xhrPort = xhrPort;
		this.host = host;
	}
	public ServiceHeartbeatPackage() {
		super();
	}
	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id 要设置的 id
	 * @return 
	 */
	public ServiceHeartbeatPackage setId(int id) {
		this.id = id;
		return this;
	}
	/**
	 * @return port
	 */
	public Integer getPort() {
		return port;
	}
	/**
	 * @param port 要设置的 port
	 * @return 
	 */
	public ServiceHeartbeatPackage setPort(Integer port) {
		this.port = port;
		return this;
	}
	/**
	 * @return xhrPort
	 */
	public Integer getXhrPort() {
		return xhrPort;
	}
	/**
	 * @param xhrPort 要设置的 xhrPort
	 * @return 
	 */
	public ServiceHeartbeatPackage setXhrPort(Integer xhrPort) {
		this.xhrPort = xhrPort;
		return this;
	}

	/**
	 * @return host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host 要设置的 host
	 * @return 
	 */
	public ServiceHeartbeatPackage setHost(String host) {
		this.host = host;
		return this;
	}

	/**
	 * @return timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp 要设置的 timestamp
	 * @return 
	 */
	public ServiceHeartbeatPackage setTimestamp(String timestamp) {
		this.timestamp = timestamp;
		return this;
	}
	public long readTimestamp(){
		try {
			return Strings.isNullOrEmpty(timestamp) 
					? 0L 
					: new SimpleDateFormat(ISO8601_FORMATTER_STR).parse(timestamp).getTime();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceHeartbeatPackage [id=");
		builder.append(id);
		builder.append(", ");
		if (port != null) {
			builder.append("port=");
			builder.append(port);
			builder.append(", ");
		}
		if (xhrPort != null) {
			builder.append("xhrPort=");
			builder.append(xhrPort);
			builder.append(", ");
		}
		if (host != null) {
			builder.append("host=");
			builder.append(host);
			builder.append(", ");
		}
		if (timestamp != null) {
			builder.append("timestamp=");
			builder.append(timestamp);
		}
		builder.append("]");
		return builder.toString();
	}

}