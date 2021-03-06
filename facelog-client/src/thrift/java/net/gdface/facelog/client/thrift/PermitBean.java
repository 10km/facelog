package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

import static com.google.common.base.Objects.toStringHelper;

@ThriftStruct("PermitBean")
public final class PermitBean
{
    public PermitBean() {
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

    private Integer deviceGroupId;

    @ThriftField(value=4, name="deviceGroupId", requiredness=Requiredness.OPTIONAL)
    public Integer getDeviceGroupId() { return deviceGroupId; }

    @ThriftField
    public void setDeviceGroupId(final Integer deviceGroupId) { this.deviceGroupId = deviceGroupId; }

    private Integer personGroupId;

    @ThriftField(value=5, name="personGroupId", requiredness=Requiredness.OPTIONAL)
    public Integer getPersonGroupId() { return personGroupId; }

    @ThriftField
    public void setPersonGroupId(final Integer personGroupId) { this.personGroupId = personGroupId; }

    private String schedule;

    @ThriftField(value=6, name="schedule", requiredness=Requiredness.OPTIONAL)
    public String getSchedule() { return schedule; }

    @ThriftField
    public void setSchedule(final String schedule) { this.schedule = schedule; }

    private String passLimit;

    @ThriftField(value=7, name="passLimit", requiredness=Requiredness.OPTIONAL)
    public String getPassLimit() { return passLimit; }

    @ThriftField
    public void setPassLimit(final String passLimit) { this.passLimit = passLimit; }

    private String remark;

    @ThriftField(value=8, name="remark", requiredness=Requiredness.OPTIONAL)
    public String getRemark() { return remark; }

    @ThriftField
    public void setRemark(final String remark) { this.remark = remark; }

    private byte [] extBin;

    @ThriftField(value=9, name="extBin", requiredness=Requiredness.OPTIONAL)
    public byte [] getExtBin() { return extBin; }

    @ThriftField
    public void setExtBin(final byte [] extBin) { this.extBin = extBin; }

    private String extTxt;

    @ThriftField(value=10, name="extTxt", requiredness=Requiredness.OPTIONAL)
    public String getExtTxt() { return extTxt; }

    @ThriftField
    public void setExtTxt(final String extTxt) { this.extTxt = extTxt; }

    private Long createTime;

    @ThriftField(value=11, name="createTime", requiredness=Requiredness.OPTIONAL)
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
            .add("deviceGroupId", deviceGroupId)
            .add("personGroupId", personGroupId)
            .add("schedule", schedule)
            .add("passLimit", passLimit)
            .add("remark", remark)
            .add("extBin", extBin)
            .add("extTxt", extTxt)
            .add("createTime", createTime)
            .toString();
    }
}
