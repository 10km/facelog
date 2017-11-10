// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: bean.java.vm
// ______________________________________________________
package net.gdface.facelog.client;
import java.io.Serializable;
import java.util.List;
/**
 * PermitBean is a mapping of fl_permit Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 通行权限关联表 </li>
 * </ul>
 * @author guyadong
*/
public  class PermitBean
    implements Serializable,BaseBean<PermitBean>,Comparable<PermitBean>,Constant,Cloneable
{
    private static final long serialVersionUID = 5281908626988960067L;
    /** NULL {@link PermitBean} bean , IMMUTABLE instance */
    public static final PermitBean NULL = new PermitBean().asNULL().immutable(Boolean.TRUE);
    /** comments:外键,设备组id */
    private Integer deviceGroupId;

    /** comments:外键,人员组id */
    private Integer personGroupId;

    private java.util.Date createTime;

    /** flag whether {@code this} can be modified */
    private Boolean _immutable;
    /** columns modified flag */
    private long modified;
    /** columns initialized flag */
    private long initialized;
    private boolean _isNew;        
    /** 
     * set {@code this} as immutable object
     * @return {@code this} 
     */
    public synchronized PermitBean immutable(Boolean immutable) {
        if(this._immutable != immutable){
            checkMutable();
            this._immutable = immutable;
        }
        return this;
    }
    /**
     * @return {@code true} if {@code this} is a mutable object  
     */
    public boolean mutable(){
        return Boolean.TRUE != this._immutable;
    }
    /**
     * @return {@code this}
     * @throws IllegalStateException if {@code this} is a immutable object 
     */
    private PermitBean checkMutable(){
        if(Boolean.TRUE == this._immutable)
            throw new IllegalStateException("this is a immutable object");
        return this;
    }
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
    public static final boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }
    public static final <T extends Comparable<T>>boolean compare(T a, T b) {
        return a == b || (a != null && 0==a.compareTo(b));
    }
    public PermitBean(){
        super();
        reset();
    }
    /**
     * construct a new instance filled with primary keys
     * @param deviceGroupId PK# 1 
     @param personGroupId PK# 2 
     */
    public PermitBean(Integer deviceGroupId,Integer personGroupId){
        this();
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
     * @param newVal the new value( NOT NULL) to be assigned to deviceGroupId
     */
    public void setDeviceGroupId(Integer newVal)
    {
        checkMutable();
        if (equal(newVal, deviceGroupId) && checkDeviceGroupIdInitialized()) {
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
     * @param newVal the new value( NOT NULL) to be assigned to personGroupId
     */
    public void setPersonGroupId(Integer newVal)
    {
        checkMutable();
        if (equal(newVal, personGroupId) && checkPersonGroupIdInitialized()) {
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
     * @param newVal the new value( NOT NULL) to be assigned to createTime
     */
    public void setCreateTime(java.util.Date newVal)
    {
        checkMutable();
        if (equal(newVal, createTime) && checkCreateTimeInitialized()) {
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
     * The referenced {@link DeviceGroupBean} by {@link #deviceGroupId} . <br>
     * FOREIGN KEY (device_group_id) REFERENCES fl_device_group(id)
     */
    private DeviceGroupBean referencedByDeviceGroupId;
    /** Getter method for {@link #referencedByDeviceGroupId}. */
    public DeviceGroupBean getReferencedByDeviceGroupId() {
        return this.referencedByDeviceGroupId;
    }
    /** Setter method for {@link #referencedByDeviceGroupId}. */
    public void setReferencedByDeviceGroupId(DeviceGroupBean reference) {
        this.referencedByDeviceGroupId = reference;
    }
    /** 
     * The referenced {@link PersonGroupBean} by {@link #personGroupId} . <br>
     * FOREIGN KEY (person_group_id) REFERENCES fl_person_group(id)
     */
    private PersonGroupBean referencedByPersonGroupId;
    /** Getter method for {@link #referencedByPersonGroupId}. */
    public PersonGroupBean getReferencedByPersonGroupId() {
        return this.referencedByPersonGroupId;
    }
    /** Setter method for {@link #referencedByPersonGroupId}. */
    public void setReferencedByPersonGroupId(PersonGroupBean reference) {
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
        checkMutable();
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
    /** reset all fields to initial value, equal to a new bean */
    public void reset(){
        checkMutable();
        this.deviceGroupId = null;
        this.personGroupId = null;
        this.createTime = null/* DEFAULT:'CURRENT_TIMESTAMP'*/;
        this._isNew = true;
        this.modified = 0L;
        this.initialized = 0L;
    }
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof PermitBean)) {
            return false;
        }

        PermitBean obj = (PermitBean) object;
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
        // only output initialized field
        StringBuilder builder = new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[");
        int count = 0;        
        if(checkDeviceGroupIdInitialized()){
            if(count++ >0)builder.append(",");
            builder.append("device_group_id=").append(getDeviceGroupId());
        }
        if(checkPersonGroupIdInitialized()){
            if(count++ >0)builder.append(",");
            builder.append("person_group_id=").append(getPersonGroupId());
        }
        if(checkCreateTimeInitialized()){
            if(count++ >0)builder.append(",");
            builder.append("create_time=").append(getCreateTime());
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(PermitBean object){
        return new CompareToBuilder()
            .append(getDeviceGroupId(), object.getDeviceGroupId())
            .append(getPersonGroupId(), object.getPersonGroupId())
            .append(getCreateTime(), object.getCreateTime())
            .toComparison();
    }
    @Override
    public PermitBean clone(){
        try {
            return (PermitBean) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Make {@code this} to a NULL bean<br>
     * set all fields to null, {@link #modified} and {@link #initialized} be set to 0
     * @return {@code this} bean
     * @author guyadong
     */
    public PermitBean asNULL()
    {   
        checkMutable();
        
        setDeviceGroupId(null);
        setPersonGroupId(null);
        setCreateTime(null);
        isNew(true);
        resetInitialized();
        resetIsModified();
        return this;
    }
    /**
     * check whether this bean is a NULL bean 
     * @return {@code true} if {@link {@link #initialized} be set to zero
     * @see #asNULL()
     */
    public boolean checkNULL(){
        return 0L == getInitialized();
    }
    /** 
     * @return {@code source} replace {@code null} element with null instance({@link #NULL})
     */
    public static final List<PermitBean> replaceNull(List<PermitBean> source){
        if(null != source){
            for(int i = 0,end_i = source.size();i<end_i;++i){
                if(null == source.get(i))source.set(i, NULL);
            }
        }
        return source;
    }
    /** 
     * @return replace null instance element with {@code null}
     * @see {@link #checkNULL()} 
     */
    public static final List<PermitBean> replaceNullInstance(List<PermitBean> source){
        if(null != source){
            for(int i = 0,end_i = source.size();i<end_i;++i){
                if(source.get(i).checkNULL())source.set(i, null);
            }
        }
        return source;
    }
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @return always {@code bean}
     */
    public PermitBean copy(PermitBean bean)
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
    public PermitBean copy(PermitBean bean, int... fieldList)
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
        return this;
    }
        
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @param fieldList the column name list to copy into the current bean
     * @return always {@code bean}
     */
    public PermitBean copy(PermitBean bean, String... fieldList)
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
    public static final Builder builder(){
        return new Builder();
    }
    /** 
     * a builder for PermitBean,the template instance is thread local variable
     * a instance of Builder can be reused.
     */
    public static final class Builder{
        /** PermitBean instance used for template to create new PermitBean instance. */
        static final ThreadLocal<PermitBean> template = new ThreadLocal<PermitBean>(){
            @Override
            protected PermitBean initialValue() {
                return new PermitBean();
            }};
        private Builder() {}
        /** 
         * reset the bean as template 
         * @see PermitBean#reset()
         */
        public Builder reset(){
            template.get().reset();
            return this;
        }
        /** 
         * set as a immutable object
         * @see PermitBean#immutable(Boolean)
         */
        public Builder immutable(){
            template.get().immutable(Boolean.TRUE);
            return this;
        }
        /** set a bean as template,must not be {@code null} */
        public Builder template(PermitBean bean){
            if(null == bean)
                throw new NullPointerException();
            template.set(bean);
            return this;
        }
        /** return a clone instance of {@link #template}*/
        public PermitBean build(){
            return template.get().clone();
        }
        /** 
         * fill the field : fl_permit.device_group_id
         * @param deviceGroupId 外键,设备组id
         * @see {@link PermitBean#getDeviceGroupId()}
         * @see {@link PermitBean#setDeviceGroupId(Integer)}
         */
        public Builder deviceGroupId(Integer deviceGroupId){
            template.get().setDeviceGroupId(deviceGroupId);
            return this;
        }
        /** 
         * fill the field : fl_permit.person_group_id
         * @param personGroupId 外键,人员组id
         * @see {@link PermitBean#getPersonGroupId()}
         * @see {@link PermitBean#setPersonGroupId(Integer)}
         */
        public Builder personGroupId(Integer personGroupId){
            template.get().setPersonGroupId(personGroupId);
            return this;
        }
        /** 
         * fill the field : fl_permit.create_time
         * @param createTime 
         * @see {@link PermitBean#getCreateTime()}
         * @see {@link PermitBean#setCreateTime(java.util.Date)}
         */
        public Builder createTime(java.util.Date createTime){
            template.get().setCreateTime(createTime);
            return this;
        }
    }
    /////// FOR THRIFT //////
    /** 
     * cast {@code this} to {@link net.gdface.facelog.client.thrift.PermitBean}
     * @see {@link ThriftConverter#converterPermitBean}
     */
    public net.gdface.facelog.client.thrift.PermitBean toThrift(){
        return ThriftConverter.converterPermitBean.toRight(this);
    }
    /** 
     * copy all fields from {@link net.gdface.facelog.client.thrift.PermitBean},do nothing if {@code thriftBean} is null
     * @return current object {@code this}
     * @see {@link ThriftConverter#converterPermitBean}
     */
    public PermitBean fromThrift(net.gdface.facelog.client.thrift.PermitBean thriftBean){
        if(null != thriftBean){
            reset();
            return ThriftConverter.converterPermitBean.fromRight(this,thriftBean);
        }
        return this;
    }
    /** 
     * construct new instance from {@link net.gdface.facelog.client.thrift.PermitBean}
     * @param thriftBean must not be null
     * @see {@link ThriftConverter#converterPermitBean}
     */
    public PermitBean(net.gdface.facelog.client.thrift.PermitBean thriftBean){
        if(null != thriftBean)
            throw new NullPointerException();
        reset();
        ThriftConverter.converterPermitBean.fromRight(this,thriftBean);
    }
}
