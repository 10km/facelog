package net.gdface.facelog.hb;

import static com.google.common.base.Preconditions.checkNotNull;
import static net.gdface.facelog.CommonConstant.FACELOG_HB_CHANNEL;

import java.util.LinkedHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

import gu.simplemq.Channel;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import net.gdface.facelog.ServiceHeartbeatPackage;

public class ServiceHeartbeatAdapter implements ServiceHeartbeatListener{
    public static final Logger logger = LoggerFactory.getLogger(ServiceHeartbeatAdapter.class);

	public static final ServiceHeartbeatAdapter INSTANCE = new ServiceHeartbeatAdapter();
	private final LinkedHashSet<ServiceHeartbeatListener> listeners = Sets.newLinkedHashSet();
	public static final Channel<ServiceHeartbeatPackage> SERVICE_HB_CHANNEL = new Channel<>(FACELOG_HB_CHANNEL, ServiceHeartbeatPackage.class,INSTANCE);

	public ServiceHeartbeatAdapter() {
	}
	/**
	 * 添加服务心跳侦听器,重复添加无效
	 * @param listener
	 * @return 当前{@link ServiceHeartbeatAdapter}对象
	 */
	public ServiceHeartbeatAdapter addServiceEventListener(ServiceHeartbeatListener listener){
		synchronized (listeners) {
			listeners.add(checkNotNull(listener,"listener is null"));	
		}
		return this;
	}
	/**
	 * 删除服务心跳侦听器
	 * @param listener
	 * @return 当前{@link ServiceHeartbeatAdapter}对象
	 */
	public ServiceHeartbeatAdapter removeServiceEventListener(ServiceHeartbeatListener listener){
		synchronized (listeners) {
			listeners.remove(listener);
		}
		return this;
	}

	@Override
	public void onSubscribe(ServiceHeartbeatPackage t) throws SmqUnsubscribeException {
		synchronized(listeners){
			for(ServiceHeartbeatListener listener:listeners){
				// 拦截所有异常，确保每个listener都能被执行
				try {
					listener.onSubscribe(t);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
	}
}
