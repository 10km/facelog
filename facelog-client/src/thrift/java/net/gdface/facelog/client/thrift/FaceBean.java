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

    private long modified;

    @ThriftField(value=2, name="modified", requiredness=Requiredness.REQUIRED)
    public long getModified() { return modified; }

    @ThriftField
    public void setModified(final long modified) { this.modified = modified; }

    private long initialized;

    @ThriftField(value=3, name="initialized", requiredness=Requiredness.REQUIRED)
    public long getInitialized() { return initialized; }

    @ThriftField
    public void setInitialized(final long initialized) { this.initialized = initialized; }

    private int id;

    @ThriftField(value=4, name="id", requiredness=Requiredness.NONE)
    public int getId() { return id; }

    @ThriftField
    public void setId(final int id) { this.id = id; }

    private String imageMd5;

    @ThriftField(value=5, name="imageMd5", requiredness=Requiredness.NONE)
    public String getImageMd5() { return imageMd5; }

    @ThriftField
    public void setImageMd5(final String imageMd5) { this.imageMd5 = imageMd5; }

    private int faceLeft;

    @ThriftField(value=6, name="faceLeft", requiredness=Requiredness.NONE)
    public int getFaceLeft() { return faceLeft; }

    @ThriftField
    public void setFaceLeft(final int faceLeft) { this.faceLeft = faceLeft; }

    private int faceTop;

    @ThriftField(value=7, name="faceTop", requiredness=Requiredness.NONE)
    public int getFaceTop() { return faceTop; }

    @ThriftField
    public void setFaceTop(final int faceTop) { this.faceTop = faceTop; }

    private int faceWidth;

    @ThriftField(value=8, name="faceWidth", requiredness=Requiredness.NONE)
    public int getFaceWidth() { return faceWidth; }

    @ThriftField
    public void setFaceWidth(final int faceWidth) { this.faceWidth = faceWidth; }

    private int faceHeight;

    @ThriftField(value=9, name="faceHeight", requiredness=Requiredness.NONE)
    public int getFaceHeight() { return faceHeight; }

    @ThriftField
    public void setFaceHeight(final int faceHeight) { this.faceHeight = faceHeight; }

    private int eyeLeftx;

    @ThriftField(value=10, name="eyeLeftx", requiredness=Requiredness.NONE)
    public int getEyeLeftx() { return eyeLeftx; }

    @ThriftField
    public void setEyeLeftx(final int eyeLeftx) { this.eyeLeftx = eyeLeftx; }

    private int eyeLefty;

    @ThriftField(value=11, name="eyeLefty", requiredness=Requiredness.NONE)
    public int getEyeLefty() { return eyeLefty; }

    @ThriftField
    public void setEyeLefty(final int eyeLefty) { this.eyeLefty = eyeLefty; }

    private int eyeRightx;

    @ThriftField(value=12, name="eyeRightx", requiredness=Requiredness.NONE)
    public int getEyeRightx() { return eyeRightx; }

    @ThriftField
    public void setEyeRightx(final int eyeRightx) { this.eyeRightx = eyeRightx; }

    private int eyeRighty;

    @ThriftField(value=13, name="eyeRighty", requiredness=Requiredness.NONE)
    public int getEyeRighty() { return eyeRighty; }

    @ThriftField
    public void setEyeRighty(final int eyeRighty) { this.eyeRighty = eyeRighty; }

    private int mouthX;

    @ThriftField(value=14, name="mouthX", requiredness=Requiredness.NONE)
    public int getMouthX() { return mouthX; }

    @ThriftField
    public void setMouthX(final int mouthX) { this.mouthX = mouthX; }

    private int mouthY;

    @ThriftField(value=15, name="mouthY", requiredness=Requiredness.NONE)
    public int getMouthY() { return mouthY; }

    @ThriftField
    public void setMouthY(final int mouthY) { this.mouthY = mouthY; }

    private int noseX;

    @ThriftField(value=16, name="noseX", requiredness=Requiredness.NONE)
    public int getNoseX() { return noseX; }

    @ThriftField
    public void setNoseX(final int noseX) { this.noseX = noseX; }

    private int noseY;

    @ThriftField(value=17, name="noseY", requiredness=Requiredness.NONE)
    public int getNoseY() { return noseY; }

    @ThriftField
    public void setNoseY(final int noseY) { this.noseY = noseY; }

    private int angleYaw;

    @ThriftField(value=18, name="angleYaw", requiredness=Requiredness.NONE)
    public int getAngleYaw() { return angleYaw; }

    @ThriftField
    public void setAngleYaw(final int angleYaw) { this.angleYaw = angleYaw; }

    private int anglePitch;

    @ThriftField(value=19, name="anglePitch", requiredness=Requiredness.NONE)
    public int getAnglePitch() { return anglePitch; }

    @ThriftField
    public void setAnglePitch(final int anglePitch) { this.anglePitch = anglePitch; }

    private int angleRoll;

    @ThriftField(value=20, name="angleRoll", requiredness=Requiredness.NONE)
    public int getAngleRoll() { return angleRoll; }

    @ThriftField
    public void setAngleRoll(final int angleRoll) { this.angleRoll = angleRoll; }

    private byte [] extInfo;

    @ThriftField(value=21, name="extInfo", requiredness=Requiredness.NONE)
    public byte [] getExtInfo() { return extInfo; }

    @ThriftField
    public void setExtInfo(final byte [] extInfo) { this.extInfo = extInfo; }

    private String featureMd5;

    @ThriftField(value=22, name="featureMd5", requiredness=Requiredness.NONE)
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
