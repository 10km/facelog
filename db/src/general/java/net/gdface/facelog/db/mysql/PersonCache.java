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
    public PersonCache(UpdateStrategy updateStragey,long maximumSize, long duration, TimeUnit unit) {
        super(updateStragey,maximumSize, duration, unit);

        imageMd5Cacher = new TableLoadCaching<String, PersonBean>(updateStragey, maximumSize, duration, unit){
            @Override
            public void registerListener() {
                manager.registerListener(tableListener);
            }
            @Override
            public void unregisterListener() {
                manager.unregisterListener(tableListener);
            }
            @Override
            public String returnKey(PersonBean bean) {
                return null == bean ? null : bean.getImageMd5();
            }
            @Override
            protected PersonBean loadfromDatabase(String key) throws Exception {
                return manager.loadByIndexImageMd5Checked(key);
            }};

        papersNumCacher = new TableLoadCaching<String, PersonBean>(updateStragey, maximumSize, duration, unit){
            @Override
            public void registerListener() {
                manager.registerListener(tableListener);
            }
            @Override
            public void unregisterListener() {
                manager.unregisterListener(tableListener);
            }
            @Override
            public String returnKey(PersonBean bean) {
                return null == bean ? null : bean.getPapersNum();
            }
            @Override
            protected PersonBean loadfromDatabase(String key) throws Exception {
                return manager.loadByIndexPapersNumChecked(key);
            }};
    }
    public PersonCache(long maximumSize, long duration, TimeUnit unit) {
        this(DEFAULT_STRATEGY,maximumSize,duration,unit);
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
        papersNumCacher.registerListener();    }
    @Override
    public void unregisterListener() {
        manager.unregisterListener(tableListener);
        
        imageMd5Cacher.unregisterListener();
        papersNumCacher.unregisterListener();
    }
    @Override
    public Integer returnKey(PersonBean bean) {
        return null == bean ? null : bean.getId();
    }
    @Override
    protected PersonBean loadfromDatabase(Integer key)throws Exception {
        return manager.loadByPrimaryKeyChecked(key);
    }
    @Override
    public void update(PersonBean bean){
        super.update(bean);
        
        imageMd5Cacher.update(bean);
        papersNumCacher.update(bean);
    }
    @Override
    public void remove(PersonBean bean){
        super.remove(bean);
        
        imageMd5Cacher.remove(bean);
        papersNumCacher.remove(bean);
    }
    public PersonBean getBeanById(Integer id) throws ExecutionException{
        return getBean(id);
    }
    public PersonBean getBeanByIdUnchecked(Integer id){
        return getBeanUnchecked(id);
    }
    public PersonBean getBeanByImageMd5(String imageMd5)  throws ExecutionException{
        return imageMd5Cacher.getBean(imageMd5);
    }
    public PersonBean getBeanByImageMd5Unchecked(String imageMd5){
        return imageMd5Cacher.getBeanUnchecked(imageMd5);
    }
    public PersonBean getBeanByPapersNum(String papersNum)  throws ExecutionException{
        return papersNumCacher.getBean(papersNum);
    }
    public PersonBean getBeanByPapersNumUnchecked(String papersNum){
        return papersNumCacher.getBeanUnchecked(papersNum);
    }
}