// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.dborm.face;
import java.io.Serializable;
import net.gdface.facelog.dborm.Constant;
import net.gdface.facelog.dborm.BaseBean;
import net.gdface.facelog.dborm.person.FlPersonBean;
import net.gdface.facelog.dborm.CompareToBuilder;
import net.gdface.facelog.dborm.EqualsBuilder;
import net.gdface.facelog.dborm.HashCodeBuilder;
/**
 * FlFeatureBean is a mapping of fl_feature Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 用于验证身份的人脸特征数据表 </li>
 * </ul>
 * @author guyadong
*/
public class FlFeatureBean
    implements Serializable,BaseBean<FlFeatureBean>,Comparable<FlFeatureBean>,Constant,Cloneable
{
    private static final long serialVersionUID = 9009645819979798812L;
    
    /** comments:主键,特征码md5校验码 */
    private String md5;

    /** comments:外键,所属用户id */
    private Integer personId;

    /** comments:二进制特征数据 */
    private java.nio.ByteBuffer feature;

    private java.util.Date createTime;

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
    public FlFeatureBean(){
        super();
    }
    /**
     * Getter method for {@link #md5}.<br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_feature.md5</li>
     * <li> imported key: fl_log.verify_feature</li>
     * <li> imported key: fl_face.feature_md5</li>
     * <li>comments: 主键,特征码md5校验码</li>
     * <li>NOT NULL</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.CHAR</li>
     * </ul>
     *
     * @return the value of md5
     */
    public String getMd5(){
        return md5;
    }
    /**
     * Setter method for {@link #md5}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value (NOT NULL) to be assigned to md5
     */
    public void setMd5(String newVal)
    {
        if ((newVal != null && md5 != null && (newVal.compareTo(md5) == 0)) ||
            (newVal == null && md5 == null && checkMd5Initialized())) {
            return;
        }
        md5 = newVal;

        modified |= FL_FEATURE_ID_MD5_MASK;
        initialized |= FL_FEATURE_ID_MD5_MASK;
    }

    /**
     * Determines if the md5 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkMd5Modified()
    {
        return 0L !=  (modified & FL_FEATURE_ID_MD5_MASK);
    }

    /**
     * Determines if the md5 has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkMd5Initialized()
    {
        return 0L !=  (initialized & FL_FEATURE_ID_MD5_MASK);
    }
    /**
     * Getter method for {@link #personId}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_feature.person_id</li>
     * <li> foreign key: fl_person.id</li>
     * <li>comments: 外键,所属用户id</li>
     * <li>NOT NULL</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of personId
     */
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
    public void setPersonId(Integer newVal)
    {
        if ((newVal != null && personId != null && (newVal.compareTo(personId) == 0)) ||
            (newVal == null && personId == null && checkPersonIdInitialized())) {
            return;
        }
        personId = newVal;

        modified |= FL_FEATURE_ID_PERSON_ID_MASK;
        initialized |= FL_FEATURE_ID_PERSON_ID_MASK;
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
        return 0L !=  (modified & FL_FEATURE_ID_PERSON_ID_MASK);
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
        return 0L !=  (initialized & FL_FEATURE_ID_PERSON_ID_MASK);
    }
    /**
     * Getter method for {@link #feature}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_feature.feature</li>
     * <li>comments: 二进制特征数据</li>
     * <li>NOT NULL</li>
     * <li>column size: 65535</li>
     * <li>jdbc type returned by the driver: Types.LONGVARBINARY</li>
     * </ul>
     *
     * @return the value of feature
     */
    public java.nio.ByteBuffer getFeature(){
        return feature;
    }
    /**
     * Setter method for {@link #feature}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value (NOT NULL) to be assigned to feature
     */
    public void setFeature(java.nio.ByteBuffer newVal)
    {
        if ((newVal != null && feature != null && (newVal.compareTo(feature) == 0)) ||
            (newVal == null && feature == null && checkFeatureInitialized())) {
            return;
        }
        feature = newVal;

        modified |= FL_FEATURE_ID_FEATURE_MASK;
        initialized |= FL_FEATURE_ID_FEATURE_MASK;
    }

    /**
     * Determines if the feature has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkFeatureModified()
    {
        return 0L !=  (modified & FL_FEATURE_ID_FEATURE_MASK);
    }

    /**
     * Determines if the feature has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkFeatureInitialized()
    {
        return 0L !=  (initialized & FL_FEATURE_ID_FEATURE_MASK);
    }
    /**
     * Getter method for {@link #createTime}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_feature.create_time</li>
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

        modified |= FL_FEATURE_ID_CREATE_TIME_MASK;
        initialized |= FL_FEATURE_ID_CREATE_TIME_MASK;
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
        return 0L !=  (modified & FL_FEATURE_ID_CREATE_TIME_MASK);
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
        return 0L !=  (initialized & FL_FEATURE_ID_CREATE_TIME_MASK);
    }
    //////////////////////////////////////
    // referenced bean for FOREIGN KEYS
    //////////////////////////////////////
    /** 
     * The referenced {@link FlPersonBean} by {@link #personId} . <br>
     * FOREIGN KEY (person_id) REFERENCES fl_person(id)
     */
    private FlPersonBean referencedByPersonId;
    /** Getter method for {@link #referencedByPersonId}. */
    public FlPersonBean getReferencedByPersonId() {
        return this.referencedByPersonId;
    }
    /** Setter method for {@link #referencedByPersonId}. */
    public void setReferencedByPersonId(FlPersonBean reference) {
        this.referencedByPersonId = reference;
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
        case FL_FEATURE_ID_MD5:
            return checkMd5Modified();
        case FL_FEATURE_ID_PERSON_ID:
            return checkPersonIdModified();
        case FL_FEATURE_ID_FEATURE:
            return checkFeatureModified();
        case FL_FEATURE_ID_CREATE_TIME:
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
        case FL_FEATURE_ID_MD5:
            return checkMd5Initialized();
        case FL_FEATURE_ID_PERSON_ID:
            return checkPersonIdInitialized();
        case FL_FEATURE_ID_FEATURE:
            return checkFeatureInitialized();
        case FL_FEATURE_ID_CREATE_TIME:
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
     * Resets the primary keys ( {@link #md5} ) modification status to 'not modified'.
     */
    public void resetPrimaryKeysModified()
    {
        modified &= (~FL_FEATURE_ID_MD5_MASK);
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
        if (!(object instanceof FlFeatureBean)) {
            return false;
        }

        FlFeatureBean obj = (FlFeatureBean) object;
        return new EqualsBuilder()
            .append(getMd5(), obj.getMd5())
            .append(getPersonId(), obj.getPersonId())
            .append(getFeature(), obj.getFeature())
            .append(getCreateTime(), obj.getCreateTime())
            .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
            .append(getMd5())
            .append(getPersonId())
            .append(getFeature())
            .append(getCreateTime())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[\n")
            .append("\tmd5=").append(getMd5()).append("\n")
            .append("\tperson_id=").append(getPersonId()).append("\n")
            .append("\tfeature=").append(getFeature()).append("\n")
            .append("\tcreate_time=").append(getCreateTime()).append("\n")
            .append("]\n")
            .toString();
    }

    @Override
    public int compareTo(FlFeatureBean object){
        return new CompareToBuilder()
            .append(getMd5(), object.getMd5())
            .toComparison();
    }
    @Override
    public FlFeatureBean clone(){
        try {
            return (FlFeatureBean) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
    * set all field to null
    *
    * @author guyadong
    */
    public FlFeatureBean clean()
    {
        setMd5(null);
        setPersonId(null);
        setFeature(null);
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
    public void copy(FlFeatureBean bean, int... fieldList)
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
    }
        
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @param fieldList the column name list to copy into the current bean
     */
    public void copy(FlFeatureBean bean, String... fieldList)
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
        case FL_FEATURE_ID_MD5: 
            return (T)getMd5();        
        case FL_FEATURE_ID_PERSON_ID: 
            return (T)getPersonId();        
        case FL_FEATURE_ID_FEATURE: 
            return (T)getFeature();        
        case FL_FEATURE_ID_CREATE_TIME: 
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
        case FL_FEATURE_ID_MD5:        
            setMd5((String)value);
        case FL_FEATURE_ID_PERSON_ID:        
            setPersonId((Integer)value);
        case FL_FEATURE_ID_FEATURE:        
            setFeature((java.nio.ByteBuffer)value);
        case FL_FEATURE_ID_CREATE_TIME:        
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
        int index = FL_FEATURE_FIELDS_LIST.indexOf(column);
        if( 0 > index ) 
            index = FL_FEATURE_JAVA_FIELDS_LIST.indexOf(column);
        return index;    
    }
}
