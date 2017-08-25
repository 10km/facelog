package net.gdface.facelog.db;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * FlPersonBeanBase is a mapping of fl_person Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: 人员基本描述信息 </li>
 * </ul>
 * @author guyadong
*/
public class PersonBean
    implements Serializable,Comparable<PersonBean>
{
	private static final long serialVersionUID = -4389648827870275876L;

	/**
     * comments:用户识别码
     */
    protected Integer id;

    /**
     * comments:用户所属组id
     */
    protected Integer groupId;

    /**
     * comments:姓名
     */
    protected String name;

    /**
     * comments:性别,0:女,1:男
     */
    protected Integer sex;

    /**
     * comments:出生日期
     */
    protected java.util.Date birthdate;

    /**
     * comments:证件类型,0:未知,1:身份证,2:护照,3:台胞证,4:港澳通行证,5:军官证,6:外国人居留证,7:员工卡,8:其他
     */
    protected Integer papersType;

    /**
     * comments:证件号码
     */
    protected String papersNum;

    /**
     * comments:用户默认照片(证件照,标准照)的md5校验码,外键
     */
    protected String photoId;

    /**
     * comments:从用户默认照片(photo_id)提取的人脸特征md5校验码,引用fl_face(md5),非存储字段,应用程序负责更新
     */
    protected String faceMd5;

    /**
     * comments:验证有效期限(超过期限不能通过验证),为NULL永久有效
     */
    protected java.util.Date expiryDate;

    protected java.util.Date createTime;

    protected java.util.Date updateTime;

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
     * Prefered methods to create a FlPersonBeanBase is via the createFlPersonBean method in FlPersonManager or
     * via the factory class FlPersonFactory create method
     */
    public PersonBean(){
    }
    /**
     * create a FlPersonBeanBase from a instance
     */
    public PersonBean(PersonBean bean){
        this.copy(bean);
    }
    /**
     * Getter method for {@link #id}.<br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_person.id</li>
     * <li> imported key: fl_face.person_id</li>
     * <li> imported key: fl_log.person_id</li>
     * <li>comments: 用户识别码</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of id
     */
    public Integer getId(){
        return id;
    }
    /**
     * Setter method for {@link #id}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to id
     */
    public void setId(Integer newVal){    
        id = newVal;
    }

    /**
     * Setter method for {@link #id}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to id
     */
    public void setId(int newVal){
        setId(new Integer(newVal));
    }

    /**
     * Getter method for {@link #groupId}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_person.group_id</li>
     * <li>comments: 用户所属组id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of groupId
     */
    public Integer getGroupId(){
        return groupId;
    }
    /**
     * Setter method for {@link #groupId}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to groupId
     */
    public void setGroupId(Integer newVal){    
        groupId = newVal;
    }

    /**
     * Setter method for {@link #groupId}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to groupId
     */
    public void setGroupId(int newVal){
        setGroupId(new Integer(newVal));
    }

    /**
     * Getter method for {@link #name}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_person.name</li>
     * <li>comments: 姓名</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of name
     */
    public String getName(){
        return name;
    }
    /**
     * Setter method for {@link #name}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to name
     */
    public void setName(String newVal){    
        name = newVal;
    }


    /**
     * Getter method for {@link #sex}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_person.sex</li>
     * <li>comments: 性别,0:女,1:男</li>
     * <li>column size: 3</li>
     * <li>jdbc type returned by the driver: Types.TINYINT</li>
     * </ul>
     *
     * @return the value of sex
     */
    public Integer getSex(){
        return sex;
    }
    /**
     * Setter method for {@link #sex}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to sex
     */
    public void setSex(Integer newVal){    
        sex = newVal;
    }

    /**
     * Setter method for {@link #sex}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to sex
     */
    public void setSex(int newVal){
        setSex(new Integer(newVal));
    }

    /**
     * Getter method for {@link #birthdate}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_person.birthdate</li>
     * <li>comments: 出生日期</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.DATE</li>
     * </ul>
     *
     * @return the value of birthdate
     */
    public java.util.Date getBirthdate(){
        return birthdate;
    }
    /**
     * Setter method for {@link #birthdate}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to birthdate
     */
    public void setBirthdate(java.util.Date newVal){    
        birthdate = newVal;
    }

    /**
     * Setter method for {@link #birthdate}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to birthdate
     */
    public void setBirthdate(long newVal){
        setBirthdate(new java.util.Date(newVal));
    }

    /**
     * Getter method for {@link #papersType}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_person.papers_type</li>
     * <li>comments: 证件类型,0:未知,1:身份证,2:护照,3:台胞证,4:港澳通行证,5:军官证,6:外国人居留证,7:员工卡,8:其他</li>
     * <li>column size: 3</li>
     * <li>jdbc type returned by the driver: Types.TINYINT</li>
     * </ul>
     *
     * @return the value of papersType
     */
    public Integer getPapersType(){
        return papersType;
    }
    /**
     * Setter method for {@link #papersType}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to papersType
     */
    public void setPapersType(Integer newVal){    
        papersType = newVal;
    }

    /**
     * Setter method for {@link #papersType}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to papersType
     */
    public void setPapersType(int newVal){
        setPapersType(new Integer(newVal));
    }

    /**
     * Getter method for {@link #papersNum}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_person.papers_num</li>
     * <li>comments: 证件号码</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of papersNum
     */
    public String getPapersNum(){
        return papersNum;
    }
    /**
     * Setter method for {@link #papersNum}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to papersNum
     */
    public void setPapersNum(String newVal){    
        papersNum = newVal;
    }


    /**
     * Getter method for {@link #photoId}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_person.photo_id</li>
     * <li> foreign key: fl_image.md5</li>
     * <li>comments: 用户默认照片(证件照,标准照)的md5校验码,外键</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.CHAR</li>
     * </ul>
     *
     * @return the value of photoId
     */
    public String getPhotoId(){
        return photoId;
    }
    /**
     * Setter method for {@link #photoId}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to photoId
     */
    public void setPhotoId(String newVal){    
        photoId = newVal;
    }


    /**
     * Getter method for {@link #faceMd5}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_person.face_md5</li>
     * <li>comments: 从用户默认照片(photo_id)提取的人脸特征md5校验码,引用fl_face(md5),非存储字段,应用程序负责更新</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.CHAR</li>
     * </ul>
     *
     * @return the value of faceMd5
     */
    public String getFaceMd5(){
        return faceMd5;
    }
    /**
     * Setter method for {@link #faceMd5}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to faceMd5
     */
    public void setFaceMd5(String newVal){    
        faceMd5 = newVal;
    }


    /**
     * Getter method for {@link #expiryDate}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_person.expiry_date</li>
     * <li>comments: 验证有效期限(超过期限不能通过验证),为NULL永久有效</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.DATE</li>
     * </ul>
     *
     * @return the value of expiryDate
     */
    public java.util.Date getExpiryDate(){
        return expiryDate;
    }
    /**
     * Setter method for {@link #expiryDate}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to expiryDate
     */
    public void setExpiryDate(java.util.Date newVal){    
        expiryDate = newVal;
    }

    /**
     * Setter method for {@link #expiryDate}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to expiryDate
     */
    public void setExpiryDate(long newVal){
        setExpiryDate(new java.util.Date(newVal));
    }

    /**
     * Getter method for {@link #createTime}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_person.create_time</li>
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

    /**
     * Getter method for {@link #updateTime}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_person.update_time</li>
     * <li>column size: 19</li>
     * <li>jdbc type returned by the driver: Types.TIMESTAMP</li>
     * </ul>
     *
     * @return the value of updateTime
     */
    public java.util.Date getUpdateTime(){
        return updateTime;
    }
    /**
     * Setter method for {@link #updateTime}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to updateTime
     */
    public void setUpdateTime(java.util.Date newVal){    
        updateTime = newVal;
    }

    /**
     * Setter method for {@link #updateTime}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to updateTime
     */
    public void setUpdateTime(long newVal){
        setUpdateTime(new java.util.Date(newVal));
    }


    //////////////////////////////////////
    // referenced bean for FOREIGN KEYS
    //////////////////////////////////////
    /** 
     * The referenced {@link ImageBean} by {@link #photoId} . <br>
     * FOREIGN KEY (photo_id) REFERENCES fl_image(md5)
     */
    private ImageBean referencedByPhotoId;
    /** Getter method for {@link #referencedByPhotoId}. */
    public ImageBean getReferencedByPhotoId() {
        return this.referencedByPhotoId;
    }
    /** Setter method for {@link #referencedByPhotoId}. */
    public void setReferencedByPhotoId(ImageBean reference) {
        this.referencedByPhotoId = reference;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof PersonBean)) {
            return false;
        }

        PersonBean obj = (PersonBean) object;
        return new EqualsBuilder()
            .append(getId(), obj.getId())
            .append(getGroupId(), obj.getGroupId())
            .append(getName(), obj.getName())
            .append(getSex(), obj.getSex())
            .append(getBirthdate(), obj.getBirthdate())
            .append(getPapersType(), obj.getPapersType())
            .append(getPapersNum(), obj.getPapersNum())
            .append(getPhotoId(), obj.getPhotoId())
            .append(getFaceMd5(), obj.getFaceMd5())
            .append(getExpiryDate(), obj.getExpiryDate())
            .append(getCreateTime(), obj.getCreateTime())
            .append(getUpdateTime(), obj.getUpdateTime())
            .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
            .append(getId())
            .append(getGroupId())
            .append(getName())
            .append(getSex())
            .append(getBirthdate())
            .append(getPapersType())
            .append(getPapersNum())
            .append(getPhotoId())
            .append(getFaceMd5())
            .append(getExpiryDate())
            .append(getCreateTime())
            .append(getUpdateTime())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[\n")
            .append("\tid=").append(getId()).append("\n")
            .append("\tgroup_id=").append(getGroupId()).append("\n")
            .append("\tname=").append(getName()).append("\n")
            .append("\tsex=").append(getSex()).append("\n")
            .append("\tbirthdate=").append(getBirthdate()).append("\n")
            .append("\tpapers_type=").append(getPapersType()).append("\n")
            .append("\tpapers_num=").append(getPapersNum()).append("\n")
            .append("\tphoto_id=").append(getPhotoId()).append("\n")
            .append("\tface_md5=").append(getFaceMd5()).append("\n")
            .append("\texpiry_date=").append(getExpiryDate()).append("\n")
            .append("\tcreate_time=").append(getCreateTime()).append("\n")
            .append("\tupdate_time=").append(getUpdateTime()).append("\n")
            .append("]\n")
            .toString();
    }

    @Override
    public int compareTo(PersonBean object){
    	return getId().compareTo(object.getId());
    }
    /**
    * Copies property of the passed bean into the current bean.<br>
    * if bean.isNew() is true, call {@link #copyIfNotNull(GfCodeBeanBase)}
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copy(PersonBean bean)
    {
        if(bean.isNew()){
            copyIfNotNull(bean);
        }else{        
            isNew(bean.isNew());
            setId(bean.getId());
            setGroupId(bean.getGroupId());
            setName(bean.getName());
            setSex(bean.getSex());
            setBirthdate(bean.getBirthdate());
            setPapersType(bean.getPapersType());
            setPapersNum(bean.getPapersNum());
            setPhotoId(bean.getPhotoId());
            setFaceMd5(bean.getFaceMd5());
            setExpiryDate(bean.getExpiryDate());
            setCreateTime(bean.getCreateTime());
            setUpdateTime(bean.getUpdateTime());
        }
    }
    /**
    * Copies property of the passed bean into the current bean if property not null.
    *
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copyIfNotNull(PersonBean bean)
    {
        isNew(bean.isNew());
        if(bean.getId()!=null)
            setId(bean.getId());
        if(bean.getGroupId()!=null)
            setGroupId(bean.getGroupId());
        if(bean.getName()!=null)
            setName(bean.getName());
        if(bean.getSex()!=null)
            setSex(bean.getSex());
        if(bean.getBirthdate()!=null)
            setBirthdate(bean.getBirthdate());
        if(bean.getPapersType()!=null)
            setPapersType(bean.getPapersType());
        if(bean.getPapersNum()!=null)
            setPapersNum(bean.getPapersNum());
        if(bean.getPhotoId()!=null)
            setPhotoId(bean.getPhotoId());
        if(bean.getFaceMd5()!=null)
            setFaceMd5(bean.getFaceMd5());
        if(bean.getExpiryDate()!=null)
            setExpiryDate(bean.getExpiryDate());
        if(bean.getCreateTime()!=null)
            setCreateTime(bean.getCreateTime());
        if(bean.getUpdateTime()!=null)
            setUpdateTime(bean.getUpdateTime());
    }

    /**
    * set all field to null
    *
    * @author guyadong
    */
    public PersonBean clean()
    {
        isNew(true);
        setId(null);
        setGroupId(null);
        setName(null);
        setSex(null);
        setBirthdate(null);
        setPapersType(null);
        setPapersNum(null);
        setPhotoId(null);
        setFaceMd5(null);
        setExpiryDate(null);
        setCreateTime(null);
        setUpdateTime(null);
        return this;
    }
}
