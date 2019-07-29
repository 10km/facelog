// Automatically generated by the Thrifty compiler; do not edit!
// Generated on: 2019-07-29T02:45:46.942Z
// Source: J:\facelog\facelog-service\IFaceLog.thrift at 59:1
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
public final class FeatureBean implements Struct {
  public static final Adapter<FeatureBean, Builder> ADAPTER = new FeatureBeanAdapter();

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
  public final String md5;

  @ThriftField(
      fieldId = 5,
      isOptional = true
  )
  public final String version;

  @ThriftField(
      fieldId = 6,
      isOptional = true
  )
  public final Integer personId;

  @ThriftField(
      fieldId = 7,
      isOptional = true
  )
  public final ByteString feature;

  @ThriftField(
      fieldId = 8,
      isOptional = true
  )
  public final Long updateTime;

  private FeatureBean(Builder builder) {
    this._new = builder._new;
    this.modified = builder.modified;
    this.initialized = builder.initialized;
    this.md5 = builder.md5;
    this.version = builder.version;
    this.personId = builder.personId;
    this.feature = builder.feature;
    this.updateTime = builder.updateTime;
  }

  @Override
  @SuppressWarnings({"NumberEquality", "StringEquality"})
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null) return false;
    if (!(other instanceof FeatureBean)) return false;
    FeatureBean that = (FeatureBean) other;
    return (this._new == that._new || this._new.equals(that._new))
        && (this.modified == that.modified || this.modified.equals(that.modified))
        && (this.initialized == that.initialized || this.initialized.equals(that.initialized))
        && (this.md5 == that.md5 || (this.md5 != null && this.md5.equals(that.md5)))
        && (this.version == that.version || (this.version != null && this.version.equals(that.version)))
        && (this.personId == that.personId || (this.personId != null && this.personId.equals(that.personId)))
        && (this.feature == that.feature || (this.feature != null && this.feature.equals(that.feature)))
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
    code ^= (this.md5 == null) ? 0 : this.md5.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.version == null) ? 0 : this.version.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.personId == null) ? 0 : this.personId.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.feature == null) ? 0 : this.feature.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.updateTime == null) ? 0 : this.updateTime.hashCode();
    code *= 0x811c9dc5;
    return code;
  }

  @Override
  public String toString() {
    return "FeatureBean{_new=" + this._new + ", modified=" + this.modified + ", initialized=" + this.initialized + ", md5=" + this.md5 + ", version=" + this.version + ", personId=" + this.personId + ", feature=" + this.feature + ", updateTime=" + this.updateTime + "}";
  }

  @Override
  public void write(Protocol protocol) throws IOException {
    ADAPTER.write(protocol, this);
  }

  public static final class Builder implements StructBuilder<FeatureBean> {
    private Boolean _new;

    private Integer modified;

    private Integer initialized;

    private String md5;

    private String version;

    private Integer personId;

    private ByteString feature;

    private Long updateTime;

    public Builder() {
    }

    public Builder(FeatureBean struct) {
      this._new = struct._new;
      this.modified = struct.modified;
      this.initialized = struct.initialized;
      this.md5 = struct.md5;
      this.version = struct.version;
      this.personId = struct.personId;
      this.feature = struct.feature;
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

    public Builder md5(String md5) {
      this.md5 = md5;
      return this;
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }

    public Builder personId(Integer personId) {
      this.personId = personId;
      return this;
    }

    public Builder feature(ByteString feature) {
      this.feature = feature;
      return this;
    }

    public Builder updateTime(Long updateTime) {
      this.updateTime = updateTime;
      return this;
    }

    @Override
    public FeatureBean build() {
      if (this._new == null) {
        throw new IllegalStateException("Required field '_new' is missing");
      }
      if (this.modified == null) {
        throw new IllegalStateException("Required field 'modified' is missing");
      }
      if (this.initialized == null) {
        throw new IllegalStateException("Required field 'initialized' is missing");
      }
      return new FeatureBean(this);
    }

    @Override
    public void reset() {
      this._new = null;
      this.modified = null;
      this.initialized = null;
      this.md5 = null;
      this.version = null;
      this.personId = null;
      this.feature = null;
      this.updateTime = null;
    }
  }

  private static final class FeatureBeanAdapter implements Adapter<FeatureBean, Builder> {
    @Override
    public void write(Protocol protocol, FeatureBean struct) throws IOException {
      protocol.writeStructBegin("FeatureBean");
      protocol.writeFieldBegin("_new", 1, TType.BOOL);
      protocol.writeBool(struct._new);
      protocol.writeFieldEnd();
      protocol.writeFieldBegin("modified", 2, TType.I32);
      protocol.writeI32(struct.modified);
      protocol.writeFieldEnd();
      protocol.writeFieldBegin("initialized", 3, TType.I32);
      protocol.writeI32(struct.initialized);
      protocol.writeFieldEnd();
      if (struct.md5 != null) {
        protocol.writeFieldBegin("md5", 4, TType.STRING);
        protocol.writeString(struct.md5);
        protocol.writeFieldEnd();
      }
      if (struct.version != null) {
        protocol.writeFieldBegin("version", 5, TType.STRING);
        protocol.writeString(struct.version);
        protocol.writeFieldEnd();
      }
      if (struct.personId != null) {
        protocol.writeFieldBegin("personId", 6, TType.I32);
        protocol.writeI32(struct.personId);
        protocol.writeFieldEnd();
      }
      if (struct.feature != null) {
        protocol.writeFieldBegin("feature", 7, TType.STRING);
        protocol.writeBinary(struct.feature);
        protocol.writeFieldEnd();
      }
      if (struct.updateTime != null) {
        protocol.writeFieldBegin("updateTime", 8, TType.I64);
        protocol.writeI64(struct.updateTime);
        protocol.writeFieldEnd();
      }
      protocol.writeFieldStop();
      protocol.writeStructEnd();
    }

    @Override
    public FeatureBean read(Protocol protocol, Builder builder) throws IOException {
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
            if (field.typeId == TType.STRING) {
              String value = protocol.readString();
              builder.md5(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 5: {
            if (field.typeId == TType.STRING) {
              String value = protocol.readString();
              builder.version(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 6: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.personId(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 7: {
            if (field.typeId == TType.STRING) {
              ByteString value = protocol.readBinary();
              builder.feature(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 8: {
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
    public FeatureBean read(Protocol protocol) throws IOException {
      return read(protocol, new Builder());
    }
  }
}
