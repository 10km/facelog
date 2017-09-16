package net.gdface.facelog.message;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.gdface.facelog.message.IOnSubscribe.UnsubscribeException;

/**
 * 消息订阅对象({@link ChannelSub})管理类
 * 
 * @author guyadong
 *
 */
public class ChannelRegister implements IOnMessage {
	protected static final Logger logger = LoggerFactory.getLogger(ChannelRegister.class);

	private JsonEncoder encoder = JsonEncoder.getEncoder();

	/** 注册的频道对象 */
	@SuppressWarnings("rawtypes")
	protected final Map<String, ChannelSub> channelSubs = Collections.synchronizedMap(new LinkedHashMap<String, ChannelSub>());

	public ChannelRegister() {
	}

	public ChannelRegister(@SuppressWarnings("rawtypes") ChannelSub...channels) {
		register(channels);
	}
	
	public ChannelRegister(@SuppressWarnings("rawtypes") Collection<ChannelSub> channels) {
		this(null ==channels?null:channels.toArray(new ChannelSub[0]));
	}
	
	public String[] registedOnly(String... channels) {
		return registedOnlyAsSet(channels).toArray(new String[0]);
	}
	
	public HashSet<String> registedOnlyAsSet(String... channels) {
		HashSet<String> chSet = new HashSet<String>(CommonUtils.cleanEmptyAsList(channels));
		if (!chSet.isEmpty())
			chSet.retainAll(channelSubs.keySet());
		return chSet;
	}
	public static String[] getChannelNames(Channel... channels) {
		return getChannelNamesAsList(channels).toArray(new String[0]);
	}

	public static Set<String> getChannelNames(Collection<? extends Channel> channels) {
		HashSet<String> names = new HashSet<String>();
		for (Channel ch : CommonUtils.cleanNullAsList(channels)) {
			names.add(ch.name);
		}
		return names;
	}
	
	public static Set<String> getChannelNamesAsList(Channel... channels) {
		HashSet<String> names = new HashSet<String>();
		for (Channel ch : CommonUtils.cleanNullAsList(channels)) {
			names.add(ch.name);
		}
		return names;
	}

	@SuppressWarnings({ "rawtypes" })
	public Set<ChannelSub> register(ChannelSub... channels) {
		synchronized (this) {
			HashSet<ChannelSub> chSet = new HashSet<ChannelSub>(CommonUtils.cleanNullAsList(channels));
			for (ChannelSub ch : chSet) {
				channelSubs.put(ch.name, ch);
			}
			return chSet;
		}
	}

	public Set<String> unregister(String... channels) {
		synchronized (this) {
			HashSet<String> chSet = new HashSet<String>(CommonUtils.cleanEmptyAsList(channels));
			for (String ch : chSet) {
				this.channelSubs.remove(ch);
			}
			return chSet;
		}
	}

	public Set<String> unregister(Channel... channels) {
		return unregister(getChannelNames(channels));
	}

	@SuppressWarnings("rawtypes")
	public ChannelSub getChannelSub(String channel) {
		return channelSubs.get(channel);
	}

	@Override
	public void onMessage(String channel, String message) {
		@SuppressWarnings("unchecked")
		ChannelSub<Object> ch=channelSubs.get(channel);
		if(null !=ch){
			try{
				Object deserialized = this.encoder.fromJson(message,ch.type);
				ch.onSubscribe(deserialized);
			} catch (UnsubscribeException e) {
				unregister(ch);
				logger.info("unregister channel: {}",channel);
			} 
		}else
			logger.warn("unregistered channel: '{}'",channel);
	}
}
