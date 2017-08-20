// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.dborm.face;

import net.gdface.facelog.dborm.FullBean;

/**
 * FlFeatureBean is a mapping of fl_feature Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: VIEW </li>
 * </ul>
 * @author sql2java
*/
public class FlFeatureBean
    extends FlFeatureBeanBase
    implements FullBean<FlFeatureBeanBase>
{
	private static final long serialVersionUID = -9030529272695775391L;
	
    private boolean createTimeIsModified = false;
    private boolean createTimeIsInitialized = false;

    private boolean featureIsModified = false;
    private boolean featureIsInitialized = false;

    private boolean imgMd5IsModified = false;
    private boolean imgMd5IsInitialized = false;

    private boolean personIdIsModified = false;
    private boolean personIdIsInitialized = false;

    private boolean md5IsModified = false;
    private boolean md5IsInitialized = false;



    /**
     * Prefered methods to create a FlFeatureBean is via the createFlFeatureBean method in FlFeatureManager or
     * via the factory class FlFeatureFactory create method
     * 为了能在webservice中传递对象，此处从protected改为public
     */
    public FlFeatureBean(){
        super();
    }
    /**
     * create a FlFeatureBean from a instance
     */
    FlFeatureBean(FlFeatureBeanBase bean){
        super();
        copy(bean);
    }
    /**
     * Getter method for createTime.
     * <br>
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
     * Setter method for createTime.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to createTime
     */
    public void setCreateTime(java.util.Date newVal)
    {
        if ((newVal != null && createTime != null && (newVal.compareTo(createTime) == 0)) ||
            (newVal == null && createTime == null && createTimeIsInitialized)) {
            return;
        }
        super.setCreateTime(newVal);
        createTimeIsModified = true;
        createTimeIsInitialized = true;
    }

    /**
     * Setter method for createTime.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to createTime
     */
    public void setCreateTime(long newVal)
    {
        setCreateTime(new java.util.Date(newVal));
    }

    /**
     * Determines if the createTime has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isCreateTimeModified()
    {
        return createTimeIsModified;
    }

    /**
     * Determines if the createTime has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isCreateTimeInitialized()
    {
        return createTimeIsInitialized;
    }

    /**
     * Getter method for feature.
     * <br>
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
     * Setter method for feature.
     * <br>
     * Attention, there will be no comparison with current value which
     * means calling this method will mark the field as 'modified' in all cases.
     *
     * @param newVal the new value to be assigned to feature
     */
    public void setFeature(byte[] newVal)
    {
        super.setFeature(newVal);
        featureIsModified = true;
        featureIsInitialized = true;
    }

    /**
     * Determines if the feature has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isFeatureModified()
    {
        return featureIsModified;
    }

    /**
     * Determines if the feature has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isFeatureInitialized()
    {
        return featureIsInitialized;
    }

    /**
     * Getter method for imgMd5.
     * <br>
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
     * Setter method for imgMd5.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to imgMd5
     */
    public void setImgMd5(String newVal)
    {
        if ((newVal != null && imgMd5 != null && (newVal.compareTo(imgMd5) == 0)) ||
            (newVal == null && imgMd5 == null && imgMd5IsInitialized)) {
            return;
        }
        super.setImgMd5(newVal);
        imgMd5IsModified = true;
        imgMd5IsInitialized = true;
    }

    /**
     * Determines if the imgMd5 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isImgMd5Modified()
    {
        return imgMd5IsModified;
    }

    /**
     * Determines if the imgMd5 has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isImgMd5Initialized()
    {
        return imgMd5IsInitialized;
    }

    /**
     * Getter method for personId.
     * <br>
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
     * Setter method for personId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to personId
     */
    public void setPersonId(Integer newVal)
    {
        if ((newVal != null && personId != null && (newVal.compareTo(personId) == 0)) ||
            (newVal == null && personId == null && personIdIsInitialized)) {
            return;
        }
        super.setPersonId(newVal);
        personIdIsModified = true;
        personIdIsInitialized = true;
    }

    /**
     * Setter method for personId.
     * <br>
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
    public boolean isPersonIdModified()
    {
        return personIdIsModified;
    }

    /**
     * Determines if the personId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isPersonIdInitialized()
    {
        return personIdIsInitialized;
    }

    /**
     * Getter method for md5.
     * <br>
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
     * Setter method for md5.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to md5
     */
    public void setMd5(String newVal)
    {
        if ((newVal != null && md5 != null && (newVal.compareTo(md5) == 0)) ||
            (newVal == null && md5 == null && md5IsInitialized)) {
            return;
        }
        super.setMd5(newVal);
        md5IsModified = true;
        md5IsInitialized = true;
    }

    /**
     * Determines if the md5 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isMd5Modified()
    {
        return md5IsModified;
    }

    /**
     * Determines if the md5 has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isMd5Initialized()
    {
        return md5IsInitialized;
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
        return createTimeIsModified 		|| featureIsModified  		|| imgMd5IsModified  		|| personIdIsModified  		|| md5IsModified  ;
    }
    
    /**
     * Determines if the {@code column} has been modified.
     * @param column
     * @return true if the field has been modified, false if the field has not been modified
     * @author guyadong
     */
    public boolean isModified(String column){
        if (null == column || "".equals(column)) {
            return false;
        } else if ("create_time".equalsIgnoreCase(column) || "createTime".equalsIgnoreCase(column)) {
            return isCreateTimeModified();
        } else if ("feature".equalsIgnoreCase(column) || "feature".equalsIgnoreCase(column)) {
            return isFeatureModified();
        } else if ("img_md5".equalsIgnoreCase(column) || "imgMd5".equalsIgnoreCase(column)) {
            return isImgMd5Modified();
        } else if ("person_id".equalsIgnoreCase(column) || "personId".equalsIgnoreCase(column)) {
            return isPersonIdModified();
        } else if ("md5".equalsIgnoreCase(column) || "md5".equalsIgnoreCase(column)) {
            return isMd5Modified();
        }
        return false;		
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
        if (null == column || "".equals(column)) {
            return false;
        } else if ("create_time".equalsIgnoreCase(column) || "createTime".equalsIgnoreCase(column)) {
            return isCreateTimeInitialized();
        } else if ("feature".equalsIgnoreCase(column) || "feature".equalsIgnoreCase(column)) {
            return isFeatureInitialized();
        } else if ("img_md5".equalsIgnoreCase(column) || "imgMd5".equalsIgnoreCase(column)) {
            return isImgMd5Initialized();
        } else if ("person_id".equalsIgnoreCase(column) || "personId".equalsIgnoreCase(column)) {
            return isPersonIdInitialized();
        } else if ("md5".equalsIgnoreCase(column) || "md5".equalsIgnoreCase(column)) {
            return isMd5Initialized();
        }
        return false;		
    }
    
    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified()
    {
        createTimeIsModified = false;
        featureIsModified = false;
        imgMd5IsModified = false;
        personIdIsModified = false;
        md5IsModified = false;
    }

    /**
     * set all field to null and reset all modification status
     * @see #resetIsModified() 
     */
    public FlFeatureBean clean()
    {
        super.clean();
        resetIsModified();
        return this;
    }

}