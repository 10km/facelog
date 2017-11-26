package gu.simplemq.redis;

import static com.google.common.base.Preconditions.checkArgument;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import com.google.common.base.Function;
import com.google.common.base.Strings;

import redis.clients.jedis.Jedis;
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
	/**
	 * 执行 redis操作,
	 * @param fun redis操作
	 * @param poolLazy
	 * @return
	 */
	public static <T> T runOnRedis(Function<Jedis,T> fun,JedisPoolLazy poolLazy) {
		if(null == fun){
			return null;
		}
		Jedis jedis = poolLazy.apply();
		try{
			return fun.apply(jedis);
		}finally{
			poolLazy.free();
		}
	}
	public static <T> T runOnRedis(Function<Jedis,T> fun) {
		return runOnRedis(fun,JedisPoolLazy.getDefaultInstance());
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

	/**
	 * 执行 {@link Jedis#setnx(String, String)} 设置{@code key}的值{@code value},如果{@code key}已经存在就返回原值<br>
	 * {@code key,value}不可为{@code null}
	 * @param key
	 * @param value
	 * @return
	 */
	public static String setnx(final String key, final String value){
		checkArgument(!Strings.isNullOrEmpty(key) && !Strings.isNullOrEmpty(value),"key or value is null or empty");
		return runOnRedis(new Function<Jedis,String>(){
			@Override
			public String apply(Jedis input) {
				input.setnx(key, value);
				return input.get(key);
			}});
	}

	/**
	 * 将redis中{@code key}指定的变量步进加1并返回
	 * @param key 变量名,不可为{@code null}或空
	 * @return
	 * @see {@link Jedis#incr(String)}
	 */
	public static long incr(final String key){
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		return runOnRedis(new Function<Jedis,Long>(){
			@Override
			public Long apply(Jedis input) {
				return input.incr(key);
			}});
	}
}
