package net.gdface.facelog.client.dtalk;

import java.net.URI;
import gu.dtalk.redis.RedisConfigProvider;
import gu.dtalk.redis.RedisConfigType;
import static com.google.common.base.Preconditions.*;

public class FacelogRedisConfigProvider implements RedisConfigProvider {
	private static URI redisLocation;
	@Override
	public String getHost() {
		return null;
	}

	@Override
	public void setHost(String host) {
	}

	@Override
	public int getPort() {
		return 0;
	}

	@Override
	public void setPort(int port) {

	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public void setPassword(String password) {

	}

	@Override
	public int getDatabase() {
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

	public static void setRedisLocation(URI redisLocation) {
		FacelogRedisConfigProvider.redisLocation = checkNotNull(redisLocation,"redisLocation is null");
	}

	

}
