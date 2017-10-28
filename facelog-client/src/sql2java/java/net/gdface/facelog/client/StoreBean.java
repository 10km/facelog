// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: bean.java.vm
// ______________________________________________________
package net.gdface.facelog.client;
import java.io.Serializable;
/**
 * StoreBean is a mapping of fl_store Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 二进制数据存储表 </li>
 * </ul>
 * @author guyadong
*/
public  class StoreBean
    implements Serializable,BaseBean<StoreBean>,Comparable<StoreBean>,Constant,Cloneable
{
    private static final long serialVersionUID = 7150971534896175420L;
    
    /** comments:主键,md5检验码 */
    private String md5;

    /** comments:编码类型,GBK,UTF8... */
    private String encoding;

    /** comments:二进制数据 */
    private byte[] data;

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
    public StoreBean(){
        super();
    }
    /**
     * Getter method for {@link #md5}.<br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_store.md5</li>
     * <li>comments: 主键,md5检验码</li>
     * <li>NOT NULL</li>
     * <li>column size: 32</li>
     * <li>JDBC type returned by the driver: Types.CHAR</li>
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

        modified |= FL_STORE_ID_MD5_MASK;
        initialized |= FL_STORE_ID_MD5_MASK;
    }

    /**
     * Determines if the md5 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkMd5Modified()
    {
        return 0L !=  (modified & FL_STORE_ID_MD5_MASK);
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
        return 0L !=  (initialized & FL_STORE_ID_MD5_MASK);
    }
    /**
     * Getter method for {@link #encoding}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_store.encoding</li>
     * <li>comments: 编码类型,GBK,UTF8...</li>
     * <li>column size: 16</li>
     * <li>JDBC type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of encoding
     */
    public String getEncoding(){
        return encoding;
    }
    /**
     * Setter method for {@link #encoding}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value  to be assigned to encoding
     */
    public void setEncoding(String newVal)
    {
        if ((newVal != null && encoding != null && (newVal.compareTo(encoding) == 0)) ||
            (newVal == null && encoding == null && checkEncodingInitialized())) {
            return;
        }
        encoding = newVal;

        modified |= FL_STORE_ID_ENCODING_MASK;
        initialized |= FL_STORE_ID_ENCODING_MASK;
    }

    /**
     * Determines if the encoding has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkEncodingModified()
    {
        return 0L !=  (modified & FL_STORE_ID_ENCODING_MASK);
    }

    /**
     * Determines if the encoding has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkEncodingInitialized()
    {
        return 0L !=  (initialized & FL_STORE_ID_ENCODING_MASK);
    }
    /**
     * Getter method for {@link #data}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_store.data</li>
     * <li>comments: 二进制数据</li>
     * <li>column size: 65535</li>
     * <li>JDBC type returned by the driver: Types.LONGVARBINARY</li>
     * </ul>
     *
     * @return the value of data
     */
    public byte[] getData(){
        return data;
    }
    /**
     * Setter method for {@link #data}.<br>
     * Attention, there will be no comparison with current value which
     * means calling this method will mark the field as 'modified' in all cases.
     *
     * @param newVal the new value  to be assigned to data
     */
    public void setData(byte[] newVal)
    {
        data = newVal;

        modified |= FL_STORE_ID_DATA_MASK;
        initialized |= FL_STORE_ID_DATA_MASK;
    }

    /**
     * Determines if the data has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkDataModified()
    {
        return 0L !=  (modified & FL_STORE_ID_DATA_MASK);
    }

    /**
     * Determines if the data has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkDataInitialized()
    {
        return 0L !=  (initialized & FL_STORE_ID_DATA_MASK);
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
        case FL_STORE_ID_MD5:
            return checkMd5Modified();
        case FL_STORE_ID_ENCODING:
            return checkEncodingModified();
        case FL_STORE_ID_DATA:
            return checkDataModified();
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
        case FL_STORE_ID_MD5:
            return checkMd5Initialized();
        case FL_STORE_ID_ENCODING:
            return checkEncodingInitialized();
        case FL_STORE_ID_DATA:
            return checkDataInitialized();
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
        modified &= (~(FL_STORE_ID_MD5_MASK));
    }
    /**
     * Resets columns modification status except primary keys to 'not modified'.
     */
    public void resetModifiedExceptPrimaryKeys()
    {
        modified &= (~(FL_STORE_ID_ENCODING_MASK |
            FL_STORE_ID_DATA_MASK));
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
        if (!(object instanceof StoreBean)) {
            return false;
        }

        StoreBean obj = (StoreBean) object;
        return new EqualsBuilder()
            .append(getMd5(), obj.getMd5())
            .append(getEncoding(), obj.getEncoding())
            .append(getData(), obj.getData())
            .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
            .append(getMd5())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[\n")
            .append("\tmd5=").append(getMd5()).append("\n")
            .append("\tencoding=").append(getEncoding()).append("\n")
            .append("\tdata=").append(getData().length).append(" bytes\n")
            .append("]\n")
            .toString();
    }

    @Override
    public int compareTo(StoreBean object){
        return new CompareToBuilder()
            .append(getMd5(), object.getMd5())
            .append(getEncoding(), object.getEncoding())
            .append(getData(), object.getData())
            .toComparison();
    }
    @Override
    public StoreBean clone(){
        try {
            return (StoreBean) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
    * set all field to null
    *
    * @author guyadong
    */
    public StoreBean clean()
    {
        setMd5(null);
        setEncoding(null);
        setData(null);
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
    public void copy(StoreBean bean, int... fieldList)
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
    public void copy(StoreBean bean, String... fieldList)
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
        case FL_STORE_ID_MD5: 
            return (T)getMd5();        
        case FL_STORE_ID_ENCODING: 
            return (T)getEncoding();        
        case FL_STORE_ID_DATA: 
            return (T)getData();        
        }
        return null;
    }

    /**
     * set a value representation of the given column id
     */
    public <T> void setValue(int columnID,T value)
    {
        switch( columnID ) {
        case FL_STORE_ID_MD5:        
            setMd5((String)value);
        case FL_STORE_ID_ENCODING:        
            setEncoding((String)value);
        case FL_STORE_ID_DATA:        
            setData((byte[])value);
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
        int index = FL_STORE_FIELDS_LIST.indexOf(column);
        if( 0 > index ) 
            index = FL_STORE_JAVA_FIELDS_LIST.indexOf(column);
        return index;    
    }
}
