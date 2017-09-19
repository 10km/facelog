package net.gdface.facelog.simplemq;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import redis.clients.jedis.Protocol;
import redis.clients.util.JedisURIHelper;

public class JedisUtils {
	private JedisUtils() {}

	public static URI createJedisURI(String host, int port,String password,int database){
		String userInfo = null;
		if (null != password && !password.isEmpty()) {
			userInfo = ":" + password;
		}
		try {
			return new URI("jedis", userInfo,host,port,"/" + database,	null,null);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}	
	}

	public static URI getCanonicalURI(Map<JedisPoolLazy.PropName,Object> parameters){
		URI uri = (URI) parameters.get(JedisPoolLazy.PropName.uri);
		if (null == uri) {
			uri = createJedisURI((String) parameters.get(JedisPoolLazy.PropName.host),
					(Integer) parameters.get(JedisPoolLazy.PropName.port),
					(String) parameters.get(JedisPoolLazy.PropName.password),
					(Integer)parameters.get(JedisPoolLazy.PropName.database));
		}else{
			try {
				uri = new URI("jedis", 
						uri.getUserInfo(), 
						convertHost(uri.getHost()),
						uri.getPort(), 
						"/" + JedisURIHelper.getDBIndex(uri), 
						null, 
						null);
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}	
		}
		return uri;
	}
	
	private static String convertHost(String host) {
		if (host.equals("127.0.0.1")) return Protocol.DEFAULT_HOST;
		else if (host.equals("::1")) return Protocol.DEFAULT_HOST;
		
		return host;
	}
}
