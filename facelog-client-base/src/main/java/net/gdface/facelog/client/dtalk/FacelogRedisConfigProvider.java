package net.gdface.facelog.client.dtalk;

import java.net.URI;

import gu.dtalk.redis.RedisConfigProvider;
import gu.dtalk.redis.RedisConfigType;
import redis.clients.util.JedisURIHelper;

import static com.google.common.base.Preconditions.*;

public class FacelogRedisConfigProvider implements RedisConfigProvider {

	private static URI redisLocation;
	public FacelogRedisConfigProvider() {
		super();
	}

	@Override
	public String getHost() {
		if(null != redisLocation){
			return redisLocation.getHost();
		}
		return null;
	}

	@Override
	public void setHost(String host) {
	}

	@Override
	public int getPort() {
		if(null != redisLocation){
			return redisLocation.getPort();
		}
		return 0;
	}

	@Override
	public void setPort(int port) {

	}

	@Override
	public String getPassword() {
		if(null != redisLocation){
			return JedisURIHelper.getPassword(redisLocation);
		}
		return null;
	}

	@Override
	public void setPassword(String password) {

	}

	@Override
	public int getDatabase() {
		if(null != redisLocation){
			return JedisURIHelper.getDBIndex(redisLocation);
		}
		return 0;
	}

	@Override
	public void setDatabase(int database) {

	}

	@Override
	public int getTimeout() {
		return 0;
	}

	@Override
	public void setTimeout(int timeout) {

	}

	@Override
	public URI getURI() {
		return redisLocation;
	}

	@Override
	public void setURI(URI uri) {

	}

	@Override
	public final RedisConfigType type() {
		return RedisConfigType.CUSTOM;
	}

	/**
	 * 设置redis的访问地址<br>
	 * 调用此方法后才能使用{@link RedisConfigType#CUSTOM}实例的方法
	 * @param redisLocation
	 */
	public static void setRedisLocation(URI redisLocation) {
		FacelogRedisConfigProvider.redisLocation = checkNotNull(redisLocation,"redisLocation is null");
	}

	

}
