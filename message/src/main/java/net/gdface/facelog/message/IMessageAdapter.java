package net.gdface.facelog.message;

/**
 * 订阅消息数据处理接口
 * @author guyadong
 *
 * @param <T>
 */
public interface IMessageAdapter<T> {
	/**
	 * 取消订阅异常, {@link IMessageAdapter}接口抛出该异常时,{@link IMessageDispatcher}对象会取消订阅当前频道<br>
	 * 参见 {@link ChannelDispatcher#dispatch(String, String)} 实现代码
	 * @author guyadong
	 *
	 */
	public class UnsubscribeException extends RuntimeException{
		private static final long serialVersionUID = 1L;}
	/**
	 * @param t
	 * @throws UnsubscribeException 取消订阅当前频道异常指示
	 */
	public void onSubscribe(T t) throws UnsubscribeException ;
}
