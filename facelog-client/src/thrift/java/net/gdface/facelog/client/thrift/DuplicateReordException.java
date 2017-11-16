package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

@ThriftStruct("DuplicateReordException")
public final class DuplicateReordException extends Exception
{
    private static final long serialVersionUID = 1L;

    public DuplicateReordException() {
    }

    private String serviceMessage;

    @ThriftField(value=1, name="serviceMessage", requiredness=Requiredness.NONE)
    public String getServiceMessage() { return serviceMessage; }

    @ThriftField
    public void setServiceMessage(final String serviceMessage) { this.serviceMessage = serviceMessage; }

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

    private String message;

    @ThriftField(value=4, name="message", requiredness=Requiredness.NONE)
    public String getMessage() { return message; }

    @ThriftField
    public void setMessage(final String message) { this.message = message; }
}
