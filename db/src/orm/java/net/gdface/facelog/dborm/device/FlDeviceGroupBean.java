// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: bean.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm.device;
import java.io.Serializable;
import net.gdface.facelog.dborm.Constant;
import net.gdface.facelog.dborm.BaseBean;
import net.gdface.facelog.dborm.CompareToBuilder;
import net.gdface.facelog.dborm.EqualsBuilder;
import net.gdface.facelog.dborm.HashCodeBuilder;
/**
 * FlDeviceGroupBean is a mapping of fl_device_group Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 设备组信息 </li>
 * </ul>
 * @author guyadong
*/
public  class FlDeviceGroupBean
    implements Serializable,BaseBean<FlDeviceGroupBean>,Comparable<FlDeviceGroupBean>,Constant,Cloneable
{
    private static final long serialVersionUID = -736132485567928706L;
    
    /** comments:设备组id */
    private Integer id;

    /** comments:设备组名 */
    private String name;

    /** comments:是否为叶子节点, 1:叶子节点 0:分支节点,null:两者都可 */
    private Integer leaf;

    /** comments:上一级设备组id */
    private Integer parent;

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
    public FlDeviceGroupBean(){
        super();
        reset();
    }
    /**
     * construct a new instance filled with primary keys
     * @param id PK# 1 
     */
    public FlDeviceGroupBean(Integer id){
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
    public void setId(Integer newVal)
    {
        if (equal(newVal, id) && checkIdInitialized()) {
            return;
        }
        id = newVal;

        modified |= FL_DEVICE_GROUP_ID_ID_MASK;
        initialized |= FL_DEVICE_GROUP_ID_ID_MASK;
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
    public void setName(String newVal)
    {
        if (equal(newVal, name) && checkNameInitialized()) {
            return;
        }
        name = newVal;

        modified |= FL_DEVICE_GROUP_ID_NAME_MASK;
        initialized |= FL_DEVICE_GROUP_ID_NAME_MASK;
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
    public Integer getLeaf(){
        return leaf;
    }
    /**
     * Setter method for {@link #leaf}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value  to be assigned to leaf
     */
    public void setLeaf(Integer newVal)
    {
        if (equal(newVal, leaf) && checkLeafInitialized()) {
            return;
        }
        leaf = newVal;

        modified |= FL_DEVICE_GROUP_ID_LEAF_MASK;
        initialized |= FL_DEVICE_GROUP_ID_LEAF_MASK;
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
    public Integer getParent(){
        return parent;
    }
    /**
     * Setter method for {@link #parent}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value  to be assigned to parent
     */
    public void setParent(Integer newVal)
    {
        if (equal(newVal, parent) && checkParentInitialized()) {
            return;
        }
        parent = newVal;

        modified |= FL_DEVICE_GROUP_ID_PARENT_MASK;
        initialized |= FL_DEVICE_GROUP_ID_PARENT_MASK;
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
     * The referenced {@link FlDeviceGroupBean} by {@link #parent} . <br>
     * FOREIGN KEY (parent) REFERENCES fl_device_group(id)
     */
    private FlDeviceGroupBean referencedByParent;
    /** Getter method for {@link #referencedByParent}. */
    public FlDeviceGroupBean getReferencedByParent() {
        return this.referencedByParent;
    }
    /** Setter method for {@link #referencedByParent}. */
    public void setReferencedByParent(FlDeviceGroupBean reference) {
        this.referencedByParent = reference;
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
        case FL_DEVICE_GROUP_ID_ID:
            return checkIdModified();
        case FL_DEVICE_GROUP_ID_NAME:
            return checkNameModified();
        case FL_DEVICE_GROUP_ID_LEAF:
            return checkLeafModified();
        case FL_DEVICE_GROUP_ID_PARENT:
            return checkParentModified();
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
        case FL_DEVICE_GROUP_ID_ID:
            return checkIdInitialized();
        case FL_DEVICE_GROUP_ID_NAME:
            return checkNameInitialized();
        case FL_DEVICE_GROUP_ID_LEAF:
            return checkLeafInitialized();
        case FL_DEVICE_GROUP_ID_PARENT:
            return checkParentInitialized();
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
        this.id = null;
        this.name = null;
        this.leaf = null;
        this.parent = null;
        this._isNew = true;
        this.modified = 0L;
        this.initialized = 0L;
    }
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof FlDeviceGroupBean)) {
            return false;
        }

        FlDeviceGroupBean obj = (FlDeviceGroupBean) object;
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
            if(count++ >0)builder.append(",");
            builder.append("id=").append(getId());
        }
        if(checkNameInitialized()){
            if(count++ >0)builder.append(",");
            builder.append("name=").append(getName());
        }
        if(checkLeafInitialized()){
            if(count++ >0)builder.append(",");
            builder.append("leaf=").append(getLeaf());
        }
        if(checkParentInitialized()){
            if(count++ >0)builder.append(",");
            builder.append("parent=").append(getParent());
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(FlDeviceGroupBean object){
        return new CompareToBuilder()
            .append(getId(), object.getId())
            .append(getName(), object.getName())
            .append(getLeaf(), object.getLeaf())
            .append(getParent(), object.getParent())
            .toComparison();
    }
    @Override
    public FlDeviceGroupBean clone(){
        try {
            return (FlDeviceGroupBean) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
    * set all field to null
    *
    * @author guyadong
    */
    public FlDeviceGroupBean clean()
    {
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
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @return always {@code bean}
     */
    public FlDeviceGroupBean copy(FlDeviceGroupBean bean)
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
    public FlDeviceGroupBean copy(FlDeviceGroupBean bean, int... fieldList)
    {
        if (null == fieldList || 0 == fieldList.length)
            for (int i = 0; i < 4; ++i) {
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
    public FlDeviceGroupBean copy(FlDeviceGroupBean bean, String... fieldList)
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
        case FL_DEVICE_GROUP_ID_ID: 
            return (T)getId();        
        case FL_DEVICE_GROUP_ID_NAME: 
            return (T)getName();        
        case FL_DEVICE_GROUP_ID_LEAF: 
            return (T)getLeaf();        
        case FL_DEVICE_GROUP_ID_PARENT: 
            return (T)getParent();        
        }
        return null;
    }

    /**
     * set a value representation of the given column id
     */
    public <T> void setValue(int columnID,T value)
    {
        switch( columnID ) {
        case FL_DEVICE_GROUP_ID_ID:        
            setId((Integer)value);
        case FL_DEVICE_GROUP_ID_NAME:        
            setName((String)value);
        case FL_DEVICE_GROUP_ID_LEAF:        
            setLeaf((Integer)value);
        case FL_DEVICE_GROUP_ID_PARENT:        
            setParent((Integer)value);
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
        int index = FL_DEVICE_GROUP_FIELDS_LIST.indexOf(column);
        if( 0 > index ) 
            index = FL_DEVICE_GROUP_JAVA_FIELDS_LIST.indexOf(column);
        return index;    
    }
    public static final Builder builder(){
        return new Builder();
    }
    /** 
     * a builder for FlDeviceGroupBean,the template instance is thread local variable
     * a instance of Builder can be reused.
     */
    public static final class Builder{
        /** FlDeviceGroupBean instance used for template to create new FlDeviceGroupBean instance. */
        static final ThreadLocal<FlDeviceGroupBean> template = new ThreadLocal<FlDeviceGroupBean>(){
            @Override
            protected FlDeviceGroupBean initialValue() {
                return new FlDeviceGroupBean();
            }};
        private Builder() {}
        /** 
         * reset the bean as template 
         * @see FlDeviceGroupBean#reset()
         */
        public Builder reset(){
            template.get().reset();
            return this;
        }
        /** set a bean as template,must not be {@code null} */
        public Builder asTemplate(FlDeviceGroupBean bean){
            if(null == bean)
                throw new NullPointerException();
            template.set(bean);
            return this;
        }
        /** return a clone instance of {@link #template}*/
        public FlDeviceGroupBean build(){
            return template.get().clone();
        }
        /** 
         * fill the field : fl_device_group.id
         * @param id 设备组id
         * @see {@link FlDeviceGroupBean#getId()}
         * @see {@link FlDeviceGroupBean#setId(Integer)}
         */
        public Builder id(Integer id){
            template.get().setId(id);
            return this;
        }
        /** 
         * fill the field : fl_device_group.name
         * @param name 设备组名
         * @see {@link FlDeviceGroupBean#getName()}
         * @see {@link FlDeviceGroupBean#setName(String)}
         */
        public Builder name(String name){
            template.get().setName(name);
            return this;
        }
        /** 
         * fill the field : fl_device_group.leaf
         * @param leaf 是否为叶子节点, 1:叶子节点 0:分支节点,null:两者都可
         * @see {@link FlDeviceGroupBean#getLeaf()}
         * @see {@link FlDeviceGroupBean#setLeaf(Integer)}
         */
        public Builder leaf(Integer leaf){
            template.get().setLeaf(leaf);
            return this;
        }
        /** 
         * fill the field : fl_device_group.parent
         * @param parent 上一级设备组id
         * @see {@link FlDeviceGroupBean#getParent()}
         * @see {@link FlDeviceGroupBean#setParent(Integer)}
         */
        public Builder parent(Integer parent){
            template.get().setParent(parent);
            return this;
        }
    }
}
