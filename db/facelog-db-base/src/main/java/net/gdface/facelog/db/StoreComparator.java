// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: comparator.java.vm
// ______________________________________________________
package net.gdface.facelog.db;

import java.util.Comparator;


/**
 * Comparator class is used to sort the StoreBean objects.
 * @author sql2java
 */
public class StoreComparator implements Comparator<StoreBean>,Constant
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
     * Constructor class for StoreComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new StoreComparator(Constant.FL_STORE_ID_MD5, bReverse));</code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_STORE_ID_MD5}
     *   <li>{@link Constant#FL_STORE_ID_ENCODING}
     *   <li>{@link Constant#FL_STORE_ID_DATA}
     * </ul>
     */
    public StoreComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for StoreComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new StoreComparator(Constant.FL_STORE_ID_MD5, bReverse));</code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_STORE_ID_MD5})
     *   <li>{@link Constant#FL_STORE_ID_ENCODING})
     *   <li>{@link Constant#FL_STORE_ID_DATA})
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public StoreComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    @Override
    public int compare(StoreBean b1, StoreBean b2)
    {
        int iReturn = 0;
        switch(iType)
        {
            case FL_STORE_ID_MD5:
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
            case FL_STORE_ID_ENCODING:
                if (b1.getEncoding() == null && b2.getEncoding() != null) {
                    iReturn = -1;
                } else if (b1.getEncoding() == null && b2.getEncoding() == null) {
                    iReturn = 0;
                } else if (b1.getEncoding() != null && b2.getEncoding() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getEncoding().compareTo(b2.getEncoding());
                }
                break;
            case FL_STORE_ID_DATA:
                if (b1.getData() == null && b2.getData() != null) {
                    iReturn = -1;
                } else if (b1.getData() == null && b2.getData() == null) {
                    iReturn = 0;
                } else if (b1.getData() != null && b2.getData() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getData().compareTo(b2.getData());
                }
                break;
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}
