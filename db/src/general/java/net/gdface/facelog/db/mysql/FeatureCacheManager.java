// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: cache.manager.java.vm
// ______________________________________________________
package net.gdface.facelog.db.mysql;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.UncheckedExecutionException;

import net.gdface.facelog.db.ITableCache;
import net.gdface.facelog.db.ITableCache.UpdateStrategy;
import net.gdface.facelog.db.exception.ObjectRetrievalException;
import net.gdface.facelog.db.exception.WrapDAOException;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.mysql.FeatureManager;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.mysql.FeatureCache;

/**
 * cache implementation for FeatureManager<br>
 * @author guyadong
 */
public class FeatureCacheManager extends FeatureManager
{
    /** singleton of FeatureCacheManager */
    private static FeatureCacheManager instance;
    /** 
     * @return a instance of FeatureCacheManager
     * @throws IllegalStateException while {@link #instance} is null
     */
    public static final FeatureCacheManager getInstance(){
        if(null == instance){
            throw new IllegalStateException("uninitialized instance of FeatureCacheManager");
        }
        return instance;
    }
    /**
     * create a instance of FeatureCacheManager and assign to {@link #instance} if {@code instance} is not initialized.<br>
     * otherwise return {@code instance}.
     * @see {@link FeatureCacheManager#FeatureCacheManager(UpdateStrategy ,long , long , TimeUnit )}
     */
    public static synchronized final FeatureCacheManager makeInstance(UpdateStrategy updateStragey,long maximumSize, long duration, TimeUnit unit){
        if(null == instance){
            instance = new FeatureCacheManager(updateStragey,maximumSize,duration,unit);
        }
        return instance;
    }
    /** @see #makeInstance(UpdateStrategy,long, long, TimeUnit) */
    public static final FeatureCacheManager makeInstance(long maximumSize, long duration, TimeUnit unit){
        return makeInstance(ITableCache.DEFAULT_STRATEGY,maximumSize, duration, unit);
    }
    /** @see #makeInstance(long, long, TimeUnit) */
    public static final FeatureCacheManager makeInstance(long maximumSize, long durationMinutes){
        return makeInstance(maximumSize, durationMinutes, ITableCache.DEFAULT_TIME_UNIT);
    }
    /** @see #makeInstance(long, long, TimeUnit) */
    public static final FeatureCacheManager makeInstance(long maximumSize){
        return makeInstance(maximumSize,ITableCache.DEFAULT_DURATION,ITableCache.DEFAULT_TIME_UNIT);
    }
    /** instance of {@link FeatureCache} */
    private final FeatureCache cache;
    /** constructor<br>
     * @see {@link FeatureCache#FeatureCache(UpdateStrategy ,long , long , TimeUnit )}
     */
    protected FeatureCacheManager(UpdateStrategy updateStragey,long maximumSize, long duration, TimeUnit unit) {
        cache = new FeatureCache(updateStragey,maximumSize,duration,unit);
        cache.registerListener();
    }
    
    @Override
    protected FaceCacheManager instanceOfFaceManager(){
        return FaceCacheManager.getInstance();
    }
    @Override
    protected LogCacheManager instanceOfLogManager(){
        return LogCacheManager.getInstance();
    }
    @Override
    protected PersonCacheManager instanceOfPersonManager(){
        return PersonCacheManager.getInstance();
    }
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    //1.1 override IFeatureManager

    @Override 
    public FeatureBean loadByPrimaryKeyChecked(String md5) throws ObjectRetrievalException
    {
        if(null == md5){
           throw new ObjectRetrievalException(new NullPointerException());
        }
        try{
            return cache.getBean(md5);
        }catch(ExecutionException ee){
            try{
                throw ee.getCause();
            }catch(ObjectRetrievalException oe){
                throw oe;
            } catch (WrapDAOException we) {
                throw we;
            } catch (RuntimeException re) {
                throw re;
            }catch (Throwable e) {
                throw new RuntimeException(ee);
            }
        }catch(UncheckedExecutionException ue){
            try{
                throw ue.getCause();
            }catch(ObjectRetrievalException oe){
                throw oe;
            } catch (WrapDAOException we) {
                throw we;
            } catch (RuntimeException re) {
                throw re;
            }catch (Throwable e) {
                throw new RuntimeException(ue);
            }
        }
    }
    //1.4 override IFeatureManager

    @Override 
    public boolean existsPrimaryKey(String md5){
        return null != loadByPrimaryKey(md5);
    }
    private class CacheAction implements Action<FeatureBean>{
        final Action<FeatureBean> action;
        CacheAction(Action<FeatureBean>action){
            this.action = action;
        }
        @Override
        public void call(FeatureBean bean) {
            if(null != action){
                action.call(bean);
            }
            cache.update(bean);
        }
        @Override
        public FeatureBean getBean() {
            return null == action?null:action.getBean();
        }
    }
    //20-5

    @Override
    public int loadUsingTemplate(FeatureBean bean, int[] fieldList, int startRow, int numRows,int searchType, Action<FeatureBean> action){
        if(null == fieldList ){
            action = new CacheAction(action);
        }
        return super.loadUsingTemplate(bean,fieldList,startRow,numRows,searchType,action);
    }


}
