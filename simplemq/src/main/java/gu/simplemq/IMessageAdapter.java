package gu.simplemq;

import gu.simplemq.exceptions.SmqUnsubscribeException;

/**
 * 订阅消息数据处理接口
 * @author guyadong
 *
 * @param <T> 消息类型
 */
public interface IMessageAdapter<T> {
	/**
	 * 订阅消息处理
	 * @param t 消息对象
	 * @throws SmqUnsubscribeException 取消订阅当前频道异常指示
	 */
	public void onSubscribe(T t) throws SmqUnsubscribeException ;
}
