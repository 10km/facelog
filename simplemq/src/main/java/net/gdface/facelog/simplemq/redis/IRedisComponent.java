package net.gdface.facelog.simplemq.redis;

public interface IRedisComponent {
	public JedisPoolLazy getPoolLazy();
}
