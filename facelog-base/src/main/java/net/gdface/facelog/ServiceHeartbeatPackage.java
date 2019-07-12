package net.gdface.facelog;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;

import net.gdface.utils.FaceUtilits;

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
	/** 绑定的ip地址列表(','号分隔) */
	private String addresses;
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
	 * @return addresses
	 */
	public String getAddresses() {
		return addresses;
	}
	/**
	 * @param addresses 要设置的 addresses
	 * @return 
	 */
	public ServiceHeartbeatPackage setAddresses(String addresses) {
		this.addresses = addresses;
		return this;
	}
	public ServiceHeartbeatPackage writeAddresses(String...addresses){
		return setAddresses(Joiner.on(',').join(addresses));
	}
	public ServiceHeartbeatPackage writeAddresses(Iterable<String>addresses){
		if(addresses != null){
			setAddresses(Joiner.on(',').join(addresses));
		}
		return this;
	}
	public ServiceHeartbeatPackage writeAddresses(Collection<InetAddress>addresses){
		if(addresses != null){
			Collection<String> c = Collections2.transform(addresses, new Function<InetAddress, String>() {
				@Override
				public String apply(InetAddress input) {
					return input.getHostAddress();
				}
			});
			writeAddresses(c);
		}
		return this;
	}
	public List<String> readAddresses(){
		return Strings.isNullOrEmpty(addresses) ? Collections.<String>emptyList() : FaceUtilits.elementsOf(addresses);
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
		if (addresses != null) {
			builder.append("address=");
			builder.append(addresses);
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