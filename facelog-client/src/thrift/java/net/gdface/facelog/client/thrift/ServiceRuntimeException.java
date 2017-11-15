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

    private ExceptionType type;

    @ThriftField(value=1, name="type", requiredness=Requiredness.NONE)
    public ExceptionType getType() { return type; }

    @ThriftField
    public void setType(final ExceptionType type) { this.type = type; }

    private String serverStackTraceMessage;

    @ThriftField(value=2, name="serverStackTraceMessage", requiredness=Requiredness.NONE)
    public String getServerStackTraceMessage() { return serverStackTraceMessage; }

    @ThriftField
    public void setServerStackTraceMessage(final String serverStackTraceMessage) { this.serverStackTraceMessage = serverStackTraceMessage; }
}
