// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.db.mysql;

import java.util.Collection;
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
    public FaceCache(long maximumSize, long duration, TimeUnit unit) {
        super(maximumSize, duration, unit);
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
    protected Integer returnKey(FaceBean bean) {
        return bean.getId();
    }
    @Override
    protected FaceBean loadfromDatabase(Integer key) {
        return manager.loadByPrimaryKey(key);
    }
    @Override
    public void put(FaceBean bean){
        super.put(bean);
    }
    @Override
    public Collection<FaceBean> put(Collection<FaceBean> beans){
        super.put(beans);
        return beans;
    }
    @Override
    public void putIfAbsent(FaceBean bean){
        super.putIfAbsent(bean);
    }
    @Override
    public Collection<FaceBean> putIfAbsent(Collection<FaceBean> beans){
        super.putIfAbsent(beans);
        return beans;
    }
    @Override
    public void replace(FaceBean bean){
        super.replace(bean);
    }
    @Override
    public Collection<FaceBean> replace(Collection<FaceBean> beans){
        super.replace(beans);
        return beans;
    }
    
    public FaceBean getBeanById(Integer id){
        return super.getBean(id);
    }
}