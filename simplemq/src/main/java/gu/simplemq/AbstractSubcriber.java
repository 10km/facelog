package gu.simplemq;

/**
 * 消息订阅者抽象类
 * @author guyadong
 *
 */
public abstract class AbstractSubcriber extends ChannelDispatcher implements AutoCloseable  {
	public AbstractSubcriber() {
	}
	
	/**
	 * 订阅频道具体实现
	 * @param channels 频道名列表,不可为null或空
	 */
	protected abstract void doSubscribe(String... channels);
	
	/**
	 * 取消订阅具体实现
	 * @param channels 频道名列表,为null或空时订阅所有 {@link #channelSubs}中的频道
	 */
	protected abstract void doUnsubscribe(String... channels);
	

	@Override
	public String[] subscribe(String... channels) {
		synchronized (this) {
			channels = super.subscribe(channels);
			if (0 < channels.length){
				this.doSubscribe(channels);
			}
			return channels;
		}
	}
	
	@Override
	public String[] unsubscribe(String... channels){
		synchronized (this) {
			channels = super.unsubscribe(channels);
			doUnsubscribe(channels);
			return channels;
		}
	}

	@Override
	public void close() {
		this.unsubscribe();		
	}

}
