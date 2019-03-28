package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

import static com.google.common.base.Objects.toStringHelper;

@ThriftStruct("DeviceBean")
public final class DeviceBean
{
    public DeviceBean() {
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

    private Integer id;

    @ThriftField(value=4, name="id", requiredness=Requiredness.OPTIONAL)
    public Integer getId() { return id; }

    @ThriftField
    public void setId(final Integer id) { this.id = id; }

    private Integer groupId;

    @ThriftField(value=5, name="groupId", requiredness=Requiredness.OPTIONAL)
    public Integer getGroupId() { return groupId; }

    @ThriftField
    public void setGroupId(final Integer groupId) { this.groupId = groupId; }

    private String name;

    @ThriftField(value=6, name="name", requiredness=Requiredness.OPTIONAL)
    public String getName() { return name; }

    @ThriftField
    public void setName(final String name) { this.name = name; }

    private String version;

    @ThriftField(value=7, name="version", requiredness=Requiredness.OPTIONAL)
    public String getVersion() { return version; }

    @ThriftField
    public void setVersion(final String version) { this.version = version; }

    private String serialNo;

    @ThriftField(value=8, name="serialNo", requiredness=Requiredness.OPTIONAL)
    public String getSerialNo() { return serialNo; }

    @ThriftField
    public void setSerialNo(final String serialNo) { this.serialNo = serialNo; }

    private String mac;

    @ThriftField(value=9, name="mac", requiredness=Requiredness.OPTIONAL)
    public String getMac() { return mac; }

    @ThriftField
    public void setMac(final String mac) { this.mac = mac; }

    private String remark;

    @ThriftField(value=10, name="remark", requiredness=Requiredness.OPTIONAL)
    public String getRemark() { return remark; }

    @ThriftField
    public void setRemark(final String remark) { this.remark = remark; }

    private byte [] extBin;

    @ThriftField(value=11, name="extBin", requiredness=Requiredness.OPTIONAL)
    public byte [] getExtBin() { return extBin; }

    @ThriftField
    public void setExtBin(final byte [] extBin) { this.extBin = extBin; }

    private String extTxt;

    @ThriftField(value=12, name="extTxt", requiredness=Requiredness.OPTIONAL)
    public String getExtTxt() { return extTxt; }

    @ThriftField
    public void setExtTxt(final String extTxt) { this.extTxt = extTxt; }

    private Long createTime;

    @ThriftField(value=13, name="createTime", requiredness=Requiredness.OPTIONAL)
    public Long getCreateTime() { return createTime; }

    @ThriftField
    public void setCreateTime(final Long createTime) { this.createTime = createTime; }

    private Long updateTime;

    @ThriftField(value=14, name="updateTime", requiredness=Requiredness.OPTIONAL)
    public Long getUpdateTime() { return updateTime; }

    @ThriftField
    public void setUpdateTime(final Long updateTime) { this.updateTime = updateTime; }

    @Override
    public String toString()
    {
        return toStringHelper(this)
            .add("New", New)
            .add("modified", modified)
            .add("initialized", initialized)
            .add("id", id)
            .add("groupId", groupId)
            .add("name", name)
            .add("version", version)
            .add("serialNo", serialNo)
            .add("mac", mac)
            .add("remark", remark)
            .add("extBin", extBin)
            .add("extTxt", extTxt)
            .add("createTime", createTime)
            .add("updateTime", updateTime)
            .toString();
    }
}
