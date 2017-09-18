package net.gdface.facelog.message;

/**
 * 消息订阅者抽象类
 * @author guyadong
 *
 */
public abstract class AbstractSubcriber extends ChannelDispatcher implements IMessageDispatcher, ISubscriber,AutoCloseable  {
	public AbstractSubcriber() {
	}
	
	/**
	 * 订阅频道具体实现
	 * @param channels 频道名列表,不可为null或空
	 */
	protected abstract void _subscribe(String... channels);
	
	/**
	 * 取消订阅具体实现
	 * @param channels 频道名列表,为null或空时订阅所有 {@link #channelSubs}中的频道
	 */
	protected abstract void _unsubscribe(String... channels);
	

	@Override
	public void subscribe(String... channels) {
		synchronized (this) {
			super.subscribe(channels);
			channels = getSubscribes();
			if (0 < channels.length)
				this._subscribe(channels);
		}
	}
	
	@Override
	public void unsubscribe(String... channels){
		synchronized (this) {
			super.unsubscribe(channels);
			_unsubscribe(getSubscribes());
		}
	}

	@Override
	public void close() {
		this.unsubscribe();		
	}

}
