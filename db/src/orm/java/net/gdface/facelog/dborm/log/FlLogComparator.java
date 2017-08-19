// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.dborm.log;

import java.util.Comparator;


/**
 * Comparator class is used to sort the FlLogBean objects.
 * @author sql2java
 */
public class FlLogComparator implements Comparator<FlLogBean>
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
     * <code>Arrays.sort(pArray, new FlLogComparator(FlLogManager.ID_CREATE_TIME, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>FlLogManager.ID_CREATE_TIME
     *   <li>FlLogManager.ID_VERIFY_TIME
     *   <li>FlLogManager.ID_SIMILARTY
     *   <li>FlLogManager.ID_COMPARE_FACE
     *   <li>FlLogManager.ID_VERIFY_FACE
     *   <li>FlLogManager.ID_DEVICE_ID
     *   <li>FlLogManager.ID_PERSON_ID
     *   <li>FlLogManager.ID_ID
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
     * <code>Arrays.sort(pArray, new FlLogComparator(FlLogManager.ID_CREATE_TIME, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>FlLogManager.ID_CREATE_TIME
     *   <li>FlLogManager.ID_VERIFY_TIME
     *   <li>FlLogManager.ID_SIMILARTY
     *   <li>FlLogManager.ID_COMPARE_FACE
     *   <li>FlLogManager.ID_VERIFY_FACE
     *   <li>FlLogManager.ID_DEVICE_ID
     *   <li>FlLogManager.ID_PERSON_ID
     *   <li>FlLogManager.ID_ID
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public FlLogComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    /**
     * Implementation of the compare method.
     */
    @Override
    public int compare(FlLogBean b1, FlLogBean b2)
    {
        int iReturn = 0;
        switch(iType)
        {
            case FlLogManager.ID_CREATE_TIME:
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
            case FlLogManager.ID_VERIFY_TIME:
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
            case FlLogManager.ID_SIMILARTY:
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
            case FlLogManager.ID_COMPARE_FACE:
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
            case FlLogManager.ID_VERIFY_FACE:
                if (b1.getVerifyFace() == null && b2.getVerifyFace() != null) {
                    iReturn = -1;
                } else if (b1.getVerifyFace() == null && b2.getVerifyFace() == null) {
                    iReturn = 0;
                } else if (b1.getVerifyFace() != null && b2.getVerifyFace() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getVerifyFace().compareTo(b2.getVerifyFace());
                }
                break;
            case FlLogManager.ID_DEVICE_ID:
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
            case FlLogManager.ID_PERSON_ID:
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
            case FlLogManager.ID_ID:
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
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}
