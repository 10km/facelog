// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: bean.java.vm
// ______________________________________________________
package net.gdface.facelog.db;
import java.io.Serializable;
import java.util.List;
import com.facebook.swift.codec.ThriftStruct;
import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftField.Requiredness;
/**
 * DeviceGroupBean is a mapping of fl_device_group Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 设备组信息 </li>
 * </ul>
 * @author guyadong
*/
@ThriftStruct
public final class DeviceGroupBean
    implements Serializable,BaseBean<DeviceGroupBean>,Comparable<DeviceGroupBean>,Constant,Cloneable
{
    private static final long serialVersionUID = 4625524271694791446L;
    /** NULL {@link DeviceGroupBean} bean , IMMUTABLE instance */
    public static final DeviceGroupBean NULL = new DeviceGroupBean().asNULL().immutable(Boolean.TRUE);
    /** comments:设备组id */
    private Integer id;

    /** comments:设备组名 */
    private String name;

    /** comments:是否为叶子节点, 1:叶子节点 0:分支节点,null:两者都可 */
    private Integer leaf;

    /** comments:上一级设备组id */
    private Integer parent;

    /** flag whether {@code this} can be modified */
    private Boolean immutable;
    /** columns modified flag */
    private long modified;
    /** columns initialized flag */
    private long initialized;
    private boolean isNew;        
    /** 
     * set {@code this} as immutable object
     * @return {@code this} 
     */
    public synchronized DeviceGroupBean immutable(Boolean immutable) {
        if(this.immutable != immutable){
            checkMutable();
            this.immutable = immutable;
        }
        return this;
    }
    /**
     * @return {@code true} if {@code this} is a mutable object  
     */
    public boolean mutable(){
        return Boolean.TRUE != this.immutable;
    }
    /**
     * @return {@code this}
     * @throws IllegalStateException if {@code this} is a immutable object 
     */
    private DeviceGroupBean checkMutable(){
        if(Boolean.TRUE == this.immutable){
            throw new IllegalStateException("this is a immutable object");
        }
        return this;
    }
    @ThriftField(value=1,name="_new",requiredness=Requiredness.REQUIRED)
    @Override
    public boolean isNew()
    {
        return this.isNew;
    }


    @Override
    public void isNew(boolean isNew)
    {
        this.isNew = isNew;
    }
    /**
     * Specifies to the object if it has been set as new.
     *
     * @param isNew the boolean value to be assigned to the isNew field
     */
    @ThriftField()
    public void setNew(boolean isNew)
    {
        this.isNew = isNew;
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
    public static final boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }
    public static final <T extends Comparable<T>>boolean compare(T a, T b) {
        return a == b || (a != null && 0==a.compareTo(b));
    }
    public DeviceGroupBean(){
        super();
        reset();
    }
    /**
     * construct a new instance filled with primary keys
     * @param id PK# 1 
     */
    public DeviceGroupBean(Integer id){
        this();
        setId(id);
    }
    /**
     * Getter method for {@link #id}.<br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_device_group.id</li>
     * <li> imported key: fl_permit.device_group_id</li>
     * <li> imported key: fl_device.group_id</li>
     * <li> imported key: fl_device_group.parent</li>
     * <li>comments: 设备组id</li>
     * <li>AUTO_INCREMENT</li>
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
     * @param newVal the new value to be assigned to id
     */
    public void setId(Integer newVal)
    {
        checkMutable();
        if (equal(newVal, id) && checkIdInitialized()) {
            return;
        }
        id = newVal;

        modified |= FL_DEVICE_GROUP_ID_ID_MASK;
        initialized |= FL_DEVICE_GROUP_ID_ID_MASK;
    }
    /** 
     * setter for thrift:swift support<br>
     * without modification for {@link #modified and {@link #initialized}<br>
     * <b>NOTE:</b>DO NOT use the method in your code
     */
    @ThriftField(name = "id")
    public void writeId(Integer newVal){
        checkMutable();
        id = newVal;
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
        return 0L !=  (modified & FL_DEVICE_GROUP_ID_ID_MASK);
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
        return 0L !=  (initialized & FL_DEVICE_GROUP_ID_ID_MASK);
    }
    /**
     * Getter method for {@link #name}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_device_group.name</li>
     * <li>comments: 设备组名</li>
     * <li>NOT NULL</li>
     * <li>column size: 32</li>
     * <li>JDBC type returned by the driver: Types.VARCHAR</li>
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
     * @param newVal the new value( NOT NULL) to be assigned to name
     */
    public void setName(String newVal)
    {
        checkMutable();
        if (equal(newVal, name) && checkNameInitialized()) {
            return;
        }
        name = newVal;

        modified |= FL_DEVICE_GROUP_ID_NAME_MASK;
        initialized |= FL_DEVICE_GROUP_ID_NAME_MASK;
    }
    /** 
     * setter for thrift:swift support<br>
     * without modification for {@link #modified and {@link #initialized}<br>
     * <b>NOTE:</b>DO NOT use the method in your code
     */
    @ThriftField(name = "name")
    public void writeName(String newVal){
        checkMutable();
        name = newVal;
    }
    /**
     * Determines if the name has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkNameModified()
    {
        return 0L !=  (modified & FL_DEVICE_GROUP_ID_NAME_MASK);
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
        return 0L !=  (initialized & FL_DEVICE_GROUP_ID_NAME_MASK);
    }
    /**
     * Getter method for {@link #leaf}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_device_group.leaf</li>
     * <li>comments: 是否为叶子节点, 1:叶子节点 0:分支节点,null:两者都可</li>
     * <li>column size: 3</li>
     * <li>JDBC type returned by the driver: Types.TINYINT</li>
     * </ul>
     *
     * @return the value of leaf
     */
    @ThriftField(value=6)
    public Integer getLeaf(){
        return leaf;
    }
    /**
     * Setter method for {@link #leaf}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to leaf
     */
    public void setLeaf(Integer newVal)
    {
        checkMutable();
        if (equal(newVal, leaf) && checkLeafInitialized()) {
            return;
        }
        leaf = newVal;

        modified |= FL_DEVICE_GROUP_ID_LEAF_MASK;
        initialized |= FL_DEVICE_GROUP_ID_LEAF_MASK;
    }
    /** 
     * setter for thrift:swift support<br>
     * without modification for {@link #modified and {@link #initialized}<br>
     * <b>NOTE:</b>DO NOT use the method in your code
     */
    @ThriftField(name = "leaf")
    public void writeLeaf(Integer newVal){
        checkMutable();
        leaf = newVal;
    }
    /**
     * Setter method for {@link #leaf}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to leaf
     */
    public void setLeaf(int newVal)
    {
        setLeaf(new Integer(newVal));
    }
    /**
     * Determines if the leaf has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkLeafModified()
    {
        return 0L !=  (modified & FL_DEVICE_GROUP_ID_LEAF_MASK);
    }

    /**
     * Determines if the leaf has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkLeafInitialized()
    {
        return 0L !=  (initialized & FL_DEVICE_GROUP_ID_LEAF_MASK);
    }
    /**
     * Getter method for {@link #parent}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_device_group.parent</li>
     * <li> foreign key: fl_device_group.id</li>
     * <li>comments: 上一级设备组id</li>
     * <li>column size: 10</li>
     * <li>JDBC type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of parent
     */
    @ThriftField(value=7)
    public Integer getParent(){
        return parent;
    }
    /**
     * Setter method for {@link #parent}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to parent
     */
    public void setParent(Integer newVal)
    {
        checkMutable();
        if (equal(newVal, parent) && checkParentInitialized()) {
            return;
        }
        parent = newVal;

        modified |= FL_DEVICE_GROUP_ID_PARENT_MASK;
        initialized |= FL_DEVICE_GROUP_ID_PARENT_MASK;
    }
    /** 
     * setter for thrift:swift support<br>
     * without modification for {@link #modified and {@link #initialized}<br>
     * <b>NOTE:</b>DO NOT use the method in your code
     */
    @ThriftField(name = "parent")
    public void writeParent(Integer newVal){
        checkMutable();
        parent = newVal;
    }
    /**
     * Setter method for {@link #parent}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to parent
     */
    public void setParent(int newVal)
    {
        setParent(new Integer(newVal));
    }
    /**
     * Determines if the parent has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkParentModified()
    {
        return 0L !=  (modified & FL_DEVICE_GROUP_ID_PARENT_MASK);
    }

    /**
     * Determines if the parent has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkParentInitialized()
    {
        return 0L !=  (initialized & FL_DEVICE_GROUP_ID_PARENT_MASK);
    }
    //////////////////////////////////////
    // referenced bean for FOREIGN KEYS
    //////////////////////////////////////
    /** 
     * The referenced {@link DeviceGroupBean} by {@link #parent} . <br>
     * FOREIGN KEY (parent) REFERENCES fl_device_group(id)
     */
    private DeviceGroupBean referencedByParent;
    /** Getter method for {@link #referencedByParent}. */
    public DeviceGroupBean getReferencedByParent() {
        return this.referencedByParent;
    }
    /** Setter method for {@link #referencedByParent}. */
    public void setReferencedByParent(DeviceGroupBean reference) {
        this.referencedByParent = reference;
    }

    @Override
    public boolean isModified()
    {
        return 0 != modified;
    }
  
    @Override
    public boolean isModified(int columnID){
        switch ( columnID ){
        case FL_DEVICE_GROUP_ID_ID:
            return checkIdModified();
        case FL_DEVICE_GROUP_ID_NAME:
            return checkNameModified();
        case FL_DEVICE_GROUP_ID_LEAF:
            return checkLeafModified();
        case FL_DEVICE_GROUP_ID_PARENT:
            return checkParentModified();
        default:
            return false;
        }        
    }

    @Override
    public boolean isInitialized(int columnID){
        switch(columnID) {
        case FL_DEVICE_GROUP_ID_ID:
            return checkIdInitialized();
        case FL_DEVICE_GROUP_ID_NAME:
            return checkNameInitialized();
        case FL_DEVICE_GROUP_ID_LEAF:
            return checkLeafInitialized();
        case FL_DEVICE_GROUP_ID_PARENT:
            return checkParentInitialized();
        default:
            return false;
        }
    }
    
    @Override
    public boolean isModified(String column){        
        return isModified(columnIDOf(column));
    }

    @Override
    public boolean isInitialized(String column){
        return isInitialized(columnIDOf(column));
    }
    
    @Override
    public void resetIsModified()
    {
        checkMutable();
        modified = 0L;
    }

    @Override
    public void resetPrimaryKeysModified()
    {
        modified &= (~(FL_DEVICE_GROUP_ID_ID_MASK));
    }
    /**
     * Resets columns modification status except primary keys to 'not modified'.
     */
    public void resetModifiedExceptPrimaryKeys()
    {
        modified &= (~(FL_DEVICE_GROUP_ID_NAME_MASK |
            FL_DEVICE_GROUP_ID_LEAF_MASK |
            FL_DEVICE_GROUP_ID_PARENT_MASK));
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
        this.id = null;
        this.name = null;
        this.leaf = null;
        this.parent = null;
        this.isNew = true;
        this.modified = 0L;
        this.initialized = 0L;
    }
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof DeviceGroupBean)) {
            return false;
        }

        DeviceGroupBean obj = (DeviceGroupBean) object;
        return new EqualsBuilder()
            .append(getId(), obj.getId())
            .append(getName(), obj.getName())
            .append(getLeaf(), obj.getLeaf())
            .append(getParent(), obj.getParent())
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
        // only output initialized field
        StringBuilder builder = new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[");
        int count = 0;        
        if(checkIdInitialized()){
            if(count++ >0){
                builder.append(",");
            }
            builder.append("id=").append(getId());
        }
        if(checkNameInitialized()){
            if(count++ >0){
                builder.append(",");
            }
            builder.append("name=").append(getName());
        }
        if(checkLeafInitialized()){
            if(count++ >0){
                builder.append(",");
            }
            builder.append("leaf=").append(getLeaf());
        }
        if(checkParentInitialized()){
            if(count++ >0){
                builder.append(",");
            }
            builder.append("parent=").append(getParent());
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(DeviceGroupBean object){
        return new CompareToBuilder()
            .append(getId(), object.getId())
            .append(getName(), object.getName())
            .append(getLeaf(), object.getLeaf())
            .append(getParent(), object.getParent())
            .toComparison();
    }
    @Override
    public DeviceGroupBean clone(){
        try {
            return (DeviceGroupBean) super.clone();
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
    public DeviceGroupBean asNULL()
    {   
        checkMutable();
        
        setId(null);
        setName(null);
        setLeaf(null);
        setParent(null);
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
    public static final List<DeviceGroupBean> replaceNull(List<DeviceGroupBean> source){
        if(null != source){
            for(int i = 0,endIndex = source.size();i<endIndex;++i){
                if(null == source.get(i)){
                    source.set(i, NULL);
                }
            }
        }
        return source;
    }
    /** 
     * @return replace null instance element with {@code null}
     * @see {@link #checkNULL()} 
     */
    public static final List<DeviceGroupBean> replaceNullInstance(List<DeviceGroupBean> source){
        if(null != source){
            for(int i = 0,endIndex = source.size();i<endIndex;++i){
                if(source.get(i).checkNULL()){
                    source.set(i, null);
                }
            }
        }
        return source;
    }
    @Override
    public DeviceGroupBean copy(DeviceGroupBean bean)
    {
        return copy(bean,new int[]{});
    }
    @Override
    public DeviceGroupBean copy(DeviceGroupBean bean, int... fieldList)
    {
        if (null == fieldList || 0 == fieldList.length){
            for (int i = 0; i < 4; ++i) {
                if( bean.isInitialized(i)){
                    setValue(i, bean.getValue(i));
                }
            }
        }
        else{
            for (int i = 0; i < fieldList.length; ++i) {
                if( bean.isInitialized(fieldList[i])){
                    setValue(fieldList[i], bean.getValue(fieldList[i]));
                }
            }
        }
        return this;
    }
        
    @Override
    public DeviceGroupBean copy(DeviceGroupBean bean, String... fieldList)
    {
        if (null == fieldList || 0 == fieldList.length){
            copy(bean,(int[])null);
        }else{
            int field;
            for (int i = 0; i < fieldList.length; i++) {
                field = columnIDOf(fieldList[i].trim());
                if(bean.isInitialized(field)){
                    setValue(field, bean.getValue(field));
                }
            }
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T>T getValue(int columnID)
    {
        switch( columnID ){
        case FL_DEVICE_GROUP_ID_ID: 
            return (T)getId();        
        case FL_DEVICE_GROUP_ID_NAME: 
            return (T)getName();        
        case FL_DEVICE_GROUP_ID_LEAF: 
            return (T)getLeaf();        
        case FL_DEVICE_GROUP_ID_PARENT: 
            return (T)getParent();        
        default:
            return null;
        }
    }

    @Override
    public <T> void setValue(int columnID,T value)
    {
        switch( columnID ) {
        case FL_DEVICE_GROUP_ID_ID:
            setId((Integer)value);
            break;
        case FL_DEVICE_GROUP_ID_NAME:
            setName((String)value);
            break;
        case FL_DEVICE_GROUP_ID_LEAF:
            setLeaf((Integer)value);
            break;
        case FL_DEVICE_GROUP_ID_PARENT:
            setParent((Integer)value);
            break;
        default:
            break;
        }
    }
    
    @Override
    public <T> T getValue(String column)
    {
        return getValue(columnIDOf(column));
    }

    @Override
    public <T> void setValue(String column,T value)
    {
        setValue(columnIDOf(column),value);
    }
    
    /** return column id for the given field name or negative if {@code column} is invalid name */
    public static int columnIDOf(String column){
        int index = FL_DEVICE_GROUP_FIELDS_LIST.indexOf(column);
        return  index < 0 
            ? FL_DEVICE_GROUP_JAVA_FIELDS_LIST.indexOf(column)
            : index;
    }
    
    public static final Builder builder(){
        return new Builder();
    }
    /** 
     * a builder for DeviceGroupBean,the template instance is thread local variable
     * a instance of Builder can be reused.
     */
    public static final class Builder{
        /** DeviceGroupBean instance used for template to create new DeviceGroupBean instance. */
        static final ThreadLocal<DeviceGroupBean> TEMPLATE = new ThreadLocal<DeviceGroupBean>(){
            @Override
            protected DeviceGroupBean initialValue() {
                return new DeviceGroupBean();
            }};
        private Builder() {}
        /** 
         * reset the bean as template 
         * @see DeviceGroupBean#reset()
         */
        public Builder reset(){
            TEMPLATE.get().reset();
            return this;
        }
        /** 
         * set as a immutable object
         * @see DeviceGroupBean#immutable(Boolean)
         */
        public Builder immutable(){
            TEMPLATE.get().immutable(Boolean.TRUE);
            return this;
        }
        /** set a bean as template,must not be {@code null} */
        public Builder template(DeviceGroupBean bean){
            if(null == bean){
                throw new NullPointerException();
            }
            TEMPLATE.set(bean);
            return this;
        }
        /** return a clone instance of {@link #TEMPLATE}*/
        public DeviceGroupBean build(){
            return TEMPLATE.get().clone();
        }
        /** 
         * fill the field : fl_device_group.id
         * @param id 设备组id
         * @see {@link DeviceGroupBean#getId()}
         * @see {@link DeviceGroupBean#setId(Integer)}
         */
        public Builder id(Integer id){
            TEMPLATE.get().setId(id);
            return this;
        }
        /** 
         * fill the field : fl_device_group.name
         * @param name 设备组名
         * @see {@link DeviceGroupBean#getName()}
         * @see {@link DeviceGroupBean#setName(String)}
         */
        public Builder name(String name){
            TEMPLATE.get().setName(name);
            return this;
        }
        /** 
         * fill the field : fl_device_group.leaf
         * @param leaf 是否为叶子节点, 1:叶子节点 0:分支节点,null:两者都可
         * @see {@link DeviceGroupBean#getLeaf()}
         * @see {@link DeviceGroupBean#setLeaf(Integer)}
         */
        public Builder leaf(Integer leaf){
            TEMPLATE.get().setLeaf(leaf);
            return this;
        }
        /** 
         * fill the field : fl_device_group.parent
         * @param parent 上一级设备组id
         * @see {@link DeviceGroupBean#getParent()}
         * @see {@link DeviceGroupBean#setParent(Integer)}
         */
        public Builder parent(Integer parent){
            TEMPLATE.get().setParent(parent);
            return this;
        }
    }
}
