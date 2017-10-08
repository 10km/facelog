// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.db.mysql;

import java.util.concurrent.Callable;

import net.gdface.facelog.db.Constant;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.IBeanConverter;
import net.gdface.facelog.db.IDbConverter;
import net.gdface.facelog.db.TableManager;
import net.gdface.facelog.db.IFeatureManager;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.WrapDAOException;

import net.gdface.facelog.dborm.exception.DAOException;
import net.gdface.facelog.dborm.face.FlFeatureManager;
import net.gdface.facelog.dborm.face.FlFeatureBean;

/**
 * Handles database calls (save, load, count, etc...) for the fl_feature table.<br>
 * all {@link DAOException} be wrapped as {@link WrapDAOException} to throw.
 * @author guyadong
 */
public class FeatureManager extends TableManager.Adapter<FeatureBean> implements IFeatureManager
{
    private FlFeatureManager nativeManager = FlFeatureManager.getInstance();
    private IDbConverter<net.gdface.facelog.dborm.device.FlDeviceBean,net.gdface.facelog.dborm.face.FlFaceBean,net.gdface.facelog.dborm.face.FlFeatureBean,net.gdface.facelog.dborm.image.FlImageBean,net.gdface.facelog.dborm.log.FlLogBean,net.gdface.facelog.dborm.person.FlPersonBean,net.gdface.facelog.dborm.image.FlStoreBean,net.gdface.facelog.dborm.log.FlLogLightBean> dbConverter = DbConverter.INSTANCE;
    private IBeanConverter<FeatureBean,FlFeatureBean> beanConverter = dbConverter.getFeatureBeanConverter();
    private static FeatureManager singleton = new FeatureManager();
    protected FeatureManager(){}
    /**
    * @return table name
    */
    public String getTableName() {
        return this.nativeManager.getTableName();
    }

    /**
    * @return field names of table
    */
    public String getFields() {
        return this.nativeManager.getFields();
    }
    
    public String getFullFields() {
        return this.nativeManager.getFullFields();
    }
    
    /**
    * @return primarykeyNames
    */
    public String[] getPrimarykeyNames() {
        return this.nativeManager.getPrimarykeyNames();
    }
    
    /**
     * Get the {@link FeatureManager} singleton.
     *
     * @return {@link FeatureManager}
     */
    public static FeatureManager getInstance()
    {
        return singleton;
    }
   
    @Override
    protected Class<FeatureBean> _beanType(){
        return FeatureBean.class;
    }
    
    public IDbConverter<net.gdface.facelog.dborm.device.FlDeviceBean,net.gdface.facelog.dborm.face.FlFaceBean,net.gdface.facelog.dborm.face.FlFeatureBean,net.gdface.facelog.dborm.image.FlImageBean,net.gdface.facelog.dborm.log.FlLogBean,net.gdface.facelog.dborm.person.FlPersonBean,net.gdface.facelog.dborm.image.FlStoreBean,net.gdface.facelog.dborm.log.FlLogLightBean> getDbConverter() {
        return dbConverter;
    }

    /**
     * set  {@link IDbConverter} as converter used by manager.<br>
     * throw {@link NullPointerException} if {@code dbConverter} is null
     * @param dbConverter
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public synchronized void setDbConverter(IDbConverter dbConverter) {
        if( null == dbConverter)
            throw new NullPointerException();
        this.dbConverter = dbConverter;
        this.beanConverter = this.dbConverter.getFeatureBeanConverter();
    }
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    //1 override IFeatureManager
    @Override 
    public FeatureBean loadByPrimaryKey(String md5)
    {
        try{
            return this.beanConverter.fromRight(nativeManager.loadByPrimaryKey(md5));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //1.2
    @Override
    public FeatureBean loadByPrimaryKey(FeatureBean bean)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByPrimaryKey(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //1.3
    @Override
    public FeatureBean loadByPrimaryKey(Object ...keys){
        if(keys.length != 1 )
            throw new IllegalArgumentException("argument number mismatch with primary key number");
        if(! (keys[0] instanceof String))
            throw new IllegalArgumentException("invalid type for the No.1 argument,expected type:String");
        return loadByPrimaryKey((String)keys[0]);
    }
    
    //1.4 override IFeatureManager
    @Override 
    public boolean existsPrimaryKey(String md5)
    {
        try{
            return nativeManager.existsPrimaryKey(md5 );
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    //1.6
    @Override
    public boolean existsByPrimaryKey(FeatureBean bean)
    {
        return null == bean ? false : existsPrimaryKey(bean.getMd5());
    }
    //1.7
    @Override
    public FeatureBean checkDuplicate(FeatureBean bean){
        if(null != bean)
            checkDuplicate(bean.getMd5());            
        return bean;   
    }
    //1.4.1 override IFeatureManager
    @Override 
    public String checkDuplicate(String md5){
        try{
            return this.nativeManager.checkDuplicate(md5);
        }catch(DAOException e){
            throw new WrapDAOException(e);
        }
    }
    //1.8 override IFeatureManager
    @Override 
    public java.util.List<FeatureBean> loadByPrimaryKey(String... keys){
        if(null == keys)return new java.util.ArrayList<FeatureBean>();
        java.util.ArrayList<FeatureBean> list = new java.util.ArrayList<FeatureBean>(keys.length);
        for(int i = 0 ;i< keys.length;++i){
            list.add(loadByPrimaryKey(keys[i]));
        }
        return list;
    }
    //1.9 override IFeatureManager
    @Override 
    public java.util.List<FeatureBean> loadByPrimaryKey(java.util.Collection<String> keys){
        if(null == keys )return new java.util.ArrayList<FeatureBean>();
        java.util.ArrayList<FeatureBean> list = new java.util.ArrayList<FeatureBean>(keys.size());
        if(keys instanceof java.util.List){
            for(String key: keys){
                list.add(loadByPrimaryKey(key));
            }
        }else{
            FeatureBean bean;
            for(String key: keys){
                if(null != (bean = loadByPrimaryKey(key)))
                    list.add(bean);
            }
        }
        return list;
    }
    //2 override IFeatureManager
    @Override 
    public int deleteByPrimaryKey(String md5)
    {
        try
        {
            return nativeManager.deleteByPrimaryKey(md5);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    //2
    @Override
    public int delete(FeatureBean bean){
        try
        {
            return nativeManager.delete(this.beanConverter.toRight(bean));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }   
    }
    //2.1
    @Override
    public int deleteByPrimaryKey(Object ...keys){
        if(keys.length != 1 )
            throw new IllegalArgumentException("argument number mismatch with primary key number");
        if(! (keys[0] instanceof String))
            throw new IllegalArgumentException("invalid type for the No.1 argument,expected type:String");
        return deleteByPrimaryKey((String)keys[0]);
    }
    //2.2 override IFeatureManager
    @Override 
    public int deleteByPrimaryKey(String... keys){
        if(null == keys)return 0;
        int count = 0;
        for(String key:keys){
            count += deleteByPrimaryKey(key);
        }
        return count;
    }
    //2.3 override IFeatureManager
    @Override 
    public int deleteByPrimaryKey(java.util.Collection<String> keys){
        if(null == keys)return 0;
        int count = 0;
        for(String key :keys){
            count += deleteByPrimaryKey(key);
        }
        return count;
    }
    //2.4 override IFeatureManager
    @Override 
    public int delete(FeatureBean... beans){
        if(null == beans)return 0;
        int count = 0;
        for(FeatureBean bean :beans){
            count += delete(bean);
        }
        return count;
    }
    //2.5 override IFeatureManager
    @Override 
    public int delete(java.util.Collection<FeatureBean> beans){
        if(null == beans)return 0;
        int count = 0;
        for(FeatureBean bean :beans){
            count += delete(bean);
        }
        return count;
    }
 
    //////////////////////////////////////
    // IMPORT KEY GENERIC METHOD
    //////////////////////////////////////
    
    private static final Class<?>[] importedBeanTypes = new Class<?>[]{FaceBean.class,LogBean.class};

    /**
     * @see #getImportedBeansAsList(FeatureBean,int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends net.gdface.facelog.db.BaseBean<?>> T[] getImportedBeans(FeatureBean bean, int ikIndex){
        return getImportedBeansAsList(bean, ikIndex).toArray((T[])java.lang.reflect.Array.newInstance(importedBeanTypes[ikIndex],0));
    }
    
    /**
     * Retrieves imported T objects by ikIndex.<br>
     * @param <T>
     * <ul>
     *     <li> {@link Constant#FL_FEATURE_IK_FL_FACE_FEATURE_MD5} -> {@link FaceBean}</li>
     *     <li> {@link Constant#FL_FEATURE_IK_FL_LOG_VERIFY_FEATURE} -> {@link LogBean}</li>
     * </ul>
     * @param bean the {@link FeatureBean} object to use
     * @param ikIndex valid values: {@link Constant#FL_FEATURE_IK_FL_FACE_FEATURE_MD5},{@link Constant#FL_FEATURE_IK_FL_LOG_VERIFY_FEATURE}
     * @return the associated T beans or {@code null} if {@code bean} is {@code null}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends net.gdface.facelog.db.BaseBean<?>> java.util.List<T> getImportedBeansAsList(FeatureBean bean,int ikIndex){
        switch(ikIndex){
        case FL_FEATURE_IK_FL_FACE_FEATURE_MD5:
            return (java.util.List<T>)this.getFaceBeansByFeatureMd5AsList(bean);
        case FL_FEATURE_IK_FL_LOG_VERIFY_FEATURE:
            return (java.util.List<T>)this.getLogBeansByVerifyFeatureAsList(bean);
        }
        throw new IllegalArgumentException(String.format("invalid ikIndex %d", ikIndex));
    }
    /**
     * Set the T objects as imported beans of bean object by ikIndex.<br>
     * @param <T>
     * 
     * <ul>
     *     <li> {@link Constant#FL_FEATURE_IK_FL_FACE_FEATURE_MD5} -> {@link FaceBean}</li>
     *     <li> {@link Constant#FL_FEATURE_IK_FL_LOG_VERIFY_FEATURE} -> {@link LogBean}</li>
     * </ul>
     * @param bean the {@link FeatureBean} object to use
     * @param importedBeans the FlLogBean array to associate to the {@link FeatureBean}
     * @param ikIndex valid values: {@link Constant#FL_FEATURE_IK_FL_FACE_FEATURE_MD5},{@link Constant#FL_FEATURE_IK_FL_LOG_VERIFY_FEATURE}
     * @return importedBeans always
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends net.gdface.facelog.db.BaseBean<?>> T[] setImportedBeans(FeatureBean bean,T[] importedBeans,int ikIndex){
        switch(ikIndex){
        case FL_FEATURE_IK_FL_FACE_FEATURE_MD5:
            return (T[])setFaceBeansByFeatureMd5(bean,(FaceBean[])importedBeans);
        case FL_FEATURE_IK_FL_LOG_VERIFY_FEATURE:
            return (T[])setLogBeansByVerifyFeature(bean,(LogBean[])importedBeans);
        }
        throw new IllegalArgumentException(String.format("invalid ikIndex %d", ikIndex));
    }
    /**
     * Set the importedBeans associates to the bean by ikIndex<br>
     * @param <T>
     * <ul>
     *     <li> {@link Constant#FL_FEATURE_IK_FL_FACE_FEATURE_MD5} -> {@link FaceBean}</li>
     *     <li> {@link Constant#FL_FEATURE_IK_FL_LOG_VERIFY_FEATURE} -> {@link LogBean}</li>
     * </ul>
     * @param bean the {@link FeatureBean} object to use
     * @param importedBeans the <T> object to associate to the {@link FeatureBean}
     * @param ikIndex valid values: {@link Constant#FL_FEATURE_IK_FL_FACE_FEATURE_MD5},{@link Constant#FL_FEATURE_IK_FL_LOG_VERIFY_FEATURE}
     * @return importedBeans always
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends net.gdface.facelog.db.BaseBean<?>,C extends java.util.Collection<T>> C setImportedBeans(FeatureBean bean,C importedBeans,int ikIndex){
        switch(ikIndex){
        case FL_FEATURE_IK_FL_FACE_FEATURE_MD5:
            return (C)setFaceBeansByFeatureMd5(bean,(java.util.Collection<FaceBean>)importedBeans);
        case FL_FEATURE_IK_FL_LOG_VERIFY_FEATURE:
            return (C)setLogBeansByVerifyFeature(bean,(java.util.Collection<LogBean>)importedBeans);
        }
        throw new IllegalArgumentException(String.format("invalid ikIndex %d", ikIndex));
    }
 

    //////////////////////////////////////
    // GET/SET IMPORTED KEY BEAN METHOD
    //////////////////////////////////////
    //3.1 GET IMPORTED override IFeatureManager
    @Override 
    public FaceBean[] getFaceBeansByFeatureMd5(FeatureBean bean)
    {
        return this.getFaceBeansByFeatureMd5AsList(bean).toArray(new FaceBean[0]);
    }
    //3.1.2 GET IMPORTED override IFeatureManager
    @Override
    public FaceBean[] getFaceBeansByFeatureMd5(String featureMd5)
    {
        FeatureBean bean = new FeatureBean();
        bean.setMd5(featureMd5);
        return getFaceBeansByFeatureMd5(bean);
    }
    //3.2 GET IMPORTED override IFeatureManager
    @Override 
    public java.util.List<FaceBean> getFaceBeansByFeatureMd5AsList(FeatureBean bean)
    {
        try {
            return this.dbConverter.getFaceBeanConverter().fromRight(nativeManager.getFaceBeansByFeatureMd5AsList( this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    //3.2.2 GET IMPORTED override IFeatureManager
    @Override
    public java.util.List<FaceBean> getFaceBeansByFeatureMd5AsList(String featureMd5)
    {
         FeatureBean bean = new FeatureBean();
        bean.setMd5(featureMd5);
        return getFaceBeansByFeatureMd5AsList(bean);
    }
    //3.2.3 DELETE IMPORTED override IFeatureManager
    @Override
    public int deleteFaceBeansByFeatureMd5(String featureMd5)
    {
        java.util.List<FaceBean> list =getFaceBeansByFeatureMd5AsList(featureMd5);
        return FaceManager.getInstance().delete(list);
    }
    //3.3 SET IMPORTED override IFeatureManager
    @Override 
    public FaceBean[] setFaceBeansByFeatureMd5(FeatureBean bean , FaceBean[] importedBeans)
    {
        if(null != importedBeans){
            for( FaceBean importBean : importedBeans ){
                FaceManager.getInstance().setReferencedByFeatureMd5(importBean , bean);
            }
        }
        return importedBeans;
    }

    //3.4 SET IMPORTED override IFeatureManager
    @Override 
    public <C extends java.util.Collection<FaceBean>> C setFaceBeansByFeatureMd5(FeatureBean bean , C importedBeans)
    {
        if(null != importedBeans){
            for( FaceBean importBean : importedBeans ){
                FaceManager.getInstance().setReferencedByFeatureMd5(importBean , bean);
            }
        }
        return importedBeans;
    }

    //3.1 GET IMPORTED override IFeatureManager
    @Override 
    public LogBean[] getLogBeansByVerifyFeature(FeatureBean bean)
    {
        return this.getLogBeansByVerifyFeatureAsList(bean).toArray(new LogBean[0]);
    }
    //3.1.2 GET IMPORTED override IFeatureManager
    @Override
    public LogBean[] getLogBeansByVerifyFeature(String featureMd5)
    {
        FeatureBean bean = new FeatureBean();
        bean.setMd5(featureMd5);
        return getLogBeansByVerifyFeature(bean);
    }
    //3.2 GET IMPORTED override IFeatureManager
    @Override 
    public java.util.List<LogBean> getLogBeansByVerifyFeatureAsList(FeatureBean bean)
    {
        try {
            return this.dbConverter.getLogBeanConverter().fromRight(nativeManager.getLogBeansByVerifyFeatureAsList( this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    //3.2.2 GET IMPORTED override IFeatureManager
    @Override
    public java.util.List<LogBean> getLogBeansByVerifyFeatureAsList(String featureMd5)
    {
         FeatureBean bean = new FeatureBean();
        bean.setMd5(featureMd5);
        return getLogBeansByVerifyFeatureAsList(bean);
    }
    //3.2.3 DELETE IMPORTED override IFeatureManager
    @Override
    public int deleteLogBeansByVerifyFeature(String featureMd5)
    {
        java.util.List<LogBean> list =getLogBeansByVerifyFeatureAsList(featureMd5);
        return LogManager.getInstance().delete(list);
    }
    //3.3 SET IMPORTED override IFeatureManager
    @Override 
    public LogBean[] setLogBeansByVerifyFeature(FeatureBean bean , LogBean[] importedBeans)
    {
        if(null != importedBeans){
            for( LogBean importBean : importedBeans ){
                LogManager.getInstance().setReferencedByVerifyFeature(importBean , bean);
            }
        }
        return importedBeans;
    }

    //3.4 SET IMPORTED override IFeatureManager
    @Override 
    public <C extends java.util.Collection<LogBean>> C setLogBeansByVerifyFeature(FeatureBean bean , C importedBeans)
    {
        if(null != importedBeans){
            for( LogBean importBean : importedBeans ){
                LogManager.getInstance().setReferencedByVerifyFeature(importBean , bean);
            }
        }
        return importedBeans;
    }



    //3.5 SYNC SAVE override IFeatureManager
    @Override  
    public FeatureBean save(FeatureBean bean
        , PersonBean refPersonByPersonId 
        , FaceBean[] impFaceByFeatureMd5 , LogBean[] impLogByVerifyFeature )
    {
        if(null == bean) return null;
        if(null != refPersonByPersonId)
            this.setReferencedByPersonId(bean,refPersonByPersonId);
        bean = this.save( bean );
        this.setFaceBeansByFeatureMd5(bean,impFaceByFeatureMd5);
        FaceManager.getInstance().save( impFaceByFeatureMd5 );
        this.setLogBeansByVerifyFeature(bean,impLogByVerifyFeature);
        LogManager.getInstance().save( impLogByVerifyFeature );
        return bean;
    } 

    //3.6 SYNC SAVE AS TRANSACTION override IFeatureManager
    @Override 
    public FeatureBean saveAsTransaction(final FeatureBean bean
        ,final PersonBean refPersonByPersonId 
        ,final FaceBean[] impFaceByFeatureMd5 ,final LogBean[] impLogByVerifyFeature )
    {
        return this.runAsTransaction(new Callable<FeatureBean>(){
            @Override
            public FeatureBean call() throws Exception {
                return save(bean , refPersonByPersonId , impFaceByFeatureMd5 , impLogByVerifyFeature );
            }});
    }
    //3.7 SYNC SAVE override IFeatureManager
    @Override 
    public FeatureBean save(FeatureBean bean
        , PersonBean refPersonByPersonId 
        , java.util.Collection<FaceBean> impFaceByFeatureMd5 , java.util.Collection<LogBean> impLogByVerifyFeature )
    {
        if(null == bean) return null;
        this.setReferencedByPersonId(bean,refPersonByPersonId);
        bean = this.save( bean );
        this.setFaceBeansByFeatureMd5(bean,impFaceByFeatureMd5);
        FaceManager.getInstance().save( impFaceByFeatureMd5 );
        this.setLogBeansByVerifyFeature(bean,impLogByVerifyFeature);
        LogManager.getInstance().save( impLogByVerifyFeature );
        return bean;
    }   

    //3.8 SYNC SAVE AS TRANSACTION override IFeatureManager
    @Override 
    public FeatureBean saveAsTransaction(final FeatureBean bean
        ,final PersonBean refPersonByPersonId 
        ,final  java.util.Collection<FaceBean> impFaceByFeatureMd5 ,final  java.util.Collection<LogBean> impLogByVerifyFeature )
    {
        return this.runAsTransaction(new Callable<FeatureBean>(){
            @Override
            public FeatureBean call() throws Exception {
                return save(bean , refPersonByPersonId , impFaceByFeatureMd5 , impLogByVerifyFeature );
            }});
    }
     /**
     * Save the {@link FeatureBean} bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link FeatureBean} bean to be saved
     * @param args referenced beans or imported beans<br>
     *      see also {@link #save(FeatureBean , PersonBean , FaceBean[] , LogBean[] )}
     * @return the inserted or updated {@link FeatureBean} bean
     */
    //3.9 SYNC SAVE 
    @Override
    public FeatureBean save(FeatureBean bean,Object ...args) 
    {
        if(args.length > 3)
            throw new IllegalArgumentException("too many dynamic arguments,max dynamic arguments number: 3");
        if( args.length > 0 && null != args[0] && !(args[0] instanceof PersonBean)){
            throw new IllegalArgumentException("invalid type for the No.1 dynamic argument,expected type:PersonBean");
        }
        if( args.length > 1 && null != args[1] && !(args[1] instanceof FaceBean[])){
            throw new IllegalArgumentException("invalid type for the No.2 argument,expected type:FaceBean[]");
        }
        if( args.length > 2 && null != args[2] && !(args[2] instanceof LogBean[])){
            throw new IllegalArgumentException("invalid type for the No.3 argument,expected type:LogBean[]");
        }
        return save(bean,(args.length < 1 || null == args[0])?null:(PersonBean)args[0],(args.length < 2 || null == args[1])?null:(FaceBean[])args[1],(args.length < 3 || null == args[2])?null:(LogBean[])args[2]);
    } 

    /**
     * Save the {@link FeatureBean} bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link FeatureBean} bean to be saved
     * @param args referenced beans or imported beans<br>
     *      see also {@link #save(FeatureBean , PersonBean , java.util.Collection , java.util.Collection )}
     * @return the inserted or updated {@link FeatureBean} bean
     */
    //3.10 SYNC SAVE 
    @SuppressWarnings("unchecked")
    @Override
    public FeatureBean saveCollection(FeatureBean bean,Object ...inputs)
    {
        if(inputs.length > 3)
            throw new IllegalArgumentException("too many dynamic arguments,max dynamic arguments number: 3");
        Object[] args = new Object[3];
        System.arraycopy(inputs,0,args,0,3);
        if( args.length > 0 && null != args[0] && !(args[0] instanceof PersonBean)){
            throw new IllegalArgumentException("invalid type for the No.1 dynamic argument,expected type:PersonBean");
        }
        if( args.length > 1 && null != args[1] && !(args[1] instanceof java.util.Collection)){
            throw new IllegalArgumentException("invalid type for the No.2 argument,expected type:java.util.Collection<FaceBean>");
        }
        if( args.length > 2 && null != args[2] && !(args[2] instanceof java.util.Collection)){
            throw new IllegalArgumentException("invalid type for the No.3 argument,expected type:java.util.Collection<LogBean>");
        }
        return save(bean,null == args[0]?null:(PersonBean)args[0],null == args[1]?null:(java.util.Collection<FaceBean>)args[1],null == args[2]?null:(java.util.Collection<LogBean>)args[2]);
    }

     //////////////////////////////////////
    // FOREIGN KEY GENERIC METHOD
    //////////////////////////////////////

    /**
     * Retrieves the bean object referenced by fkIndex.<br>
     * @param <T>
     * <ul>
     *     <li> {@link Constant#FL_FEATURE_FK_PERSON_ID} -> {@link PersonBean}</li>
     * </ul>
     * @param bean the {@link FeatureBean} object to use
     * @param fkIndex valid values: <br>
     *        {@link Constant#FL_FEATURE_FK_PERSON_ID}
     * @return the associated <T> bean or {@code null} if {@code bean} or {@code beanToSet} is {@code null}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends net.gdface.facelog.db.BaseBean<?>> T getReferencedBean(FeatureBean bean,int fkIndex){
        switch(fkIndex){
        case FL_FEATURE_FK_PERSON_ID:
            return  (T)this.getReferencedByPersonId(bean);
        }
        throw new IllegalArgumentException(String.format("invalid fkIndex %d", fkIndex));
    }
    /**
     * Associates the {@link FeatureBean} object to the bean object by fkIndex field.<br>
     * 
     * @param <T> see also {@link #getReferencedBean(FeatureBean,int)}
     * @param bean the {@link FeatureBean} object to use
     * @param beanToSet the <T> object to associate to the {@link FeatureBean}
     * @param fkIndex valid values: see also {@link #getReferencedBean(FeatureBean,int)}
     * @return always beanToSet saved
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends net.gdface.facelog.db.BaseBean<?>> T setReferencedBean(FeatureBean bean,T beanToSet,int fkIndex){
        switch(fkIndex){
        case FL_FEATURE_FK_PERSON_ID:
            return  (T)this.setReferencedByPersonId(bean, (PersonBean)beanToSet);
        }
        throw new IllegalArgumentException(String.format("invalid fkIndex %d", fkIndex));
    }
    
    //////////////////////////////////////
    // GET/SET FOREIGN KEY BEAN METHOD
    //////////////////////////////////////


    //5.1 GET REFERENCED VALUE override IFeatureManager
    @Override 
    public PersonBean getReferencedByPersonId(FeatureBean bean)
    {
        try{
            return this.dbConverter.getPersonBeanConverter().fromRight(this.nativeManager.getReferencedByPersonId(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
        
    }

    //5.2 SET REFERENCED override IFeatureManager
    @Override 
    public PersonBean setReferencedByPersonId(FeatureBean bean, PersonBean beanToSet)
    {
        try{
            FlFeatureBean nativeBean = this.beanConverter.toRight(bean);
            IBeanConverter<PersonBean,net.gdface.facelog.dborm.person.FlPersonBean> foreignConverter = this.dbConverter.getPersonBeanConverter();
            net.gdface.facelog.dborm.person.FlPersonBean foreignNativeBean = foreignConverter.toRight(beanToSet);
            this.nativeManager.setReferencedByPersonId(nativeBean,foreignNativeBean);
            this.beanConverter.fromRight(bean, nativeBean);
            foreignConverter.fromRight(beanToSet,foreignNativeBean);
            return beanToSet;
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //////////////////////////////////////
    // SQL 'WHERE' METHOD
    //////////////////////////////////////

    //11
    @Override
    public int deleteByWhere(String where)
    {
        try{
            return this.nativeManager.deleteByWhere(where);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //_____________________________________________________________________
    //
    // SAVE
    //_____________________________________________________________________

    //13
    @Override
    protected FeatureBean insert(FeatureBean bean)
    {
        try{
            return this.beanConverter.fromRight(bean,this.nativeManager.insert(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //14
    @Override
    protected FeatureBean update(FeatureBean bean)
    {
        try{
            return this.beanConverter.fromRight(bean,this.nativeManager.update(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //_____________________________________________________________________
    //
    // USING TEMPLATE
    //_____________________________________________________________________
    //18
    @Override
    public FeatureBean loadUniqueUsingTemplate(FeatureBean bean)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadUniqueUsingTemplate(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
     }

    //20-5
    @Override
    public int loadUsingTemplate(FeatureBean bean, int[] fieldList, int startRow, int numRows,int searchType, Action<FeatureBean> action)
    {
        try {
            return this.nativeManager.loadUsingTemplate(this.beanConverter.toRight(bean),fieldList,startRow,numRows,searchType,this.toNative(action));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //21
    @Override
    public int deleteUsingTemplate(FeatureBean bean)
    {
        try{
            return this.nativeManager.deleteUsingTemplate(this.beanConverter.toRight(bean));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }


    //_____________________________________________________________________
    //
    // USING INDICES
    //_____________________________________________________________________

     // override IFeatureManager
    @Override 
    public FeatureBean[] loadByIndexPersonId(Integer personId)
    {
        return this.loadByIndexPersonIdAsList(personId).toArray(new FeatureBean[0]);
    }
    
    // override IFeatureManager
    @Override 
    public java.util.List<FeatureBean> loadByIndexPersonIdAsList(Integer personId)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByIndexPersonIdAsList(personId));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    // override IFeatureManager
    @Override 
    public int deleteByIndexPersonId(Integer personId)
    {
        try{
            return this.nativeManager.deleteByIndexPersonId(personId);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
    
    /**
     * Retrieves a list of FeatureBean using the index specified by keyIndex.
     * @param keyIndex valid values: <br>
     *        {@link Constant#FL_FEATURE_INDEX_PERSON_ID}
     * @param keys key values of index
     * @return a list of FeatureBean
     */
    @Override
    public java.util.List<FeatureBean> loadByIndexAsList(int keyIndex,Object ...keys)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByIndexAsList(keyIndex,keys));
        }catch(DAOException e){
            throw new WrapDAOException(e);
        }
    }
    
    /**
     * Deletes rows using key.
     * @param keyIndex valid values: <br>
     *        {@link Constant#FL_FEATURE_INDEX_PERSON_ID}
     * @param keys key values of index
     * @return the number of deleted objects
     */
    @Override
    public int deleteByIndex(int keyIndex,Object ...keys)
    {
        try{
            return this.nativeManager.deleteByIndex(keyIndex,keys);
        }catch(DAOException e){
            throw new WrapDAOException(e);
        }
    }

    //_____________________________________________________________________
    //
    // COUNT
    //_____________________________________________________________________
    //25
    @Override
    public int countWhere(String where)
    {
        try{
            return this.nativeManager.countWhere(where);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //20
    @Override
    public int countUsingTemplate(FeatureBean bean, int searchType)
    {
        try{
            return this.nativeManager.countUsingTemplate(this.beanConverter.toRight(bean),searchType);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }


    //_____________________________________________________________________
    //
    // LISTENER
    //_____________________________________________________________________

    //35
    @Override
    public void registerListener(TableListener<FeatureBean> listener)
    {
        this.nativeManager.registerListener(this.toNative(listener));
    }

    //36
    @Override
    public void unregisterListener(TableListener<FeatureBean> listener)
    {
        this.nativeManager.unregisterListener(this.toNative(listener));
    }
    
    private net.gdface.facelog.dborm.TableListener<FlFeatureBean> toNative(final TableListener<FeatureBean> listener) {
        return null == listener ?null:new net.gdface.facelog.dborm.TableListener<FlFeatureBean> (){

            @Override
            public void beforeInsert(FlFeatureBean bean) throws DAOException {
                listener.beforeInsert(FeatureManager.this.beanConverter.fromRight(bean));                
            }

            @Override
            public void afterInsert(FlFeatureBean bean) throws DAOException {
                listener.afterInsert(FeatureManager.this.beanConverter.fromRight(bean));
                
            }

            @Override
            public void beforeUpdate(FlFeatureBean bean) throws DAOException {
                listener.beforeUpdate(FeatureManager.this.beanConverter.fromRight(bean));
                
            }

            @Override
            public void afterUpdate(FlFeatureBean bean) throws DAOException {
                listener.afterUpdate(FeatureManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public void beforeDelete(FlFeatureBean bean) throws DAOException {
                listener.beforeDelete(FeatureManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public void afterDelete(FlFeatureBean bean) throws DAOException {
                listener.afterDelete(FeatureManager.this.beanConverter.fromRight(bean));
            }};
    }

    //_____________________________________________________________________
    //
    // UTILS
    //_____________________________________________________________________

    //43
    @Override
    public boolean isPrimaryKey(String column){
        return this.nativeManager.isPrimaryKey(column);
    }
    
    @Override
    public int loadBySqlForAction(String sql, Object[] argList, int[] fieldList,int startRow, int numRows,Action<FeatureBean> action){
        try{
            return this.nativeManager.loadBySqlForAction(sql,argList,fieldList,startRow,numRows,this.toNative(action));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
    @Override
    public <T>T runAsTransaction(Callable<T> fun) {
        try{
            return this.nativeManager.runAsTransaction(fun);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
    private net.gdface.facelog.dborm.TableManager.Action<FlFeatureBean> toNative(final Action<FeatureBean> action){
        if(null == action)
            throw new NullPointerException();
        return new net.gdface.facelog.dborm.TableManager.Action<FlFeatureBean>(){

            @Override
            public void call(FlFeatureBean bean) {
                action.call(FeatureManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public FlFeatureBean getBean() {
                return  FeatureManager.this.beanConverter.toRight(action.getBean());
            }};
    }
    
    //45 override IFeatureManager
    @Override 
    public java.util.List<String> toPrimaryKeyList(FeatureBean... array){        
        if(null == array)return new java.util.ArrayList<String>();
        java.util.ArrayList<String> list = new java.util.ArrayList<String>(array.length);
        for(FeatureBean bean:array){
            list.add(null == bean ? null : bean.getMd5());
        }
        return list;
    }
    //46 override IFeatureManager
    @Override 
    public java.util.List<String> toPrimaryKeyList(java.util.Collection<FeatureBean> collection){        
        if(null == collection)return new java.util.ArrayList<String>();
        java.util.ArrayList<String> list = new java.util.ArrayList<String>(collection.size());
        for(FeatureBean bean:collection){
            list.add(null == bean ? null : bean.getMd5());
        }
        return list;
    }
}
