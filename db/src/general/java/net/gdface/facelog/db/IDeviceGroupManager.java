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
 * Interface to handle database calls (save, load, count, etc...) for the fl_device_group table.<br>
 * Remarks: 设备组信息
 * @author guyadong
 */
public interface IDeviceGroupManager extends TableManager<DeviceGroupBean>
{  
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    /**
     * Loads a {@link DeviceGroupBean} from the fl_device_group using primary key fields.
     *
     * @param id Integer - PK# 1
     * @return a unique DeviceGroupBean or {@code null} if not found
     */
    //1
    public DeviceGroupBean loadByPrimaryKey(Integer id);

    /**
     * Loads a {@link DeviceGroupBean} from the fl_device_group using primary key fields.
     *
     * @param id Integer - PK# 1
     * @return a unique DeviceGroupBean
     * @throws ObjectRetrievalException if not found
     */
    //1.1
    public DeviceGroupBean loadByPrimaryKeyChecked(Integer id) throws ObjectRetrievalException;
    
    /**
     * Returns true if this fl_device_group contains row with primary key fields.
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
     * Loads {@link DeviceGroupBean} from the fl_device_group using primary key fields.
     *
     * @param keys primary keys array
     * @return list of DeviceGroupBean
     */
    //1.8
    public java.util.List<DeviceGroupBean> loadByPrimaryKey(int... keys);
    /**
     * Loads {@link DeviceGroupBean} from the fl_device_group using primary key fields.
     *
     * @param keys primary keys collection
     * @return list of DeviceGroupBean
     */
    //1.9
    public java.util.List<DeviceGroupBean> loadByPrimaryKey(java.util.Collection<Integer> keys);
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
     * @param beans DeviceGroupBean collection wille be deleted
     * @return the number of deleted rows
     */
    //2.4
    public int delete(DeviceGroupBean... beans);
    /**
     * Delete beans.<br>
     *
     * @param beans DeviceGroupBean collection wille be deleted
     * @return the number of deleted rows
     */
    //2.5
    public int delete(java.util.Collection<DeviceGroupBean> beans);
 

    //////////////////////////////////////
    // GET/SET IMPORTED KEY BEAN METHOD
    //////////////////////////////////////
    /**
     * Retrieves the {@link DeviceBean} object from the fl_device.group_id field.<BR>
     * FK_NAME : fl_device_ibfk_1 
     * @param bean the {@link DeviceGroupBean}
     * @return the associated {@link DeviceBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.1 GET IMPORTED
    public DeviceBean[] getDeviceBeansByGroupId(DeviceGroupBean bean);
    
    /**
     * Retrieves the {@link DeviceBean} object from the fl_device.group_id field.<BR>
     * FK_NAME : fl_device_ibfk_1 
     * @param id Integer - PK# 1
     * @return the associated {@link DeviceBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DAOException
     */
    //3.1.2 GET IMPORTED
    public DeviceBean[] getDeviceBeansByGroupId(Integer devicegroupId);
    
    /**
     * @see #getDeviceBeansByGroupIdAsList(DeviceGroupBean,int,int)
     */
    //3.2 GET IMPORTED
    public java.util.List<DeviceBean> getDeviceBeansByGroupIdAsList(DeviceGroupBean bean);

    /**
     * Retrieves the {@link DeviceBean} object from fl_device.group_id field.<BR>
     * FK_NAME:fl_device_ibfk_1
     * @param id Integer - PK# 1
     * @return the associated {@link DeviceBean} beans 
     * @throws DAOException
     */
    //3.2.2 GET IMPORTED
    public java.util.List<DeviceBean> getDeviceBeansByGroupIdAsList(Integer devicegroupId);
    /**
     * delete the associated {@link DeviceBean} objects from fl_device.group_id field.<BR>
     * FK_NAME:fl_device_ibfk_1
     * @param id Integer - PK# 1
     * @return the number of deleted rows
     */
    //3.2.3 DELETE IMPORTED
    public int deleteDeviceBeansByGroupId(Integer devicegroupId);
    /**
     * Retrieves the {@link DeviceBean} object from fl_device.group_id field.<BR>
     * FK_NAME:fl_device_ibfk_1
     * @param bean the {@link DeviceGroupBean}
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the associated {@link DeviceBean} beans or empty list if {@code bean} is {@code null}
     */
    //3.2.4 GET IMPORTED
    public java.util.List<DeviceBean> getDeviceBeansByGroupIdAsList(DeviceGroupBean bean,int startRow,int numRows);    
    /**
     * set  the {@link DeviceBean} object array associate to DeviceGroupBean by the fl_device.group_id field.<BR>
     * FK_NAME : fl_device_ibfk_1 
     * @param bean the referenced {@link DeviceGroupBean}
     * @param importedBeans imported beans from fl_device
     * @return importedBeans always
     * @see {@link DeviceManager#setReferencedByGroupId(DeviceBean, DeviceGroupBean)
     */
    //3.3 SET IMPORTED
    public DeviceBean[] setDeviceBeansByGroupId(DeviceGroupBean bean , DeviceBean[] importedBeans);

    /**
     * set  the {@link DeviceBean} object java.util.Collection associate to DeviceGroupBean by the fl_device.group_id field.<BR>
     * FK_NAME:fl_device_ibfk_1
     * @param bean the referenced {@link DeviceGroupBean} 
     * @param importedBeans imported beans from fl_device 
     * @return importedBeans always
     * @see {@link DeviceManager#setReferencedByGroupId(DeviceBean, DeviceGroupBean)
     */
    //3.4 SET IMPORTED
    public <C extends java.util.Collection<DeviceBean>> C setDeviceBeansByGroupId(DeviceGroupBean bean , C importedBeans);

    /**
     * Retrieves the {@link DeviceGroupBean} object from the fl_device_group.parent field.<BR>
     * FK_NAME : fl_device_group_ibfk_1 
     * @param bean the {@link DeviceGroupBean}
     * @return the associated {@link DeviceGroupBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.1 GET IMPORTED
    public DeviceGroupBean[] getDeviceGroupBeansByParent(DeviceGroupBean bean);
    
    /**
     * Retrieves the {@link DeviceGroupBean} object from the fl_device_group.parent field.<BR>
     * FK_NAME : fl_device_group_ibfk_1 
     * @param id Integer - PK# 1
     * @return the associated {@link DeviceGroupBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DAOException
     */
    //3.1.2 GET IMPORTED
    public DeviceGroupBean[] getDeviceGroupBeansByParent(Integer devicegroupId);
    
    /**
     * @see #getDeviceGroupBeansByParentAsList(DeviceGroupBean,int,int)
     */
    //3.2 GET IMPORTED
    public java.util.List<DeviceGroupBean> getDeviceGroupBeansByParentAsList(DeviceGroupBean bean);

    /**
     * Retrieves the {@link DeviceGroupBean} object from fl_device_group.parent field.<BR>
     * FK_NAME:fl_device_group_ibfk_1
     * @param id Integer - PK# 1
     * @return the associated {@link DeviceGroupBean} beans 
     * @throws DAOException
     */
    //3.2.2 GET IMPORTED
    public java.util.List<DeviceGroupBean> getDeviceGroupBeansByParentAsList(Integer devicegroupId);
    /**
     * delete the associated {@link DeviceGroupBean} objects from fl_device_group.parent field.<BR>
     * FK_NAME:fl_device_group_ibfk_1
     * @param id Integer - PK# 1
     * @return the number of deleted rows
     */
    //3.2.3 DELETE IMPORTED
    public int deleteDeviceGroupBeansByParent(Integer devicegroupId);
    /**
     * Retrieves the {@link DeviceGroupBean} object from fl_device_group.parent field.<BR>
     * FK_NAME:fl_device_group_ibfk_1
     * @param bean the {@link DeviceGroupBean}
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the associated {@link DeviceGroupBean} beans or empty list if {@code bean} is {@code null}
     */
    //3.2.4 GET IMPORTED
    public java.util.List<DeviceGroupBean> getDeviceGroupBeansByParentAsList(DeviceGroupBean bean,int startRow,int numRows);    
    /**
     * set  the {@link DeviceGroupBean} object array associate to DeviceGroupBean by the fl_device_group.parent field.<BR>
     * FK_NAME : fl_device_group_ibfk_1 
     * @param bean the referenced {@link DeviceGroupBean}
     * @param importedBeans imported beans from fl_device_group
     * @return importedBeans always
     * @see {@link DeviceGroupManager#setReferencedByParent(DeviceGroupBean, DeviceGroupBean)
     */
    //3.3 SET IMPORTED
    public DeviceGroupBean[] setDeviceGroupBeansByParent(DeviceGroupBean bean , DeviceGroupBean[] importedBeans);

    /**
     * set  the {@link DeviceGroupBean} object java.util.Collection associate to DeviceGroupBean by the fl_device_group.parent field.<BR>
     * FK_NAME:fl_device_group_ibfk_1
     * @param bean the referenced {@link DeviceGroupBean} 
     * @param importedBeans imported beans from fl_device_group 
     * @return importedBeans always
     * @see {@link DeviceGroupManager#setReferencedByParent(DeviceGroupBean, DeviceGroupBean)
     */
    //3.4 SET IMPORTED
    public <C extends java.util.Collection<DeviceGroupBean>> C setDeviceGroupBeansByParent(DeviceGroupBean bean , C importedBeans);

    /**
     * Retrieves the {@link PermitBean} object from the fl_permit.device_group_id field.<BR>
     * FK_NAME : fl_permit_ibfk_1 
     * @param bean the {@link DeviceGroupBean}
     * @return the associated {@link PermitBean} beans or {@code null} if {@code bean} is {@code null}
     */
    //3.1 GET IMPORTED
    public PermitBean[] getPermitBeansByDeviceGroupId(DeviceGroupBean bean);
    
    /**
     * Retrieves the {@link PermitBean} object from the fl_permit.device_group_id field.<BR>
     * FK_NAME : fl_permit_ibfk_1 
     * @param id Integer - PK# 1
     * @return the associated {@link PermitBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DAOException
     */
    //3.1.2 GET IMPORTED
    public PermitBean[] getPermitBeansByDeviceGroupId(Integer devicegroupId);
    
    /**
     * @see #getPermitBeansByDeviceGroupIdAsList(DeviceGroupBean,int,int)
     */
    //3.2 GET IMPORTED
    public java.util.List<PermitBean> getPermitBeansByDeviceGroupIdAsList(DeviceGroupBean bean);

    /**
     * Retrieves the {@link PermitBean} object from fl_permit.device_group_id field.<BR>
     * FK_NAME:fl_permit_ibfk_1
     * @param id Integer - PK# 1
     * @return the associated {@link PermitBean} beans 
     * @throws DAOException
     */
    //3.2.2 GET IMPORTED
    public java.util.List<PermitBean> getPermitBeansByDeviceGroupIdAsList(Integer devicegroupId);
    /**
     * delete the associated {@link PermitBean} objects from fl_permit.device_group_id field.<BR>
     * FK_NAME:fl_permit_ibfk_1
     * @param id Integer - PK# 1
     * @return the number of deleted rows
     */
    //3.2.3 DELETE IMPORTED
    public int deletePermitBeansByDeviceGroupId(Integer devicegroupId);
    /**
     * Retrieves the {@link PermitBean} object from fl_permit.device_group_id field.<BR>
     * FK_NAME:fl_permit_ibfk_1
     * @param bean the {@link DeviceGroupBean}
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the associated {@link PermitBean} beans or empty list if {@code bean} is {@code null}
     */
    //3.2.4 GET IMPORTED
    public java.util.List<PermitBean> getPermitBeansByDeviceGroupIdAsList(DeviceGroupBean bean,int startRow,int numRows);    
    /**
     * set  the {@link PermitBean} object array associate to DeviceGroupBean by the fl_permit.device_group_id field.<BR>
     * FK_NAME : fl_permit_ibfk_1 
     * @param bean the referenced {@link DeviceGroupBean}
     * @param importedBeans imported beans from fl_permit
     * @return importedBeans always
     * @see {@link PermitManager#setReferencedByDeviceGroupId(PermitBean, DeviceGroupBean)
     */
    //3.3 SET IMPORTED
    public PermitBean[] setPermitBeansByDeviceGroupId(DeviceGroupBean bean , PermitBean[] importedBeans);

    /**
     * set  the {@link PermitBean} object java.util.Collection associate to DeviceGroupBean by the fl_permit.device_group_id field.<BR>
     * FK_NAME:fl_permit_ibfk_1
     * @param bean the referenced {@link DeviceGroupBean} 
     * @param importedBeans imported beans from fl_permit 
     * @return importedBeans always
     * @see {@link PermitManager#setReferencedByDeviceGroupId(PermitBean, DeviceGroupBean)
     */
    //3.4 SET IMPORTED
    public <C extends java.util.Collection<PermitBean>> C setPermitBeansByDeviceGroupId(DeviceGroupBean bean , C importedBeans);

    /**
     * Save the DeviceGroupBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link DeviceGroupBean} bean to be saved
     * @param refDevicegroupByParent the {@link DeviceGroupBean} bean referenced by {@link DeviceGroupBean} 
     * @param impDeviceByGroupId the {@link DeviceBean} bean refer to {@link DeviceGroupBean} 
     * @param impDevicegroupByParent the {@link DeviceGroupBean} bean refer to {@link DeviceGroupBean} 
     * @param impPermitByDeviceGroupId the {@link PermitBean} bean refer to {@link DeviceGroupBean} 
     * @return the inserted or updated {@link DeviceGroupBean} bean
     */
    //3.5 SYNC SAVE 
    public DeviceGroupBean save(DeviceGroupBean bean
        , DeviceGroupBean refDevicegroupByParent 
        , DeviceBean[] impDeviceByGroupId , DeviceGroupBean[] impDevicegroupByParent , PermitBean[] impPermitByDeviceGroupId );
    /**
     * Transaction version for sync save
     * @see {@link #save(DeviceGroupBean , DeviceGroupBean , DeviceBean[] , DeviceGroupBean[] , PermitBean[] )}
     */
    //3.6 SYNC SAVE AS TRANSACTION
    public DeviceGroupBean saveAsTransaction(final DeviceGroupBean bean
        ,final DeviceGroupBean refDevicegroupByParent 
        ,final DeviceBean[] impDeviceByGroupId ,final DeviceGroupBean[] impDevicegroupByParent ,final PermitBean[] impPermitByDeviceGroupId );
    /**
     * Save the DeviceGroupBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link DeviceGroupBean} bean to be saved
     * @param refDevicegroupByParent the {@link DeviceGroupBean} bean referenced by {@link DeviceGroupBean} 
     * @param impDeviceByGroupId the {@link DeviceBean} bean refer to {@link DeviceGroupBean} 
     * @param impDevicegroupByParent the {@link DeviceGroupBean} bean refer to {@link DeviceGroupBean} 
     * @param impPermitByDeviceGroupId the {@link PermitBean} bean refer to {@link DeviceGroupBean} 
     * @return the inserted or updated {@link DeviceGroupBean} bean
     */
    //3.7 SYNC SAVE 
    public DeviceGroupBean save(DeviceGroupBean bean
        , DeviceGroupBean refDevicegroupByParent 
        , java.util.Collection<DeviceBean> impDeviceByGroupId , java.util.Collection<DeviceGroupBean> impDevicegroupByParent , java.util.Collection<PermitBean> impPermitByDeviceGroupId );
    /**
     * Transaction version for sync save
     * @see {@link #save(DeviceGroupBean , DeviceGroupBean , java.util.Collection , java.util.Collection , java.util.Collection )}
     */
    //3.8 SYNC SAVE AS TRANSACTION
    public DeviceGroupBean saveAsTransaction(final DeviceGroupBean bean
        ,final DeviceGroupBean refDevicegroupByParent 
        ,final  java.util.Collection<DeviceBean> impDeviceByGroupId ,final  java.util.Collection<DeviceGroupBean> impDevicegroupByParent ,final  java.util.Collection<PermitBean> impPermitByDeviceGroupId );
      //////////////////////////////////////
    // GET/SET FOREIGN KEY BEAN METHOD
    //////////////////////////////////////
    /**
     * Retrieves the {@link DeviceGroupBean} object referenced by {@link DeviceGroupBean#getParent}() field.<br>
     * FK_NAME : fl_device_group_ibfk_1
     * @param bean the {@link DeviceGroupBean}
     * @return the associated {@link DeviceGroupBean} bean or {@code null} if {@code bean} is {@code null}
     */
    //5.1 GET REFERENCED VALUE
    public DeviceGroupBean getReferencedByParent(DeviceGroupBean bean);

    /**
     * Associates the {@link DeviceGroupBean} object to the {@link DeviceGroupBean} object by {@link DeviceGroupBean#getParent}() field.
     *
     * @param bean the {@link DeviceGroupBean} object to use
     * @param beanToSet the {@link DeviceGroupBean} object to associate to the {@link DeviceGroupBean}
     * @return always beanToSet saved
     * @throws WrapDAOException
     */
    //5.2 SET REFERENCED 
    public DeviceGroupBean setReferencedByParent(DeviceGroupBean bean, DeviceGroupBean beanToSet);
    //_____________________________________________________________________
    //
    // USING INDICES
    //_____________________________________________________________________


     /**
     * Retrieves an array of DeviceGroupBean using the parent index.
     *
     * @param parent the parent column's value filter.
     * @return an array of DeviceGroupBean
     */
    public DeviceGroupBean[] loadByIndexParent(Integer parent);
    
    /**
     * Retrieves a list of DeviceGroupBean using the parent index.
     *
     * @param parent the parent column's value filter.
     * @return a list of DeviceGroupBean
     */
    public java.util.List<DeviceGroupBean> loadByIndexParentAsList(Integer parent);

    /**
     * Deletes rows using the parent index.
     *
     * @param parent the parent column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexParent(Integer parent);
    

    /**
     * return a primary key list from {@link DeviceGroupBean} array
     * @param array
     */
    //45
    public java.util.List<Integer> toPrimaryKeyList(DeviceGroupBean... array);
    /**
     * return a primary key list from {@link DeviceGroupBean} collection
     * @param array
     */
    //46
    public java.util.List<Integer> toPrimaryKeyList(java.util.Collection<DeviceGroupBean> collection);

    //_____________________________________________________________________
    //
    // MANY TO MANY: LOAD OTHER BEAN VIA JUNCTION TABLE
    //_____________________________________________________________________
    /**
     * @see #loadViaPermitAsList(DeviceGroupBean,int,int)
     */
    //22 MANY TO MANY
    public java.util.List<DeviceGroupBean> loadViaPermitAsList(PersonGroupBean bean);

    /**
     * Retrieves an list of DeviceGroupBean using the junction table Permit, given a PersonGroupBean, 
     * specifying the start row and the number of rows.
     *
     * @param bean the PersonGroupBean bean to be used
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return a list of DeviceGroupBean
     */
    //23 MANY TO MANY
    public java.util.List<DeviceGroupBean> loadViaPermitAsList(PersonGroupBean bean, int startRow, int numRows);
    /**
     * add junction between {@link DeviceGroupBean} and {@link PersonGroupBean} if junction not exists
     * @param bean
     * @param linked
     */
    //23.2 MANY TO MANY
    public void addJunction(DeviceGroupBean bean,PersonGroupBean linked);
    /**
     * remove junction between {@link DeviceGroupBean} and {@link PersonGroupBean}
     * @param bean
     * @param linked
     */
    //23.3 MANY TO MANY
    public int deleteJunction(DeviceGroupBean bean,PersonGroupBean linked);
    /** @see #addJunction(DeviceGroupBean,PersonGroupBean) */
    //23.4 MANY TO MANY
    public void addJunction(DeviceGroupBean bean,PersonGroupBean... linkedBeans);
    /** @see #addJunction(DeviceGroupBean,PersonGroupBean) */
    //23.5 MANY TO MANY
    public void addJunction(DeviceGroupBean bean,java.util.Collection<PersonGroupBean> linkedBeans);
    /** @see #deleteJunction(DeviceGroupBean,PersonGroupBean) */
    //23.6 MANY TO MANY
    public int deleteJunction(DeviceGroupBean bean,PersonGroupBean... linkedBeans);
    /** @see #deleteJunction(DeviceGroupBean,PersonGroupBean) */
    //23.7 MANY TO MANY
    public int deleteJunction(DeviceGroupBean bean,java.util.Collection<PersonGroupBean> linkedBeans);

    //_____________________________________________________________________
    //
    // SELF-REFERENCE
    //_____________________________________________________________________
    /**
     * return bean list ( include {@code bean}) by the self-reference field : {@code fl_device_group(parent) }<br>
     * first element is top bean
     * @param id PK# 1 
     * @return  empty list if input primary key is {@code null}<br>
     *         first element equal last if self-reference field is cycle
     * @throws WrapDAOException
     */
    //47
    public java.util.List<DeviceGroupBean> listOfParent(Integer id);
    /**
     * see also {@link #listOfParent(Integer)}
     */
    //48
    public java.util.List<DeviceGroupBean> listOfParent(DeviceGroupBean bean);
    /**
     * get level count on the self-reference field : {@code fl_device_group(parent) }
     * @param id PK# 1 
     * @return  0 if input primary key is {@code null}<br>
     *         -1 if self-reference field is cycle
     * @throws WrapDAOException
     */
    //49
    public int levelOfParent(Integer id);
    /**
     * see also {@link #levelOfParent(Integer)}
     */
    //50
    public int levelOfParent(DeviceGroupBean bean);
    /**
     * test whether the self-reference field is cycle : {@code fl_device_group(parent) }
     * @param id PK# 1 
     * @throws WrapDAOException
     * @see #levelOfParent(DeviceGroupBean)
     */
    //51
    public boolean isCycleOnParent(Integer id);
    /**
     * test whether the self-reference field is cycle : {@code fl_device_group(parent) }
     * @param bean
     * @throws WrapDAOException
     * @see #levelOfParent(DeviceGroupBean)
     */
    //52
    public boolean isCycleOnParent(DeviceGroupBean bean);
    /**
     * return top bean that with {@code null} self-reference field  : {@code fl_device_group(parent) }
     * @param id PK# 1 
     * @return top bean
     * @throws NullPointerException if input primary key is {@code null}
     * @throws IllegalStateException if self-reference field is cycle
     * @throws WrapDAOException
     */
    //53
    public DeviceGroupBean topOfParent(Integer id);
    /**
     * see also {@link #topOfParent(Integer)}
     */
    //54
    public DeviceGroupBean topOfParent(DeviceGroupBean bean);
    /**
     * Ensures the self-reference field is not cycle : {@code fl_device_group(parent) }
     * @param id PK# 1
     * @return always {@code id}
     * @throws IllegalStateException if self-reference field is cycle 
     * @throws WrapDAOException
     * @see #isCycleOnParent(Integer)
     */
    //55
    public Integer checkCycleOfParent(Integer id);
    /**
     * Ensures the self-reference field is not cycle : {@code fl_device_group(parent) }<br>
     * @param bean
     * @return always {@code bean}
     * @throws IllegalStateException if self-reference field is cycle
     * @throws WrapDAOException
     * @see #isCycleOnParent(DeviceGroupBean)
     */
    //56
    public DeviceGroupBean checkCycleOfParent(DeviceGroupBean bean);
}