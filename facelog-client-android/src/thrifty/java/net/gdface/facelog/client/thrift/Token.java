// Automatically generated by the Thrifty compiler; do not edit!
// Generated on: 2019-07-06T06:24:05.409Z
// Source: J:\facelog\facelog-service\IFaceLog.thrift at 203:1
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
public final class Token implements Struct {
  public static final Adapter<Token, Builder> ADAPTER = new TokenAdapter();

  @ThriftField(
      fieldId = 1,
      isRequired = true
  )
  public final Integer id;

  @ThriftField(
      fieldId = 2,
      isRequired = true
  )
  public final Integer t1;

  @ThriftField(
      fieldId = 3,
      isRequired = true
  )
  public final Integer t2;

  @ThriftField(
      fieldId = 4,
      isRequired = true
  )
  public final Integer t3;

  @ThriftField(
      fieldId = 5,
      isRequired = true
  )
  public final Integer t4;

  @ThriftField(
      fieldId = 6,
      isOptional = true
  )
  public final TokenType type;

  private Token(Builder builder) {
    this.id = builder.id;
    this.t1 = builder.t1;
    this.t2 = builder.t2;
    this.t3 = builder.t3;
    this.t4 = builder.t4;
    this.type = builder.type;
  }

  @Override
  @SuppressWarnings("NumberEquality")
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null) return false;
    if (!(other instanceof Token)) return false;
    Token that = (Token) other;
    return (this.id == that.id || this.id.equals(that.id))
        && (this.t1 == that.t1 || this.t1.equals(that.t1))
        && (this.t2 == that.t2 || this.t2.equals(that.t2))
        && (this.t3 == that.t3 || this.t3.equals(that.t3))
        && (this.t4 == that.t4 || this.t4.equals(that.t4))
        && (this.type == that.type || (this.type != null && this.type.equals(that.type)));
  }

  @Override
  public int hashCode() {
    int code = 16777619;
    code ^= this.id.hashCode();
    code *= 0x811c9dc5;
    code ^= this.t1.hashCode();
    code *= 0x811c9dc5;
    code ^= this.t2.hashCode();
    code *= 0x811c9dc5;
    code ^= this.t3.hashCode();
    code *= 0x811c9dc5;
    code ^= this.t4.hashCode();
    code *= 0x811c9dc5;
    code ^= (this.type == null) ? 0 : this.type.hashCode();
    code *= 0x811c9dc5;
    return code;
  }

  @Override
  public String toString() {
    return "Token{id=" + this.id + ", t1=" + this.t1 + ", t2=" + this.t2 + ", t3=" + this.t3 + ", t4=" + this.t4 + ", type=" + this.type + "}";
  }

  @Override
  public void write(Protocol protocol) throws IOException {
    ADAPTER.write(protocol, this);
  }

  public static final class Builder implements StructBuilder<Token> {
    private Integer id;

    private Integer t1;

    private Integer t2;

    private Integer t3;

    private Integer t4;

    private TokenType type;

    public Builder() {
    }

    public Builder(Token struct) {
      this.id = struct.id;
      this.t1 = struct.t1;
      this.t2 = struct.t2;
      this.t3 = struct.t3;
      this.t4 = struct.t4;
      this.type = struct.type;
    }

    public Builder id(Integer id) {
      if (id == null) {
        throw new NullPointerException("Required field 'id' cannot be null");
      }
      this.id = id;
      return this;
    }

    public Builder t1(Integer t1) {
      if (t1 == null) {
        throw new NullPointerException("Required field 't1' cannot be null");
      }
      this.t1 = t1;
      return this;
    }

    public Builder t2(Integer t2) {
      if (t2 == null) {
        throw new NullPointerException("Required field 't2' cannot be null");
      }
      this.t2 = t2;
      return this;
    }

    public Builder t3(Integer t3) {
      if (t3 == null) {
        throw new NullPointerException("Required field 't3' cannot be null");
      }
      this.t3 = t3;
      return this;
    }

    public Builder t4(Integer t4) {
      if (t4 == null) {
        throw new NullPointerException("Required field 't4' cannot be null");
      }
      this.t4 = t4;
      return this;
    }

    public Builder type(TokenType type) {
      this.type = type;
      return this;
    }

    @Override
    public Token build() {
      if (this.id == null) {
        throw new IllegalStateException("Required field 'id' is missing");
      }
      if (this.t1 == null) {
        throw new IllegalStateException("Required field 't1' is missing");
      }
      if (this.t2 == null) {
        throw new IllegalStateException("Required field 't2' is missing");
      }
      if (this.t3 == null) {
        throw new IllegalStateException("Required field 't3' is missing");
      }
      if (this.t4 == null) {
        throw new IllegalStateException("Required field 't4' is missing");
      }
      return new Token(this);
    }

    @Override
    public void reset() {
      this.id = null;
      this.t1 = null;
      this.t2 = null;
      this.t3 = null;
      this.t4 = null;
      this.type = null;
    }
  }

  private static final class TokenAdapter implements Adapter<Token, Builder> {
    @Override
    public void write(Protocol protocol, Token struct) throws IOException {
      protocol.writeStructBegin("Token");
      protocol.writeFieldBegin("id", 1, TType.I32);
      protocol.writeI32(struct.id);
      protocol.writeFieldEnd();
      protocol.writeFieldBegin("t1", 2, TType.I32);
      protocol.writeI32(struct.t1);
      protocol.writeFieldEnd();
      protocol.writeFieldBegin("t2", 3, TType.I32);
      protocol.writeI32(struct.t2);
      protocol.writeFieldEnd();
      protocol.writeFieldBegin("t3", 4, TType.I32);
      protocol.writeI32(struct.t3);
      protocol.writeFieldEnd();
      protocol.writeFieldBegin("t4", 5, TType.I32);
      protocol.writeI32(struct.t4);
      protocol.writeFieldEnd();
      if (struct.type != null) {
        protocol.writeFieldBegin("type", 6, TType.I32);
        protocol.writeI32(struct.type.value);
        protocol.writeFieldEnd();
      }
      protocol.writeFieldStop();
      protocol.writeStructEnd();
    }

    @Override
    public Token read(Protocol protocol, Builder builder) throws IOException {
      protocol.readStructBegin();
      while (true) {
        FieldMetadata field = protocol.readFieldBegin();
        if (field.typeId == TType.STOP) {
          break;
        }
        switch (field.fieldId) {
          case 1: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.id(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 2: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.t1(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 3: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.t2(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 4: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.t3(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 5: {
            if (field.typeId == TType.I32) {
              int value = protocol.readI32();
              builder.t4(value);
            } else {
              ProtocolUtil.skip(protocol, field.typeId);
            }
          }
          break;
          case 6: {
            if (field.typeId == TType.I32) {
              int i32_0 = protocol.readI32();
              net.gdface.facelog.client.thrift.TokenType value = net.gdface.facelog.client.thrift.TokenType.findByValue(i32_0);
              if (value == null) {
                throw new ThriftException(ThriftException.Kind.PROTOCOL_ERROR, "Unexpected value for enum-type TokenType: " + i32_0);
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
    public Token read(Protocol protocol) throws IOException {
      return read(protocol, new Builder());
    }
  }
}
