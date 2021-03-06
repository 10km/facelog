/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Threading.Tasks;
using Thrift;
using Thrift.Collections;
using System.Runtime.Serialization;
using Thrift.Protocol;
using Thrift.Transport;


#if !SILVERLIGHT
[Serializable]
#endif
public partial class ServiceRuntimeException : TException, TBase
{

  public string Message { get; set; }

  public string CauseClass { get; set; }

  public string ServiceStackTraceMessage { get; set; }

  public string CauseFields { get; set; }

  public int Type { get; set; }

  public ServiceRuntimeException() {
  }

  public ServiceRuntimeException(int type) : this() {
    this.Type = type;
  }

  public void Read (TProtocol iprot)
  {
    iprot.IncrementRecursionDepth();
    try
    {
      bool isset_type = false;
      TField field;
      iprot.ReadStructBegin();
      while (true)
      {
        field = iprot.ReadFieldBegin();
        if (field.Type == TType.Stop) { 
          break;
        }
        switch (field.ID)
        {
          case 1:
            if (field.Type == TType.String) {
              Message = iprot.ReadString();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 2:
            if (field.Type == TType.String) {
              CauseClass = iprot.ReadString();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 3:
            if (field.Type == TType.String) {
              ServiceStackTraceMessage = iprot.ReadString();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 4:
            if (field.Type == TType.String) {
              CauseFields = iprot.ReadString();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 5:
            if (field.Type == TType.I32) {
              Type = iprot.ReadI32();
              isset_type = true;
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          default: 
            TProtocolUtil.Skip(iprot, field.Type);
            break;
        }
        iprot.ReadFieldEnd();
      }
      iprot.ReadStructEnd();
      if (!isset_type)
        throw new TProtocolException(TProtocolException.INVALID_DATA, "required field Type not set");
    }
    finally
    {
      iprot.DecrementRecursionDepth();
    }
  }

  public void Write(TProtocol oprot) {
    oprot.IncrementRecursionDepth();
    try
    {
      TStruct struc = new TStruct("ServiceRuntimeException");
      oprot.WriteStructBegin(struc);
      TField field = new TField();
      if (Message != null) {
        field.Name = "message";
        field.Type = TType.String;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(Message);
        oprot.WriteFieldEnd();
      }
      if (CauseClass != null) {
        field.Name = "causeClass";
        field.Type = TType.String;
        field.ID = 2;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(CauseClass);
        oprot.WriteFieldEnd();
      }
      if (ServiceStackTraceMessage != null) {
        field.Name = "serviceStackTraceMessage";
        field.Type = TType.String;
        field.ID = 3;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(ServiceStackTraceMessage);
        oprot.WriteFieldEnd();
      }
      if (CauseFields != null) {
        field.Name = "causeFields";
        field.Type = TType.String;
        field.ID = 4;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(CauseFields);
        oprot.WriteFieldEnd();
      }
      field.Name = "type";
      field.Type = TType.I32;
      field.ID = 5;
      oprot.WriteFieldBegin(field);
      oprot.WriteI32(Type);
      oprot.WriteFieldEnd();
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }
    finally
    {
      oprot.DecrementRecursionDepth();
    }
  }

  public override string ToString() {
    StringBuilder __sb = new StringBuilder("ServiceRuntimeException(");
    bool __first = true;
    if (Message != null) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Message: ");
      __sb.Append(Message);
    }
    if (CauseClass != null) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("CauseClass: ");
      __sb.Append(CauseClass);
    }
    if (ServiceStackTraceMessage != null) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("ServiceStackTraceMessage: ");
      __sb.Append(ServiceStackTraceMessage);
    }
    if (CauseFields != null) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("CauseFields: ");
      __sb.Append(CauseFields);
    }
    if(!__first) { __sb.Append(", "); }
    __sb.Append("Type: ");
    __sb.Append(Type);
    __sb.Append(")");
    return __sb.ToString();
  }

}

