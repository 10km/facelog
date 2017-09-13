package net.gdface.facelog.message;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Subcriber implements IOnMessage  {
	protected static final Logger logger = LoggerFactory.getLogger(Subcriber.class);
	private JsonEncoder encoder = JsonEncoder.getEncoder();
	@SuppressWarnings("rawtypes")
	private Map<String,ChannelSub> channelSubs = Collections.synchronizedMap(new LinkedHashMap<String,ChannelSub>());
	public Subcriber() {
	}
	
	private String[] registedChannels(String... channels){
		HashSet<String> chSet = new HashSet<String>(CommonUtils.cleanEmptyAsList(channels));
		if(!chSet.isEmpty())
			chSet.retainAll(channelSubs.keySet());
		return chSet.toArray(new String[chSet.size()]);
	}
	
	protected abstract void _subscribe(String... channels);
	
	protected abstract void _unsubscribe(String... channels);
	
	public void subscribe(String... channels) {
		synchronized (this) {
			if (null == channels || 0 == channels.length)
				channels = channelSubs.keySet().toArray(new String[0]);
			else {
				channels = registedChannels(channels);
			}
			if (0 < channels.length)
				this._subscribe(channels);
		}
	}
	
	public void unsubscribe(String... channels){
		synchronized (this) {
			_unsubscribe(registedChannels(channels));
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onMessage(String channel, String message) {
		ChannelSub channelSub = channelSubs.get(channel);
		if(null == channelSub){
			logger.warn("not found registered channel info for '{}'",channel);
			return;
		}
		if(null != channelSub.handle){
			try{
				Object deserialized = encoder.fromJson(message, channelSub.type);
				if(null != deserialized)
					channelSub.handle.onSubscribe(deserialized);
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void register(ChannelSub... channels) {
		synchronized (this) {
			channels = CommonUtils.cleanNull(channels);
			String[] names = getChannelNames(channels);
			if (0 < names.length) {
				for (ChannelSub ch : channels) {
					channelSubs.put(ch.name, ch);
				}
				subscribe(names);
			}
		}
	}
	
	public void unregister(String... channels) {
		synchronized (this) {
			channels = registedChannels(channels);
			if (0 < channels.length) {
				_unsubscribe(channels);
				for (String ch : channels) {
					this.channelSubs.remove(ch);
				}
			}
		}
	}
	
	public void unregister(Channel...channels){
		unregister(getChannelNames(channels));	
	}
	
	private static String[] getChannelNames(Channel...channels){
		HashSet<String> names = new HashSet<String>();
		for (Channel ch : CommonUtils.cleanNullAsList(channels)) {
			names.add(ch.name);
		}
		return names.toArray(new String[names.size()]);
	}
}
