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

    @ThriftField(value=4, name="md5", requiredness=Requiredness.OPTIONAL)
    public String getMd5() { return md5; }

    @ThriftField
    public void setMd5(final String md5) { this.md5 = md5; }

    private String format;

    @ThriftField(value=5, name="format", requiredness=Requiredness.OPTIONAL)
    public String getFormat() { return format; }

    @ThriftField
    public void setFormat(final String format) { this.format = format; }

    private Integer width;

    @ThriftField(value=6, name="width", requiredness=Requiredness.OPTIONAL)
    public Integer getWidth() { return width; }

    @ThriftField
    public void setWidth(final Integer width) { this.width = width; }

    private Integer height;

    @ThriftField(value=7, name="height", requiredness=Requiredness.OPTIONAL)
    public Integer getHeight() { return height; }

    @ThriftField
    public void setHeight(final Integer height) { this.height = height; }

    private Integer depth;

    @ThriftField(value=8, name="depth", requiredness=Requiredness.OPTIONAL)
    public Integer getDepth() { return depth; }

    @ThriftField
    public void setDepth(final Integer depth) { this.depth = depth; }

    private Integer faceNum;

    @ThriftField(value=9, name="faceNum", requiredness=Requiredness.OPTIONAL)
    public Integer getFaceNum() { return faceNum; }

    @ThriftField
    public void setFaceNum(final Integer faceNum) { this.faceNum = faceNum; }

    private String thumbMd5;

    @ThriftField(value=10, name="thumbMd5", requiredness=Requiredness.OPTIONAL)
    public String getThumbMd5() { return thumbMd5; }

    @ThriftField
    public void setThumbMd5(final String thumbMd5) { this.thumbMd5 = thumbMd5; }

    private Integer deviceId;

    @ThriftField(value=11, name="deviceId", requiredness=Requiredness.OPTIONAL)
    public Integer getDeviceId() { return deviceId; }

    @ThriftField
    public void setDeviceId(final Integer deviceId) { this.deviceId = deviceId; }

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
            .toString();
    }
}
