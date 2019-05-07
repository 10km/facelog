package net.gdface.facelog.client;

import static com.google.common.base.Preconditions.checkNotNull;
import static net.gdface.facelog.CommonConstant.FACELOG_EVT_CHANNEL;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import gu.simplemq.Channel;
import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import net.gdface.facelog.ServiceEvent;

public class ServiceEventAdapter extends ServiceEventListener implements IMessageAdapter<ServiceEvent>{
	public static final ServiceEventAdapter INSTANCE = new ServiceEventAdapter();
	private final List<ServiceEventListener> listeners = Collections.synchronizedList(Lists.<ServiceEventListener>newLinkedList());
	public static final Channel<ServiceEvent> SERVICE_EVENT_CHANNEL = new Channel<>(FACELOG_EVT_CHANNEL, ServiceEvent.class,INSTANCE);

	public ServiceEventAdapter() {
	}
	public ServiceEventAdapter addServiceEventListener(ServiceEventListener listener){
		synchronized (listeners) {
			listeners.add(checkNotNull(listener,"listener is null"));	
		}		
		return this;
	}
	public ServiceEventAdapter removeServiceEventListener(ServiceEventListener listener){
		synchronized (listeners) {
			listeners.remove(listener);
		}
		return this;
	}

	@Override
	public void online() {
		synchronized(listeners){
			for(ServiceEventListener listener:listeners){
				listener.online();
			}
		}
	}
	@Override
	public void xhrOnline() {
		synchronized(listeners){
			for(ServiceEventListener listener:listeners){
				listener.xhrOnline();
			}
		}
	}
	@Override
	public void frameOnline() {
		synchronized(listeners){
			for(ServiceEventListener listener:listeners){
				listener.frameOnline();
			}
		}
	}
	@Override
	public void onSubscribe(ServiceEvent t) throws SmqUnsubscribeException {
		System.out.printf("ServiceEvent :%s\n",t.name());
		switch (t) {
		case ONLINE:
			online();
			break;
		case FRAME_ONLINE:
			frameOnline();
			break;
		case XHR_ONLINE:
			xhrOnline();
			break;
		default:
			break;
		}
	}
}
