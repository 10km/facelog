package net.gdface.facelog.client.location;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.slf4j.Logger;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import net.gdface.thrift.ClientFactory;
import static net.gdface.facelog.CommonConstant.*;

public enum ConnectConfigType implements ConnectConfigProvider {
	/** 本机配置(仅用于测试) */LOCALHOST(new LocalhostConnectConfigProvider())
	/** 局域网配置 */,LAN(new LocalConnectConfigProvider())
	/** 公有云配置 */,CLOUD(new CloudConnectConfigProvider())
	/** 自定义配置 */,CUSTOM;
	private final ConnectConfigProvider defImpl;
	private volatile ConnectConfigProvider instance;
	private ConnectConfigType(){
		this(null);
	}
	private ConnectConfigType(ConnectConfigProvider defImpl) {
		this.defImpl = defImpl;
		findRedisConfigProvider();
	}
	private ConnectConfigProvider findRedisConfigProvider(){
		// double checking
		if(instance == null){
			synchronized (this) {
				if(instance == null){
					ServiceLoader<ConnectConfigProvider> providers = ServiceLoader.load(ConnectConfigProvider.class);
					Iterator<ConnectConfigProvider> itor = providers.iterator();
					Optional<ConnectConfigProvider> find = Iterators.tryFind(itor, new Predicate<ConnectConfigProvider>() {

						@Override
						public boolean apply(ConnectConfigProvider input) {
							return input.type() == ConnectConfigType.this;
						}
					});
					instance =  find.isPresent() ? find.get() : this.defImpl;
				}
			}
		}
		return instance;
	}
	/**
	 * 测试redis连接
	 * @return 连接成功返回{@code true},否则返回{@code false}
	 */
	public boolean testConnect(){
		if(instance == null){
			return false;
		}
		System.out.printf("try to connect %s...", this);
		
		boolean connectable = ClientFactory.builder()
				.setHostAndPort(instance.getHost(),instance.getPort())
				.testConnect();
		System.out.println(connectable?"OK":"FAIL");
		return connectable;
	}

	/**
	 * 按照如下优先顺序测试配置的redis连接，返回第一个能建立有效连接的配置，否则抛出异常<br>
	 * <li>{@link ConnectConfigType#CUSTOM}</li>
	 * <li>{@link ConnectConfigType#LAN}</li>
	 * <li>{@link ConnectConfigType#CLOUD}</li>
	 * <li>{@link ConnectConfigType#LOCALHOST}</li>
	 * @return
	 * @throws FaceLogConnectException 没有找到有效redis连接
	 */
	public static ConnectConfigType lookupRedisConnect() throws FaceLogConnectException{
		if(ConnectConfigType.CUSTOM.testConnect()){
			return ConnectConfigType.CUSTOM;
		}else if(ConnectConfigType.LAN.testConnect()){
			return ConnectConfigType.LAN;
		}else if(ConnectConfigType.CLOUD.testConnect()){
			return ConnectConfigType.CLOUD;
		}else if(ConnectConfigType.LOCALHOST.testConnect()){
			return ConnectConfigType.LOCALHOST;
		}
		throw new FaceLogConnectException("NOT FOUND VALID REDIS SERVER");
	}

	/**
	 * 与{@link #lookupRedisConnect()}功能相似,不同的时当没有找到有效redis连接时,不抛出异常,返回{@code null}
	 * @param logger
	 * @return 返回第一个能建立有效连接的配置,否则返回{@code null}
	 */
	public static ConnectConfigType lookupRedisConnectUnchecked(Logger logger) {
		try {
			return lookupRedisConnect();
		} catch (FaceLogConnectException e) {
			return null;
		}
	}
	@Override
	public	String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(name()).append(" : ");
		if(instance==null){
			buffer.append("null");
		}else{
			buffer.append(getHost()).append(":")
			.append(getPort());
		}
		return buffer.toString();
	}
	private ConnectConfigProvider checkInstance(){
		if(null == instance){
			throw new UnsupportedOperationException(String.format("%s unavailable", name()));
		}
		return instance;
	}
	@Override
	public String getHost() {
		return checkInstance().getHost();
	}
	@Override
	public void setHost(String host) {
		
	}
	@Override
	public int getPort() {
		int port = checkInstance().getPort();
		return port > 0 ? port : DEFAULT_PORT;
	}
	@Override
	public void setPort(int port) {
		checkInstance().setPort(port);
	}
	@Override
	public int getTimeout() {
		int timeout = checkInstance().getTimeout();
		return timeout > 0 ? timeout : 0;
	}
	@Override
	public void setTimeout(int timeout) {
		checkInstance().setTimeout(timeout);
	}
	@Override
	public ConnectConfigType type() {
		return this;
	}
}