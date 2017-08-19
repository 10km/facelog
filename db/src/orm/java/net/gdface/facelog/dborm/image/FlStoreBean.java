// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.dborm.image;

import net.gdface.facelog.dborm.FullBean;

/**
 * FlStoreBean is a mapping of fl_store Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 二进制大数据存储表 </li>
 * </ul>
 * @author sql2java
*/
public class FlStoreBean
    extends FlStoreBeanBase
    implements FullBean<FlStoreBeanBase>
{
	private static final long serialVersionUID = 4570078444699714076L;
	
    private boolean dataIsModified = false;
    private boolean dataIsInitialized = false;

    private boolean encodingIsModified = false;
    private boolean encodingIsInitialized = false;

    private boolean md5IsModified = false;
    private boolean md5IsInitialized = false;



    /**
     * Prefered methods to create a FlStoreBean is via the createFlStoreBean method in FlStoreManager or
     * via the factory class FlStoreFactory create method
     * 为了能在webservice中传递对象，此处从protected改为public
     */
    public FlStoreBean(){
        super();
    }
    /**
     * create a FlStoreBean from a instance
     */
    FlStoreBean(FlStoreBeanBase bean){
        super();
        copy(bean);
    }
    /**
     * Getter method for data.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_store.data</li>
     * <li>comments: 二进制数据</li>
     * <li>column size: 65535</li>
     * <li>jdbc type returned by the driver: Types.LONGVARBINARY</li>
     * </ul>
     *
     * @return the value of data
     */
    public byte[] getData(){
        return data;
    }
    /**
     * Setter method for data.
     * <br>
     * Attention, there will be no comparison with current value which
     * means calling this method will mark the field as 'modified' in all cases.
     *
     * @param newVal the new value to be assigned to data
     */
    public void setData(byte[] newVal)
    {
        super.setData(newVal);
        dataIsModified = true;
        dataIsInitialized = true;
    }

    /**
     * Determines if the data has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isDataModified()
    {
        return dataIsModified;
    }

    /**
     * Determines if the data has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isDataInitialized()
    {
        return dataIsInitialized;
    }

    /**
     * Getter method for encoding.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_store.encoding</li>
     * <li>comments: 编码类型,GBK,UTF8...</li>
     * <li>column size: 16</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of encoding
     */
    public String getEncoding(){
        return encoding;
    }
    /**
     * Setter method for encoding.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to encoding
     */
    public void setEncoding(String newVal)
    {
        if ((newVal != null && encoding != null && (newVal.compareTo(encoding) == 0)) ||
            (newVal == null && encoding == null && encodingIsInitialized)) {
            return;
        }
        super.setEncoding(newVal);
        encodingIsModified = true;
        encodingIsInitialized = true;
    }

    /**
     * Determines if the encoding has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isEncodingModified()
    {
        return encodingIsModified;
    }

    /**
     * Determines if the encoding has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isEncodingInitialized()
    {
        return encodingIsInitialized;
    }

    /**
     * Getter method for md5.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_store.md5</li>
     * <li> imported key: fl_image.md5</li>
     * <li> imported key: fl_image.thumb_md5</li>
     * <li>comments: 主键,md5检验码</li>
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
     * Setter method for md5.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to md5
     */
    public void setMd5(String newVal)
    {
        if ((newVal != null && md5 != null && (newVal.compareTo(md5) == 0)) ||
            (newVal == null && md5 == null && md5IsInitialized)) {
            return;
        }
        super.setMd5(newVal);
        md5IsModified = true;
        md5IsInitialized = true;
    }

    /**
     * Determines if the md5 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isMd5Modified()
    {
        return md5IsModified;
    }

    /**
     * Determines if the md5 has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isMd5Initialized()
    {
        return md5IsInitialized;
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
        return dataIsModified 		|| encodingIsModified  		|| md5IsModified  ;
    }
    
    /**
     * Determines if the {@code column} has been modified.
     * @param column
     * @return true if the field has been modified, false if the field has not been modified
     * @author guyadong
     */
    public boolean isModified(String column){
        if (null == column || "".equals(column)) {
            return false;
        } else if ("data".equalsIgnoreCase(column) || "data".equalsIgnoreCase(column)) {
            return isDataModified();
        } else if ("encoding".equalsIgnoreCase(column) || "encoding".equalsIgnoreCase(column)) {
            return isEncodingModified();
        } else if ("md5".equalsIgnoreCase(column) || "md5".equalsIgnoreCase(column)) {
            return isMd5Modified();
        }
        return false;		
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
        if (null == column || "".equals(column)) {
            return false;
        } else if ("data".equalsIgnoreCase(column) || "data".equalsIgnoreCase(column)) {
            return isDataInitialized();
        } else if ("encoding".equalsIgnoreCase(column) || "encoding".equalsIgnoreCase(column)) {
            return isEncodingInitialized();
        } else if ("md5".equalsIgnoreCase(column) || "md5".equalsIgnoreCase(column)) {
            return isMd5Initialized();
        }
        return false;		
    }
    
    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified()
    {
        dataIsModified = false;
        encodingIsModified = false;
        md5IsModified = false;
    }

    /**
     * set all field to null and reset all modification status
     * @see #resetIsModified() 
     */
    public FlStoreBean clean()
    {
        super.clean();
        resetIsModified();
        return this;
    }

}
