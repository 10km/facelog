package net.gdface.facelog.db;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * FlFaceBeanBase is a mapping of fl_face Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 人脸检测信息数据表,用于保存检测到的人脸的所有信息(特征数据除外) </li>
 * </ul>
 * @author guyadong
*/
public class FaceBean
    implements Serializable,Comparable<FaceBean>
{
	private static final long serialVersionUID = -8767040876006428508L;

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

    protected Integer faceLeft;

    protected Integer faceTop;

    protected Integer faceWidth;

    protected Integer faceHeight;

    protected Integer eyeLeftx;

    protected Integer eyeLefty;

    protected Integer eyeRightx;

    protected Integer eyeRighty;

    protected Integer mouthX;

    protected Integer mouthY;

    protected Integer noseX;

    protected Integer noseY;

    protected Integer angleYaw;

    protected Integer anglePitch;

    protected Integer angleRoll;

    /**
     * comments:扩展字段,保存人脸检测基本信息之外的其他数据,内容由SDK负责解析
     */
    protected byte[] extInfo;

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
     * Prefered methods to create a FlFaceBeanBase is via the createFlFaceBean method in FlFaceManager or
     * via the factory class FlFaceFactory create method
     */
    public FaceBean(){
    }
    /**
     * create a FlFaceBeanBase from a instance
     */
    public FaceBean(FaceBean bean){
        this.copy(bean);
    }
    /**
     * Getter method for {@link #md5}.<br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.md5</li>
     * <li> imported key: fl_log.verify_face</li>
     * <li> imported key: fl_log.compare_face</li>
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
     * <li>full name: fl_face.person_id</li>
     * <li> foreign key: fl_person.id</li>
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
     * <li>full name: fl_face.img_md5</li>
     * <li> foreign key: fl_image.md5</li>
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
     * Getter method for {@link #faceLeft}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.face_left</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of faceLeft
     */
    public Integer getFaceLeft(){
        return faceLeft;
    }
    /**
     * Setter method for {@link #faceLeft}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to faceLeft
     */
    public void setFaceLeft(Integer newVal){    
        faceLeft = newVal;
    }

    /**
     * Setter method for {@link #faceLeft}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to faceLeft
     */
    public void setFaceLeft(int newVal){
        setFaceLeft(new Integer(newVal));
    }

    /**
     * Getter method for {@link #faceTop}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.face_top</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of faceTop
     */
    public Integer getFaceTop(){
        return faceTop;
    }
    /**
     * Setter method for {@link #faceTop}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to faceTop
     */
    public void setFaceTop(Integer newVal){    
        faceTop = newVal;
    }

    /**
     * Setter method for {@link #faceTop}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to faceTop
     */
    public void setFaceTop(int newVal){
        setFaceTop(new Integer(newVal));
    }

    /**
     * Getter method for {@link #faceWidth}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.face_width</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of faceWidth
     */
    public Integer getFaceWidth(){
        return faceWidth;
    }
    /**
     * Setter method for {@link #faceWidth}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to faceWidth
     */
    public void setFaceWidth(Integer newVal){    
        faceWidth = newVal;
    }

    /**
     * Setter method for {@link #faceWidth}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to faceWidth
     */
    public void setFaceWidth(int newVal){
        setFaceWidth(new Integer(newVal));
    }

    /**
     * Getter method for {@link #faceHeight}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.face_height</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of faceHeight
     */
    public Integer getFaceHeight(){
        return faceHeight;
    }
    /**
     * Setter method for {@link #faceHeight}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to faceHeight
     */
    public void setFaceHeight(Integer newVal){    
        faceHeight = newVal;
    }

    /**
     * Setter method for {@link #faceHeight}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to faceHeight
     */
    public void setFaceHeight(int newVal){
        setFaceHeight(new Integer(newVal));
    }

    /**
     * Getter method for {@link #eyeLeftx}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.eye_leftx</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of eyeLeftx
     */
    public Integer getEyeLeftx(){
        return eyeLeftx;
    }
    /**
     * Setter method for {@link #eyeLeftx}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to eyeLeftx
     */
    public void setEyeLeftx(Integer newVal){    
        eyeLeftx = newVal;
    }

    /**
     * Setter method for {@link #eyeLeftx}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to eyeLeftx
     */
    public void setEyeLeftx(int newVal){
        setEyeLeftx(new Integer(newVal));
    }

    /**
     * Getter method for {@link #eyeLefty}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.eye_lefty</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of eyeLefty
     */
    public Integer getEyeLefty(){
        return eyeLefty;
    }
    /**
     * Setter method for {@link #eyeLefty}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to eyeLefty
     */
    public void setEyeLefty(Integer newVal){    
        eyeLefty = newVal;
    }

    /**
     * Setter method for {@link #eyeLefty}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to eyeLefty
     */
    public void setEyeLefty(int newVal){
        setEyeLefty(new Integer(newVal));
    }

    /**
     * Getter method for {@link #eyeRightx}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.eye_rightx</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of eyeRightx
     */
    public Integer getEyeRightx(){
        return eyeRightx;
    }
    /**
     * Setter method for {@link #eyeRightx}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to eyeRightx
     */
    public void setEyeRightx(Integer newVal){    
        eyeRightx = newVal;
    }

    /**
     * Setter method for {@link #eyeRightx}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to eyeRightx
     */
    public void setEyeRightx(int newVal){
        setEyeRightx(new Integer(newVal));
    }

    /**
     * Getter method for {@link #eyeRighty}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.eye_righty</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of eyeRighty
     */
    public Integer getEyeRighty(){
        return eyeRighty;
    }
    /**
     * Setter method for {@link #eyeRighty}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to eyeRighty
     */
    public void setEyeRighty(Integer newVal){    
        eyeRighty = newVal;
    }

    /**
     * Setter method for {@link #eyeRighty}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to eyeRighty
     */
    public void setEyeRighty(int newVal){
        setEyeRighty(new Integer(newVal));
    }

    /**
     * Getter method for {@link #mouthX}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.mouth_x</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of mouthX
     */
    public Integer getMouthX(){
        return mouthX;
    }
    /**
     * Setter method for {@link #mouthX}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to mouthX
     */
    public void setMouthX(Integer newVal){    
        mouthX = newVal;
    }

    /**
     * Setter method for {@link #mouthX}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to mouthX
     */
    public void setMouthX(int newVal){
        setMouthX(new Integer(newVal));
    }

    /**
     * Getter method for {@link #mouthY}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.mouth_y</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of mouthY
     */
    public Integer getMouthY(){
        return mouthY;
    }
    /**
     * Setter method for {@link #mouthY}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to mouthY
     */
    public void setMouthY(Integer newVal){    
        mouthY = newVal;
    }

    /**
     * Setter method for {@link #mouthY}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to mouthY
     */
    public void setMouthY(int newVal){
        setMouthY(new Integer(newVal));
    }

    /**
     * Getter method for {@link #noseX}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.nose_x</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of noseX
     */
    public Integer getNoseX(){
        return noseX;
    }
    /**
     * Setter method for {@link #noseX}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to noseX
     */
    public void setNoseX(Integer newVal){    
        noseX = newVal;
    }

    /**
     * Setter method for {@link #noseX}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to noseX
     */
    public void setNoseX(int newVal){
        setNoseX(new Integer(newVal));
    }

    /**
     * Getter method for {@link #noseY}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.nose_y</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of noseY
     */
    public Integer getNoseY(){
        return noseY;
    }
    /**
     * Setter method for {@link #noseY}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to noseY
     */
    public void setNoseY(Integer newVal){    
        noseY = newVal;
    }

    /**
     * Setter method for {@link #noseY}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to noseY
     */
    public void setNoseY(int newVal){
        setNoseY(new Integer(newVal));
    }

    /**
     * Getter method for {@link #angleYaw}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.angle_yaw</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of angleYaw
     */
    public Integer getAngleYaw(){
        return angleYaw;
    }
    /**
     * Setter method for {@link #angleYaw}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to angleYaw
     */
    public void setAngleYaw(Integer newVal){    
        angleYaw = newVal;
    }

    /**
     * Setter method for {@link #angleYaw}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to angleYaw
     */
    public void setAngleYaw(int newVal){
        setAngleYaw(new Integer(newVal));
    }

    /**
     * Getter method for {@link #anglePitch}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.angle_pitch</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of anglePitch
     */
    public Integer getAnglePitch(){
        return anglePitch;
    }
    /**
     * Setter method for {@link #anglePitch}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to anglePitch
     */
    public void setAnglePitch(Integer newVal){    
        anglePitch = newVal;
    }

    /**
     * Setter method for {@link #anglePitch}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to anglePitch
     */
    public void setAnglePitch(int newVal){
        setAnglePitch(new Integer(newVal));
    }

    /**
     * Getter method for {@link #angleRoll}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.angle_roll</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of angleRoll
     */
    public Integer getAngleRoll(){
        return angleRoll;
    }
    /**
     * Setter method for {@link #angleRoll}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to angleRoll
     */
    public void setAngleRoll(Integer newVal){    
        angleRoll = newVal;
    }

    /**
     * Setter method for {@link #angleRoll}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to angleRoll
     */
    public void setAngleRoll(int newVal){
        setAngleRoll(new Integer(newVal));
    }

    /**
     * Getter method for {@link #extInfo}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.ext_info</li>
     * <li>comments: 扩展字段,保存人脸检测基本信息之外的其他数据,内容由SDK负责解析</li>
     * <li>column size: 65535</li>
     * <li>jdbc type returned by the driver: Types.LONGVARBINARY</li>
     * </ul>
     *
     * @return the value of extInfo
     */
    public byte[] getExtInfo(){
        return extInfo;
    }
    /**
     * Setter method for {@link #extInfo}.<br>
     * Attention, there will be no comparison with current value which
     * means calling this method will mark the field as 'modified' in all cases.
     *
     * @param newVal the new value to be assigned to extInfo
     */
    public void setExtInfo(byte[] newVal){    
        extInfo = newVal;
    }


    /**
     * Getter method for {@link #feature}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_face.feature</li>
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
     * <li>full name: fl_face.create_time</li>
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


    //////////////////////////////////////
    // referenced bean for FOREIGN KEYS
    //////////////////////////////////////
    /** 
     * The referenced {@link ImageBean} by {@link #imgMd5} . <br>
     * FOREIGN KEY (img_md5) REFERENCES fl_image(md5)
     */
    private ImageBean referencedByImgMd5;
    /** Getter method for {@link #referencedByImgMd5}. */
    public ImageBean getReferencedByImgMd5() {
        return this.referencedByImgMd5;
    }
    /** Setter method for {@link #referencedByImgMd5}. */
    public void setReferencedByImgMd5(ImageBean reference) {
        this.referencedByImgMd5 = reference;
    }
    /** 
     * The referenced {@link PersonBean} by {@link #personId} . <br>
     * FOREIGN KEY (person_id) REFERENCES fl_person(id)
     */
    private PersonBean referencedByPersonId;
    /** Getter method for {@link #referencedByPersonId}. */
    public PersonBean getReferencedByPersonId() {
        return this.referencedByPersonId;
    }
    /** Setter method for {@link #referencedByPersonId}. */
    public void setReferencedByPersonId(PersonBean reference) {
        this.referencedByPersonId = reference;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof FaceBean)) {
            return false;
        }

        FaceBean obj = (FaceBean) object;
        return new EqualsBuilder()
            .append(getMd5(), obj.getMd5())
            .append(getPersonId(), obj.getPersonId())
            .append(getImgMd5(), obj.getImgMd5())
            .append(getFaceLeft(), obj.getFaceLeft())
            .append(getFaceTop(), obj.getFaceTop())
            .append(getFaceWidth(), obj.getFaceWidth())
            .append(getFaceHeight(), obj.getFaceHeight())
            .append(getEyeLeftx(), obj.getEyeLeftx())
            .append(getEyeLefty(), obj.getEyeLefty())
            .append(getEyeRightx(), obj.getEyeRightx())
            .append(getEyeRighty(), obj.getEyeRighty())
            .append(getMouthX(), obj.getMouthX())
            .append(getMouthY(), obj.getMouthY())
            .append(getNoseX(), obj.getNoseX())
            .append(getNoseY(), obj.getNoseY())
            .append(getAngleYaw(), obj.getAngleYaw())
            .append(getAnglePitch(), obj.getAnglePitch())
            .append(getAngleRoll(), obj.getAngleRoll())
            .append(getExtInfo(), obj.getExtInfo())
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
            .append(getFaceLeft())
            .append(getFaceTop())
            .append(getFaceWidth())
            .append(getFaceHeight())
            .append(getEyeLeftx())
            .append(getEyeLefty())
            .append(getEyeRightx())
            .append(getEyeRighty())
            .append(getMouthX())
            .append(getMouthY())
            .append(getNoseX())
            .append(getNoseY())
            .append(getAngleYaw())
            .append(getAnglePitch())
            .append(getAngleRoll())
            .append(getExtInfo())
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
            .append("\tface_left=").append(getFaceLeft()).append("\n")
            .append("\tface_top=").append(getFaceTop()).append("\n")
            .append("\tface_width=").append(getFaceWidth()).append("\n")
            .append("\tface_height=").append(getFaceHeight()).append("\n")
            .append("\teye_leftx=").append(getEyeLeftx()).append("\n")
            .append("\teye_lefty=").append(getEyeLefty()).append("\n")
            .append("\teye_rightx=").append(getEyeRightx()).append("\n")
            .append("\teye_righty=").append(getEyeRighty()).append("\n")
            .append("\tmouth_x=").append(getMouthX()).append("\n")
            .append("\tmouth_y=").append(getMouthY()).append("\n")
            .append("\tnose_x=").append(getNoseX()).append("\n")
            .append("\tnose_y=").append(getNoseY()).append("\n")
            .append("\tangle_yaw=").append(getAngleYaw()).append("\n")
            .append("\tangle_pitch=").append(getAnglePitch()).append("\n")
            .append("\tangle_roll=").append(getAngleRoll()).append("\n")
            .append("\text_info=").append(getExtInfo()).append("\n")
            .append("\tfeature=").append(getFeature()).append("\n")
            .append("\tcreate_time=").append(getCreateTime()).append("\n")
            .append("]\n")
            .toString();
    }

    @Override
    public int compareTo(FaceBean object){
    	return getMd5().compareTo(object.getImgMd5());
    }
    /**
    * Copies property of the passed bean into the current bean.<br>
    * if bean.isNew() is true, call {@link #copyIfNotNull(GfCodeBeanBase)}
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copy(FaceBean bean)
    {
        if(bean.isNew()){
            copyIfNotNull(bean);
        }else{        
            isNew(bean.isNew());
            setMd5(bean.getMd5());
            setPersonId(bean.getPersonId());
            setImgMd5(bean.getImgMd5());
            setFaceLeft(bean.getFaceLeft());
            setFaceTop(bean.getFaceTop());
            setFaceWidth(bean.getFaceWidth());
            setFaceHeight(bean.getFaceHeight());
            setEyeLeftx(bean.getEyeLeftx());
            setEyeLefty(bean.getEyeLefty());
            setEyeRightx(bean.getEyeRightx());
            setEyeRighty(bean.getEyeRighty());
            setMouthX(bean.getMouthX());
            setMouthY(bean.getMouthY());
            setNoseX(bean.getNoseX());
            setNoseY(bean.getNoseY());
            setAngleYaw(bean.getAngleYaw());
            setAnglePitch(bean.getAnglePitch());
            setAngleRoll(bean.getAngleRoll());
            setExtInfo(bean.getExtInfo());
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
    public void copyIfNotNull(FaceBean bean)
    {
        isNew(bean.isNew());
        if(bean.getMd5()!=null)
            setMd5(bean.getMd5());
        if(bean.getPersonId()!=null)
            setPersonId(bean.getPersonId());
        if(bean.getImgMd5()!=null)
            setImgMd5(bean.getImgMd5());
        if(bean.getFaceLeft()!=null)
            setFaceLeft(bean.getFaceLeft());
        if(bean.getFaceTop()!=null)
            setFaceTop(bean.getFaceTop());
        if(bean.getFaceWidth()!=null)
            setFaceWidth(bean.getFaceWidth());
        if(bean.getFaceHeight()!=null)
            setFaceHeight(bean.getFaceHeight());
        if(bean.getEyeLeftx()!=null)
            setEyeLeftx(bean.getEyeLeftx());
        if(bean.getEyeLefty()!=null)
            setEyeLefty(bean.getEyeLefty());
        if(bean.getEyeRightx()!=null)
            setEyeRightx(bean.getEyeRightx());
        if(bean.getEyeRighty()!=null)
            setEyeRighty(bean.getEyeRighty());
        if(bean.getMouthX()!=null)
            setMouthX(bean.getMouthX());
        if(bean.getMouthY()!=null)
            setMouthY(bean.getMouthY());
        if(bean.getNoseX()!=null)
            setNoseX(bean.getNoseX());
        if(bean.getNoseY()!=null)
            setNoseY(bean.getNoseY());
        if(bean.getAngleYaw()!=null)
            setAngleYaw(bean.getAngleYaw());
        if(bean.getAnglePitch()!=null)
            setAnglePitch(bean.getAnglePitch());
        if(bean.getAngleRoll()!=null)
            setAngleRoll(bean.getAngleRoll());
        if(bean.getExtInfo()!=null)
            setExtInfo(bean.getExtInfo());
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
    public FaceBean clean()
    {
        isNew(true);
        setMd5(null);
        setPersonId(null);
        setImgMd5(null);
        setFaceLeft(null);
        setFaceTop(null);
        setFaceWidth(null);
        setFaceHeight(null);
        setEyeLeftx(null);
        setEyeLefty(null);
        setEyeRightx(null);
        setEyeRighty(null);
        setMouthX(null);
        setMouthY(null);
        setNoseX(null);
        setNoseY(null);
        setAngleYaw(null);
        setAnglePitch(null);
        setAngleRoll(null);
        setExtInfo(null);
        setFeature(null);
        setCreateTime(null);
        return this;
    }
    
}
