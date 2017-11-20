package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

@ThriftStruct("ServiceSecurityException")
public final class ServiceSecurityException extends Exception
{
    private static final long serialVersionUID = 1L;

    public ServiceSecurityException() {
    }

    private String message;

    @ThriftField(value=1, name="message", requiredness=Requiredness.NONE)
    public String getMessage() { return message; }

    @ThriftField
    public void setMessage(final String message) { this.message = message; }

    private String causeClass;

    @ThriftField(value=2, name="causeClass", requiredness=Requiredness.NONE)
    public String getCauseClass() { return causeClass; }

    @ThriftField
    public void setCauseClass(final String causeClass) { this.causeClass = causeClass; }

    private String serviceStackTraceMessage;

    @ThriftField(value=3, name="serviceStackTraceMessage", requiredness=Requiredness.NONE)
    public String getServiceStackTraceMessage() { return serviceStackTraceMessage; }

    @ThriftField
    public void setServiceStackTraceMessage(final String serviceStackTraceMessage) { this.serviceStackTraceMessage = serviceStackTraceMessage; }

    private String causeField;

    @ThriftField(value=4, name="causeField", requiredness=Requiredness.NONE)
    public String getCauseField() { return causeField; }

    @ThriftField
    public void setCauseField(final String causeField) { this.causeField = causeField; }

    private SecurityExceptionType type;

    @ThriftField(value=5, name="type", requiredness=Requiredness.NONE)
    public SecurityExceptionType getType() { return type; }

    @ThriftField
    public void setType(final SecurityExceptionType type) { this.type = type; }

    private int deviceID;

    @ThriftField(value=6, name="deviceID", requiredness=Requiredness.NONE)
    public int getDeviceID() { return deviceID; }

    @ThriftField
    public void setDeviceID(final int deviceID) { this.deviceID = deviceID; }
}
