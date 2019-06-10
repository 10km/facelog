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
 * Comparator class is used to sort the ImageBean objects.
 * @author sql2java
 */
public class ImageComparator implements Comparator<ImageBean>,Constant
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
     * Constructor class for ImageComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new ImageComparator(Constant.FL_IMAGE_ID_MD5, bReverse));</code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_IMAGE_ID_MD5}
     *   <li>{@link Constant#FL_IMAGE_ID_FORMAT}
     *   <li>{@link Constant#FL_IMAGE_ID_WIDTH}
     *   <li>{@link Constant#FL_IMAGE_ID_HEIGHT}
     *   <li>{@link Constant#FL_IMAGE_ID_DEPTH}
     *   <li>{@link Constant#FL_IMAGE_ID_FACE_NUM}
     *   <li>{@link Constant#FL_IMAGE_ID_THUMB_MD5}
     *   <li>{@link Constant#FL_IMAGE_ID_DEVICE_ID}
     * </ul>
     */
    public ImageComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for ImageComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new ImageComparator(Constant.FL_IMAGE_ID_MD5, bReverse));</code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_IMAGE_ID_MD5})
     *   <li>{@link Constant#FL_IMAGE_ID_FORMAT})
     *   <li>{@link Constant#FL_IMAGE_ID_WIDTH})
     *   <li>{@link Constant#FL_IMAGE_ID_HEIGHT})
     *   <li>{@link Constant#FL_IMAGE_ID_DEPTH})
     *   <li>{@link Constant#FL_IMAGE_ID_FACE_NUM})
     *   <li>{@link Constant#FL_IMAGE_ID_THUMB_MD5})
     *   <li>{@link Constant#FL_IMAGE_ID_DEVICE_ID})
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public ImageComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    @Override
    public int compare(ImageBean b1, ImageBean b2)
    {
        int iReturn = 0;
        switch(iType)
        {
            case FL_IMAGE_ID_MD5:
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
            case FL_IMAGE_ID_FORMAT:
                if (b1.getFormat() == null && b2.getFormat() != null) {
                    iReturn = -1;
                } else if (b1.getFormat() == null && b2.getFormat() == null) {
                    iReturn = 0;
                } else if (b1.getFormat() != null && b2.getFormat() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getFormat().compareTo(b2.getFormat());
                }
                break;
            case FL_IMAGE_ID_WIDTH:
                if (b1.getWidth() == null && b2.getWidth() != null) {
                    iReturn = -1;
                } else if (b1.getWidth() == null && b2.getWidth() == null) {
                    iReturn = 0;
                } else if (b1.getWidth() != null && b2.getWidth() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getWidth().compareTo(b2.getWidth());
                }
                break;
            case FL_IMAGE_ID_HEIGHT:
                if (b1.getHeight() == null && b2.getHeight() != null) {
                    iReturn = -1;
                } else if (b1.getHeight() == null && b2.getHeight() == null) {
                    iReturn = 0;
                } else if (b1.getHeight() != null && b2.getHeight() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getHeight().compareTo(b2.getHeight());
                }
                break;
            case FL_IMAGE_ID_DEPTH:
                if (b1.getDepth() == null && b2.getDepth() != null) {
                    iReturn = -1;
                } else if (b1.getDepth() == null && b2.getDepth() == null) {
                    iReturn = 0;
                } else if (b1.getDepth() != null && b2.getDepth() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getDepth().compareTo(b2.getDepth());
                }
                break;
            case FL_IMAGE_ID_FACE_NUM:
                if (b1.getFaceNum() == null && b2.getFaceNum() != null) {
                    iReturn = -1;
                } else if (b1.getFaceNum() == null && b2.getFaceNum() == null) {
                    iReturn = 0;
                } else if (b1.getFaceNum() != null && b2.getFaceNum() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getFaceNum().compareTo(b2.getFaceNum());
                }
                break;
            case FL_IMAGE_ID_THUMB_MD5:
                if (b1.getThumbMd5() == null && b2.getThumbMd5() != null) {
                    iReturn = -1;
                } else if (b1.getThumbMd5() == null && b2.getThumbMd5() == null) {
                    iReturn = 0;
                } else if (b1.getThumbMd5() != null && b2.getThumbMd5() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getThumbMd5().compareTo(b2.getThumbMd5());
                }
                break;
            case FL_IMAGE_ID_DEVICE_ID:
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
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}
