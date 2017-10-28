// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: table.loadcaching.java.vm
// ______________________________________________________
package net.gdface.facelog.db;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;

import net.gdface.facelog.db.exception.ObjectRetrievalException;

/**
 * 
 * 基于 {@link LoadingCache}实现表数据缓存,并可以通过{@link TableListener}实现缓存数据自动更新
 * @author guyadong
 *
 * @param <K> 主键类型(Primary or Unique)
 * @param <B> 数据库记录对象类型(Java Bean)
 */
public abstract class TableLoadCaching<K ,B extends BaseBean<B>> implements ITableCache<K, B> {

    private static class Singleton{
        final static ExecutorService DEFAULT_POOL = Executors.newCachedThreadPool();
        static{
            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run() {
                    DEFAULT_POOL.shutdown();
                }});
        }
    }
    private static ExecutorService executor;

    protected static ExecutorService getExecutorService() {
        if(null == executor)
            synchronized(TableLoadCaching.class){
                if(null == executor){
                    executor = Singleton.DEFAULT_POOL;
                }
            }
        return executor;
    }
    /**
     * initialize the member {@link #executor },
     * if {@code executor} is {@code null} or the member {@link #executor } is initialized, do nothing
     * @param executor
     */
    public static void setExecutorService(ExecutorService executor) {
        checkNotNull(executor);
        // Double Checked Locking
        if(null == TableLoadCaching.executor)
            synchronized(TableLoadCaching.class){
                if(null == TableLoadCaching.executor){
                    TableLoadCaching.executor = executor;
                }
            }
    }
    private final LoadingCache<K, B> cache;
    protected final ConcurrentMap<K, B> cacheMap;
    protected final  TableListener.Adapter<B> tableListener;
    /** 当前更新策略 */
    private final UpdateStrategy updateStragey;
    /** 返回bean中主键值 */
    public abstract K returnKey(B bean)
    /** 从数据库中加载主键(pk)指定的记录 */;
    protected abstract B loadfromDatabase(K key)throws Exception;

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
        this(DEFAULT_STRATEGY,maximumSize,duration,unit);
    }
    /**
     * 构造函数
     * @param updateStragey 缓存更新策略
     * @param maximumSize 最大缓存容量,参见 {@link CacheBuilder#maximumSize(long)}
     * @param duration 失效时间,参见 {@link CacheBuilder#expireAfterWrite(long, TimeUnit)}
     * @param unit {@code duration}的时间单位
     */
    public TableLoadCaching(UpdateStrategy updateStragey,long maximumSize,long duration, TimeUnit unit) {        
        if(null == updateStragey ) updateStragey = DEFAULT_STRATEGY;
        if(0 >= maximumSize) maximumSize = DEFAULT_CACHE_MAXIMUMSIZE;
        if(0 >= duration) maximumSize = DEFAULT_DURATION;
        if(null == unit) unit = DEFAULT_TIME_UNIT;
        this.updateStragey = updateStragey;
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
        // 初始化侦听器,当表数据改变时自动更新缓存
        tableListener = new TableListener.Adapter<B>(){
            @Override
            public void afterUpdate(B bean) {
                update(bean);
            }
            
            @Override
            public void afterInsert(B bean) {
                update(bean);
            }
            
            @Override
            public void afterDelete(B bean) {
                // the remove method allow null key
                // see also com.google.common.cache.LocalCache.remove(Object key)
                cacheMap.remove(returnKey(bean));
            }};
    }
    /**
     * @see {@link com.google.common.cache.LoadingCache#get(Object)}
     */
    @Override
    public B getBean(K key)throws ExecutionException{
        return cache.get(key);
    }
    @Override
    public B getBeanIfPresent(K key){
        return null == key ? null : cache.getIfPresent(key);
    }
    @Override
    public B getBeanUnchecked(K key){
        try{
            return cache.getUnchecked(key);
        }catch(UncheckedExecutionException e){
            if(e.getCause() instanceof ObjectRetrievalException){
                return null;
            }
            throw e;
        }        
    }
    @Override
    public void remove(B bean){
        cacheMap.remove(returnKey(bean));
    }
    /**
     * 根据当前更新策略({@link UpdateStrategy})将{@code bean}更新到缓存
     * @see ITableCache#update(net.gdface.facelog.db.BaseBean)
     */
    @Override
    public void update(B bean){
        updateStragey.update(cacheMap, returnKey(bean), bean);
    }
}

