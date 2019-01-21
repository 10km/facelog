package net.gdface.facelog.thrift.client;

import com.facebook.swift.codec.*;

public enum MQParam
{
    REDIS_URI(0), CMD_CHANNEL(1), LOG_MONITOR_CHANNEL(2), HB_MONITOR_CHANNEL(3), HB_INTERVAL(4), HB_EXPIRE(5);

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
