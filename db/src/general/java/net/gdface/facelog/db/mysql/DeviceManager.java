// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.db.mysql;

import java.util.concurrent.Callable;

import net.gdface.facelog.db.Constant;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.IBeanConverter;
import net.gdface.facelog.db.IDbConverter;
import net.gdface.facelog.db.TableManager;
import net.gdface.facelog.db.IDeviceManager;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.WrapDAOException;

import net.gdface.facelog.dborm.exception.DAOException;
import net.gdface.facelog.dborm.device.FlDeviceManager;
import net.gdface.facelog.dborm.device.FlDeviceBean;

/**
 * Handles database calls (save, load, count, etc...) for the fl_device table.<br>
 * all {@link DAOException} be wrapped as {@link WrapDAOException} to throw.
 * @author guyadong
 */
public class DeviceManager extends TableManager.Adapter<DeviceBean> implements IDeviceManager
{
    private FlDeviceManager nativeManager = FlDeviceManager.getInstance();
    private IDbConverter<net.gdface.facelog.dborm.device.FlDeviceBean,net.gdface.facelog.dborm.face.FlFaceBean,net.gdface.facelog.dborm.face.FlFeatureBean,net.gdface.facelog.dborm.image.FlImageBean,net.gdface.facelog.dborm.log.FlLogBean,net.gdface.facelog.dborm.person.FlPersonBean,net.gdface.facelog.dborm.image.FlStoreBean,net.gdface.facelog.dborm.log.FlLogLightBean> dbConverter = DbConverter.INSTANCE;
    private IBeanConverter<DeviceBean,FlDeviceBean> beanConverter = dbConverter.getDeviceBeanConverter();
    private static DeviceManager singleton = new DeviceManager();

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
     * Get the {@link DeviceManager} singleton.
     *
     * @return {@link DeviceManager}
     */
    public static DeviceManager getInstance()
    {
        return singleton;
    }
   
    @Override
    protected Class<DeviceBean> _beanType(){
        return DeviceBean.class;
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
        this.beanConverter = this.dbConverter.getDeviceBeanConverter();
    }
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    //1 override IDeviceManager
    @Override 
    public DeviceBean loadByPrimaryKey(Integer id)
    {
        try{
            return this.beanConverter.fromRight(nativeManager.loadByPrimaryKey(id));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //1.2
    @Override
    public DeviceBean loadByPrimaryKey(DeviceBean bean)
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
    public DeviceBean loadByPrimaryKey(Object ...keys){
        if(keys.length != 1 )
            throw new IllegalArgumentException("argument number mismatch with primary key number");
        if(! (keys[0] instanceof Integer))
            throw new IllegalArgumentException("invalid type for the No.1 argument,expected type:Integer");
        return loadByPrimaryKey((Integer)keys[0]);
    }
    
    //1.4 override IDeviceManager
    @Override 
    public boolean existsPrimaryKey(Integer id)
    {
        try{
            return nativeManager.existsPrimaryKey(id );
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    //1.6
    @Override
    public boolean existsByPrimaryKey(DeviceBean bean)
    {
        return null == bean ? false : existsPrimaryKey(bean.getId());
    }
    //1.7
    @Override
    public DeviceBean checkDuplicate(DeviceBean bean){
        if(null != bean)
            checkDuplicate(bean.getId());            
        return bean;   
    }
    //1.4.1 override IDeviceManager
    @Override 
    public Integer checkDuplicate(Integer id){
        try{
            return this.nativeManager.checkDuplicate(id);
        }catch(DAOException e){
            throw new WrapDAOException(e);
        }
    }
    //1.8 override IDeviceManager
    @Override 
    public java.util.List<DeviceBean> loadByPrimaryKey(int... keys){
        if(null == keys)return new java.util.ArrayList<DeviceBean>();
        java.util.ArrayList<DeviceBean> list = new java.util.ArrayList<DeviceBean>(keys.length);
        for(int i = 0 ;i< keys.length;++i){
            list.add(loadByPrimaryKey(keys[i]));
        }
        return list;
    }
    //1.9 override IDeviceManager
    @Override 
    public java.util.List<DeviceBean> loadByPrimaryKey(java.util.Collection<Integer> keys){
        if(null == keys )return new java.util.ArrayList<DeviceBean>();
        java.util.ArrayList<DeviceBean> list = new java.util.ArrayList<DeviceBean>(keys.size());
        if(keys instanceof java.util.List){
            for(Integer key: keys){
                list.add(loadByPrimaryKey(key));
            }
        }else{
            DeviceBean bean;
            for(Integer key: keys){
                if(null != (bean = loadByPrimaryKey(key)))
                    list.add(bean);
            }
        }
        return list;
    }
    //2 override IDeviceManager
    @Override 
    public int deleteByPrimaryKey(Integer id)
    {
        try
        {
            return nativeManager.deleteByPrimaryKey(id);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    //2
    @Override
    public int delete(DeviceBean bean){
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
        if(! (keys[0] instanceof Integer))
            throw new IllegalArgumentException("invalid type for the No.1 argument,expected type:Integer");
        return deleteByPrimaryKey((Integer)keys[0]);
    }
    //2.2 override IDeviceManager
    @Override 
    public int deleteByPrimaryKey(int... keys){
        if(null == keys)return 0;
        int count = 0;
        for(int key:keys){
            count += deleteByPrimaryKey(key);
        }
        return count;
    }
    //2.3 override IDeviceManager
    @Override 
    public int deleteByPrimaryKey(java.util.Collection<Integer> keys){
        if(null == keys)return 0;
        int count = 0;
        for(Integer key :keys){
            count += deleteByPrimaryKey(key);
        }
        return count;
    }
    //2.4 override IDeviceManager
    @Override 
    public int delete(DeviceBean... beans){
        if(null == beans)return 0;
        int count = 0;
        for(DeviceBean bean :beans){
            count += delete(bean);
        }
        return count;
    }
    //2.5 override IDeviceManager
    @Override 
    public int delete(java.util.Collection<DeviceBean> beans){
        if(null == beans)return 0;
        int count = 0;
        for(DeviceBean bean :beans){
            count += delete(bean);
        }
        return count;
    }
 
    //////////////////////////////////////
    // IMPORT KEY GENERIC METHOD
    //////////////////////////////////////
    
    private static final Class<?>[] importedBeanTypes = new Class<?>[]{ImageBean.class,LogBean.class};

    /**
     * @see #getImportedBeansAsList(DeviceBean,int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends net.gdface.facelog.db.BaseBean<?>> T[] getImportedBeans(DeviceBean bean, int ikIndex){
        return getImportedBeansAsList(bean, ikIndex).toArray((T[])java.lang.reflect.Array.newInstance(importedBeanTypes[ikIndex],0));
    }
    
    /**
     * Retrieves imported T objects by ikIndex.<br>
     * @param <T>
     * <ul>
     *     <li> {@link Constant#FL_DEVICE_IK_FL_IMAGE_DEVICE_ID} -> {@link ImageBean}</li>
     *     <li> {@link Constant#FL_DEVICE_IK_FL_LOG_DEVICE_ID} -> {@link LogBean}</li>
     * </ul>
     * @param bean the {@link DeviceBean} object to use
     * @param ikIndex valid values: {@link Constant#FL_DEVICE_IK_FL_IMAGE_DEVICE_ID},{@link Constant#FL_DEVICE_IK_FL_LOG_DEVICE_ID}
     * @return the associated T beans or {@code null} if {@code bean} is {@code null}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends net.gdface.facelog.db.BaseBean<?>> java.util.List<T> getImportedBeansAsList(DeviceBean bean,int ikIndex){
        switch(ikIndex){
        case FL_DEVICE_IK_FL_IMAGE_DEVICE_ID:
            return (java.util.List<T>)this.getImageBeansByDeviceIdAsList(bean);
        case FL_DEVICE_IK_FL_LOG_DEVICE_ID:
            return (java.util.List<T>)this.getLogBeansByDeviceIdAsList(bean);
        }
        throw new IllegalArgumentException(String.format("invalid ikIndex %d", ikIndex));
    }
    /**
     * Set the T objects as imported beans of bean object by ikIndex.<br>
     * @param <T>
     * 
     * <ul>
     *     <li> {@link Constant#FL_DEVICE_IK_FL_IMAGE_DEVICE_ID} -> {@link ImageBean}</li>
     *     <li> {@link Constant#FL_DEVICE_IK_FL_LOG_DEVICE_ID} -> {@link LogBean}</li>
     * </ul>
     * @param bean the {@link DeviceBean} object to use
     * @param importedBeans the FlLogBean array to associate to the {@link DeviceBean}
     * @param ikIndex valid values: {@link Constant#FL_DEVICE_IK_FL_IMAGE_DEVICE_ID},{@link Constant#FL_DEVICE_IK_FL_LOG_DEVICE_ID}
     * @return importedBeans always
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends net.gdface.facelog.db.BaseBean<?>> T[] setImportedBeans(DeviceBean bean,T[] importedBeans,int ikIndex){
        switch(ikIndex){
        case FL_DEVICE_IK_FL_IMAGE_DEVICE_ID:
            return (T[])setImageBeansByDeviceId(bean,(ImageBean[])importedBeans);
        case FL_DEVICE_IK_FL_LOG_DEVICE_ID:
            return (T[])setLogBeansByDeviceId(bean,(LogBean[])importedBeans);
        }
        throw new IllegalArgumentException(String.format("invalid ikIndex %d", ikIndex));
    }
    /**
     * Set the importedBeans associates to the bean by ikIndex<br>
     * @param <T>
     * <ul>
     *     <li> {@link Constant#FL_DEVICE_IK_FL_IMAGE_DEVICE_ID} -> {@link ImageBean}</li>
     *     <li> {@link Constant#FL_DEVICE_IK_FL_LOG_DEVICE_ID} -> {@link LogBean}</li>
     * </ul>
     * @param bean the {@link DeviceBean} object to use
     * @param importedBeans the <T> object to associate to the {@link DeviceBean}
     * @param ikIndex valid values: {@link Constant#FL_DEVICE_IK_FL_IMAGE_DEVICE_ID},{@link Constant#FL_DEVICE_IK_FL_LOG_DEVICE_ID}
     * @return importedBeans always
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends net.gdface.facelog.db.BaseBean<?>,C extends java.util.Collection<T>> C setImportedBeans(DeviceBean bean,C importedBeans,int ikIndex){
        switch(ikIndex){
        case FL_DEVICE_IK_FL_IMAGE_DEVICE_ID:
            return (C)setImageBeansByDeviceId(bean,(java.util.Collection<ImageBean>)importedBeans);
        case FL_DEVICE_IK_FL_LOG_DEVICE_ID:
            return (C)setLogBeansByDeviceId(bean,(java.util.Collection<LogBean>)importedBeans);
        }
        throw new IllegalArgumentException(String.format("invalid ikIndex %d", ikIndex));
    }
 

    //////////////////////////////////////
    // GET/SET IMPORTED KEY BEAN METHOD
    //////////////////////////////////////
    //3.1 GET IMPORTED override IDeviceManager
    @Override 
    public ImageBean[] getImageBeansByDeviceId(DeviceBean bean)
    {
        return this.getImageBeansByDeviceIdAsList(bean).toArray(new ImageBean[0]);
    }
    //3.1.2 GET IMPORTED override IDeviceManager
    @Override
    public ImageBean[] getImageBeansByDeviceId(Integer deviceId)
    {
        DeviceBean bean = new DeviceBean();
        bean.setId(deviceId);
        return getImageBeansByDeviceId(bean);
    }
    //3.2 GET IMPORTED override IDeviceManager
    @Override 
    public java.util.List<ImageBean> getImageBeansByDeviceIdAsList(DeviceBean bean)
    {
        try {
            return this.dbConverter.getImageBeanConverter().fromRight(nativeManager.getImageBeansByDeviceIdAsList( this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    //3.2.2 GET IMPORTED override IDeviceManager
    @Override
    public java.util.List<ImageBean> getImageBeansByDeviceIdAsList(Integer deviceId)
    {
         DeviceBean bean = new DeviceBean();
        bean.setId(deviceId);
        return getImageBeansByDeviceIdAsList(bean);
    }
    //3.2.3 DELETE IMPORTED override IDeviceManager
    @Override
    public int deleteImageBeansByDeviceId(Integer deviceId)
    {
        java.util.List<ImageBean> list =getImageBeansByDeviceIdAsList(deviceId);
        return ImageManager.getInstance().delete(list);
    }
    //3.3 SET IMPORTED override IDeviceManager
    @Override 
    public ImageBean[] setImageBeansByDeviceId(DeviceBean bean , ImageBean[] importedBeans)
    {
        if(null != importedBeans){
            for( ImageBean importBean : importedBeans ){
                ImageManager.getInstance().setReferencedByDeviceId(importBean , bean);
            }
        }
        return importedBeans;
    }

    //3.4 SET IMPORTED override IDeviceManager
    @Override 
    public <C extends java.util.Collection<ImageBean>> C setImageBeansByDeviceId(DeviceBean bean , C importedBeans)
    {
        if(null != importedBeans){
            for( ImageBean importBean : importedBeans ){
                ImageManager.getInstance().setReferencedByDeviceId(importBean , bean);
            }
        }
        return importedBeans;
    }

    //3.1 GET IMPORTED override IDeviceManager
    @Override 
    public LogBean[] getLogBeansByDeviceId(DeviceBean bean)
    {
        return this.getLogBeansByDeviceIdAsList(bean).toArray(new LogBean[0]);
    }
    //3.1.2 GET IMPORTED override IDeviceManager
    @Override
    public LogBean[] getLogBeansByDeviceId(Integer deviceId)
    {
        DeviceBean bean = new DeviceBean();
        bean.setId(deviceId);
        return getLogBeansByDeviceId(bean);
    }
    //3.2 GET IMPORTED override IDeviceManager
    @Override 
    public java.util.List<LogBean> getLogBeansByDeviceIdAsList(DeviceBean bean)
    {
        try {
            return this.dbConverter.getLogBeanConverter().fromRight(nativeManager.getLogBeansByDeviceIdAsList( this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    //3.2.2 GET IMPORTED override IDeviceManager
    @Override
    public java.util.List<LogBean> getLogBeansByDeviceIdAsList(Integer deviceId)
    {
         DeviceBean bean = new DeviceBean();
        bean.setId(deviceId);
        return getLogBeansByDeviceIdAsList(bean);
    }
    //3.2.3 DELETE IMPORTED override IDeviceManager
    @Override
    public int deleteLogBeansByDeviceId(Integer deviceId)
    {
        java.util.List<LogBean> list =getLogBeansByDeviceIdAsList(deviceId);
        return LogManager.getInstance().delete(list);
    }
    //3.3 SET IMPORTED override IDeviceManager
    @Override 
    public LogBean[] setLogBeansByDeviceId(DeviceBean bean , LogBean[] importedBeans)
    {
        if(null != importedBeans){
            for( LogBean importBean : importedBeans ){
                LogManager.getInstance().setReferencedByDeviceId(importBean , bean);
            }
        }
        return importedBeans;
    }

    //3.4 SET IMPORTED override IDeviceManager
    @Override 
    public <C extends java.util.Collection<LogBean>> C setLogBeansByDeviceId(DeviceBean bean , C importedBeans)
    {
        if(null != importedBeans){
            for( LogBean importBean : importedBeans ){
                LogManager.getInstance().setReferencedByDeviceId(importBean , bean);
            }
        }
        return importedBeans;
    }



    //3.5 SYNC SAVE override IDeviceManager
    @Override  
    public DeviceBean save(DeviceBean bean
        
        , ImageBean[] impImageByDeviceId , LogBean[] impLogByDeviceId )
    {
        if(null == bean) return null;
        bean = this.save( bean );
        this.setImageBeansByDeviceId(bean,impImageByDeviceId);
        ImageManager.getInstance().save( impImageByDeviceId );
        this.setLogBeansByDeviceId(bean,impLogByDeviceId);
        LogManager.getInstance().save( impLogByDeviceId );
        return bean;
    } 

    //3.6 SYNC SAVE AS TRANSACTION override IDeviceManager
    @Override 
    public DeviceBean saveAsTransaction(final DeviceBean bean
        
        ,final ImageBean[] impImageByDeviceId ,final LogBean[] impLogByDeviceId )
    {
        return this.runAsTransaction(new Callable<DeviceBean>(){
            @Override
            public DeviceBean call() throws Exception {
                return save(bean , impImageByDeviceId , impLogByDeviceId );
            }});
    }
    //3.7 SYNC SAVE override IDeviceManager
    @Override 
    public DeviceBean save(DeviceBean bean
        
        , java.util.Collection<ImageBean> impImageByDeviceId , java.util.Collection<LogBean> impLogByDeviceId )
    {
        if(null == bean) return null;
        bean = this.save( bean );
        this.setImageBeansByDeviceId(bean,impImageByDeviceId);
        ImageManager.getInstance().save( impImageByDeviceId );
        this.setLogBeansByDeviceId(bean,impLogByDeviceId);
        LogManager.getInstance().save( impLogByDeviceId );
        return bean;
    }   

    //3.8 SYNC SAVE AS TRANSACTION override IDeviceManager
    @Override 
    public DeviceBean saveAsTransaction(final DeviceBean bean
        
        ,final  java.util.Collection<ImageBean> impImageByDeviceId ,final  java.util.Collection<LogBean> impLogByDeviceId )
    {
        return this.runAsTransaction(new Callable<DeviceBean>(){
            @Override
            public DeviceBean call() throws Exception {
                return save(bean , impImageByDeviceId , impLogByDeviceId );
            }});
    }
     /**
     * Save the {@link DeviceBean} bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link DeviceBean} bean to be saved
     * @param args referenced beans or imported beans<br>
     *      see also {@link #save(DeviceBean , ImageBean[] , LogBean[] )}
     * @return the inserted or updated {@link DeviceBean} bean
     */
    //3.9 SYNC SAVE 
    @Override
    public DeviceBean save(DeviceBean bean,Object ...args) 
    {
        if(args.length > 2)
            throw new IllegalArgumentException("too many dynamic arguments,max dynamic arguments number: 2");
        if( args.length > 0 && null != args[0] && !(args[0] instanceof ImageBean[])){
            throw new IllegalArgumentException("invalid type for the No.1 argument,expected type:ImageBean[]");
        }
        if( args.length > 1 && null != args[1] && !(args[1] instanceof LogBean[])){
            throw new IllegalArgumentException("invalid type for the No.2 argument,expected type:LogBean[]");
        }
        return save(bean,(args.length < 1 || null == args[0])?null:(ImageBean[])args[0],(args.length < 2 || null == args[1])?null:(LogBean[])args[1]);
    } 

    /**
     * Save the {@link DeviceBean} bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link DeviceBean} bean to be saved
     * @param args referenced beans or imported beans<br>
     *      see also {@link #save(DeviceBean , java.util.Collection , java.util.Collection )}
     * @return the inserted or updated {@link DeviceBean} bean
     */
    //3.10 SYNC SAVE 
    @SuppressWarnings("unchecked")
    @Override
    public DeviceBean saveCollection(DeviceBean bean,Object ...inputs)
    {
        if(inputs.length > 2)
            throw new IllegalArgumentException("too many dynamic arguments,max dynamic arguments number: 2");
        Object[] args = new Object[2];
        System.arraycopy(inputs,0,args,0,2);
        if( args.length > 0 && null != args[0] && !(args[0] instanceof java.util.Collection)){
            throw new IllegalArgumentException("invalid type for the No.1 argument,expected type:java.util.Collection<ImageBean>");
        }
        if( args.length > 1 && null != args[1] && !(args[1] instanceof java.util.Collection)){
            throw new IllegalArgumentException("invalid type for the No.2 argument,expected type:java.util.Collection<LogBean>");
        }
        return save(bean,null == args[0]?null:(java.util.Collection<ImageBean>)args[0],null == args[1]?null:(java.util.Collection<LogBean>)args[1]);
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
    protected DeviceBean insert(DeviceBean bean)
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
    protected DeviceBean update(DeviceBean bean)
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
    public DeviceBean loadUniqueUsingTemplate(DeviceBean bean)
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
    public int loadUsingTemplate(DeviceBean bean, int[] fieldList, int startRow, int numRows,int searchType, Action<DeviceBean> action)
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
    public int deleteUsingTemplate(DeviceBean bean)
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

    // override IDeviceManager
    @Override 
    public DeviceBean loadByIndexMac(String mac)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByIndexMac(mac));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    // override IDeviceManager
    @Override 
    public java.util.List<DeviceBean> loadByIndexMac(String... indexs)
    {
        if(null == indexs)return new java.util.ArrayList<DeviceBean>();
        java.util.ArrayList<DeviceBean> list = new java.util.ArrayList<DeviceBean>(indexs.length);
        for(int i = 0 ;i< indexs.length;++i){
            list.add(loadByIndexMac(indexs[i]));
        }
        return list;
    }
    // override IDeviceManager
    @Override 
    public java.util.List<DeviceBean> loadByIndexMac(java.util.Collection<String> indexs)
    {
        if(null == indexs )return new java.util.ArrayList<DeviceBean>();
        java.util.ArrayList<DeviceBean> list = new java.util.ArrayList<DeviceBean>(indexs.size());
        if(indexs instanceof java.util.List){
            for(String key: indexs){
                list.add(loadByIndexMac(key));
            }
        }else{
            DeviceBean bean;
            for(String key: indexs){
                if(null != (bean = loadByIndexMac(key)))
                    list.add(bean);
            }
        }
        return list;
    }
    // override IDeviceManager
    @Override 
    public int deleteByIndexMac(String... indexs)
    {
        if(null == indexs)return 0;
        int count = 0;
        for(String index : indexs){
            count += deleteByIndexMac(index);
        }
        return count;
    }
    // override IDeviceManager
    @Override 
    public int deleteByIndexMac(java.util.Collection<String> indexs)
    {
        if(null == indexs)return 0;
        int count = 0;
        for(String index : indexs){
            count += deleteByIndexMac(index);
        }
        return count;
    }

    // override IDeviceManager
    @Override 
    public int deleteByIndexMac(String mac)
    {
        try{
            return this.nativeManager.deleteByIndexMac(mac);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
    // override IDeviceManager
    @Override 
    public DeviceBean loadByIndexSerialNo(String serialNo)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByIndexSerialNo(serialNo));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    // override IDeviceManager
    @Override 
    public java.util.List<DeviceBean> loadByIndexSerialNo(String... indexs)
    {
        if(null == indexs)return new java.util.ArrayList<DeviceBean>();
        java.util.ArrayList<DeviceBean> list = new java.util.ArrayList<DeviceBean>(indexs.length);
        for(int i = 0 ;i< indexs.length;++i){
            list.add(loadByIndexSerialNo(indexs[i]));
        }
        return list;
    }
    // override IDeviceManager
    @Override 
    public java.util.List<DeviceBean> loadByIndexSerialNo(java.util.Collection<String> indexs)
    {
        if(null == indexs )return new java.util.ArrayList<DeviceBean>();
        java.util.ArrayList<DeviceBean> list = new java.util.ArrayList<DeviceBean>(indexs.size());
        if(indexs instanceof java.util.List){
            for(String key: indexs){
                list.add(loadByIndexSerialNo(key));
            }
        }else{
            DeviceBean bean;
            for(String key: indexs){
                if(null != (bean = loadByIndexSerialNo(key)))
                    list.add(bean);
            }
        }
        return list;
    }
    // override IDeviceManager
    @Override 
    public int deleteByIndexSerialNo(String... indexs)
    {
        if(null == indexs)return 0;
        int count = 0;
        for(String index : indexs){
            count += deleteByIndexSerialNo(index);
        }
        return count;
    }
    // override IDeviceManager
    @Override 
    public int deleteByIndexSerialNo(java.util.Collection<String> indexs)
    {
        if(null == indexs)return 0;
        int count = 0;
        for(String index : indexs){
            count += deleteByIndexSerialNo(index);
        }
        return count;
    }

    // override IDeviceManager
    @Override 
    public int deleteByIndexSerialNo(String serialNo)
    {
        try{
            return this.nativeManager.deleteByIndexSerialNo(serialNo);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
     // override IDeviceManager
    @Override 
    public DeviceBean[] loadByIndexGroupId(Integer groupId)
    {
        return this.loadByIndexGroupIdAsList(groupId).toArray(new DeviceBean[0]);
    }
    
    // override IDeviceManager
    @Override 
    public java.util.List<DeviceBean> loadByIndexGroupIdAsList(Integer groupId)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByIndexGroupIdAsList(groupId));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    // override IDeviceManager
    @Override 
    public int deleteByIndexGroupId(Integer groupId)
    {
        try{
            return this.nativeManager.deleteByIndexGroupId(groupId);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
    
    /**
     * Retrieves a list of DeviceBean using the index specified by keyIndex.
     * @param keyIndex valid values: <br>
     *        {@link Constant#FL_DEVICE_INDEX_MAC},{@link Constant#FL_DEVICE_INDEX_SERIAL_NO},{@link Constant#FL_DEVICE_INDEX_GROUP_ID}
     * @param keys key values of index
     * @return a list of DeviceBean
     */
    @Override
    public java.util.List<DeviceBean> loadByIndexAsList(int keyIndex,Object ...keys)
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
     *        {@link Constant#FL_DEVICE_INDEX_MAC},{@link Constant#FL_DEVICE_INDEX_SERIAL_NO},{@link Constant#FL_DEVICE_INDEX_GROUP_ID}
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
    public int countUsingTemplate(DeviceBean bean, int searchType)
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
    public void registerListener(TableListener<DeviceBean> listener)
    {
        this.nativeManager.registerListener(this.toNative(listener));
    }

    //36
    @Override
    public void unregisterListener(TableListener<DeviceBean> listener)
    {
        this.nativeManager.unregisterListener(this.toNative(listener));
    }
    
    private net.gdface.facelog.dborm.TableListener<FlDeviceBean> toNative(final TableListener<DeviceBean> listener) {
        return null == listener ?null:new net.gdface.facelog.dborm.TableListener<FlDeviceBean> (){

            @Override
            public void beforeInsert(FlDeviceBean bean) throws DAOException {
                listener.beforeInsert(DeviceManager.this.beanConverter.fromRight(bean));                
            }

            @Override
            public void afterInsert(FlDeviceBean bean) throws DAOException {
                listener.afterInsert(DeviceManager.this.beanConverter.fromRight(bean));
                
            }

            @Override
            public void beforeUpdate(FlDeviceBean bean) throws DAOException {
                listener.beforeUpdate(DeviceManager.this.beanConverter.fromRight(bean));
                
            }

            @Override
            public void afterUpdate(FlDeviceBean bean) throws DAOException {
                listener.afterUpdate(DeviceManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public void beforeDelete(FlDeviceBean bean) throws DAOException {
                listener.beforeDelete(DeviceManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public void afterDelete(FlDeviceBean bean) throws DAOException {
                listener.afterDelete(DeviceManager.this.beanConverter.fromRight(bean));
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
    public int loadBySqlForAction(String sql, Object[] argList, int[] fieldList,int startRow, int numRows,Action<DeviceBean> action){
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
    
    private net.gdface.facelog.dborm.TableManager.Action<FlDeviceBean> toNative(final Action<DeviceBean> action){
        if(null == action)
            throw new NullPointerException();
        return new net.gdface.facelog.dborm.TableManager.Action<FlDeviceBean>(){

            @Override
            public void call(FlDeviceBean bean) {
                action.call(DeviceManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public FlDeviceBean getBean() {
                return  DeviceManager.this.beanConverter.toRight(action.getBean());
            }};
    }
    
    //45 override IDeviceManager
    @Override 
    public java.util.List<Integer> toPrimaryKeyList(DeviceBean... array){        
        if(null == array)return new java.util.ArrayList<Integer>();
        java.util.ArrayList<Integer> list = new java.util.ArrayList<Integer>(array.length);
        for(DeviceBean bean:array){
            list.add(null == bean ? null : bean.getId());
        }
        return list;
    }
    //46 override IDeviceManager
    @Override 
    public java.util.List<Integer> toPrimaryKeyList(java.util.Collection<DeviceBean> collection){        
        if(null == collection)return new java.util.ArrayList<Integer>();
        java.util.ArrayList<Integer> list = new java.util.ArrayList<Integer>(collection.size());
        for(DeviceBean bean:collection){
            list.add(null == bean ? null : bean.getId());
        }
        return list;
    }
}
