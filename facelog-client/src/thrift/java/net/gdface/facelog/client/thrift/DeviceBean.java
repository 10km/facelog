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

    private int id;

    @ThriftField(value=4, name="id", requiredness=Requiredness.NONE)
    public int getId() { return id; }

    @ThriftField
    public void setId(final int id) { this.id = id; }

    private int groupId;

    @ThriftField(value=5, name="groupId", requiredness=Requiredness.NONE)
    public int getGroupId() { return groupId; }

    @ThriftField
    public void setGroupId(final int groupId) { this.groupId = groupId; }

    private String name;

    @ThriftField(value=6, name="name", requiredness=Requiredness.NONE)
    public String getName() { return name; }

    @ThriftField
    public void setName(final String name) { this.name = name; }

    private String version;

    @ThriftField(value=7, name="version", requiredness=Requiredness.NONE)
    public String getVersion() { return version; }

    @ThriftField
    public void setVersion(final String version) { this.version = version; }

    private String serialNo;

    @ThriftField(value=8, name="serialNo", requiredness=Requiredness.NONE)
    public String getSerialNo() { return serialNo; }

    @ThriftField
    public void setSerialNo(final String serialNo) { this.serialNo = serialNo; }

    private String mac;

    @ThriftField(value=9, name="mac", requiredness=Requiredness.NONE)
    public String getMac() { return mac; }

    @ThriftField
    public void setMac(final String mac) { this.mac = mac; }

    private long createTime;

    @ThriftField(value=10, name="createTime", requiredness=Requiredness.NONE)
    public long getCreateTime() { return createTime; }

    @ThriftField
    public void setCreateTime(final long createTime) { this.createTime = createTime; }

    private long updateTime;

    @ThriftField(value=11, name="updateTime", requiredness=Requiredness.NONE)
    public long getUpdateTime() { return updateTime; }

    @ThriftField
    public void setUpdateTime(final long updateTime) { this.updateTime = updateTime; }

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
            .add("createTime", createTime)
            .add("updateTime", updateTime)
            .toString();
    }
}
