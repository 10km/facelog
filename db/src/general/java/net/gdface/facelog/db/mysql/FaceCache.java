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
import net.gdface.facelog.db.TableLoadCaching;
import net.gdface.facelog.db.FaceBean;

/**
 * cache manager for FaceBean base {@link com.google.common.cache.LoadingCache}<br>
 * primary key (fl_face.id) is key
 * @author guyadong
 *
 */
public class FaceCache extends TableLoadCaching<Integer, FaceBean> {
    private final FaceManager manager = FaceManager.getInstance();
/*
    private final RemovalListener<String, ImageBean> bindOnDeleteImageListener = new RemovalListener<String, ImageBean>(){
        @Override
        public void onRemoval(RemovalNotification<String, ImageBean> notification) {
            PersonBean bean = imageMd5Cacher.getBeanIfPresent(notification.getKey());                
            remove(bean);
            imageMd5Cacher.remove(bean);
            papersNumCacher.remove(bean);
        }
    };
    private final RemovalListener<String, ImageBean> bindSetNullImageListener = new RemovalListener<String, ImageBean>(){
        @Override
        public void onRemoval(RemovalNotification<String, ImageBean> notification) {
            PersonBean bean = imageMd5Cacher.getBeanIfPresent(notification.getKey());
            bean.setImageMd5(null);
            update(bean);
            imageMd5Cacher.update(bean);
            papersNumCacher.update(bean);
        }
    };
    public void bind(ImageCache imageCache){
        imageCache.addRemovalListener(bindOnDeleteImageListener);
    }
*/
    /** constructor<br>
     * @see {@link TableLoadCaching#TableLoadCaching(UpdateStrategy ,long , long , TimeUnit )}
     */
    public FaceCache(UpdateStrategy updateStragey,long maximumSize, long duration, TimeUnit unit) {
        super(updateStragey,maximumSize, duration, unit);
    }
    public FaceCache(long maximumSize, long duration, TimeUnit unit) {
        this(DEFAULT_STRATEGY,maximumSize,duration,unit);
    }
    public FaceCache(long maximumSize, long durationMinutes) {
        this(maximumSize, durationMinutes, DEFAULT_TIME_UNIT);
    }

    public FaceCache(long maximumSize) {
        this(maximumSize,DEFAULT_DURATION,DEFAULT_TIME_UNIT);
    }
    public FaceCache() {
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
    public Integer returnKey(FaceBean bean) {
        return null == bean ? null : bean.getId();
    }
    @Override
    protected FaceBean loadfromDatabase(Integer key)throws Exception {
        return manager.loadByPrimaryKeyChecked(key);
    }
    @Override
    public void update(FaceBean bean){
        super.update(bean);
    }
    @Override
    public Collection<FaceBean> update(Collection<FaceBean> beans){
        super.update(beans);
        return beans;
    }
    
    public FaceBean getBeanById(Integer id) throws ExecutionException{
        return getBean(id);
    }
    public FaceBean getBeanByIdUnchecked(Integer id){
        return getBeanUnchecked(id);
    }
}