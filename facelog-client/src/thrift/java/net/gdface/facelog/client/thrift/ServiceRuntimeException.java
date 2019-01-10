package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

@ThriftStruct("ServiceRuntimeException")
public final class ServiceRuntimeException extends Exception
{
    private static final long serialVersionUID = 1L;

    public ServiceRuntimeException() {
    }

    private String message;

    @ThriftField(value=1, name="message", requiredness=Requiredness.OPTIONAL)
    public String getMessage() { return message; }

    @ThriftField
    public void setMessage(final String message) { this.message = message; }

    private String causeClass;

    @ThriftField(value=2, name="causeClass", requiredness=Requiredness.OPTIONAL)
    public String getCauseClass() { return causeClass; }

    @ThriftField
    public void setCauseClass(final String causeClass) { this.causeClass = causeClass; }

    private String serviceStackTraceMessage;

    @ThriftField(value=3, name="serviceStackTraceMessage", requiredness=Requiredness.OPTIONAL)
    public String getServiceStackTraceMessage() { return serviceStackTraceMessage; }

    @ThriftField
    public void setServiceStackTraceMessage(final String serviceStackTraceMessage) { this.serviceStackTraceMessage = serviceStackTraceMessage; }

    private String causeFields;

    @ThriftField(value=4, name="causeFields", requiredness=Requiredness.OPTIONAL)
    public String getCauseFields() { return causeFields; }

    @ThriftField
    public void setCauseFields(final String causeFields) { this.causeFields = causeFields; }

    private int type;

    @ThriftField(value=5, name="type", requiredness=Requiredness.REQUIRED)
    public int getType() { return type; }

    @ThriftField
    public void setType(final int type) { this.type = type; }
}
