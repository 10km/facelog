package gu.simplemq;

/**
 * 消息分发接口,用于将收到的消息分派到不同的消息处理器
 * @author guyadong
 *
 */
public interface IMessageDispatcher {
	/**
	 * 消息分发
	 * @param channel 收到消息的频道名
	 * @param message 消息内容
	 */
	public void dispatch(String channel, String message);
}
