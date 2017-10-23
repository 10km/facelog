// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// template: comparator.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm.face;

import java.util.Comparator;
import net.gdface.facelog.dborm.Constant;


/**
 * Comparator class is used to sort the FlFaceBean objects.
 * @author sql2java
 */
public class FlFaceComparator implements Comparator<FlFaceBean>,Constant
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
     * Constructor class for FlFaceComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new FlFaceComparator(Constant.FL_FACE_ID_ID, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_FACE_ID_ID}
     *   <li>{@link Constant#FL_FACE_ID_IMAGE_MD5}
     *   <li>{@link Constant#FL_FACE_ID_FACE_LEFT}
     *   <li>{@link Constant#FL_FACE_ID_FACE_TOP}
     *   <li>{@link Constant#FL_FACE_ID_FACE_WIDTH}
     *   <li>{@link Constant#FL_FACE_ID_FACE_HEIGHT}
     *   <li>{@link Constant#FL_FACE_ID_EYE_LEFTX}
     *   <li>{@link Constant#FL_FACE_ID_EYE_LEFTY}
     *   <li>{@link Constant#FL_FACE_ID_EYE_RIGHTX}
     *   <li>{@link Constant#FL_FACE_ID_EYE_RIGHTY}
     *   <li>{@link Constant#FL_FACE_ID_MOUTH_X}
     *   <li>{@link Constant#FL_FACE_ID_MOUTH_Y}
     *   <li>{@link Constant#FL_FACE_ID_NOSE_X}
     *   <li>{@link Constant#FL_FACE_ID_NOSE_Y}
     *   <li>{@link Constant#FL_FACE_ID_ANGLE_YAW}
     *   <li>{@link Constant#FL_FACE_ID_ANGLE_PITCH}
     *   <li>{@link Constant#FL_FACE_ID_ANGLE_ROLL}
     *   <li>{@link Constant#FL_FACE_ID_EXT_INFO}
     *   <li>{@link Constant#FL_FACE_ID_FEATURE_MD5}
     *   <li>{@link Constant#FL_FACE_ID_CREATE_TIME}
     * </ul>
     */
    public FlFaceComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for FlFaceComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new FlFaceComparator(Constant.FL_FACE_ID_ID, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>{@link Constant#FL_FACE_ID_ID})
     *   <li>{@link Constant#FL_FACE_ID_IMAGE_MD5})
     *   <li>{@link Constant#FL_FACE_ID_FACE_LEFT})
     *   <li>{@link Constant#FL_FACE_ID_FACE_TOP})
     *   <li>{@link Constant#FL_FACE_ID_FACE_WIDTH})
     *   <li>{@link Constant#FL_FACE_ID_FACE_HEIGHT})
     *   <li>{@link Constant#FL_FACE_ID_EYE_LEFTX})
     *   <li>{@link Constant#FL_FACE_ID_EYE_LEFTY})
     *   <li>{@link Constant#FL_FACE_ID_EYE_RIGHTX})
     *   <li>{@link Constant#FL_FACE_ID_EYE_RIGHTY})
     *   <li>{@link Constant#FL_FACE_ID_MOUTH_X})
     *   <li>{@link Constant#FL_FACE_ID_MOUTH_Y})
     *   <li>{@link Constant#FL_FACE_ID_NOSE_X})
     *   <li>{@link Constant#FL_FACE_ID_NOSE_Y})
     *   <li>{@link Constant#FL_FACE_ID_ANGLE_YAW})
     *   <li>{@link Constant#FL_FACE_ID_ANGLE_PITCH})
     *   <li>{@link Constant#FL_FACE_ID_ANGLE_ROLL})
     *   <li>{@link Constant#FL_FACE_ID_EXT_INFO})
     *   <li>{@link Constant#FL_FACE_ID_FEATURE_MD5})
     *   <li>{@link Constant#FL_FACE_ID_CREATE_TIME})
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public FlFaceComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    @Override
    public int compare(FlFaceBean b1, FlFaceBean b2)
    {
        int iReturn = 0;
        switch(iType)
        {
            case FL_FACE_ID_ID:
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
            case FL_FACE_ID_IMAGE_MD5:
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
            case FL_FACE_ID_FACE_LEFT:
                if (b1.getFaceLeft() == null && b2.getFaceLeft() != null) {
                    iReturn = -1;
                } else if (b1.getFaceLeft() == null && b2.getFaceLeft() == null) {
                    iReturn = 0;
                } else if (b1.getFaceLeft() != null && b2.getFaceLeft() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getFaceLeft().compareTo(b2.getFaceLeft());
                }
                break;
            case FL_FACE_ID_FACE_TOP:
                if (b1.getFaceTop() == null && b2.getFaceTop() != null) {
                    iReturn = -1;
                } else if (b1.getFaceTop() == null && b2.getFaceTop() == null) {
                    iReturn = 0;
                } else if (b1.getFaceTop() != null && b2.getFaceTop() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getFaceTop().compareTo(b2.getFaceTop());
                }
                break;
            case FL_FACE_ID_FACE_WIDTH:
                if (b1.getFaceWidth() == null && b2.getFaceWidth() != null) {
                    iReturn = -1;
                } else if (b1.getFaceWidth() == null && b2.getFaceWidth() == null) {
                    iReturn = 0;
                } else if (b1.getFaceWidth() != null && b2.getFaceWidth() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getFaceWidth().compareTo(b2.getFaceWidth());
                }
                break;
            case FL_FACE_ID_FACE_HEIGHT:
                if (b1.getFaceHeight() == null && b2.getFaceHeight() != null) {
                    iReturn = -1;
                } else if (b1.getFaceHeight() == null && b2.getFaceHeight() == null) {
                    iReturn = 0;
                } else if (b1.getFaceHeight() != null && b2.getFaceHeight() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getFaceHeight().compareTo(b2.getFaceHeight());
                }
                break;
            case FL_FACE_ID_EYE_LEFTX:
                if (b1.getEyeLeftx() == null && b2.getEyeLeftx() != null) {
                    iReturn = -1;
                } else if (b1.getEyeLeftx() == null && b2.getEyeLeftx() == null) {
                    iReturn = 0;
                } else if (b1.getEyeLeftx() != null && b2.getEyeLeftx() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getEyeLeftx().compareTo(b2.getEyeLeftx());
                }
                break;
            case FL_FACE_ID_EYE_LEFTY:
                if (b1.getEyeLefty() == null && b2.getEyeLefty() != null) {
                    iReturn = -1;
                } else if (b1.getEyeLefty() == null && b2.getEyeLefty() == null) {
                    iReturn = 0;
                } else if (b1.getEyeLefty() != null && b2.getEyeLefty() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getEyeLefty().compareTo(b2.getEyeLefty());
                }
                break;
            case FL_FACE_ID_EYE_RIGHTX:
                if (b1.getEyeRightx() == null && b2.getEyeRightx() != null) {
                    iReturn = -1;
                } else if (b1.getEyeRightx() == null && b2.getEyeRightx() == null) {
                    iReturn = 0;
                } else if (b1.getEyeRightx() != null && b2.getEyeRightx() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getEyeRightx().compareTo(b2.getEyeRightx());
                }
                break;
            case FL_FACE_ID_EYE_RIGHTY:
                if (b1.getEyeRighty() == null && b2.getEyeRighty() != null) {
                    iReturn = -1;
                } else if (b1.getEyeRighty() == null && b2.getEyeRighty() == null) {
                    iReturn = 0;
                } else if (b1.getEyeRighty() != null && b2.getEyeRighty() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getEyeRighty().compareTo(b2.getEyeRighty());
                }
                break;
            case FL_FACE_ID_MOUTH_X:
                if (b1.getMouthX() == null && b2.getMouthX() != null) {
                    iReturn = -1;
                } else if (b1.getMouthX() == null && b2.getMouthX() == null) {
                    iReturn = 0;
                } else if (b1.getMouthX() != null && b2.getMouthX() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getMouthX().compareTo(b2.getMouthX());
                }
                break;
            case FL_FACE_ID_MOUTH_Y:
                if (b1.getMouthY() == null && b2.getMouthY() != null) {
                    iReturn = -1;
                } else if (b1.getMouthY() == null && b2.getMouthY() == null) {
                    iReturn = 0;
                } else if (b1.getMouthY() != null && b2.getMouthY() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getMouthY().compareTo(b2.getMouthY());
                }
                break;
            case FL_FACE_ID_NOSE_X:
                if (b1.getNoseX() == null && b2.getNoseX() != null) {
                    iReturn = -1;
                } else if (b1.getNoseX() == null && b2.getNoseX() == null) {
                    iReturn = 0;
                } else if (b1.getNoseX() != null && b2.getNoseX() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getNoseX().compareTo(b2.getNoseX());
                }
                break;
            case FL_FACE_ID_NOSE_Y:
                if (b1.getNoseY() == null && b2.getNoseY() != null) {
                    iReturn = -1;
                } else if (b1.getNoseY() == null && b2.getNoseY() == null) {
                    iReturn = 0;
                } else if (b1.getNoseY() != null && b2.getNoseY() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getNoseY().compareTo(b2.getNoseY());
                }
                break;
            case FL_FACE_ID_ANGLE_YAW:
                if (b1.getAngleYaw() == null && b2.getAngleYaw() != null) {
                    iReturn = -1;
                } else if (b1.getAngleYaw() == null && b2.getAngleYaw() == null) {
                    iReturn = 0;
                } else if (b1.getAngleYaw() != null && b2.getAngleYaw() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getAngleYaw().compareTo(b2.getAngleYaw());
                }
                break;
            case FL_FACE_ID_ANGLE_PITCH:
                if (b1.getAnglePitch() == null && b2.getAnglePitch() != null) {
                    iReturn = -1;
                } else if (b1.getAnglePitch() == null && b2.getAnglePitch() == null) {
                    iReturn = 0;
                } else if (b1.getAnglePitch() != null && b2.getAnglePitch() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getAnglePitch().compareTo(b2.getAnglePitch());
                }
                break;
            case FL_FACE_ID_ANGLE_ROLL:
                if (b1.getAngleRoll() == null && b2.getAngleRoll() != null) {
                    iReturn = -1;
                } else if (b1.getAngleRoll() == null && b2.getAngleRoll() == null) {
                    iReturn = 0;
                } else if (b1.getAngleRoll() != null && b2.getAngleRoll() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getAngleRoll().compareTo(b2.getAngleRoll());
                }
                break;
            case FL_FACE_ID_EXT_INFO:
                if (b1.getExtInfo() == null && b2.getExtInfo() != null) {
                    iReturn = -1;
                } else if (b1.getExtInfo() == null && b2.getExtInfo() == null) {
                    iReturn = 0;
                } else if (b1.getExtInfo() != null && b2.getExtInfo() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getExtInfo().compareTo(b2.getExtInfo());
                }
                break;
            case FL_FACE_ID_FEATURE_MD5:
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
            case FL_FACE_ID_CREATE_TIME:
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
