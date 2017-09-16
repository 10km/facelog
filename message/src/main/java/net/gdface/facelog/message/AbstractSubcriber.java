package net.gdface.facelog.message;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息订阅者抽象类
 * @author guyadong
 *
 */
public abstract class AbstractSubcriber extends ChannelRegister implements IOnMessage, ISubscriber  {
	protected static final Logger logger = LoggerFactory.getLogger(AbstractSubcriber.class);
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
			if (null == channels || 0 == channels.length)
				channels = channelSubs.keySet().toArray(new String[0]);
			else {
				channels = registedOnly(channels);
			}
			if (0 < channels.length)
				this._subscribe(channels);
		}
	}
	
	@Override
	public void unsubscribe(String... channels){
		synchronized (this) {
			_unsubscribe(registedOnly(channels));
		}
	}
	
	@Override
	@SuppressWarnings({ "rawtypes" })
	public Set<ChannelSub> register(ChannelSub... channels) {
		synchronized (this) {
			Set<ChannelSub> names = super.register(channels);
			subscribe(getChannelNames(names).toArray(new String[0]));
			return names;
		}
	}
	
	@Override
	public Set<String> unregister(String... channels) {
		synchronized (this) {
			Set<String> names = super.unregister(channels);
			if(!names.isEmpty())
				_unsubscribe(names.toArray(new String[0]));
			return names;
		}
	}
	
	@Override
	public Set<String> unregister(Channel...channels){
		return unregister(getChannelNames(channels));
	}

	@Override
	public void close() {
		this._unsubscribe();		
	}
}
