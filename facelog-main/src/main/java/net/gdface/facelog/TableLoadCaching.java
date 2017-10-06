package net.gdface.facelog;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import net.gdface.facelog.db.BaseBean;
import net.gdface.facelog.db.TableListener;

/**
 * 
 * 基于 {@link LoadingCache}实现表数据缓存,并可以通过{@link TableListener}实现缓存数组的更新
 * @author guyadong
 *
 * @param <K> 主键类型(Primary)
 * @param <B> 数据库记录对象类型(Java Bean)
 */
public abstract class TableLoadCaching<K ,B extends BaseBean<B>> implements ITableCache<K, B> {
	private final LoadingCache<K, B> cache;
	private final ConcurrentMap<K, B> cacheMap;
	protected final  TableListener.Adapter<B> tableListener;
	/** 返回bean中主键值 */
	protected abstract K returnPrimaryKey(B bean)
	/** 从数据库中加载主键(pk)指定的记录 */;
	protected abstract B loadfromDatabase(K pk);

	public TableLoadCaching(){
		this(DEFAULT_CACHE_MAXIMUMSIZE,
				DEFAULT_DURATION,
				DEFAULT_TIME_UNIT);
	}
	public TableLoadCaching(long maximumSize){
		this(maximumSize,
				DEFAULT_DURATION,
				DEFAULT_TIME_UNIT);
	}
	public TableLoadCaching(long maximumSize,long durationMinutes){
		this(maximumSize,durationMinutes,DEFAULT_TIME_UNIT);
	}
	public TableLoadCaching(long maximumSize,long duration, TimeUnit unit) {
		cache = CacheBuilder.newBuilder()
				.maximumSize(maximumSize)
				.expireAfterWrite(duration, unit)
				.build(
						new CacheLoader<K,B>() {
							@Override
							public B load(K key) throws Exception {
								return loadfromDatabase(key);
							}});
		cacheMap = cache.asMap();
		tableListener = new TableListener.Adapter<B>(){@Override
			public void afterUpdate(B bean) {
			cacheMap.putIfAbsent(returnPrimaryKey(bean), bean);
		}

		@Override
		public void afterDelete(B bean) {
			cacheMap.remove(returnPrimaryKey(bean));
		}};
	}
	@Override
	public B getBean(K key) {
		try {
			return cache.get(key);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

}
