package net.gdface.facelog;

import java.util.concurrent.TimeUnit;

import net.gdface.facelog.db.BaseBean;

public interface ITableCache<K, B extends BaseBean<B>> {
	public static final long DEFAULT_CACHE_MAXIMUMSIZE = 10000;
	public static final long DEFAULT_DURATION = 10;
	public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MINUTES;
	/**
	 * 加载主键(key)指定的记录,如果缓存中没有则从数据库中查询
	 * @param key
	 * @return
	 */
	B getBean(K key);
}