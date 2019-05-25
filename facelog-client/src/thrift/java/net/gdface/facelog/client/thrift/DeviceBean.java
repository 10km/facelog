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

    private String productName;

    @ThriftField(value=7, name="productName", requiredness=Requiredness.OPTIONAL)
    public String getProductName() { return productName; }

    @ThriftField
    public void setProductName(final String productName) { this.productName = productName; }

    private String model;

    @ThriftField(value=8, name="model", requiredness=Requiredness.OPTIONAL)
    public String getModel() { return model; }

    @ThriftField
    public void setModel(final String model) { this.model = model; }

    private String vendor;

    @ThriftField(value=9, name="vendor", requiredness=Requiredness.OPTIONAL)
    public String getVendor() { return vendor; }

    @ThriftField
    public void setVendor(final String vendor) { this.vendor = vendor; }

    private String manufacturer;

    @ThriftField(value=10, name="manufacturer", requiredness=Requiredness.OPTIONAL)
    public String getManufacturer() { return manufacturer; }

    @ThriftField
    public void setManufacturer(final String manufacturer) { this.manufacturer = manufacturer; }

    private Long madeDate;

    @ThriftField(value=11, name="madeDate", requiredness=Requiredness.OPTIONAL)
    public Long getMadeDate() { return madeDate; }

    @ThriftField
    public void setMadeDate(final Long madeDate) { this.madeDate = madeDate; }

    private String version;

    @ThriftField(value=12, name="version", requiredness=Requiredness.OPTIONAL)
    public String getVersion() { return version; }

    @ThriftField
    public void setVersion(final String version) { this.version = version; }

    private String serialNo;

    @ThriftField(value=13, name="serialNo", requiredness=Requiredness.OPTIONAL)
    public String getSerialNo() { return serialNo; }

    @ThriftField
    public void setSerialNo(final String serialNo) { this.serialNo = serialNo; }

    private String mac;

    @ThriftField(value=14, name="mac", requiredness=Requiredness.OPTIONAL)
    public String getMac() { return mac; }

    @ThriftField
    public void setMac(final String mac) { this.mac = mac; }

    private String remark;

    @ThriftField(value=15, name="remark", requiredness=Requiredness.OPTIONAL)
    public String getRemark() { return remark; }

    @ThriftField
    public void setRemark(final String remark) { this.remark = remark; }

    private byte [] extBin;

    @ThriftField(value=16, name="extBin", requiredness=Requiredness.OPTIONAL)
    public byte [] getExtBin() { return extBin; }

    @ThriftField
    public void setExtBin(final byte [] extBin) { this.extBin = extBin; }

    private String extTxt;

    @ThriftField(value=17, name="extTxt", requiredness=Requiredness.OPTIONAL)
    public String getExtTxt() { return extTxt; }

    @ThriftField
    public void setExtTxt(final String extTxt) { this.extTxt = extTxt; }

    private Long createTime;

    @ThriftField(value=18, name="createTime", requiredness=Requiredness.OPTIONAL)
    public Long getCreateTime() { return createTime; }

    @ThriftField
    public void setCreateTime(final Long createTime) { this.createTime = createTime; }

    private Long updateTime;

    @ThriftField(value=19, name="updateTime", requiredness=Requiredness.OPTIONAL)
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
            .add("productName", productName)
            .add("model", model)
            .add("vendor", vendor)
            .add("manufacturer", manufacturer)
            .add("madeDate", madeDate)
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
