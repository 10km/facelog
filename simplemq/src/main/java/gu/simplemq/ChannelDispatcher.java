package gu.simplemq;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.util.concurrent.MoreExecutors;

import gu.simplemq.exceptions.SmqTypeException;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.json.BaseJsonEncoder;
import gu.simplemq.utils.CommonUtils;
import gu.simplemq.utils.Synchronizer;
import gu.simplemq.utils.Synchronizer.ReadWriteSynchronizer;;

/**
 * (消息)频道订阅对象({@link Channel})管理类,负责频道的注册/注销,订阅/取消,消息数据解析及分发<p>
 * <b>NOTE:</b>如果不设置线程池对象,消息分发{@link #dispatch(String, String)}将以单线程工作,<br>
 * 参见{@link  #setExecutor(ExecutorService)}
 * @author guyadong
 *
 */
public class ChannelDispatcher implements IMessageDispatcher,IMessageRegister {
	protected static final Logger logger = LoggerFactory.getLogger(ChannelDispatcher.class);
	private BaseJsonEncoder encoder = BaseJsonEncoder.getEncoder();

	/** 注册的频道对象 */
	protected final LinkedHashMap<String, Channel<?>> channelSubs = new LinkedHashMap<String, Channel<?>>();
	/** 订阅的频道名 */
	private final Set<String> subChannelSet= new LinkedHashSet<String>();
	private final Synchronizer sync = new ReadWriteSynchronizer();
	/** 线程池对象 */
	private Executor executor = MoreExecutors.directExecutor(); 
	/** 定时任务线程池 */
	private ScheduledExecutorService timerExecutor;
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
	/**
	 * 注册并订阅指定的频道,并在指定的时间后注销频道<br>
	 * 调用该方法必须要提供一个{@link ScheduledExecutorService}对象,
	 * 参见{@link #setTimerExecutor(ScheduledExecutorService)}
	 * @param channel 频道名
	 * @param duration 频道订阅持续时间,>0有效
	 * @param unit 时间单位,不可为{@code null}
	 * @see #register(Channel...)
	 */
	public <T>void register(final Channel<T>channel,
			long duration,
			TimeUnit unit) {
		checkState(null != this.timerExecutor,"timerExecutor is uninitialized,please call setTimerExecutor firstly");
		register(checkNotNull(channel,"channel is null"));
		if(duration > 0){
			checkArgument(null != unit,"unit is null");
			this.timerExecutor.schedule(new Runnable(){
				@Override
				public void run() {
					unregister(channel.name);
				}}, duration, unit);
		}
	}
	
	@Override
	public Set<String> unregister(String... channels) {
		sync.beginWrite();
		try {
			HashSet<String> chSet = new HashSet<String>(CommonUtils.cleanEmptyAsList(channels));
			// 这里要判断是否为空,因为unsubscribe方法对于空列表参数会清除所有订阅
			if(!chSet.isEmpty()){
				unsubscribe(chSet.toArray(new String[0]));
				for (String ch : chSet) {
					Channel<?> channel = this.channelSubs.get(ch);
					if(null != channel){
						this.channelSubs.remove(ch);
						channel.onUnregisted();
					}
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
	 * 设置线程池对象,如果不指定线程对象,消息分发{@link #dispatch(String, String)}将以单线程工作
	 * @param executor 线程池对象，不可为{@code null}
	 * @return
	 */
	public ChannelDispatcher setExecutor(ExecutorService executor) {
		this.executor = checkNotNull(executor,"executor is null");
		return this;
	}

	/**
	 * 设置用于定时任务的线程池对象
	 * @param timerExecutor
	 * @return 
	 */
	public ChannelDispatcher setTimerExecutor(ScheduledExecutorService timerExecutor) {
		this.timerExecutor = checkNotNull(timerExecutor,"timerExecutor is null");
		return this;
	}
}
