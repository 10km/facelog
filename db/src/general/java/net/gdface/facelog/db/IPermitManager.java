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
 * Interface to handle database calls (save, load, count, etc...) for the fl_permit table.<br>
 * Remarks: 通行权限关联表
 * @author guyadong
 */
public interface IPermitManager extends TableManager<PermitBean>
{  
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    //1
    /**
     * Loads a {@link PermitBean} from the fl_permit using primary key fields.
     *
     * @param deviceGroupId Integer - PK# 1
     * @param personGroupId Integer - PK# 2
     * @return a unique PermitBean or {@code null} if not found
     */
    public PermitBean loadByPrimaryKey(Integer deviceGroupId,Integer personGroupId);

    //1.1
    /**
     * Loads a {@link PermitBean} from the fl_permit using primary key fields.
     *
     * @param deviceGroupId Integer - PK# 1
     * @param personGroupId Integer - PK# 2
     * @return a unique PermitBean
     * @throws ObjectRetrievalException if not found
     */
    public PermitBean loadByPrimaryKeyChecked(Integer deviceGroupId,Integer personGroupId) throws ObjectRetrievalException;
    
    //1.4
    /**
     * Returns true if this fl_permit contains row with primary key fields.
     * @param deviceGroupId Integer - PK# 1
     * @param personGroupId Integer - PK# 2
     * @see #loadByPrimaryKey($keys)
     * @return
     */
    public boolean existsPrimaryKey(Integer deviceGroupId,Integer personGroupId);
    //2
    /**
     * Delete row according to its primary keys.<br>
     * all keys must not be null
     *
     * @param deviceGroupId Integer - PK# 1
     * @param personGroupId Integer - PK# 2
     * @return the number of deleted rows
     */
    public int deleteByPrimaryKey(Integer deviceGroupId,Integer personGroupId);
    //2.4
    /**
     * Delete beans.<br>
     *
     * @param beans PermitBean collection wille be deleted
     * @return the number of deleted rows
     */
    public int delete(PermitBean... beans);
    //2.5
    /**
     * Delete beans.<br>
     *
     * @param beans PermitBean collection wille be deleted
     * @return the number of deleted rows
     */
    public int delete(java.util.Collection<PermitBean> beans);
 
    //3.5 SYNC SAVE 
    /**
     * Save the PermitBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link PermitBean} bean to be saved
     * @param refDevicegroupByDeviceGroupId the {@link DeviceGroupBean} bean referenced by {@link PermitBean} 
     * @param refPersongroupByPersonGroupId the {@link PersonGroupBean} bean referenced by {@link PermitBean} 
         * @return the inserted or updated {@link PermitBean} bean
     */
    public PermitBean save(PermitBean bean
        , DeviceGroupBean refDevicegroupByDeviceGroupId , PersonGroupBean refPersongroupByPersonGroupId 
        );
    //3.6 SYNC SAVE AS TRANSACTION
    /**
     * Transaction version for sync save<br>
     * see also {@link #save(PermitBean , DeviceGroupBean , PersonGroupBean )}
     * @param bean the {@link PermitBean} bean to be saved
     * @param refDevicegroupByDeviceGroupId the {@link DeviceGroupBean} bean referenced by {@link PermitBean} 
     * @param refPersongroupByPersonGroupId the {@link PersonGroupBean} bean referenced by {@link PermitBean} 
         * @return the inserted or updated {@link PermitBean} bean
     */
    public PermitBean saveAsTransaction(final PermitBean bean
        ,final DeviceGroupBean refDevicegroupByDeviceGroupId ,final PersonGroupBean refPersongroupByPersonGroupId 
        );
      //////////////////////////////////////
    // GET/SET FOREIGN KEY BEAN METHOD
    //////////////////////////////////////
    //5.1 GET REFERENCED VALUE
    /**
     * Retrieves the {@link DeviceGroupBean} object referenced by {@link PermitBean#getDeviceGroupId}() field.<br>
     * FK_NAME : fl_permit_ibfk_1
     * @param bean the {@link PermitBean}
     * @return the associated {@link DeviceGroupBean} bean or {@code null} if {@code bean} is {@code null}
     */
    public DeviceGroupBean getReferencedByDeviceGroupId(PermitBean bean);

    //5.2 SET REFERENCED 
    /**
     * Associates the {@link PermitBean} object to the {@link DeviceGroupBean} object by {@link PermitBean#getDeviceGroupId}() field.
     *
     * @param bean the {@link PermitBean} object to use
     * @param beanToSet the {@link DeviceGroupBean} object to associate to the {@link PermitBean}
     * @return always beanToSet saved
     * @throws WrapDaoException
     */
    public DeviceGroupBean setReferencedByDeviceGroupId(PermitBean bean, DeviceGroupBean beanToSet);
    //5.1 GET REFERENCED VALUE
    /**
     * Retrieves the {@link PersonGroupBean} object referenced by {@link PermitBean#getPersonGroupId}() field.<br>
     * FK_NAME : fl_permit_ibfk_2
     * @param bean the {@link PermitBean}
     * @return the associated {@link PersonGroupBean} bean or {@code null} if {@code bean} is {@code null}
     */
    public PersonGroupBean getReferencedByPersonGroupId(PermitBean bean);

    //5.2 SET REFERENCED 
    /**
     * Associates the {@link PermitBean} object to the {@link PersonGroupBean} object by {@link PermitBean#getPersonGroupId}() field.
     *
     * @param bean the {@link PermitBean} object to use
     * @param beanToSet the {@link PersonGroupBean} object to associate to the {@link PermitBean}
     * @return always beanToSet saved
     * @throws WrapDaoException
     */
    public PersonGroupBean setReferencedByPersonGroupId(PermitBean bean, PersonGroupBean beanToSet);


}
