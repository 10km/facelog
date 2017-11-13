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
 * Interface to handle database calls (save, load, count, etc...) for the fl_person_group table.<br>
 * Remarks: 用户组信息
 * @author guyadong
 */
public interface IPersonGroupManager extends TableManager<PersonGroupBean>
{  
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    //1
    /**
     * Loads a {@link PersonGroupBean} from the fl_person_group using primary key fields.
     *
     * @param id Integer - PK# 1
     * @return a unique PersonGroupBean or {@code null} if not found
     */
    public PersonGroupBean loadByPrimaryKey(Integer id);

    //1.1
    /**
     * Loads a {@link PersonGroupBean} from the fl_person_group using primary key fields.
     *
     * @param id Integer - PK# 1
     * @return a unique PersonGroupBean
     * @throws ObjectRetrievalException if not found
     */
    public PersonGroupBean loadByPrimaryKeyChecked(Integer id) throws ObjectRetrievalException;
    
    //1.4
    /**
     * Returns true if this fl_person_group contains row with primary key fields.
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
     * Loads {@link PersonGroupBean} from the fl_person_group using primary key fields.
     *
     * @param keys primary keys array
     * @return list of PersonGroupBean
     */
    public java.util.List<PersonGroupBean> loadByPrimaryKey(int... keys);
    //1.9
    /**
     * Loads {@link PersonGroupBean} from the fl_person_group using primary key fields.
     *
     * @param keys primary keys collection
     * @return list of PersonGroupBean
     */
    public java.util.List<PersonGroupBean> loadByPrimaryKey(java.util.Collection<Integer> keys);
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
     * @param beans PersonGroupBean collection wille be deleted
     * @return the number of deleted rows
     */
    public int delete(PersonGroupBean... beans);
    //2.5
    /**
     * Delete beans.<br>
     *
     * @param beans PersonGroupBean collection wille be deleted
     * @return the number of deleted rows
     */
    public int delete(java.util.Collection<PersonGroupBean> beans);
 

    //////////////////////////////////////
    // GET/SET IMPORTED KEY BEAN METHOD
    //////////////////////////////////////
    //3.1 GET IMPORTED
    /**
     * Retrieves the {@link PermitBean} object from the fl_permit.person_group_id field.<BR>
     * FK_NAME : fl_permit_ibfk_2 
     * @param bean the {@link PersonGroupBean}
     * @return the associated {@link PermitBean} beans or {@code null} if {@code bean} is {@code null}
     */
    public PermitBean[] getPermitBeansByPersonGroupId(PersonGroupBean bean);
    
    //3.1.2 GET IMPORTED
    /**
     * Retrieves the {@link PermitBean} object from the fl_permit.person_group_id field.<BR>
     * FK_NAME : fl_permit_ibfk_2 
     * @param idOfPersonGroup Integer - PK# 1
     * @return the associated {@link PermitBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DaoException
     */
    public PermitBean[] getPermitBeansByPersonGroupId(Integer idOfPersonGroup);
    
    //3.2 GET IMPORTED
    /**
     * see also #getPermitBeansByPersonGroupIdAsList(PersonGroupBean,int,int)
     * @param bean
     * @return
     */
    public java.util.List<PermitBean> getPermitBeansByPersonGroupIdAsList(PersonGroupBean bean);

    //3.2.2 GET IMPORTED
    /**
     * Retrieves the {@link PermitBean} object from fl_permit.person_group_id field.<BR>
     * FK_NAME:fl_permit_ibfk_2
     * @param idOfPersonGroup Integer - PK# 1
     * @return the associated {@link PermitBean} beans 
     * @throws DaoException
     */
    public java.util.List<PermitBean> getPermitBeansByPersonGroupIdAsList(Integer idOfPersonGroup);
    //3.2.3 DELETE IMPORTED
    /**
     * delete the associated {@link PermitBean} objects from fl_permit.person_group_id field.<BR>
     * FK_NAME:fl_permit_ibfk_2
     * @param idOfPersonGroup Integer - PK# 1
     * @return the number of deleted rows
     */
    public int deletePermitBeansByPersonGroupId(Integer idOfPersonGroup);
    //3.2.4 GET IMPORTED
    /**
     * Retrieves the {@link PermitBean} object from fl_permit.person_group_id field.<BR>
     * FK_NAME:fl_permit_ibfk_2
     * @param bean the {@link PersonGroupBean}
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the associated {@link PermitBean} beans or empty list if {@code bean} is {@code null}
     */
    public java.util.List<PermitBean> getPermitBeansByPersonGroupIdAsList(PersonGroupBean bean,int startRow,int numRows);    
    //3.3 SET IMPORTED
    /**
     * set  the {@link PermitBean} object array associate to PersonGroupBean by the fl_permit.person_group_id field.<BR>
     * FK_NAME : fl_permit_ibfk_2 
     * @param bean the referenced {@link PersonGroupBean}
     * @param importedBeans imported beans from fl_permit
     * @return importedBeans always
     * @see {@link PermitManager#setReferencedByPersonGroupId(PermitBean, PersonGroupBean)
     */
    public PermitBean[] setPermitBeansByPersonGroupId(PersonGroupBean bean , PermitBean[] importedBeans);

    //3.4 SET IMPORTED
    /**
     * set  the {@link PermitBean} object java.util.Collection associate to PersonGroupBean by the fl_permit.person_group_id field.<BR>
     * FK_NAME:fl_permit_ibfk_2
     * @param bean the referenced {@link PersonGroupBean} 
     * @param importedBeans imported beans from fl_permit 
     * @return importedBeans always
     * @see {@link PermitManager#setReferencedByPersonGroupId(PermitBean, PersonGroupBean)
     */
    public <C extends java.util.Collection<PermitBean>> C setPermitBeansByPersonGroupId(PersonGroupBean bean , C importedBeans);

    //3.1 GET IMPORTED
    /**
     * Retrieves the {@link PersonBean} object from the fl_person.group_id field.<BR>
     * FK_NAME : fl_person_ibfk_1 
     * @param bean the {@link PersonGroupBean}
     * @return the associated {@link PersonBean} beans or {@code null} if {@code bean} is {@code null}
     */
    public PersonBean[] getPersonBeansByGroupId(PersonGroupBean bean);
    
    //3.1.2 GET IMPORTED
    /**
     * Retrieves the {@link PersonBean} object from the fl_person.group_id field.<BR>
     * FK_NAME : fl_person_ibfk_1 
     * @param idOfPersonGroup Integer - PK# 1
     * @return the associated {@link PersonBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DaoException
     */
    public PersonBean[] getPersonBeansByGroupId(Integer idOfPersonGroup);
    
    //3.2 GET IMPORTED
    /**
     * see also #getPersonBeansByGroupIdAsList(PersonGroupBean,int,int)
     * @param bean
     * @return
     */
    public java.util.List<PersonBean> getPersonBeansByGroupIdAsList(PersonGroupBean bean);

    //3.2.2 GET IMPORTED
    /**
     * Retrieves the {@link PersonBean} object from fl_person.group_id field.<BR>
     * FK_NAME:fl_person_ibfk_1
     * @param idOfPersonGroup Integer - PK# 1
     * @return the associated {@link PersonBean} beans 
     * @throws DaoException
     */
    public java.util.List<PersonBean> getPersonBeansByGroupIdAsList(Integer idOfPersonGroup);
    //3.2.3 DELETE IMPORTED
    /**
     * delete the associated {@link PersonBean} objects from fl_person.group_id field.<BR>
     * FK_NAME:fl_person_ibfk_1
     * @param idOfPersonGroup Integer - PK# 1
     * @return the number of deleted rows
     */
    public int deletePersonBeansByGroupId(Integer idOfPersonGroup);
    //3.2.4 GET IMPORTED
    /**
     * Retrieves the {@link PersonBean} object from fl_person.group_id field.<BR>
     * FK_NAME:fl_person_ibfk_1
     * @param bean the {@link PersonGroupBean}
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the associated {@link PersonBean} beans or empty list if {@code bean} is {@code null}
     */
    public java.util.List<PersonBean> getPersonBeansByGroupIdAsList(PersonGroupBean bean,int startRow,int numRows);    
    //3.3 SET IMPORTED
    /**
     * set  the {@link PersonBean} object array associate to PersonGroupBean by the fl_person.group_id field.<BR>
     * FK_NAME : fl_person_ibfk_1 
     * @param bean the referenced {@link PersonGroupBean}
     * @param importedBeans imported beans from fl_person
     * @return importedBeans always
     * @see {@link PersonManager#setReferencedByGroupId(PersonBean, PersonGroupBean)
     */
    public PersonBean[] setPersonBeansByGroupId(PersonGroupBean bean , PersonBean[] importedBeans);

    //3.4 SET IMPORTED
    /**
     * set  the {@link PersonBean} object java.util.Collection associate to PersonGroupBean by the fl_person.group_id field.<BR>
     * FK_NAME:fl_person_ibfk_1
     * @param bean the referenced {@link PersonGroupBean} 
     * @param importedBeans imported beans from fl_person 
     * @return importedBeans always
     * @see {@link PersonManager#setReferencedByGroupId(PersonBean, PersonGroupBean)
     */
    public <C extends java.util.Collection<PersonBean>> C setPersonBeansByGroupId(PersonGroupBean bean , C importedBeans);

    //3.1 GET IMPORTED
    /**
     * Retrieves the {@link PersonGroupBean} object from the fl_person_group.parent field.<BR>
     * FK_NAME : fl_person_group_ibfk_1 
     * @param bean the {@link PersonGroupBean}
     * @return the associated {@link PersonGroupBean} beans or {@code null} if {@code bean} is {@code null}
     */
    public PersonGroupBean[] getPersonGroupBeansByParent(PersonGroupBean bean);
    
    //3.1.2 GET IMPORTED
    /**
     * Retrieves the {@link PersonGroupBean} object from the fl_person_group.parent field.<BR>
     * FK_NAME : fl_person_group_ibfk_1 
     * @param idOfPersonGroup Integer - PK# 1
     * @return the associated {@link PersonGroupBean} beans or {@code null} if {@code bean} is {@code null}
     * @throws DaoException
     */
    public PersonGroupBean[] getPersonGroupBeansByParent(Integer idOfPersonGroup);
    
    //3.2 GET IMPORTED
    /**
     * see also #getPersonGroupBeansByParentAsList(PersonGroupBean,int,int)
     * @param bean
     * @return
     */
    public java.util.List<PersonGroupBean> getPersonGroupBeansByParentAsList(PersonGroupBean bean);

    //3.2.2 GET IMPORTED
    /**
     * Retrieves the {@link PersonGroupBean} object from fl_person_group.parent field.<BR>
     * FK_NAME:fl_person_group_ibfk_1
     * @param idOfPersonGroup Integer - PK# 1
     * @return the associated {@link PersonGroupBean} beans 
     * @throws DaoException
     */
    public java.util.List<PersonGroupBean> getPersonGroupBeansByParentAsList(Integer idOfPersonGroup);
    //3.2.3 DELETE IMPORTED
    /**
     * delete the associated {@link PersonGroupBean} objects from fl_person_group.parent field.<BR>
     * FK_NAME:fl_person_group_ibfk_1
     * @param idOfPersonGroup Integer - PK# 1
     * @return the number of deleted rows
     */
    public int deletePersonGroupBeansByParent(Integer idOfPersonGroup);
    //3.2.4 GET IMPORTED
    /**
     * Retrieves the {@link PersonGroupBean} object from fl_person_group.parent field.<BR>
     * FK_NAME:fl_person_group_ibfk_1
     * @param bean the {@link PersonGroupBean}
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the associated {@link PersonGroupBean} beans or empty list if {@code bean} is {@code null}
     */
    public java.util.List<PersonGroupBean> getPersonGroupBeansByParentAsList(PersonGroupBean bean,int startRow,int numRows);    
    //3.3 SET IMPORTED
    /**
     * set  the {@link PersonGroupBean} object array associate to PersonGroupBean by the fl_person_group.parent field.<BR>
     * FK_NAME : fl_person_group_ibfk_1 
     * @param bean the referenced {@link PersonGroupBean}
     * @param importedBeans imported beans from fl_person_group
     * @return importedBeans always
     * @see {@link PersonGroupManager#setReferencedByParent(PersonGroupBean, PersonGroupBean)
     */
    public PersonGroupBean[] setPersonGroupBeansByParent(PersonGroupBean bean , PersonGroupBean[] importedBeans);

    //3.4 SET IMPORTED
    /**
     * set  the {@link PersonGroupBean} object java.util.Collection associate to PersonGroupBean by the fl_person_group.parent field.<BR>
     * FK_NAME:fl_person_group_ibfk_1
     * @param bean the referenced {@link PersonGroupBean} 
     * @param importedBeans imported beans from fl_person_group 
     * @return importedBeans always
     * @see {@link PersonGroupManager#setReferencedByParent(PersonGroupBean, PersonGroupBean)
     */
    public <C extends java.util.Collection<PersonGroupBean>> C setPersonGroupBeansByParent(PersonGroupBean bean , C importedBeans);

    //3.5 SYNC SAVE 
    /**
     * Save the PersonGroupBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link PersonGroupBean} bean to be saved
     * @param refPersongroupByParent the {@link PersonGroupBean} bean referenced by {@link PersonGroupBean} 
     * @param impPermitByPersonGroupId the {@link PermitBean} bean refer to {@link PersonGroupBean} 
     * @param impPersonByGroupId the {@link PersonBean} bean refer to {@link PersonGroupBean} 
     * @param impPersongroupByParent the {@link PersonGroupBean} bean refer to {@link PersonGroupBean} 
     * @return the inserted or updated {@link PersonGroupBean} bean
     */
    public PersonGroupBean save(PersonGroupBean bean
        , PersonGroupBean refPersongroupByParent 
        , PermitBean[] impPermitByPersonGroupId , PersonBean[] impPersonByGroupId , PersonGroupBean[] impPersongroupByParent );
    //3.6 SYNC SAVE AS TRANSACTION
    /**
     * Transaction version for sync save<br>
     * see also {@link #save(PersonGroupBean , PersonGroupBean , PermitBean[] , PersonBean[] , PersonGroupBean[] )}
     * @param bean the {@link PersonGroupBean} bean to be saved
     * @param refPersongroupByParent the {@link PersonGroupBean} bean referenced by {@link PersonGroupBean} 
     * @param impPermitByPersonGroupId the {@link PermitBean} bean refer to {@link PersonGroupBean} 
     * @param impPersonByGroupId the {@link PersonBean} bean refer to {@link PersonGroupBean} 
     * @param impPersongroupByParent the {@link PersonGroupBean} bean refer to {@link PersonGroupBean} 
     * @return the inserted or updated {@link PersonGroupBean} bean
     */
    public PersonGroupBean saveAsTransaction(final PersonGroupBean bean
        ,final PersonGroupBean refPersongroupByParent 
        ,final PermitBean[] impPermitByPersonGroupId ,final PersonBean[] impPersonByGroupId ,final PersonGroupBean[] impPersongroupByParent );
    //3.7 SYNC SAVE 
    /**
     * Save the PersonGroupBean bean and referenced beans and imported beans into the database.
     *
     * @param bean the {@link PersonGroupBean} bean to be saved
     * @param refPersongroupByParent the {@link PersonGroupBean} bean referenced by {@link PersonGroupBean} 
     * @param impPermitByPersonGroupId the {@link PermitBean} bean refer to {@link PersonGroupBean} 
     * @param impPersonByGroupId the {@link PersonBean} bean refer to {@link PersonGroupBean} 
     * @param impPersongroupByParent the {@link PersonGroupBean} bean refer to {@link PersonGroupBean} 
     * @return the inserted or updated {@link PersonGroupBean} bean
     */
    public PersonGroupBean save(PersonGroupBean bean
        , PersonGroupBean refPersongroupByParent 
        , java.util.Collection<PermitBean> impPermitByPersonGroupId , java.util.Collection<PersonBean> impPersonByGroupId , java.util.Collection<PersonGroupBean> impPersongroupByParent );
    //3.8 SYNC SAVE AS TRANSACTION
    /**
     * Transaction version for sync save<br>
     * see also {@link #save(PersonGroupBean , PersonGroupBean , java.util.Collection , java.util.Collection , java.util.Collection )}
     * @param bean the {@link PersonGroupBean} bean to be saved
     * @param refPersongroupByParent the {@link PersonGroupBean} bean referenced by {@link PersonGroupBean} 
     * @param impPermitByPersonGroupId the {@link PermitBean} bean refer to {@link PersonGroupBean} 
     * @param impPersonByGroupId the {@link PersonBean} bean refer to {@link PersonGroupBean} 
     * @param impPersongroupByParent the {@link PersonGroupBean} bean refer to {@link PersonGroupBean} 
     * @return the inserted or updated {@link PersonGroupBean} bean
     */
    public PersonGroupBean saveAsTransaction(final PersonGroupBean bean
        ,final PersonGroupBean refPersongroupByParent 
        ,final  java.util.Collection<PermitBean> impPermitByPersonGroupId ,final  java.util.Collection<PersonBean> impPersonByGroupId ,final  java.util.Collection<PersonGroupBean> impPersongroupByParent );
      //////////////////////////////////////
    // GET/SET FOREIGN KEY BEAN METHOD
    //////////////////////////////////////
    //5.1 GET REFERENCED VALUE
    /**
     * Retrieves the {@link PersonGroupBean} object referenced by {@link PersonGroupBean#getParent}() field.<br>
     * FK_NAME : fl_person_group_ibfk_1
     * @param bean the {@link PersonGroupBean}
     * @return the associated {@link PersonGroupBean} bean or {@code null} if {@code bean} is {@code null}
     */
    public PersonGroupBean getReferencedByParent(PersonGroupBean bean);

    //5.2 SET REFERENCED 
    /**
     * Associates the {@link PersonGroupBean} object to the {@link PersonGroupBean} object by {@link PersonGroupBean#getParent}() field.
     *
     * @param bean the {@link PersonGroupBean} object to use
     * @param beanToSet the {@link PersonGroupBean} object to associate to the {@link PersonGroupBean}
     * @return always beanToSet saved
     * @throws WrapDaoException
     */
    public PersonGroupBean setReferencedByParent(PersonGroupBean bean, PersonGroupBean beanToSet);
    //_____________________________________________________________________
    //
    // USING INDICES
    //_____________________________________________________________________


     /**
     * Retrieves an array of PersonGroupBean using the parent index.
     *
     * @param parent the parent column's value filter.
     * @return an array of PersonGroupBean
     */
    public PersonGroupBean[] loadByIndexParent(Integer parent);
    
    /**
     * Retrieves a list of PersonGroupBean using the parent index.
     *
     * @param parent the parent column's value filter.
     * @return a list of PersonGroupBean
     */
    public java.util.List<PersonGroupBean> loadByIndexParentAsList(Integer parent);

    /**
     * Deletes rows using the parent index.
     *
     * @param parent the parent column's value filter.
     * @return the number of deleted objects
     */
    public int deleteByIndexParent(Integer parent);
    

    //45
    /**
     * return a primary key list from {@link PersonGroupBean} array
     * @param beans
     * @return
     */
    public java.util.List<Integer> toPrimaryKeyList(PersonGroupBean... beans);
    //46
    /**
     * return a primary key list from {@link PersonGroupBean} collection
     * @param beans
     * @return
     */
    public java.util.List<Integer> toPrimaryKeyList(java.util.Collection<PersonGroupBean> beans);

    //_____________________________________________________________________
    //
    // MANY TO MANY: LOAD OTHER BEAN VIA JUNCTION TABLE
    //_____________________________________________________________________
    //22 MANY TO MANY
    /**
     * see also #loadViaPermitAsList(PersonGroupBean,int,int)
     * @param bean
     * @return
     */
    public java.util.List<PersonGroupBean> loadViaPermitAsList(DeviceGroupBean bean);

    //23 MANY TO MANY
    /**
     * Retrieves an list of PersonGroupBean using the junction table Permit, given a DeviceGroupBean, 
     * specifying the start row and the number of rows.
     *
     * @param bean the DeviceGroupBean bean to be used
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return a list of PersonGroupBean
     */
    public java.util.List<PersonGroupBean> loadViaPermitAsList(DeviceGroupBean bean, int startRow, int numRows);
    //23.2 MANY TO MANY
    /**
     * add junction between {@link PersonGroupBean} and {@link DeviceGroupBean} if junction not exists
     * @param bean
     * @param linked
     */
    public void addJunction(PersonGroupBean bean,DeviceGroupBean linked);
    //23.3 MANY TO MANY
    /**
     * remove junction between {@link PersonGroupBean} and {@link DeviceGroupBean}
     * @param bean
     * @param linked
     * @return deleted rows count
     */
    public int deleteJunction(PersonGroupBean bean,DeviceGroupBean linked);
    //23.4 MANY TO MANY
    /** 
     * see also {@link #addJunction(PersonGroupBean,DeviceGroupBean)}
     * @param bean
     * @param linkedBeans
     */
    public void addJunction(PersonGroupBean bean,DeviceGroupBean... linkedBeans);
    //23.5 MANY TO MANY
    /** 
     * see also {@link #addJunction(PersonGroupBean,DeviceGroupBean)}
     * @param bean
     * @param linkedBeans
     */
    public void addJunction(PersonGroupBean bean,java.util.Collection<DeviceGroupBean> linkedBeans);
    //23.6 MANY TO MANY
    /** 
     * see also {@link #deleteJunction(PersonGroupBean,DeviceGroupBean)}
     * @param bean
     * @param linkedBeans
     * @return
     */
    public int deleteJunction(PersonGroupBean bean,DeviceGroupBean... linkedBeans);
    //23.7 MANY TO MANY
    /** 
     * see also {@link #deleteJunction(PersonGroupBean,DeviceGroupBean)} 
     * @param bean
     * @param linkedBeans
     * @return
     */
    public int deleteJunction(PersonGroupBean bean,java.util.Collection<DeviceGroupBean> linkedBeans);

    //_____________________________________________________________________
    //
    // SELF-REFERENCE
    //_____________________________________________________________________
    //47
    /**
     * return bean list ( include {@code bean}) by the self-reference field : {@code fl_person_group(parent) }<br>
     * first element is top bean
     * @param id PK# 1 
     * @return  empty list if input primary key is {@code null}<br>
     *         first element equal last if self-reference field is cycle
     * @throws WrapDaoException
     */
    public java.util.List<PersonGroupBean> listOfParent(Integer id);
    //48
    /**
     * see also {@link #listOfParent(Integer)}
     * @param bean
     * @return
     */
    public java.util.List<PersonGroupBean> listOfParent(PersonGroupBean bean);
    //49
    /**
     * get level count on the self-reference field : {@code fl_person_group(parent) }
     * @param id PK# 1 
     * @return  0 if input primary key is {@code null}<br>
     *         -1 if self-reference field is cycle
     * @throws WrapDaoException
     */
    public int levelOfParent(Integer id);
    //50
    /**
     * see also {@link #levelOfParent(Integer)}
     * @param bean
     * @return 
     */
    public int levelOfParent(PersonGroupBean bean);
    //51
    /**
     * test whether the self-reference field is cycle : {@code fl_person_group(parent) }
     * @param id PK# 1 
     * @throws WrapDaoException
     * @see #levelOfParent(PersonGroupBean)
     * @return
     */
    public boolean isCycleOnParent(Integer id);
    //52
    /**
     * test whether the self-reference field is cycle : {@code fl_person_group(parent) }
     * @param bean
     * @return
     * @throws WrapDaoException
     * @see #levelOfParent(PersonGroupBean)
     */
    public boolean isCycleOnParent(PersonGroupBean bean);
    //53
    /**
     * return top bean that with {@code null} self-reference field  : {@code fl_person_group(parent) }
     * @param id PK# 1 
     * @return top bean
     * @throws NullPointerException if input primary key is {@code null}
     * @throws IllegalStateException if self-reference field is cycle
     * @throws WrapDaoException
     */
    public PersonGroupBean topOfParent(Integer id);
    //54
    /**
     * see also {@link #topOfParent(Integer)}
     * @param bean
     * @return
     */
    public PersonGroupBean topOfParent(PersonGroupBean bean);
    //55
    /**
     * Ensures the self-reference field is not cycle : {@code fl_person_group(parent) }
     * @param id PK# 1
     * @return always {@code id}
     * @throws IllegalStateException if self-reference field is cycle 
     * @throws WrapDaoException
     * @see #isCycleOnParent(Integer)
     */
    public Integer checkCycleOfParent(Integer id);
    //56
    /**
     * Ensures the self-reference field is not cycle : {@code fl_person_group(parent) }<br>
     * @param bean
     * @return always {@code bean}
     * @throws IllegalStateException if self-reference field is cycle
     * @throws WrapDaoException
     * @see #isCycleOnParent(PersonGroupBean)
     */
    public PersonGroupBean checkCycleOfParent(PersonGroupBean bean);
}
