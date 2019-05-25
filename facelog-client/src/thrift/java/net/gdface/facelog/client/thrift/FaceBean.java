package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

import static com.google.common.base.Objects.toStringHelper;

@ThriftStruct("FaceBean")
public final class FaceBean
{
    public FaceBean() {
    }

    private boolean New;

    @ThriftField(value=1, name="_new", requiredness=Requiredness.REQUIRED)
    public boolean isNew() { return New; }

    @ThriftField
    public void setNew(final boolean New) { this.New = New; }

    private int modified;

    @ThriftField(value=2, name="modified", requiredness=Requiredness.REQUIRED)
    public int getModified() { return modified; }

    @ThriftField
    public void setModified(final int modified) { this.modified = modified; }

    private int initialized;

    @ThriftField(value=3, name="initialized", requiredness=Requiredness.REQUIRED)
    public int getInitialized() { return initialized; }

    @ThriftField
    public void setInitialized(final int initialized) { this.initialized = initialized; }

    private Integer id;

    @ThriftField(value=4, name="id", requiredness=Requiredness.OPTIONAL)
    public Integer getId() { return id; }

    @ThriftField
    public void setId(final Integer id) { this.id = id; }

    private String imageMd5;

    @ThriftField(value=5, name="imageMd5", requiredness=Requiredness.OPTIONAL)
    public String getImageMd5() { return imageMd5; }

    @ThriftField
    public void setImageMd5(final String imageMd5) { this.imageMd5 = imageMd5; }

    private Integer faceLeft;

    @ThriftField(value=6, name="faceLeft", requiredness=Requiredness.OPTIONAL)
    public Integer getFaceLeft() { return faceLeft; }

    @ThriftField
    public void setFaceLeft(final Integer faceLeft) { this.faceLeft = faceLeft; }

    private Integer faceTop;

    @ThriftField(value=7, name="faceTop", requiredness=Requiredness.OPTIONAL)
    public Integer getFaceTop() { return faceTop; }

    @ThriftField
    public void setFaceTop(final Integer faceTop) { this.faceTop = faceTop; }

    private Integer faceWidth;

    @ThriftField(value=8, name="faceWidth", requiredness=Requiredness.OPTIONAL)
    public Integer getFaceWidth() { return faceWidth; }

    @ThriftField
    public void setFaceWidth(final Integer faceWidth) { this.faceWidth = faceWidth; }

    private Integer faceHeight;

    @ThriftField(value=9, name="faceHeight", requiredness=Requiredness.OPTIONAL)
    public Integer getFaceHeight() { return faceHeight; }

    @ThriftField
    public void setFaceHeight(final Integer faceHeight) { this.faceHeight = faceHeight; }

    private Integer eyeLeftx;

    @ThriftField(value=10, name="eyeLeftx", requiredness=Requiredness.OPTIONAL)
    public Integer getEyeLeftx() { return eyeLeftx; }

    @ThriftField
    public void setEyeLeftx(final Integer eyeLeftx) { this.eyeLeftx = eyeLeftx; }

    private Integer eyeLefty;

    @ThriftField(value=11, name="eyeLefty", requiredness=Requiredness.OPTIONAL)
    public Integer getEyeLefty() { return eyeLefty; }

    @ThriftField
    public void setEyeLefty(final Integer eyeLefty) { this.eyeLefty = eyeLefty; }

    private Integer eyeRightx;

    @ThriftField(value=12, name="eyeRightx", requiredness=Requiredness.OPTIONAL)
    public Integer getEyeRightx() { return eyeRightx; }

    @ThriftField
    public void setEyeRightx(final Integer eyeRightx) { this.eyeRightx = eyeRightx; }

    private Integer eyeRighty;

    @ThriftField(value=13, name="eyeRighty", requiredness=Requiredness.OPTIONAL)
    public Integer getEyeRighty() { return eyeRighty; }

    @ThriftField
    public void setEyeRighty(final Integer eyeRighty) { this.eyeRighty = eyeRighty; }

    private Integer mouthX;

    @ThriftField(value=14, name="mouthX", requiredness=Requiredness.OPTIONAL)
    public Integer getMouthX() { return mouthX; }

    @ThriftField
    public void setMouthX(final Integer mouthX) { this.mouthX = mouthX; }

    private Integer mouthY;

    @ThriftField(value=15, name="mouthY", requiredness=Requiredness.OPTIONAL)
    public Integer getMouthY() { return mouthY; }

    @ThriftField
    public void setMouthY(final Integer mouthY) { this.mouthY = mouthY; }

    private Integer noseX;

    @ThriftField(value=16, name="noseX", requiredness=Requiredness.OPTIONAL)
    public Integer getNoseX() { return noseX; }

    @ThriftField
    public void setNoseX(final Integer noseX) { this.noseX = noseX; }

    private Integer noseY;

    @ThriftField(value=17, name="noseY", requiredness=Requiredness.OPTIONAL)
    public Integer getNoseY() { return noseY; }

    @ThriftField
    public void setNoseY(final Integer noseY) { this.noseY = noseY; }

    private Integer angleYaw;

    @ThriftField(value=18, name="angleYaw", requiredness=Requiredness.OPTIONAL)
    public Integer getAngleYaw() { return angleYaw; }

    @ThriftField
    public void setAngleYaw(final Integer angleYaw) { this.angleYaw = angleYaw; }

    private Integer anglePitch;

    @ThriftField(value=19, name="anglePitch", requiredness=Requiredness.OPTIONAL)
    public Integer getAnglePitch() { return anglePitch; }

    @ThriftField
    public void setAnglePitch(final Integer anglePitch) { this.anglePitch = anglePitch; }

    private Integer angleRoll;

    @ThriftField(value=20, name="angleRoll", requiredness=Requiredness.OPTIONAL)
    public Integer getAngleRoll() { return angleRoll; }

    @ThriftField
    public void setAngleRoll(final Integer angleRoll) { this.angleRoll = angleRoll; }

    private byte [] extInfo;

    @ThriftField(value=21, name="extInfo", requiredness=Requiredness.OPTIONAL)
    public byte [] getExtInfo() { return extInfo; }

    @ThriftField
    public void setExtInfo(final byte [] extInfo) { this.extInfo = extInfo; }

    private String featureMd5;

    @ThriftField(value=22, name="featureMd5", requiredness=Requiredness.OPTIONAL)
    public String getFeatureMd5() { return featureMd5; }

    @ThriftField
    public void setFeatureMd5(final String featureMd5) { this.featureMd5 = featureMd5; }

    @Override
    public String toString()
    {
        return toStringHelper(this)
            .add("New", New)
            .add("modified", modified)
            .add("initialized", initialized)
            .add("id", id)
            .add("imageMd5", imageMd5)
            .add("faceLeft", faceLeft)
            .add("faceTop", faceTop)
            .add("faceWidth", faceWidth)
            .add("faceHeight", faceHeight)
            .add("eyeLeftx", eyeLeftx)
            .add("eyeLefty", eyeLefty)
            .add("eyeRightx", eyeRightx)
            .add("eyeRighty", eyeRighty)
            .add("mouthX", mouthX)
            .add("mouthY", mouthY)
            .add("noseX", noseX)
            .add("noseY", noseY)
            .add("angleYaw", angleYaw)
            .add("anglePitch", anglePitch)
            .add("angleRoll", angleRoll)
            .add("extInfo", extInfo)
            .add("featureMd5", featureMd5)
            .toString();
    }
}
