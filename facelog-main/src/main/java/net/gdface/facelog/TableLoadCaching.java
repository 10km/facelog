package net.gdface.facelog;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import net.gdface.facelog.db.BaseBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.TableManager;

public abstract class TableLoadCaching<P ,B extends BaseBean<?>,M extends TableManager<B>> {
	private final M manager;
	private final LoadingCache<P, B> cache;
	private final ConcurrentMap<P, B> cacheMap;
	protected abstract P getKey(B bean);
	public TableLoadCaching(M manager) {
		this.manager = Preconditions.checkNotNull(manager);
		cache = CacheBuilder.newBuilder()
				.maximumSize(10000)
				.expireAfterWrite(10, TimeUnit.MINUTES)
				.build(
						new CacheLoader<P,B>() {
							@Override
							public B load(P key) throws Exception {
								return TableLoadCaching.this.manager.loadByPrimaryKey(key);
							}});
		cacheMap = cache.asMap();
		manager.registerListener(new TableListener.Adapter<B>(){
			@Override
			public void afterUpdate(B bean) {
				cacheMap.putIfAbsent(getKey(bean), bean);
			}

			@Override
			public void afterDelete(B bean) {
				cacheMap.remove(getKey(bean));
			}			
		});
	}
}
