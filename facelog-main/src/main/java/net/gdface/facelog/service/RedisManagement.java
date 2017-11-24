package net.gdface.facelog.service;

import gu.simplemq.redis.JedisPoolLazy;

/**
 * redis管理模块
 * @author guyadong
 *
 */
class RedisManagement implements ServiceConstant{	
	private final String redisURI;
	private String commendChannel;
	public RedisManagement() {
		JedisPoolLazy.createDefaultInstance(GlobalConfig.makeRedisParameters());
		redisURI = JedisPoolLazy.getDefaultInstance().getCanonicalURI().toString();
		GlobalConfig.showRedisParameters(JedisPoolLazy.getDefaultInstance().getParameters());
	}
	/** 返回redis服务器地址 */
	public String getRedisURI() {
		return redisURI;
	}
}
