// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: base.junction.table.cache.java.vm
// ______________________________________________________
package net.gdface.facelog.db;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.UncheckedExecutionException;
import static com.google.common.base.Preconditions.checkNotNull;

import net.gdface.facelog.db.ITableCache.UpdateStrategy;
import net.gdface.facelog.db.exception.ObjectRetrievalException;

/**
 * 
 * 基于 {@link LoadingCache}实现MANY-TO-MANY 联接表(junction table)数据缓存,并可以通过{@link TableListener}实现缓存数据自动更新<br>
 * 联接表(junction table)定义:主键为两个字段K1,K2,并且两个字段又各是联接另外两张表的外键
 * @author guyadong
 *
 * @param <K1> 外键1类型(Foreign Key)
 * @param <K2> 外键2类型(Foreign Key)
 * @param <B> 数据库记录对象类型(Java Bean)
 */
public abstract class BaseJunctionTableCache<K1 ,K2,B extends BaseBean<B>> {
    @SuppressWarnings("serial")
    private static class CollectionReturnException extends Exception{}
    public final class Key{
        public K1 k1;
        public K2 k2;
        Key(K1 k1, K2 k2) {
            this.k1 = k1;
            this.k2 = k2;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((k1 == null) ? 0 : k1.hashCode());
            result = prime * result + ((k2 == null) ? 0 : k2.hashCode());
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj){
                return true;
            }
            if (obj == null || Key.class != obj.getClass()){
                return false;
            }
            @SuppressWarnings("unchecked")
            Key other = (Key) obj;
            return (Objects.equals(k1, other.k1) && Objects.equals(k2, other.k2));
        }
    }
    /** 返回一个用于查询的临时对象 */
    private Key asTmpKey(K1 k1,K2 k2){
        return new Key(k1,k2);
    }
    private final LoadingCache<Key, B> cache;
    private final ConcurrentMap<Key, B> cacheMap;
    protected final  TableListener.Adapter<B> tableListener;
    /** 当前更新策略 */
    private final UpdateStrategy updateStragey;
    private final Function<B, K1> funReturnK1 =new Function<B,K1>(){
        @Override
        public K1 apply(B input) {
            return returnK1(input);
        }};
    private final Function<B, K2> funReturnK2 = new Function<B,K2>(){
        @Override
        public K2 apply(B input) {
            return returnK2(input);
        }};
    /**
     * 返回{@code bean}中外键K1值,{@code bean}为{@code null}时返回{@code null}
     * @param bean
     * @return
     */
    protected abstract K1 returnK1(B bean);
    /**
     * 返回{@code bean}中外键K2值,{@code bean}为{@code null}时返回{@code null}
     * @param bean
     * @return
     */
    protected abstract K2 returnK2(B bean);
    /**
     * 从数据库中加载外键指定的记录,没有找到指定的记录则抛出异常{@link ObjectRetrievalException}<br>
     * {@code Key.k1,Key.k2}都不为{@code null}返回{@code B},否则返回{@code Collection<B>}<br> 
     * 不可返回{@code null}
     * @param key
     * @return
     * @throws ObjectRetrievalException
     * @throws Exception
     */
    protected abstract Object loadfromDatabase(Key key)throws Exception;
    /** 注册侦听器 */
    public abstract void registerListener();
    /** 注销侦听器 */
    public abstract void unregisterListener();
    public BaseJunctionTableCache(){
        this(ITableCache.DEFAULT_CACHE_MAXIMUMSIZE,
                ITableCache.DEFAULT_DURATION,
                ITableCache.DEFAULT_TIME_UNIT);
    }
    public BaseJunctionTableCache(long maximumSize){
        this(maximumSize,
                ITableCache.DEFAULT_DURATION,
                ITableCache.DEFAULT_TIME_UNIT);
    }
    public BaseJunctionTableCache(long maximumSize,long durationMinutes){
        this(maximumSize,durationMinutes,ITableCache.DEFAULT_TIME_UNIT);
    }
    public BaseJunctionTableCache(long maximumSize,long duration, TimeUnit unit) {
        this(ITableCache.DEFAULT_STRATEGY,maximumSize,duration,unit);
    }
    /**
     * 构造函数
     * @param updateStragey 缓存更新策略
     * @param maximumSize 最大缓存容量,参见 {@link CacheBuilder#maximumSize(long)}
     * @param duration 失效时间,参见 {@link CacheBuilder#expireAfterWrite(long, TimeUnit)}
     * @param unit {@code duration}的时间单位
     */
    public BaseJunctionTableCache(UpdateStrategy updateStragey,long maximumSize,long duration, TimeUnit unit) {        
        if(null == updateStragey ){
            updateStragey = ITableCache.DEFAULT_STRATEGY;
        }
        if(0 >= maximumSize){
            maximumSize = ITableCache.DEFAULT_CACHE_MAXIMUMSIZE;
        }
        if(0 >= duration){
            maximumSize = ITableCache.DEFAULT_DURATION;
        }
        if(null == unit){
            unit = ITableCache.DEFAULT_TIME_UNIT;
        }
        this.updateStragey = updateStragey;
        cache = CacheBuilder.newBuilder()
            .maximumSize(maximumSize)
            .expireAfterWrite(duration, unit)
            .build(
                new CacheLoader<Key,B>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public B load(Key key) throws Exception {
                        Object obj = loadfromDatabase(key);
                        try{
                            return (B)obj;
                        }catch(ClassCastException e){
                            if(obj instanceof Collection){
                                for(B bean:(Collection<B>)obj){
                                    update(bean);
                                }
                                throw new CollectionReturnException();
                            }
                            throw e;
                        }
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
                remove(bean);
            }};
    }
    private<K> Set<B> filter(final K k,final Function <B,K>fun){
        return null == k 
                ? ImmutableSet.<B>of()
                : Sets.newHashSet(Collections2.filter(cacheMap.values(), new Predicate<B>(){
                    @Override
                    public boolean apply(B input) {
                        return Objects.equals(k, fun.apply(input));
                    }}));
    }
    /** 
     * 返回数据库中匹配{@code k1}的所有记录,没有结果返回则抛出异常 
     * @see {@link com.google.common.cache.LoadingCache#get(Object)}
     * @throws ObjectRetrievalException not found
     * @throws ExecutionException
     */
    public Set<B> getBeansByK1(K1 k1)throws ExecutionException{
        try{
            cache.get(asTmpKey(k1,null));
            // dead code 不会执行到这里
            return null;
        }catch(ExecutionException e){
            if( e.getCause() instanceof CollectionReturnException){
                return filter(k1,funReturnK1);
            }
            throw e;
        }
    }
    /** 
     * 返回内存cache中匹配{@code k1}的所有记录,没有结果返回则返回empty Set
     * @see {@link com.google.common.cache.LoadingCache#getIfPresent(Object)}
     */
    public Set<B> getBeansByK1IfPresent(K1 k1){
        return filter(k1,funReturnK1);
    }
    /** 
     * 返回数据库中匹配{@code k1}的所有记录,没有结果返回则返回empty Set
     * @see {@link com.google.common.cache.LoadingCache#getUnchecked(Object)}
     * @throws UncheckedExecutionException
     */
    public Set<B> getBeansByK1Unchecked(K1 k1){
        try{
            cache.getUnchecked(asTmpKey(k1,null));
            // dead code 不会执行到这里
            return null;
        }catch(UncheckedExecutionException e){
            if( e.getCause() instanceof CollectionReturnException){
                return filter(k1,funReturnK1);
            }
            throw e;
        }
    }
    /** see also {@link #getBeansByK1(K1)}*/
    public Set<B> getBeansByK2(K2 k2)throws ExecutionException{
        try{
            cache.get(asTmpKey(null,k2));
            // dead code 不会执行到这里
            return null;
        }catch(ExecutionException e){
            if( e.getCause() instanceof CollectionReturnException){
                return filter(k2,funReturnK2);
            }
            throw e;
        }
    }
    /** see also {@link #getBeansByK1IfPresent(K1)} */
    public Set<B> getBeansByK2IfPresent(K2 k2){
        return filter(k2,funReturnK2);
    }
    /** see also {@link #getBeansByK1Unchecked(K1)} */
    public Set<B> getBeansByK2Unchecked(K2 k2){
        try{
            cache.getUnchecked(asTmpKey(null,k2));
            // dead code 不会执行到这里
            return null;
        }catch(UncheckedExecutionException e){
            if( e.getCause() instanceof CollectionReturnException){
                return filter(k2,funReturnK2);
            }
            throw e;
        }
    }
    /** 返回数据库中与{@code k1,k2}(不可为{@code null})匹配的记录<br>
     *  如果没找到记录则抛出异常
     * @see {@link com.google.common.cache.LoadingCache#get(Object)}
     * @throws ObjectRetrievalException not found
     * @throws ExecutionException
     */
    public B getBean(K1 k1,K2 k2) throws ExecutionException{
        return cache.get(new Key(checkNotNull(k1),checkNotNull(k2)));
    }
    /** 
     * 返回内存cache中与{@code k1,k2}匹配的记录<br>
     * 如果没找到记录则返回{@code null}
     * @see {@link com.google.common.cache.LoadingCache#getIfPresent(Object)}
     * @throws ExecutionException
     */
    public B getBeanIfPresent(K1 k1,K2 k2){
        return null ==k1 || null == k2 ? null : cache.getIfPresent(asTmpKey(k1,k2));
    }
    /** 
     * 返回数据库中与{@code k1,k2}匹配的记录<br>
     * 如果没找到记录则返回{@code null}
     * @see {@link com.google.common.cache.LoadingCache#getUnchecked(Object)}
     * @throws ExecutionException
     */
    public B getBeanUnchecked(K1 k1,K2 k2){
        try{
            return null ==k1 || null == k2 ? null : cache.getUnchecked(new Key(k1,k2));
        }catch(UncheckedExecutionException e){
            if(e.getCause() instanceof ObjectRetrievalException){
                return null;
            }
            throw e;
        } 
    }

    /** 从缓存中删除{@code bean}指定的记录 */
    public void remove(B bean){
        K1 k1 = returnK1(bean);
        K2 k2 = returnK2(bean);
        if(null !=k1 && null != k2){
            cacheMap.remove(new Key(k1,k2));
        }
    }
    /**
     * 更新{@code bean}到指定的缓存对象{@code cacheMap}
     * @param bean
     * @param cacheMap
     */
    public void update(B bean){
        K1 k1 = returnK1(bean);
        K2 k2 = returnK2(bean);
        if(null !=k1 && null != k2){
            this.updateStragey.update(cacheMap, new Key(k1,k2), bean);
        }
    }
}