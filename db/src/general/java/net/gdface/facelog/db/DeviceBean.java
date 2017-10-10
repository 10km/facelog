// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.db;
import java.io.Serializable;
import com.facebook.swift.codec.ThriftStruct;
import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftField.Requiredness;
/**
 * DeviceBean is a mapping of fl_device Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 前端设备基本信息 </li>
 * </ul>
 * @author guyadong
*/
@ThriftStruct
public final class DeviceBean
    implements Serializable,BaseBean<DeviceBean>,Comparable<DeviceBean>,Constant,Cloneable
{
    private static final long serialVersionUID = -1983784566394161565L;
    
    /** comments:设备id */
    private Integer id;

    /** comments:设备名称 */
    private String name;

    /** comments:设备所属组id */
    private Integer groupId;

    /** comments:设备版本号 */
    private String version;

    /** comments:设备序列号 */
    private String serialNo;

    /** comments:6字节MAC地址(HEX) */
    private String mac;

    private java.util.Date createTime; // DEFAULT 'CURRENT_TIMESTAMP';

    private java.util.Date updateTime; // DEFAULT 'CURRENT_TIMESTAMP';

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
    @ThriftField(value=1,requiredness=Requiredness.REQUIRED)
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
    @ThriftField
    public void setNew(boolean isNew)
    {
        this._isNew = isNew;
    }
    /**
     * @return the modified status of columns
     */
    @ThriftField(value=2,requiredness=Requiredness.REQUIRED)
    public long getModified(){
        return modified;
    }

    /**
     * @param modified the modified status bit to be assigned to {@link #modified}
     */
    @ThriftField
    public void setModified(long modified){
        this.modified = modified;
    }
    /**
     * @return the initialized status of columns
     */
    @ThriftField(value=3,requiredness=Requiredness.REQUIRED)
    public long getInitialized(){
        return initialized;
    }

    /**
     * @param initialized the initialized status bit to be assigned to {@link #initialized}
     */
    @ThriftField
    public void setInitialized(long initialized){
        this.initialized = initialized;
    }
    public DeviceBean(){
        super();
    }
    /**
     * Getter method for {@link #id}.<br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_device.id</li>
     * <li> imported key: fl_log.device_id</li>
     * <li> imported key: fl_image.device_id</li>
     * <li>comments: 设备id</li>
     * <li>AUTO_INCREMENT</li>
     * <li>NOT NULL</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of id
     */
    @ThriftField(value=4)
    public Integer getId(){
        return id;
    }
    /**
     * Setter method for {@link #id}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value (NOT NULL) to be assigned to id
     */
    @ThriftField
    public void setId(Integer newVal)
    {
        if ((newVal != null && id != null && (newVal.compareTo(id) == 0)) ||
            (newVal == null && id == null && checkIdInitialized())) {
            return;
        }
        id = newVal;

        modified |= FL_DEVICE_ID_ID_MASK;
        initialized |= FL_DEVICE_ID_ID_MASK;
    }

    /**
     * Setter method for {@link #id}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to id
     */
    public void setId(int newVal)
    {
        setId(new Integer(newVal));
    }
    /**
     * Determines if the id has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkIdModified()
    {
        return 0L !=  (modified & FL_DEVICE_ID_ID_MASK);
    }

    /**
     * Determines if the id has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkIdInitialized()
    {
        return 0L !=  (initialized & FL_DEVICE_ID_ID_MASK);
    }
    /**
     * Getter method for {@link #name}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_device.name</li>
     * <li>comments: 设备名称</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of name
     */
    @ThriftField(value=5)
    public String getName(){
        return name;
    }
    /**
     * Setter method for {@link #name}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value  to be assigned to name
     */
    @ThriftField
    public void setName(String newVal)
    {
        if ((newVal != null && name != null && (newVal.compareTo(name) == 0)) ||
            (newVal == null && name == null && checkNameInitialized())) {
            return;
        }
        name = newVal;

        modified |= FL_DEVICE_ID_NAME_MASK;
        initialized |= FL_DEVICE_ID_NAME_MASK;
    }

    /**
     * Determines if the name has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkNameModified()
    {
        return 0L !=  (modified & FL_DEVICE_ID_NAME_MASK);
    }

    /**
     * Determines if the name has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkNameInitialized()
    {
        return 0L !=  (initialized & FL_DEVICE_ID_NAME_MASK);
    }
    /**
     * Getter method for {@link #groupId}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_device.group_id</li>
     * <li>comments: 设备所属组id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of groupId
     */
    @ThriftField(value=6)
    public Integer getGroupId(){
        return groupId;
    }
    /**
     * Setter method for {@link #groupId}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value  to be assigned to groupId
     */
    @ThriftField
    public void setGroupId(Integer newVal)
    {
        if ((newVal != null && groupId != null && (newVal.compareTo(groupId) == 0)) ||
            (newVal == null && groupId == null && checkGroupIdInitialized())) {
            return;
        }
        groupId = newVal;

        modified |= FL_DEVICE_ID_GROUP_ID_MASK;
        initialized |= FL_DEVICE_ID_GROUP_ID_MASK;
    }

    /**
     * Setter method for {@link #groupId}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to groupId
     */
    public void setGroupId(int newVal)
    {
        setGroupId(new Integer(newVal));
    }
    /**
     * Determines if the groupId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkGroupIdModified()
    {
        return 0L !=  (modified & FL_DEVICE_ID_GROUP_ID_MASK);
    }

    /**
     * Determines if the groupId has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkGroupIdInitialized()
    {
        return 0L !=  (initialized & FL_DEVICE_ID_GROUP_ID_MASK);
    }
    /**
     * Getter method for {@link #version}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_device.version</li>
     * <li>comments: 设备版本号</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of version
     */
    @ThriftField(value=7)
    public String getVersion(){
        return version;
    }
    /**
     * Setter method for {@link #version}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value  to be assigned to version
     */
    @ThriftField
    public void setVersion(String newVal)
    {
        if ((newVal != null && version != null && (newVal.compareTo(version) == 0)) ||
            (newVal == null && version == null && checkVersionInitialized())) {
            return;
        }
        version = newVal;

        modified |= FL_DEVICE_ID_VERSION_MASK;
        initialized |= FL_DEVICE_ID_VERSION_MASK;
    }

    /**
     * Determines if the version has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkVersionModified()
    {
        return 0L !=  (modified & FL_DEVICE_ID_VERSION_MASK);
    }

    /**
     * Determines if the version has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkVersionInitialized()
    {
        return 0L !=  (initialized & FL_DEVICE_ID_VERSION_MASK);
    }
    /**
     * Getter method for {@link #serialNo}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_device.serial_no</li>
     * <li>comments: 设备序列号</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of serialNo
     */
    @ThriftField(value=8)
    public String getSerialNo(){
        return serialNo;
    }
    /**
     * Setter method for {@link #serialNo}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value  to be assigned to serialNo
     */
    @ThriftField
    public void setSerialNo(String newVal)
    {
        if ((newVal != null && serialNo != null && (newVal.compareTo(serialNo) == 0)) ||
            (newVal == null && serialNo == null && checkSerialNoInitialized())) {
            return;
        }
        serialNo = newVal;

        modified |= FL_DEVICE_ID_SERIAL_NO_MASK;
        initialized |= FL_DEVICE_ID_SERIAL_NO_MASK;
    }

    /**
     * Determines if the serialNo has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkSerialNoModified()
    {
        return 0L !=  (modified & FL_DEVICE_ID_SERIAL_NO_MASK);
    }

    /**
     * Determines if the serialNo has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkSerialNoInitialized()
    {
        return 0L !=  (initialized & FL_DEVICE_ID_SERIAL_NO_MASK);
    }
    /**
     * Getter method for {@link #mac}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_device.mac</li>
     * <li>comments: 6字节MAC地址(HEX)</li>
     * <li>column size: 12</li>
     * <li>jdbc type returned by the driver: Types.CHAR</li>
     * </ul>
     *
     * @return the value of mac
     */
    @ThriftField(value=9)
    public String getMac(){
        return mac;
    }
    /**
     * Setter method for {@link #mac}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value  to be assigned to mac
     */
    @ThriftField
    public void setMac(String newVal)
    {
        if ((newVal != null && mac != null && (newVal.compareTo(mac) == 0)) ||
            (newVal == null && mac == null && checkMacInitialized())) {
            return;
        }
        mac = newVal;

        modified |= FL_DEVICE_ID_MAC_MASK;
        initialized |= FL_DEVICE_ID_MAC_MASK;
    }

    /**
     * Determines if the mac has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkMacModified()
    {
        return 0L !=  (modified & FL_DEVICE_ID_MAC_MASK);
    }

    /**
     * Determines if the mac has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkMacInitialized()
    {
        return 0L !=  (initialized & FL_DEVICE_ID_MAC_MASK);
    }
    /**
     * Getter method for {@link #createTime}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_device.create_time</li>
     * <li>default value: ; // DEFAULT 'CURRENT_TIMESTAMP'</li>
     * <li>NOT NULL</li>
     * <li>column size: 19</li>
     * <li>jdbc type returned by the driver: Types.TIMESTAMP</li>
     * </ul>
     *
     * @return the value of createTime
     */
    public java.util.Date getCreateTime(){
        return createTime;
    }
    /** 
     * use Long to represent date type for thrift:swift support 
     * @see #getCreateTime()
     */
    @ThriftField(name = "createTime",value = 10)
    public Long readCreateTime(){
        return null == createTime ? null:createTime.getTime();
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

        modified |= FL_DEVICE_ID_CREATE_TIME_MASK;
        initialized |= FL_DEVICE_ID_CREATE_TIME_MASK;
    }

    /** 
     * use Long to represent date type for thrift:swift support
     * @see #setCreateTime(java.util.Date)  
     */
    @ThriftField(name = "createTime",value = 10)
    public void writeCreateTime(Long newVal){
        setCreateTime(null == newVal?null:new java.util.Date(newVal));
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
        return 0L !=  (modified & FL_DEVICE_ID_CREATE_TIME_MASK);
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
        return 0L !=  (initialized & FL_DEVICE_ID_CREATE_TIME_MASK);
    }
    /**
     * Getter method for {@link #updateTime}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_device.update_time</li>
     * <li>default value: ; // DEFAULT 'CURRENT_TIMESTAMP'</li>
     * <li>NOT NULL</li>
     * <li>column size: 19</li>
     * <li>jdbc type returned by the driver: Types.TIMESTAMP</li>
     * </ul>
     *
     * @return the value of updateTime
     */
    public java.util.Date getUpdateTime(){
        return updateTime;
    }
    /** 
     * use Long to represent date type for thrift:swift support 
     * @see #getUpdateTime()
     */
    @ThriftField(name = "updateTime",value = 11)
    public Long readUpdateTime(){
        return null == updateTime ? null:updateTime.getTime();
    }
    /**
     * Setter method for {@link #updateTime}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value (NOT NULL) to be assigned to updateTime
     */
    public void setUpdateTime(java.util.Date newVal)
    {
        if ((newVal != null && updateTime != null && (newVal.compareTo(updateTime) == 0)) ||
            (newVal == null && updateTime == null && checkUpdateTimeInitialized())) {
            return;
        }
        updateTime = newVal;

        modified |= FL_DEVICE_ID_UPDATE_TIME_MASK;
        initialized |= FL_DEVICE_ID_UPDATE_TIME_MASK;
    }

    /** 
     * use Long to represent date type for thrift:swift support
     * @see #setUpdateTime(java.util.Date)  
     */
    @ThriftField(name = "updateTime",value = 11)
    public void writeUpdateTime(Long newVal){
        setUpdateTime(null == newVal?null:new java.util.Date(newVal));
    }
    /**
     * Setter method for {@link #updateTime}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to updateTime
     */
    public void setUpdateTime(long newVal)
    {
        setUpdateTime(new java.util.Date(newVal));
    }
    /**
     * Determines if the updateTime has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkUpdateTimeModified()
    {
        return 0L !=  (modified & FL_DEVICE_ID_UPDATE_TIME_MASK);
    }

    /**
     * Determines if the updateTime has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkUpdateTimeInitialized()
    {
        return 0L !=  (initialized & FL_DEVICE_ID_UPDATE_TIME_MASK);
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
        case FL_DEVICE_ID_ID:
            return checkIdModified();
        case FL_DEVICE_ID_NAME:
            return checkNameModified();
        case FL_DEVICE_ID_GROUP_ID:
            return checkGroupIdModified();
        case FL_DEVICE_ID_VERSION:
            return checkVersionModified();
        case FL_DEVICE_ID_SERIAL_NO:
            return checkSerialNoModified();
        case FL_DEVICE_ID_MAC:
            return checkMacModified();
        case FL_DEVICE_ID_CREATE_TIME:
            return checkCreateTimeModified();
        case FL_DEVICE_ID_UPDATE_TIME:
            return checkUpdateTimeModified();
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
        case FL_DEVICE_ID_ID:
            return checkIdInitialized();
        case FL_DEVICE_ID_NAME:
            return checkNameInitialized();
        case FL_DEVICE_ID_GROUP_ID:
            return checkGroupIdInitialized();
        case FL_DEVICE_ID_VERSION:
            return checkVersionInitialized();
        case FL_DEVICE_ID_SERIAL_NO:
            return checkSerialNoInitialized();
        case FL_DEVICE_ID_MAC:
            return checkMacInitialized();
        case FL_DEVICE_ID_CREATE_TIME:
            return checkCreateTimeInitialized();
        case FL_DEVICE_ID_UPDATE_TIME:
            return checkUpdateTimeInitialized();
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
     * Resets the primary keys ( {@link #id} ) modification status to 'not modified'.
     */
    public void resetPrimaryKeysModified()
    {
        modified &= (~(FL_DEVICE_ID_ID_MASK));
    }
    /**
     * Resets columns modification status except primary keys to 'not modified'.
     */
    public void resetModifiedExceptPrimaryKeys()
    {
        modified &= (~(FL_DEVICE_ID_NAME_MASK |
            FL_DEVICE_ID_GROUP_ID_MASK |
            FL_DEVICE_ID_VERSION_MASK |
            FL_DEVICE_ID_SERIAL_NO_MASK |
            FL_DEVICE_ID_MAC_MASK |
            FL_DEVICE_ID_CREATE_TIME_MASK |
            FL_DEVICE_ID_UPDATE_TIME_MASK));
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
        if (!(object instanceof DeviceBean)) {
            return false;
        }

        DeviceBean obj = (DeviceBean) object;
        return new EqualsBuilder()
            .append(getId(), obj.getId())
            .append(getName(), obj.getName())
            .append(getGroupId(), obj.getGroupId())
            .append(getVersion(), obj.getVersion())
            .append(getSerialNo(), obj.getSerialNo())
            .append(getMac(), obj.getMac())
            .append(getCreateTime(), obj.getCreateTime())
            .append(getUpdateTime(), obj.getUpdateTime())
            .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
            .append(getId())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[\n")
            .append("\tid=").append(getId()).append("\n")
            .append("\tname=").append(getName()).append("\n")
            .append("\tgroup_id=").append(getGroupId()).append("\n")
            .append("\tversion=").append(getVersion()).append("\n")
            .append("\tserial_no=").append(getSerialNo()).append("\n")
            .append("\tmac=").append(getMac()).append("\n")
            .append("\tcreate_time=").append(getCreateTime()).append("\n")
            .append("\tupdate_time=").append(getUpdateTime()).append("\n")
            .append("]\n")
            .toString();
    }

    @Override
    public int compareTo(DeviceBean object){
        return new CompareToBuilder()
            .append(getId(), object.getId())
            .append(getName(), object.getName())
            .append(getGroupId(), object.getGroupId())
            .append(getVersion(), object.getVersion())
            .append(getSerialNo(), object.getSerialNo())
            .append(getMac(), object.getMac())
            .append(getCreateTime(), object.getCreateTime())
            .append(getUpdateTime(), object.getUpdateTime())
            .toComparison();
    }
    @Override
    public DeviceBean clone(){
        try {
            return (DeviceBean) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
    * set all field to null
    *
    * @author guyadong
    */
    public DeviceBean clean()
    {
        setId(null);
        setName(null);
        setGroupId(null);
        setVersion(null);
        setSerialNo(null);
        setMac(null);
        setCreateTime(null);
        setUpdateTime(null);
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
    public void copy(DeviceBean bean, int... fieldList)
    {
        if (null == fieldList || 0 == fieldList.length)
            for (int i = 0; i < 8; ++i) {
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
    public void copy(DeviceBean bean, String... fieldList)
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
        case FL_DEVICE_ID_ID: 
            return (T)getId();        
        case FL_DEVICE_ID_NAME: 
            return (T)getName();        
        case FL_DEVICE_ID_GROUP_ID: 
            return (T)getGroupId();        
        case FL_DEVICE_ID_VERSION: 
            return (T)getVersion();        
        case FL_DEVICE_ID_SERIAL_NO: 
            return (T)getSerialNo();        
        case FL_DEVICE_ID_MAC: 
            return (T)getMac();        
        case FL_DEVICE_ID_CREATE_TIME: 
            return (T)getCreateTime();        
        case FL_DEVICE_ID_UPDATE_TIME: 
            return (T)getUpdateTime();        
        }
        return null;
    }

    /**
     * set a value representation of the given column id
     */
    public <T> void setValue(int columnID,T value)
    {
        switch( columnID ) {
        case FL_DEVICE_ID_ID:        
            setId((Integer)value);
        case FL_DEVICE_ID_NAME:        
            setName((String)value);
        case FL_DEVICE_ID_GROUP_ID:        
            setGroupId((Integer)value);
        case FL_DEVICE_ID_VERSION:        
            setVersion((String)value);
        case FL_DEVICE_ID_SERIAL_NO:        
            setSerialNo((String)value);
        case FL_DEVICE_ID_MAC:        
            setMac((String)value);
        case FL_DEVICE_ID_CREATE_TIME:        
            setCreateTime((java.util.Date)value);
        case FL_DEVICE_ID_UPDATE_TIME:        
            setUpdateTime((java.util.Date)value);
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
        int index = FL_DEVICE_FIELDS_LIST.indexOf(column);
        if( 0 > index ) 
            index = FL_DEVICE_JAVA_FIELDS_LIST.indexOf(column);
        return index;    
    }
}
