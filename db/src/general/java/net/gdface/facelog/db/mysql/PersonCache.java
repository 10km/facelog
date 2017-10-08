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
import net.gdface.facelog.db.PersonBean;

/**
 * cache manager for PersonBean base {@link com.google.common.cache.LoadingCache}<br>
 * primary key (fl_person.id) is key
 * @author guyadong
 *
 */
public class PersonCache extends TableLoadCaching<Integer, PersonBean> {
    private final PersonManager manager = PersonManager.getInstance();
    private final TableLoadCaching<String, PersonBean> imageMd5Cacher;
    private final TableLoadCaching<String, PersonBean> papersNumCacher;
    public PersonCache(long maximumSize, long duration, TimeUnit unit) {
        super(maximumSize, duration, unit);

        imageMd5Cacher = new TableLoadCaching<String, PersonBean>(){
            @Override
            public void registerListener() {
                manager.registerListener(tableListener);
            }
            @Override
            public void unregisterListener() {
                manager.unregisterListener(tableListener);        
            }
            @Override
            protected String returnKey(PersonBean bean) {
                return bean.getImageMd5();
            }
            @Override
            protected PersonBean loadfromDatabase(String key) {
                return manager.loadByIndexImageMd5(key);
            }};

        papersNumCacher = new TableLoadCaching<String, PersonBean>(){
            @Override
            public void registerListener() {
                manager.registerListener(tableListener);
            }
            @Override
            public void unregisterListener() {
                manager.unregisterListener(tableListener);        
            }
            @Override
            protected String returnKey(PersonBean bean) {
                return bean.getPapersNum();
            }
            @Override
            protected PersonBean loadfromDatabase(String key) {
                return manager.loadByIndexPapersNum(key);
            }};
    }

    public PersonCache(long maximumSize, long durationMinutes) {
        this(maximumSize, durationMinutes, DEFAULT_TIME_UNIT);
    }

    public PersonCache(long maximumSize) {
        this(maximumSize,DEFAULT_DURATION,DEFAULT_TIME_UNIT);
    }
    public PersonCache() {
        this(DEFAULT_CACHE_MAXIMUMSIZE,DEFAULT_DURATION,DEFAULT_TIME_UNIT);
    }
    
    @Override
    public void registerListener() {
        manager.registerListener(tableListener);
        imageMd5Cacher.registerListener();
        papersNumCacher.registerListener();
    }
    @Override
    public void unregisterListener() {
        manager.unregisterListener(tableListener);
        imageMd5Cacher.unregisterListener();
        papersNumCacher.unregisterListener();
    }
    @Override
    protected Integer returnKey(PersonBean bean) {
        return bean.getId();
    }
    @Override
    protected PersonBean loadfromDatabase(Integer key) {
        return manager.loadByPrimaryKey(key);
    }
    @Override
    public void put(PersonBean bean){
        super.put(bean);
        imageMd5Cacher.put(bean);
        papersNumCacher.put(bean);
    }
    @Override
    public Collection<PersonBean> put(Collection<PersonBean> beans){
        super.put(beans);
        imageMd5Cacher.put(beans);
        papersNumCacher.put(beans);
        return beans;
    }
    @Override
    public void putIfAbsent(PersonBean bean){
        super.putIfAbsent(bean);
        imageMd5Cacher.putIfAbsent(bean);
        papersNumCacher.putIfAbsent(bean);
    }
    @Override
    public Collection<PersonBean> putIfAbsent(Collection<PersonBean> beans){
        super.putIfAbsent(beans);
        imageMd5Cacher.putIfAbsent(beans);
        papersNumCacher.putIfAbsent(beans);
        return beans;
    }
    @Override
    public void replace(PersonBean bean){
        super.replace(bean);
        imageMd5Cacher.replace(bean);
        papersNumCacher.replace(bean);
    }
    @Override
    public Collection<PersonBean> replace(Collection<PersonBean> beans){
        super.replace(beans);
        imageMd5Cacher.replace(beans);
        papersNumCacher.replace(beans);
        return beans;
    }
    
    public PersonBean getBeanById(Integer id){
        return super.getBean(id);
    }
    public PersonBean getBeanByImageMd5(String imageMd5){
        return imageMd5Cacher.getBean(imageMd5);
    }
    public PersonBean getBeanByPapersNum(String papersNum){
        return papersNumCacher.getBean(papersNum);
    }
}