package gu.simplemq.redis;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import com.google.common.base.Strings;

import redis.clients.jedis.Protocol;
import redis.clients.util.JedisURIHelper;

/**
 * @author guyadong
 *
 */
public class JedisUtils {
	private JedisUtils() {}

	public static URI createJedisURI(String host, int port,String password,int database){
		String userInfo = null;
		if (!Strings.isNullOrEmpty(password)) {
			userInfo = ":" + password;
		}
		try {
			return new URI("jedis", userInfo,host,port,"/" + database,	null,null);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}	
	}

	/**
	 * 根据连接参数创建一个{@link URI}对象
	 * @param parameters
	 * @return
	 */
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
		if ("127.0.0.1".equals(host)) {
			return Protocol.DEFAULT_HOST;
		}
		else if ("::1".equals(host)) {
			return Protocol.DEFAULT_HOST;
		}
		
		return host;
	}
}
