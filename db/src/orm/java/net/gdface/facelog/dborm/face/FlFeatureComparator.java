// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.dborm.face;

import java.util.Comparator;
import net.gdface.facelog.dborm.Constant;


/**
 * Comparator class is used to sort the FlFeatureBean objects.
 * @author sql2java
 */
public class FlFeatureComparator implements Comparator<FlFeatureBean>,Constant
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
     * Constructor class for FlFeatureComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new FlFeatureComparator(Constant.FL_FEATURE_ID_MD5, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_FEATURE_ID_MD5}
     *   <li>{@link Constant#FL_FEATURE_ID_PERSON_ID}
     *   <li>{@link Constant#FL_FEATURE_ID_FEATURE}
     *   <li>{@link Constant#FL_FEATURE_ID_CREATE_TIME}
     * </ul>
     */
    public FlFeatureComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for FlFeatureComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new FlFeatureComparator(Constant.FL_FEATURE_ID_MD5, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_FEATURE_ID_MD5})
     *   <li>{@link Constant#FL_FEATURE_ID_PERSON_ID})
     *   <li>{@link Constant#FL_FEATURE_ID_FEATURE})
     *   <li>{@link Constant#FL_FEATURE_ID_CREATE_TIME})
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public FlFeatureComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    @Override
    public int compare(FlFeatureBean b1, FlFeatureBean b2)
    {
        int iReturn = 0;
        switch(iType)
        {
            case FL_FEATURE_ID_MD5:
                if (b1.getMd5() == null && b2.getMd5() != null) {
                    iReturn = -1;
                } else if (b1.getMd5() == null && b2.getMd5() == null) {
                    iReturn = 0;
                } else if (b1.getMd5() != null && b2.getMd5() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getMd5().compareTo(b2.getMd5());
                }
                break;
            case FL_FEATURE_ID_PERSON_ID:
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
            case FL_FEATURE_ID_FEATURE:
                if (b1.getFeature() == null && b2.getFeature() != null) {
                    iReturn = -1;
                } else if (b1.getFeature() == null && b2.getFeature() == null) {
                    iReturn = 0;
                } else if (b1.getFeature() != null && b2.getFeature() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getFeature().compareTo(b2.getFeature());
                }
                break;
            case FL_FEATURE_ID_CREATE_TIME:
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
