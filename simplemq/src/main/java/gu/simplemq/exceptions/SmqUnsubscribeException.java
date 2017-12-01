package gu.simplemq.exceptions;

import gu.simplemq.ChannelDispatcher;
import gu.simplemq.IMessageAdapter;
import gu.simplemq.IMessageDispatcher;

/**
 * 取消订阅异常, {@link IMessageAdapter}接口抛出该异常时,{@link IMessageDispatcher}对象会取消订阅当前频道<br>
 * 参见 {@link ChannelDispatcher#dispatch(String, String)} 实现代码
 * @author guyadong
 *
 */
public class SmqUnsubscribeException extends SmqException{
	private static final long serialVersionUID = 1L;
	/** 是否注销频道 */
	public final boolean unregister;
	public SmqUnsubscribeException() {
		this(false);
	}
	/**
	 * @param unregister 是否注销订阅频道
	 */
	public SmqUnsubscribeException(boolean unregister) {
		super();
		this.unregister = unregister;
	}
	
}