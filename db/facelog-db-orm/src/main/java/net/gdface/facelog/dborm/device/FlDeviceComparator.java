// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: comparator.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm.device;

import java.util.Comparator;
import net.gdface.facelog.dborm.Constant;


/**
 * Comparator class is used to sort the FlDeviceBean objects.
 * @author sql2java
 */
public class FlDeviceComparator implements Comparator<FlDeviceBean>,Constant
{
    /**
     * Holds the field on which the comparison is performed.
     */
    private int iType;
    /**
     * Value that will contain the information about the order of the sort: normal or reversal.
     */
    private boolean bReverse;

    /**
     * Constructor class for FlDeviceComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new FlDeviceComparator(Constant.FL_DEVICE_ID_ID, bReverse));</code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_DEVICE_ID_ID}
     *   <li>{@link Constant#FL_DEVICE_ID_GROUP_ID}
     *   <li>{@link Constant#FL_DEVICE_ID_NAME}
     *   <li>{@link Constant#FL_DEVICE_ID_PRODUCT_NAME}
     *   <li>{@link Constant#FL_DEVICE_ID_MODEL}
     *   <li>{@link Constant#FL_DEVICE_ID_VENDOR}
     *   <li>{@link Constant#FL_DEVICE_ID_MANUFACTURER}
     *   <li>{@link Constant#FL_DEVICE_ID_MADE_DATE}
     *   <li>{@link Constant#FL_DEVICE_ID_VERSION}
     *   <li>{@link Constant#FL_DEVICE_ID_USED_SDKS}
     *   <li>{@link Constant#FL_DEVICE_ID_SERIAL_NO}
     *   <li>{@link Constant#FL_DEVICE_ID_MAC}
     *   <li>{@link Constant#FL_DEVICE_ID_DIRECTION}
     *   <li>{@link Constant#FL_DEVICE_ID_REMARK}
     *   <li>{@link Constant#FL_DEVICE_ID_EXT_BIN}
     *   <li>{@link Constant#FL_DEVICE_ID_EXT_TXT}
     *   <li>{@link Constant#FL_DEVICE_ID_CREATE_TIME}
     *   <li>{@link Constant#FL_DEVICE_ID_UPDATE_TIME}
     * </ul>
     */
    public FlDeviceComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for FlDeviceComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new FlDeviceComparator(Constant.FL_DEVICE_ID_ID, bReverse));</code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_DEVICE_ID_ID})
     *   <li>{@link Constant#FL_DEVICE_ID_GROUP_ID})
     *   <li>{@link Constant#FL_DEVICE_ID_NAME})
     *   <li>{@link Constant#FL_DEVICE_ID_PRODUCT_NAME})
     *   <li>{@link Constant#FL_DEVICE_ID_MODEL})
     *   <li>{@link Constant#FL_DEVICE_ID_VENDOR})
     *   <li>{@link Constant#FL_DEVICE_ID_MANUFACTURER})
     *   <li>{@link Constant#FL_DEVICE_ID_MADE_DATE})
     *   <li>{@link Constant#FL_DEVICE_ID_VERSION})
     *   <li>{@link Constant#FL_DEVICE_ID_USED_SDKS})
     *   <li>{@link Constant#FL_DEVICE_ID_SERIAL_NO})
     *   <li>{@link Constant#FL_DEVICE_ID_MAC})
     *   <li>{@link Constant#FL_DEVICE_ID_DIRECTION})
     *   <li>{@link Constant#FL_DEVICE_ID_REMARK})
     *   <li>{@link Constant#FL_DEVICE_ID_EXT_BIN})
     *   <li>{@link Constant#FL_DEVICE_ID_EXT_TXT})
     *   <li>{@link Constant#FL_DEVICE_ID_CREATE_TIME})
     *   <li>{@link Constant#FL_DEVICE_ID_UPDATE_TIME})
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public FlDeviceComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    @Override
    public int compare(FlDeviceBean b1, FlDeviceBean b2)
    {
        int iReturn = 0;
        switch(iType)
        {
            case FL_DEVICE_ID_ID:
                if (b1.getId() == null && b2.getId() != null) {
                    iReturn = -1;
                } else if (b1.getId() == null && b2.getId() == null) {
                    iReturn = 0;
                } else if (b1.getId() != null && b2.getId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getId().compareTo(b2.getId());
                }
                break;
            case FL_DEVICE_ID_GROUP_ID:
                if (b1.getGroupId() == null && b2.getGroupId() != null) {
                    iReturn = -1;
                } else if (b1.getGroupId() == null && b2.getGroupId() == null) {
                    iReturn = 0;
                } else if (b1.getGroupId() != null && b2.getGroupId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getGroupId().compareTo(b2.getGroupId());
                }
                break;
            case FL_DEVICE_ID_NAME:
                if (b1.getName() == null && b2.getName() != null) {
                    iReturn = -1;
                } else if (b1.getName() == null && b2.getName() == null) {
                    iReturn = 0;
                } else if (b1.getName() != null && b2.getName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getName().compareTo(b2.getName());
                }
                break;
            case FL_DEVICE_ID_PRODUCT_NAME:
                if (b1.getProductName() == null && b2.getProductName() != null) {
                    iReturn = -1;
                } else if (b1.getProductName() == null && b2.getProductName() == null) {
                    iReturn = 0;
                } else if (b1.getProductName() != null && b2.getProductName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getProductName().compareTo(b2.getProductName());
                }
                break;
            case FL_DEVICE_ID_MODEL:
                if (b1.getModel() == null && b2.getModel() != null) {
                    iReturn = -1;
                } else if (b1.getModel() == null && b2.getModel() == null) {
                    iReturn = 0;
                } else if (b1.getModel() != null && b2.getModel() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getModel().compareTo(b2.getModel());
                }
                break;
            case FL_DEVICE_ID_VENDOR:
                if (b1.getVendor() == null && b2.getVendor() != null) {
                    iReturn = -1;
                } else if (b1.getVendor() == null && b2.getVendor() == null) {
                    iReturn = 0;
                } else if (b1.getVendor() != null && b2.getVendor() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getVendor().compareTo(b2.getVendor());
                }
                break;
            case FL_DEVICE_ID_MANUFACTURER:
                if (b1.getManufacturer() == null && b2.getManufacturer() != null) {
                    iReturn = -1;
                } else if (b1.getManufacturer() == null && b2.getManufacturer() == null) {
                    iReturn = 0;
                } else if (b1.getManufacturer() != null && b2.getManufacturer() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getManufacturer().compareTo(b2.getManufacturer());
                }
                break;
            case FL_DEVICE_ID_MADE_DATE:
                if (b1.getMadeDate() == null && b2.getMadeDate() != null) {
                    iReturn = -1;
                } else if (b1.getMadeDate() == null && b2.getMadeDate() == null) {
                    iReturn = 0;
                } else if (b1.getMadeDate() != null && b2.getMadeDate() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getMadeDate().compareTo(b2.getMadeDate());
                }
                break;
            case FL_DEVICE_ID_VERSION:
                if (b1.getVersion() == null && b2.getVersion() != null) {
                    iReturn = -1;
                } else if (b1.getVersion() == null && b2.getVersion() == null) {
                    iReturn = 0;
                } else if (b1.getVersion() != null && b2.getVersion() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getVersion().compareTo(b2.getVersion());
                }
                break;
            case FL_DEVICE_ID_USED_SDKS:
                if (b1.getUsedSdks() == null && b2.getUsedSdks() != null) {
                    iReturn = -1;
                } else if (b1.getUsedSdks() == null && b2.getUsedSdks() == null) {
                    iReturn = 0;
                } else if (b1.getUsedSdks() != null && b2.getUsedSdks() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getUsedSdks().compareTo(b2.getUsedSdks());
                }
                break;
            case FL_DEVICE_ID_SERIAL_NO:
                if (b1.getSerialNo() == null && b2.getSerialNo() != null) {
                    iReturn = -1;
                } else if (b1.getSerialNo() == null && b2.getSerialNo() == null) {
                    iReturn = 0;
                } else if (b1.getSerialNo() != null && b2.getSerialNo() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getSerialNo().compareTo(b2.getSerialNo());
                }
                break;
            case FL_DEVICE_ID_MAC:
                if (b1.getMac() == null && b2.getMac() != null) {
                    iReturn = -1;
                } else if (b1.getMac() == null && b2.getMac() == null) {
                    iReturn = 0;
                } else if (b1.getMac() != null && b2.getMac() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getMac().compareTo(b2.getMac());
                }
                break;
            case FL_DEVICE_ID_DIRECTION:
                if (b1.getDirection() == null && b2.getDirection() != null) {
                    iReturn = -1;
                } else if (b1.getDirection() == null && b2.getDirection() == null) {
                    iReturn = 0;
                } else if (b1.getDirection() != null && b2.getDirection() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getDirection().compareTo(b2.getDirection());
                }
                break;
            case FL_DEVICE_ID_REMARK:
                if (b1.getRemark() == null && b2.getRemark() != null) {
                    iReturn = -1;
                } else if (b1.getRemark() == null && b2.getRemark() == null) {
                    iReturn = 0;
                } else if (b1.getRemark() != null && b2.getRemark() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getRemark().compareTo(b2.getRemark());
                }
                break;
            case FL_DEVICE_ID_EXT_BIN:
                if (b1.getExtBin() == null && b2.getExtBin() != null) {
                    iReturn = -1;
                } else if (b1.getExtBin() == null && b2.getExtBin() == null) {
                    iReturn = 0;
                } else if (b1.getExtBin() != null && b2.getExtBin() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getExtBin().compareTo(b2.getExtBin());
                }
                break;
            case FL_DEVICE_ID_EXT_TXT:
                if (b1.getExtTxt() == null && b2.getExtTxt() != null) {
                    iReturn = -1;
                } else if (b1.getExtTxt() == null && b2.getExtTxt() == null) {
                    iReturn = 0;
                } else if (b1.getExtTxt() != null && b2.getExtTxt() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getExtTxt().compareTo(b2.getExtTxt());
                }
                break;
            case FL_DEVICE_ID_CREATE_TIME:
                if (b1.getCreateTime() == null && b2.getCreateTime() != null) {
                    iReturn = -1;
                } else if (b1.getCreateTime() == null && b2.getCreateTime() == null) {
                    iReturn = 0;
                } else if (b1.getCreateTime() != null && b2.getCreateTime() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getCreateTime().compareTo(b2.getCreateTime());
                }
                break;
            case FL_DEVICE_ID_UPDATE_TIME:
                if (b1.getUpdateTime() == null && b2.getUpdateTime() != null) {
                    iReturn = -1;
                } else if (b1.getUpdateTime() == null && b2.getUpdateTime() == null) {
                    iReturn = 0;
                } else if (b1.getUpdateTime() != null && b2.getUpdateTime() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getUpdateTime().compareTo(b2.getUpdateTime());
                }
                break;
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}
