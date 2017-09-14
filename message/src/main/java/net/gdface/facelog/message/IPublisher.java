package net.gdface.facelog.message;

import java.lang.reflect.Type;

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
	 * @param type 消息类型
	 */
	public void publish(Channel channel,Object obj,Type type);
}
