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

    private int type;

    @ThriftField(value=4, name="type", requiredness=Requiredness.NONE)
    public int getType() { return type; }

    @ThriftField
    public void setType(final int type) { this.type = type; }
}
