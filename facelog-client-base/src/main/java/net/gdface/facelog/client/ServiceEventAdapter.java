package net.gdface.facelog.client;

import static com.google.common.base.Preconditions.checkNotNull;
import static net.gdface.facelog.CommonConstant.FACELOG_HB_CHANNEL;

import java.util.List;

import com.google.common.collect.Lists;

import gu.simplemq.Channel;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import net.gdface.facelog.ServiceHeartbeatPackage;

public class ServiceEventAdapter implements ServiceHeartbeatListener{
	public static final ServiceEventAdapter INSTANCE = new ServiceEventAdapter();
	private final List<ServiceHeartbeatListener> listeners = Lists.<ServiceHeartbeatListener>newLinkedList();
	public static final Channel<ServiceHeartbeatPackage> SERVICE_EVENT_CHANNEL = new Channel<>(FACELOG_HB_CHANNEL, ServiceHeartbeatPackage.class,INSTANCE);

	public ServiceEventAdapter() {
	}
	/**
	 * 添加服务心跳侦听器
	 * @param listener
	 * @return 当前{@link ServiceEventAdapter}对象
	 */
	public ServiceEventAdapter addServiceEventListener(ServiceHeartbeatListener listener){
		synchronized (listeners) {
			listeners.add(checkNotNull(listener,"listener is null"));	
		}
		return this;
	}
	/**
	 * 删除服务心跳侦听器
	 * @param listener
	 * @return 当前{@link ServiceEventAdapter}对象
	 */
	public ServiceEventAdapter removeServiceEventListener(ServiceHeartbeatListener listener){
		synchronized (listeners) {
			listeners.remove(listener);
		}
		return this;
	}

	@Override
	public void onSubscribe(ServiceHeartbeatPackage t) throws SmqUnsubscribeException {
		synchronized(listeners){
			for(ServiceHeartbeatListener listener:listeners){
				listener.onSubscribe(t);
			}
		}
	}
}
