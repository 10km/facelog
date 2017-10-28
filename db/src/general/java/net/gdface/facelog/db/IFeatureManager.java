// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: manager.interface.java.vm
// ______________________________________________________
package net.gdface.facelog.db;
import net.gdface.facelog.db.exception.ObjectRetrievalException;
import net.gdface.facelog.db.exception.WrapDAOException;

/**
 * Interface to handle database calls (save, load, count, etc...) for the fl_feature table.<br>
 * Remarks: 用于验证身份的人脸特征数据表
 * @author guyadong
 */
public interface IFeatureManager extends TableManager<FeatureBean>
{  
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    /**
     * Loads a {@link FeatureBean} from the fl_feature using primary key fields.
     *
     * @param md5 String - PK# 1
     * @return a unique FeatureBean or {@code null} if not found
     */
    //1
    public FeatureBean loadByPrimaryKey(String md5);

    /**
     * Loads a {@link FeatureBean} from the fl_feature using primary key fields.
     *
     * @param md5 String - PK# 1
     * @return a unique FeatureBean
     * @throws ObjectRetrievalException if not found
     */
    //1.1
    public FeatureBean loadByPrimaryKeyChecked(String md5) throws ObjectRetrievalException;
    
    /**
     * Returns true if this fl_feature contains row with primary key fields.
     * @param md5 String - PK# 1
     * @see #loadByPrimaryKey($keys)
     */
    //1.4
    public boolean existsPrimaryKey(String md5);
    /**
     * Check duplicated row by primary keys,if row exists throw exception
     * @param md5 String
     */
    //1.4.1
    public String checkDuplicate(String md5);
    /**
     * Loads {@link FeatureBean} from the fl_feature using primary key fields.
     *
     * @param keys primary keys array
     * @return list of FeatureBean
     */
    //1.8
    public java.util.List<FeatureBean> loadByPrimaryKey(String... keys);
    /**
     * Loads {@link FeatureBean} from the fl_feature using primary key fields.
     *
     * @param keys primary keys collection
     * @return list of FeatureBean
     */
    //1.9
    public java.util.List<FeatureBean> loadByPrimaryKey(java.util.Collection<String> keys);
    /**
     * Delete row according to its primary keys.<br>
     * all keys must not be null
     *
     * @param md5 String - PK# 1
     * @return the number of deleted rows
     */
    //2
    public int deleteByPrimaryKey(String md5);
    /**
     * Delete rows according to primary key.<br>
     *
     * @param keys primary keys array
     * @return the number of deleted rows
     */
    //2.2
    public int deleteByPrimaryKey(String... keys);
    /**
     * Delete rows according to primary key.<br>
     *
     * @param keys primary keys collection
     * @return the number of deleted rows
     */
    //2.3
    public int deleteByPrimaryKey(java.util.Collection<String> keys);
    /**
     * Delete beans.<br>
     *
     * @param beans FeatureBean collection wille be deleted
     * @return the number of deleted rows
     */
    //2.4
    public int delete(FeatureBean... beans);
    /**
     * Delete beans.<br>
     *
     * @param beans FeatureBean collection wille be deleted
     * @return the number of deleted rows
     */
    //2.5
    public int delete(java.util.Collection<FeatureBean> beans);
 

    //////////////////////////////////////
    // GET/SET IMPORTED KEY BEAN METHOD
    //////////////////////////////////////
    /**
     * Retrieves the {@link FaceBean} object from the fl_face.feature_md5 field.<BR>
     * FK_NAME : fl_face_ibfk_2 
     * @param bean the {@link FeatureBean}
     * @return the associated {@link FaceBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.1 GET IMPORTED
    public FaceBean[] getFaceBeansByFeatureMd5(FeatureBean bean);
    
    /**
     * Retrieves the {@link FaceBean} object from the fl_face.feature_md5 field.<BR>
     * FK_NAME : fl_face_ibfk_2 
     * @param md5 String - PK# 1
     * @return the associated {@link FaceBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DAOException
     */
    //3.1.2 GET IMPORTED
    public FaceBean[] getFaceBeansByFeatureMd5(String featureMd5);
    
    /**
     * Retrieves the {@link FaceBean} object from fl_face.feature_md5 field.<BR>
     * FK_NAME:fl_face_ibfk_2
     * @param bean the {@link FeatureBean}
     * @return the associated {@link FaceBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET IMPORTED
    public java.util.List<FaceBean> getFaceBeansByFeatureMd5AsList(FeatureBean bean);

    /**
     * Retrieves the {@link FaceBean} object from fl_face.feature_md5 field.<BR>
     * FK_NAME:fl_face_ibfk_2
     * @param md5 String - PK# 1
     * @return the associated {@link FaceBean} beans 
     * @throws DAOException
     */
    //3.2.2 GET IMPORTED
    public java.util.List<FaceBean> getFaceBeansByFeatureMd5AsList(String featureMd5);
    /**
     * delete the associated {@link FaceBean} objects from fl_face.feature_md5 field.<BR>
     * FK_NAME:fl_face_ibfk_2
     * @param md5 String - PK# 1
     * @return the number of deleted rows
     */
    //3.2.3 DELETE IMPORTED
    public int deleteFaceBeansByFeatureMd5(String featureMd5);
    
    /**
     * set  the {@link FaceBean} object array associate to FeatureBean by the fl_face.feature_md5 field.<BR>
     * FK_NAME : fl_face_ibfk_2 
     * @param bean the referenced {@link FeatureBean}
     * @param importedBeans imported beans from fl_face
     * @return importedBeans always
     * @see {@link FaceManager#setReferencedByFeatureMd5(FaceBean, FeatureBean)
     */
    //3.3 SET IMPORTED
    public FaceBean[] setFaceBeansByFeatureMd5(FeatureBean bean , FaceBean[] importedBeans);

    /**
     * set  the {@link FaceBean} object java.util.Collection associate to FeatureBean by the fl_face.feature_md5 field.<BR>
     * FK_NAME:fl_face_ibfk_2
     * @param bean the referenced {@link FeatureBean} 
     * @param importedBeans imported beans from fl_face 
     * @return importedBeans always
     * @see {@link FaceManager#setReferencedByFeatureMd5(FaceBean, FeatureBean)
     */
    //3.4 SET IMPORTED
    public <C extends java.util.Collection<FaceBean>> C setFaceBeansByFeatureMd5(FeatureBean bean , C importedBeans);

    /**
     * Retrieves the {@link LogBean} object from the fl_log.verify_feature field.<BR>
     * FK_NAME : fl_log_ibfk_3 
     * @param bean the {@link FeatureBean}
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.1 GET IMPORTED
    public LogBean[] getLogBeansByVerifyFeature(FeatureBean bean);
    
    /**
     * Retrieves the {@link LogBean} object from the fl_log.verify_feature field.<BR>
     * FK_NAME : fl_log_ibfk_3 
     * @param md5 String - PK# 1
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DAOException
     */
    //3.1.2 GET IMPORTED
    public LogBean[] getLogBeansByVerifyFeature(String featureMd5);
    
    /**
     * Retrieves the {@link LogBean} object from fl_log.verify_feature field.<BR>
     * FK_NAME:fl_log_ibfk_3
     * @param bean the {@link FeatureBean}
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET IMPORTED
    public java.util.List<LogBean> getLogBeansByVerifyFeatureAsList(FeatureBean bean);

    /**
     * Retrieves the {@link LogBean} object from fl_log.verify_feature field.<BR>
     * FK_NAME:fl_log_ibfk_3
     * @param md5 String - PK# 1
     * @return the associated {@link LogBean} beans 
     * @throws DAOException
     */
    //3.2.2 GET IMPORTED
    public java.util.List<LogBean> getLogBeansByVerifyFeatureAsList(String featureMd5);
    /**
     * delete the associated {@link LogBean} objects from fl_log.verify_feature field.<BR>
     * FK_NAME:fl_log_ibfk_3
     * @param md5 String - PK# 1
     * @return the number of deleted rows
     */
    //3.2.3 DELETE IMPORTED
    public int deleteLogBeansByVerifyFeature(String featureMd5);
    
    /**
     * set  the {@link LogBean} object array associate to FeatureBean by the fl_log.verify_feature field.<BR>
     * FK_NAME : fl_log_ibfk_3 
     * @param bean the referenced {@link FeatureBean}
     * @param importedBeans imported beans from fl_log
     * @return importedBeans always
     * @see {@link LogManager#setReferencedByVerifyFeature(LogBean, FeatureBean)
     */
    //3.3 SET IMPORTED
    public LogBean[] setLogBeansByVerifyFeature(FeatureBean bean , LogBean[] importedBeans);

    /**
     * set  the {@link LogBean} object java.util.Collection associate to FeatureBean by the fl_log.verify_feature field.<BR>
     * FK_NAME:fl_log_ibfk_3
     * @param bean the referenced {@link FeatureBean} 
     * @param importedBeans imported beans from fl_log 
     * @return importedBeans always
     * @see {@link LogManager#setReferencedByVerifyFeature(LogBean, FeatureBean)
     */
    //3.4 SET IMPORTED
    public <C extends java.util.Collection<LogBean>> C setLogBeansByVerifyFeature(FeatureBean bean , C importedBeans);

    /**
     * Save the FeatureBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link FeatureBean} bean to be saved
     * @param refPersonByPersonId the {@link PersonBean} bean referenced by {@link FeatureBean} 
     * @param impFaceByFeatureMd5 the {@link FaceBean} bean refer to {@link FeatureBean} 
     * @param impLogByVerifyFeature the {@link LogBean} bean refer to {@link FeatureBean} 
     * @return the inserted or updated {@link FeatureBean} bean
     */
    //3.5 SYNC SAVE 
    public FeatureBean save(FeatureBean bean
        , PersonBean refPersonByPersonId 
        , FaceBean[] impFaceByFeatureMd5 , LogBean[] impLogByVerifyFeature );
    /**
     * Transaction version for sync save
     * @see {@link #save(FeatureBean , PersonBean , FaceBean[] , LogBean[] )}
     */
    //3.6 SYNC SAVE AS TRANSACTION
    public FeatureBean saveAsTransaction(final FeatureBean bean
        ,final PersonBean refPersonByPersonId 
        ,final FaceBean[] impFaceByFeatureMd5 ,final LogBean[] impLogByVerifyFeature );
    /**
     * Save the FeatureBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link FeatureBean} bean to be saved
     * @param refPersonByPersonId the {@link PersonBean} bean referenced by {@link FeatureBean} 
     * @param impFaceByFeatureMd5 the {@link FaceBean} bean refer to {@link FeatureBean} 
     * @param impLogByVerifyFeature the {@link LogBean} bean refer to {@link FeatureBean} 
     * @return the inserted or updated {@link FeatureBean} bean
     */
    //3.7 SYNC SAVE 
    public FeatureBean save(FeatureBean bean
        , PersonBean refPersonByPersonId 
        , java.util.Collection<FaceBean> impFaceByFeatureMd5 , java.util.Collection<LogBean> impLogByVerifyFeature );
    /**
     * Transaction version for sync save
     * @see {@link #save(FeatureBean , PersonBean , java.util.Collection , java.util.Collection )}
     */
    //3.8 SYNC SAVE AS TRANSACTION
    public FeatureBean saveAsTransaction(final FeatureBean bean
        ,final PersonBean refPersonByPersonId 
        ,final  java.util.Collection<FaceBean> impFaceByFeatureMd5 ,final  java.util.Collection<LogBean> impLogByVerifyFeature );
      //////////////////////////////////////
    // GET/SET FOREIGN KEY BEAN METHOD
    //////////////////////////////////////
    /**
     * Retrieves the {@link PersonBean} object referenced by {@link FeatureBean#getPersonId}() field.<br>
     * FK_NAME : fl_feature_ibfk_1
     * @param bean the {@link FeatureBean}
     * @return the associated {@link PersonBean} bean or {@code null} if {@code bean} is {@code null}
     */
    //5.1 GET REFERENCED VALUE
    public PersonBean getReferencedByPersonId(FeatureBean bean);

    /**
     * Associates the {@link FeatureBean} object to the {@link PersonBean} object by {@link FeatureBean#getPersonId}() field.
     *
     * @param bean the {@link FeatureBean} object to use
     * @param beanToSet the {@link PersonBean} object to associate to the {@link FeatureBean}
     * @return always beanToSet saved
     * @throws WrapDAOException
     */
    //5.2 SET REFERENCED 
    public PersonBean setReferencedByPersonId(FeatureBean bean, PersonBean beanToSet);
    //_____________________________________________________________________
    //
    // USING INDICES
    //_____________________________________________________________________


     /**
     * Retrieves an array of FeatureBean using the person_id index.
     *
     * @param personId the person_id column's value filter.
     * @return an array of FeatureBean
     */
    public FeatureBean[] loadByIndexPersonId(Integer personId);
    
    /**
     * Retrieves a list of FeatureBean using the person_id index.
     *
     * @param personId the person_id column's value filter.
     * @return a list of FeatureBean
     */
    public java.util.List<FeatureBean> loadByIndexPersonIdAsList(Integer personId);

    /**
     * Deletes rows using the person_id index.
     *
     * @param personId the person_id column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexPersonId(Integer personId);
    

    /**
     * return a primary key list from {@link FeatureBean} array
     * @param array
     */
    //45
    public java.util.List<String> toPrimaryKeyList(FeatureBean... array);
    /**
     * return a primary key list from {@link FeatureBean} collection
     * @param array
     */
    //46
    public java.util.List<String> toPrimaryKeyList(java.util.Collection<FeatureBean> collection);
}
