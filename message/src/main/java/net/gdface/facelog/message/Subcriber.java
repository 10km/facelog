package net.gdface.facelog.message;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.gdface.facelog.message.IOnSubscribe.UnsubscribeException;

/**
 * 消息订阅者抽象类
 * @author guyadong
 *
 */
public abstract class Subcriber implements IOnMessage, ISubscriber  {
	protected static final Logger logger = LoggerFactory.getLogger(Subcriber.class);
	private JsonEncoder encoder = JsonEncoder.getEncoder();
	/**  注册的频道对象 */
	@SuppressWarnings("rawtypes")
	private Map<String,ChannelSub> channelSubs = Collections.synchronizedMap(new LinkedHashMap<String,ChannelSub>());
	public Subcriber() {
	}
	
	private String[] registedOnly(String... channels){
		HashSet<String> chSet = new HashSet<String>(CommonUtils.cleanEmptyAsList(channels));
		if(!chSet.isEmpty())
			chSet.retainAll(channelSubs.keySet());
		return chSet.toArray(new String[chSet.size()]);
	}
	
	protected static String[] getChannelNames(Channel...channels){
		HashSet<String> names = new HashSet<String>();
		for (Channel ch : CommonUtils.cleanNullAsList(channels)) {
			names.add(ch.name);
		}
		return names.toArray(new String[names.size()]);
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
	public void register(ChannelSub... channels) {
		synchronized (this) {
			HashSet<ChannelSub> chSet = new HashSet<ChannelSub>(CommonUtils.cleanNullAsList(channels));
			if(chSet.isEmpty())return;
			LinkedList<String> names = new LinkedList<String>();
			for (ChannelSub ch : chSet) {
				channelSubs.put(ch.name, ch);
				names.add(ch.name);
			}
			subscribe(names.toArray(new String[names.size()]));
		}
	}
	
	@Override
	public void unregister(String... channels) {
		synchronized (this) {
			channels = registedOnly(channels);
			if (0 < channels.length) {
				_unsubscribe(channels);
				for (String ch : channels) {
					this.channelSubs.remove(ch);
				}
			}
		}
	}
	
	@Override
	public void unregister(Channel...channels){
		unregister(getChannelNames(channels));
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public ChannelSub getChannelSub(String channel) {
		return channelSubs.get(channel);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onMessage(String channel, String message) {
		ChannelSub channelSub = getChannelSub(channel);
		if(null == channelSub){			
			logger.warn("unregistered channel: '{}'",channel);
			logger.warn(message);
			return;
		}
		if(null == channelSub.handle){
			logger.warn("cann't dispatch because of null handle : '{}'",channel);
			return; 
		}
		try{
			Object deserialized = encoder.fromJson(message, channelSub.type);
			if(null != deserialized){
				channelSub.handle.onSubscribe(deserialized);
			}
		}catch(UnsubscribeException e){
			this._unsubscribe(channel);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void close() {
		this._unsubscribe();		
	}
}
