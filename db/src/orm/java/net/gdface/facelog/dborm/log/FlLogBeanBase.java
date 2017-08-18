// ______________________________________________________
// Generated by sql2java - http://sql2java.sourceforge.net/
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
//
// Please help us improve this tool by reporting:
// - problems and suggestions to
//   http://sourceforge.net/tracker/?group_id=54687
// - feedbacks and ideas on
//   http://sourceforge.net/forum/forum.php?forum_id=182208
// ______________________________________________________

package net.gdface.facelog.dborm.log;
import net.gdface.facelog.dborm.BaseBean;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import net.gdface.facelog.dborm.person.FlPersonBean;
import net.gdface.facelog.dborm.device.FlDeviceBean;
import net.gdface.facelog.dborm.face.FlFaceBean;
import net.gdface.facelog.dborm.CompareToBuilder;
import net.gdface.facelog.dborm.EqualsBuilder;
import net.gdface.facelog.dborm.HashCodeBuilder;

/**
 * FlLogBean is a mapping of fl_log Table.
 * @author guyadong
*/
public class FlLogBeanBase
    implements Serializable,BaseBean<FlLogBeanBase>
{
	private static final long serialVersionUID = -8472273736755030888L;
	
    protected java.util.Date createTime;

    protected java.util.Date verifyTime;

    protected Double similarty;

    protected String compareFace;

    protected String verifyFace;

    protected Integer deviceId;

    protected Integer personId;

    protected Integer id;

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
     * Prefered methods to create a FlLogBeanBase is via the createFlLogBean method in FlLogManager or
     * via the factory class FlLogFactory create method
     */
    public FlLogBeanBase(){
    }
    /**
     * create a FlLogBeanBase from a instance
     */
    public FlLogBeanBase(FlLogBeanBase bean){
        this.copy(bean);
    }
    /**
     * Getter method for createTime.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log.create_time</li>
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
     * Setter method for createTime.
     * <br>
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
     * Setter method for createTime.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to createTime
     */
    public void setCreateTime(long newVal){
        setCreateTime(new java.util.Date(newVal));
    }


    /**
     * Getter method for verifyTime.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log.verify_time</li>
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
     * Setter method for verifyTime.
     * <br>
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
     * Setter method for verifyTime.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to verifyTime
     */
    public void setVerifyTime(long newVal){
        setVerifyTime(new java.util.Date(newVal));
    }


    /**
     * Getter method for similarty.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log.similarty</li>
     * <li>comments: 验证相似度</li>
     * <li>column size: 22</li>
     * <li>jdbc type returned by the driver: Types.DOUBLE</li>
     * </ul>
     *
     * @return the value of similarty
     */
    public Double getSimilarty(){
        return similarty;
    }
    /**
     * Setter method for similarty.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to similarty
     */
    public void setSimilarty(Double newVal){    
        similarty = newVal;
    }

    /**
     * Setter method for similarty.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to similarty
     */
    public void setSimilarty(double newVal){
        setSimilarty(new Double(newVal));
    }


    /**
     * Getter method for compareFace.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log.compare_face</li>
     * <li> foreign key: fl_face.md5</li>
     * <li>comments: 外键,数据库中最相似的对比人脸id</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of compareFace
     */
    public String getCompareFace(){
        return compareFace;
    }
    /**
     * Setter method for compareFace.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to compareFace
     */
    public void setCompareFace(String newVal){    
        compareFace = newVal;
    }



    /**
     * Getter method for verifyFace.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log.verify_face</li>
     * <li> foreign key: fl_face.md5</li>
     * <li>comments: 外键,验证人脸信息id</li>
     * <li>column size: 32</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of verifyFace
     */
    public String getVerifyFace(){
        return verifyFace;
    }
    /**
     * Setter method for verifyFace.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to verifyFace
     */
    public void setVerifyFace(String newVal){    
        verifyFace = newVal;
    }



    /**
     * Getter method for deviceId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log.device_id</li>
     * <li> foreign key: fl_device.id</li>
     * <li>comments: 外键,图像来源设备id</li>
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
     * Setter method for deviceId.
     * <br>
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
     * Setter method for deviceId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to deviceId
     */
    public void setDeviceId(int newVal){
        setDeviceId(new Integer(newVal));
    }


    /**
     * Getter method for personId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log.person_id</li>
     * <li> foreign key: fl_person.id</li>
     * <li>comments: 外键,用户id</li>
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
     * Setter method for personId.
     * <br>
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
     * Setter method for personId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to personId
     */
    public void setPersonId(int newVal){
        setPersonId(new Integer(newVal));
    }


    /**
     * Getter method for id.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log.id</li>
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
     * Setter method for id.
     * <br>
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
     * Setter method for id.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to id
     */
    public void setId(int newVal){
        setId(new Integer(newVal));
    }


    /** The FlPerson referenced by this bean. */
    private FlPersonBean referencedFlPerson;
    /** Getter method for FlPersonBean. */
    public FlPersonBean getFlPersonBean() {
        return this.referencedFlPerson;
    }
    /** Setter method for FlPersonBean. */
    public void setFlPersonBean(FlPersonBean reference) {
        this.referencedFlPerson = reference;
    }    
    /** The FlDevice referenced by this bean. */
    private FlDeviceBean referencedFlDevice;
    /** Getter method for FlDeviceBean. */
    public FlDeviceBean getFlDeviceBean() {
        return this.referencedFlDevice;
    }
    /** Setter method for FlDeviceBean. */
    public void setFlDeviceBean(FlDeviceBean reference) {
        this.referencedFlDevice = reference;
    }    
    /** The FlFace referenced by this bean. */
    private FlFaceBean referencedFlFace;
    /** Getter method for FlFaceBean. */
    public FlFaceBean getFlFaceBean() {
        return this.referencedFlFace;
    }
    /** Setter method for FlFaceBean. */
    public void setFlFaceBean(FlFaceBean reference) {
        this.referencedFlFace = reference;
    }    
    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object)
    {
        if (!(object instanceof FlLogBean)) {
            return false;
        }

        FlLogBean obj = (FlLogBean) object;
        return new EqualsBuilder()
            .append(getCreateTime(), obj.getCreateTime())
            .append(getVerifyTime(), obj.getVerifyTime())
            .append(getSimilarty(), obj.getSimilarty())
            .append(getCompareFace(), obj.getCompareFace())
            .append(getVerifyFace(), obj.getVerifyFace())
            .append(getDeviceId(), obj.getDeviceId())
            .append(getPersonId(), obj.getPersonId())
            .append(getId(), obj.getId())
            .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
            .append(getCreateTime())
            .append(getVerifyTime())
            .append(getSimilarty())
            .append(getCompareFace())
            .append(getVerifyFace())
            .append(getDeviceId())
            .append(getPersonId())
            .append(getId())
            .toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[\n")
            .append("\tcreate_time=").append(getCreateTime()).append("\n")
            .append("\tverify_time=").append(getVerifyTime()).append("\n")
            .append("\tsimilarty=").append(getSimilarty()).append("\n")
            .append("\tcompare_face=").append(getCompareFace()).append("\n")
            .append("\tverify_face=").append(getVerifyFace()).append("\n")
            .append("\tdevice_id=").append(getDeviceId()).append("\n")
            .append("\tperson_id=").append(getPersonId()).append("\n")
            .append("\tid=").append(getId()).append("\n")
            .append("]\n")
            .toString();
    }


    public int compareTo(Object object){
        FlLogBean obj = (FlLogBean) object;
        return new CompareToBuilder()
            .append(getCreateTime(), obj.getCreateTime())
            .append(getVerifyTime(), obj.getVerifyTime())
            .append(getSimilarty(), obj.getSimilarty())
            .append(getCompareFace(), obj.getCompareFace())
            .append(getVerifyFace(), obj.getVerifyFace())
            .append(getDeviceId(), obj.getDeviceId())
            .append(getPersonId(), obj.getPersonId())
            .append(getId(), obj.getId())
            .toComparison();
    }
    /**
    * Copies proterty of the passed bean into the current bean.<br>
    * if bean.isNew() is true, call {@link #copyIfNotNull(GfCodeBeanBase)}
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copy(FlLogBeanBase bean)
    {
        if(bean.isNew()){
            copyIfNotNull(bean);
        }else{        
            isNew(bean.isNew());
            setCreateTime(bean.getCreateTime());
            setVerifyTime(bean.getVerifyTime());
            setSimilarty(bean.getSimilarty());
            setCompareFace(bean.getCompareFace());
            setVerifyFace(bean.getVerifyFace());
            setDeviceId(bean.getDeviceId());
            setPersonId(bean.getPersonId());
            setId(bean.getId());
        }
    }
    /**
    * Copies proterty of the passed bean into the current bean if property not null.
    *
    * @param bean the bean to copy into the current bean
    * @author guyadong
    */
    public void copyIfNotNull(FlLogBeanBase bean)
    {
        isNew(bean.isNew());
        if(bean.getCreateTime()!=null)
            setCreateTime(bean.getCreateTime());
        if(bean.getVerifyTime()!=null)
            setVerifyTime(bean.getVerifyTime());
        if(bean.getSimilarty()!=null)
            setSimilarty(bean.getSimilarty());
        if(bean.getCompareFace()!=null)
            setCompareFace(bean.getCompareFace());
        if(bean.getVerifyFace()!=null)
            setVerifyFace(bean.getVerifyFace());
        if(bean.getDeviceId()!=null)
            setDeviceId(bean.getDeviceId());
        if(bean.getPersonId()!=null)
            setPersonId(bean.getPersonId());
        if(bean.getId()!=null)
            setId(bean.getId());
    }

    /**
    * set all field to null
    *
    * @author guyadong
    */
    public FlLogBeanBase clean()
    {
        isNew(true);
        setCreateTime(null);
        setVerifyTime(null);
        setSimilarty(null);
        setCompareFace(null);
        setVerifyFace(null);
        setDeviceId(null);
        setPersonId(null);
        setId(null);
        return this;
    }
    
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @param fieldList the column name list to copy into the current bean
     */
    public void copy(FlLogBeanBase bean, String[] fieldList)
    {
        if (null == fieldList)
            copy(bean);
        else
            for (int i = 0; i < fieldList.length; i++) {
                setObject(fieldList[i].trim(), bean.getObject(fieldList[i].trim()));
            }
    }
    /**
     * create new FlLogBean form {@code bean} if not instanceof FlLogBean<br>
     * 
    * @param bean
    * @return null if bean is null
    * @see #toFullBean()
    * @author guyadong
    */
    public final static FlLogBean toFullBean(FlLogBeanBase bean)
    {
        return null==bean?null:(bean.toFullBean());
    }
    /**
    * @param bases
    * @return
    * @see #toFullBean(FlLogBeanBase)
    * @author guyadong
    */
    public static FlLogBean[] toFullBean(FlLogBeanBase[] bases){
        FlLogBean[] b = new FlLogBean[bases.length];
        for(int i=0;i<b.length;i++){
            b[i]=toFullBean(bases[i]);
        }
        return b;
    }
    /**
     * create new FlLogBean form {@code bean} if not instanceof FlLogBean<br>
     * 
    * @param bean
    * @return null if bean is null
    * @author guyadong
    */
    @SuppressWarnings("unchecked")
    public FlLogBean toFullBean()
    {
        return this instanceof FlLogBean?(FlLogBean)this:new FlLogBean(this);
    }

    /**
     * return a dictionnary of the object
     */
    public Map<String,String> readDictionnary()
    {
        Map<String,String> dictionnary = new HashMap<String,String>();
        dictionnary.put("create_time", getCreateTime() == null ? "" : getCreateTime().toString());
        dictionnary.put("verify_time", getVerifyTime() == null ? "" : getVerifyTime().toString());
        dictionnary.put("similarty", getSimilarty() == null ? "" : getSimilarty().toString());
        dictionnary.put("compare_face", getCompareFace() == null ? "" : getCompareFace().toString());
        dictionnary.put("verify_face", getVerifyFace() == null ? "" : getVerifyFace().toString());
        dictionnary.put("device_id", getDeviceId() == null ? "" : getDeviceId().toString());
        dictionnary.put("person_id", getPersonId() == null ? "" : getPersonId().toString());
        dictionnary.put("id", getId() == null ? "" : getId().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map<String,String> readPkDictionnary()
    {
        Map<String,String> dictionnary = new HashMap<String,String>();
        dictionnary.put("id", getId() == null ? "" : getId().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("create_time".equalsIgnoreCase(column) || "createTime".equalsIgnoreCase(column)) {
            return getCreateTime() == null ? "" : getCreateTime().toString();
        } else if ("verify_time".equalsIgnoreCase(column) || "verifyTime".equalsIgnoreCase(column)) {
            return getVerifyTime() == null ? "" : getVerifyTime().toString();
        } else if ("similarty".equalsIgnoreCase(column) || "similarty".equalsIgnoreCase(column)) {
            return getSimilarty() == null ? "" : getSimilarty().toString();
        } else if ("compare_face".equalsIgnoreCase(column) || "compareFace".equalsIgnoreCase(column)) {
            return getCompareFace() == null ? "" : getCompareFace().toString();
        } else if ("verify_face".equalsIgnoreCase(column) || "verifyFace".equalsIgnoreCase(column)) {
            return getVerifyFace() == null ? "" : getVerifyFace().toString();
        } else if ("device_id".equalsIgnoreCase(column) || "deviceId".equalsIgnoreCase(column)) {
            return getDeviceId() == null ? "" : getDeviceId().toString();
        } else if ("person_id".equalsIgnoreCase(column) || "personId".equalsIgnoreCase(column)) {
            return getPersonId() == null ? "" : getPersonId().toString();
        } else if ("id".equalsIgnoreCase(column) || "id".equalsIgnoreCase(column)) {
            return getId() == null ? "" : getId().toString();
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
        } else if ("create_time".equalsIgnoreCase(column) || "createTime".equalsIgnoreCase(column)) {
            return getCreateTime() == null ? null : (T)getCreateTime();
        } else if ("verify_time".equalsIgnoreCase(column) || "verifyTime".equalsIgnoreCase(column)) {
            return getVerifyTime() == null ? null : (T)getVerifyTime();
        } else if ("similarty".equalsIgnoreCase(column) || "similarty".equalsIgnoreCase(column)) {
            return getSimilarty() == null ? null : (T)getSimilarty();
        } else if ("compare_face".equalsIgnoreCase(column) || "compareFace".equalsIgnoreCase(column)) {
            return getCompareFace() == null ? null : (T)getCompareFace();
        } else if ("verify_face".equalsIgnoreCase(column) || "verifyFace".equalsIgnoreCase(column)) {
            return getVerifyFace() == null ? null : (T)getVerifyFace();
        } else if ("device_id".equalsIgnoreCase(column) || "deviceId".equalsIgnoreCase(column)) {
            return getDeviceId() == null ? null : (T)getDeviceId();
        } else if ("person_id".equalsIgnoreCase(column) || "personId".equalsIgnoreCase(column)) {
            return getPersonId() == null ? null : (T)getPersonId();
        } else if ("id".equalsIgnoreCase(column) || "id".equalsIgnoreCase(column)) {
            return getId() == null ? null : (T)getId();
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
        } else if ("create_time".equalsIgnoreCase(column) || "createTime".equalsIgnoreCase(column)) {
            setCreateTime((java.util.Date)object);
        } else if ("verify_time".equalsIgnoreCase(column) || "verifyTime".equalsIgnoreCase(column)) {
            setVerifyTime((java.util.Date)object);
        } else if ("similarty".equalsIgnoreCase(column) || "similarty".equalsIgnoreCase(column)) {
            setSimilarty((Double)object);
        } else if ("compare_face".equalsIgnoreCase(column) || "compareFace".equalsIgnoreCase(column)) {
            setCompareFace((String)object);
        } else if ("verify_face".equalsIgnoreCase(column) || "verifyFace".equalsIgnoreCase(column)) {
            setVerifyFace((String)object);
        } else if ("device_id".equalsIgnoreCase(column) || "deviceId".equalsIgnoreCase(column)) {
            setDeviceId((Integer)object);
        } else if ("person_id".equalsIgnoreCase(column) || "personId".equalsIgnoreCase(column)) {
            setPersonId((Integer)object);
        } else if ("id".equalsIgnoreCase(column) || "id".equalsIgnoreCase(column)) {
            setId((Integer)object);
        }
    }
}
