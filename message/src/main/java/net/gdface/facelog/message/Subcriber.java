package net.gdface.facelog.message;

import java.util.ArrayList;
import java.util.Collections;
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
	
	protected abstract void subscribe(String... channels);
	protected abstract void unsubscribe(String... channels);
	
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
		channels = CommonUtils.cleanNull(channels);
		String[] names = getChannelNames(channels);
		if (0 < names.length) {
			synchronized (this.channelSubs) {				
				for(ChannelSub ch:channels){
					channelSubs.put(ch.name, ch);
				}
				subscribe(names);
			}
		}
	}
	
	public void unregister(String... channels) {
		channels = CommonUtils.cleanEmpty(channels);
		synchronized (this.channelSubs) {
			unsubscribe(channels);
			if(0 == channels.length)
				this.channelSubs.clear();
			for(String ch:channels){
				this.channelSubs.remove(ch);
			}
		}
	}
	
	public void unregister(Channel...channels){
		unregister(getChannelNames(channels));	
	}
	
	private String[] getChannelNames(Channel...channels){
		ArrayList<String> list = new ArrayList<String>(channels.length);
		if (null != channels) {
			for (Channel ch : channels) {
				if (null != ch)
					list.add(ch.name);
			}
		}
		return list.toArray(new String[list.size()]);
	}
}
