package net.gdface.facelog.db;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * FlStoreBeanBase is a mapping of fl_store Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 二进制大数据存储表 </li>
 * </ul>
 * @author guyadong
*/
public class StoreBean
    implements Serializable,BaseBean,Comparable<StoreBean>
{
	private static final long serialVersionUID = -3656272639623326787L;

	/**
     * comments:主键,md5检验码
     */
    protected String md5;

    /**
     * comments:编码类型,GBK,UTF8...
     */
    protected String encoding;

    /**
     * comments:二进制数据
     */
    protected byte[] data;

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
     * Prefered methods to create a FlStoreBeanBase is via the createFlStoreBean method in FlStoreManager or
     * via the factory class FlStoreFactory create method
     */
    public StoreBean(){
    }
    /**
     * create a FlStoreBeanBase from a instance
     */
    public StoreBean(StoreBean bean){
        this.copy(bean);
    }
    /**
     * Getter method for {@link #md5}.<br>
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
     * Setter method for {@link #md5}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to md5
     */
    public void setMd5(String newVal){    
        md5 = newVal;
    }


    /**
     * Getter method for {@link #encoding}.<br>
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
     * Setter method for {@link #encoding}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to encoding
     */
    public void setEncoding(String newVal){    
        encoding = newVal;
    }


    /**
     * Getter method for {@link #data}.<br>
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
     * Setter method for {@link #data}.<br>
     * Attention, there will be no comparison with current value which
     * means calling this method will mark the field as 'modified' in all cases.
     *
     * @param newVal the new value to be assigned to data
     */
    public void setData(byte[] newVal){    
        data = newVal;
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
            .append(getEncoding())
            .append(getData())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[\n")
            .append("\tmd5=").append(getMd5()).append("\n")
            .append("\tencoding=").append(getEncoding()).append("\n")
            .append("\tdata=").append(getData()).append("\n")
            .append("]\n")
            .toString();
    }

    @Override
    public int compareTo(StoreBean object){
    	return getMd5().compareTo(object.getMd5());
    }
    /**
    * Copies property of the passed bean into the current bean.<br>
    * if bean.isNew() is true, call {@link #copyIfNotNull(GfCodeBeanBase)}
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copy(StoreBean bean)
    {
        if(bean.isNew()){
            copyIfNotNull(bean);
        }else{        
            isNew(bean.isNew());
            setMd5(bean.getMd5());
            setEncoding(bean.getEncoding());
            setData(bean.getData());
        }
    }
    /**
    * Copies property of the passed bean into the current bean if property not null.
    *
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copyIfNotNull(StoreBean bean)
    {
        isNew(bean.isNew());
        if(bean.getMd5()!=null)
            setMd5(bean.getMd5());
        if(bean.getEncoding()!=null)
            setEncoding(bean.getEncoding());
        if(bean.getData()!=null)
            setData(bean.getData());
    }

    /**
    * set all field to null
    *
    * @author guyadong
    */
    public StoreBean clean()
    {
        isNew(true);
        setMd5(null);
        setEncoding(null);
        setData(null);
        return this;
    }
}
