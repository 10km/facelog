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

    private int type;

    @ThriftField(value=1, name="type", requiredness=Requiredness.NONE)
    public int getType() { return type; }

    @ThriftField
    public void setType(final int type) { this.type = type; }

    private String serverMessage;

    @ThriftField(value=2, name="serverMessage", requiredness=Requiredness.NONE)
    public String getServerMessage() { return serverMessage; }

    @ThriftField
    public void setServerMessage(final String serverMessage) { this.serverMessage = serverMessage; }

    private String causeClass;

    @ThriftField(value=3, name="causeClass", requiredness=Requiredness.NONE)
    public String getCauseClass() { return causeClass; }

    @ThriftField
    public void setCauseClass(final String causeClass) { this.causeClass = causeClass; }

    private String serverStackTraceMessage;

    @ThriftField(value=4, name="serverStackTraceMessage", requiredness=Requiredness.NONE)
    public String getServerStackTraceMessage() { return serverStackTraceMessage; }

    @ThriftField
    public void setServerStackTraceMessage(final String serverStackTraceMessage) { this.serverStackTraceMessage = serverStackTraceMessage; }
}
