package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

import static com.google.common.base.Objects.toStringHelper;

@ThriftStruct("ImageBean")
public final class ImageBean
{
    public ImageBean() {
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

    private String md5;

    @ThriftField(value=4, name="md5", requiredness=Requiredness.NONE)
    public String getMd5() { return md5; }

    @ThriftField
    public void setMd5(final String md5) { this.md5 = md5; }

    private String format;

    @ThriftField(value=5, name="format", requiredness=Requiredness.NONE)
    public String getFormat() { return format; }

    @ThriftField
    public void setFormat(final String format) { this.format = format; }

    private int width;

    @ThriftField(value=6, name="width", requiredness=Requiredness.NONE)
    public int getWidth() { return width; }

    @ThriftField
    public void setWidth(final int width) { this.width = width; }

    private int height;

    @ThriftField(value=7, name="height", requiredness=Requiredness.NONE)
    public int getHeight() { return height; }

    @ThriftField
    public void setHeight(final int height) { this.height = height; }

    private int depth;

    @ThriftField(value=8, name="depth", requiredness=Requiredness.NONE)
    public int getDepth() { return depth; }

    @ThriftField
    public void setDepth(final int depth) { this.depth = depth; }

    private int faceNum;

    @ThriftField(value=9, name="faceNum", requiredness=Requiredness.NONE)
    public int getFaceNum() { return faceNum; }

    @ThriftField
    public void setFaceNum(final int faceNum) { this.faceNum = faceNum; }

    private String thumbMd5;

    @ThriftField(value=10, name="thumbMd5", requiredness=Requiredness.NONE)
    public String getThumbMd5() { return thumbMd5; }

    @ThriftField
    public void setThumbMd5(final String thumbMd5) { this.thumbMd5 = thumbMd5; }

    private int deviceId;

    @ThriftField(value=11, name="deviceId", requiredness=Requiredness.NONE)
    public int getDeviceId() { return deviceId; }

    @ThriftField
    public void setDeviceId(final int deviceId) { this.deviceId = deviceId; }

    private DeviceBean referencedByDeviceId;

    @ThriftField(value=12, name="referencedByDeviceId", requiredness=Requiredness.NONE)
    public DeviceBean getReferencedByDeviceId() { return referencedByDeviceId; }

    @ThriftField
    public void setReferencedByDeviceId(final DeviceBean referencedByDeviceId) { this.referencedByDeviceId = referencedByDeviceId; }

    @Override
    public String toString()
    {
        return toStringHelper(this)
            .add("New", New)
            .add("modified", modified)
            .add("initialized", initialized)
            .add("md5", md5)
            .add("format", format)
            .add("width", width)
            .add("height", height)
            .add("depth", depth)
            .add("faceNum", faceNum)
            .add("thumbMd5", thumbMd5)
            .add("deviceId", deviceId)
            .add("referencedByDeviceId", referencedByDeviceId)
            .toString();
    }
}
