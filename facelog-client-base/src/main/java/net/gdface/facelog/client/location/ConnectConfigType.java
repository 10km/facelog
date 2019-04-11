package net.gdface.facelog.client.location;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterators;

import net.gdface.thrift.ClientFactory;
import static net.gdface.facelog.CommonConstant.*;

/**
 * facelog连接配置参数
 * @author guyadong
 *
 */
public enum ConnectConfigType implements ConnectConfigProvider {
	/** 自定义配置 */CUSTOM(new DefaultCustomConnectConfigProvider())
	/** 局域网配置 */,LAN(new DefaultLocalConnectConfigProvider())
	/** 公有云配置 */,CLOUD(new DefaultCloudConnectConfigProvider())
	/** 本机配置(仅用于测试) */,LOCALHOST(new DefaultLocalhostConnectConfigProvider());
	/**
	 * 接口实例的默认实现
	 */
	private final ConnectConfigProvider defImpl;
	/**
	 * 接口实例
	 */
	private volatile ConnectConfigProvider instance;
	/**
	 * 当前配置是否可连接
	 */
	private boolean connectable;
	private ConnectConfigType(){
		this(null);
	}
	private ConnectConfigType(ConnectConfigProvider defImpl) {
		this.defImpl = defImpl;
		findConnectConfigProvider();
	}
	/**
	 * SPI(Server Load Interface)方式加载当前类型的{@link ConnectConfigProvider}实例,
	 * 没找到则用默认{@link #defImpl}实例代替
	 * @return
	 */
	private ConnectConfigProvider findConnectConfigProvider(){
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
					// 通过host是否为空判断接口实例提供的数据是否有效
					instance =  find.isPresent() && !Strings.isNullOrEmpty(find.get().getHost()) 
							? find.get() : this.defImpl;
				}
			}
		}
		return instance;
	}
	private static volatile ConnectConfigType activeConnectType = null;

	/**
	 * 测试redis连接
	 * @return 连接成功返回{@code true},否则返回{@code false}
	 */
	public synchronized boolean testConnect(){
		connectable = false;
		if(instance != null){
//			System.out.printf("try to connect %s...\n", this);
			try{
				connectable = ClientFactory.builder()
						.setHostAndPort(instance.getHost(),instance.getPort())
						.testConnect();
			}catch (Exception e) {
			}
			if(connectable){
				System.out.println(toString() + " connect OK\n");
			}
//			System.out.printf("%s connect %s\n",this.toString(),connectable?"OK":"FAIL");
		}
		return connectable;
	}

	/**
	 * 按照如下优先顺序测试配置的facelog服务连接，返回第一个能建立有效连接的配置，否则抛出异常<br>
	 * <li>{@link ConnectConfigType#CUSTOM}</li>
	 * <li>{@link ConnectConfigType#LAN}</li>
	 * <li>{@link ConnectConfigType#CLOUD}</li>
	 * <li>{@link ConnectConfigType#LOCALHOST}</li>
	 * @return
	 * @throws FaceLogConnectException 没有找到有效redis连接
	 */
	public static ConnectConfigType lookupRedisConnect() throws FaceLogConnectException{
		// double check
		if(activeConnectType == null){
			synchronized (ConnectConfigType.class) {
				if(activeConnectType == null){
					// 并发执行连接测试，以减少等待时间
					Thread[] threads = new Thread[values().length];
					int index = 0;
					for (final ConnectConfigType type : values()) {
						threads[index] = new Thread(){

							@Override
							public void run() {
								type.testConnect();
							}

						};
						threads[index].start();
						index++;
					}
					// 等待所有子线程结束
					// 以枚举变量定义的顺序为优先级查找第一个connectable为true的对象返回
					// 都为false则抛出异常
					try {
						for(int i =0;i<threads.length;++i){
							threads[i].join();
							ConnectConfigType type = values()[i];
							if(type.connectable){
								return type;
							}
						}
					} catch (InterruptedException e) {
					}
					throw new FaceLogConnectException("NOT FOUND VALID Facelog SERVER");
				}
			}
		}
		return activeConnectType;
	}

	/**
	 * 与{@link #lookupRedisConnect()}功能相似,不同的时当没有找到有效redis连接时,不抛出异常,返回{@code null}
	 * @param logger
	 * @return 返回第一个能建立有效连接的配置,否则返回{@code null}
	 */
	public static ConnectConfigType lookupRedisConnectUnchecked() {
		try {
			return lookupRedisConnect();
		} catch (FaceLogConnectException e) {
			return null;
		}
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
		checkInstance().setHost(host);
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
	@Override
	public	String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(name());
		if(instance==null){
			buffer.append("(UNDEFINED)");
		}else{
			buffer.append("(")
					.append(getHost()).append(":")
					.append(getPort())
					.append(")");
		}
		return buffer.toString();
	}
}