package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;

public enum RedisParam
{
    REDIS_URI(0), CMD_CHANNEL(1);

    private final int value;

    RedisParam(int value)
    {
        this.value = value;
    }

    @ThriftEnumValue
    public int getValue()
    {
        return value;
    }
}
