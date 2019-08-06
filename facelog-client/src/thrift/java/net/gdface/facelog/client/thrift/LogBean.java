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

    private Integer personId;

    @ThriftField(value=5, name="personId", requiredness=Requiredness.OPTIONAL)
    public Integer getPersonId() { return personId; }

    @ThriftField
    public void setPersonId(final Integer personId) { this.personId = personId; }

    private Integer deviceId;

    @ThriftField(value=6, name="deviceId", requiredness=Requiredness.OPTIONAL)
    public Integer getDeviceId() { return deviceId; }

    @ThriftField
    public void setDeviceId(final Integer deviceId) { this.deviceId = deviceId; }

    private String verifyFeature;

    @ThriftField(value=7, name="verifyFeature", requiredness=Requiredness.OPTIONAL)
    public String getVerifyFeature() { return verifyFeature; }

    @ThriftField
    public void setVerifyFeature(final String verifyFeature) { this.verifyFeature = verifyFeature; }

    private Integer compareFace;

    @ThriftField(value=8, name="compareFace", requiredness=Requiredness.OPTIONAL)
    public Integer getCompareFace() { return compareFace; }

    @ThriftField
    public void setCompareFace(final Integer compareFace) { this.compareFace = compareFace; }

    private Integer verifyStatus;

    @ThriftField(value=9, name="verifyStatus", requiredness=Requiredness.OPTIONAL)
    public Integer getVerifyStatus() { return verifyStatus; }

    @ThriftField
    public void setVerifyStatus(final Integer verifyStatus) { this.verifyStatus = verifyStatus; }

    private Double similarty;

    @ThriftField(value=10, name="similarty", requiredness=Requiredness.OPTIONAL)
    public Double getSimilarty() { return similarty; }

    @ThriftField
    public void setSimilarty(final Double similarty) { this.similarty = similarty; }

    private Integer direction;

    @ThriftField(value=11, name="direction", requiredness=Requiredness.OPTIONAL)
    public Integer getDirection() { return direction; }

    @ThriftField
    public void setDirection(final Integer direction) { this.direction = direction; }

    private Long verifyTime;

    @ThriftField(value=12, name="verifyTime", requiredness=Requiredness.OPTIONAL)
    public Long getVerifyTime() { return verifyTime; }

    @ThriftField
    public void setVerifyTime(final Long verifyTime) { this.verifyTime = verifyTime; }

    private Long createTime;

    @ThriftField(value=13, name="createTime", requiredness=Requiredness.OPTIONAL)
    public Long getCreateTime() { return createTime; }

    @ThriftField
    public void setCreateTime(final Long createTime) { this.createTime = createTime; }

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
            .add("direction", direction)
            .add("verifyTime", verifyTime)
            .add("createTime", createTime)
            .toString();
    }
}
