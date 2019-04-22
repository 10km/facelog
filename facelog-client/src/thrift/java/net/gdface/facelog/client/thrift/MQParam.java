package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;

public enum MQParam
{
    REDIS_URI(0), WEBREDIS_URL(1), CMD_CHANNEL(2), LOG_MONITOR_CHANNEL(3), HB_MONITOR_CHANNEL(4), HB_INTERVAL(5), HB_EXPIRE(6);

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
