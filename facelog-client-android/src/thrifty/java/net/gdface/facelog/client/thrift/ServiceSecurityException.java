// Automatically generated by the Thrifty compiler; do not edit!
// Generated on: 2019-03-28T05:46:24.716Z
// Source: J:\facelog\facelog-service\IFaceLog.thrift at 199:1
package net.gdface.facelog.client.thrift;

import com.microsoft.thrifty.Adapter;
import com.microsoft.thrifty.Struct;
import com.microsoft.thrifty.StructBuilder;
import com.microsoft.thrifty.TType;
import com.microsoft.thrifty.ThriftException;
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
public final class ServiceSecurityException extends Exception implements Struct {
  public static final Adapter<ServiceSecurityException, Builder> ADAPTER = new ServiceSecurityExceptionAdapter();

  @ThriftField(
      fieldId = 1,
      isOptional = true
  )
  public final String message;

  @ThriftField(
      fieldId = 2,
      isOptional = true
  )
  public final String causeClass;

  @ThriftField(
      fieldId = 3,
      isOptional = true
  )
  public final String serviceStackTraceMessage;

  @ThriftField(
      fieldId = 4,
      isOptional = true
  )
  public final String causeFields;

  @ThriftField(
      fieldId = 5,
      isOptional = true
  )
  public final Integer deviceID;

  @ThriftField(
      fieldId = 6,
      isOptional = true
  )
  public final SecurityExceptionType type;

  private ServiceSecurityException(Builder builder) {
    this.message = builder.message;
    this.causeClass = builder.causeClass;
    this.serviceStackTraceMessage = builder.serviceStackTraceMessage;
    this.causeFields = builder.causeFields;
    this.deviceID = builder.deviceID;
    this.type = builder.type;
  }

  @Override
  @SuppressWarnings({"StringEquality", "NumberEquality"})
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null) return false;
    if (!(other instanceof ServiceSecurityException)) return false;
    ServiceSecurityException that = (ServiceSecurityException) other;
    return (this.message == that.message || (this.message != null && this.message.equals(that.message)))
        && (this.causeClass == that.causeClass || (this.causeClass != null && this.causeClass.equals(that.causeClass)))
        && (this.serviceStackTraceMessage == that.serviceStackTraceMessage || (this.serviceStackTraceMessage != null && this.serviceStackTraceMessage.equals(that.serviceStackTraceMessage)))
        && (this.causeFields == that.causeFields || (this.causeFields != null && this.causeFields.equals(that.causeFields)))
        && (this.deviceID == that.deviceID || (this.deviceID != null && this.deviceID.equals(that.deviceID)))
        && (this.type == that.type || (this.type != null && this.type.equals(that.type)));
  }

  @Override
  public int hashCode() {
    int code = 16777619;
    code ^= (this.message == null) ? 0 : this.message.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.causeClass == null) ? 0 : this.causeClass.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.serviceStackTraceMessage == null) ? 0 : this.serviceStackTraceMessage.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.causeFields == null) ? 0 : this.causeFields.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.deviceID == null) ? 0 : this.deviceID.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.type == null) ? 0 : this.type.hashCode();
    code *= 0x811c9dc5;
    return code;
  }

  @Override
  public String toString() {
    return "ServiceSecurityException{message=" + this.message + ", causeClass=" + this.causeClass + ", serviceStackTraceMessage=" + this.serviceStackTraceMessage + ", causeFields=" + this.causeFields + ", deviceID=" + this.deviceID + ", type=" + this.type + "}";
  }

  @Override
  public void write(Protocol protocol) throws IOException {
    ADAPTER.write(protocol, this);
  }

  public static final class Builder implements StructBuilder<ServiceSecurityException> {
    private String message;

    private String causeClass;

    private String serviceStackTraceMessage;

    private String causeFields;

    private Integer deviceID;

    private SecurityExceptionType type;

    public Builder() {
    }

    public Builder(ServiceSecurityException struct) {
      this.message = struct.message;
      this.causeClass = struct.causeClass;
      this.serviceStackTraceMessage = struct.serviceStackTraceMessage;
      this.causeFields = struct.causeFields;
      this.deviceID = struct.deviceID;
      this.type = struct.type;
    }

    public Builder message(String message) {
      this.message = message;
      return this;
    }

    public Builder causeClass(String causeClass) {
      this.causeClass = causeClass;
      return this;
    }

    public Builder serviceStackTraceMessage(String serviceStackTraceMessage) {
      this.serviceStackTraceMessage = serviceStackTraceMessage;
      return this;
    }

    public Builder causeFields(String causeFields) {
      this.causeFields = causeFields;
      return this;
    }

    public Builder deviceID(Integer deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    public Builder type(SecurityExceptionType type) {
      this.type = type;
      return this;
    }

    @Override
    public ServiceSecurityException build() {
      return new ServiceSecurityException(this);
    }

    @Override
    public void reset() {
      this.message = null;
      this.causeClass = null;
      this.serviceStackTraceMessage = null;
      this.causeFields = null;
      this.deviceID = null;
      this.type = null;
    }
  }

  private static final class ServiceSecurityExceptionAdapter implements Adapter<ServiceSecurityException, Builder> {
    @Override
    public void write(Protocol protocol, ServiceSecurityException struct) throws IOException {
      protocol.writeStructBegin("ServiceSecurityException");
      if (struct.message != null) {
        protocol.writeFieldBegin("message", 1, TType.STRING);
        protocol.writeString(struct.message);
        protocol.writeFieldEnd();
      }
      if (struct.causeClass != null) {
        protocol.writeFieldBegin("causeClass", 2, TType.STRING);
        protocol.writeString(struct.causeClass);
        protocol.writeFieldEnd();
      }
      if (struct.serviceStackTraceMessage != null) {
        protocol.writeFieldBegin("serviceStackTraceMessage", 3, TType.STRING);
        protocol.writeString(struct.serviceStackTraceMessage);
        protocol.writeFieldEnd();
      }
      if (struct.causeFields != null) {
        protocol.writeFieldBegin("causeFields", 4, TType.STRING);
        protocol.writeString(struct.causeFields);
        protocol.writeFieldEnd();
      }
      if (struct.deviceID != null) {
        protocol.writeFieldBegin("deviceID", 5, TType.I32);
        protocol.writeI32(struct.deviceID);
        protocol.writeFieldEnd();
      }
      if (struct.type != null) {
        protocol.writeFieldBegin("type", 6, TType.I32);
        protocol.writeI32(struct.type.value);
        protocol.writeFieldEnd();
      }
      protocol.writeFieldStop();
      protocol.writeStructEnd();
    }

    @Override
    public ServiceSecurityException read(Protocol protocol, Builder builder) throws IOException {
      protocol.readStructBegin();
      while (true) {
        FieldMetadata field = protocol.readFieldBegin();
        if (field.typeId == TType.STOP) {
          break;
        }
        switch (field.fieldId) {
          case 1: {
            if (field.typeId == TType.STRING) {
              String value = protocol.readString();
              builder.message(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 2: {
            if (field.typeId == TType.STRING) {
              String value = protocol.readString();
              builder.causeClass(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 3: {
            if (field.typeId == TType.STRING) {
              String value = protocol.readString();
              builder.serviceStackTraceMessage(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 4: {
            if (field.typeId == TType.STRING) {
              String value = protocol.readString();
              builder.causeFields(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 5: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.deviceID(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 6: {
            if (field.typeId == TType.I32) {
              int i32_0 = protocol.readI32();
              net.gdface.facelog.client.thrift.SecurityExceptionType value = net.gdface.facelog.client.thrift.SecurityExceptionType.findByValue(i32_0);
              if (value == null) {
                throw new ThriftException(ThriftException.Kind.PROTOCOL_ERROR, "Unexpected value for enum-type SecurityExceptionType: " + i32_0);
              }
              builder.type(value);
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
    public ServiceSecurityException read(Protocol protocol) throws IOException {
      return read(protocol, new Builder());
    }
  }
}
