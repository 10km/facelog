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

    private String serverStackTraceMessage;

    @ThriftField(value=1, name="serverStackTraceMessage", requiredness=Requiredness.NONE)
    public String getServerStackTraceMessage() { return serverStackTraceMessage; }

    @ThriftField
    public void setServerStackTraceMessage(final String serverStackTraceMessage) { this.serverStackTraceMessage = serverStackTraceMessage; }
}
