// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.dborm.person;

import java.util.Comparator;
import net.gdface.facelog.dborm.Constant;


/**
 * Comparator class is used to sort the FlPersonBean objects.
 * @author sql2java
 */
public class FlPersonComparator implements Comparator<FlPersonBean>,Constant
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
     * Constructor class for FlPersonComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new FlPersonComparator(Constant.FL_PERSON_ID_ID, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_PERSON_ID_ID}
     *   <li>{@link Constant#FL_PERSON_ID_GROUP_ID}
     *   <li>{@link Constant#FL_PERSON_ID_NAME}
     *   <li>{@link Constant#FL_PERSON_ID_SEX}
     *   <li>{@link Constant#FL_PERSON_ID_BIRTHDATE}
     *   <li>{@link Constant#FL_PERSON_ID_PAPERS_TYPE}
     *   <li>{@link Constant#FL_PERSON_ID_PAPERS_NUM}
     *   <li>{@link Constant#FL_PERSON_ID_IMAGE_MD5}
     *   <li>{@link Constant#FL_PERSON_ID_FEATURE_MD5}
     *   <li>{@link Constant#FL_PERSON_ID_EXPIRY_DATE}
     *   <li>{@link Constant#FL_PERSON_ID_CREATE_TIME}
     *   <li>{@link Constant#FL_PERSON_ID_UPDATE_TIME}
     * </ul>
     */
    public FlPersonComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for FlPersonComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new FlPersonComparator(Constant.FL_PERSON_ID_ID, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_PERSON_ID_ID})
     *   <li>{@link Constant#FL_PERSON_ID_GROUP_ID})
     *   <li>{@link Constant#FL_PERSON_ID_NAME})
     *   <li>{@link Constant#FL_PERSON_ID_SEX})
     *   <li>{@link Constant#FL_PERSON_ID_BIRTHDATE})
     *   <li>{@link Constant#FL_PERSON_ID_PAPERS_TYPE})
     *   <li>{@link Constant#FL_PERSON_ID_PAPERS_NUM})
     *   <li>{@link Constant#FL_PERSON_ID_IMAGE_MD5})
     *   <li>{@link Constant#FL_PERSON_ID_FEATURE_MD5})
     *   <li>{@link Constant#FL_PERSON_ID_EXPIRY_DATE})
     *   <li>{@link Constant#FL_PERSON_ID_CREATE_TIME})
     *   <li>{@link Constant#FL_PERSON_ID_UPDATE_TIME})
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public FlPersonComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    @Override
    public int compare(FlPersonBean b1, FlPersonBean b2)
    {
        int iReturn = 0;
        switch(iType)
        {
            case FL_PERSON_ID_ID:
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
            case FL_PERSON_ID_GROUP_ID:
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
            case FL_PERSON_ID_NAME:
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
            case FL_PERSON_ID_SEX:
                if (b1.getSex() == null && b2.getSex() != null) {
                    iReturn = -1;
                } else if (b1.getSex() == null && b2.getSex() == null) {
                    iReturn = 0;
                } else if (b1.getSex() != null && b2.getSex() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getSex().compareTo(b2.getSex());
                }
                break;
            case FL_PERSON_ID_BIRTHDATE:
                if (b1.getBirthdate() == null && b2.getBirthdate() != null) {
                    iReturn = -1;
                } else if (b1.getBirthdate() == null && b2.getBirthdate() == null) {
                    iReturn = 0;
                } else if (b1.getBirthdate() != null && b2.getBirthdate() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getBirthdate().compareTo(b2.getBirthdate());
                }
                break;
            case FL_PERSON_ID_PAPERS_TYPE:
                if (b1.getPapersType() == null && b2.getPapersType() != null) {
                    iReturn = -1;
                } else if (b1.getPapersType() == null && b2.getPapersType() == null) {
                    iReturn = 0;
                } else if (b1.getPapersType() != null && b2.getPapersType() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getPapersType().compareTo(b2.getPapersType());
                }
                break;
            case FL_PERSON_ID_PAPERS_NUM:
                if (b1.getPapersNum() == null && b2.getPapersNum() != null) {
                    iReturn = -1;
                } else if (b1.getPapersNum() == null && b2.getPapersNum() == null) {
                    iReturn = 0;
                } else if (b1.getPapersNum() != null && b2.getPapersNum() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getPapersNum().compareTo(b2.getPapersNum());
                }
                break;
            case FL_PERSON_ID_IMAGE_MD5:
                if (b1.getImageMd5() == null && b2.getImageMd5() != null) {
                    iReturn = -1;
                } else if (b1.getImageMd5() == null && b2.getImageMd5() == null) {
                    iReturn = 0;
                } else if (b1.getImageMd5() != null && b2.getImageMd5() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getImageMd5().compareTo(b2.getImageMd5());
                }
                break;
            case FL_PERSON_ID_FEATURE_MD5:
                if (b1.getFeatureMd5() == null && b2.getFeatureMd5() != null) {
                    iReturn = -1;
                } else if (b1.getFeatureMd5() == null && b2.getFeatureMd5() == null) {
                    iReturn = 0;
                } else if (b1.getFeatureMd5() != null && b2.getFeatureMd5() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getFeatureMd5().compareTo(b2.getFeatureMd5());
                }
                break;
            case FL_PERSON_ID_EXPIRY_DATE:
                if (b1.getExpiryDate() == null && b2.getExpiryDate() != null) {
                    iReturn = -1;
                } else if (b1.getExpiryDate() == null && b2.getExpiryDate() == null) {
                    iReturn = 0;
                } else if (b1.getExpiryDate() != null && b2.getExpiryDate() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getExpiryDate().compareTo(b2.getExpiryDate());
                }
                break;
            case FL_PERSON_ID_CREATE_TIME:
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
            case FL_PERSON_ID_UPDATE_TIME:
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
