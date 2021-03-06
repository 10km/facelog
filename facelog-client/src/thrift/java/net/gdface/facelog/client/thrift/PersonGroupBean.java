package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

import static com.google.common.base.Objects.toStringHelper;

@ThriftStruct("PersonGroupBean")
public final class PersonGroupBean
{
    public PersonGroupBean() {
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

    private String name;

    @ThriftField(value=5, name="name", requiredness=Requiredness.OPTIONAL)
    public String getName() { return name; }

    @ThriftField
    public void setName(final String name) { this.name = name; }

    private Integer leaf;

    @ThriftField(value=6, name="leaf", requiredness=Requiredness.OPTIONAL)
    public Integer getLeaf() { return leaf; }

    @ThriftField
    public void setLeaf(final Integer leaf) { this.leaf = leaf; }

    private Integer parent;

    @ThriftField(value=7, name="parent", requiredness=Requiredness.OPTIONAL)
    public Integer getParent() { return parent; }

    @ThriftField
    public void setParent(final Integer parent) { this.parent = parent; }

    private Integer rootGroup;

    @ThriftField(value=8, name="rootGroup", requiredness=Requiredness.OPTIONAL)
    public Integer getRootGroup() { return rootGroup; }

    @ThriftField
    public void setRootGroup(final Integer rootGroup) { this.rootGroup = rootGroup; }

    private String remark;

    @ThriftField(value=9, name="remark", requiredness=Requiredness.OPTIONAL)
    public String getRemark() { return remark; }

    @ThriftField
    public void setRemark(final String remark) { this.remark = remark; }

    private byte [] extBin;

    @ThriftField(value=10, name="extBin", requiredness=Requiredness.OPTIONAL)
    public byte [] getExtBin() { return extBin; }

    @ThriftField
    public void setExtBin(final byte [] extBin) { this.extBin = extBin; }

    private String extTxt;

    @ThriftField(value=11, name="extTxt", requiredness=Requiredness.OPTIONAL)
    public String getExtTxt() { return extTxt; }

    @ThriftField
    public void setExtTxt(final String extTxt) { this.extTxt = extTxt; }

    private Long createTime;

    @ThriftField(value=12, name="createTime", requiredness=Requiredness.OPTIONAL)
    public Long getCreateTime() { return createTime; }

    @ThriftField
    public void setCreateTime(final Long createTime) { this.createTime = createTime; }

    private Long updateTime;

    @ThriftField(value=13, name="updateTime", requiredness=Requiredness.OPTIONAL)
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
            .add("name", name)
            .add("leaf", leaf)
            .add("parent", parent)
            .add("rootGroup", rootGroup)
            .add("remark", remark)
            .add("extBin", extBin)
            .add("extTxt", extTxt)
            .add("createTime", createTime)
            .add("updateTime", updateTime)
            .toString();
    }
}
