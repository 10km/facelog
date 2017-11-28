package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

import static com.google.common.base.Objects.toStringHelper;

@ThriftStruct("LogBean")
public final class LogBean
{
    public LogBean() {
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

    private int personId;

    @ThriftField(value=5, name="personId", requiredness=Requiredness.NONE)
    public int getPersonId() { return personId; }

    @ThriftField
    public void setPersonId(final int personId) { this.personId = personId; }

    private int deviceId;

    @ThriftField(value=6, name="deviceId", requiredness=Requiredness.NONE)
    public int getDeviceId() { return deviceId; }

    @ThriftField
    public void setDeviceId(final int deviceId) { this.deviceId = deviceId; }

    private String verifyFeature;

    @ThriftField(value=7, name="verifyFeature", requiredness=Requiredness.NONE)
    public String getVerifyFeature() { return verifyFeature; }

    @ThriftField
    public void setVerifyFeature(final String verifyFeature) { this.verifyFeature = verifyFeature; }

    private int compareFace;

    @ThriftField(value=8, name="compareFace", requiredness=Requiredness.NONE)
    public int getCompareFace() { return compareFace; }

    @ThriftField
    public void setCompareFace(final int compareFace) { this.compareFace = compareFace; }

    private int verifyStatus;

    @ThriftField(value=9, name="verifyStatus", requiredness=Requiredness.NONE)
    public int getVerifyStatus() { return verifyStatus; }

    @ThriftField
    public void setVerifyStatus(final int verifyStatus) { this.verifyStatus = verifyStatus; }

    private double similarty;

    @ThriftField(value=10, name="similarty", requiredness=Requiredness.NONE)
    public double getSimilarty() { return similarty; }

    @ThriftField
    public void setSimilarty(final double similarty) { this.similarty = similarty; }

    private long verifyTime;

    @ThriftField(value=11, name="verifyTime", requiredness=Requiredness.NONE)
    public long getVerifyTime() { return verifyTime; }

    @ThriftField
    public void setVerifyTime(final long verifyTime) { this.verifyTime = verifyTime; }

    private long createTime;

    @ThriftField(value=12, name="createTime", requiredness=Requiredness.NONE)
    public long getCreateTime() { return createTime; }

    @ThriftField
    public void setCreateTime(final long createTime) { this.createTime = createTime; }

    @Override
    public String toString()
    {
        return toStringHelper(this)
            .add("New", New)
            .add("modified", modified)
            .add("initialized", initialized)
            .add("id", id)
            .add("personId", personId)
            .add("deviceId", deviceId)
            .add("verifyFeature", verifyFeature)
            .add("compareFace", compareFace)
            .add("verifyStatus", verifyStatus)
            .add("similarty", similarty)
            .add("verifyTime", verifyTime)
            .add("createTime", createTime)
            .toString();
    }
}
