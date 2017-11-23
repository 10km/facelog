package net.gdface.facelog.service;

import gu.simplemq.redis.JedisPoolLazy;

/**
 * redis管理模块
 * @author guyadong
 *
 */
class RedisInfo {	
	private final String redisURI;
	private String commendChannel;
	public RedisInfo() {
		JedisPoolLazy.createDefaultInstance(GlobalConfig.makeRedisParameters());
		redisURI = JedisPoolLazy.getDefaultInstance().getCanonicalURI().toString();
	}
	/** 返回redis服务器地址 */
	public String getRedisURI() {
		return redisURI;
	}
}
