package gu.simplemq;

/**
 * @author guyadong
 *
 * @param <V>
 */
public interface IKeyHelper<V> {
	/**
	 * 根据V返回 K 实例
	 * @param v
	 * @return
	 */
	public String returnKey(V v);
}
