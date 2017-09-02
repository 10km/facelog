// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.db.mysql;

import java.util.concurrent.Callable;

import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.IBeanConverter;
import net.gdface.facelog.db.IDbConverter;
import net.gdface.facelog.db.BaseBean;
import net.gdface.facelog.db.TableManager;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.WrapDAOException;

import net.gdface.facelog.dborm.exception.DAOException;
import net.gdface.facelog.dborm.face.FlFaceManager;
import net.gdface.facelog.dborm.face.FlFaceBean;
import net.gdface.facelog.dborm.log.FlLogBean;
/**
 * Handles database calls (save, load, count, etc...) for the fl_face table.<br>
 * all {@link DAOException} be wrapped as {@link WrapDAOException} to throw.
 * @author guyadong
 */
public class FaceManager extends TableManager.Adapter<FaceBean>
{
    private FlFaceManager nativeManager = FlFaceManager.getInstance();
    private IDbConverter<net.gdface.facelog.dborm.device.FlDeviceBean,net.gdface.facelog.dborm.face.FlFaceBean,net.gdface.facelog.dborm.image.FlImageBean,net.gdface.facelog.dborm.log.FlLogBean,net.gdface.facelog.dborm.person.FlPersonBean,net.gdface.facelog.dborm.image.FlStoreBean,net.gdface.facelog.dborm.face.FlFaceLightBean,net.gdface.facelog.dborm.face.FlFeatureBean,net.gdface.facelog.dborm.log.FlLogLightBean> dbConverter = DbConverter.INSTANCE;
    private IBeanConverter<FaceBean,FlFaceBean> beanConverter = dbConverter.getFaceBeanConverter();
    private static FaceManager singleton = new FaceManager();

    /**
    * @return table name
    */
    public String getTableName() {
        return this.nativeManager.getTableName();
    }

    /**
    * @return field names of table
    */
    public String[] getFieldNames() {
        return this.nativeManager.getFieldNames();
    }

    public String getFieldNamesAsString() {
        return this.nativeManager.getFieldNamesAsString();
    }
    
    public String[] getFullFieldNames() {
        return this.nativeManager.getFullFieldNames();
    }
    
    /**
    * @return primarykeyNames
    */
    public String[] getPrimarykeyNames() {
        return this.nativeManager.getPrimarykeyNames();
    }
    
    /**
     * Get the {@link FaceManager} singleton.
     *
     * @return {@link FaceManager}
     */
    public static FaceManager getInstance()
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
        this.beanConverter = this.dbConverter.getFaceBeanConverter();
    }
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    /**
     * Loads a {@link FaceBean} from the fl_face using primary key fields.
     *
     * @param md5 String - PK# 1
     * @return a unique FaceBean or {@code null} if not found
     */
    //1
    public FaceBean loadByPrimaryKey(String md5)
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
    public FaceBean loadByPrimaryKey(FaceBean bean)
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
     * Loads a {@link FaceBean} from the fl_face using primary key fields.
     * when you don't know which is primary key of table,you can use the method.
     * @param keys primary keys value:<br> 
     *             PK# 1:String     
     * @return a unique {@link FaceBean} or {@code null} if not found
     * @see {@link #loadByPrimaryKey(String md5)}
     */
    //1.3
    public FaceBean loadByPrimaryKey(Object ...keys){
        if(keys.length != 1 )
            throw new IllegalArgumentException("argument number mismatch with primary key number");
        if(! (keys[0] instanceof String))
            throw new IllegalArgumentException("invalid type for the No.1 argument,expected type:String");
        return loadByPrimaryKey((String)keys[0]);
    }
    
    /**
     * Returns true if this fl_face contains row with primary key fields.
     * @param md5 String - PK# 1
     * @see #loadByPrimaryKey(String md5)
     */
    //1.4
    public boolean existsPrimaryKey(String md5)
    {
        return null!=loadByPrimaryKey(md5 );
    }
    
    /**
     * Delete row according to its primary keys.<br>
     * all keys must not be null
     *
     * @param md5 String - PK# 1
     * @return the number of deleted rows
     */
    //2
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

    /**
     * Delete row according to its primary keys.
     *
     * @param keys primary keys value:<br> 
     *             PK# 1:String     
     * @return the number of deleted rows
     * @see {@link #deleteByPrimaryKey(String md5)}
     */   
    //2.1
    public int deleteByPrimaryKey(Object ...keys){
        if(keys.length != 1 )
            throw new IllegalArgumentException("argument number mismatch with primary key number");
        if(! (keys[0] instanceof String))
            throw new IllegalArgumentException("invalid type for the No.1 argument,expected type:String");
        return deleteByPrimaryKey((String)keys[0]);
    }

 
    //////////////////////////////////////
    // IMPORT KEY GENERIC METHOD
    //////////////////////////////////////
    
    /**
     * Retrieves imported T objects by ikIndex.<br>
     * @param <T>
     * <ul>
     *     <li> {@link TableManager#FL_FACE_IK_FL_LOG_VERIFY_FACE} -> {@link FlLogBean}</li>
     *     <li> {@link TableManager#FL_FACE_IK_FL_LOG_COMPARE_FACE} -> {@link FlLogBean}</li>
     * </ul>
     * @param bean the {@link FaceBean} object to use
     * @param ikIndex valid values: {@link TableManager#FL_FACE_IK_FL_LOG_VERIFY_FACE},{@link TableManager#FL_FACE_IK_FL_LOG_COMPARE_FACE}
     * @return the associated T beans or {@code null} if {@code bean} is {@code null}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseBean> java.util.List<T> getImportedBeansAsList(FaceBean bean,int ikIndex){
        switch(ikIndex){
        case FL_FACE_IK_FL_LOG_VERIFY_FACE:
            return (java.util.List<T>)this.getFlLogBeansByVerifyFaceAsList(bean);
        case FL_FACE_IK_FL_LOG_COMPARE_FACE:
            return (java.util.List<T>)this.getFlLogBeansByCompareFaceAsList(bean);
        }
        throw new IllegalArgumentException(String.format("invalid ikIndex %d", ikIndex));
    }
    /**
     * Set the T objects as imported beans of bean object by ikIndex.<br>
     * @param <T>
     * 
     * <ul>
     *     <li> {@link TableManager#FL_FACE_IK_FL_LOG_VERIFY_FACE} -> {@link FlLogBean}</li>
     *     <li> {@link TableManager#FL_FACE_IK_FL_LOG_COMPARE_FACE} -> {@link FlLogBean}</li>
     * </ul>
     * @param bean the {@link FaceBean} object to use
     * @param importedBeans the FlLogBean array to associate to the {@link FaceBean}
     * @param ikIndex valid values: {@link TableManager#FL_FACE_IK_FL_LOG_VERIFY_FACE},{@link TableManager#FL_FACE_IK_FL_LOG_COMPARE_FACE}
     * @return importedBeans always
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseBean> T[] setImportedBeans(FaceBean bean,T[] importedBeans,int ikIndex){
        switch(ikIndex){
        case FL_FACE_IK_FL_LOG_VERIFY_FACE:
            return (T[])setFlLogBeansByVerifyFace(bean,(LogBean[])importedBeans);
        case FL_FACE_IK_FL_LOG_COMPARE_FACE:
            return (T[])setFlLogBeansByCompareFace(bean,(LogBean[])importedBeans);
        }
        throw new IllegalArgumentException(String.format("invalid ikIndex %d", ikIndex));
    }
    /**
     * Set the importedBeans associates to the bean by ikIndex<br>
     * @param <T>
     * <ul>
     *     <li> {@link TableManager#FL_FACE_IK_FL_LOG_VERIFY_FACE} -> {@link FlLogBean}</li>
     *     <li> {@link TableManager#FL_FACE_IK_FL_LOG_COMPARE_FACE} -> {@link FlLogBean}</li>
     * </ul>
     * @param bean the {@link FaceBean} object to use
     * @param importedBeans the <T> object to associate to the {@link FaceBean}
     * @param ikIndex valid values: {@link TableManager#FL_FACE_IK_FL_LOG_VERIFY_FACE},{@link TableManager#FL_FACE_IK_FL_LOG_COMPARE_FACE}
     * @return importedBeans always
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseBean,C extends java.util.Collection<T>> C setImportedBeans(FaceBean bean,C importedBeans,int ikIndex){
        switch(ikIndex){
        case FL_FACE_IK_FL_LOG_VERIFY_FACE:
            return (C)setFlLogBeansByVerifyFace(bean,(java.util.Collection<LogBean>)importedBeans);
        case FL_FACE_IK_FL_LOG_COMPARE_FACE:
            return (C)setFlLogBeansByCompareFace(bean,(java.util.Collection<LogBean>)importedBeans);
        }
        throw new IllegalArgumentException(String.format("invalid ikIndex %d", ikIndex));
    }
 

    //////////////////////////////////////
    // GET/SET IMPORTED KEY BEAN METHOD
    //////////////////////////////////////
    /**
     * Retrieves the {@link LogBean} object from the fl_log.verify_face field.<BR>
     * FK_NAME : fl_log_ibfk_3 
     * @param bean the {@link FaceBean}
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.1 GET IMPORTED
    public LogBean[] getFlLogBeansByVerifyFace(FaceBean bean)
    {
        return this.getFlLogBeansByVerifyFaceAsList(bean).toArray(new LogBean[0]);
    }

    /**
     * Retrieves the {@link LogBean} object from fl_log.verify_face field.<BR>
     * FK_NAME:fl_log_ibfk_3
     * @param bean the {@link FaceBean}
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET IMPORTED
    public java.util.List<LogBean> getFlLogBeansByVerifyFaceAsList(FaceBean bean)
    {
        try {
            return this.dbConverter.getLogBeanConverter().fromRight(nativeManager.getFlLogBeansByVerifyFaceAsList( this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * set  the {@link LogBean} object array associate to FaceBean by the fl_log.verify_face field.<BR>
     * FK_NAME : fl_log_ibfk_3 
     * @param bean the referenced {@link FaceBean}
     * @param importedBeans imported beans from fl_log
     * @return importedBeans always
     * @see {@link FlLogManager#setReferencedByVerifyFace(LogBean, FaceBean)
     */
    //3.3 SET IMPORTED
    public LogBean[] setFlLogBeansByVerifyFace(FaceBean bean , LogBean[] importedBeans)
    {
        try {
            IBeanConverter<LogBean,FlLogBean> importedConverter = this.dbConverter.getLogBeanConverter();
            return importedConverter.fromRight(importedBeans,
                this.nativeManager.setFlLogBeansByVerifyFace(
                    this.beanConverter.toRight(bean),
                    importedConverter.toRight(importedBeans)
                ));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * set  the {@link LogBean} object java.util.Collection associate to FaceBean by the fl_log.verify_face field.<BR>
     * FK_NAME:fl_log_ibfk_3
     * @param bean the referenced {@link FaceBean} 
     * @param importedBeans imported beans from fl_log 
     * @return importedBeans always
     * @see {@link FlLogManager#setReferencedByVerifyFace(LogBean, FaceBean)
     */
    //3.4 SET IMPORTED
    public <C extends java.util.Collection<LogBean>> C setFlLogBeansByVerifyFace(FaceBean bean , C importedBeans)
    {
        try {
            IBeanConverter<LogBean,FlLogBean> importedConverter = this.dbConverter.getLogBeanConverter();
            if(importedBeans instanceof java.util.List){
                importedConverter.fromRight((java.util.List<LogBean>)importedBeans,nativeManager.setFlLogBeansByVerifyFace(
                    this.beanConverter.toRight(bean),
                    importedConverter.toRight(importedBeans)
                    ));
            }else{
                LogBean[] array = importedBeans.toArray(new LogBean[0]);
                importedConverter.fromRight(array,nativeManager.setFlLogBeansByVerifyFace(
                    this.beanConverter.toRight(bean),
                    importedConverter.toRight(array)
                    ));
            }
            return importedBeans;
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * Retrieves the {@link LogBean} object from the fl_log.compare_face field.<BR>
     * FK_NAME : fl_log_ibfk_4 
     * @param bean the {@link FaceBean}
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.1 GET IMPORTED
    public LogBean[] getFlLogBeansByCompareFace(FaceBean bean)
    {
        return this.getFlLogBeansByCompareFaceAsList(bean).toArray(new LogBean[0]);
    }

    /**
     * Retrieves the {@link LogBean} object from fl_log.compare_face field.<BR>
     * FK_NAME:fl_log_ibfk_4
     * @param bean the {@link FaceBean}
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET IMPORTED
    public java.util.List<LogBean> getFlLogBeansByCompareFaceAsList(FaceBean bean)
    {
        try {
            return this.dbConverter.getLogBeanConverter().fromRight(nativeManager.getFlLogBeansByCompareFaceAsList( this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * set  the {@link LogBean} object array associate to FaceBean by the fl_log.compare_face field.<BR>
     * FK_NAME : fl_log_ibfk_4 
     * @param bean the referenced {@link FaceBean}
     * @param importedBeans imported beans from fl_log
     * @return importedBeans always
     * @see {@link FlLogManager#setReferencedByCompareFace(LogBean, FaceBean)
     */
    //3.3 SET IMPORTED
    public LogBean[] setFlLogBeansByCompareFace(FaceBean bean , LogBean[] importedBeans)
    {
        try {
            IBeanConverter<LogBean,FlLogBean> importedConverter = this.dbConverter.getLogBeanConverter();
            return importedConverter.fromRight(importedBeans,
                this.nativeManager.setFlLogBeansByCompareFace(
                    this.beanConverter.toRight(bean),
                    importedConverter.toRight(importedBeans)
                ));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * set  the {@link LogBean} object java.util.Collection associate to FaceBean by the fl_log.compare_face field.<BR>
     * FK_NAME:fl_log_ibfk_4
     * @param bean the referenced {@link FaceBean} 
     * @param importedBeans imported beans from fl_log 
     * @return importedBeans always
     * @see {@link FlLogManager#setReferencedByCompareFace(LogBean, FaceBean)
     */
    //3.4 SET IMPORTED
    public <C extends java.util.Collection<LogBean>> C setFlLogBeansByCompareFace(FaceBean bean , C importedBeans)
    {
        try {
            IBeanConverter<LogBean,FlLogBean> importedConverter = this.dbConverter.getLogBeanConverter();
            if(importedBeans instanceof java.util.List){
                importedConverter.fromRight((java.util.List<LogBean>)importedBeans,nativeManager.setFlLogBeansByCompareFace(
                    this.beanConverter.toRight(bean),
                    importedConverter.toRight(importedBeans)
                    ));
            }else{
                LogBean[] array = importedBeans.toArray(new LogBean[0]);
                importedConverter.fromRight(array,nativeManager.setFlLogBeansByCompareFace(
                    this.beanConverter.toRight(bean),
                    importedConverter.toRight(array)
                    ));
            }
            return importedBeans;
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }



    /**
     * Save the FaceBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link FaceBean} bean to be saved
     * @param refFlImagebyImgMd5 the {@link ImageBean} bean referenced by {@link FaceBean} 
     * @param refFlPersonbyPersonId the {@link PersonBean} bean referenced by {@link FaceBean} 
     * @param impFlLogbyVerifyFace the {@link LogBean} bean refer to {@link FaceBean} 
     * @param impFlLogbyCompareFace the {@link LogBean} bean refer to {@link FaceBean} 
     * @return the inserted or updated {@link FaceBean} bean
     */
    //3.5 SYNC SAVE 
    public FaceBean save(FaceBean bean
        , ImageBean refFlImagebyImgMd5 , PersonBean refFlPersonbyPersonId 
        , LogBean[] impFlLogbyVerifyFace , LogBean[] impFlLogbyCompareFace )
    {
        try{
            return this.beanConverter.fromRight(bean,nativeManager.save(this.beanConverter.toRight(bean)
                , this.dbConverter.getImageBeanConverter().toRight(refFlImagebyImgMd5) , this.dbConverter.getPersonBeanConverter().toRight(refFlPersonbyPersonId)                 , this.dbConverter.getLogBeanConverter().toRight(impFlLogbyVerifyFace)  , this.dbConverter.getLogBeanConverter().toRight(impFlLogbyCompareFace)  ));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    } 
    /**
     * Transaction version for sync save
     * @see {@link #save(FaceBean , ImageBean , PersonBean , LogBean[] , LogBean[] )}
     */
    //3.6 SYNC SAVE AS TRANSACTION
    public FaceBean saveAsTransaction(final FaceBean bean
        ,final ImageBean refFlImagebyImgMd5 ,final PersonBean refFlPersonbyPersonId 
        ,final LogBean[] impFlLogbyVerifyFace ,final LogBean[] impFlLogbyCompareFace )
    {
        return this.runAsTransaction(new Callable<FaceBean>(){
            @Override
            public FaceBean call() throws Exception {
                return save(bean , refFlImagebyImgMd5 , refFlPersonbyPersonId , impFlLogbyVerifyFace , impFlLogbyCompareFace );
            }});
    }
    /**
     * Save the FaceBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link FaceBean} bean to be saved
     * @param refFlImagebyImgMd5 the {@link ImageBean} bean referenced by {@link FaceBean} 
     * @param refFlPersonbyPersonId the {@link PersonBean} bean referenced by {@link FaceBean} 
     * @param impFlLogbyVerifyFace the {@link LogBean} bean refer to {@link FaceBean} 
     * @param impFlLogbyCompareFace the {@link LogBean} bean refer to {@link FaceBean} 
     * @return the inserted or updated {@link FaceBean} bean
     */
    //3.7 SYNC SAVE 
    public FaceBean save(FaceBean bean
        , ImageBean refFlImagebyImgMd5 , PersonBean refFlPersonbyPersonId 
        , java.util.Collection<LogBean> impFlLogbyVerifyFace , java.util.Collection<LogBean> impFlLogbyCompareFace )
    {
        try{
            return this.beanConverter.fromRight(bean,nativeManager.save(this.beanConverter.toRight(bean)
                , this.dbConverter.getImageBeanConverter().toRight(refFlImagebyImgMd5) , this.dbConverter.getPersonBeanConverter().toRight(refFlPersonbyPersonId)                 , this.dbConverter.getLogBeanConverter().toRight(impFlLogbyVerifyFace)  , this.dbConverter.getLogBeanConverter().toRight(impFlLogbyCompareFace)  ));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }   
    /**
     * Transaction version for sync save
     * @see {@link #save(FaceBean , ImageBean , PersonBean , java.util.Collection , java.util.Collection )}
     */
    //3.8 SYNC SAVE AS TRANSACTION
    public FaceBean saveAsTransaction(final FaceBean bean
        ,final ImageBean refFlImagebyImgMd5 ,final PersonBean refFlPersonbyPersonId 
        ,final  java.util.Collection<LogBean> impFlLogbyVerifyFace ,final  java.util.Collection<LogBean> impFlLogbyCompareFace )
    {
        return this.runAsTransaction(new Callable<FaceBean>(){
            @Override
            public FaceBean call() throws Exception {
                return save(bean , refFlImagebyImgMd5 , refFlPersonbyPersonId , impFlLogbyVerifyFace , impFlLogbyCompareFace );
            }});
    }
      //////////////////////////////////////
    // FOREIGN KEY GENERIC METHOD
    //////////////////////////////////////

    /**
     * Retrieves the bean object referenced by fkIndex.<br>
     * @param <T>
     * <ul>
     *     <li> {@link TableManager#FL_FACE_FK_IMG_MD5} -> {@link ImageBean}</li>
     *     <li> {@link TableManager#FL_FACE_FK_PERSON_ID} -> {@link PersonBean}</li>
     * </ul>
     * @param bean the {@link FaceBean} object to use
     * @param fkIndex valid values: <br>
     *        {@link TableManager#FL_FACE_FK_IMG_MD5},{@link TableManager#FL_FACE_FK_PERSON_ID}
     * @return the associated <T> bean or {@code null} if {@code bean} or {@code beanToSet} is {@code null}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseBean> T getReferencedBean(FaceBean bean,int fkIndex){
        switch(fkIndex){
        case FL_FACE_FK_IMG_MD5:
            return  (T)this.getReferencedByImgMd5(bean);
        case FL_FACE_FK_PERSON_ID:
            return  (T)this.getReferencedByPersonId(bean);
        }
        throw new IllegalArgumentException(String.format("invalid fkIndex %d", fkIndex));
    }
    /**
     * Associates the {@link FaceBean} object to the bean object by fkIndex field.<br>
     * 
     * @param <T>
     * <ul>
     *     <li> {@link TableManager#FL_FACE_FK_IMG_MD5} -> {@link ImageBean}</li>
     *     <li> {@link TableManager#FL_FACE_FK_PERSON_ID} -> {@link PersonBean}</li>
     * </ul>
     * @param bean the {@link FaceBean} object to use
     * @param beanToSet the <T> object to associate to the {@link FaceBean}
     * @param fkIndex valid values: <br>
     *        {@link TableManager#FL_FACE_FK_IMG_MD5},{@link TableManager#FL_FACE_FK_PERSON_ID}
     * @return always beanToSet saved
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseBean> T setReferencedBean(FaceBean bean,T beanToSet,int fkIndex){
        switch(fkIndex){
        case FL_FACE_FK_IMG_MD5:
            return  (T)this.setReferencedByImgMd5(bean, (ImageBean)beanToSet);
        case FL_FACE_FK_PERSON_ID:
            return  (T)this.setReferencedByPersonId(bean, (PersonBean)beanToSet);
        }
        throw new IllegalArgumentException(String.format("invalid fkIndex %d", fkIndex));
    }
    
    //////////////////////////////////////
    // GET/SET FOREIGN KEY BEAN METHOD
    //////////////////////////////////////


    /**
     * Retrieves the {@link ImageBean} object referenced by {@link FaceBean#getImgMd5}() field.<br>
     * FK_NAME : fl_face_ibfk_1
     * @param bean the {@link FaceBean}
     * @return the associated {@link ImageBean} bean or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET REFERENCED VALUE
    public ImageBean getReferencedByImgMd5(FaceBean bean)
    {
        try{
            return this.dbConverter.getImageBeanConverter().fromRight(this.nativeManager.getReferencedByImgMd5(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
        
    }

    /**
     * Associates the {@link FaceBean} object to the {@link ImageBean} object by {@link FaceBean#getImgMd5}() field.
     *
     * @param bean the {@link FaceBean} object to use
     * @param beanToSet the {@link ImageBean} object to associate to the {@link FaceBean}
     * @return always beanToSet saved
     * @throws Exception
     */
    //5.2 SET REFERENCED 
    public ImageBean setReferencedByImgMd5(FaceBean bean, ImageBean beanToSet)
    {
        try{
            return this.dbConverter.getImageBeanConverter().fromRight(beanToSet,this.nativeManager.setReferencedByImgMd5(this.beanConverter.toRight(bean),this.dbConverter.getImageBeanConverter().toRight(beanToSet)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * Retrieves the {@link PersonBean} object referenced by {@link FaceBean#getPersonId}() field.<br>
     * FK_NAME : fl_face_ibfk_2
     * @param bean the {@link FaceBean}
     * @return the associated {@link PersonBean} bean or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET REFERENCED VALUE
    public PersonBean getReferencedByPersonId(FaceBean bean)
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
     * Associates the {@link FaceBean} object to the {@link PersonBean} object by {@link FaceBean#getPersonId}() field.
     *
     * @param bean the {@link FaceBean} object to use
     * @param beanToSet the {@link PersonBean} object to associate to the {@link FaceBean}
     * @return always beanToSet saved
     * @throws Exception
     */
    //5.2 SET REFERENCED 
    public PersonBean setReferencedByPersonId(FaceBean bean, PersonBean beanToSet)
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
    protected FaceBean insert(FaceBean bean)
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
    protected FaceBean update(FaceBean bean)
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
    public FaceBean loadUniqueUsingTemplate(FaceBean bean)
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
    public int loadUsingTemplate(FaceBean bean, int[] fieldList, int startRow, int numRows,int searchType, Action<FaceBean> action)
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
    public int deleteUsingTemplate(FaceBean bean)
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
     * Retrieves an array of FaceBean using the img_md5 index.
     *
     * @param imgMd5 the img_md5 column's value filter.
     * @return an array of FaceBean
     */
    public FaceBean[] loadByindexImgMd5(String imgMd5)
    {
        return this.loadByindexImgMd5AsList(imgMd5).toArray(new FaceBean[0]);
    }
    
    /**
     * Retrieves a list of FaceBean using the img_md5 index.
     *
     * @param imgMd5 the img_md5 column's value filter.
     * @return a list of FaceBean
     */
    public java.util.List<FaceBean> loadByindexImgMd5AsList(String imgMd5)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByindexImgMd5AsList(imgMd5));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    /**
     * Deletes rows using the img_md5 index.
     *
     * @param imgMd5 the img_md5 column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByindexImgMd5(String imgMd5)
    {
        try{
            return this.nativeManager.deleteByindexImgMd5(imgMd5);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
     /**
     * Retrieves an array of FaceBean using the person_id index.
     *
     * @param personId the person_id column's value filter.
     * @return an array of FaceBean
     */
    public FaceBean[] loadByindexPersonId(Integer personId)
    {
        return this.loadByindexPersonIdAsList(personId).toArray(new FaceBean[0]);
    }
    
    /**
     * Retrieves a list of FaceBean using the person_id index.
     *
     * @param personId the person_id column's value filter.
     * @return a list of FaceBean
     */
    public java.util.List<FaceBean> loadByindexPersonIdAsList(Integer personId)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadByindexPersonIdAsList(personId));
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
    public int deleteByindexPersonId(Integer personId)
    {
        try{
            return this.nativeManager.deleteByindexPersonId(personId);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
    
    /**
     * Retrieves a list of FaceBean using the index specified by keyIndex.
     * @param keyIndex valid values: <br>
     *        {@link TableManager#FL_FACE_INDEX_IMG_MD5},{@link TableManager#FL_FACE_INDEX_PERSON_ID}
     * @param keys key values of index
     * @return a list of FaceBean
     */
    @Override
    public java.util.List<FaceBean> loadByIndexAsList(int keyIndex,Object ...keys)
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
     *        {@link TableManager#FL_FACE_INDEX_IMG_MD5},{@link TableManager#FL_FACE_INDEX_PERSON_ID}
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
    public int countUsingTemplate(FaceBean bean, int searchType)
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
    public void registerListener(TableListener<FaceBean> listener)
    {
        this.nativeManager.registerListener(this.toNative(listener));
    }

    //36
    @Override
    public void unregisterListener(TableListener<FaceBean> listener)
    {
        this.nativeManager.unregisterListener(this.toNative(listener));
    }
    
    private net.gdface.facelog.dborm.TableListener<FlFaceBean> toNative(final TableListener<FaceBean> listener) {
        return null == listener ?null:new net.gdface.facelog.dborm.TableListener<FlFaceBean> (){

            @Override
            public void beforeInsert(FlFaceBean bean) throws DAOException {
                listener.beforeInsert(FaceManager.this.beanConverter.fromRight(bean));                
            }

            @Override
            public void afterInsert(FlFaceBean bean) throws DAOException {
                listener.afterInsert(FaceManager.this.beanConverter.fromRight(bean));
                
            }

            @Override
            public void beforeUpdate(FlFaceBean bean) throws DAOException {
                listener.beforeUpdate(FaceManager.this.beanConverter.fromRight(bean));
                
            }

            @Override
            public void afterUpdate(FlFaceBean bean) throws DAOException {
                listener.afterUpdate(FaceManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public void beforeDelete(FlFaceBean bean) throws DAOException {
                listener.beforeDelete(FaceManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public void afterDelete(FlFaceBean bean) throws DAOException {
                listener.afterDelete(FaceManager.this.beanConverter.fromRight(bean));
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
    public int loadBySqlForAction(String sql, Object[] argList, int[] fieldList,int startRow, int numRows,Action<FaceBean> action){
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
    
    @Override
    public void runAsTransaction(final Runnable fun){
        try{
            this.nativeManager.runAsTransaction(fun);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    private net.gdface.facelog.dborm.TableManager.Action<FlFaceBean> toNative(final Action<FaceBean> action){
        if(null == action)
            throw new NullPointerException();
        return new net.gdface.facelog.dborm.TableManager.Action<FlFaceBean>(){

            @Override
            public void call(FlFaceBean bean) {
                action.call(FaceManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public FlFaceBean getBean() {
                return  FaceManager.this.beanConverter.toRight(action.getBean());
            }};
    }
}
