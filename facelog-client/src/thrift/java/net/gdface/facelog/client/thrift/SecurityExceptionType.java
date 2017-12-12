package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;

public enum SecurityExceptionType
{
    UNCLASSIFIED(0), INVALID_MAC(1), INVALID_SN(2), OCCUPIED_SN(3), INVALID_TOKEN(4), INVALID_DEVICE_ID(5), INVALID_PERSON_ID(6), INVALID_PASSWORD(7);

    private final int value;

    SecurityExceptionType(int value)
    {
        this.value = value;
    }

    @ThriftEnumValue
    public int getValue()
    {
        return value;
    }
}
