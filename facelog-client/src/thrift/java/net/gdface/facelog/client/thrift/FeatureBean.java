package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

import static com.google.common.base.Objects.toStringHelper;

@ThriftStruct("FeatureBean")
public final class FeatureBean
{
    public FeatureBean() {
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

    private String md5;

    @ThriftField(value=4, name="md5", requiredness=Requiredness.OPTIONAL)
    public String getMd5() { return md5; }

    @ThriftField
    public void setMd5(final String md5) { this.md5 = md5; }

    private String version;

    @ThriftField(value=5, name="version", requiredness=Requiredness.OPTIONAL)
    public String getVersion() { return version; }

    @ThriftField
    public void setVersion(final String version) { this.version = version; }

    private Integer personId;

    @ThriftField(value=6, name="personId", requiredness=Requiredness.OPTIONAL)
    public Integer getPersonId() { return personId; }

    @ThriftField
    public void setPersonId(final Integer personId) { this.personId = personId; }

    private byte [] feature;

    @ThriftField(value=7, name="feature", requiredness=Requiredness.OPTIONAL)
    public byte [] getFeature() { return feature; }

    @ThriftField
    public void setFeature(final byte [] feature) { this.feature = feature; }

    private Long updateTime;

    @ThriftField(value=8, name="updateTime", requiredness=Requiredness.OPTIONAL)
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
            .add("md5", md5)
            .add("version", version)
            .add("personId", personId)
            .add("feature", feature)
            .add("updateTime", updateTime)
            .toString();
    }
}
