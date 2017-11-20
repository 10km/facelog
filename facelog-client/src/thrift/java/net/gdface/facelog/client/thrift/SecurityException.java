package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

@ThriftStruct("SecurityException")
public final class SecurityException extends Exception
{
    private static final long serialVersionUID = 1L;

    public SecurityException() {
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

    private DeviceExceptionType type;

    @ThriftField(value=4, name="type", requiredness=Requiredness.NONE)
    public DeviceExceptionType getType() { return type; }

    @ThriftField
    public void setType(final DeviceExceptionType type) { this.type = type; }

    private int deviceID;

    @ThriftField(value=5, name="deviceID", requiredness=Requiredness.NONE)
    public int getDeviceID() { return deviceID; }

    @ThriftField
    public void setDeviceID(final int deviceID) { this.deviceID = deviceID; }
}
