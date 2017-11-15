package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;

public enum ExceptionType
{
    UNKNOWN(0), DAO(1);

    private final int value;

    ExceptionType(int value)
    {
        this.value = value;
    }

    @ThriftEnumValue
    public int getValue()
    {
        return value;
    }
}
