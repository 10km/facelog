// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: manager.interface.java.vm
// ______________________________________________________
package net.gdface.facelog.db;
import net.gdface.facelog.db.exception.ObjectRetrievalException;
import net.gdface.facelog.db.exception.WrapDaoException;

/**
 * Interface to handle database calls (save, load, count, etc...) for the fl_device table.<br>
 * Remarks: 前端设备基本信息
 * @author guyadong
 */
public interface IDeviceManager extends TableManager<DeviceBean>
{  
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    //1
    /**
     * Loads a {@link DeviceBean} from the fl_device using primary key fields.
     *
     * @param id Integer - PK# 1
     * @return a unique DeviceBean or {@code null} if not found
     */
    public DeviceBean loadByPrimaryKey(Integer id);

    //1.1
    /**
     * Loads a {@link DeviceBean} from the fl_device using primary key fields.
     *
     * @param id Integer - PK# 1
     * @return a unique DeviceBean
     * @throws ObjectRetrievalException if not found
     */
    public DeviceBean loadByPrimaryKeyChecked(Integer id) throws ObjectRetrievalException;
    
    //1.4
    /**
     * Returns true if this fl_device contains row with primary key fields.
     * @param id Integer - PK# 1
     * @see #loadByPrimaryKey($keys)
     * @return
     */
    public boolean existsPrimaryKey(Integer id);
    //1.4.1
    /**
     * Check duplicated row by primary keys,if row exists throw exception
     * @param id Integer
     * @return 
     * @throws ObjectRetrievalException
     */
    public Integer checkDuplicate(Integer id)throws ObjectRetrievalException;
    //1.8
    /**
     * Loads {@link DeviceBean} from the fl_device using primary key fields.
     *
     * @param keys primary keys array
     * @return list of DeviceBean
     */
    public java.util.List<DeviceBean> loadByPrimaryKey(int... keys);
    //1.9
    /**
     * Loads {@link DeviceBean} from the fl_device using primary key fields.
     *
     * @param keys primary keys collection
     * @return list of DeviceBean
     */
    public java.util.List<DeviceBean> loadByPrimaryKey(java.util.Collection<Integer> keys);
    //2
    /**
     * Delete row according to its primary keys.<br>
     * all keys must not be null
     *
     * @param id Integer - PK# 1
     * @return the number of deleted rows
     */
    public int deleteByPrimaryKey(Integer id);
    //2.2
    /**
     * Delete rows according to primary key.<br>
     *
     * @param keys primary keys array
     * @return the number of deleted rows
     */
    public int deleteByPrimaryKey(int... keys);
    //2.3
    /**
     * Delete rows according to primary key.<br>
     *
     * @param keys primary keys collection
     * @return the number of deleted rows
     */
    public int deleteByPrimaryKey(java.util.Collection<Integer> keys);
    //2.4
    /**
     * Delete beans.<br>
     *
     * @param beans DeviceBean collection wille be deleted
     * @return the number of deleted rows
     */
    public int delete(DeviceBean... beans);
    //2.5
    /**
     * Delete beans.<br>
     *
     * @param beans DeviceBean collection wille be deleted
     * @return the number of deleted rows
     */
    public int delete(java.util.Collection<DeviceBean> beans);
 

    //////////////////////////////////////
    // GET/SET IMPORTED KEY BEAN METHOD
    //////////////////////////////////////
    //3.1 GET IMPORTED
    /**
     * Retrieves the {@link ImageBean} object from the fl_image.device_id field.<BR>
     * FK_NAME : fl_image_ibfk_1 
     * @param bean the {@link DeviceBean}
     * @return the associated {@link ImageBean} beans or {@code null} if {@code bean} is {@code null}
     */
    public ImageBean[] getImageBeansByDeviceId(DeviceBean bean);
    
    //3.1.2 GET IMPORTED
    /**
     * Retrieves the {@link ImageBean} object from the fl_image.device_id field.<BR>
     * FK_NAME : fl_image_ibfk_1 
     * @param idOfDevice Integer - PK# 1
     * @return the associated {@link ImageBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DaoException
     */
    public ImageBean[] getImageBeansByDeviceId(Integer idOfDevice);
    
    //3.2 GET IMPORTED
    /**
     * see also #getImageBeansByDeviceIdAsList(DeviceBean,int,int)
     * @param bean
     * @return
     */
    public java.util.List<ImageBean> getImageBeansByDeviceIdAsList(DeviceBean bean);

    //3.2.2 GET IMPORTED
    /**
     * Retrieves the {@link ImageBean} object from fl_image.device_id field.<BR>
     * FK_NAME:fl_image_ibfk_1
     * @param idOfDevice Integer - PK# 1
     * @return the associated {@link ImageBean} beans 
     * @throws DaoException
     */
    public java.util.List<ImageBean> getImageBeansByDeviceIdAsList(Integer idOfDevice);
    //3.2.3 DELETE IMPORTED
    /**
     * delete the associated {@link ImageBean} objects from fl_image.device_id field.<BR>
     * FK_NAME:fl_image_ibfk_1
     * @param idOfDevice Integer - PK# 1
     * @return the number of deleted rows
     */
    public int deleteImageBeansByDeviceId(Integer idOfDevice);
    //3.2.4 GET IMPORTED
    /**
     * Retrieves the {@link ImageBean} object from fl_image.device_id field.<BR>
     * FK_NAME:fl_image_ibfk_1
     * @param bean the {@link DeviceBean}
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the associated {@link ImageBean} beans or empty list if {@code bean} is {@code null}
     */
    public java.util.List<ImageBean> getImageBeansByDeviceIdAsList(DeviceBean bean,int startRow,int numRows);    
    //3.3 SET IMPORTED
    /**
     * set  the {@link ImageBean} object array associate to DeviceBean by the fl_image.device_id field.<BR>
     * FK_NAME : fl_image_ibfk_1 
     * @param bean the referenced {@link DeviceBean}
     * @param importedBeans imported beans from fl_image
     * @return importedBeans always
     * @see {@link ImageManager#setReferencedByDeviceId(ImageBean, DeviceBean)
     */
    public ImageBean[] setImageBeansByDeviceId(DeviceBean bean , ImageBean[] importedBeans);

    //3.4 SET IMPORTED
    /**
     * set  the {@link ImageBean} object java.util.Collection associate to DeviceBean by the fl_image.device_id field.<BR>
     * FK_NAME:fl_image_ibfk_1
     * @param bean the referenced {@link DeviceBean} 
     * @param importedBeans imported beans from fl_image 
     * @return importedBeans always
     * @see {@link ImageManager#setReferencedByDeviceId(ImageBean, DeviceBean)
     */
    public <C extends java.util.Collection<ImageBean>> C setImageBeansByDeviceId(DeviceBean bean , C importedBeans);

    //3.1 GET IMPORTED
    /**
     * Retrieves the {@link LogBean} object from the fl_log.device_id field.<BR>
     * FK_NAME : fl_log_ibfk_2 
     * @param bean the {@link DeviceBean}
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     */
    public LogBean[] getLogBeansByDeviceId(DeviceBean bean);
    
    //3.1.2 GET IMPORTED
    /**
     * Retrieves the {@link LogBean} object from the fl_log.device_id field.<BR>
     * FK_NAME : fl_log_ibfk_2 
     * @param idOfDevice Integer - PK# 1
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DaoException
     */
    public LogBean[] getLogBeansByDeviceId(Integer idOfDevice);
    
    //3.2 GET IMPORTED
    /**
     * see also #getLogBeansByDeviceIdAsList(DeviceBean,int,int)
     * @param bean
     * @return
     */
    public java.util.List<LogBean> getLogBeansByDeviceIdAsList(DeviceBean bean);

    //3.2.2 GET IMPORTED
    /**
     * Retrieves the {@link LogBean} object from fl_log.device_id field.<BR>
     * FK_NAME:fl_log_ibfk_2
     * @param idOfDevice Integer - PK# 1
     * @return the associated {@link LogBean} beans 
     * @throws DaoException
     */
    public java.util.List<LogBean> getLogBeansByDeviceIdAsList(Integer idOfDevice);
    //3.2.3 DELETE IMPORTED
    /**
     * delete the associated {@link LogBean} objects from fl_log.device_id field.<BR>
     * FK_NAME:fl_log_ibfk_2
     * @param idOfDevice Integer - PK# 1
     * @return the number of deleted rows
     */
    public int deleteLogBeansByDeviceId(Integer idOfDevice);
    //3.2.4 GET IMPORTED
    /**
     * Retrieves the {@link LogBean} object from fl_log.device_id field.<BR>
     * FK_NAME:fl_log_ibfk_2
     * @param bean the {@link DeviceBean}
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the associated {@link LogBean} beans or empty list if {@code bean} is {@code null}
     */
    public java.util.List<LogBean> getLogBeansByDeviceIdAsList(DeviceBean bean,int startRow,int numRows);    
    //3.3 SET IMPORTED
    /**
     * set  the {@link LogBean} object array associate to DeviceBean by the fl_log.device_id field.<BR>
     * FK_NAME : fl_log_ibfk_2 
     * @param bean the referenced {@link DeviceBean}
     * @param importedBeans imported beans from fl_log
     * @return importedBeans always
     * @see {@link LogManager#setReferencedByDeviceId(LogBean, DeviceBean)
     */
    public LogBean[] setLogBeansByDeviceId(DeviceBean bean , LogBean[] importedBeans);

    //3.4 SET IMPORTED
    /**
     * set  the {@link LogBean} object java.util.Collection associate to DeviceBean by the fl_log.device_id field.<BR>
     * FK_NAME:fl_log_ibfk_2
     * @param bean the referenced {@link DeviceBean} 
     * @param importedBeans imported beans from fl_log 
     * @return importedBeans always
     * @see {@link LogManager#setReferencedByDeviceId(LogBean, DeviceBean)
     */
    public <C extends java.util.Collection<LogBean>> C setLogBeansByDeviceId(DeviceBean bean , C importedBeans);

    //3.5 SYNC SAVE 
    /**
     * Save the DeviceBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link DeviceBean} bean to be saved
     * @param refDevicegroupByGroupId the {@link DeviceGroupBean} bean referenced by {@link DeviceBean} 
     * @param impImageByDeviceId the {@link ImageBean} bean refer to {@link DeviceBean} 
     * @param impLogByDeviceId the {@link LogBean} bean refer to {@link DeviceBean} 
     * @return the inserted or updated {@link DeviceBean} bean
     */
    public DeviceBean save(DeviceBean bean
        , DeviceGroupBean refDevicegroupByGroupId 
        , ImageBean[] impImageByDeviceId , LogBean[] impLogByDeviceId );
    //3.6 SYNC SAVE AS TRANSACTION
    /**
     * Transaction version for sync save<br>
     * see also {@link #save(DeviceBean , DeviceGroupBean , ImageBean[] , LogBean[] )}
     * @param bean the {@link DeviceBean} bean to be saved
     * @param refDevicegroupByGroupId the {@link DeviceGroupBean} bean referenced by {@link DeviceBean} 
     * @param impImageByDeviceId the {@link ImageBean} bean refer to {@link DeviceBean} 
     * @param impLogByDeviceId the {@link LogBean} bean refer to {@link DeviceBean} 
     * @return the inserted or updated {@link DeviceBean} bean
     */
    public DeviceBean saveAsTransaction(final DeviceBean bean
        ,final DeviceGroupBean refDevicegroupByGroupId 
        ,final ImageBean[] impImageByDeviceId ,final LogBean[] impLogByDeviceId );
    //3.7 SYNC SAVE 
    /**
     * Save the DeviceBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link DeviceBean} bean to be saved
     * @param refDevicegroupByGroupId the {@link DeviceGroupBean} bean referenced by {@link DeviceBean} 
     * @param impImageByDeviceId the {@link ImageBean} bean refer to {@link DeviceBean} 
     * @param impLogByDeviceId the {@link LogBean} bean refer to {@link DeviceBean} 
     * @return the inserted or updated {@link DeviceBean} bean
     */
    public DeviceBean save(DeviceBean bean
        , DeviceGroupBean refDevicegroupByGroupId 
        , java.util.Collection<ImageBean> impImageByDeviceId , java.util.Collection<LogBean> impLogByDeviceId );
    //3.8 SYNC SAVE AS TRANSACTION
    /**
     * Transaction version for sync save<br>
     * see also {@link #save(DeviceBean , DeviceGroupBean , java.util.Collection , java.util.Collection )}
     * @param bean the {@link DeviceBean} bean to be saved
     * @param refDevicegroupByGroupId the {@link DeviceGroupBean} bean referenced by {@link DeviceBean} 
     * @param impImageByDeviceId the {@link ImageBean} bean refer to {@link DeviceBean} 
     * @param impLogByDeviceId the {@link LogBean} bean refer to {@link DeviceBean} 
     * @return the inserted or updated {@link DeviceBean} bean
     */
    public DeviceBean saveAsTransaction(final DeviceBean bean
        ,final DeviceGroupBean refDevicegroupByGroupId 
        ,final  java.util.Collection<ImageBean> impImageByDeviceId ,final  java.util.Collection<LogBean> impLogByDeviceId );
      //////////////////////////////////////
    // GET/SET FOREIGN KEY BEAN METHOD
    //////////////////////////////////////
    //5.1 GET REFERENCED VALUE
    /**
     * Retrieves the {@link DeviceGroupBean} object referenced by {@link DeviceBean#getGroupId}() field.<br>
     * FK_NAME : fl_device_ibfk_1
     * @param bean the {@link DeviceBean}
     * @return the associated {@link DeviceGroupBean} bean or {@code null} if {@code bean} is {@code null}
     */
    public DeviceGroupBean getReferencedByGroupId(DeviceBean bean);

    //5.2 SET REFERENCED 
    /**
     * Associates the {@link DeviceBean} object to the {@link DeviceGroupBean} object by {@link DeviceBean#getGroupId}() field.
     *
     * @param bean the {@link DeviceBean} object to use
     * @param beanToSet the {@link DeviceGroupBean} object to associate to the {@link DeviceBean}
     * @return always beanToSet saved
     * @throws WrapDaoException
     */
    public DeviceGroupBean setReferencedByGroupId(DeviceBean bean, DeviceGroupBean beanToSet);
    //_____________________________________________________________________
    //
    // USING INDICES
    //_____________________________________________________________________


    /**
     * Retrieves an unique DeviceBean using the mac index.
     * 
     * @param mac the mac column's value filter
     * @return an DeviceBean,otherwise null if not found or exists null in input arguments
     * @throws WrapDaoException
     */
    public DeviceBean loadByIndexMac(String mac);
    /**
     * Retrieves an unique DeviceBean using the mac index.
     * 
     * @param mac the mac column's value filter. must not be null
     * @return an DeviceBean
     * @throws NullPointerException exists null in input arguments
     * @throws ObjectRetrievalException if not found
     * @throws WrapDaoException
     */
    public DeviceBean loadByIndexMacChecked(String mac)throws ObjectRetrievalException;
    /**
     * Retrieves an unique DeviceBean for each mac index.
     *
     * @param indexs index array
     * @return an list of DeviceBean
     */
    public java.util.List<DeviceBean> loadByIndexMac(String... indexs);
    /**
     * Retrieves an unique DeviceBean for each mac index.
     *
     * @param indexs index collection
     * @return an list of DeviceBean
     */
    public java.util.List<DeviceBean> loadByIndexMac(java.util.Collection<String> indexs);
    /**
     * Deletes rows for each mac index.
     *
     * @param indexs index array
     * @return the number of deleted rows
     */
    public int deleteByIndexMac(String... indexs);
    /**
     * Deletes rows for each mac index.
     *
     * @param indexs index collection
     * @return the number of deleted rows
     */
    public int deleteByIndexMac(java.util.Collection<String> indexs);

    /**
     * Deletes rows using the mac index.
     *
     * @param mac the mac column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexMac(String mac);
    

    /**
     * Retrieves an unique DeviceBean using the serial_no index.
     * 
     * @param serialNo the serial_no column's value filter
     * @return an DeviceBean,otherwise null if not found or exists null in input arguments
     * @throws WrapDaoException
     */
    public DeviceBean loadByIndexSerialNo(String serialNo);
    /**
     * Retrieves an unique DeviceBean using the serial_no index.
     * 
     * @param serialNo the serial_no column's value filter. must not be null
     * @return an DeviceBean
     * @throws NullPointerException exists null in input arguments
     * @throws ObjectRetrievalException if not found
     * @throws WrapDaoException
     */
    public DeviceBean loadByIndexSerialNoChecked(String serialNo)throws ObjectRetrievalException;
    /**
     * Retrieves an unique DeviceBean for each serial_no index.
     *
     * @param indexs index array
     * @return an list of DeviceBean
     */
    public java.util.List<DeviceBean> loadByIndexSerialNo(String... indexs);
    /**
     * Retrieves an unique DeviceBean for each serial_no index.
     *
     * @param indexs index collection
     * @return an list of DeviceBean
     */
    public java.util.List<DeviceBean> loadByIndexSerialNo(java.util.Collection<String> indexs);
    /**
     * Deletes rows for each serial_no index.
     *
     * @param indexs index array
     * @return the number of deleted rows
     */
    public int deleteByIndexSerialNo(String... indexs);
    /**
     * Deletes rows for each serial_no index.
     *
     * @param indexs index collection
     * @return the number of deleted rows
     */
    public int deleteByIndexSerialNo(java.util.Collection<String> indexs);

    /**
     * Deletes rows using the serial_no index.
     *
     * @param serialNo the serial_no column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexSerialNo(String serialNo);
    

     /**
     * Retrieves an array of DeviceBean using the group_id index.
     *
     * @param groupId the group_id column's value filter.
     * @return an array of DeviceBean
     */
    public DeviceBean[] loadByIndexGroupId(Integer groupId);
    
    /**
     * Retrieves a list of DeviceBean using the group_id index.
     *
     * @param groupId the group_id column's value filter.
     * @return a list of DeviceBean
     */
    public java.util.List<DeviceBean> loadByIndexGroupIdAsList(Integer groupId);

    /**
     * Deletes rows using the group_id index.
     *
     * @param groupId the group_id column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexGroupId(Integer groupId);
    

    //45
    /**
     * return a primary key list from {@link DeviceBean} array
     * @param beans
     * @return
     */
    public java.util.List<Integer> toPrimaryKeyList(DeviceBean... beans);
    //46
    /**
     * return a primary key list from {@link DeviceBean} collection
     * @param beans
     * @return
     */
    public java.util.List<Integer> toPrimaryKeyList(java.util.Collection<DeviceBean> beans);

}
