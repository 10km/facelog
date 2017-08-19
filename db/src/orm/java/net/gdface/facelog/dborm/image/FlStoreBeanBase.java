// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.dborm.image;
import net.gdface.facelog.dborm.BaseBean;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import net.gdface.facelog.dborm.CompareToBuilder;
import net.gdface.facelog.dborm.EqualsBuilder;
import net.gdface.facelog.dborm.HashCodeBuilder;

/**
 * FlStoreBean is a mapping of fl_store Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 二进制大数据存储表 </li>
 * </ul>
 * @author guyadong
*/
public class FlStoreBeanBase
    implements Serializable,BaseBean<FlStoreBeanBase>,Comparable<FlStoreBean>
{
	private static final long serialVersionUID = -4951123832670214323L;
	
    /**
     * comments:二进制数据
     */
    protected byte[] data;

    /**
     * comments:编码类型,GBK,UTF8...
     */
    protected String encoding;

    /**
     * comments:主键,md5检验码
     */
    protected String md5;

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
    public FlStoreBeanBase(){
    }
    /**
     * create a FlStoreBeanBase from a instance
     */
    public FlStoreBeanBase(FlStoreBeanBase bean){
        this.copy(bean);
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



    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof FlStoreBean)) {
            return false;
        }

        FlStoreBean obj = (FlStoreBean) object;
        return new EqualsBuilder()
            .append(getData(), obj.getData())
            .append(getEncoding(), obj.getEncoding())
            .append(getMd5(), obj.getMd5())
            .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
            .append(getData())
            .append(getEncoding())
            .append(getMd5())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[\n")
            .append("\tdata=").append(getData()).append("\n")
            .append("\tencoding=").append(getEncoding()).append("\n")
            .append("\tmd5=").append(getMd5()).append("\n")
            .append("]\n")
            .toString();
    }

    @Override
    public int compareTo(FlStoreBean object){
        return new CompareToBuilder()
            .append(getData(), object.getData())
            .append(getEncoding(), object.getEncoding())
            .append(getMd5(), object.getMd5())
            .toComparison();
    }
    /**
    * Copies property of the passed bean into the current bean.<br>
    * if bean.isNew() is true, call {@link #copyIfNotNull(GfCodeBeanBase)}
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copy(FlStoreBeanBase bean)
    {
        if(bean.isNew()){
            copyIfNotNull(bean);
        }else{        
            isNew(bean.isNew());
            setData(bean.getData());
            setEncoding(bean.getEncoding());
            setMd5(bean.getMd5());
        }
    }
    /**
    * Copies property of the passed bean into the current bean if property not null.
    *
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copyIfNotNull(FlStoreBeanBase bean)
    {
        isNew(bean.isNew());
        if(bean.getData()!=null)
            setData(bean.getData());
        if(bean.getEncoding()!=null)
            setEncoding(bean.getEncoding());
        if(bean.getMd5()!=null)
            setMd5(bean.getMd5());
    }

    /**
    * set all field to null
    *
    * @author guyadong
    */
    public FlStoreBeanBase clean()
    {
        isNew(true);
        setData(null);
        setEncoding(null);
        setMd5(null);
        return this;
    }
    
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @param fieldList the column name list to copy into the current bean
     */
    public void copy(FlStoreBeanBase bean, String[] fieldList)
    {
        if (null == fieldList)
            copy(bean);
        else
            for (int i = 0; i < fieldList.length; i++) {
                setObject(fieldList[i].trim(), bean.getObject(fieldList[i].trim()));
            }
    }
    /**
     * create new FlStoreBean form {@code bean} if not instanceof FlStoreBean<br>
     * 
    * @param bean
    * @return null if bean is null
    * @see #toFullBean()
    * @author guyadong
    */
    public final static FlStoreBean toFullBean(FlStoreBeanBase bean)
    {
        return null==bean?null:(bean.toFullBean());
    }
    /**
    * @param bases
    * @return
    * @see #toFullBean(FlStoreBeanBase)
    * @author guyadong
    */
    public static FlStoreBean[] toFullBean(FlStoreBeanBase[] bases){
        FlStoreBean[] b = new FlStoreBean[bases.length];
        for(int i=0;i<b.length;i++){
            b[i]=toFullBean(bases[i]);
        }
        return b;
    }
    /**
     * create new FlStoreBean form {@code bean} if not instanceof FlStoreBean<br>
     * 
    * @param bean
    * @return null if bean is null
    * @author guyadong
    */
    @SuppressWarnings("unchecked")
    public FlStoreBean toFullBean()
    {
        return this instanceof FlStoreBean?(FlStoreBean)this:new FlStoreBean(this);
    }

    /**
     * return a dictionnary of the object
     */
    public Map<String,String> readDictionnary()
    {
        Map<String,String> dictionnary = new HashMap<String,String>();
        dictionnary.put("data", getData() == null ? "" : getData().toString());
        dictionnary.put("encoding", getEncoding() == null ? "" : getEncoding().toString());
        dictionnary.put("md5", getMd5() == null ? "" : getMd5().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map<String,String> readPkDictionnary()
    {
        Map<String,String> dictionnary = new HashMap<String,String>();
        dictionnary.put("md5", getMd5() == null ? "" : getMd5().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("data".equalsIgnoreCase(column) || "data".equalsIgnoreCase(column)) {
            return getData() == null ? "" : getData().toString();
        } else if ("encoding".equalsIgnoreCase(column) || "encoding".equalsIgnoreCase(column)) {
            return getEncoding() == null ? "" : getEncoding().toString();
        } else if ("md5".equalsIgnoreCase(column) || "md5".equalsIgnoreCase(column)) {
            return getMd5() == null ? "" : getMd5().toString();
        }
        return "";
    }

    /**
     * return a object representation of the given field
     */
    @SuppressWarnings("unchecked")
    public <T>T getObject(String column)
    {
        if (null == column || "".equals(column)) {
            return null;
        } else if ("data".equalsIgnoreCase(column) || "data".equalsIgnoreCase(column)) {
            return getData() == null ? null : (T)getData();
        } else if ("encoding".equalsIgnoreCase(column) || "encoding".equalsIgnoreCase(column)) {
            return getEncoding() == null ? null : (T)getEncoding();
        } else if ("md5".equalsIgnoreCase(column) || "md5".equalsIgnoreCase(column)) {
            return getMd5() == null ? null : (T)getMd5();
        }
        return null;
    }

    /**
     * set a value representation of the given field
     */
    public <T>void setObject(String column,T object)
    {
        if (null == column || "".equals(column)) {
            return ;
        } else if ("data".equalsIgnoreCase(column) || "data".equalsIgnoreCase(column)) {
            setData((byte[])object);
        } else if ("encoding".equalsIgnoreCase(column) || "encoding".equalsIgnoreCase(column)) {
            setEncoding((String)object);
        } else if ("md5".equalsIgnoreCase(column) || "md5".equalsIgnoreCase(column)) {
            setMd5((String)object);
        }
    }
}
