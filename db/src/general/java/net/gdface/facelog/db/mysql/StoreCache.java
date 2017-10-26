// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// template: cache.java.vm
// ______________________________________________________
package net.gdface.facelog.db.mysql;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import net.gdface.facelog.db.TableLoadCaching;
import net.gdface.facelog.db.StoreBean;

/**
 * cache manager for StoreBean base {@link com.google.common.cache.LoadingCache}<br>
 * primary key (fl_store.md5) is key
 * @author guyadong
 *
 */
public class StoreCache extends TableLoadCaching<String, StoreBean> {
    private final StoreManager manager = StoreManager.getInstance();
    
/*
    private RemovalListener<String, ImageBean> foreignKeyRevmovalListener = null;
    public void bind(ImageCache fkCache){
        if(null == foreignKeyRevmovalListener){
            synchronized(this){
                if(null == foreignKeyRevmovalListener){
                    foreignKeyRevmovalListener = new ForeignKeyListner<String, ImageBean>(){
                        @Override
                        protected void onRemove(ImageBean fb) {
                            for(PersonBean bean:ImageManager.getInstance().getPersonBeansByImageMd5AsList(fb)){
                                manager.fire(Event.DELETE, bean);
                            }    
                        }};
                }
            }
            bind(fkCache,foreignKeyRevmovalListener);
        }
    }
*/
    /** constructor<br>
     * @see {@link TableLoadCaching#TableLoadCaching(UpdateStrategy ,long , long , TimeUnit )}
     */
    public StoreCache(UpdateStrategy updateStragey,long maximumSize, long duration, TimeUnit unit) {
        super(updateStragey,maximumSize, duration, unit);
    }
    public StoreCache(long maximumSize, long duration, TimeUnit unit) {
        this(DEFAULT_STRATEGY,maximumSize,duration,unit);
    }
    public StoreCache(long maximumSize, long durationMinutes) {
        this(maximumSize, durationMinutes, DEFAULT_TIME_UNIT);
    }

    public StoreCache(long maximumSize) {
        this(maximumSize,DEFAULT_DURATION,DEFAULT_TIME_UNIT);
    }
    public StoreCache() {
        this(DEFAULT_CACHE_MAXIMUMSIZE,DEFAULT_DURATION,DEFAULT_TIME_UNIT);
    }
    
    @Override
    public void registerListener() {
        manager.registerListener(tableListener);
            }
    @Override
    public void unregisterListener() {
        manager.unregisterListener(tableListener);
        
    }
    @Override
    public String returnKey(StoreBean bean) {
        return null == bean ? null : bean.getMd5();
    }
    @Override
    protected StoreBean loadfromDatabase(String key)throws Exception {
        return manager.loadByPrimaryKeyChecked(key);
    }
    @Override
    public void update(StoreBean bean){
        super.update(bean);
        
    }
    @Override
    public void remove(StoreBean bean){
        super.remove(bean);
        
    }
    public StoreBean getBeanByMd5(String md5) throws ExecutionException{
        return getBean(md5);
    }
    public StoreBean getBeanByMd5Unchecked(String md5){
        return getBeanUnchecked(md5);
    }
}