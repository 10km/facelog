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

    private int deviceGroupId;

    @ThriftField(value=4, name="deviceGroupId", requiredness=Requiredness.NONE)
    public int getDeviceGroupId() { return deviceGroupId; }

    @ThriftField
    public void setDeviceGroupId(final int deviceGroupId) { this.deviceGroupId = deviceGroupId; }

    private int personGroupId;

    @ThriftField(value=5, name="personGroupId", requiredness=Requiredness.NONE)
    public int getPersonGroupId() { return personGroupId; }

    @ThriftField
    public void setPersonGroupId(final int personGroupId) { this.personGroupId = personGroupId; }

    private long createTime;

    @ThriftField(value=6, name="createTime", requiredness=Requiredness.NONE)
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
            .add("deviceGroupId", deviceGroupId)
            .add("personGroupId", personGroupId)
            .add("createTime", createTime)
            .toString();
    }
}
