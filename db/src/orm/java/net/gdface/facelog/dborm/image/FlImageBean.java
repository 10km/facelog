// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.dborm.image;
import java.io.Serializable;
import net.gdface.facelog.dborm.Constant;
import net.gdface.facelog.dborm.BaseBean;
import net.gdface.facelog.dborm.device.FlDeviceBean;
import net.gdface.facelog.dborm.image.FlStoreBean;
import net.gdface.facelog.dborm.CompareToBuilder;
import net.gdface.facelog.dborm.EqualsBuilder;
import net.gdface.facelog.dborm.HashCodeBuilder;
/**
 * FlImageBean is a mapping of fl_image Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 图像存储表,用于存储系统中所有用到的图像数据 </li>
 * </ul>
 * @author guyadong
*/
public class FlImageBean
    implements Serializable,BaseBean<FlImageBean>,Comparable<FlImageBean>,Constant,Cloneable
{
    private static final long serialVersionUID = 646979810912117585L;
    
    /** comments:主键,图像md5检验码,同时也是外键fl_store(md5) */
    private String md5;

    /** comments:图像格式 */
    private String format;

    /** comments:图像宽度 */
    private Integer width;

    /** comments:图像高度 */
    private Integer height;

    /** comments:通道数 */
    private Integer depth;

    /** comments:图像中的人脸数目 */
    private Integer faceNum;

    /** comments:外键,缩略图md5,图像数据存储在fl_imae_store(md5) */
    private String thumbMd5;

    /** comments:外键,图像来源设备 */
    private Integer deviceId;

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
    public FlImageBean(){
        super();
    }
    /**
     * Getter method for {@link #md5}.<br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_image.md5</li>
     * <li> foreign key: fl_store.md5</li>
     * <li> imported key: fl_face.image_md5</li>
     * <li> imported key: fl_person.image_md5</li>
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
    public void setMd5(String newVal)
    {
        if ((newVal != null && md5 != null && (newVal.compareTo(md5) == 0)) ||
            (newVal == null && md5 == null && checkMd5Initialized())) {
            return;
        }
        md5 = newVal;

        modified |= FL_IMAGE_ID_MD5_MASK;
        initialized |= FL_IMAGE_ID_MD5_MASK;
    }

    /**
     * Determines if the md5 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkMd5Modified()
    {
        return 0L !=  (modified & FL_IMAGE_ID_MD5_MASK);
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
        return 0L !=  (initialized & FL_IMAGE_ID_MD5_MASK);
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
    public void setFormat(String newVal)
    {
        if ((newVal != null && format != null && (newVal.compareTo(format) == 0)) ||
            (newVal == null && format == null && checkFormatInitialized())) {
            return;
        }
        format = newVal;

        modified |= FL_IMAGE_ID_FORMAT_MASK;
        initialized |= FL_IMAGE_ID_FORMAT_MASK;
    }

    /**
     * Determines if the format has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkFormatModified()
    {
        return 0L !=  (modified & FL_IMAGE_ID_FORMAT_MASK);
    }

    /**
     * Determines if the format has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkFormatInitialized()
    {
        return 0L !=  (initialized & FL_IMAGE_ID_FORMAT_MASK);
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
    public void setWidth(Integer newVal)
    {
        if ((newVal != null && width != null && (newVal.compareTo(width) == 0)) ||
            (newVal == null && width == null && checkWidthInitialized())) {
            return;
        }
        width = newVal;

        modified |= FL_IMAGE_ID_WIDTH_MASK;
        initialized |= FL_IMAGE_ID_WIDTH_MASK;
    }

    /**
     * Setter method for {@link #width}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to width
     */
    public void setWidth(int newVal)
    {
        setWidth(new Integer(newVal));
    }
    /**
     * Determines if the width has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkWidthModified()
    {
        return 0L !=  (modified & FL_IMAGE_ID_WIDTH_MASK);
    }

    /**
     * Determines if the width has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkWidthInitialized()
    {
        return 0L !=  (initialized & FL_IMAGE_ID_WIDTH_MASK);
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
    public void setHeight(Integer newVal)
    {
        if ((newVal != null && height != null && (newVal.compareTo(height) == 0)) ||
            (newVal == null && height == null && checkHeightInitialized())) {
            return;
        }
        height = newVal;

        modified |= FL_IMAGE_ID_HEIGHT_MASK;
        initialized |= FL_IMAGE_ID_HEIGHT_MASK;
    }

    /**
     * Setter method for {@link #height}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to height
     */
    public void setHeight(int newVal)
    {
        setHeight(new Integer(newVal));
    }
    /**
     * Determines if the height has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkHeightModified()
    {
        return 0L !=  (modified & FL_IMAGE_ID_HEIGHT_MASK);
    }

    /**
     * Determines if the height has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkHeightInitialized()
    {
        return 0L !=  (initialized & FL_IMAGE_ID_HEIGHT_MASK);
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
    public void setDepth(Integer newVal)
    {
        if ((newVal != null && depth != null && (newVal.compareTo(depth) == 0)) ||
            (newVal == null && depth == null && checkDepthInitialized())) {
            return;
        }
        depth = newVal;

        modified |= FL_IMAGE_ID_DEPTH_MASK;
        initialized |= FL_IMAGE_ID_DEPTH_MASK;
    }

    /**
     * Setter method for {@link #depth}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to depth
     */
    public void setDepth(int newVal)
    {
        setDepth(new Integer(newVal));
    }
    /**
     * Determines if the depth has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkDepthModified()
    {
        return 0L !=  (modified & FL_IMAGE_ID_DEPTH_MASK);
    }

    /**
     * Determines if the depth has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkDepthInitialized()
    {
        return 0L !=  (initialized & FL_IMAGE_ID_DEPTH_MASK);
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
    public void setFaceNum(Integer newVal)
    {
        if ((newVal != null && faceNum != null && (newVal.compareTo(faceNum) == 0)) ||
            (newVal == null && faceNum == null && checkFaceNumInitialized())) {
            return;
        }
        faceNum = newVal;

        modified |= FL_IMAGE_ID_FACE_NUM_MASK;
        initialized |= FL_IMAGE_ID_FACE_NUM_MASK;
    }

    /**
     * Setter method for {@link #faceNum}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to faceNum
     */
    public void setFaceNum(int newVal)
    {
        setFaceNum(new Integer(newVal));
    }
    /**
     * Determines if the faceNum has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkFaceNumModified()
    {
        return 0L !=  (modified & FL_IMAGE_ID_FACE_NUM_MASK);
    }

    /**
     * Determines if the faceNum has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkFaceNumInitialized()
    {
        return 0L !=  (initialized & FL_IMAGE_ID_FACE_NUM_MASK);
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
    public void setThumbMd5(String newVal)
    {
        if ((newVal != null && thumbMd5 != null && (newVal.compareTo(thumbMd5) == 0)) ||
            (newVal == null && thumbMd5 == null && checkThumbMd5Initialized())) {
            return;
        }
        thumbMd5 = newVal;

        modified |= FL_IMAGE_ID_THUMB_MD5_MASK;
        initialized |= FL_IMAGE_ID_THUMB_MD5_MASK;
    }

    /**
     * Determines if the thumbMd5 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkThumbMd5Modified()
    {
        return 0L !=  (modified & FL_IMAGE_ID_THUMB_MD5_MASK);
    }

    /**
     * Determines if the thumbMd5 has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkThumbMd5Initialized()
    {
        return 0L !=  (initialized & FL_IMAGE_ID_THUMB_MD5_MASK);
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
    public void setDeviceId(Integer newVal)
    {
        if ((newVal != null && deviceId != null && (newVal.compareTo(deviceId) == 0)) ||
            (newVal == null && deviceId == null && checkDeviceIdInitialized())) {
            return;
        }
        deviceId = newVal;

        modified |= FL_IMAGE_ID_DEVICE_ID_MASK;
        initialized |= FL_IMAGE_ID_DEVICE_ID_MASK;
    }

    /**
     * Setter method for {@link #deviceId}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to deviceId
     */
    public void setDeviceId(int newVal)
    {
        setDeviceId(new Integer(newVal));
    }
    /**
     * Determines if the deviceId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkDeviceIdModified()
    {
        return 0L !=  (modified & FL_IMAGE_ID_DEVICE_ID_MASK);
    }

    /**
     * Determines if the deviceId has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkDeviceIdInitialized()
    {
        return 0L !=  (initialized & FL_IMAGE_ID_DEVICE_ID_MASK);
    }
    //////////////////////////////////////
    // referenced bean for FOREIGN KEYS
    //////////////////////////////////////
    /** 
     * The referenced {@link FlDeviceBean} by {@link #deviceId} . <br>
     * FOREIGN KEY (device_id) REFERENCES fl_device(id)
     */
    private FlDeviceBean referencedByDeviceId;
    /** Getter method for {@link #referencedByDeviceId}. */
    public FlDeviceBean getReferencedByDeviceId() {
        return this.referencedByDeviceId;
    }
    /** Setter method for {@link #referencedByDeviceId}. */
    public void setReferencedByDeviceId(FlDeviceBean reference) {
        this.referencedByDeviceId = reference;
    }
    /** 
     * The referenced {@link FlStoreBean} by {@link #md5} . <br>
     * FOREIGN KEY (md5) REFERENCES fl_store(md5)
     */
    private FlStoreBean referencedByMd5;
    /** Getter method for {@link #referencedByMd5}. */
    public FlStoreBean getReferencedByMd5() {
        return this.referencedByMd5;
    }
    /** Setter method for {@link #referencedByMd5}. */
    public void setReferencedByMd5(FlStoreBean reference) {
        this.referencedByMd5 = reference;
    }
    /** 
     * The referenced {@link FlStoreBean} by {@link #thumbMd5} . <br>
     * FOREIGN KEY (thumb_md5) REFERENCES fl_store(md5)
     */
    private FlStoreBean referencedByThumbMd5;
    /** Getter method for {@link #referencedByThumbMd5}. */
    public FlStoreBean getReferencedByThumbMd5() {
        return this.referencedByThumbMd5;
    }
    /** Setter method for {@link #referencedByThumbMd5}. */
    public void setReferencedByThumbMd5(FlStoreBean reference) {
        this.referencedByThumbMd5 = reference;
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
        case FL_IMAGE_ID_MD5:
            return checkMd5Modified();
        case FL_IMAGE_ID_FORMAT:
            return checkFormatModified();
        case FL_IMAGE_ID_WIDTH:
            return checkWidthModified();
        case FL_IMAGE_ID_HEIGHT:
            return checkHeightModified();
        case FL_IMAGE_ID_DEPTH:
            return checkDepthModified();
        case FL_IMAGE_ID_FACE_NUM:
            return checkFaceNumModified();
        case FL_IMAGE_ID_THUMB_MD5:
            return checkThumbMd5Modified();
        case FL_IMAGE_ID_DEVICE_ID:
            return checkDeviceIdModified();
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
        case FL_IMAGE_ID_MD5:
            return checkMd5Initialized();
        case FL_IMAGE_ID_FORMAT:
            return checkFormatInitialized();
        case FL_IMAGE_ID_WIDTH:
            return checkWidthInitialized();
        case FL_IMAGE_ID_HEIGHT:
            return checkHeightInitialized();
        case FL_IMAGE_ID_DEPTH:
            return checkDepthInitialized();
        case FL_IMAGE_ID_FACE_NUM:
            return checkFaceNumInitialized();
        case FL_IMAGE_ID_THUMB_MD5:
            return checkThumbMd5Initialized();
        case FL_IMAGE_ID_DEVICE_ID:
            return checkDeviceIdInitialized();
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
     * Resets the object initialization status to 'not initialized'.
     */
    private void resetInitialized()
    {
        initialized = 0L;
    }
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof FlImageBean)) {
            return false;
        }

        FlImageBean obj = (FlImageBean) object;
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
    public int compareTo(FlImageBean object){
        return new CompareToBuilder()
            .append(getMd5(), object.getMd5())
            .toComparison();
    }
    @Override
    public FlImageBean clone(){
        try {
            return (FlImageBean) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
    * set all field to null
    *
    * @author guyadong
    */
    public FlImageBean clean()
    {
        setMd5(null);
        setFormat(null);
        setWidth(null);
        setHeight(null);
        setDepth(null);
        setFaceNum(null);
        setThumbMd5(null);
        setDeviceId(null);
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
    public void copy(FlImageBean bean, int... fieldList)
    {
        if (null == fieldList || 0 == fieldList.length)
            for (int i = 0; i < 8; ++i) {
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
    public void copy(FlImageBean bean, String... fieldList)
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
        case FL_IMAGE_ID_MD5: 
            return (T)getMd5();        
        case FL_IMAGE_ID_FORMAT: 
            return (T)getFormat();        
        case FL_IMAGE_ID_WIDTH: 
            return (T)getWidth();        
        case FL_IMAGE_ID_HEIGHT: 
            return (T)getHeight();        
        case FL_IMAGE_ID_DEPTH: 
            return (T)getDepth();        
        case FL_IMAGE_ID_FACE_NUM: 
            return (T)getFaceNum();        
        case FL_IMAGE_ID_THUMB_MD5: 
            return (T)getThumbMd5();        
        case FL_IMAGE_ID_DEVICE_ID: 
            return (T)getDeviceId();        
        }
        return null;
    }

    /**
     * set a value representation of the given column id
     */
    public <T> void setValue(int columnID,T value)
    {
        switch( columnID ) {
        case FL_IMAGE_ID_MD5:        
            setMd5((String)value);
        case FL_IMAGE_ID_FORMAT:        
            setFormat((String)value);
        case FL_IMAGE_ID_WIDTH:        
            setWidth((Integer)value);
        case FL_IMAGE_ID_HEIGHT:        
            setHeight((Integer)value);
        case FL_IMAGE_ID_DEPTH:        
            setDepth((Integer)value);
        case FL_IMAGE_ID_FACE_NUM:        
            setFaceNum((Integer)value);
        case FL_IMAGE_ID_THUMB_MD5:        
            setThumbMd5((String)value);
        case FL_IMAGE_ID_DEVICE_ID:        
            setDeviceId((Integer)value);
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
        int index = FL_IMAGE_FIELDS_LIST.indexOf(column);
        if( 0 > index ) 
            index = FL_IMAGE_JAVA_FIELDS_LIST.indexOf(column);
        return index;    
    }
}
