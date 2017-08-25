package net.gdface.facelog.db;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * FlFeatureBeanBase is a mapping of fl_feature Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: VIEW </li>
 * </ul>
 * @author guyadong
*/
public class FeatureBean
    implements Serializable,BaseBean,Comparable<FeatureBean>
{
	private static final long serialVersionUID = 1482337690356583744L;

	/**
     * comments:主键,特征数据md5校验码
     */
    protected String md5;

    /**
     * comments:外键,所属用户id
     */
    protected Integer personId;

    /**
     * comments:外键,所属图像id
     */
    protected String imgMd5;

    /**
     * comments:二进制特征数据
     */
    protected byte[] feature;

    protected java.util.Date createTime;

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
     * Prefered methods to create a FlFeatureBeanBase is via the createFlFeatureBean method in FlFeatureManager or
     * via the factory class FlFeatureFactory create method
     */
    public FeatureBean(){
    }
    /**
     * create a FlFeatureBeanBase from a instance
     */
    public FeatureBean(FeatureBean bean){
        this.copy(bean);
    }
    /**
     * Getter method for {@link #md5}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_feature.md5</li>
     * <li>comments: 主键,特征数据md5校验码</li>
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
     * Getter method for {@link #personId}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_feature.person_id</li>
     * <li>comments: 外键,所属用户id</li>
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
     * @param newVal the new value to be assigned to personId
     */
    public void setPersonId(Integer newVal){    
        personId = newVal;
    }

    /**
     * Setter method for {@link #personId}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to personId
     */
    public void setPersonId(int newVal){
        setPersonId(new Integer(newVal));
    }

    /**
     * Getter method for {@link #imgMd5}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_feature.img_md5</li>
     * <li>comments: 外键,所属图像id</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.CHAR</li>
     * </ul>
     *
     * @return the value of imgMd5
     */
    public String getImgMd5(){
        return imgMd5;
    }
    /**
     * Setter method for {@link #imgMd5}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to imgMd5
     */
    public void setImgMd5(String newVal){    
        imgMd5 = newVal;
    }


    /**
     * Getter method for {@link #feature}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_feature.feature</li>
     * <li>comments: 二进制特征数据</li>
     * <li>column size: 65535</li>
     * <li>jdbc type returned by the driver: Types.LONGVARBINARY</li>
     * </ul>
     *
     * @return the value of feature
     */
    public byte[] getFeature(){
        return feature;
    }
    /**
     * Setter method for {@link #feature}.<br>
     * Attention, there will be no comparison with current value which
     * means calling this method will mark the field as 'modified' in all cases.
     *
     * @param newVal the new value to be assigned to feature
     */
    public void setFeature(byte[] newVal){    
        feature = newVal;
    }


    /**
     * Getter method for {@link #createTime}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_feature.create_time</li>
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
     * @param newVal the new value to be assigned to createTime
     */
    public void setCreateTime(java.util.Date newVal){    
        createTime = newVal;
    }

    /**
     * Setter method for {@link #createTime}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to createTime
     */
    public void setCreateTime(long newVal){
        setCreateTime(new java.util.Date(newVal));
    }



    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof FeatureBean)) {
            return false;
        }

        FeatureBean obj = (FeatureBean) object;
        return new EqualsBuilder()
            .append(getMd5(), obj.getMd5())
            .append(getPersonId(), obj.getPersonId())
            .append(getImgMd5(), obj.getImgMd5())
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
            .append(getImgMd5())
            .append(getFeature())
            .append(getCreateTime())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[\n")
            .append("\tmd5=").append(getMd5()).append("\n")
            .append("\tperson_id=").append(getPersonId()).append("\n")
            .append("\timg_md5=").append(getImgMd5()).append("\n")
            .append("\tfeature=").append(getFeature()).append("\n")
            .append("\tcreate_time=").append(getCreateTime()).append("\n")
            .append("]\n")
            .toString();
    }

    @Override
    public int compareTo(FeatureBean object){
    	return getMd5().compareTo(object.getMd5());
    }
    /**
    * Copies property of the passed bean into the current bean.<br>
    * if bean.isNew() is true, call {@link #copyIfNotNull(GfCodeBeanBase)}
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copy(FeatureBean bean)
    {
        if(bean.isNew()){
            copyIfNotNull(bean);
        }else{        
            isNew(bean.isNew());
            setMd5(bean.getMd5());
            setPersonId(bean.getPersonId());
            setImgMd5(bean.getImgMd5());
            setFeature(bean.getFeature());
            setCreateTime(bean.getCreateTime());
        }
    }
    /**
    * Copies property of the passed bean into the current bean if property not null.
    *
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copyIfNotNull(FeatureBean bean)
    {
        isNew(bean.isNew());
        if(bean.getMd5()!=null)
            setMd5(bean.getMd5());
        if(bean.getPersonId()!=null)
            setPersonId(bean.getPersonId());
        if(bean.getImgMd5()!=null)
            setImgMd5(bean.getImgMd5());
        if(bean.getFeature()!=null)
            setFeature(bean.getFeature());
        if(bean.getCreateTime()!=null)
            setCreateTime(bean.getCreateTime());
    }

    /**
    * set all field to null
    *
    * @author guyadong
    */
    public FeatureBean clean()
    {
        isNew(true);
        setMd5(null);
        setPersonId(null);
        setImgMd5(null);
        setFeature(null);
        setCreateTime(null);
        return this;
    }
}
