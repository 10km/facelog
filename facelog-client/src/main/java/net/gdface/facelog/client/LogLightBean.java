package net.gdface.facelog.client;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

import static com.google.common.base.Objects.toStringHelper;

@ThriftStruct("LogLightBean")
public final class LogLightBean
{
    public LogLightBean() {
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

    private String name;

    @ThriftField(value=6, name="name", requiredness=Requiredness.NONE)
    public String getName() { return name; }

    @ThriftField
    public void setName(final String name) { this.name = name; }

    private int papersType;

    @ThriftField(value=7, name="papersType", requiredness=Requiredness.NONE)
    public int getPapersType() { return papersType; }

    @ThriftField
    public void setPapersType(final int papersType) { this.papersType = papersType; }

    private String papersNum;

    @ThriftField(value=8, name="papersNum", requiredness=Requiredness.NONE)
    public String getPapersNum() { return papersNum; }

    @ThriftField
    public void setPapersNum(final String papersNum) { this.papersNum = papersNum; }

    private long verifyTime;

    @ThriftField(value=9, name="verifyTime", requiredness=Requiredness.NONE)
    public long getVerifyTime() { return verifyTime; }

    @ThriftField
    public void setVerifyTime(final long verifyTime) { this.verifyTime = verifyTime; }

    @Override
    public String toString()
    {
        return toStringHelper(this)
            .add("New", New)
            .add("modified", modified)
            .add("initialized", initialized)
            .add("id", id)
            .add("personId", personId)
            .add("name", name)
            .add("papersType", papersType)
            .add("papersNum", papersNum)
            .add("verifyTime", verifyTime)
            .toString();
    }
}
