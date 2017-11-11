package gu.simplemq;

import java.util.Collection;

/**
 * 生产者模型接口(多队列)
 * @author guyadong
 *
 */
public interface IProducer {
	
	/**
	 * 向指定的消息队列(频道)添加一个消息对象
	 * @param channel
	 * @param object
	 * @param offerLast 为true添加到队列末尾
	 */
	<T> void produce(Channel<T> channel, T object, boolean offerLast);

	/**
	 * 根据默认的队列添加模式,向指定的消息队列(频道)添加一个消息对象
	 * @param channel
	 * @param object
	 * @see #setOfferLast(boolean)
	 */
	<T> void produce(Channel<T> channel, T object);

	/**
	 * 生产数据
	 * @param channel
	 * @param offerLast
	 * @param objects
	 * @see #produce(Channel, Object, boolean)
	 */
	<T> void produce(Channel<T> channel, boolean offerLast, @SuppressWarnings("unchecked") T... objects);

	/**
	 * 生产数据
	 * @param channel
	 * @param offerLast
	 * @param c
	 * @see #produce(Channel, Object, boolean)
	 */
	<T> void produce(Channel<T> channel, boolean offerLast, Collection<T> c);

	/**
	 * 生产数据
	 * @param channel
	 * @param c
	 * @see #produce(Channel, Object)
	 */
	<T> void produce(Channel<T> channel, Collection<T>c);

	/**
	 * 生产数据
	 * @param channel
	 * @param objects
	 * @see #produce(Channel, Object) 
	 */
	<T> void produce(Channel<T> channel, @SuppressWarnings("unchecked") T... objects);

	/**
	 * 设置默认的队列添加模式
	 * @param offerLast 为true默认添加到队列末尾
	 */
	void setOfferLast(boolean offerLast);

}