package net.gdface.simplemq.exceptions;

import net.gdface.simplemq.ChannelDispatcher;
import net.gdface.simplemq.IMessageAdapter;
import net.gdface.simplemq.IMessageDispatcher;

/**
 * 取消订阅异常, {@link IMessageAdapter}接口抛出该异常时,{@link IMessageDispatcher}对象会取消订阅当前频道<br>
 * 参见 {@link ChannelDispatcher#dispatch(String, String)} 实现代码
 * @author guyadong
 *
 */
public class SmqUnsubscribeException extends SmqExcepiton{
	private static final long serialVersionUID = 1L;}