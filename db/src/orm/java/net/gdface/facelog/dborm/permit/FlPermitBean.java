// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: bean.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm.permit;
import java.io.Serializable;
import net.gdface.facelog.dborm.Constant;
import net.gdface.facelog.dborm.BaseBean;
import net.gdface.facelog.dborm.device.FlDeviceGroupBean;
import net.gdface.facelog.dborm.person.FlPersonGroupBean;
import net.gdface.facelog.dborm.CompareToBuilder;
import net.gdface.facelog.dborm.EqualsBuilder;
import net.gdface.facelog.dborm.HashCodeBuilder;
/**
 * FlPermitBean is a mapping of fl_permit Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 通行权限关联表 </li>
 * </ul>
 * @author guyadong
*/
public  class FlPermitBean
    implements Serializable,BaseBean<FlPermitBean>,Comparable<FlPermitBean>,Constant,Cloneable
{
    private static final long serialVersionUID = 6130251228058473821L;
    
    /** comments:外键,设备组id */
    private Integer deviceGroupId;

    /** comments:外键,人员组id */
    private Integer personGroupId;

    private java.util.Date createTime/* DEFAULT:'CURRENT_TIMESTAMP'*/;

    /** columns modified flag */
    private long modified = 0L;
    /** columns initialized flag */
    private long initialized = 0L;
    private boolean _isNew = true;
    /**
     * Determines if the current object is new.
     *
     * @return true if the current object is new, false if the object is not new
     */
    public boolean isNew()
    {
        return _isNew;
    }

    /**
     * Specifies to the object if it has been set as new.
     *
     * @param isNew the boolean value to be assigned to the isNew field
     */
    public void isNew(boolean isNew)
    {
        this._isNew = isNew;
    }
    /**
     * Specifies to the object if it has been set as new.
     *
     * @param isNew the boolean value to be assigned to the isNew field
     */
    public void setNew(boolean isNew)
    {
        this._isNew = isNew;
    }
    /**
     * @return the modified status of columns
     */
    public long getModified(){
        return modified;
    }

    /**
     * @param modified the modified status bit to be assigned to {@link #modified}
     */
    public void setModified(long modified){
        this.modified = modified;
    }
    /**
     * @return the initialized status of columns
     */
    public long getInitialized(){
        return initialized;
    }

    /**
     * @param initialized the initialized status bit to be assigned to {@link #initialized}
     */
    public void setInitialized(long initialized){
        this.initialized = initialized;
    }
    public FlPermitBean(){
        super();
    }
    /**
     * construct a new instance filled with primary keys
     * @param deviceGroupId PK# 1 
     @param personGroupId PK# 2 
     */
    public FlPermitBean(Integer deviceGroupId,Integer personGroupId){
        super();
        setDeviceGroupId(deviceGroupId);
        setPersonGroupId(personGroupId);
    }
    /**
     * Getter method for {@link #deviceGroupId}.<br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_permit.device_group_id</li>
     * <li> foreign key: fl_device_group.id</li>
     * <li>comments: 外键,设备组id</li>
     * <li>NOT NULL</li>
     * <li>column size: 10</li>
     * <li>JDBC type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of deviceGroupId
     */
    public Integer getDeviceGroupId(){
        return deviceGroupId;
    }
    /**
     * Setter method for {@link #deviceGroupId}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value (NOT NULL) to be assigned to deviceGroupId
     */
    public void setDeviceGroupId(Integer newVal)
    {
        if ((newVal != null && deviceGroupId != null && (newVal.compareTo(deviceGroupId) == 0)) ||
            (newVal == null && deviceGroupId == null && checkDeviceGroupIdInitialized())) {
            return;
        }
        deviceGroupId = newVal;

        modified |= FL_PERMIT_ID_DEVICE_GROUP_ID_MASK;
        initialized |= FL_PERMIT_ID_DEVICE_GROUP_ID_MASK;
    }

    /**
     * Setter method for {@link #deviceGroupId}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to deviceGroupId
     */
    public void setDeviceGroupId(int newVal)
    {
        setDeviceGroupId(new Integer(newVal));
    }
    /**
     * Determines if the deviceGroupId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkDeviceGroupIdModified()
    {
        return 0L !=  (modified & FL_PERMIT_ID_DEVICE_GROUP_ID_MASK);
    }

    /**
     * Determines if the deviceGroupId has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkDeviceGroupIdInitialized()
    {
        return 0L !=  (initialized & FL_PERMIT_ID_DEVICE_GROUP_ID_MASK);
    }
    /**
     * Getter method for {@link #personGroupId}.<br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_permit.person_group_id</li>
     * <li> foreign key: fl_person_group.id</li>
     * <li>comments: 外键,人员组id</li>
     * <li>NOT NULL</li>
     * <li>column size: 10</li>
     * <li>JDBC type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of personGroupId
     */
    public Integer getPersonGroupId(){
        return personGroupId;
    }
    /**
     * Setter method for {@link #personGroupId}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value (NOT NULL) to be assigned to personGroupId
     */
    public void setPersonGroupId(Integer newVal)
    {
        if ((newVal != null && personGroupId != null && (newVal.compareTo(personGroupId) == 0)) ||
            (newVal == null && personGroupId == null && checkPersonGroupIdInitialized())) {
            return;
        }
        personGroupId = newVal;

        modified |= FL_PERMIT_ID_PERSON_GROUP_ID_MASK;
        initialized |= FL_PERMIT_ID_PERSON_GROUP_ID_MASK;
    }

    /**
     * Setter method for {@link #personGroupId}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to personGroupId
     */
    public void setPersonGroupId(int newVal)
    {
        setPersonGroupId(new Integer(newVal));
    }
    /**
     * Determines if the personGroupId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkPersonGroupIdModified()
    {
        return 0L !=  (modified & FL_PERMIT_ID_PERSON_GROUP_ID_MASK);
    }

    /**
     * Determines if the personGroupId has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkPersonGroupIdInitialized()
    {
        return 0L !=  (initialized & FL_PERMIT_ID_PERSON_GROUP_ID_MASK);
    }
    /**
     * Getter method for {@link #createTime}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_permit.create_time</li>
     * <li>default value: 'CURRENT_TIMESTAMP'</li>
     * <li>NOT NULL</li>
     * <li>column size: 19</li>
     * <li>JDBC type returned by the driver: Types.TIMESTAMP</li>
     * </ul>
     *
     * @return the value of createTime
     */
    public java.util.Date getCreateTime(){
        return createTime;
    }
    /**
     * Setter method for {@link #createTime}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value (NOT NULL) to be assigned to createTime
     */
    public void setCreateTime(java.util.Date newVal)
    {
        if ((newVal != null && createTime != null && (newVal.compareTo(createTime) == 0)) ||
            (newVal == null && createTime == null && checkCreateTimeInitialized())) {
            return;
        }
        createTime = newVal;

        modified |= FL_PERMIT_ID_CREATE_TIME_MASK;
        initialized |= FL_PERMIT_ID_CREATE_TIME_MASK;
    }

    /**
     * Setter method for {@link #createTime}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to createTime
     */
    public void setCreateTime(long newVal)
    {
        setCreateTime(new java.util.Date(newVal));
    }
    /**
     * Determines if the createTime has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkCreateTimeModified()
    {
        return 0L !=  (modified & FL_PERMIT_ID_CREATE_TIME_MASK);
    }

    /**
     * Determines if the createTime has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkCreateTimeInitialized()
    {
        return 0L !=  (initialized & FL_PERMIT_ID_CREATE_TIME_MASK);
    }
    //////////////////////////////////////
    // referenced bean for FOREIGN KEYS
    //////////////////////////////////////
    /** 
     * The referenced {@link FlDeviceGroupBean} by {@link #deviceGroupId} . <br>
     * FOREIGN KEY (device_group_id) REFERENCES fl_device_group(id)
     */
    private FlDeviceGroupBean referencedByDeviceGroupId;
    /** Getter method for {@link #referencedByDeviceGroupId}. */
    public FlDeviceGroupBean getReferencedByDeviceGroupId() {
        return this.referencedByDeviceGroupId;
    }
    /** Setter method for {@link #referencedByDeviceGroupId}. */
    public void setReferencedByDeviceGroupId(FlDeviceGroupBean reference) {
        this.referencedByDeviceGroupId = reference;
    }
    /** 
     * The referenced {@link FlPersonGroupBean} by {@link #personGroupId} . <br>
     * FOREIGN KEY (person_group_id) REFERENCES fl_person_group(id)
     */
    private FlPersonGroupBean referencedByPersonGroupId;
    /** Getter method for {@link #referencedByPersonGroupId}. */
    public FlPersonGroupBean getReferencedByPersonGroupId() {
        return this.referencedByPersonGroupId;
    }
    /** Setter method for {@link #referencedByPersonGroupId}. */
    public void setReferencedByPersonGroupId(FlPersonGroupBean reference) {
        this.referencedByPersonGroupId = reference;
    }

    /**
     * Determines if the object has been modified since the last time this method was called.
     * <br>
     * We can also determine if this object has ever been modified since its creation.
     *
     * @return true if the object has been modified, false if the object has not been modified
     */
    public boolean isModified()
    {
        return 0 != modified;
    }
  
    /**
     * Determines if the {@code column} has been modified.
     * @param columnID
     * @return true if the field has been modified, false if the field has not been modified
     * @author guyadong
     */
    public boolean isModified(int columnID){
        switch ( columnID ){
        case FL_PERMIT_ID_DEVICE_GROUP_ID:
            return checkDeviceGroupIdModified();
        case FL_PERMIT_ID_PERSON_GROUP_ID:
            return checkPersonGroupIdModified();
        case FL_PERMIT_ID_CREATE_TIME:
            return checkCreateTimeModified();
        }
        return false;
    }
    /**
     * Determines if the {@code column} has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     * @param columnID
     * @return true if the field has been initialized, false otherwise
     * @author guyadong
     */
    public boolean isInitialized(int columnID){
        switch(columnID) {
        case FL_PERMIT_ID_DEVICE_GROUP_ID:
            return checkDeviceGroupIdInitialized();
        case FL_PERMIT_ID_PERSON_GROUP_ID:
            return checkPersonGroupIdInitialized();
        case FL_PERMIT_ID_CREATE_TIME:
            return checkCreateTimeInitialized();
        }
        return false;
    }
    
    /**
     * Determines if the {@code column} has been modified.
     * @param column
     * @return true if the field has been modified, false if the field has not been modified
     * @author guyadong
     */
    public boolean isModified(String column){        
        return isModified(columnIDOf(column));
    }

    /**
     * Determines if the {@code column} has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     * @param column
     * @return true if the field has been initialized, false otherwise
     * @author guyadong
     */
    public boolean isInitialized(String column){
        return isInitialized(columnIDOf(column));
    }
    
    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified()
    {
        modified = 0L;
    }
    /**
     * Resets the primary keys ( {@link #deviceGroupId},{@link #personGroupId} ) modification status to 'not modified'.
     */
    public void resetPrimaryKeysModified()
    {
        modified &= (~(FL_PERMIT_ID_DEVICE_GROUP_ID_MASK |
            FL_PERMIT_ID_PERSON_GROUP_ID_MASK));
    }
    /**
     * Resets columns modification status except primary keys to 'not modified'.
     */
    public void resetModifiedExceptPrimaryKeys()
    {
        modified &= (~(FL_PERMIT_ID_CREATE_TIME_MASK));
    }
    /**
     * Resets the object initialization status to 'not initialized'.
     */
    private void resetInitialized()
    {
        initialized = 0L;
    }
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof FlPermitBean)) {
            return false;
        }

        FlPermitBean obj = (FlPermitBean) object;
        return new EqualsBuilder()
            .append(getDeviceGroupId(), obj.getDeviceGroupId())
            .append(getPersonGroupId(), obj.getPersonGroupId())
            .append(getCreateTime(), obj.getCreateTime())
            .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
            .append(getDeviceGroupId())
            .append(getPersonGroupId())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[\n")
            .append("\tdevice_group_id=").append(getDeviceGroupId()).append("\n")
            .append("\tperson_group_id=").append(getPersonGroupId()).append("\n")
            .append("\tcreate_time=").append(getCreateTime()).append("\n")
            .append("]\n")
            .toString();
    }

    @Override
    public int compareTo(FlPermitBean object){
        return new CompareToBuilder()
            .append(getDeviceGroupId(), object.getDeviceGroupId())
            .append(getPersonGroupId(), object.getPersonGroupId())
            .append(getCreateTime(), object.getCreateTime())
            .toComparison();
    }
    @Override
    public FlPermitBean clone(){
        try {
            return (FlPermitBean) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
    * set all field to null
    *
    * @author guyadong
    */
    public FlPermitBean clean()
    {
        setDeviceGroupId(null);
        setPersonGroupId(null);
        setCreateTime(null);
        isNew(true);
        resetInitialized();
        resetIsModified();
        return this;
    }
    
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @param fieldList the column id list to copy into the current bean
     */
    public void copy(FlPermitBean bean, int... fieldList)
    {
        if (null == fieldList || 0 == fieldList.length)
            for (int i = 0; i < 3; ++i) {
                if( bean.isInitialized(i))
                    setValue(i, bean.getValue(i));
            }
        else
            for (int i = 0; i < fieldList.length; ++i) {
                if( bean.isInitialized(fieldList[i]))
                    setValue(fieldList[i], bean.getValue(fieldList[i]));
            }
    }
        
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @param fieldList the column name list to copy into the current bean
     */
    public void copy(FlPermitBean bean, String... fieldList)
    {
        if (null == fieldList || 0 == fieldList.length)
            copy(bean,(int[])null);
        else{
            int field;
            for (int i = 0; i < fieldList.length; i++) {
                field = columnIDOf(fieldList[i].trim());
                if(bean.isInitialized(field))
                    setValue(field, bean.getValue(field));
            }
        }
    }

    /**
     * return a object representation of the given column id
     */
    @SuppressWarnings("unchecked")
    public <T>T getValue(int columnID)
    {
        switch( columnID ){
        case FL_PERMIT_ID_DEVICE_GROUP_ID: 
            return (T)getDeviceGroupId();        
        case FL_PERMIT_ID_PERSON_GROUP_ID: 
            return (T)getPersonGroupId();        
        case FL_PERMIT_ID_CREATE_TIME: 
            return (T)getCreateTime();        
        }
        return null;
    }

    /**
     * set a value representation of the given column id
     */
    public <T> void setValue(int columnID,T value)
    {
        switch( columnID ) {
        case FL_PERMIT_ID_DEVICE_GROUP_ID:        
            setDeviceGroupId((Integer)value);
        case FL_PERMIT_ID_PERSON_GROUP_ID:        
            setPersonGroupId((Integer)value);
        case FL_PERMIT_ID_CREATE_TIME:        
            setCreateTime((java.util.Date)value);
        }
    }
    
    /**
     * return a object representation of the given field
     */
    public <T>T getValue(String column)
    {
        return getValue(columnIDOf(column));
    }

    /**
     * set a value representation of the given field
     */
    public <T>void setValue(String column,T value)
    {
        setValue(columnIDOf(column),value);
    }

    public static int columnIDOf(String column){
        int index = FL_PERMIT_FIELDS_LIST.indexOf(column);
        if( 0 > index ) 
            index = FL_PERMIT_JAVA_FIELDS_LIST.indexOf(column);
        return index;    
    }
}
