package net.gdface.facelog;


import java.net.SocketAddress;

import com.facebook.nifty.core.ConnectionContext;
import com.facebook.nifty.core.RequestContext;
import com.facebook.nifty.core.RequestContexts;

/**
 * @author guyadong
 *
 */
public class ServiceUtil {
	private ServiceUtil() {}
	/**
	 * 返回客户端IP地址
	 * @return
	 */
	public static final SocketAddress niftyClientAddress(){
		RequestContext request = RequestContexts.getCurrentContext();
		if(null == request){
			return null;
		}
		ConnectionContext connect = request.getConnectionContext();
		return connect.getRemoteAddress();	
	}
	public static final String clientAddressAsString(){
		SocketAddress address = niftyClientAddress();
		return null == address ? "unknow" :address.toString();
	}
}
