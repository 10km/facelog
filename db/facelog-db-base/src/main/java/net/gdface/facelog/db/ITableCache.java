// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: itablecache.java.vm
// ______________________________________________________
package net.gdface.facelog.db;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import net.gdface.facelog.db.exception.ObjectRetrievalException;
/**
 * 数据库对象缓存接口
 * @param <K> 主键类型(Primary or Unique)
 * @param <B> 数据库记录对象类型(Java Bean)
 * @author guyadong
 */
public interface ITableCache<K, B extends BaseBean<B>> {
    class ImmutableEntry<K,V> implements Map.Entry<K,V>{
        private K key;
        private V value;
        public ImmutableEntry(K key) {
            this.key = key;
        }
        public ImmutableEntry(K key, V value) {
            this(key);
            this.value = value;
        }
        @Override
        public final K getKey() {
            return key;
        }

        @Override
        public final V getValue() {            
            try {
                return (V) reload();
            } catch(ObjectRetrievalException e){
                return null;
            }catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public final V setValue(V value) {
            throw new UnsupportedOperationException();
        }
        protected V reload()throws Exception{
            return value;
        }
    }
    /** 
     * Update strategy for cache
     */
    public static enum UpdateStrategy{        
        /** update no matter whether key exists */
        always,
        /** update only if key exists */
        replace,
        /** remove key  */
        remove,
        /** reload data if key exists, need {@code entry } implement the reload method */
        refresh;
        /**
         * update {@code entry} to {@code map},if {code getValue()} return {@code null},remove key.
         */
        public <K,V> void update(ConcurrentMap<K,V> map,ImmutableEntry<K,V>entry){
            if(null == map || null == entry ){
                return ;
            }
            K key = entry.getKey();
            if( null == key){
                return;
            }
            V value = entry.getValue();
            if(null == value){
                map.remove(key);
                return ;
            }
            switch(this){
            case always:
                map.put(key, value);
                break;
            case replace:
                map.replace(key, value);
                break;
            case remove:
                map.remove(key);
                break;
            case refresh:
                map.replace(key, value);
            default:
                break;
            }
        }
    }
    public static final UpdateStrategy DEFAULT_STRATEGY = UpdateStrategy.always;
    public static final long DEFAULT_CACHE_MAXIMUMSIZE = 10000;
    public static final long DEFAULT_DURATION = 10;
    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MINUTES;
    /**
     * 加载主键(key)指定的记录,如果缓存中没有则从数据库中查询<br>
     * 数据库中没有找到则抛出异常
     * @param key
     * @return
     * @throws Exception
     */
    public B getBean(K key) throws Exception;
    /**
     * 加载主键(key)指定的记录,如果缓存中没有则从数据库中查询<br>
     * 数据库中没有找到则返回{@code null}
     * @param key
     * @return
     */
    public B getBeanUnchecked(K key);
    /**
     * 返回cache中{@code key}指定的记录,如果不存在就返回{@code null}
     * @param key  
     * @return return false if key is null
     */
    public B getBeanIfPresent(K key);
    /**
     * 删除cache中{@code key}指定的记录
     * @param bean
     */
    public void remove(B bean);
    /**
     * 向cache中更新数据
     * @param bean
     * @see UpdateStrategy
     */
    public void update(B bean);
    /** 注册侦听器 */
    public void registerListener();
    /** 注销侦听器 */
    public void unregisterListener();
}