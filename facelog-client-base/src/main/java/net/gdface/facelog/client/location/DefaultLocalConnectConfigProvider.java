package net.gdface.facelog.client.location;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

import net.gdface.facelog.CommonConstant;
import net.gdface.facelog.ServiceHeartbeatPackage;
import net.gdface.facelog.hb.LanServiceHeartbeatListener;
import net.gdface.thrift.ClientFactory;
import net.gdface.utils.JcifsUtil;

import static net.gdface.facelog.hb.LanServiceHeartbeatListener.*;
import static com.google.common.base.Preconditions.*;
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
	 * 返回局域网facelog主机名
	 * @return landtalkhost
	 */
	public static String getLanfaceloghost() {
		return landfaceloghost;
	}

	/**
	 * 初始化局域网facelog主机名，默认值为'landfaceloghost'
	 * @param lanfaceloghost 要设置的 landfaceloghost,不可为{@code null}或空
	 */
	public static void initLanfaceloghost(String lanfaceloghost) {
		DefaultLocalConnectConfigProvider.landfaceloghost = checkNotNull(Strings.emptyToNull(lanfaceloghost),"lanfaceloghost is null or empty");
	}

	private ServiceHeartbeatPackage lanServer = null;
	
	private static ServiceHeartbeatPackage findHost(List<ServiceHeartbeatPackage> servers,String host){
		Optional<ServiceHeartbeatPackage> find = Iterables.tryFind(servers, new Filter(host));
		return find.isPresent() ? find.get() : null;
	}
	private static String addressOf(String host,int port){
		String address = JcifsUtil.getAddressIfPossible(host);
		if(null != address && ClientFactory.testConnect(address, port, 0)){
			return address;
		}
		return null;
	}
	@Override
	public synchronized String getHost() {
		if(lanServer != null){
			String address = firstReachableAddress(lanServer);
			if(address != null){
				return address;
			}
			lanServer = null;
		}
		String address;
		if(null != (address = addressOf(landfaceloghost,getPort()))){
			return address;
		}
		// 如果外部设置了不同的主机名,则不再尝试解析landtalkhost
		if(DEFAULT_LANDFACELOGHOST.equals(landfaceloghost)){
			// 如果LANDTALKHOST有facelog连接则用此IP地址
			if(null != (address = addressOf(DEFAULT_LANDTALKHOST,getPort()))){
				return address;
			}
		}

		List<ServiceHeartbeatPackage> servers = LanServiceHeartbeatListener.INSTANCE.lanServers();
		if(!servers.isEmpty()){
			if(null != (lanServer = findHost(servers,landfaceloghost))){
				// DO NOTHING
			}else // 如果外部设置了不同的主机名,则不再尝试解析landtalkhost
				if(DEFAULT_LANDFACELOGHOST.equals(landfaceloghost) && null != (lanServer = findHost(servers,DEFAULT_LANDTALKHOST))){
					// 如果LANDTALKHOST有facelog连接则用此IP地址
					//	DO NOTHING
				}else{
					Collections.reverse(servers);
					lanServer = servers.get(0);
				}
			address = firstReachableAddress(lanServer);
			if(address != null){
				return address;
			}
		}
		return landfaceloghost;
	}

	@Override
	public void setHost(String host) {
	}

	@Override
	public int getPort() {
		return lanServer == null ? DEFAULT_PORT : lanServer.getPort();
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

	private static class Filter implements Predicate<ServiceHeartbeatPackage>{

		private final String host; 
		public Filter(String host) {
			this.host = checkNotNull(Strings.emptyToNull(host),"host is null or empty");
		}
		@Override
		public boolean apply(ServiceHeartbeatPackage input) {
			return host.equals(input.getHost());
		}
		
	}

	/**
	 * @return lanServer
	 */
	public ServiceHeartbeatPackage getLanServer() {
		return lanServer;
	}

	/**
	 * @param lanServer 要设置的 lanServer
	 * @return 
	 */
	public DefaultLocalConnectConfigProvider setLanServer(ServiceHeartbeatPackage lanServer) {
		this.lanServer = lanServer;
		return this;
	}

}
