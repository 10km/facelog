package net.gdface.facelog.hb;

import gu.simplemq.IMessageAdapter;
import net.gdface.facelog.ServiceHeartbeatPackage;

/**
 * 服务心跳侦听器
 * @author guyadong
 *
 */
public interface ServiceHeartbeatListener extends IMessageAdapter<ServiceHeartbeatPackage>{ 
}