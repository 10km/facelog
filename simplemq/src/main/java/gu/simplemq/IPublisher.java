package gu.simplemq;

import java.util.Collection;

/**
 * 发布订阅接口
 * @author guyadong
 *
 */
public interface IPublisher  {
	/**
	 * 向指定的频道发布消息
	 * @param channel 频道
	 * @param obj 消息对象
	 */
	<T> void publish(Channel<T> channel,T obj);

	/**
	 * 参见 {@link #publish(Channel, Object)}
	 * @param channel
	 * @param objects
	 */
	<T> void publish(Channel<T> channel, Collection<T> objects);

	/**
	 * 参见 {@link #publish(Channel, Object)}
	 * @param channel
	 * @param objects
	 */
	<T> void publish(Channel<T> channel, @SuppressWarnings("unchecked") T... objects);
}
