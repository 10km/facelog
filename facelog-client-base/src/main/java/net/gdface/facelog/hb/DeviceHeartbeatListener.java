package net.gdface.facelog.hb;

import gu.simplemq.IMessageAdapter;
import net.gdface.facelog.DeviceHeadbeatPackage;

/**
 * 设备心跳侦听器
 * @author guyadong
 *
 */
public interface DeviceHeartbeatListener extends IMessageAdapter<DeviceHeadbeatPackage> {

}
