package net.gdface.facelog.service;

import gu.simplemq.Channel;

public interface ServiceConstant {
    public static final Channel<Long> TABLE_DEVICE_TOKEN = new Channel<Long>("DeviceToken"){} ;

}
