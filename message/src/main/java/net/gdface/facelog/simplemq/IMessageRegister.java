package net.gdface.facelog.simplemq;

import java.util.Set;

/**
 * (消息)频道注册订阅接口
 * @author guyadong
 *
 */
public interface IMessageRegister {

	/**
	 * 订阅频道
	 * @param channels 频道名列表,为null或空时订阅所有 {@link #channelSubs}中的频道
	 * @return 返回实际订阅的频道列表
	 */
	String[] subscribe(String... channels);

	/**
	 * 取消频道订阅
	 * @param channels
	 * @return 返回实际取消订阅的频道列表
	 */
	String[] unsubscribe(String... channels);

	/**
	 * 注册并订阅指定的频道
	 * @param channels
	 * @see #subscribe(String...)
	 * @return 返回实际注册的频道名列表
	 */
	@SuppressWarnings("rawtypes")
	Set<Channel> register(Channel... channels);

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
	@SuppressWarnings("rawtypes")
	Set<String> unregister(Channel... channels);

	/**
	 * 返回注册的 {@link Channel}对象
	 * @param channel 频道名
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Channel getChannel(String channel);

	/**
	 * @return 返回当前订阅的所有频道名
	 */
	String[] getSubscribes();

}