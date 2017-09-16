package net.gdface.facelog.message;

import java.util.Set;

public interface ISubscriber {

	/**
	 * 订阅频道
	 * @param channels 频道名列表,为null或空时订阅所有 {@link #channelSubs}中的频道
	 * @see #_subscribe(String...)
	 */
	void subscribe(String... channels);

	/**
	 * 取消频道订阅
	 * @param channels
	 * @see #_unsubscribe(String...)
	 */
	void unsubscribe(String... channels);

	/**
	 * 注册并订阅指定的频道
	 * @param channels
	 * @see #subscribe(String...)
	 * @return 返回实际注册的频道名列表
	 */
	@SuppressWarnings("rawtypes")
	Set<ChannelSub> register(ChannelSub... channels);

	/**
	 * 取消订阅指定的频道,并注销频道
	 * @param channels
	 * @see #unsubscribe(String...)
	 * @return 返回实际注销的频道名列表
	 */
	Set<String> unregister(String... channels);

	/**
	 * @param channels
	 * @see #unregister(String...)
	 * @return 返回实际注销的频道名列表
	 */
	Set<String> unregister(Channel... channels);

	/**
	 * 返回注册的 {@link ChannelSub}对象
	 * @param channel 频道名
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	ChannelSub getChannelSub(String channel);

	String[] getSubscribes();

}