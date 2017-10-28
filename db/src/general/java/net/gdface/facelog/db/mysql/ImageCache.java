// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: cache.java.vm
// ______________________________________________________
package net.gdface.facelog.db.mysql;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import net.gdface.facelog.db.TableLoadCaching;
import net.gdface.facelog.db.ImageBean;

/**
 * cache manager for ImageBean base {@link com.google.common.cache.LoadingCache}<br>
 * primary key (fl_image.md5) is key
 * @author guyadong
 *
 */
public class ImageCache extends TableLoadCaching<String, ImageBean> {
    private final ImageManager manager = ImageManager.getInstance();
    
    /** constructor<br>
     * @see {@link TableLoadCaching#TableLoadCaching(UpdateStrategy ,long , long , TimeUnit )}
     */
    public ImageCache(UpdateStrategy updateStragey,long maximumSize, long duration, TimeUnit unit) {
        super(updateStragey,maximumSize, duration, unit);
        manager.bindForeignKeyListenerForDeleteRule();
    }
    public ImageCache(long maximumSize, long duration, TimeUnit unit) {
        this(DEFAULT_STRATEGY,maximumSize,duration,unit);
    }
    public ImageCache(long maximumSize, long durationMinutes) {
        this(maximumSize, durationMinutes, DEFAULT_TIME_UNIT);
    }

    public ImageCache(long maximumSize) {
        this(maximumSize,DEFAULT_DURATION,DEFAULT_TIME_UNIT);
    }
    public ImageCache() {
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
    public String returnKey(ImageBean bean) {
        return null == bean ? null : bean.getMd5();
    }
    @Override
    protected ImageBean loadfromDatabase(String key)throws Exception {
        return manager.loadByPrimaryKeyChecked(key);
    }
    public ImageBean getBeanByMd5(String md5) throws ExecutionException{
        return getBean(md5);
    }
    public ImageBean getBeanByMd5Unchecked(String md5){
        return getBeanUnchecked(md5);
    }
}