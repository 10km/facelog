package net.gdface.facelog.db;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * FlImageBeanBase is a mapping of fl_image Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 图像存储表,用于存储系统中所有用到的图像数据 </li>
 * </ul>
 * @author guyadong
*/
public class ImageBean
    implements Serializable,BaseBean,Comparable<ImageBean>
{
	private static final long serialVersionUID = 333221336623905925L;

	/**
     * comments:主键,图像md5检验码,同时也是外键fl_store(md5)
     */
    protected String md5;

    /**
     * comments:图像格式
     */
    protected String format;

    /**
     * comments:图像宽度
     */
    protected Integer width;

    /**
     * comments:图像高度
     */
    protected Integer height;

    /**
     * comments:通道数
     */
    protected Integer depth;

    /**
     * comments:图像中的人脸数目
     */
    protected Integer faceNum;

    /**
     * comments:外键,缩略图md5,图像数据存储在fl_imae_store(md5)
     */
    protected String thumbMd5;

    /**
     * comments:外键,图像来源设备
     */
    protected Integer deviceId;

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
     * Prefered methods to create a FlImageBeanBase is via the createFlImageBean method in FlImageManager or
     * via the factory class FlImageFactory create method
     */
    public ImageBean(){
    }
    /**
     * create a FlImageBeanBase from a instance
     */
    public ImageBean(ImageBean bean){
        this.copy(bean);
    }
    /**
     * Getter method for {@link #md5}.<br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_image.md5</li>
     * <li> foreign key: fl_store.md5</li>
     * <li> imported key: fl_face.img_md5</li>
     * <li> imported key: fl_person.photo_id</li>
     * <li>comments: 主键,图像md5检验码,同时也是外键fl_store(md5)</li>
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
     * Getter method for {@link #format}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_image.format</li>
     * <li>comments: 图像格式</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of format
     */
    public String getFormat(){
        return format;
    }
    /**
     * Setter method for {@link #format}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to format
     */
    public void setFormat(String newVal){    
        format = newVal;
    }


    /**
     * Getter method for {@link #width}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_image.width</li>
     * <li>comments: 图像宽度</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of width
     */
    public Integer getWidth(){
        return width;
    }
    /**
     * Setter method for {@link #width}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to width
     */
    public void setWidth(Integer newVal){    
        width = newVal;
    }

    /**
     * Setter method for {@link #width}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to width
     */
    public void setWidth(int newVal){
        setWidth(new Integer(newVal));
    }

    /**
     * Getter method for {@link #height}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_image.height</li>
     * <li>comments: 图像高度</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of height
     */
    public Integer getHeight(){
        return height;
    }
    /**
     * Setter method for {@link #height}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to height
     */
    public void setHeight(Integer newVal){    
        height = newVal;
    }

    /**
     * Setter method for {@link #height}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to height
     */
    public void setHeight(int newVal){
        setHeight(new Integer(newVal));
    }

    /**
     * Getter method for {@link #depth}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_image.depth</li>
     * <li>comments: 通道数</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of depth
     */
    public Integer getDepth(){
        return depth;
    }
    /**
     * Setter method for {@link #depth}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to depth
     */
    public void setDepth(Integer newVal){    
        depth = newVal;
    }

    /**
     * Setter method for {@link #depth}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to depth
     */
    public void setDepth(int newVal){
        setDepth(new Integer(newVal));
    }

    /**
     * Getter method for {@link #faceNum}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_image.face_num</li>
     * <li>comments: 图像中的人脸数目</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of faceNum
     */
    public Integer getFaceNum(){
        return faceNum;
    }
    /**
     * Setter method for {@link #faceNum}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to faceNum
     */
    public void setFaceNum(Integer newVal){    
        faceNum = newVal;
    }

    /**
     * Setter method for {@link #faceNum}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to faceNum
     */
    public void setFaceNum(int newVal){
        setFaceNum(new Integer(newVal));
    }

    /**
     * Getter method for {@link #thumbMd5}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_image.thumb_md5</li>
     * <li> foreign key: fl_store.md5</li>
     * <li>comments: 外键,缩略图md5,图像数据存储在fl_imae_store(md5)</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.CHAR</li>
     * </ul>
     *
     * @return the value of thumbMd5
     */
    public String getThumbMd5(){
        return thumbMd5;
    }
    /**
     * Setter method for {@link #thumbMd5}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to thumbMd5
     */
    public void setThumbMd5(String newVal){    
        thumbMd5 = newVal;
    }


    /**
     * Getter method for {@link #deviceId}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_image.device_id</li>
     * <li> foreign key: fl_device.id</li>
     * <li>comments: 外键,图像来源设备</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of deviceId
     */
    public Integer getDeviceId(){
        return deviceId;
    }
    /**
     * Setter method for {@link #deviceId}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to deviceId
     */
    public void setDeviceId(Integer newVal){    
        deviceId = newVal;
    }

    /**
     * Setter method for {@link #deviceId}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to deviceId
     */
    public void setDeviceId(int newVal){
        setDeviceId(new Integer(newVal));
    }


    //////////////////////////////////////
    // referenced bean for FOREIGN KEYS
    //////////////////////////////////////
    /** 
     * The referenced {@link DeviceBean} by {@link #deviceId} . <br>
     * FOREIGN KEY (device_id) REFERENCES fl_device(id)
     */
    private DeviceBean referencedByDeviceId;
    /** Getter method for {@link #referencedByDeviceId}. */
    public DeviceBean getReferencedByDeviceId() {
        return this.referencedByDeviceId;
    }
    /** Setter method for {@link #referencedByDeviceId}. */
    public void setReferencedByDeviceId(DeviceBean reference) {
        this.referencedByDeviceId = reference;
    }
    /** 
     * The referenced {@link StoreBean} by {@link #md5} . <br>
     * FOREIGN KEY (md5) REFERENCES fl_store(md5)
     */
    private StoreBean referencedByMd5;
    /** Getter method for {@link #referencedByMd5}. */
    public StoreBean getReferencedByMd5() {
        return this.referencedByMd5;
    }
    /** Setter method for {@link #referencedByMd5}. */
    public void setReferencedByMd5(StoreBean reference) {
        this.referencedByMd5 = reference;
    }
    /** 
     * The referenced {@link StoreBean} by {@link #thumbMd5} . <br>
     * FOREIGN KEY (thumb_md5) REFERENCES fl_store(md5)
     */
    private StoreBean referencedByThumbMd5;
    /** Getter method for {@link #referencedByThumbMd5}. */
    public StoreBean getReferencedByThumbMd5() {
        return this.referencedByThumbMd5;
    }
    /** Setter method for {@link #referencedByThumbMd5}. */
    public void setReferencedByThumbMd5(StoreBean reference) {
        this.referencedByThumbMd5 = reference;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof ImageBean)) {
            return false;
        }

        ImageBean obj = (ImageBean) object;
        return new EqualsBuilder()
            .append(getMd5(), obj.getMd5())
            .append(getFormat(), obj.getFormat())
            .append(getWidth(), obj.getWidth())
            .append(getHeight(), obj.getHeight())
            .append(getDepth(), obj.getDepth())
            .append(getFaceNum(), obj.getFaceNum())
            .append(getThumbMd5(), obj.getThumbMd5())
            .append(getDeviceId(), obj.getDeviceId())
            .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
            .append(getMd5())
            .append(getFormat())
            .append(getWidth())
            .append(getHeight())
            .append(getDepth())
            .append(getFaceNum())
            .append(getThumbMd5())
            .append(getDeviceId())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[\n")
            .append("\tmd5=").append(getMd5()).append("\n")
            .append("\tformat=").append(getFormat()).append("\n")
            .append("\twidth=").append(getWidth()).append("\n")
            .append("\theight=").append(getHeight()).append("\n")
            .append("\tdepth=").append(getDepth()).append("\n")
            .append("\tface_num=").append(getFaceNum()).append("\n")
            .append("\tthumb_md5=").append(getThumbMd5()).append("\n")
            .append("\tdevice_id=").append(getDeviceId()).append("\n")
            .append("]\n")
            .toString();
    }

    @Override
    public int compareTo(ImageBean object){
    	return getMd5().compareTo(object.getMd5());
    }
    /**
    * Copies property of the passed bean into the current bean.<br>
    * if bean.isNew() is true, call {@link #copyIfNotNull(GfCodeBeanBase)}
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copy(ImageBean bean)
    {
        if(bean.isNew()){
            copyIfNotNull(bean);
        }else{        
            isNew(bean.isNew());
            setMd5(bean.getMd5());
            setFormat(bean.getFormat());
            setWidth(bean.getWidth());
            setHeight(bean.getHeight());
            setDepth(bean.getDepth());
            setFaceNum(bean.getFaceNum());
            setThumbMd5(bean.getThumbMd5());
            setDeviceId(bean.getDeviceId());
        }
    }
    /**
    * Copies property of the passed bean into the current bean if property not null.
    *
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copyIfNotNull(ImageBean bean)
    {
        isNew(bean.isNew());
        if(bean.getMd5()!=null)
            setMd5(bean.getMd5());
        if(bean.getFormat()!=null)
            setFormat(bean.getFormat());
        if(bean.getWidth()!=null)
            setWidth(bean.getWidth());
        if(bean.getHeight()!=null)
            setHeight(bean.getHeight());
        if(bean.getDepth()!=null)
            setDepth(bean.getDepth());
        if(bean.getFaceNum()!=null)
            setFaceNum(bean.getFaceNum());
        if(bean.getThumbMd5()!=null)
            setThumbMd5(bean.getThumbMd5());
        if(bean.getDeviceId()!=null)
            setDeviceId(bean.getDeviceId());
    }

    /**
    * set all field to null
    *
    * @author guyadong
    */
    public ImageBean clean()
    {
        isNew(true);
        setMd5(null);
        setFormat(null);
        setWidth(null);
        setHeight(null);
        setDepth(null);
        setFaceNum(null);
        setThumbMd5(null);
        setDeviceId(null);
        return this;
    }
}
