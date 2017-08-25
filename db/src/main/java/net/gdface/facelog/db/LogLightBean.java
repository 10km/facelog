package net.gdface.facelog.db;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * FlLogLightBeanBase is a mapping of fl_log_light Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: VIEW </li>
 * </ul>
 * @author guyadong
*/
public class LogLightBean
    implements Serializable,BaseBean,Comparable<LogLightBean>
{
	private static final long serialVersionUID = -424252416358387796L;

	/**
     * comments:日志id
     */
    protected Integer id;

    /**
     * comments:用户识别码
     */
    protected Integer personId;

    /**
     * comments:姓名
     */
    protected String name;

    /**
     * comments:证件类型,0:未知,1:身份证,2:护照,3:台胞证,4:港澳通行证,5:军官证,6:外国人居留证,7:员工卡,8:其他
     */
    protected Integer papersType;

    /**
     * comments:证件号码
     */
    protected String papersNum;

    /**
     * comments:验证时间(可能由前端设备提供时间)
     */
    protected java.util.Date verifyTime;

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
     * Prefered methods to create a FlLogLightBeanBase is via the createFlLogLightBean method in FlLogLightManager or
     * via the factory class FlLogLightFactory create method
     */
    public LogLightBean(){
    }
    /**
     * create a FlLogLightBeanBase from a instance
     */
    public LogLightBean(LogLightBean bean){
        this.copy(bean);
    }
    /**
     * Getter method for {@link #id}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.id</li>
     * <li>comments: 日志id</li>
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
     * Getter method for {@link #personId}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.person_id</li>
     * <li>comments: 用户识别码</li>
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
     * Getter method for {@link #name}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.name</li>
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
     * Getter method for {@link #papersType}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.papers_type</li>
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
     * <li>full name: fl_log_light.papers_num</li>
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
     * Getter method for {@link #verifyTime}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.verify_time</li>
     * <li>comments: 验证时间(可能由前端设备提供时间)</li>
     * <li>column size: 19</li>
     * <li>jdbc type returned by the driver: Types.TIMESTAMP</li>
     * </ul>
     *
     * @return the value of verifyTime
     */
    public java.util.Date getVerifyTime(){
        return verifyTime;
    }
    /**
     * Setter method for {@link #verifyTime}.<br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to verifyTime
     */
    public void setVerifyTime(java.util.Date newVal){    
        verifyTime = newVal;
    }

    /**
     * Setter method for {@link #verifyTime}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to verifyTime
     */
    public void setVerifyTime(long newVal){
        setVerifyTime(new java.util.Date(newVal));
    }



    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof LogLightBean)) {
            return false;
        }

        LogLightBean obj = (LogLightBean) object;
        return new EqualsBuilder()
            .append(getId(), obj.getId())
            .append(getPersonId(), obj.getPersonId())
            .append(getName(), obj.getName())
            .append(getPapersType(), obj.getPapersType())
            .append(getPapersNum(), obj.getPapersNum())
            .append(getVerifyTime(), obj.getVerifyTime())
            .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
            .append(getId())
            .append(getPersonId())
            .append(getName())
            .append(getPapersType())
            .append(getPapersNum())
            .append(getVerifyTime())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[\n")
            .append("\tid=").append(getId()).append("\n")
            .append("\tperson_id=").append(getPersonId()).append("\n")
            .append("\tname=").append(getName()).append("\n")
            .append("\tpapers_type=").append(getPapersType()).append("\n")
            .append("\tpapers_num=").append(getPapersNum()).append("\n")
            .append("\tverify_time=").append(getVerifyTime()).append("\n")
            .append("]\n")
            .toString();
    }

    @Override
    public int compareTo(LogLightBean object){
    	return getId().compareTo(object.getId());
    }
    /**
    * Copies property of the passed bean into the current bean.<br>
    * if bean.isNew() is true, call {@link #copyIfNotNull(GfCodeBeanBase)}
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copy(LogLightBean bean)
    {
        if(bean.isNew()){
            copyIfNotNull(bean);
        }else{        
            isNew(bean.isNew());
            setId(bean.getId());
            setPersonId(bean.getPersonId());
            setName(bean.getName());
            setPapersType(bean.getPapersType());
            setPapersNum(bean.getPapersNum());
            setVerifyTime(bean.getVerifyTime());
        }
    }
    /**
    * Copies property of the passed bean into the current bean if property not null.
    *
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copyIfNotNull(LogLightBean bean)
    {
        isNew(bean.isNew());
        if(bean.getId()!=null)
            setId(bean.getId());
        if(bean.getPersonId()!=null)
            setPersonId(bean.getPersonId());
        if(bean.getName()!=null)
            setName(bean.getName());
        if(bean.getPapersType()!=null)
            setPapersType(bean.getPapersType());
        if(bean.getPapersNum()!=null)
            setPapersNum(bean.getPapersNum());
        if(bean.getVerifyTime()!=null)
            setVerifyTime(bean.getVerifyTime());
    }

    /**
    * set all field to null
    *
    * @author guyadong
    */
    public LogLightBean clean()
    {
        isNew(true);
        setId(null);
        setPersonId(null);
        setName(null);
        setPapersType(null);
        setPapersNum(null);
        setVerifyTime(null);
        return this;
    }
}
