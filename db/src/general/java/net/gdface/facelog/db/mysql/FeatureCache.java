// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// template: cache.java.vm
// ______________________________________________________
package net.gdface.facelog.db.mysql;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import net.gdface.facelog.db.TableLoadCaching;
import net.gdface.facelog.db.FeatureBean;

/**
 * cache manager for FeatureBean base {@link com.google.common.cache.LoadingCache}<br>
 * primary key (fl_feature.md5) is key
 * @author guyadong
 *
 */
public class FeatureCache extends TableLoadCaching<String, FeatureBean> {
    private final FeatureManager manager = FeatureManager.getInstance();
    /** constructor<br>
     * @see {@link TableLoadCaching#TableLoadCaching(UpdateStrategy ,long , long , TimeUnit )}
     */
    public FeatureCache(UpdateStrategy updateStragey,long maximumSize, long duration, TimeUnit unit) {
        super(updateStragey,maximumSize, duration, unit);
    }
    public FeatureCache(long maximumSize, long duration, TimeUnit unit) {
        this(DEFAULT_STRATEGY,maximumSize,duration,unit);
    }
    public FeatureCache(long maximumSize, long durationMinutes) {
        this(maximumSize, durationMinutes, DEFAULT_TIME_UNIT);
    }

    public FeatureCache(long maximumSize) {
        this(maximumSize,DEFAULT_DURATION,DEFAULT_TIME_UNIT);
    }
    public FeatureCache() {
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
    protected String returnKey(FeatureBean bean) {
        return bean.getMd5();
    }
    @Override
    protected FeatureBean loadfromDatabase(String key) {
        return manager.loadByPrimaryKey(key);
    }
    @Override
    public void update(FeatureBean bean){
        super.update(bean);
    }
    @Override
    public Collection<FeatureBean> update(Collection<FeatureBean> beans){
        super.update(beans);
        return beans;
    }
    
    public FeatureBean getBeanByMd5(String md5){
        return super.getBean(md5);
    }
}