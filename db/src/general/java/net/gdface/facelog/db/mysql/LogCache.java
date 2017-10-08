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
import net.gdface.facelog.db.LogBean;

/**
 * cache manager for LogBean base {@link com.google.common.cache.LoadingCache}<br>
 * primary key (fl_log.id) is key
 * @author guyadong
 *
 */
public class LogCache extends TableLoadCaching<Integer, LogBean> {
    private final LogManager manager = LogManager.getInstance();
    public LogCache(long maximumSize, long duration, TimeUnit unit) {
        super(maximumSize, duration, unit);
    }

    public LogCache(long maximumSize, long durationMinutes) {
        this(maximumSize, durationMinutes, DEFAULT_TIME_UNIT);
    }

    public LogCache(long maximumSize) {
        this(maximumSize,DEFAULT_DURATION,DEFAULT_TIME_UNIT);
    }
    public LogCache() {
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
    protected Integer returnKey(LogBean bean) {
        return bean.getId();
    }
    @Override
    protected LogBean loadfromDatabase(Integer key) {
        return manager.loadByPrimaryKey(key);
    }
    @Override
    public void put(LogBean bean){
        super.put(bean);
    }
    @Override
    public Collection<LogBean> put(Collection<LogBean> beans){
        super.put(beans);
        return beans;
    }
    @Override
    public void putIfAbsent(LogBean bean){
        super.putIfAbsent(bean);
    }
    @Override
    public Collection<LogBean> putIfAbsent(Collection<LogBean> beans){
        super.putIfAbsent(beans);
        return beans;
    }
    @Override
    public void replace(LogBean bean){
        super.replace(bean);
    }
    @Override
    public Collection<LogBean> replace(Collection<LogBean> beans){
        super.replace(beans);
        return beans;
    }
    
    public LogBean getBeanById(Integer id){
        return super.getBean(id);
    }
}