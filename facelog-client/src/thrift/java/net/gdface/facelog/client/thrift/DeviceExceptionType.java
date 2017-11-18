package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;

public enum DeviceExceptionType
{
    UNCLASSIFIED(0), INVALID_MAC(1), INVALID_SN(2), OCCUPIED_SN(3);

    private final int value;

    DeviceExceptionType(int value)
    {
        this.value = value;
    }

    @ThriftEnumValue
    public int getValue()
    {
        return value;
    }
}
