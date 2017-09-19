package gu.exceptions;

import gu.simplemq.ChannelDispatcher;
import gu.simplemq.IMessageAdapter;
import gu.simplemq.IMessageDispatcher;

/**
 * 取消订阅异常, {@link IMessageAdapter}接口抛出该异常时,{@link IMessageDispatcher}对象会取消订阅当前频道<br>
 * 参见 {@link ChannelDispatcher#dispatch(String, String)} 实现代码
 * @author guyadong
 *
 */
public class SmqUnsubscribeException extends SmqExcepiton{
	private static final long serialVersionUID = 1L;}