package gu.simplemq.redis;

/**
 * @author guyadong
 *
 */
public interface IRedisComponent {
	/**
	 * 返回 连接池对象
	 * @return
	 */
	public JedisPoolLazy getPoolLazy();
}
