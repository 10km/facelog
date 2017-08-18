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

package net.gdface.facelog.dborm.image;

import net.gdface.facelog.dborm.FullBean;

/**
 * FlImageBean is a mapping of fl_image Table.
 * @author sql2java
*/
public class FlImageBean
    extends FlImageBeanBase
    implements FullBean<FlImageBeanBase>
{
	private static final long serialVersionUID = -259266274543182315L;
	
    private boolean deviceIdIsModified = false;
    private boolean deviceIdIsInitialized = false;

    private boolean thumbMd5IsModified = false;
    private boolean thumbMd5IsInitialized = false;

    private boolean faceNumIsModified = false;
    private boolean faceNumIsInitialized = false;

    private boolean depthIsModified = false;
    private boolean depthIsInitialized = false;

    private boolean heightIsModified = false;
    private boolean heightIsInitialized = false;

    private boolean widthIsModified = false;
    private boolean widthIsInitialized = false;

    private boolean formatIsModified = false;
    private boolean formatIsInitialized = false;

    private boolean md5IsModified = false;
    private boolean md5IsInitialized = false;



    /**
     * Prefered methods to create a FlImageBean is via the createFlImageBean method in FlImageManager or
     * via the factory class FlImageFactory create method
     * 为了能在webservice中传递对象，此处从protected改为public
     */
    public FlImageBean(){
        super();
    }
    /**
     * create a FlImageBean from a instance
     */
    FlImageBean(FlImageBeanBase bean){
        super();
        copy(bean);
    }
    /**
     * Getter method for deviceId.
     * <br>
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
     * Setter method for deviceId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to deviceId
     */
    public void setDeviceId(Integer newVal)
    {
        if ((newVal != null && deviceId != null && (newVal.compareTo(deviceId) == 0)) ||
            (newVal == null && deviceId == null && deviceIdIsInitialized)) {
            return;
        }
        super.setDeviceId(newVal);
        deviceIdIsModified = true;
        deviceIdIsInitialized = true;
    }

    /**
     * Setter method for deviceId.
     * <br>
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
    public boolean isDeviceIdModified()
    {
        return deviceIdIsModified;
    }

    /**
     * Determines if the deviceId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isDeviceIdInitialized()
    {
        return deviceIdIsInitialized;
    }

    /**
     * Getter method for thumbMd5.
     * <br>
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
     * Setter method for thumbMd5.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to thumbMd5
     */
    public void setThumbMd5(String newVal)
    {
        if ((newVal != null && thumbMd5 != null && (newVal.compareTo(thumbMd5) == 0)) ||
            (newVal == null && thumbMd5 == null && thumbMd5IsInitialized)) {
            return;
        }
        super.setThumbMd5(newVal);
        thumbMd5IsModified = true;
        thumbMd5IsInitialized = true;
    }

    /**
     * Determines if the thumbMd5 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isThumbMd5Modified()
    {
        return thumbMd5IsModified;
    }

    /**
     * Determines if the thumbMd5 has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isThumbMd5Initialized()
    {
        return thumbMd5IsInitialized;
    }

    /**
     * Getter method for faceNum.
     * <br>
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
     * Setter method for faceNum.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to faceNum
     */
    public void setFaceNum(Integer newVal)
    {
        if ((newVal != null && faceNum != null && (newVal.compareTo(faceNum) == 0)) ||
            (newVal == null && faceNum == null && faceNumIsInitialized)) {
            return;
        }
        super.setFaceNum(newVal);
        faceNumIsModified = true;
        faceNumIsInitialized = true;
    }

    /**
     * Setter method for faceNum.
     * <br>
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
    public boolean isFaceNumModified()
    {
        return faceNumIsModified;
    }

    /**
     * Determines if the faceNum has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isFaceNumInitialized()
    {
        return faceNumIsInitialized;
    }

    /**
     * Getter method for depth.
     * <br>
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
     * Setter method for depth.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to depth
     */
    public void setDepth(Integer newVal)
    {
        if ((newVal != null && depth != null && (newVal.compareTo(depth) == 0)) ||
            (newVal == null && depth == null && depthIsInitialized)) {
            return;
        }
        super.setDepth(newVal);
        depthIsModified = true;
        depthIsInitialized = true;
    }

    /**
     * Setter method for depth.
     * <br>
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
    public boolean isDepthModified()
    {
        return depthIsModified;
    }

    /**
     * Determines if the depth has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isDepthInitialized()
    {
        return depthIsInitialized;
    }

    /**
     * Getter method for height.
     * <br>
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
     * Setter method for height.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to height
     */
    public void setHeight(Integer newVal)
    {
        if ((newVal != null && height != null && (newVal.compareTo(height) == 0)) ||
            (newVal == null && height == null && heightIsInitialized)) {
            return;
        }
        super.setHeight(newVal);
        heightIsModified = true;
        heightIsInitialized = true;
    }

    /**
     * Setter method for height.
     * <br>
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
    public boolean isHeightModified()
    {
        return heightIsModified;
    }

    /**
     * Determines if the height has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isHeightInitialized()
    {
        return heightIsInitialized;
    }

    /**
     * Getter method for width.
     * <br>
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
     * Setter method for width.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to width
     */
    public void setWidth(Integer newVal)
    {
        if ((newVal != null && width != null && (newVal.compareTo(width) == 0)) ||
            (newVal == null && width == null && widthIsInitialized)) {
            return;
        }
        super.setWidth(newVal);
        widthIsModified = true;
        widthIsInitialized = true;
    }

    /**
     * Setter method for width.
     * <br>
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
    public boolean isWidthModified()
    {
        return widthIsModified;
    }

    /**
     * Determines if the width has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isWidthInitialized()
    {
        return widthIsInitialized;
    }

    /**
     * Getter method for format.
     * <br>
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
     * Setter method for format.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to format
     */
    public void setFormat(String newVal)
    {
        if ((newVal != null && format != null && (newVal.compareTo(format) == 0)) ||
            (newVal == null && format == null && formatIsInitialized)) {
            return;
        }
        super.setFormat(newVal);
        formatIsModified = true;
        formatIsInitialized = true;
    }

    /**
     * Determines if the format has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isFormatModified()
    {
        return formatIsModified;
    }

    /**
     * Determines if the format has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isFormatInitialized()
    {
        return formatIsInitialized;
    }

    /**
     * Getter method for md5.
     * <br>
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
        return deviceIdIsModified 		|| thumbMd5IsModified  		|| faceNumIsModified  		|| depthIsModified  		|| heightIsModified  		|| widthIsModified  		|| formatIsModified  		|| md5IsModified  ;
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
        } else if ("device_id".equalsIgnoreCase(column) || "deviceId".equalsIgnoreCase(column)) {
            return isDeviceIdModified();
        } else if ("thumb_md5".equalsIgnoreCase(column) || "thumbMd5".equalsIgnoreCase(column)) {
            return isThumbMd5Modified();
        } else if ("face_num".equalsIgnoreCase(column) || "faceNum".equalsIgnoreCase(column)) {
            return isFaceNumModified();
        } else if ("depth".equalsIgnoreCase(column) || "depth".equalsIgnoreCase(column)) {
            return isDepthModified();
        } else if ("height".equalsIgnoreCase(column) || "height".equalsIgnoreCase(column)) {
            return isHeightModified();
        } else if ("width".equalsIgnoreCase(column) || "width".equalsIgnoreCase(column)) {
            return isWidthModified();
        } else if ("format".equalsIgnoreCase(column) || "format".equalsIgnoreCase(column)) {
            return isFormatModified();
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
        } else if ("device_id".equalsIgnoreCase(column) || "deviceId".equalsIgnoreCase(column)) {
            return isDeviceIdInitialized();
        } else if ("thumb_md5".equalsIgnoreCase(column) || "thumbMd5".equalsIgnoreCase(column)) {
            return isThumbMd5Initialized();
        } else if ("face_num".equalsIgnoreCase(column) || "faceNum".equalsIgnoreCase(column)) {
            return isFaceNumInitialized();
        } else if ("depth".equalsIgnoreCase(column) || "depth".equalsIgnoreCase(column)) {
            return isDepthInitialized();
        } else if ("height".equalsIgnoreCase(column) || "height".equalsIgnoreCase(column)) {
            return isHeightInitialized();
        } else if ("width".equalsIgnoreCase(column) || "width".equalsIgnoreCase(column)) {
            return isWidthInitialized();
        } else if ("format".equalsIgnoreCase(column) || "format".equalsIgnoreCase(column)) {
            return isFormatInitialized();
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
        deviceIdIsModified = false;
        thumbMd5IsModified = false;
        faceNumIsModified = false;
        depthIsModified = false;
        heightIsModified = false;
        widthIsModified = false;
        formatIsModified = false;
        md5IsModified = false;
    }

    /**
     * set all field to null and reset all modification status
     * @see #resetIsModified() 
     */
    public FlImageBean clean()
    {
        super.clean();
        resetIsModified();
        return this;
    }

}
