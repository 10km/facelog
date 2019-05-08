package net.gdface.facelog.client;

import com.google.common.base.Objects;

import gu.simplemq.exceptions.SmqUnsubscribeException;
import net.gdface.facelog.ServiceHeartbeatPackage;

/**
 * 服务心跳侦听器基类
 * @author guyadong
 *
 */
public class BaseServiceHeartbeatListener implements ServiceHeartbeatListener{
	public static final ServiceHeartbeatListener EMPTY_LISTENER = new BaseServiceHeartbeatListener();
	/** 服务ID */
	private volatile Integer serviceID;
	@Override
	public final void onSubscribe(ServiceHeartbeatPackage heartbeatPackage) throws SmqUnsubscribeException {
		// 比较服务ID是否相等，不相等则执行令牌刷新，更新redis参数
		// 确保每次服务端重启后都执行令牌刷新和redis参数
		if(!Objects.equal(heartbeatPackage.getId(),serviceID)){	
			if(doServiceOnline(heartbeatPackage)){
				serviceID = heartbeatPackage.getId();
			}
		}
	}
	/**
	 * 子类重写此方法实现自己的业务逻辑，如果不希望收重复的服务心跳返回{@code true}，否则返回{@code false}
	 * @param heartbeatPackage 服务心跳包
	 * @return 返回{@code true}将不会再收到同样的消息，返回{@code false}
	 */
	protected boolean doServiceOnline(ServiceHeartbeatPackage heartbeatPackage) {
		return false;		
	}
}