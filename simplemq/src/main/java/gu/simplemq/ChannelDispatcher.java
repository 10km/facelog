package gu.simplemq;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import gu.simplemq.exceptions.SmqTypeException;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.json.BaseJsonEncoder;
import gu.simplemq.utils.CommonUtils;
import gu.simplemq.utils.Synchronizer;
import gu.simplemq.utils.Synchronizer.ReadWriteSynchronizer;;

/**
 * (消息)频道订阅对象({@link Channel})管理类,负责频道的注册/注销,订阅/取消,消息数据解析及分发<p>
 * <b>NOTE:</b>如果不设置线程池对象,消息分发{@link #dispatch(String, String)}将以单线程工作,<br>
 * 参见{@link  #setExecutor(Executor)}
 * @author guyadong
 *
 */
public class ChannelDispatcher implements IMessageDispatcher,IMessageRegister {
	protected static final Logger logger = LoggerFactory.getLogger(ChannelDispatcher.class);
	/** 默认单线程实例 */
    private final Executor defaultExecutor = new Executor() {
        // DirectExecutor using caller thread
    	@Override
        public void execute(Runnable r) {
            r.run();
        }
    };
	private BaseJsonEncoder encoder = BaseJsonEncoder.getEncoder();

	/** 注册的频道对象 */
	protected final LinkedHashMap<String, Channel<?>> channelSubs = new LinkedHashMap<String, Channel<?>>();
	/** 订阅的频道名 */
	private final Set<String> subChannelSet= new LinkedHashSet<String>();
	private final Synchronizer sync = new ReadWriteSynchronizer();
	/** 线程池对象 */
	private Executor executor = defaultExecutor; 
	public ChannelDispatcher() {
	}

	public ChannelDispatcher(Channel<?>...channels) {
		register(channels);
	}
	
	public ChannelDispatcher(Collection<Channel<?>> channels) {
		this(null ==channels?null:channels.toArray(new Channel[0]));
	}

	/**
	 * @param channels
	 * @return
	 * @see #registedOnlyAsSet(String...)
	 */
	public String[] registedOnly(String... channels) {
		return registedOnlyAsSet(channels).toArray(new String[0]);
	}
	
	/**
	 * 返回{@code channels}指定的频道名中已经注册的频道名
	 * @param channels
	 * @return
	 */
	public HashSet<String> registedOnlyAsSet(String... channels) {
		sync.beginRead();
		try{
			HashSet<String> chSet = new HashSet<String>(CommonUtils.cleanEmptyAsList(channels));
			if (!chSet.isEmpty()){
				chSet.retainAll(channelSubs.keySet());
			}
			return chSet;
		}finally{
			sync.endRead();
		}
	}
	public static String[] getChannelNames(Channel<?>... channels) {
		return getChannelNamesAsList(channels).toArray(new String[0]);
	}

	public static Set<String> getChannelNames(Collection<Channel<?>> channels) {
		HashSet<String> names = new HashSet<String>();
		for (Channel<?> ch : CommonUtils.cleanNullAsList(channels)) {
			names.add(ch.name);
		}
		return names;
	}
	
	public static Set<String> getChannelNamesAsList(Channel<?>... channels) {
		HashSet<String> names = new HashSet<String>();
		for (Channel<?> ch : CommonUtils.cleanNullAsList(channels)) {
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
	
	@Override
	public Set<Channel<?>> register(Channel<?>... channels) {
		sync.beginWrite();
		try {
			HashSet<Channel<?>> chSet = new HashSet<Channel<?>>(CommonUtils.cleanNullAsList(channels));
			for (Channel<?> ch : chSet) {
				channelSubs.put(check(ch.name), ch);
			}
			subscribe(getChannelNames(chSet).toArray(new String[0]));
			return chSet;
		}finally{
			sync.endWrite();;
		}
	}
	@Override
	public Set<String> unregister(String... channels) {
		sync.beginWrite();
		try {
			HashSet<String> chSet = new HashSet<String>(CommonUtils.cleanEmptyAsList(channels));
			if(!chSet.isEmpty()){
				unsubscribe(chSet.toArray(new String[0]));
				for (String ch : chSet) {
					this.channelSubs.remove(ch);
				}
			}
			return chSet;
		}finally{
			sync.endWrite();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Set<String> unregister(Channel... channels) {
		return unregister(getChannelNames(channels));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Channel getChannel(String channel) {
		sync.beginRead();
		try{
			return channelSubs.get(channel);
		}finally{
			sync.endRead();
		}
	}

	@Override
	public void dispatch(final String channel, final String message) {
		this.executor.execute(new Runnable(){
			@Override
			public void run() {
				Channel<?> ch = getChannel(channel);
				if(null !=ch){
					try{
						Object deserialized = encoder.fromJson(message,ch.type);
						ch.onSubscribe(deserialized);
					} catch (SmqUnsubscribeException e) {
						if(e.unregister){
							unregister(ch.name);
						}else{
							unsubscribe(ch.name);	
						}				
						logger.info("unsubscribe channel: {}",channel);
					} 
				}else{
					logger.warn("unregistered channel: '{}'",channel);
				}
			}});
	}
	
	@Override
	public String[] subscribe(String... channels) {
		sync.beginWrite();
		try{
			if (null == channels || 0 == channels.length)
				channels = channelSubs.keySet().toArray(new String[0]);
			else {
				channels = registedOnly(channels);
			}
			this.subChannelSet.addAll(Arrays.asList(channels));
			return channels;
		}finally{
			sync.endWrite();
		}
	}

	@Override
	public String[] unsubscribe(String... channels) {
		sync.beginWrite();
		try{
			if (null == channels || 0 == channels.length){
				channels = this.getSubscribes();
				this.subChannelSet.clear();
			}else{
				HashSet<String> chSet = this.registedOnlyAsSet(channels);
				this.subChannelSet.removeAll(chSet);
				channels = chSet.toArray(new String[0]);
			}
			return channels;
		}finally{
			sync.endWrite();
		}
	}
	
	@Override
	public String[] getSubscribes(){
		sync.beginRead();
		try{
			return this.subChannelSet.toArray(new String[0]);
		}finally{
			sync.endRead();
		}
	}
	/**
	 * 注销符合条件{@code removeIfTrue}的的频道
	 * @param removeIfTrue
	 */
	public void unregister(Predicate<Channel<?>> removeIfTrue){
		sync.beginWrite();
		try{
			checkArgument(null != removeIfTrue,"retainIfTrue is null");
			String[] channels = Maps.filterValues(channelSubs, removeIfTrue).keySet().toArray(new String[0]);
			unregister(channels);
		}finally{
			sync.endWrite();
		}
	}
	/**
	 * 取消频道订阅符合条件{@code removeIfTrue}的的频道
	 * @param removeIfTrue
	 */
	public void unsubscribe(Predicate<String> removeIfTrue){
		sync.beginWrite();
		try{
			checkArgument(null != removeIfTrue,"retainIfTrue is null");
			String[] channels = Sets.filter(subChannelSet, removeIfTrue).toArray(new String[0]);
			unsubscribe(channels);
		}finally{
			sync.endWrite();
		}
	}

	/**
	 * 设置线程池对象,如果不指定线程对象,消息分发{@link #dispatch(String, String)}将以单线程工作
	 * @param executor 线程池对象，不可为{@code null}
	 * @return 
	 * @return
	 */
	public ChannelDispatcher setExecutor(Executor executor) {
		this.executor = checkNotNull(executor,"executor is null");
		return this;
	}
}
