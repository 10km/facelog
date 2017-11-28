package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;

public enum MQParam
{
    REDIS_URI(0), CMD_CHANNEL(1), LOG_MONITOR_CHANNEL(2), HB_MONITOR_CHANNEL(3);

    private final int value;

    MQParam(int value)
    {
        this.value = value;
    }

    @ThriftEnumValue
    public int getValue()
    {
        return value;
    }
}
