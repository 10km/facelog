// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.db.mysql;

import java.util.concurrent.Callable;

import net.gdface.facelog.db.Constant;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.IBeanConverter;
import net.gdface.facelog.db.IDbConverter;
import net.gdface.facelog.db.TableManager;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.WrapDAOException;

import net.gdface.facelog.dborm.exception.DAOException;
import net.gdface.facelog.dborm.log.FlLogManager;
import net.gdface.facelog.dborm.log.FlLogBean;

/**
 * Handles database calls (save, load, count, etc...) for the fl_log table.<br>
 * all {@link DAOException} be wrapped as {@link WrapDAOException} to throw.
 * @author guyadong
 */
public class LogManager extends TableManager.Adapter<LogBean>
{
    private FlLogManager nativeManager = FlLogManager.getInstance();
    private IDbConverter<net.gdface.facelog.dborm.device.FlDeviceBean,net.gdface.facelog.dborm.face.FlFaceBean,net.gdface.facelog.dborm.image.FlImageBean,net.gdface.facelog.dborm.log.FlLogBean,net.gdface.facelog.dborm.person.FlPersonBean,net.gdface.facelog.dborm.image.FlStoreBean,net.gdface.facelog.dborm.face.FlFaceLightBean,net.gdface.facelog.dborm.face.FlFeatureBean,net.gdface.facelog.dborm.log.FlLogLightBean> dbConverter = DbConverter.INSTANCE;
    private IBeanConverter<LogBean,FlLogBean> beanConverter = dbConverter.getLogBeanConverter();
    private static LogManager singleton = new LogManager();

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
     * Get the {@link LogManager} singleton.
     *
     * @return {@link LogManager}
     */
    public static LogManager getInstance()
    {
        return singleton;
    }
   
    public IDbConverter<net.gdface.facelog.dborm.device.FlDeviceBean,net.gdface.facelog.dborm.face.FlFaceBean,net.gdface.facelog.dborm.image.FlImageBean,net.gdface.facelog.dborm.log.FlLogBean,net.gdface.facelog.dborm.person.FlPersonBean,net.gdface.facelog.dborm.image.FlStoreBean,net.gdface.facelog.dborm.face.FlFaceLightBean,net.gdface.facelog.dborm.face.FlFeatureBean,net.gdface.facelog.dborm.log.FlLogLightBean> getDbConverter() {
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
        this.beanConverter = this.dbConverter.getLogBeanConverter();
    }
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    /**
     * Loads a {@link LogBean} from the fl_log using primary key fields.
     *
     * @param id Integer - PK# 1
     * @return a unique LogBean or {@code null} if not found
     */
    //1
    public LogBean loadByPrimaryKey(Integer id)
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
    public LogBean loadByPrimaryKey(LogBean bean)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByPrimaryKey(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    /**
     * Loads a {@link LogBean} from the fl_log using primary key fields.
     * @param keys primary keys value:<br> 
     * @return a unique {@link LogBean} or {@code null} if not found
     * @see {@link #loadByPrimaryKey(Integer id)}
     */
    //1.3
    @Override
    public LogBean loadByPrimaryKey(Object ...keys){
        if(keys.length != 1 )
            throw new IllegalArgumentException("argument number mismatch with primary key number");
        if(! (keys[0] instanceof Integer))
            throw new IllegalArgumentException("invalid type for the No.1 argument,expected type:Integer");
        return loadByPrimaryKey((Integer)keys[0]);
    }
    
    /**
     * Returns true if this fl_log contains row with primary key fields.
     * @param id Integer - PK# 1
     * @see #loadByPrimaryKey(Integer id)
     */
    //1.4
    public boolean existsPrimaryKey(Integer id)
    {
        return null!=loadByPrimaryKey(id );
    }
    
    /**
     * Delete row according to its primary keys.<br>
     * all keys must not be null
     *
     * @param id Integer - PK# 1
     * @return the number of deleted rows
     */
    //2
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

    /**
     * Delete row according to its primary keys.
     *
     * @param keys primary keys value:<br> 
     * @return the number of deleted rows
     * @see {@link #deleteByPrimaryKey(Integer id)}
     */   
    //2.1
    @Override
    public int deleteByPrimaryKey(Object ...keys){
        if(keys.length != 1 )
            throw new IllegalArgumentException("argument number mismatch with primary key number");
        if(! (keys[0] instanceof Integer))
            throw new IllegalArgumentException("invalid type for the No.1 argument,expected type:Integer");
        return deleteByPrimaryKey((Integer)keys[0]);
    }

 
 


    /**
     * Save the LogBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link LogBean} bean to be saved
     * @param refFlDevicebyDeviceId the {@link DeviceBean} bean referenced by {@link LogBean} 
     * @param refFlFacebyVerifyFace the {@link FaceBean} bean referenced by {@link LogBean} 
     * @param refFlFacebyCompareFace the {@link FaceBean} bean referenced by {@link LogBean} 
     * @param refFlPersonbyPersonId the {@link PersonBean} bean referenced by {@link LogBean} 
         * @return the inserted or updated {@link LogBean} bean
     */
    //3.5 SYNC SAVE 
    public LogBean save(LogBean bean
        , DeviceBean refFlDevicebyDeviceId , FaceBean refFlFacebyVerifyFace , FaceBean refFlFacebyCompareFace , PersonBean refFlPersonbyPersonId 
        )
    {
        try{
            return this.beanConverter.fromRight(bean,nativeManager.save(this.beanConverter.toRight(bean)
                , this.dbConverter.getDeviceBeanConverter().toRight(refFlDevicebyDeviceId) , this.dbConverter.getFaceBeanConverter().toRight(refFlFacebyVerifyFace) , this.dbConverter.getFaceBeanConverter().toRight(refFlFacebyCompareFace) , this.dbConverter.getPersonBeanConverter().toRight(refFlPersonbyPersonId)                 ));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    } 
    /**
     * Transaction version for sync save
     * @see {@link #save(LogBean , DeviceBean , FaceBean , FaceBean , PersonBean )}
     */
    //3.6 SYNC SAVE AS TRANSACTION
    public LogBean saveAsTransaction(final LogBean bean
        ,final DeviceBean refFlDevicebyDeviceId ,final FaceBean refFlFacebyVerifyFace ,final FaceBean refFlFacebyCompareFace ,final PersonBean refFlPersonbyPersonId 
        )
    {
        return this.runAsTransaction(new Callable<LogBean>(){
            @Override
            public LogBean call() throws Exception {
                return save(bean , refFlDevicebyDeviceId , refFlFacebyVerifyFace , refFlFacebyCompareFace , refFlPersonbyPersonId );
            }});
    }
     /**
     * Save the {@link LogBean} bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link LogBean} bean to be saved
     * @param args referenced beans or imported beans<br>
     *      see also {@link #save(LogBean , DeviceBean , FaceBean , FaceBean , PersonBean )}
     * @return the inserted or updated {@link LogBean} bean
     */
    //3.9 SYNC SAVE 
    @Override
    public LogBean save(LogBean bean,Object ...args) 
    {
        if(args.length > 4)
            throw new IllegalArgumentException("too many dynamic arguments,max dynamic arguments number: 4");
        if( args.length > 0 && null != args[0] && !(args[0] instanceof DeviceBean)){
            throw new IllegalArgumentException("invalid type for the No.1 dynamic argument,expected type:DeviceBean");
        }
        if( args.length > 1 && null != args[1] && !(args[1] instanceof FaceBean)){
            throw new IllegalArgumentException("invalid type for the No.2 dynamic argument,expected type:FaceBean");
        }
        if( args.length > 2 && null != args[2] && !(args[2] instanceof FaceBean)){
            throw new IllegalArgumentException("invalid type for the No.3 dynamic argument,expected type:FaceBean");
        }
        if( args.length > 3 && null != args[3] && !(args[3] instanceof PersonBean)){
            throw new IllegalArgumentException("invalid type for the No.4 dynamic argument,expected type:PersonBean");
        }
        return save(bean,(args.length < 1 || null == args[0])?null:(DeviceBean)args[0],(args.length < 2 || null == args[1])?null:(FaceBean)args[1],(args.length < 3 || null == args[2])?null:(FaceBean)args[2],(args.length < 4 || null == args[3])?null:(PersonBean)args[3]);
    } 

    /**
     * Save the {@link LogBean} bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link LogBean} bean to be saved
     * @param args referenced beans or imported beans<br>
     *      see also {@link #save(LogBean , DeviceBean , FaceBean , FaceBean , PersonBean )}
     * @return the inserted or updated {@link LogBean} bean
     */
    //3.10 SYNC SAVE 
    @SuppressWarnings("unchecked")
    @Override
    public LogBean saveCollection(LogBean bean,Object ...inputs)
    {
        if(inputs.length > 4)
            throw new IllegalArgumentException("too many dynamic arguments,max dynamic arguments number: 4");
        Object[] args = new Object[4];
        System.arraycopy(inputs,0,args,0,4);
        if( args.length > 0 && null != args[0] && !(args[0] instanceof DeviceBean)){
            throw new IllegalArgumentException("invalid type for the No.1 dynamic argument,expected type:DeviceBean");
        }
        if( args.length > 1 && null != args[1] && !(args[1] instanceof FaceBean)){
            throw new IllegalArgumentException("invalid type for the No.2 dynamic argument,expected type:FaceBean");
        }
        if( args.length > 2 && null != args[2] && !(args[2] instanceof FaceBean)){
            throw new IllegalArgumentException("invalid type for the No.3 dynamic argument,expected type:FaceBean");
        }
        if( args.length > 3 && null != args[3] && !(args[3] instanceof PersonBean)){
            throw new IllegalArgumentException("invalid type for the No.4 dynamic argument,expected type:PersonBean");
        }
        return save(bean,null == args[0]?null:(DeviceBean)args[0],null == args[1]?null:(FaceBean)args[1],null == args[2]?null:(FaceBean)args[2],null == args[3]?null:(PersonBean)args[3]);
    }

     //////////////////////////////////////
    // FOREIGN KEY GENERIC METHOD
    //////////////////////////////////////

    /**
     * Retrieves the bean object referenced by fkIndex.<br>
     * @param <T>
     * <ul>
     *     <li> {@link Constant#FL_LOG_FK_DEVICE_ID} -> {@link DeviceBean}</li>
     *     <li> {@link Constant#FL_LOG_FK_VERIFY_FACE} -> {@link FaceBean}</li>
     *     <li> {@link Constant#FL_LOG_FK_COMPARE_FACE} -> {@link FaceBean}</li>
     *     <li> {@link Constant#FL_LOG_FK_PERSON_ID} -> {@link PersonBean}</li>
     * </ul>
     * @param bean the {@link LogBean} object to use
     * @param fkIndex valid values: <br>
     *        {@link Constant#FL_LOG_FK_DEVICE_ID},{@link Constant#FL_LOG_FK_VERIFY_FACE},{@link Constant#FL_LOG_FK_COMPARE_FACE},{@link Constant#FL_LOG_FK_PERSON_ID}
     * @return the associated <T> bean or {@code null} if {@code bean} or {@code beanToSet} is {@code null}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends net.gdface.facelog.db.BaseBean> T getReferencedBean(LogBean bean,int fkIndex){
        switch(fkIndex){
        case FL_LOG_FK_DEVICE_ID:
            return  (T)this.getReferencedByDeviceId(bean);
        case FL_LOG_FK_VERIFY_FACE:
            return  (T)this.getReferencedByVerifyFace(bean);
        case FL_LOG_FK_COMPARE_FACE:
            return  (T)this.getReferencedByCompareFace(bean);
        case FL_LOG_FK_PERSON_ID:
            return  (T)this.getReferencedByPersonId(bean);
        }
        throw new IllegalArgumentException(String.format("invalid fkIndex %d", fkIndex));
    }
    /**
     * Associates the {@link LogBean} object to the bean object by fkIndex field.<br>
     * 
     * @param <T> see also {@link #getReferencedBean(LogBean,int)}
     * @param bean the {@link LogBean} object to use
     * @param beanToSet the <T> object to associate to the {@link LogBean}
     * @param fkIndex valid values: see also {@link #getReferencedBean(LogBean,int)}
     * @return always beanToSet saved
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends net.gdface.facelog.db.BaseBean> T setReferencedBean(LogBean bean,T beanToSet,int fkIndex){
        switch(fkIndex){
        case FL_LOG_FK_DEVICE_ID:
            return  (T)this.setReferencedByDeviceId(bean, (DeviceBean)beanToSet);
        case FL_LOG_FK_VERIFY_FACE:
            return  (T)this.setReferencedByVerifyFace(bean, (FaceBean)beanToSet);
        case FL_LOG_FK_COMPARE_FACE:
            return  (T)this.setReferencedByCompareFace(bean, (FaceBean)beanToSet);
        case FL_LOG_FK_PERSON_ID:
            return  (T)this.setReferencedByPersonId(bean, (PersonBean)beanToSet);
        }
        throw new IllegalArgumentException(String.format("invalid fkIndex %d", fkIndex));
    }
    
    //////////////////////////////////////
    // GET/SET FOREIGN KEY BEAN METHOD
    //////////////////////////////////////


    /**
     * Retrieves the {@link DeviceBean} object referenced by {@link LogBean#getDeviceId}() field.<br>
     * FK_NAME : fl_log_ibfk_2
     * @param bean the {@link LogBean}
     * @return the associated {@link DeviceBean} bean or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET REFERENCED VALUE
    public DeviceBean getReferencedByDeviceId(LogBean bean)
    {
        try{
            return this.dbConverter.getDeviceBeanConverter().fromRight(this.nativeManager.getReferencedByDeviceId(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
        
    }

    /**
     * Associates the {@link LogBean} object to the {@link DeviceBean} object by {@link LogBean#getDeviceId}() field.
     *
     * @param bean the {@link LogBean} object to use
     * @param beanToSet the {@link DeviceBean} object to associate to the {@link LogBean}
     * @return always beanToSet saved
     * @throws Exception
     */
    //5.2 SET REFERENCED 
    public DeviceBean setReferencedByDeviceId(LogBean bean, DeviceBean beanToSet)
    {
        try{
            return this.dbConverter.getDeviceBeanConverter().fromRight(beanToSet,this.nativeManager.setReferencedByDeviceId(this.beanConverter.toRight(bean),this.dbConverter.getDeviceBeanConverter().toRight(beanToSet)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * Retrieves the {@link FaceBean} object referenced by {@link LogBean#getVerifyFace}() field.<br>
     * FK_NAME : fl_log_ibfk_3
     * @param bean the {@link LogBean}
     * @return the associated {@link FaceBean} bean or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET REFERENCED VALUE
    public FaceBean getReferencedByVerifyFace(LogBean bean)
    {
        try{
            return this.dbConverter.getFaceBeanConverter().fromRight(this.nativeManager.getReferencedByVerifyFace(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
        
    }

    /**
     * Associates the {@link LogBean} object to the {@link FaceBean} object by {@link LogBean#getVerifyFace}() field.
     *
     * @param bean the {@link LogBean} object to use
     * @param beanToSet the {@link FaceBean} object to associate to the {@link LogBean}
     * @return always beanToSet saved
     * @throws Exception
     */
    //5.2 SET REFERENCED 
    public FaceBean setReferencedByVerifyFace(LogBean bean, FaceBean beanToSet)
    {
        try{
            return this.dbConverter.getFaceBeanConverter().fromRight(beanToSet,this.nativeManager.setReferencedByVerifyFace(this.beanConverter.toRight(bean),this.dbConverter.getFaceBeanConverter().toRight(beanToSet)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * Retrieves the {@link FaceBean} object referenced by {@link LogBean#getCompareFace}() field.<br>
     * FK_NAME : fl_log_ibfk_4
     * @param bean the {@link LogBean}
     * @return the associated {@link FaceBean} bean or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET REFERENCED VALUE
    public FaceBean getReferencedByCompareFace(LogBean bean)
    {
        try{
            return this.dbConverter.getFaceBeanConverter().fromRight(this.nativeManager.getReferencedByCompareFace(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
        
    }

    /**
     * Associates the {@link LogBean} object to the {@link FaceBean} object by {@link LogBean#getCompareFace}() field.
     *
     * @param bean the {@link LogBean} object to use
     * @param beanToSet the {@link FaceBean} object to associate to the {@link LogBean}
     * @return always beanToSet saved
     * @throws Exception
     */
    //5.2 SET REFERENCED 
    public FaceBean setReferencedByCompareFace(LogBean bean, FaceBean beanToSet)
    {
        try{
            return this.dbConverter.getFaceBeanConverter().fromRight(beanToSet,this.nativeManager.setReferencedByCompareFace(this.beanConverter.toRight(bean),this.dbConverter.getFaceBeanConverter().toRight(beanToSet)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * Retrieves the {@link PersonBean} object referenced by {@link LogBean#getPersonId}() field.<br>
     * FK_NAME : fl_log_ibfk_1
     * @param bean the {@link LogBean}
     * @return the associated {@link PersonBean} bean or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET REFERENCED VALUE
    public PersonBean getReferencedByPersonId(LogBean bean)
    {
        try{
            return this.dbConverter.getPersonBeanConverter().fromRight(this.nativeManager.getReferencedByPersonId(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
        
    }

    /**
     * Associates the {@link LogBean} object to the {@link PersonBean} object by {@link LogBean#getPersonId}() field.
     *
     * @param bean the {@link LogBean} object to use
     * @param beanToSet the {@link PersonBean} object to associate to the {@link LogBean}
     * @return always beanToSet saved
     * @throws Exception
     */
    //5.2 SET REFERENCED 
    public PersonBean setReferencedByPersonId(LogBean bean, PersonBean beanToSet)
    {
        try{
            return this.dbConverter.getPersonBeanConverter().fromRight(beanToSet,this.nativeManager.setReferencedByPersonId(this.beanConverter.toRight(bean),this.dbConverter.getPersonBeanConverter().toRight(beanToSet)));
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
    protected LogBean insert(LogBean bean)
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
    protected LogBean update(LogBean bean)
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
    public LogBean loadUniqueUsingTemplate(LogBean bean)
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
    public int loadUsingTemplate(LogBean bean, int[] fieldList, int startRow, int numRows,int searchType, Action<LogBean> action)
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
    public int deleteUsingTemplate(LogBean bean)
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

     /**
     * Retrieves an array of LogBean using the compare_face index.
     *
     * @param compareFace the compare_face column's value filter.
     * @return an array of LogBean
     */
    public LogBean[] loadByIndexCompareFace(String compareFace)
    {
        return this.loadByIndexCompareFaceAsList(compareFace).toArray(new LogBean[0]);
    }
    
    /**
     * Retrieves a list of LogBean using the compare_face index.
     *
     * @param compareFace the compare_face column's value filter.
     * @return a list of LogBean
     */
    public java.util.List<LogBean> loadByIndexCompareFaceAsList(String compareFace)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByIndexCompareFaceAsList(compareFace));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * Deletes rows using the compare_face index.
     *
     * @param compareFace the compare_face column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexCompareFace(String compareFace)
    {
        try{
            return this.nativeManager.deleteByIndexCompareFace(compareFace);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
     /**
     * Retrieves an array of LogBean using the device_id index.
     *
     * @param deviceId the device_id column's value filter.
     * @return an array of LogBean
     */
    public LogBean[] loadByIndexDeviceId(Integer deviceId)
    {
        return this.loadByIndexDeviceIdAsList(deviceId).toArray(new LogBean[0]);
    }
    
    /**
     * Retrieves a list of LogBean using the device_id index.
     *
     * @param deviceId the device_id column's value filter.
     * @return a list of LogBean
     */
    public java.util.List<LogBean> loadByIndexDeviceIdAsList(Integer deviceId)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByIndexDeviceIdAsList(deviceId));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * Deletes rows using the device_id index.
     *
     * @param deviceId the device_id column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexDeviceId(Integer deviceId)
    {
        try{
            return this.nativeManager.deleteByIndexDeviceId(deviceId);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
     /**
     * Retrieves an array of LogBean using the person_id index.
     *
     * @param personId the person_id column's value filter.
     * @return an array of LogBean
     */
    public LogBean[] loadByIndexPersonId(Integer personId)
    {
        return this.loadByIndexPersonIdAsList(personId).toArray(new LogBean[0]);
    }
    
    /**
     * Retrieves a list of LogBean using the person_id index.
     *
     * @param personId the person_id column's value filter.
     * @return a list of LogBean
     */
    public java.util.List<LogBean> loadByIndexPersonIdAsList(Integer personId)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByIndexPersonIdAsList(personId));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * Deletes rows using the person_id index.
     *
     * @param personId the person_id column's value filter.
     * @return the number of deleted objects
     */
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
     * Retrieves an array of LogBean using the verify_face index.
     *
     * @param verifyFace the verify_face column's value filter.
     * @return an array of LogBean
     */
    public LogBean[] loadByIndexVerifyFace(String verifyFace)
    {
        return this.loadByIndexVerifyFaceAsList(verifyFace).toArray(new LogBean[0]);
    }
    
    /**
     * Retrieves a list of LogBean using the verify_face index.
     *
     * @param verifyFace the verify_face column's value filter.
     * @return a list of LogBean
     */
    public java.util.List<LogBean> loadByIndexVerifyFaceAsList(String verifyFace)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByIndexVerifyFaceAsList(verifyFace));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * Deletes rows using the verify_face index.
     *
     * @param verifyFace the verify_face column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexVerifyFace(String verifyFace)
    {
        try{
            return this.nativeManager.deleteByIndexVerifyFace(verifyFace);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
    
    /**
     * Retrieves a list of LogBean using the index specified by keyIndex.
     * @param keyIndex valid values: <br>
     *        {@link Constant#FL_LOG_INDEX_COMPARE_FACE},{@link Constant#FL_LOG_INDEX_DEVICE_ID},{@link Constant#FL_LOG_INDEX_PERSON_ID},{@link Constant#FL_LOG_INDEX_VERIFY_FACE}
     * @param keys key values of index
     * @return a list of LogBean
     */
    @Override
    public java.util.List<LogBean> loadByIndexAsList(int keyIndex,Object ...keys)
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
     *        {@link Constant#FL_LOG_INDEX_COMPARE_FACE},{@link Constant#FL_LOG_INDEX_DEVICE_ID},{@link Constant#FL_LOG_INDEX_PERSON_ID},{@link Constant#FL_LOG_INDEX_VERIFY_FACE}
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
    public int countUsingTemplate(LogBean bean, int searchType)
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
    public void registerListener(TableListener<LogBean> listener)
    {
        this.nativeManager.registerListener(this.toNative(listener));
    }

    //36
    @Override
    public void unregisterListener(TableListener<LogBean> listener)
    {
        this.nativeManager.unregisterListener(this.toNative(listener));
    }
    
    private net.gdface.facelog.dborm.TableListener<FlLogBean> toNative(final TableListener<LogBean> listener) {
        return null == listener ?null:new net.gdface.facelog.dborm.TableListener<FlLogBean> (){

            @Override
            public void beforeInsert(FlLogBean bean) throws DAOException {
                listener.beforeInsert(LogManager.this.beanConverter.fromRight(bean));                
            }

            @Override
            public void afterInsert(FlLogBean bean) throws DAOException {
                listener.afterInsert(LogManager.this.beanConverter.fromRight(bean));
                
            }

            @Override
            public void beforeUpdate(FlLogBean bean) throws DAOException {
                listener.beforeUpdate(LogManager.this.beanConverter.fromRight(bean));
                
            }

            @Override
            public void afterUpdate(FlLogBean bean) throws DAOException {
                listener.afterUpdate(LogManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public void beforeDelete(FlLogBean bean) throws DAOException {
                listener.beforeDelete(LogManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public void afterDelete(FlLogBean bean) throws DAOException {
                listener.afterDelete(LogManager.this.beanConverter.fromRight(bean));
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
    public int loadBySqlForAction(String sql, Object[] argList, int[] fieldList,int startRow, int numRows,Action<LogBean> action){
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
    
    private net.gdface.facelog.dborm.TableManager.Action<FlLogBean> toNative(final Action<LogBean> action){
        if(null == action)
            throw new NullPointerException();
        return new net.gdface.facelog.dborm.TableManager.Action<FlLogBean>(){

            @Override
            public void call(FlLogBean bean) {
                action.call(LogManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public FlLogBean getBean() {
                return  LogManager.this.beanConverter.toRight(action.getBean());
            }};
    }
}
