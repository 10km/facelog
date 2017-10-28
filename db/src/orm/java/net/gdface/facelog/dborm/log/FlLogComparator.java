// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: comparator.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm.log;

import java.util.Comparator;
import net.gdface.facelog.dborm.Constant;


/**
 * Comparator class is used to sort the FlLogBean objects.
 * @author sql2java
 */
public class FlLogComparator implements Comparator<FlLogBean>,Constant
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
     * Constructor class for FlLogComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new FlLogComparator(Constant.FL_LOG_ID_ID, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_LOG_ID_ID}
     *   <li>{@link Constant#FL_LOG_ID_PERSON_ID}
     *   <li>{@link Constant#FL_LOG_ID_DEVICE_ID}
     *   <li>{@link Constant#FL_LOG_ID_VERIFY_FEATURE}
     *   <li>{@link Constant#FL_LOG_ID_COMPARE_FACE}
     *   <li>{@link Constant#FL_LOG_ID_SIMILARTY}
     *   <li>{@link Constant#FL_LOG_ID_VERIFY_TIME}
     *   <li>{@link Constant#FL_LOG_ID_CREATE_TIME}
     * </ul>
     */
    public FlLogComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for FlLogComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new FlLogComparator(Constant.FL_LOG_ID_ID, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_LOG_ID_ID})
     *   <li>{@link Constant#FL_LOG_ID_PERSON_ID})
     *   <li>{@link Constant#FL_LOG_ID_DEVICE_ID})
     *   <li>{@link Constant#FL_LOG_ID_VERIFY_FEATURE})
     *   <li>{@link Constant#FL_LOG_ID_COMPARE_FACE})
     *   <li>{@link Constant#FL_LOG_ID_SIMILARTY})
     *   <li>{@link Constant#FL_LOG_ID_VERIFY_TIME})
     *   <li>{@link Constant#FL_LOG_ID_CREATE_TIME})
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public FlLogComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    @Override
    public int compare(FlLogBean b1, FlLogBean b2)
    {
        int iReturn = 0;
        switch(iType)
        {
            case FL_LOG_ID_ID:
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
            case FL_LOG_ID_PERSON_ID:
                if (b1.getPersonId() == null && b2.getPersonId() != null) {
                    iReturn = -1;
                } else if (b1.getPersonId() == null && b2.getPersonId() == null) {
                    iReturn = 0;
                } else if (b1.getPersonId() != null && b2.getPersonId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getPersonId().compareTo(b2.getPersonId());
                }
                break;
            case FL_LOG_ID_DEVICE_ID:
                if (b1.getDeviceId() == null && b2.getDeviceId() != null) {
                    iReturn = -1;
                } else if (b1.getDeviceId() == null && b2.getDeviceId() == null) {
                    iReturn = 0;
                } else if (b1.getDeviceId() != null && b2.getDeviceId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getDeviceId().compareTo(b2.getDeviceId());
                }
                break;
            case FL_LOG_ID_VERIFY_FEATURE:
                if (b1.getVerifyFeature() == null && b2.getVerifyFeature() != null) {
                    iReturn = -1;
                } else if (b1.getVerifyFeature() == null && b2.getVerifyFeature() == null) {
                    iReturn = 0;
                } else if (b1.getVerifyFeature() != null && b2.getVerifyFeature() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getVerifyFeature().compareTo(b2.getVerifyFeature());
                }
                break;
            case FL_LOG_ID_COMPARE_FACE:
                if (b1.getCompareFace() == null && b2.getCompareFace() != null) {
                    iReturn = -1;
                } else if (b1.getCompareFace() == null && b2.getCompareFace() == null) {
                    iReturn = 0;
                } else if (b1.getCompareFace() != null && b2.getCompareFace() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getCompareFace().compareTo(b2.getCompareFace());
                }
                break;
            case FL_LOG_ID_SIMILARTY:
                if (b1.getSimilarty() == null && b2.getSimilarty() != null) {
                    iReturn = -1;
                } else if (b1.getSimilarty() == null && b2.getSimilarty() == null) {
                    iReturn = 0;
                } else if (b1.getSimilarty() != null && b2.getSimilarty() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getSimilarty().compareTo(b2.getSimilarty());
                }
                break;
            case FL_LOG_ID_VERIFY_TIME:
                if (b1.getVerifyTime() == null && b2.getVerifyTime() != null) {
                    iReturn = -1;
                } else if (b1.getVerifyTime() == null && b2.getVerifyTime() == null) {
                    iReturn = 0;
                } else if (b1.getVerifyTime() != null && b2.getVerifyTime() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getVerifyTime().compareTo(b2.getVerifyTime());
                }
                break;
            case FL_LOG_ID_CREATE_TIME:
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
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}
