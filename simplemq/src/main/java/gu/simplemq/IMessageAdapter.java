package gu.simplemq;

import gu.simplemq.exceptions.SmqUnsubscribeException;

/**
 * 订阅消息数据处理接口
 * @author guyadong
 *
 * @param <T>
 */
public interface IMessageAdapter<T> {
	/**
	 * @param t
	 * @throws SmqUnsubscribeException 取消订阅当前频道异常指示
	 */
	public void onSubscribe(T t) throws SmqUnsubscribeException ;
}
