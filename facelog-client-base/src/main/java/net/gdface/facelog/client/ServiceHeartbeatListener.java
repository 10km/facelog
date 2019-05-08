package net.gdface.facelog.client;

import gu.simplemq.IMessageAdapter;
import net.gdface.facelog.ServiceHeartbeatPackage;

public interface ServiceHeartbeatListener extends IMessageAdapter<ServiceHeartbeatPackage>{ 
}