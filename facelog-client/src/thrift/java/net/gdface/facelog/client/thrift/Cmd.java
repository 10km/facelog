package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;

public enum Cmd
{
    PARAMETER(0), CONFIG(1), STATUS(2), REPORT(3), ENABLE(4), IS_ENABLE(5), RESET(6), TIME(7), UPDATE(8), MESSAGE(9), CUSTOM(10);

    private final int value;

    Cmd(int value)
    {
        this.value = value;
    }

    @ThriftEnumValue
    public int getValue()
    {
        return value;
    }
}
