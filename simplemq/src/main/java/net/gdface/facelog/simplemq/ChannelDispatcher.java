package net.gdface.facelog.simplemq;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.gdface.facelog.simplemq.exceptions.SmqTypeException;
import net.gdface.facelog.simplemq.exceptions.SmqUnsubscribeException;
import net.gdface.facelog.simplemq.json.JsonEncoder;

/**
 * (消息)频道订阅对象({@link Channel})管理类,负责频道的注册/注销,订阅/取消,消息数据解析及分发
 * 
 * @author guyadong
 *
 */
public class ChannelDispatcher implements IMessageDispatcher,IMessageRegister {
	protected static final Logger logger = LoggerFactory.getLogger(ChannelDispatcher.class);

	private JsonEncoder encoder = JsonEncoder.getEncoder();

	/** 注册的频道对象 */
	@SuppressWarnings("rawtypes")
	protected final Map<String, Channel> channelSubs = Collections.synchronizedMap(new LinkedHashMap<String, Channel>());
	private final Set<String> subChannelSet=Collections.synchronizedSet(new LinkedHashSet<String>());

	public ChannelDispatcher() {
	}

	public ChannelDispatcher(@SuppressWarnings("rawtypes") Channel...channels) {
		register(channels);
	}
	
	public ChannelDispatcher(@SuppressWarnings("rawtypes") Collection<Channel> channels) {
		this(null ==channels?null:channels.toArray(new Channel[0]));
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
	@SuppressWarnings("rawtypes")
	public static String[] getChannelNames(Channel... channels) {
		return getChannelNamesAsList(channels).toArray(new String[0]);
	}

	@SuppressWarnings("rawtypes")
	public static Set<String> getChannelNames(Collection<Channel> channels) {
		HashSet<String> names = new HashSet<String>();
		for (Channel ch : CommonUtils.cleanNullAsList(channels)) {
			names.add(ch.name);
		}
		return names;
	}
	
	@SuppressWarnings("rawtypes")
	public static Set<String> getChannelNamesAsList(Channel... channels) {
		HashSet<String> names = new HashSet<String>();
		for (Channel ch : CommonUtils.cleanNullAsList(channels)) {
			names.add(ch.name);
		}
		return names;
	}

	/**
	 * 子类重写此方法,检查通道名是否合法
	 * @param name
	 * @return name 否则抛出异常
	 * @throws SmqTypeException 
	 */
	protected String check(String name) throws SmqTypeException{return name;}
	
	@SuppressWarnings({ "rawtypes" })
	public Set<Channel> register(Channel... channels) {
		synchronized (this) {
			HashSet<Channel> chSet = new HashSet<Channel>(CommonUtils.cleanNullAsList(channels));
			for (Channel ch : chSet) {
				channelSubs.put(check(ch.name), ch);
			}
			subscribe(getChannelNames(chSet).toArray(new String[0]));
			return chSet;
		}
	}
	@Override
	public Set<String> unregister(String... channels) {
		synchronized (this) {
			HashSet<String> chSet = new HashSet<String>(CommonUtils.cleanEmptyAsList(channels));
			if(!chSet.isEmpty()){
				unsubscribe(chSet.toArray(new String[0]));
				for (String ch : chSet) {
					this.channelSubs.remove(ch);
				}
			}
			return chSet;
		}
	}

	@SuppressWarnings("rawtypes")
	public Set<String> unregister(Channel... channels) {
		return unregister(getChannelNames(channels));
	}

	@SuppressWarnings("rawtypes")
	public Channel getChannel(String channel) {
		return channelSubs.get(channel);
	}

	@Override
	public void dispatch(String channel, String message) {
		@SuppressWarnings("unchecked")
		Channel<Object> ch=channelSubs.get(channel);
		if(null !=ch){
			try{
				Object deserialized = this.encoder.fromJson(message,ch.type);
				ch.onSubscribe(deserialized);
			} catch (SmqUnsubscribeException e) {
				unsubscribe(ch.name);
				logger.info("unregister channel: {}",channel);
			} 
		}else
			logger.warn("unregistered channel: '{}'",channel);
	}
	
	@Override
	public String[] subscribe(String... channels) {
		synchronized(this){
			if (null == channels || 0 == channels.length)
				channels = channelSubs.keySet().toArray(new String[0]);
			else {
				channels = registedOnly(channels);
			}
			this.subChannelSet.addAll(Arrays.asList(channels));
			return channels;
		}
	}

	@Override
	public String[] unsubscribe(String... channels) {
		if (null == channels || 0 == channels.length){
			channels = this.getSubscribes();
			this.subChannelSet.clear();
		}else{
			HashSet<String> chSet = this.registedOnlyAsSet(channels);
			this.subChannelSet.removeAll(chSet);
			channels = chSet.toArray(new String[0]);
		}
		return channels;
	}
	
	@Override
	public String[] getSubscribes(){
		 return this.subChannelSet.toArray(new String[0]);
	}
}
