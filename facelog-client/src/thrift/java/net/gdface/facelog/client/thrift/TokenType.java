package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;

public enum TokenType
{
    UNINITIALIZED(0), DEVICE(1), PERSON(2), ROOT(3);

    private final int value;

    TokenType(int value)
    {
        this.value = value;
    }

    @ThriftEnumValue
    public int getValue()
    {
        return value;
    }
}
