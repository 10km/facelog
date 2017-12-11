// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: bean.java.vm
// ______________________________________________________
package net.gdface.facelog.client;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * LogLightBean is a mapping of fl_log_light Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: VIEW </li>
 * </ul>
 * @author guyadong
*/
public  class LogLightBean
    implements Serializable,BaseBean<LogLightBean>,Comparable<LogLightBean>,Constant,Cloneable
{
    private static final long serialVersionUID = 55112047030814160L;
    /** NULL {@link LogLightBean} bean , IMMUTABLE instance */
    public static final LogLightBean NULL = new LogLightBean().asNULL().immutable(Boolean.TRUE);
    /** comments:日志id */
    private Integer id;

    /** comments:用户id */
    private Integer personId;

    /** comments:姓名 */
    private String name;

    /** comments:证件类型,0:未知,1:身份证,2:护照,3:台胞证,4:港澳通行证,5:军官证,6:外国人居留证,7:员工卡,8:其他 */
    private Integer papersType;

    /** comments:证件号码 */
    private String papersNum;

    /** comments:验证时间(可能由前端设备提供时间) */
    private java.util.Date verifyTime;

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
    public synchronized LogLightBean immutable(Boolean immutable) {
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
    private LogLightBean checkMutable(){
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
    protected static final <T extends Comparable<T>>boolean equals(T a, T b) {
        return a == b || (a != null && 0==a.compareTo(b));
    }
    public LogLightBean(){
        super();
        reset();
    }
    /**
     * Getter method for {@link #id}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.id</li>
     * <li>comments: 日志id</li>
     * <li>default value: '0'</li>
     * <li>NOT NULL</li>
     * <li>column size: 10</li>
     * <li>JDBC type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of id
     */
    public Integer getId(){
        return id;
    }
    /**
     * Setter method for {@link #id}.<br>
     * The new value is set only if equals() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value( NOT NULL) to be assigned to id
     */
    public void setId(Integer newVal)
    {
        checkMutable();
        if (Objects.equals(newVal, id) && checkIdInitialized()) {
            return;
        }
        id = newVal;

        modified |= FL_LOG_LIGHT_ID_ID_MASK;
        initialized |= FL_LOG_LIGHT_ID_ID_MASK;
    }
    /**
     * Setter method for {@link #id}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to id
     */
    public void setId(int newVal)
    {
        setId(new Integer(newVal));
    }
    /**
     * Determines if the id has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkIdModified()
    {
        return 0L !=  (modified & FL_LOG_LIGHT_ID_ID_MASK);
    }

    /**
     * Determines if the id has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkIdInitialized()
    {
        return 0L !=  (initialized & FL_LOG_LIGHT_ID_ID_MASK);
    }
    /**
     * Getter method for {@link #personId}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.person_id</li>
     * <li>comments: 用户id</li>
     * <li>default value: '0'</li>
     * <li>NOT NULL</li>
     * <li>column size: 10</li>
     * <li>JDBC type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of personId
     */
    public Integer getPersonId(){
        return personId;
    }
    /**
     * Setter method for {@link #personId}.<br>
     * The new value is set only if equals() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value( NOT NULL) to be assigned to personId
     */
    public void setPersonId(Integer newVal)
    {
        checkMutable();
        if (Objects.equals(newVal, personId) && checkPersonIdInitialized()) {
            return;
        }
        personId = newVal;

        modified |= FL_LOG_LIGHT_ID_PERSON_ID_MASK;
        initialized |= FL_LOG_LIGHT_ID_PERSON_ID_MASK;
    }
    /**
     * Setter method for {@link #personId}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to personId
     */
    public void setPersonId(int newVal)
    {
        setPersonId(new Integer(newVal));
    }
    /**
     * Determines if the personId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkPersonIdModified()
    {
        return 0L !=  (modified & FL_LOG_LIGHT_ID_PERSON_ID_MASK);
    }

    /**
     * Determines if the personId has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkPersonIdInitialized()
    {
        return 0L !=  (initialized & FL_LOG_LIGHT_ID_PERSON_ID_MASK);
    }
    /**
     * Getter method for {@link #name}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.name</li>
     * <li>comments: 姓名</li>
     * <li>NOT NULL</li>
     * <li>column size: 32</li>
     * <li>JDBC type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of name
     */
    public String getName(){
        return name;
    }
    /**
     * Setter method for {@link #name}.<br>
     * The new value is set only if equals() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value( NOT NULL) to be assigned to name
     */
    public void setName(String newVal)
    {
        checkMutable();
        if (Objects.equals(newVal, name) && checkNameInitialized()) {
            return;
        }
        name = newVal;

        modified |= FL_LOG_LIGHT_ID_NAME_MASK;
        initialized |= FL_LOG_LIGHT_ID_NAME_MASK;
    }
    /**
     * Determines if the name has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkNameModified()
    {
        return 0L !=  (modified & FL_LOG_LIGHT_ID_NAME_MASK);
    }

    /**
     * Determines if the name has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkNameInitialized()
    {
        return 0L !=  (initialized & FL_LOG_LIGHT_ID_NAME_MASK);
    }
    /**
     * Getter method for {@link #papersType}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.papers_type</li>
     * <li>comments: 证件类型,0:未知,1:身份证,2:护照,3:台胞证,4:港澳通行证,5:军官证,6:外国人居留证,7:员工卡,8:其他</li>
     * <li>column size: 3</li>
     * <li>JDBC type returned by the driver: Types.TINYINT</li>
     * </ul>
     *
     * @return the value of papersType
     */
    public Integer getPapersType(){
        return papersType;
    }
    /**
     * Setter method for {@link #papersType}.<br>
     * The new value is set only if equals() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to papersType
     */
    public void setPapersType(Integer newVal)
    {
        checkMutable();
        if (Objects.equals(newVal, papersType) && checkPapersTypeInitialized()) {
            return;
        }
        papersType = newVal;

        modified |= FL_LOG_LIGHT_ID_PAPERS_TYPE_MASK;
        initialized |= FL_LOG_LIGHT_ID_PAPERS_TYPE_MASK;
    }
    /**
     * Setter method for {@link #papersType}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to papersType
     */
    public void setPapersType(int newVal)
    {
        setPapersType(new Integer(newVal));
    }
    /**
     * Determines if the papersType has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkPapersTypeModified()
    {
        return 0L !=  (modified & FL_LOG_LIGHT_ID_PAPERS_TYPE_MASK);
    }

    /**
     * Determines if the papersType has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkPapersTypeInitialized()
    {
        return 0L !=  (initialized & FL_LOG_LIGHT_ID_PAPERS_TYPE_MASK);
    }
    /**
     * Getter method for {@link #papersNum}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.papers_num</li>
     * <li>comments: 证件号码</li>
     * <li>column size: 32</li>
     * <li>JDBC type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of papersNum
     */
    public String getPapersNum(){
        return papersNum;
    }
    /**
     * Setter method for {@link #papersNum}.<br>
     * The new value is set only if equals() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to papersNum
     */
    public void setPapersNum(String newVal)
    {
        checkMutable();
        if (Objects.equals(newVal, papersNum) && checkPapersNumInitialized()) {
            return;
        }
        papersNum = newVal;

        modified |= FL_LOG_LIGHT_ID_PAPERS_NUM_MASK;
        initialized |= FL_LOG_LIGHT_ID_PAPERS_NUM_MASK;
    }
    /**
     * Determines if the papersNum has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkPapersNumModified()
    {
        return 0L !=  (modified & FL_LOG_LIGHT_ID_PAPERS_NUM_MASK);
    }

    /**
     * Determines if the papersNum has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkPapersNumInitialized()
    {
        return 0L !=  (initialized & FL_LOG_LIGHT_ID_PAPERS_NUM_MASK);
    }
    /**
     * Getter method for {@link #verifyTime}.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: fl_log_light.verify_time</li>
     * <li>comments: 验证时间(可能由前端设备提供时间)</li>
     * <li>default value: '0000-00-00 00:00:00'</li>
     * <li>NOT NULL</li>
     * <li>column size: 19</li>
     * <li>JDBC type returned by the driver: Types.TIMESTAMP</li>
     * </ul>
     *
     * @return the value of verifyTime
     */
    public java.util.Date getVerifyTime(){
        return verifyTime;
    }
    /**
     * Setter method for {@link #verifyTime}.<br>
     * The new value is set only if equals() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value( NOT NULL) to be assigned to verifyTime
     */
    public void setVerifyTime(java.util.Date newVal)
    {
        checkMutable();
        if (Objects.equals(newVal, verifyTime) && checkVerifyTimeInitialized()) {
            return;
        }
        verifyTime = newVal;

        modified |= FL_LOG_LIGHT_ID_VERIFY_TIME_MASK;
        initialized |= FL_LOG_LIGHT_ID_VERIFY_TIME_MASK;
    }
    /**
     * Setter method for {@link #verifyTime}.<br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to verifyTime
     */
    public void setVerifyTime(long newVal)
    {
        setVerifyTime(new java.util.Date(newVal));
    }
    /**
     * Determines if the verifyTime has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean checkVerifyTimeModified()
    {
        return 0L !=  (modified & FL_LOG_LIGHT_ID_VERIFY_TIME_MASK);
    }

    /**
     * Determines if the verifyTime has been initialized.<br>
     *
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean checkVerifyTimeInitialized()
    {
        return 0L !=  (initialized & FL_LOG_LIGHT_ID_VERIFY_TIME_MASK);
    }

    @Override
    public boolean isModified()
    {
        return 0 != modified;
    }
  
    @Override
    public boolean isModified(int columnID){
        switch ( columnID ){
        case FL_LOG_LIGHT_ID_ID:
            return checkIdModified();
        case FL_LOG_LIGHT_ID_PERSON_ID:
            return checkPersonIdModified();
        case FL_LOG_LIGHT_ID_NAME:
            return checkNameModified();
        case FL_LOG_LIGHT_ID_PAPERS_TYPE:
            return checkPapersTypeModified();
        case FL_LOG_LIGHT_ID_PAPERS_NUM:
            return checkPapersNumModified();
        case FL_LOG_LIGHT_ID_VERIFY_TIME:
            return checkVerifyTimeModified();
        default:
            return false;
        }        
    }

    @Override
    public boolean isInitialized(int columnID){
        switch(columnID) {
        case FL_LOG_LIGHT_ID_ID:
            return checkIdInitialized();
        case FL_LOG_LIGHT_ID_PERSON_ID:
            return checkPersonIdInitialized();
        case FL_LOG_LIGHT_ID_NAME:
            return checkNameInitialized();
        case FL_LOG_LIGHT_ID_PAPERS_TYPE:
            return checkPapersTypeInitialized();
        case FL_LOG_LIGHT_ID_PAPERS_NUM:
            return checkPapersNumInitialized();
        case FL_LOG_LIGHT_ID_VERIFY_TIME:
            return checkVerifyTimeInitialized();
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
        // columns is null or empty;
    }
    /**
     * Resets columns modification status except primary keys to 'not modified'.
     */
    public void resetModifiedExceptPrimaryKeys()
    {
        modified &= (~(FL_LOG_LIGHT_ID_ID_MASK |
            FL_LOG_LIGHT_ID_PERSON_ID_MASK |
            FL_LOG_LIGHT_ID_NAME_MASK |
            FL_LOG_LIGHT_ID_PAPERS_TYPE_MASK |
            FL_LOG_LIGHT_ID_PAPERS_NUM_MASK |
            FL_LOG_LIGHT_ID_VERIFY_TIME_MASK));
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
        /* DEFAULT:'0'*/
        this.id = new Integer(0);
        /* DEFAULT:'0'*/
        this.personId = new Integer(0);
        this.name = null;
        this.papersType = null;
        this.papersNum = null;
        /* DEFAULT:'0000-00-00 00:00:00'*/
        this.verifyTime = null;
        this.isNew = true;
        this.modified = 0L;
        this.initialized = (FL_LOG_LIGHT_ID_ID_MASK | FL_LOG_LIGHT_ID_PERSON_ID_MASK);
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
        return toString(false);
    }
    /**
     * @param notNull output not null field only if {@code true}
     */
    public String toString(boolean notNull) {
        // only output initialized field
        StringBuilder builder = new StringBuilder(this.getClass().getName()).append("@").append(Integer.toHexString(this.hashCode())).append("[");
        int count = 0;        
        if(checkIdInitialized()){
            if(!notNull || null != getId()){
                if(count++ >0){
                    builder.append(",");
                }            
                builder.append("id=").append(getId());
            }
        }
        if(checkPersonIdInitialized()){
            if(!notNull || null != getPersonId()){
                if(count++ >0){
                    builder.append(",");
                }            
                builder.append("person_id=").append(getPersonId());
            }
        }
        if(checkNameInitialized()){
            if(!notNull || null != getName()){
                if(count++ >0){
                    builder.append(",");
                }            
                builder.append("name=").append(getName());
            }
        }
        if(checkPapersTypeInitialized()){
            if(!notNull || null != getPapersType()){
                if(count++ >0){
                    builder.append(",");
                }            
                builder.append("papers_type=").append(getPapersType());
            }
        }
        if(checkPapersNumInitialized()){
            if(!notNull || null != getPapersNum()){
                if(count++ >0){
                    builder.append(",");
                }            
                builder.append("papers_num=").append(getPapersNum());
            }
        }
        if(checkVerifyTimeInitialized()){
            if(!notNull || null != getVerifyTime()){
                if(count++ >0){
                    builder.append(",");
                }            
                builder.append("verify_time=").append(getVerifyTime());
            }
        }
        builder.append("]");
        return builder.toString();
    }
    @Override
    public int compareTo(LogLightBean object){
        return new CompareToBuilder()
            .append(getId(), object.getId())
            .append(getPersonId(), object.getPersonId())
            .append(getName(), object.getName())
            .append(getPapersType(), object.getPapersType())
            .append(getPapersNum(), object.getPapersNum())
            .append(getVerifyTime(), object.getVerifyTime())
            .toComparison();
    }
    @Override
    public LogLightBean clone(){
        try {
            return (LogLightBean) super.clone();
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
    public LogLightBean asNULL()
    {   
        checkMutable();
        
        setId(null);
        setPersonId(null);
        setName(null);
        setPapersType(null);
        setPapersNum(null);
        setVerifyTime(null);
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
    public static final List<LogLightBean> replaceNull(List<LogLightBean> source){
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
    public static final List<LogLightBean> replaceNullInstance(List<LogLightBean> source){
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
    public LogLightBean copy(LogLightBean bean)
    {
        return copy(bean,new int[]{});
    }
    @Override
    public LogLightBean copy(LogLightBean bean, int... fieldList)
    {
        if (null == fieldList || 0 == fieldList.length){
            for (int i = 0; i < FL_LOG_LIGHT_COLUMN_COUNT; ++i) {
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
    public LogLightBean copy(LogLightBean bean, String... fieldList)
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
        case FL_LOG_LIGHT_ID_ID: 
            return (T)getId();        
        case FL_LOG_LIGHT_ID_PERSON_ID: 
            return (T)getPersonId();        
        case FL_LOG_LIGHT_ID_NAME: 
            return (T)getName();        
        case FL_LOG_LIGHT_ID_PAPERS_TYPE: 
            return (T)getPapersType();        
        case FL_LOG_LIGHT_ID_PAPERS_NUM: 
            return (T)getPapersNum();        
        case FL_LOG_LIGHT_ID_VERIFY_TIME: 
            return (T)getVerifyTime();        
        default:
            return null;
        }
    }

    @Override
    public <T> void setValue(int columnID,T value)
    {
        switch( columnID ) {
        case FL_LOG_LIGHT_ID_ID:
            setId((Integer)value);
            break;
        case FL_LOG_LIGHT_ID_PERSON_ID:
            setPersonId((Integer)value);
            break;
        case FL_LOG_LIGHT_ID_NAME:
            setName((String)value);
            break;
        case FL_LOG_LIGHT_ID_PAPERS_TYPE:
            setPapersType((Integer)value);
            break;
        case FL_LOG_LIGHT_ID_PAPERS_NUM:
            setPapersNum((String)value);
            break;
        case FL_LOG_LIGHT_ID_VERIFY_TIME:
            setVerifyTime((java.util.Date)value);
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
        int index = FL_LOG_LIGHT_FIELDS_LIST.indexOf(column);
        return  index < 0 
            ? FL_LOG_LIGHT_JAVA_FIELDS_LIST.indexOf(column)
            : index;
    }
    
    public static final Builder builder(){
        return new Builder();
    }
    /** 
     * a builder for LogLightBean,the template instance is thread local variable
     * a instance of Builder can be reused.
     */
    public static final class Builder{
        /** LogLightBean instance used for template to create new LogLightBean instance. */
        static final ThreadLocal<LogLightBean> TEMPLATE = new ThreadLocal<LogLightBean>(){
            @Override
            protected LogLightBean initialValue() {
                return new LogLightBean();
            }};
        private Builder() {}
        /** 
         * reset the bean as template 
         * @see LogLightBean#reset()
         */
        public Builder reset(){
            TEMPLATE.get().reset();
            return this;
        }
        /** 
         * set as a immutable object
         * @see LogLightBean#immutable(Boolean)
         */
        public Builder immutable(){
            TEMPLATE.get().immutable(Boolean.TRUE);
            return this;
        }
        /** set a bean as template,must not be {@code null} */
        public Builder template(LogLightBean bean){
            if(null == bean){
                throw new NullPointerException();
            }
            TEMPLATE.set(bean);
            return this;
        }
        /** return a clone instance of {@link #TEMPLATE}*/
        public LogLightBean build(){
            return TEMPLATE.get().clone();
        }
        /** 
         * fill the field : fl_log_light.id
         * @param id 日志id
         * @see {@link LogLightBean#getId()}
         * @see {@link LogLightBean#setId(Integer)}
         */
        public Builder id(Integer id){
            TEMPLATE.get().setId(id);
            return this;
        }
        /** 
         * fill the field : fl_log_light.person_id
         * @param personId 用户id
         * @see {@link LogLightBean#getPersonId()}
         * @see {@link LogLightBean#setPersonId(Integer)}
         */
        public Builder personId(Integer personId){
            TEMPLATE.get().setPersonId(personId);
            return this;
        }
        /** 
         * fill the field : fl_log_light.name
         * @param name 姓名
         * @see {@link LogLightBean#getName()}
         * @see {@link LogLightBean#setName(String)}
         */
        public Builder name(String name){
            TEMPLATE.get().setName(name);
            return this;
        }
        /** 
         * fill the field : fl_log_light.papers_type
         * @param papersType 证件类型,0:未知,1:身份证,2:护照,3:台胞证,4:港澳通行证,5:军官证,6:外国人居留证,7:员工卡,8:其他
         * @see {@link LogLightBean#getPapersType()}
         * @see {@link LogLightBean#setPapersType(Integer)}
         */
        public Builder papersType(Integer papersType){
            TEMPLATE.get().setPapersType(papersType);
            return this;
        }
        /** 
         * fill the field : fl_log_light.papers_num
         * @param papersNum 证件号码
         * @see {@link LogLightBean#getPapersNum()}
         * @see {@link LogLightBean#setPapersNum(String)}
         */
        public Builder papersNum(String papersNum){
            TEMPLATE.get().setPapersNum(papersNum);
            return this;
        }
        /** 
         * fill the field : fl_log_light.verify_time
         * @param verifyTime 验证时间(可能由前端设备提供时间)
         * @see {@link LogLightBean#getVerifyTime()}
         * @see {@link LogLightBean#setVerifyTime(java.util.Date)}
         */
        public Builder verifyTime(java.util.Date verifyTime){
            TEMPLATE.get().setVerifyTime(verifyTime);
            return this;
        }
    }
    /////// FOR THRIFT //////
    /** 
     * cast {@code this} to {@link net.gdface.facelog.client.thrift.LogLightBean}
     * @see {@link ThriftConverter#converterLogLightBean}
     */
    public net.gdface.facelog.client.thrift.LogLightBean toThrift(){
        return ThriftConverter.CONVERTER_LOGLIGHTBEAN.toRight(this);
    }
    /** 
     * copy all fields from {@link net.gdface.facelog.client.thrift.LogLightBean},do nothing if {@code thriftBean} is null
     * @return current object {@code this}
     * @see {@link ThriftConverter#converterLogLightBean}
     */
    public LogLightBean fromThrift(net.gdface.facelog.client.thrift.LogLightBean thriftBean){
        if(null != thriftBean){
            reset();
            return ThriftConverter.CONVERTER_LOGLIGHTBEAN.fromRight(this,thriftBean);
        }
        return this;
    }
    /** 
     * construct new instance from {@link net.gdface.facelog.client.thrift.LogLightBean}
     * @param thriftBean must not be null
     * @see {@link ThriftConverter#converterLogLightBean}
     */
    public LogLightBean(net.gdface.facelog.client.thrift.LogLightBean thriftBean){
        if(null != thriftBean){
            throw new NullPointerException();
        }
        reset();
        ThriftConverter.CONVERTER_LOGLIGHTBEAN.fromRight(this,thriftBean);
    }
}
