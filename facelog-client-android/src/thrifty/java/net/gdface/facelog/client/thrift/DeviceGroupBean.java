// Automatically generated by the Thrifty compiler; do not edit!
// Generated on: 2019-05-25T10:56:47.121Z
// Source: J:\facelog\facelog-service\IFaceLog.thrift at 98:1
package net.gdface.facelog.client.thrift;

import com.microsoft.thrifty.Adapter;
import com.microsoft.thrifty.Struct;
import com.microsoft.thrifty.StructBuilder;
import com.microsoft.thrifty.TType;
import com.microsoft.thrifty.ThriftField;
import com.microsoft.thrifty.protocol.FieldMetadata;
import com.microsoft.thrifty.protocol.Protocol;
import com.microsoft.thrifty.util.ProtocolUtil;
import java.io.IOException;
import javax.annotation.Generated;
import okio.ByteString;

@Generated(
    value = "com.microsoft.thrifty.gen.ThriftyCodeGenerator",
    comments = "https://github.com/microsoft/thrifty"
)
public final class DeviceGroupBean implements Struct {
  public static final Adapter<DeviceGroupBean, Builder> ADAPTER = new DeviceGroupBeanAdapter();

  @ThriftField(
      fieldId = 1,
      isRequired = true
  )
  public final Boolean _new;

  @ThriftField(
      fieldId = 2,
      isRequired = true
  )
  public final Integer modified;

  @ThriftField(
      fieldId = 3,
      isRequired = true
  )
  public final Integer initialized;

  @ThriftField(
      fieldId = 4,
      isOptional = true
  )
  public final Integer id;

  @ThriftField(
      fieldId = 5,
      isOptional = true
  )
  public final String name;

  @ThriftField(
      fieldId = 6,
      isOptional = true
  )
  public final Integer leaf;

  @ThriftField(
      fieldId = 7,
      isOptional = true
  )
  public final Integer parent;

  @ThriftField(
      fieldId = 8,
      isOptional = true
  )
  public final Integer rootGroup;

  @ThriftField(
      fieldId = 9,
      isOptional = true
  )
  public final String remark;

  @ThriftField(
      fieldId = 10,
      isOptional = true
  )
  public final ByteString extBin;

  @ThriftField(
      fieldId = 11,
      isOptional = true
  )
  public final String extTxt;

  @ThriftField(
      fieldId = 12,
      isOptional = true
  )
  public final Long createTime;

  @ThriftField(
      fieldId = 13,
      isOptional = true
  )
  public final Long updateTime;

  private DeviceGroupBean(Builder builder) {
    this._new = builder._new;
    this.modified = builder.modified;
    this.initialized = builder.initialized;
    this.id = builder.id;
    this.name = builder.name;
    this.leaf = builder.leaf;
    this.parent = builder.parent;
    this.rootGroup = builder.rootGroup;
    this.remark = builder.remark;
    this.extBin = builder.extBin;
    this.extTxt = builder.extTxt;
    this.createTime = builder.createTime;
    this.updateTime = builder.updateTime;
  }

  @Override
  @SuppressWarnings({"NumberEquality", "StringEquality"})
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null) return false;
    if (!(other instanceof DeviceGroupBean)) return false;
    DeviceGroupBean that = (DeviceGroupBean) other;
    return (this._new == that._new || this._new.equals(that._new))
        && (this.modified == that.modified || this.modified.equals(that.modified))
        && (this.initialized == that.initialized || this.initialized.equals(that.initialized))
        && (this.id == that.id || (this.id != null && this.id.equals(that.id)))
        && (this.name == that.name || (this.name != null && this.name.equals(that.name)))
        && (this.leaf == that.leaf || (this.leaf != null && this.leaf.equals(that.leaf)))
        && (this.parent == that.parent || (this.parent != null && this.parent.equals(that.parent)))
        && (this.rootGroup == that.rootGroup || (this.rootGroup != null && this.rootGroup.equals(that.rootGroup)))
        && (this.remark == that.remark || (this.remark != null && this.remark.equals(that.remark)))
        && (this.extBin == that.extBin || (this.extBin != null && this.extBin.equals(that.extBin)))
        && (this.extTxt == that.extTxt || (this.extTxt != null && this.extTxt.equals(that.extTxt)))
        && (this.createTime == that.createTime || (this.createTime != null && this.createTime.equals(that.createTime)))
        && (this.updateTime == that.updateTime || (this.updateTime != null && this.updateTime.equals(that.updateTime)));
  }

  @Override
  public int hashCode() {
    int code = 16777619;
    code ^= this._new.hashCode();
    code *= 0x811c9dc5;
    code ^= this.modified.hashCode();
    code *= 0x811c9dc5;
    code ^= this.initialized.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.id == null) ? 0 : this.id.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.name == null) ? 0 : this.name.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.leaf == null) ? 0 : this.leaf.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.parent == null) ? 0 : this.parent.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.rootGroup == null) ? 0 : this.rootGroup.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.remark == null) ? 0 : this.remark.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.extBin == null) ? 0 : this.extBin.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.extTxt == null) ? 0 : this.extTxt.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.createTime == null) ? 0 : this.createTime.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.updateTime == null) ? 0 : this.updateTime.hashCode();
    code *= 0x811c9dc5;
    return code;
  }

  @Override
  public String toString() {
    return "DeviceGroupBean{_new=" + this._new + ", modified=" + this.modified + ", initialized=" + this.initialized + ", id=" + this.id + ", name=" + this.name + ", leaf=" + this.leaf + ", parent=" + this.parent + ", rootGroup=" + this.rootGroup + ", remark=" + this.remark + ", extBin=" + this.extBin + ", extTxt=" + this.extTxt + ", createTime=" + this.createTime + ", updateTime=" + this.updateTime + "}";
  }

  @Override
  public void write(Protocol protocol) throws IOException {
    ADAPTER.write(protocol, this);
  }

  public static final class Builder implements StructBuilder<DeviceGroupBean> {
    private Boolean _new;

    private Integer modified;

    private Integer initialized;

    private Integer id;

    private String name;

    private Integer leaf;

    private Integer parent;

    private Integer rootGroup;

    private String remark;

    private ByteString extBin;

    private String extTxt;

    private Long createTime;

    private Long updateTime;

    public Builder() {
    }

    public Builder(DeviceGroupBean struct) {
      this._new = struct._new;
      this.modified = struct.modified;
      this.initialized = struct.initialized;
      this.id = struct.id;
      this.name = struct.name;
      this.leaf = struct.leaf;
      this.parent = struct.parent;
      this.rootGroup = struct.rootGroup;
      this.remark = struct.remark;
      this.extBin = struct.extBin;
      this.extTxt = struct.extTxt;
      this.createTime = struct.createTime;
      this.updateTime = struct.updateTime;
    }

    public Builder _new(Boolean _new) {
      if (_new == null) {
        throw new NullPointerException("Required field '_new' cannot be null");
      }
      this._new = _new;
      return this;
    }

    public Builder modified(Integer modified) {
      if (modified == null) {
        throw new NullPointerException("Required field 'modified' cannot be null");
      }
      this.modified = modified;
      return this;
    }

    public Builder initialized(Integer initialized) {
      if (initialized == null) {
        throw new NullPointerException("Required field 'initialized' cannot be null");
      }
      this.initialized = initialized;
      return this;
    }

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder leaf(Integer leaf) {
      this.leaf = leaf;
      return this;
    }

    public Builder parent(Integer parent) {
      this.parent = parent;
      return this;
    }

    public Builder rootGroup(Integer rootGroup) {
      this.rootGroup = rootGroup;
      return this;
    }

    public Builder remark(String remark) {
      this.remark = remark;
      return this;
    }

    public Builder extBin(ByteString extBin) {
      this.extBin = extBin;
      return this;
    }

    public Builder extTxt(String extTxt) {
      this.extTxt = extTxt;
      return this;
    }

    public Builder createTime(Long createTime) {
      this.createTime = createTime;
      return this;
    }

    public Builder updateTime(Long updateTime) {
      this.updateTime = updateTime;
      return this;
    }

    @Override
    public DeviceGroupBean build() {
      if (this._new == null) {
        throw new IllegalStateException("Required field '_new' is missing");
      }
      if (this.modified == null) {
        throw new IllegalStateException("Required field 'modified' is missing");
      }
      if (this.initialized == null) {
        throw new IllegalStateException("Required field 'initialized' is missing");
      }
      return new DeviceGroupBean(this);
    }

    @Override
    public void reset() {
      this._new = null;
      this.modified = null;
      this.initialized = null;
      this.id = null;
      this.name = null;
      this.leaf = null;
      this.parent = null;
      this.rootGroup = null;
      this.remark = null;
      this.extBin = null;
      this.extTxt = null;
      this.createTime = null;
      this.updateTime = null;
    }
  }

  private static final class DeviceGroupBeanAdapter implements Adapter<DeviceGroupBean, Builder> {
    @Override
    public void write(Protocol protocol, DeviceGroupBean struct) throws IOException {
      protocol.writeStructBegin("DeviceGroupBean");
      protocol.writeFieldBegin("_new", 1, TType.BOOL);
      protocol.writeBool(struct._new);
      protocol.writeFieldEnd();
      protocol.writeFieldBegin("modified", 2, TType.I32);
      protocol.writeI32(struct.modified);
      protocol.writeFieldEnd();
      protocol.writeFieldBegin("initialized", 3, TType.I32);
      protocol.writeI32(struct.initialized);
      protocol.writeFieldEnd();
      if (struct.id != null) {
        protocol.writeFieldBegin("id", 4, TType.I32);
        protocol.writeI32(struct.id);
        protocol.writeFieldEnd();
      }
      if (struct.name != null) {
        protocol.writeFieldBegin("name", 5, TType.STRING);
        protocol.writeString(struct.name);
        protocol.writeFieldEnd();
      }
      if (struct.leaf != null) {
        protocol.writeFieldBegin("leaf", 6, TType.I32);
        protocol.writeI32(struct.leaf);
        protocol.writeFieldEnd();
      }
      if (struct.parent != null) {
        protocol.writeFieldBegin("parent", 7, TType.I32);
        protocol.writeI32(struct.parent);
        protocol.writeFieldEnd();
      }
      if (struct.rootGroup != null) {
        protocol.writeFieldBegin("rootGroup", 8, TType.I32);
        protocol.writeI32(struct.rootGroup);
        protocol.writeFieldEnd();
      }
      if (struct.remark != null) {
        protocol.writeFieldBegin("remark", 9, TType.STRING);
        protocol.writeString(struct.remark);
        protocol.writeFieldEnd();
      }
      if (struct.extBin != null) {
        protocol.writeFieldBegin("extBin", 10, TType.STRING);
        protocol.writeBinary(struct.extBin);
        protocol.writeFieldEnd();
      }
      if (struct.extTxt != null) {
        protocol.writeFieldBegin("extTxt", 11, TType.STRING);
        protocol.writeString(struct.extTxt);
        protocol.writeFieldEnd();
      }
      if (struct.createTime != null) {
        protocol.writeFieldBegin("createTime", 12, TType.I64);
        protocol.writeI64(struct.createTime);
        protocol.writeFieldEnd();
      }
      if (struct.updateTime != null) {
        protocol.writeFieldBegin("updateTime", 13, TType.I64);
        protocol.writeI64(struct.updateTime);
        protocol.writeFieldEnd();
      }
      protocol.writeFieldStop();
      protocol.writeStructEnd();
    }

    @Override
    public DeviceGroupBean read(Protocol protocol, Builder builder) throws IOException {
      protocol.readStructBegin();
      while (true) {
        FieldMetadata field = protocol.readFieldBegin();
        if (field.typeId == TType.STOP) {
          break;
        }
        switch (field.fieldId) {
          case 1: {
            if (field.typeId == TType.BOOL) {
              boolean value = protocol.readBool();
              builder._new(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 2: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.modified(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 3: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.initialized(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 4: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.id(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 5: {
            if (field.typeId == TType.STRING) {
              String value = protocol.readString();
              builder.name(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 6: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.leaf(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 7: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.parent(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 8: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.rootGroup(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 9: {
            if (field.typeId == TType.STRING) {
              String value = protocol.readString();
              builder.remark(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 10: {
            if (field.typeId == TType.STRING) {
              ByteString value = protocol.readBinary();
              builder.extBin(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 11: {
            if (field.typeId == TType.STRING) {
              String value = protocol.readString();
              builder.extTxt(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 12: {
            if (field.typeId == TType.I64) {
              long value = protocol.readI64();
              builder.createTime(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 13: {
            if (field.typeId == TType.I64) {
              long value = protocol.readI64();
              builder.updateTime(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          default: {
            ProtocolUtil.skip(protocol, field.typeId);
          }
          break;
        }
        protocol.readFieldEnd();
      }
      protocol.readStructEnd();
      return builder.build();
    }

    @Override
    public DeviceGroupBean read(Protocol protocol) throws IOException {
      return read(protocol, new Builder());
    }
  }
}
