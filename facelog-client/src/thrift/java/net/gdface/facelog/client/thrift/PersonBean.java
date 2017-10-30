package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

import static com.google.common.base.Objects.toStringHelper;

@ThriftStruct("PersonBean")
public final class PersonBean
{
    public PersonBean() {
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

    private int sex;

    @ThriftField(value=7, name="sex", requiredness=Requiredness.NONE)
    public int getSex() { return sex; }

    @ThriftField
    public void setSex(final int sex) { this.sex = sex; }

    private long birthdate;

    @ThriftField(value=8, name="birthdate", requiredness=Requiredness.NONE)
    public long getBirthdate() { return birthdate; }

    @ThriftField
    public void setBirthdate(final long birthdate) { this.birthdate = birthdate; }

    private int papersType;

    @ThriftField(value=9, name="papersType", requiredness=Requiredness.NONE)
    public int getPapersType() { return papersType; }

    @ThriftField
    public void setPapersType(final int papersType) { this.papersType = papersType; }

    private String papersNum;

    @ThriftField(value=10, name="papersNum", requiredness=Requiredness.NONE)
    public String getPapersNum() { return papersNum; }

    @ThriftField
    public void setPapersNum(final String papersNum) { this.papersNum = papersNum; }

    private String imageMd5;

    @ThriftField(value=11, name="imageMd5", requiredness=Requiredness.NONE)
    public String getImageMd5() { return imageMd5; }

    @ThriftField
    public void setImageMd5(final String imageMd5) { this.imageMd5 = imageMd5; }

    private long expiryDate;

    @ThriftField(value=12, name="expiryDate", requiredness=Requiredness.NONE)
    public long getExpiryDate() { return expiryDate; }

    @ThriftField
    public void setExpiryDate(final long expiryDate) { this.expiryDate = expiryDate; }

    private long createTime;

    @ThriftField(value=13, name="createTime", requiredness=Requiredness.NONE)
    public long getCreateTime() { return createTime; }

    @ThriftField
    public void setCreateTime(final long createTime) { this.createTime = createTime; }

    private long updateTime;

    @ThriftField(value=14, name="updateTime", requiredness=Requiredness.NONE)
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
            .add("sex", sex)
            .add("birthdate", birthdate)
            .add("papersType", papersType)
            .add("papersNum", papersNum)
            .add("imageMd5", imageMd5)
            .add("expiryDate", expiryDate)
            .add("createTime", createTime)
            .add("updateTime", updateTime)
            .toString();
    }
}
