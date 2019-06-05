// Automatically generated by the Thrifty compiler; do not edit!
// Generated on: 2019-06-05T02:34:09.038Z
// Source: J:\facelog\facelog-service\IFaceLog.thrift at 84:1
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

@Generated(
    value = "com.microsoft.thrifty.gen.ThriftyCodeGenerator",
    comments = "https://github.com/microsoft/thrifty"
)
public final class LogBean implements Struct {
  public static final Adapter<LogBean, Builder> ADAPTER = new LogBeanAdapter();

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
  public final Integer personId;

  @ThriftField(
      fieldId = 6,
      isOptional = true
  )
  public final Integer deviceId;

  @ThriftField(
      fieldId = 7,
      isOptional = true
  )
  public final String verifyFeature;

  @ThriftField(
      fieldId = 8,
      isOptional = true
  )
  public final Integer compareFace;

  @ThriftField(
      fieldId = 9,
      isOptional = true
  )
  public final Integer verifyStatus;

  @ThriftField(
      fieldId = 10,
      isOptional = true
  )
  public final Double similarty;

  @ThriftField(
      fieldId = 11,
      isOptional = true
  )
  public final Long verifyTime;

  @ThriftField(
      fieldId = 12,
      isOptional = true
  )
  public final Long createTime;

  private LogBean(Builder builder) {
    this._new = builder._new;
    this.modified = builder.modified;
    this.initialized = builder.initialized;
    this.id = builder.id;
    this.personId = builder.personId;
    this.deviceId = builder.deviceId;
    this.verifyFeature = builder.verifyFeature;
    this.compareFace = builder.compareFace;
    this.verifyStatus = builder.verifyStatus;
    this.similarty = builder.similarty;
    this.verifyTime = builder.verifyTime;
    this.createTime = builder.createTime;
  }

  @Override
  @SuppressWarnings({"NumberEquality", "StringEquality"})
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null) return false;
    if (!(other instanceof LogBean)) return false;
    LogBean that = (LogBean) other;
    return (this._new == that._new || this._new.equals(that._new))
        && (this.modified == that.modified || this.modified.equals(that.modified))
        && (this.initialized == that.initialized || this.initialized.equals(that.initialized))
        && (this.id == that.id || (this.id != null && this.id.equals(that.id)))
        && (this.personId == that.personId || (this.personId != null && this.personId.equals(that.personId)))
        && (this.deviceId == that.deviceId || (this.deviceId != null && this.deviceId.equals(that.deviceId)))
        && (this.verifyFeature == that.verifyFeature || (this.verifyFeature != null && this.verifyFeature.equals(that.verifyFeature)))
        && (this.compareFace == that.compareFace || (this.compareFace != null && this.compareFace.equals(that.compareFace)))
        && (this.verifyStatus == that.verifyStatus || (this.verifyStatus != null && this.verifyStatus.equals(that.verifyStatus)))
        && (this.similarty == that.similarty || (this.similarty != null && this.similarty.equals(that.similarty)))
        && (this.verifyTime == that.verifyTime || (this.verifyTime != null && this.verifyTime.equals(that.verifyTime)))
        && (this.createTime == that.createTime || (this.createTime != null && this.createTime.equals(that.createTime)));
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
    code ^= (this.personId == null) ? 0 : this.personId.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.deviceId == null) ? 0 : this.deviceId.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.verifyFeature == null) ? 0 : this.verifyFeature.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.compareFace == null) ? 0 : this.compareFace.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.verifyStatus == null) ? 0 : this.verifyStatus.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.similarty == null) ? 0 : this.similarty.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.verifyTime == null) ? 0 : this.verifyTime.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.createTime == null) ? 0 : this.createTime.hashCode();
    code *= 0x811c9dc5;
    return code;
  }

  @Override
  public String toString() {
    return "LogBean{_new=" + this._new + ", modified=" + this.modified + ", initialized=" + this.initialized + ", id=" + this.id + ", personId=" + this.personId + ", deviceId=" + this.deviceId + ", verifyFeature=" + this.verifyFeature + ", compareFace=" + this.compareFace + ", verifyStatus=" + this.verifyStatus + ", similarty=" + this.similarty + ", verifyTime=" + this.verifyTime + ", createTime=" + this.createTime + "}";
  }

  @Override
  public void write(Protocol protocol) throws IOException {
    ADAPTER.write(protocol, this);
  }

  public static final class Builder implements StructBuilder<LogBean> {
    private Boolean _new;

    private Integer modified;

    private Integer initialized;

    private Integer id;

    private Integer personId;

    private Integer deviceId;

    private String verifyFeature;

    private Integer compareFace;

    private Integer verifyStatus;

    private Double similarty;

    private Long verifyTime;

    private Long createTime;

    public Builder() {
    }

    public Builder(LogBean struct) {
      this._new = struct._new;
      this.modified = struct.modified;
      this.initialized = struct.initialized;
      this.id = struct.id;
      this.personId = struct.personId;
      this.deviceId = struct.deviceId;
      this.verifyFeature = struct.verifyFeature;
      this.compareFace = struct.compareFace;
      this.verifyStatus = struct.verifyStatus;
      this.similarty = struct.similarty;
      this.verifyTime = struct.verifyTime;
      this.createTime = struct.createTime;
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

    public Builder personId(Integer personId) {
      this.personId = personId;
      return this;
    }

    public Builder deviceId(Integer deviceId) {
      this.deviceId = deviceId;
      return this;
    }

    public Builder verifyFeature(String verifyFeature) {
      this.verifyFeature = verifyFeature;
      return this;
    }

    public Builder compareFace(Integer compareFace) {
      this.compareFace = compareFace;
      return this;
    }

    public Builder verifyStatus(Integer verifyStatus) {
      this.verifyStatus = verifyStatus;
      return this;
    }

    public Builder similarty(Double similarty) {
      this.similarty = similarty;
      return this;
    }

    public Builder verifyTime(Long verifyTime) {
      this.verifyTime = verifyTime;
      return this;
    }

    public Builder createTime(Long createTime) {
      this.createTime = createTime;
      return this;
    }

    @Override
    public LogBean build() {
      if (this._new == null) {
        throw new IllegalStateException("Required field '_new' is missing");
      }
      if (this.modified == null) {
        throw new IllegalStateException("Required field 'modified' is missing");
      }
      if (this.initialized == null) {
        throw new IllegalStateException("Required field 'initialized' is missing");
      }
      return new LogBean(this);
    }

    @Override
    public void reset() {
      this._new = null;
      this.modified = null;
      this.initialized = null;
      this.id = null;
      this.personId = null;
      this.deviceId = null;
      this.verifyFeature = null;
      this.compareFace = null;
      this.verifyStatus = null;
      this.similarty = null;
      this.verifyTime = null;
      this.createTime = null;
    }
  }

  private static final class LogBeanAdapter implements Adapter<LogBean, Builder> {
    @Override
    public void write(Protocol protocol, LogBean struct) throws IOException {
      protocol.writeStructBegin("LogBean");
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
      if (struct.personId != null) {
        protocol.writeFieldBegin("personId", 5, TType.I32);
        protocol.writeI32(struct.personId);
        protocol.writeFieldEnd();
      }
      if (struct.deviceId != null) {
        protocol.writeFieldBegin("deviceId", 6, TType.I32);
        protocol.writeI32(struct.deviceId);
        protocol.writeFieldEnd();
      }
      if (struct.verifyFeature != null) {
        protocol.writeFieldBegin("verifyFeature", 7, TType.STRING);
        protocol.writeString(struct.verifyFeature);
        protocol.writeFieldEnd();
      }
      if (struct.compareFace != null) {
        protocol.writeFieldBegin("compareFace", 8, TType.I32);
        protocol.writeI32(struct.compareFace);
        protocol.writeFieldEnd();
      }
      if (struct.verifyStatus != null) {
        protocol.writeFieldBegin("verifyStatus", 9, TType.I32);
        protocol.writeI32(struct.verifyStatus);
        protocol.writeFieldEnd();
      }
      if (struct.similarty != null) {
        protocol.writeFieldBegin("similarty", 10, TType.DOUBLE);
        protocol.writeDouble(struct.similarty);
        protocol.writeFieldEnd();
      }
      if (struct.verifyTime != null) {
        protocol.writeFieldBegin("verifyTime", 11, TType.I64);
        protocol.writeI64(struct.verifyTime);
        protocol.writeFieldEnd();
      }
      if (struct.createTime != null) {
        protocol.writeFieldBegin("createTime", 12, TType.I64);
        protocol.writeI64(struct.createTime);
        protocol.writeFieldEnd();
      }
      protocol.writeFieldStop();
      protocol.writeStructEnd();
    }

    @Override
    public LogBean read(Protocol protocol, Builder builder) throws IOException {
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
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.personId(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 6: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.deviceId(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 7: {
            if (field.typeId == TType.STRING) {
              String value = protocol.readString();
              builder.verifyFeature(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 8: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.compareFace(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 9: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.verifyStatus(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 10: {
            if (field.typeId == TType.DOUBLE) {
              double value = protocol.readDouble();
              builder.similarty(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 11: {
            if (field.typeId == TType.I64) {
              long value = protocol.readI64();
              builder.verifyTime(value);
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
    public LogBean read(Protocol protocol) throws IOException {
      return read(protocol, new Builder());
    }
  }
}
