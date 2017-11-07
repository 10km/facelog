// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: bean.java.vm
// ______________________________________________________
package net.gdface.facelog.db;
import java.io.Serializable;
import com.facebook.swift.codec.ThriftStruct;
import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftField.Requiredness;
/**
 * LogLightBean is a mapping of fl_log_light Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: VIEW </li>
 * </ul>
 * @author guyadong
*/
@ThriftStruct
public final class LogLightBean
    implements Serializable,BaseBean<LogLightBean>,Comparable<LogLightBean>,Constant,Cloneable
{
    private static final long serialVersionUID = 4419196843551738129L;
    
    /** comments:日志id */
    private Integer id;

    /** comments:用户id */
    private Integer personId;

    /** comments:姓名 */
    private String name;

    /** comments:证件类型,0:未知,1:身份证,2:护照,3:台胞证,4:港澳通行证,5:军官证,6:外国人居留证,7:员工卡,8:其他 */
    private Integer papersType;

    /** comments:证件号码 */
    private String papersNum;

    /** comments:验证时间(可能由前端设备提供时间) */
    private java.util.Date verifyTime;

    /** columns modified flag */
    private long modified;
    /** columns initialized flag */
    private long initialized;
    private boolean _isNew;
    /**
     * Determines if the current object is new.
     *
     * @return true if the current object is new, false if the object is not new
     */
    @ThriftField(value=1,name="_new",requiredness=Requiredness.REQUIRED)
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
    @ThriftField()
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
    @ThriftField()
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
    @ThriftField()
    public void setInitialized(long initialized){
        this.initialized = initialized;
    }
    public LogLightBean(){
        super();
        reset();
    }
    /**
     * Getter method for {@link #id}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.id</li>
     * <li>comments: 日志id</li>
     * <li>default value: '0'</li>
     * <li>NOT NULL</li>
     * <li>column size: 10</li>
     * <li>JDBC type returned by the driver: Types.INTEGER</li>
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
    @ThriftField()
    public void setId(Integer newVal)
    {
        if ((newVal != null && id != null && (newVal.compareTo(id) == 0)) ||
            (newVal == null && id == null && checkIdInitialized())) {
            return;
        }
        id = newVal;

        modified |= FL_LOG_LIGHT_ID_ID_MASK;
        initialized |= FL_LOG_LIGHT_ID_ID_MASK;
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
        return 0L !=  (modified & FL_LOG_LIGHT_ID_ID_MASK);
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
        return 0L !=  (initialized & FL_LOG_LIGHT_ID_ID_MASK);
    }
    /**
     * Getter method for {@link #personId}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.person_id</li>
     * <li>comments: 用户id</li>
     * <li>default value: '0'</li>
     * <li>NOT NULL</li>
     * <li>column size: 10</li>
     * <li>JDBC type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of personId
     */
    @ThriftField(value=5)
    public Integer getPersonId(){
        return personId;
    }
    /**
     * Setter method for {@link #personId}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value (NOT NULL) to be assigned to personId
     */
    @ThriftField()
    public void setPersonId(Integer newVal)
    {
        if ((newVal != null && personId != null && (newVal.compareTo(personId) == 0)) ||
            (newVal == null && personId == null && checkPersonIdInitialized())) {
            return;
        }
        personId = newVal;

        modified |= FL_LOG_LIGHT_ID_PERSON_ID_MASK;
        initialized |= FL_LOG_LIGHT_ID_PERSON_ID_MASK;
    }

    /**
     * Setter method for {@link #personId}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to personId
     */
    public void setPersonId(int newVal)
    {
        setPersonId(new Integer(newVal));
    }
    /**
     * Determines if the personId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkPersonIdModified()
    {
        return 0L !=  (modified & FL_LOG_LIGHT_ID_PERSON_ID_MASK);
    }

    /**
     * Determines if the personId has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkPersonIdInitialized()
    {
        return 0L !=  (initialized & FL_LOG_LIGHT_ID_PERSON_ID_MASK);
    }
    /**
     * Getter method for {@link #name}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.name</li>
     * <li>comments: 姓名</li>
     * <li>NOT NULL</li>
     * <li>column size: 32</li>
     * <li>JDBC type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of name
     */
    @ThriftField(value=6)
    public String getName(){
        return name;
    }
    /**
     * Setter method for {@link #name}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value (NOT NULL) to be assigned to name
     */
    @ThriftField()
    public void setName(String newVal)
    {
        if ((newVal != null && name != null && (newVal.compareTo(name) == 0)) ||
            (newVal == null && name == null && checkNameInitialized())) {
            return;
        }
        name = newVal;

        modified |= FL_LOG_LIGHT_ID_NAME_MASK;
        initialized |= FL_LOG_LIGHT_ID_NAME_MASK;
    }

    /**
     * Determines if the name has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkNameModified()
    {
        return 0L !=  (modified & FL_LOG_LIGHT_ID_NAME_MASK);
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
        return 0L !=  (initialized & FL_LOG_LIGHT_ID_NAME_MASK);
    }
    /**
     * Getter method for {@link #papersType}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.papers_type</li>
     * <li>comments: 证件类型,0:未知,1:身份证,2:护照,3:台胞证,4:港澳通行证,5:军官证,6:外国人居留证,7:员工卡,8:其他</li>
     * <li>column size: 3</li>
     * <li>JDBC type returned by the driver: Types.TINYINT</li>
     * </ul>
     *
     * @return the value of papersType
     */
    @ThriftField(value=7)
    public Integer getPapersType(){
        return papersType;
    }
    /**
     * Setter method for {@link #papersType}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value  to be assigned to papersType
     */
    @ThriftField()
    public void setPapersType(Integer newVal)
    {
        if ((newVal != null && papersType != null && (newVal.compareTo(papersType) == 0)) ||
            (newVal == null && papersType == null && checkPapersTypeInitialized())) {
            return;
        }
        papersType = newVal;

        modified |= FL_LOG_LIGHT_ID_PAPERS_TYPE_MASK;
        initialized |= FL_LOG_LIGHT_ID_PAPERS_TYPE_MASK;
    }

    /**
     * Setter method for {@link #papersType}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to papersType
     */
    public void setPapersType(int newVal)
    {
        setPapersType(new Integer(newVal));
    }
    /**
     * Determines if the papersType has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkPapersTypeModified()
    {
        return 0L !=  (modified & FL_LOG_LIGHT_ID_PAPERS_TYPE_MASK);
    }

    /**
     * Determines if the papersType has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkPapersTypeInitialized()
    {
        return 0L !=  (initialized & FL_LOG_LIGHT_ID_PAPERS_TYPE_MASK);
    }
    /**
     * Getter method for {@link #papersNum}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.papers_num</li>
     * <li>comments: 证件号码</li>
     * <li>column size: 32</li>
     * <li>JDBC type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of papersNum
     */
    @ThriftField(value=8)
    public String getPapersNum(){
        return papersNum;
    }
    /**
     * Setter method for {@link #papersNum}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value  to be assigned to papersNum
     */
    @ThriftField()
    public void setPapersNum(String newVal)
    {
        if ((newVal != null && papersNum != null && (newVal.compareTo(papersNum) == 0)) ||
            (newVal == null && papersNum == null && checkPapersNumInitialized())) {
            return;
        }
        papersNum = newVal;

        modified |= FL_LOG_LIGHT_ID_PAPERS_NUM_MASK;
        initialized |= FL_LOG_LIGHT_ID_PAPERS_NUM_MASK;
    }

    /**
     * Determines if the papersNum has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkPapersNumModified()
    {
        return 0L !=  (modified & FL_LOG_LIGHT_ID_PAPERS_NUM_MASK);
    }

    /**
     * Determines if the papersNum has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkPapersNumInitialized()
    {
        return 0L !=  (initialized & FL_LOG_LIGHT_ID_PAPERS_NUM_MASK);
    }
    /**
     * Getter method for {@link #verifyTime}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.verify_time</li>
     * <li>comments: 验证时间(可能由前端设备提供时间)</li>
     * <li>default value: '0000-00-00 00:00:00'</li>
     * <li>NOT NULL</li>
     * <li>column size: 19</li>
     * <li>JDBC type returned by the driver: Types.TIMESTAMP</li>
     * </ul>
     *
     * @return the value of verifyTime
     */
    public java.util.Date getVerifyTime(){
        return verifyTime;
    }
    /** 
     * use Long to represent date type for thrift:swift support 
     * @see #getVerifyTime()
     */
    @ThriftField(name = "verifyTime",value = 9)
    public Long readVerifyTime(){
        return null == verifyTime ? null:verifyTime.getTime();
    }
    /**
     * Setter method for {@link #verifyTime}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value (NOT NULL) to be assigned to verifyTime
     */
    public void setVerifyTime(java.util.Date newVal)
    {
        if ((newVal != null && verifyTime != null && (newVal.compareTo(verifyTime) == 0)) ||
            (newVal == null && verifyTime == null && checkVerifyTimeInitialized())) {
            return;
        }
        verifyTime = newVal;

        modified |= FL_LOG_LIGHT_ID_VERIFY_TIME_MASK;
        initialized |= FL_LOG_LIGHT_ID_VERIFY_TIME_MASK;
    }

    /** 
     * use Long to represent date type for thrift:swift support
     * @see #setVerifyTime(java.util.Date)  
     */
    @ThriftField(name = "verifyTime",value = 9)
    public void writeVerifyTime(Long newVal){
        setVerifyTime(null == newVal?null:new java.util.Date(newVal));
    }
    /**
     * Setter method for {@link #verifyTime}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to verifyTime
     */
    public void setVerifyTime(long newVal)
    {
        setVerifyTime(new java.util.Date(newVal));
    }
    /**
     * Determines if the verifyTime has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkVerifyTimeModified()
    {
        return 0L !=  (modified & FL_LOG_LIGHT_ID_VERIFY_TIME_MASK);
    }

    /**
     * Determines if the verifyTime has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkVerifyTimeInitialized()
    {
        return 0L !=  (initialized & FL_LOG_LIGHT_ID_VERIFY_TIME_MASK);
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
        case FL_LOG_LIGHT_ID_ID:
            return checkIdModified();
        case FL_LOG_LIGHT_ID_PERSON_ID:
            return checkPersonIdModified();
        case FL_LOG_LIGHT_ID_NAME:
            return checkNameModified();
        case FL_LOG_LIGHT_ID_PAPERS_TYPE:
            return checkPapersTypeModified();
        case FL_LOG_LIGHT_ID_PAPERS_NUM:
            return checkPapersNumModified();
        case FL_LOG_LIGHT_ID_VERIFY_TIME:
            return checkVerifyTimeModified();
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
        case FL_LOG_LIGHT_ID_ID:
            return checkIdInitialized();
        case FL_LOG_LIGHT_ID_PERSON_ID:
            return checkPersonIdInitialized();
        case FL_LOG_LIGHT_ID_NAME:
            return checkNameInitialized();
        case FL_LOG_LIGHT_ID_PAPERS_TYPE:
            return checkPapersTypeInitialized();
        case FL_LOG_LIGHT_ID_PAPERS_NUM:
            return checkPapersNumInitialized();
        case FL_LOG_LIGHT_ID_VERIFY_TIME:
            return checkVerifyTimeInitialized();
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
     * Resets the primary keys (  ) modification status to 'not modified'.
     */
    public void resetPrimaryKeysModified()
    {
        // columns is null or empty;
    }
    /**
     * Resets columns modification status except primary keys to 'not modified'.
     */
    public void resetModifiedExceptPrimaryKeys()
    {
        modified &= (~(FL_LOG_LIGHT_ID_ID_MASK |
            FL_LOG_LIGHT_ID_PERSON_ID_MASK |
            FL_LOG_LIGHT_ID_NAME_MASK |
            FL_LOG_LIGHT_ID_PAPERS_TYPE_MASK |
            FL_LOG_LIGHT_ID_PAPERS_NUM_MASK |
            FL_LOG_LIGHT_ID_VERIFY_TIME_MASK));
    }
    /**
     * Resets the object initialization status to 'not initialized'.
     */
    private void resetInitialized()
    {
        initialized = 0L;
    }
    /** reset all fields to initial value, equal to a new bean */
    public void reset(){
        this.id = new Integer(0)/* DEFAULT:'0'*/;
        this.personId = new Integer(0)/* DEFAULT:'0'*/;
        this.name = null;
        this.papersType = null;
        this.papersNum = null;
        this.verifyTime = null/* DEFAULT:'0000-00-00 00:00:00'*/;
        this._isNew = true;
        this.modified = 0L;
        this.initialized = (FL_LOG_LIGHT_ID_ID_MASK | FL_LOG_LIGHT_ID_PERSON_ID_MASK);
    }
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof LogLightBean)) {
            return false;
        }

        LogLightBean obj = (LogLightBean) object;
        return new EqualsBuilder()
            .append(getId(), obj.getId())
            .append(getPersonId(), obj.getPersonId())
            .append(getName(), obj.getName())
            .append(getPapersType(), obj.getPapersType())
            .append(getPapersNum(), obj.getPapersNum())
            .append(getVerifyTime(), obj.getVerifyTime())
            .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
            .append(getId())
            .append(getPersonId())
            .append(getName())
            .append(getPapersType())
            .append(getPapersNum())
            .append(getVerifyTime())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[\n")
            .append("\tid=").append(getId()).append("\n")
            .append("\tperson_id=").append(getPersonId()).append("\n")
            .append("\tname=").append(getName()).append("\n")
            .append("\tpapers_type=").append(getPapersType()).append("\n")
            .append("\tpapers_num=").append(getPapersNum()).append("\n")
            .append("\tverify_time=").append(getVerifyTime()).append("\n")
            .append("]\n")
            .toString();
    }

    @Override
    public int compareTo(LogLightBean object){
        return new CompareToBuilder()
            .append(getId(), object.getId())
            .append(getPersonId(), object.getPersonId())
            .append(getName(), object.getName())
            .append(getPapersType(), object.getPapersType())
            .append(getPapersNum(), object.getPapersNum())
            .append(getVerifyTime(), object.getVerifyTime())
            .toComparison();
    }
    @Override
    public LogLightBean clone(){
        try {
            return (LogLightBean) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
    * set all field to null
    *
    * @author guyadong
    */
    public LogLightBean clean()
    {
        setId(null);
        setPersonId(null);
        setName(null);
        setPapersType(null);
        setPapersNum(null);
        setVerifyTime(null);
        isNew(true);
        resetInitialized();
        resetIsModified();
        return this;
    }
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @return always {@code bean}
     */
    public LogLightBean copy(LogLightBean bean)
    {
        return copy(bean,new int[]{});
    }
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @param fieldList the column id list to copy into the current bean
     * @return always {@code bean}
     */
    public LogLightBean copy(LogLightBean bean, int... fieldList)
    {
        if (null == fieldList || 0 == fieldList.length)
            for (int i = 0; i < 6; ++i) {
                if( bean.isInitialized(i))
                    setValue(i, bean.getValue(i));
            }
        else
            for (int i = 0; i < fieldList.length; ++i) {
                if( bean.isInitialized(fieldList[i]))
                    setValue(fieldList[i], bean.getValue(fieldList[i]));
            }
        return this;
    }
        
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @param fieldList the column name list to copy into the current bean
     * @return always {@code bean}
     */
    public LogLightBean copy(LogLightBean bean, String... fieldList)
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
        return this;
    }

    /**
     * return a object representation of the given column id
     */
    @SuppressWarnings("unchecked")
    public <T>T getValue(int columnID)
    {
        switch( columnID ){
        case FL_LOG_LIGHT_ID_ID: 
            return (T)getId();        
        case FL_LOG_LIGHT_ID_PERSON_ID: 
            return (T)getPersonId();        
        case FL_LOG_LIGHT_ID_NAME: 
            return (T)getName();        
        case FL_LOG_LIGHT_ID_PAPERS_TYPE: 
            return (T)getPapersType();        
        case FL_LOG_LIGHT_ID_PAPERS_NUM: 
            return (T)getPapersNum();        
        case FL_LOG_LIGHT_ID_VERIFY_TIME: 
            return (T)getVerifyTime();        
        }
        return null;
    }

    /**
     * set a value representation of the given column id
     */
    public <T> void setValue(int columnID,T value)
    {
        switch( columnID ) {
        case FL_LOG_LIGHT_ID_ID:        
            setId((Integer)value);
        case FL_LOG_LIGHT_ID_PERSON_ID:        
            setPersonId((Integer)value);
        case FL_LOG_LIGHT_ID_NAME:        
            setName((String)value);
        case FL_LOG_LIGHT_ID_PAPERS_TYPE:        
            setPapersType((Integer)value);
        case FL_LOG_LIGHT_ID_PAPERS_NUM:        
            setPapersNum((String)value);
        case FL_LOG_LIGHT_ID_VERIFY_TIME:        
            setVerifyTime((java.util.Date)value);
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
        int index = FL_LOG_LIGHT_FIELDS_LIST.indexOf(column);
        if( 0 > index ) 
            index = FL_LOG_LIGHT_JAVA_FIELDS_LIST.indexOf(column);
        return index;    
    }
}
