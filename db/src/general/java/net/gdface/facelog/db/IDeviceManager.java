// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.db;

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

    /**
     * Loads a {@link DeviceBean} from the fl_device using primary key fields.
     *
     * @param id Integer - PK# 1
     * @return a unique DeviceBean or {@code null} if not found
     */
    //1
    public DeviceBean loadByPrimaryKey(Integer id);

    
    /**
     * Returns true if this fl_device contains row with primary key fields.
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
     * Loads {@link DeviceBean} from the fl_device using primary key fields.
     *
     * @param keys primary keys array
     * @return list of DeviceBean
     */
    //1.8
    public java.util.List<DeviceBean> loadByPrimaryKey(int... keys);
    /**
     * Loads {@link DeviceBean} from the fl_device using primary key fields.
     *
     * @param keys primary keys collection
     * @return list of DeviceBean
     */
    //1.9
    public java.util.List<DeviceBean> loadByPrimaryKey(java.util.Collection<Integer> keys);
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
     * @param beans DeviceBean collection wille be deleted
     * @return the number of deleted rows
     */
    //2.4
    public int delete(DeviceBean... beans);
    /**
     * Delete beans.<br>
     *
     * @param beans DeviceBean collection wille be deleted
     * @return the number of deleted rows
     */
    //2.5
    public int delete(java.util.Collection<DeviceBean> beans);
 

    //////////////////////////////////////
    // GET/SET IMPORTED KEY BEAN METHOD
    //////////////////////////////////////
    /**
     * Retrieves the {@link ImageBean} object from the fl_image.device_id field.<BR>
     * FK_NAME : fl_image_ibfk_1 
     * @param bean the {@link DeviceBean}
     * @return the associated {@link ImageBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.1 GET IMPORTED
    public ImageBean[] getImageBeansByDeviceId(DeviceBean bean);
    
    /**
     * Retrieves the {@link ImageBean} object from the fl_image.device_id field.<BR>
     * FK_NAME : fl_image_ibfk_1 
     * @param id Integer - PK# 1
     * @return the associated {@link ImageBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DAOException
     */
    //3.1.2 GET IMPORTED
    public ImageBean[] getImageBeansByDeviceId(Integer deviceId);
    
    /**
     * Retrieves the {@link ImageBean} object from fl_image.device_id field.<BR>
     * FK_NAME:fl_image_ibfk_1
     * @param bean the {@link DeviceBean}
     * @return the associated {@link ImageBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET IMPORTED
    public java.util.List<ImageBean> getImageBeansByDeviceIdAsList(DeviceBean bean);

    /**
     * Retrieves the {@link ImageBean} object from fl_image.device_id field.<BR>
     * FK_NAME:fl_image_ibfk_1
     * @param id Integer - PK# 1
     * @return the associated {@link ImageBean} beans 
     * @throws DAOException
     */
    //3.2.2 GET IMPORTED
    public java.util.List<ImageBean> getImageBeansByDeviceIdAsList(Integer deviceId);
    /**
     * delete the associated {@link ImageBean} objects from fl_image.device_id field.<BR>
     * FK_NAME:fl_image_ibfk_1
     * @param id Integer - PK# 1
     * @return the number of deleted rows
     */
    //3.2.3 DELETE IMPORTED
    public int deleteImageBeansByDeviceId(Integer deviceId);
    
    /**
     * set  the {@link ImageBean} object array associate to DeviceBean by the fl_image.device_id field.<BR>
     * FK_NAME : fl_image_ibfk_1 
     * @param bean the referenced {@link DeviceBean}
     * @param importedBeans imported beans from fl_image
     * @return importedBeans always
     * @see {@link ImageManager#setReferencedByDeviceId(ImageBean, DeviceBean)
     */
    //3.3 SET IMPORTED
    public ImageBean[] setImageBeansByDeviceId(DeviceBean bean , ImageBean[] importedBeans);

    /**
     * set  the {@link ImageBean} object java.util.Collection associate to DeviceBean by the fl_image.device_id field.<BR>
     * FK_NAME:fl_image_ibfk_1
     * @param bean the referenced {@link DeviceBean} 
     * @param importedBeans imported beans from fl_image 
     * @return importedBeans always
     * @see {@link ImageManager#setReferencedByDeviceId(ImageBean, DeviceBean)
     */
    //3.4 SET IMPORTED
    public <C extends java.util.Collection<ImageBean>> C setImageBeansByDeviceId(DeviceBean bean , C importedBeans);

    /**
     * Retrieves the {@link LogBean} object from the fl_log.device_id field.<BR>
     * FK_NAME : fl_log_ibfk_2 
     * @param bean the {@link DeviceBean}
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.1 GET IMPORTED
    public LogBean[] getLogBeansByDeviceId(DeviceBean bean);
    
    /**
     * Retrieves the {@link LogBean} object from the fl_log.device_id field.<BR>
     * FK_NAME : fl_log_ibfk_2 
     * @param id Integer - PK# 1
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DAOException
     */
    //3.1.2 GET IMPORTED
    public LogBean[] getLogBeansByDeviceId(Integer deviceId);
    
    /**
     * Retrieves the {@link LogBean} object from fl_log.device_id field.<BR>
     * FK_NAME:fl_log_ibfk_2
     * @param bean the {@link DeviceBean}
     * @return the associated {@link LogBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.2 GET IMPORTED
    public java.util.List<LogBean> getLogBeansByDeviceIdAsList(DeviceBean bean);

    /**
     * Retrieves the {@link LogBean} object from fl_log.device_id field.<BR>
     * FK_NAME:fl_log_ibfk_2
     * @param id Integer - PK# 1
     * @return the associated {@link LogBean} beans 
     * @throws DAOException
     */
    //3.2.2 GET IMPORTED
    public java.util.List<LogBean> getLogBeansByDeviceIdAsList(Integer deviceId);
    /**
     * delete the associated {@link LogBean} objects from fl_log.device_id field.<BR>
     * FK_NAME:fl_log_ibfk_2
     * @param id Integer - PK# 1
     * @return the number of deleted rows
     */
    //3.2.3 DELETE IMPORTED
    public int deleteLogBeansByDeviceId(Integer deviceId);
    
    /**
     * set  the {@link LogBean} object array associate to DeviceBean by the fl_log.device_id field.<BR>
     * FK_NAME : fl_log_ibfk_2 
     * @param bean the referenced {@link DeviceBean}
     * @param importedBeans imported beans from fl_log
     * @return importedBeans always
     * @see {@link LogManager#setReferencedByDeviceId(LogBean, DeviceBean)
     */
    //3.3 SET IMPORTED
    public LogBean[] setLogBeansByDeviceId(DeviceBean bean , LogBean[] importedBeans);

    /**
     * set  the {@link LogBean} object java.util.Collection associate to DeviceBean by the fl_log.device_id field.<BR>
     * FK_NAME:fl_log_ibfk_2
     * @param bean the referenced {@link DeviceBean} 
     * @param importedBeans imported beans from fl_log 
     * @return importedBeans always
     * @see {@link LogManager#setReferencedByDeviceId(LogBean, DeviceBean)
     */
    //3.4 SET IMPORTED
    public <C extends java.util.Collection<LogBean>> C setLogBeansByDeviceId(DeviceBean bean , C importedBeans);

    /**
     * Save the DeviceBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link DeviceBean} bean to be saved
         * @param impImageByDeviceId the {@link ImageBean} bean refer to {@link DeviceBean} 
     * @param impLogByDeviceId the {@link LogBean} bean refer to {@link DeviceBean} 
     * @return the inserted or updated {@link DeviceBean} bean
     */
    //3.5 SYNC SAVE 
    public DeviceBean save(DeviceBean bean
        
        , ImageBean[] impImageByDeviceId , LogBean[] impLogByDeviceId );
    /**
     * Transaction version for sync save
     * @see {@link #save(DeviceBean , ImageBean[] , LogBean[] )}
     */
    //3.6 SYNC SAVE AS TRANSACTION
    public DeviceBean saveAsTransaction(final DeviceBean bean
        
        ,final ImageBean[] impImageByDeviceId ,final LogBean[] impLogByDeviceId );
    /**
     * Save the DeviceBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link DeviceBean} bean to be saved
         * @param impImageByDeviceId the {@link ImageBean} bean refer to {@link DeviceBean} 
     * @param impLogByDeviceId the {@link LogBean} bean refer to {@link DeviceBean} 
     * @return the inserted or updated {@link DeviceBean} bean
     */
    //3.7 SYNC SAVE 
    public DeviceBean save(DeviceBean bean
        
        , java.util.Collection<ImageBean> impImageByDeviceId , java.util.Collection<LogBean> impLogByDeviceId );
    /**
     * Transaction version for sync save
     * @see {@link #save(DeviceBean , java.util.Collection , java.util.Collection )}
     */
    //3.8 SYNC SAVE AS TRANSACTION
    public DeviceBean saveAsTransaction(final DeviceBean bean
        
        ,final  java.util.Collection<ImageBean> impImageByDeviceId ,final  java.util.Collection<LogBean> impLogByDeviceId );
      //_____________________________________________________________________
    //
    // USING INDICES
    //_____________________________________________________________________


    /**
     * Retrieves an unique DeviceBean using the mac index.
     *
     * @param mac the mac column's value filter. must not be null
     * @return 
     */
    public DeviceBean loadByIndexMac(String mac);
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
     * @param serialNo the serial_no column's value filter. must not be null
     * @return 
     */
    public DeviceBean loadByIndexSerialNo(String serialNo);
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
    

    /**
     * return a primary key list from {@link DeviceBean} array
     * @param array
     */
    //45
    public java.util.List<Integer> toPrimaryKeyList(DeviceBean... array);
    /**
     * return a primary key list from {@link DeviceBean} collection
     * @param array
     */
    //46
    public java.util.List<Integer> toPrimaryKeyList(java.util.Collection<DeviceBean> collection);
}