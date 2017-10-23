// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// template: manager.interface.java.vm
// ______________________________________________________
package net.gdface.facelog.db;

/**
 * Interface to handle database calls (save, load, count, etc...) for the fl_person table.<br>
 * Remarks: 人员基本描述信息
 * @author guyadong
 */
public interface IPersonManager extends TableManager<PersonBean>
{  
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    /**
     * Loads a {@link PersonBean} from the fl_person using primary key fields.
     *
     * @param id Integer - PK# 1
     * @return a unique PersonBean or {@code null} if not found
     */
    //1
    public PersonBean loadByPrimaryKey(Integer id);

    
    /**
     * Returns true if this fl_person contains row with primary key fields.
     * @param id Integer - PK# 1
     * @see #loadByPrimaryKey($keys)
     */
    //1.4
    public boolean existsPrimaryKey(Integer id);
    /**
     * Check duplicated row by primary keys,if row exists throw exception
     * @param id Integer
     */
    //1.4.1
    public Integer checkDuplicate(Integer id);
    /**
     * Loads {@link PersonBean} from the fl_person using primary key fields.
     *
     * @param keys primary keys array
     * @return list of PersonBean
     */
    //1.8
    public java.util.List<PersonBean> loadByPrimaryKey(int... keys);
    /**
     * Loads {@link PersonBean} from the fl_person using primary key fields.
     *
     * @param keys primary keys collection
     * @return list of PersonBean
     */
    //1.9
    public java.util.List<PersonBean> loadByPrimaryKey(java.util.Collection<Integer> keys);
    /**
     * Delete row according to its primary keys.<br>
     * all keys must not be null
     *
     * @param id Integer - PK# 1
     * @return the number of deleted rows
     */
    //2
    public int deleteByPrimaryKey(Integer id);
    /**
     * Delete rows according to primary key.<br>
     *
     * @param keys primary keys array
     * @return the number of deleted rows
     */
    //2.2
    public int deleteByPrimaryKey(int... keys);
    /**
     * Delete rows according to primary key.<br>
     *
     * @param keys primary keys collection
     * @return the number of deleted rows
     */
    //2.3
    public int deleteByPrimaryKey(java.util.Collection<Integer> keys);
    /**
     * Delete beans.<br>
     *
     * @param beans PersonBean collection wille be deleted
     * @return the number of deleted rows
     */
    //2.4
    public int delete(PersonBean... beans);
    /**
     * Delete beans.<br>
     *
     * @param beans PersonBean collection wille be deleted
     * @return the number of deleted rows
     */
    //2.5
    public int delete(java.util.Collection<PersonBean> beans);
 

    //////////////////////////////////////
    // GET/SET IMPORTED KEY BEAN METHOD
    //////////////////////////////////////
    /**
     * Retrieves the {@link FeatureBean} object from the fl_feature.person_id field.<BR>
     * FK_NAME : fl_feature_ibfk_1 
     * @param bean the {@link PersonBean}
     * @return the associated {@link FeatureBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.1 GET IMPORTED
    public FeatureBean[] getFeatureBeansByPersonId(PersonBean bean);
    
    /**
     * Retrieves the {@link FeatureBean} object from the fl_feature.person_id field.<BR>
     * FK_NAME : fl_feature_ibfk_1 
     * @param id Integer - PK# 1
     * @return the associated {@link FeatureBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DAOException
     */
    //3.1.2 GET IMPORTED
    public FeatureBean[] getFeatureBeansByPersonId(Integer personId);
    
    /**
     * Retrieves the {@link FeatureBean} object from fl_feature.person_id field.<BR>
     * FK_NAME:fl_feature_ibfk_1
     * @param bean the {@link PersonBean}
     * @return the associated {@link FeatureBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET IMPORTED
    public java.util.List<FeatureBean> getFeatureBeansByPersonIdAsList(PersonBean bean);

    /**
     * Retrieves the {@link FeatureBean} object from fl_feature.person_id field.<BR>
     * FK_NAME:fl_feature_ibfk_1
     * @param id Integer - PK# 1
     * @return the associated {@link FeatureBean} beans 
     * @throws DAOException
     */
    //3.2.2 GET IMPORTED
    public java.util.List<FeatureBean> getFeatureBeansByPersonIdAsList(Integer personId);
    /**
     * delete the associated {@link FeatureBean} objects from fl_feature.person_id field.<BR>
     * FK_NAME:fl_feature_ibfk_1
     * @param id Integer - PK# 1
     * @return the number of deleted rows
     */
    //3.2.3 DELETE IMPORTED
    public int deleteFeatureBeansByPersonId(Integer personId);
    
    /**
     * set  the {@link FeatureBean} object array associate to PersonBean by the fl_feature.person_id field.<BR>
     * FK_NAME : fl_feature_ibfk_1 
     * @param bean the referenced {@link PersonBean}
     * @param importedBeans imported beans from fl_feature
     * @return importedBeans always
     * @see {@link FeatureManager#setReferencedByPersonId(FeatureBean, PersonBean)
     */
    //3.3 SET IMPORTED
    public FeatureBean[] setFeatureBeansByPersonId(PersonBean bean , FeatureBean[] importedBeans);

    /**
     * set  the {@link FeatureBean} object java.util.Collection associate to PersonBean by the fl_feature.person_id field.<BR>
     * FK_NAME:fl_feature_ibfk_1
     * @param bean the referenced {@link PersonBean} 
     * @param importedBeans imported beans from fl_feature 
     * @return importedBeans always
     * @see {@link FeatureManager#setReferencedByPersonId(FeatureBean, PersonBean)
     */
    //3.4 SET IMPORTED
    public <C extends java.util.Collection<FeatureBean>> C setFeatureBeansByPersonId(PersonBean bean , C importedBeans);

    /**
     * Retrieves the {@link LogBean} object from the fl_log.person_id field.<BR>
     * FK_NAME : fl_log_ibfk_1 
     * @param bean the {@link PersonBean}
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.1 GET IMPORTED
    public LogBean[] getLogBeansByPersonId(PersonBean bean);
    
    /**
     * Retrieves the {@link LogBean} object from the fl_log.person_id field.<BR>
     * FK_NAME : fl_log_ibfk_1 
     * @param id Integer - PK# 1
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DAOException
     */
    //3.1.2 GET IMPORTED
    public LogBean[] getLogBeansByPersonId(Integer personId);
    
    /**
     * Retrieves the {@link LogBean} object from fl_log.person_id field.<BR>
     * FK_NAME:fl_log_ibfk_1
     * @param bean the {@link PersonBean}
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET IMPORTED
    public java.util.List<LogBean> getLogBeansByPersonIdAsList(PersonBean bean);

    /**
     * Retrieves the {@link LogBean} object from fl_log.person_id field.<BR>
     * FK_NAME:fl_log_ibfk_1
     * @param id Integer - PK# 1
     * @return the associated {@link LogBean} beans 
     * @throws DAOException
     */
    //3.2.2 GET IMPORTED
    public java.util.List<LogBean> getLogBeansByPersonIdAsList(Integer personId);
    /**
     * delete the associated {@link LogBean} objects from fl_log.person_id field.<BR>
     * FK_NAME:fl_log_ibfk_1
     * @param id Integer - PK# 1
     * @return the number of deleted rows
     */
    //3.2.3 DELETE IMPORTED
    public int deleteLogBeansByPersonId(Integer personId);
    
    /**
     * set  the {@link LogBean} object array associate to PersonBean by the fl_log.person_id field.<BR>
     * FK_NAME : fl_log_ibfk_1 
     * @param bean the referenced {@link PersonBean}
     * @param importedBeans imported beans from fl_log
     * @return importedBeans always
     * @see {@link LogManager#setReferencedByPersonId(LogBean, PersonBean)
     */
    //3.3 SET IMPORTED
    public LogBean[] setLogBeansByPersonId(PersonBean bean , LogBean[] importedBeans);

    /**
     * set  the {@link LogBean} object java.util.Collection associate to PersonBean by the fl_log.person_id field.<BR>
     * FK_NAME:fl_log_ibfk_1
     * @param bean the referenced {@link PersonBean} 
     * @param importedBeans imported beans from fl_log 
     * @return importedBeans always
     * @see {@link LogManager#setReferencedByPersonId(LogBean, PersonBean)
     */
    //3.4 SET IMPORTED
    public <C extends java.util.Collection<LogBean>> C setLogBeansByPersonId(PersonBean bean , C importedBeans);

    /**
     * Save the PersonBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link PersonBean} bean to be saved
     * @param refImageByImageMd5 the {@link ImageBean} bean referenced by {@link PersonBean} 
     * @param impFeatureByPersonId the {@link FeatureBean} bean refer to {@link PersonBean} 
     * @param impLogByPersonId the {@link LogBean} bean refer to {@link PersonBean} 
     * @return the inserted or updated {@link PersonBean} bean
     */
    //3.5 SYNC SAVE 
    public PersonBean save(PersonBean bean
        , ImageBean refImageByImageMd5 
        , FeatureBean[] impFeatureByPersonId , LogBean[] impLogByPersonId );
    /**
     * Transaction version for sync save
     * @see {@link #save(PersonBean , ImageBean , FeatureBean[] , LogBean[] )}
     */
    //3.6 SYNC SAVE AS TRANSACTION
    public PersonBean saveAsTransaction(final PersonBean bean
        ,final ImageBean refImageByImageMd5 
        ,final FeatureBean[] impFeatureByPersonId ,final LogBean[] impLogByPersonId );
    /**
     * Save the PersonBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link PersonBean} bean to be saved
     * @param refImageByImageMd5 the {@link ImageBean} bean referenced by {@link PersonBean} 
     * @param impFeatureByPersonId the {@link FeatureBean} bean refer to {@link PersonBean} 
     * @param impLogByPersonId the {@link LogBean} bean refer to {@link PersonBean} 
     * @return the inserted or updated {@link PersonBean} bean
     */
    //3.7 SYNC SAVE 
    public PersonBean save(PersonBean bean
        , ImageBean refImageByImageMd5 
        , java.util.Collection<FeatureBean> impFeatureByPersonId , java.util.Collection<LogBean> impLogByPersonId );
    /**
     * Transaction version for sync save
     * @see {@link #save(PersonBean , ImageBean , java.util.Collection , java.util.Collection )}
     */
    //3.8 SYNC SAVE AS TRANSACTION
    public PersonBean saveAsTransaction(final PersonBean bean
        ,final ImageBean refImageByImageMd5 
        ,final  java.util.Collection<FeatureBean> impFeatureByPersonId ,final  java.util.Collection<LogBean> impLogByPersonId );
      //////////////////////////////////////
    // GET/SET FOREIGN KEY BEAN METHOD
    //////////////////////////////////////
    /**
     * Retrieves the {@link ImageBean} object referenced by {@link PersonBean#getImageMd5}() field.<br>
     * FK_NAME : fl_person_ibfk_1
     * @param bean the {@link PersonBean}
     * @return the associated {@link ImageBean} bean or {@code null} if {@code bean} is {@code null}
     */
    //5.1 GET REFERENCED VALUE
    public ImageBean getReferencedByImageMd5(PersonBean bean);

    /**
     * Associates the {@link PersonBean} object to the {@link ImageBean} object by {@link PersonBean#getImageMd5}() field.
     *
     * @param bean the {@link PersonBean} object to use
     * @param beanToSet the {@link ImageBean} object to associate to the {@link PersonBean}
     * @return always beanToSet saved
     * @throws Exception
     */
    //5.2 SET REFERENCED 
    public ImageBean setReferencedByImageMd5(PersonBean bean, ImageBean beanToSet);
    //_____________________________________________________________________
    //
    // USING INDICES
    //_____________________________________________________________________


    /**
     * Retrieves an unique PersonBean using the image_md5 index.
     *
     * @param imageMd5 the image_md5 column's value filter. must not be null
     * @return 
     */
    public PersonBean loadByIndexImageMd5(String imageMd5);
    /**
     * Retrieves an unique PersonBean for each image_md5 index.
     *
     * @param indexs index array
     * @return an list of PersonBean
     */
    public java.util.List<PersonBean> loadByIndexImageMd5(String... indexs);
    /**
     * Retrieves an unique PersonBean for each image_md5 index.
     *
     * @param indexs index collection
     * @return an list of PersonBean
     */
    public java.util.List<PersonBean> loadByIndexImageMd5(java.util.Collection<String> indexs);
    /**
     * Deletes rows for each image_md5 index.
     *
     * @param indexs index array
     * @return the number of deleted rows
     */
    public int deleteByIndexImageMd5(String... indexs);
    /**
     * Deletes rows for each image_md5 index.
     *
     * @param indexs index collection
     * @return the number of deleted rows
     */
    public int deleteByIndexImageMd5(java.util.Collection<String> indexs);

    /**
     * Deletes rows using the image_md5 index.
     *
     * @param imageMd5 the image_md5 column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexImageMd5(String imageMd5);
    

    /**
     * Retrieves an unique PersonBean using the papers_num index.
     *
     * @param papersNum the papers_num column's value filter. must not be null
     * @return 
     */
    public PersonBean loadByIndexPapersNum(String papersNum);
    /**
     * Retrieves an unique PersonBean for each papers_num index.
     *
     * @param indexs index array
     * @return an list of PersonBean
     */
    public java.util.List<PersonBean> loadByIndexPapersNum(String... indexs);
    /**
     * Retrieves an unique PersonBean for each papers_num index.
     *
     * @param indexs index collection
     * @return an list of PersonBean
     */
    public java.util.List<PersonBean> loadByIndexPapersNum(java.util.Collection<String> indexs);
    /**
     * Deletes rows for each papers_num index.
     *
     * @param indexs index array
     * @return the number of deleted rows
     */
    public int deleteByIndexPapersNum(String... indexs);
    /**
     * Deletes rows for each papers_num index.
     *
     * @param indexs index collection
     * @return the number of deleted rows
     */
    public int deleteByIndexPapersNum(java.util.Collection<String> indexs);

    /**
     * Deletes rows using the papers_num index.
     *
     * @param papersNum the papers_num column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexPapersNum(String papersNum);
    

     /**
     * Retrieves an array of PersonBean using the expiry_date index.
     *
     * @param expiryDate the expiry_date column's value filter.
     * @return an array of PersonBean
     */
    public PersonBean[] loadByIndexExpiryDate(java.util.Date expiryDate);
    
    /**
     * Retrieves a list of PersonBean using the expiry_date index.
     *
     * @param expiryDate the expiry_date column's value filter.
     * @return a list of PersonBean
     */
    public java.util.List<PersonBean> loadByIndexExpiryDateAsList(java.util.Date expiryDate);

    /**
     * Deletes rows using the expiry_date index.
     *
     * @param expiryDate the expiry_date column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexExpiryDate(java.util.Date expiryDate);
    

     /**
     * Retrieves an array of PersonBean using the group_id index.
     *
     * @param groupId the group_id column's value filter.
     * @return an array of PersonBean
     */
    public PersonBean[] loadByIndexGroupId(Integer groupId);
    
    /**
     * Retrieves a list of PersonBean using the group_id index.
     *
     * @param groupId the group_id column's value filter.
     * @return a list of PersonBean
     */
    public java.util.List<PersonBean> loadByIndexGroupIdAsList(Integer groupId);

    /**
     * Deletes rows using the group_id index.
     *
     * @param groupId the group_id column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexGroupId(Integer groupId);
    

    /**
     * return a primary key list from {@link PersonBean} array
     * @param array
     */
    //45
    public java.util.List<Integer> toPrimaryKeyList(PersonBean... array);
    /**
     * return a primary key list from {@link PersonBean} collection
     * @param array
     */
    //46
    public java.util.List<Integer> toPrimaryKeyList(java.util.Collection<PersonBean> collection);
}
