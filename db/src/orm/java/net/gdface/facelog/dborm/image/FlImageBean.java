// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: bean.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm.image;
import java.io.Serializable;
import java.util.List;
import net.gdface.facelog.dborm.Constant;
import net.gdface.facelog.dborm.BaseBean;
import net.gdface.facelog.dborm.device.FlDeviceBean;
import net.gdface.facelog.dborm.CompareToBuilder;
import net.gdface.facelog.dborm.EqualsBuilder;
import net.gdface.facelog.dborm.HashCodeBuilder;
/**
 * FlImageBean is a mapping of fl_image Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 图像信息存储表,用于存储系统中所有用到的图像数据,表中只包含图像基本信息,图像二进制源数据存在在fl_store中(md5对应) </li>
 * </ul>
 * @author guyadong
*/
public  class FlImageBean
    implements Serializable,BaseBean<FlImageBean>,Comparable<FlImageBean>,Constant,Cloneable
{
    private static final long serialVersionUID = 646979810912117585L;
    /** NULL {@link FlImageBean} bean , IMMUTABLE instance */
    public static final FlImageBean NULL = new FlImageBean().asNULL().immutable(Boolean.TRUE);
    /** comments:主键,图像md5检验码,同时也是从 fl_store 获取图像数据的key */
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

    /** comments:缩略图md5,图像数据存储在 fl_imae_store(md5) */
    private String thumbMd5;

    /** comments:外键,图像来源设备 */
    private Integer deviceId;

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
    public synchronized FlImageBean immutable(Boolean immutable) {
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
    private FlImageBean checkMutable(){
        if(Boolean.TRUE == this.immutable){
            throw new IllegalStateException("this is a immutable object");
        }
        return this;
    }
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
    public void setNew(boolean isNew)
    {
        this.isNew = isNew;
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
    public FlImageBean(){
        super();
        reset();
    }
    /**
     * construct a new instance filled with primary keys
     * @param md5 PK# 1 
     */
    public FlImageBean(String md5){
        this();
        setMd5(md5);
    }
    /**
     * Getter method for {@link #md5}.<br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_image.md5</li>
     * <li> imported key: fl_face.image_md5</li>
     * <li> imported key: fl_person.image_md5</li>
     * <li>comments: 主键,图像md5检验码,同时也是从 fl_store 获取图像数据的key</li>
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
     * @param newVal the new value( NOT NULL) to be assigned to md5
     */
    public void setMd5(String newVal)
    {
        checkMutable();
        if (equal(newVal, md5) && checkMd5Initialized()) {
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
     * <li>JDBC type returned by the driver: Types.VARCHAR</li>
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
        checkMutable();
        if (equal(newVal, format) && checkFormatInitialized()) {
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
     * <li>NOT NULL</li>
     * <li>column size: 10</li>
     * <li>JDBC type returned by the driver: Types.INTEGER</li>
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
     * @param newVal the new value( NOT NULL) to be assigned to width
     */
    public void setWidth(Integer newVal)
    {
        checkMutable();
        if (equal(newVal, width) && checkWidthInitialized()) {
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
     * <li>NOT NULL</li>
     * <li>column size: 10</li>
     * <li>JDBC type returned by the driver: Types.INTEGER</li>
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
     * @param newVal the new value( NOT NULL) to be assigned to height
     */
    public void setHeight(Integer newVal)
    {
        checkMutable();
        if (equal(newVal, height) && checkHeightInitialized()) {
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
     * <li>default value: '0'</li>
     * <li>NOT NULL</li>
     * <li>column size: 10</li>
     * <li>JDBC type returned by the driver: Types.INTEGER</li>
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
     * @param newVal the new value( NOT NULL) to be assigned to depth
     */
    public void setDepth(Integer newVal)
    {
        checkMutable();
        if (equal(newVal, depth) && checkDepthInitialized()) {
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
     * <li>default value: '0'</li>
     * <li>NOT NULL</li>
     * <li>column size: 10</li>
     * <li>JDBC type returned by the driver: Types.INTEGER</li>
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
     * @param newVal the new value( NOT NULL) to be assigned to faceNum
     */
    public void setFaceNum(Integer newVal)
    {
        checkMutable();
        if (equal(newVal, faceNum) && checkFaceNumInitialized()) {
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
     * <li>comments: 缩略图md5,图像数据存储在 fl_imae_store(md5)</li>
     * <li>column size: 32</li>
     * <li>JDBC type returned by the driver: Types.CHAR</li>
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
        checkMutable();
        if (equal(newVal, thumbMd5) && checkThumbMd5Initialized()) {
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
     * <li>JDBC type returned by the driver: Types.INTEGER</li>
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
        checkMutable();
        if (equal(newVal, deviceId) && checkDeviceIdInitialized()) {
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

    @Override
    public boolean isModified()
    {
        return 0 != modified;
    }
  
    @Override
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
        default:
            return false;
        }        
    }

    @Override
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
        modified &= (~(FL_IMAGE_ID_MD5_MASK));
    }
    /**
     * Resets columns modification status except primary keys to 'not modified'.
     */
    public void resetModifiedExceptPrimaryKeys()
    {
        modified &= (~(FL_IMAGE_ID_FORMAT_MASK |
            FL_IMAGE_ID_WIDTH_MASK |
            FL_IMAGE_ID_HEIGHT_MASK |
            FL_IMAGE_ID_DEPTH_MASK |
            FL_IMAGE_ID_FACE_NUM_MASK |
            FL_IMAGE_ID_THUMB_MD5_MASK |
            FL_IMAGE_ID_DEVICE_ID_MASK));
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
        this.md5 = null;
        this.format = null;
        this.width = null;
        this.height = null;
        this.depth = new Integer(0)/* DEFAULT:'0'*/;
        this.faceNum = new Integer(0)/* DEFAULT:'0'*/;
        this.thumbMd5 = null;
        this.deviceId = null;
        this.isNew = true;
        this.modified = 0L;
        this.initialized = (FL_IMAGE_ID_DEPTH_MASK | FL_IMAGE_ID_FACE_NUM_MASK);
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
            .toHashCode();
    }

    @Override
    public String toString() {
        // only output initialized field
        StringBuilder builder = new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[");
        int count = 0;        
        if(checkMd5Initialized()){
            if(count++ >0){
                builder.append(",");
            }
            builder.append("md5=").append(getMd5());
        }
        if(checkFormatInitialized()){
            if(count++ >0){
                builder.append(",");
            }
            builder.append("format=").append(getFormat());
        }
        if(checkWidthInitialized()){
            if(count++ >0){
                builder.append(",");
            }
            builder.append("width=").append(getWidth());
        }
        if(checkHeightInitialized()){
            if(count++ >0){
                builder.append(",");
            }
            builder.append("height=").append(getHeight());
        }
        if(checkDepthInitialized()){
            if(count++ >0){
                builder.append(",");
            }
            builder.append("depth=").append(getDepth());
        }
        if(checkFaceNumInitialized()){
            if(count++ >0){
                builder.append(",");
            }
            builder.append("face_num=").append(getFaceNum());
        }
        if(checkThumbMd5Initialized()){
            if(count++ >0){
                builder.append(",");
            }
            builder.append("thumb_md5=").append(getThumbMd5());
        }
        if(checkDeviceIdInitialized()){
            if(count++ >0){
                builder.append(",");
            }
            builder.append("device_id=").append(getDeviceId());
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(FlImageBean object){
        return new CompareToBuilder()
            .append(getMd5(), object.getMd5())
            .append(getFormat(), object.getFormat())
            .append(getWidth(), object.getWidth())
            .append(getHeight(), object.getHeight())
            .append(getDepth(), object.getDepth())
            .append(getFaceNum(), object.getFaceNum())
            .append(getThumbMd5(), object.getThumbMd5())
            .append(getDeviceId(), object.getDeviceId())
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
     * Make {@code this} to a NULL bean<br>
     * set all fields to null, {@link #modified} and {@link #initialized} be set to 0
     * @return {@code this} bean
     * @author guyadong
     */
    public FlImageBean asNULL()
    {   
        checkMutable();
        
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
    public static final List<FlImageBean> replaceNull(List<FlImageBean> source){
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
    public static final List<FlImageBean> replaceNullInstance(List<FlImageBean> source){
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
    public FlImageBean copy(FlImageBean bean)
    {
        return copy(bean,new int[]{});
    }
    @Override
    public FlImageBean copy(FlImageBean bean, int... fieldList)
    {
        if (null == fieldList || 0 == fieldList.length){
            for (int i = 0; i < 8; ++i) {
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
    public FlImageBean copy(FlImageBean bean, String... fieldList)
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
        default:
            return null;
        }
    }

    @Override
    public <T> void setValue(int columnID,T value)
    {
        switch( columnID ) {
        case FL_IMAGE_ID_MD5:
            setMd5((String)value);
            break;
        case FL_IMAGE_ID_FORMAT:
            setFormat((String)value);
            break;
        case FL_IMAGE_ID_WIDTH:
            setWidth((Integer)value);
            break;
        case FL_IMAGE_ID_HEIGHT:
            setHeight((Integer)value);
            break;
        case FL_IMAGE_ID_DEPTH:
            setDepth((Integer)value);
            break;
        case FL_IMAGE_ID_FACE_NUM:
            setFaceNum((Integer)value);
            break;
        case FL_IMAGE_ID_THUMB_MD5:
            setThumbMd5((String)value);
            break;
        case FL_IMAGE_ID_DEVICE_ID:
            setDeviceId((Integer)value);
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
        int index = FL_IMAGE_FIELDS_LIST.indexOf(column);
        return  index < 0 
            ? FL_IMAGE_JAVA_FIELDS_LIST.indexOf(column)
            : index;
    }
    
    public static final Builder builder(){
        return new Builder();
    }
    /** 
     * a builder for FlImageBean,the template instance is thread local variable
     * a instance of Builder can be reused.
     */
    public static final class Builder{
        /** FlImageBean instance used for template to create new FlImageBean instance. */
        static final ThreadLocal<FlImageBean> TEMPLATE = new ThreadLocal<FlImageBean>(){
            @Override
            protected FlImageBean initialValue() {
                return new FlImageBean();
            }};
        private Builder() {}
        /** 
         * reset the bean as template 
         * @see FlImageBean#reset()
         */
        public Builder reset(){
            TEMPLATE.get().reset();
            return this;
        }
        /** 
         * set as a immutable object
         * @see FlImageBean#immutable(Boolean)
         */
        public Builder immutable(){
            TEMPLATE.get().immutable(Boolean.TRUE);
            return this;
        }
        /** set a bean as template,must not be {@code null} */
        public Builder template(FlImageBean bean){
            if(null == bean){
                throw new NullPointerException();
            }
            TEMPLATE.set(bean);
            return this;
        }
        /** return a clone instance of {@link #TEMPLATE}*/
        public FlImageBean build(){
            return TEMPLATE.get().clone();
        }
        /** 
         * fill the field : fl_image.md5
         * @param md5 主键,图像md5检验码,同时也是从 fl_store 获取图像数据的key
         * @see {@link FlImageBean#getMd5()}
         * @see {@link FlImageBean#setMd5(String)}
         */
        public Builder md5(String md5){
            TEMPLATE.get().setMd5(md5);
            return this;
        }
        /** 
         * fill the field : fl_image.format
         * @param format 图像格式
         * @see {@link FlImageBean#getFormat()}
         * @see {@link FlImageBean#setFormat(String)}
         */
        public Builder format(String format){
            TEMPLATE.get().setFormat(format);
            return this;
        }
        /** 
         * fill the field : fl_image.width
         * @param width 图像宽度
         * @see {@link FlImageBean#getWidth()}
         * @see {@link FlImageBean#setWidth(Integer)}
         */
        public Builder width(Integer width){
            TEMPLATE.get().setWidth(width);
            return this;
        }
        /** 
         * fill the field : fl_image.height
         * @param height 图像高度
         * @see {@link FlImageBean#getHeight()}
         * @see {@link FlImageBean#setHeight(Integer)}
         */
        public Builder height(Integer height){
            TEMPLATE.get().setHeight(height);
            return this;
        }
        /** 
         * fill the field : fl_image.depth
         * @param depth 通道数
         * @see {@link FlImageBean#getDepth()}
         * @see {@link FlImageBean#setDepth(Integer)}
         */
        public Builder depth(Integer depth){
            TEMPLATE.get().setDepth(depth);
            return this;
        }
        /** 
         * fill the field : fl_image.face_num
         * @param faceNum 图像中的人脸数目
         * @see {@link FlImageBean#getFaceNum()}
         * @see {@link FlImageBean#setFaceNum(Integer)}
         */
        public Builder faceNum(Integer faceNum){
            TEMPLATE.get().setFaceNum(faceNum);
            return this;
        }
        /** 
         * fill the field : fl_image.thumb_md5
         * @param thumbMd5 缩略图md5,图像数据存储在 fl_imae_store(md5)
         * @see {@link FlImageBean#getThumbMd5()}
         * @see {@link FlImageBean#setThumbMd5(String)}
         */
        public Builder thumbMd5(String thumbMd5){
            TEMPLATE.get().setThumbMd5(thumbMd5);
            return this;
        }
        /** 
         * fill the field : fl_image.device_id
         * @param deviceId 外键,图像来源设备
         * @see {@link FlImageBean#getDeviceId()}
         * @see {@link FlImageBean#setDeviceId(Integer)}
         */
        public Builder deviceId(Integer deviceId){
            TEMPLATE.get().setDeviceId(deviceId);
            return this;
        }
    }
}
