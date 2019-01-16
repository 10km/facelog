// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: junction.cache.java.vm
// ______________________________________________________

package net.gdface.facelog.db.mysql;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import net.gdface.facelog.db.BaseTableLoadCaching;
import net.gdface.facelog.db.exception.ObjectRetrievalException;
import net.gdface.facelog.db.ITableCache.UpdateStrategy;
import net.gdface.facelog.db.BaseJunctionTableCache;


import net.gdface.facelog.db.PermitBean;

/**
 * cache manager for PermitBean base {@link BaseJunctionTableCache}<br>
 * primary key {@code(device_group_id ->K1,person_group_id -> K2)}
 * @author guyadong
 *
 */
public class PermitCache extends BaseJunctionTableCache<Integer,Integer, PermitBean> {
    private final PermitManager manager = PermitManager.getInstance();
    /** constructor<br>
     * @see BaseTableLoadCaching#BaseTableLoadCaching(UpdateStrategy ,long , long , TimeUnit )
     */
    public PermitCache(UpdateStrategy updateStrategy,long maximumSize, long duration, TimeUnit unit) {
        super(updateStrategy,maximumSize, duration, unit);
        manager.bindForeignKeyListenerForDeleteRule();
    }
    public PermitCache(long maximumSize, long duration, TimeUnit unit) {
        this(BaseTableLoadCaching.DEFAULT_STRATEGY,maximumSize,duration,unit);
    }
    public PermitCache(long maximumSize, long durationMinutes) {
        this(maximumSize, durationMinutes, BaseTableLoadCaching.DEFAULT_TIME_UNIT);
    }

    public PermitCache(long maximumSize) {
        this(maximumSize,BaseTableLoadCaching.DEFAULT_DURATION,BaseTableLoadCaching.DEFAULT_TIME_UNIT);
    }
    public PermitCache() {
        this(BaseTableLoadCaching.DEFAULT_CACHE_MAXIMUMSIZE,BaseTableLoadCaching.DEFAULT_DURATION,BaseTableLoadCaching.DEFAULT_TIME_UNIT);
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
    protected Integer returnK1(PermitBean bean) {
        return null == bean ? null : bean.getDeviceGroupId();
    }
    @Override
    protected Integer returnK2(PermitBean bean) {
        return null == bean ? null : bean.getPersonGroupId();
    }
    @Override
    protected Object loadfromDatabase(Key key)throws Exception {
        if(null != key.k1 && null != key.k2){
            return manager.loadByPrimaryKey(key.k1, key.k2);
        }
        PermitBean bean = new PermitBean();
        if(null != key.k1){
            bean.setDeviceGroupId(key.k1);
        }else if(null != key.k2){
            bean.setPersonGroupId(key.k2);
        }else{
            throw new ObjectRetrievalException();
        }
        List<PermitBean> list = manager.loadUsingTemplateAsList(bean);
        if(list.isEmpty()){
            throw new ObjectRetrievalException();
        }
        return list;
    }
    /** 
     * return all matched beans on field fl_permit(device_group_id) with deviceGroupId 
     * @see BaseJunctionTableCache#getBeansByK1(Object)
     */
    public Set<PermitBean> getBeanByDeviceGroupId(Integer deviceGroupId) throws ExecutionException{
        return getBeansByK1(deviceGroupId);
    }
    /** 
     * return all matched beans on field fl_permit(device_group_id) with deviceGroupId 
     * @see BaseJunctionTableCache#getBeansByK1Unchecked(Object)
     */
    public Set<PermitBean> getBeanByDeviceGroupIdUnchecked(Integer deviceGroupId){
        return getBeansByK1Unchecked(deviceGroupId);
    }
    /** 
     * return all matched beans on field fl_permit(person_group_id) with personGroupId 
     * @see BaseJunctionTableCache#getBeansByK2(Object)
     */
    public Set<PermitBean> getBeanByPersonGroupId(Integer personGroupId) throws ExecutionException{
        return getBeansByK2(personGroupId);
    }
    /** 
     * return all matched beans on field fl_permit(person_group_id) with personGroupId 
     * @see BaseJunctionTableCache#getBeansByK2Unchecked(Object)
     */
    public Set<PermitBean> getBeanByPersonGroupIdUnchecked(Integer personGroupId){
        return getBeansByK2Unchecked(personGroupId);
    }
    /** see also {@link BaseJunctionTableCache#getBean(Object,Object)} */
    public PermitBean getBeanByPrimaryKey(Integer deviceGroupId,Integer personGroupId) throws ExecutionException{
        return getBean(deviceGroupId,personGroupId);
    }
    /** see also {@link BaseJunctionTableCache#getBeanUnchecked(Object,Object)} */
    public PermitBean getBeanByPrimaryKeyUnchecked(Integer deviceGroupId,Integer personGroupId){
        return getBeanUnchecked(deviceGroupId,personGroupId);
    }
}