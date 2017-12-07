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

    private int rank;

    @ThriftField(value=8, name="rank", requiredness=Requiredness.NONE)
    public int getRank() { return rank; }

    @ThriftField
    public void setRank(final int rank) { this.rank = rank; }

    private String password;

    @ThriftField(value=9, name="password", requiredness=Requiredness.NONE)
    public String getPassword() { return password; }

    @ThriftField
    public void setPassword(final String password) { this.password = password; }

    private long birthdate;

    @ThriftField(value=10, name="birthdate", requiredness=Requiredness.NONE)
    public long getBirthdate() { return birthdate; }

    @ThriftField
    public void setBirthdate(final long birthdate) { this.birthdate = birthdate; }

    private String mobilePhone;

    @ThriftField(value=11, name="mobilePhone", requiredness=Requiredness.NONE)
    public String getMobilePhone() { return mobilePhone; }

    @ThriftField
    public void setMobilePhone(final String mobilePhone) { this.mobilePhone = mobilePhone; }

    private int papersType;

    @ThriftField(value=12, name="papersType", requiredness=Requiredness.NONE)
    public int getPapersType() { return papersType; }

    @ThriftField
    public void setPapersType(final int papersType) { this.papersType = papersType; }

    private String papersNum;

    @ThriftField(value=13, name="papersNum", requiredness=Requiredness.NONE)
    public String getPapersNum() { return papersNum; }

    @ThriftField
    public void setPapersNum(final String papersNum) { this.papersNum = papersNum; }

    private String imageMd5;

    @ThriftField(value=14, name="imageMd5", requiredness=Requiredness.NONE)
    public String getImageMd5() { return imageMd5; }

    @ThriftField
    public void setImageMd5(final String imageMd5) { this.imageMd5 = imageMd5; }

    private long expiryDate;

    @ThriftField(value=15, name="expiryDate", requiredness=Requiredness.NONE)
    public long getExpiryDate() { return expiryDate; }

    @ThriftField
    public void setExpiryDate(final long expiryDate) { this.expiryDate = expiryDate; }

    private String remark;

    @ThriftField(value=16, name="remark", requiredness=Requiredness.NONE)
    public String getRemark() { return remark; }

    @ThriftField
    public void setRemark(final String remark) { this.remark = remark; }

    private long createTime;

    @ThriftField(value=17, name="createTime", requiredness=Requiredness.NONE)
    public long getCreateTime() { return createTime; }

    @ThriftField
    public void setCreateTime(final long createTime) { this.createTime = createTime; }

    private long updateTime;

    @ThriftField(value=18, name="updateTime", requiredness=Requiredness.NONE)
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
            .add("rank", rank)
            .add("password", password)
            .add("birthdate", birthdate)
            .add("mobilePhone", mobilePhone)
            .add("papersType", papersType)
            .add("papersNum", papersNum)
            .add("imageMd5", imageMd5)
            .add("expiryDate", expiryDate)
            .add("remark", remark)
            .add("createTime", createTime)
            .add("updateTime", updateTime)
            .toString();
    }
}
