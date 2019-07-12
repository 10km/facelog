package net.gdface.facelog.hb;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import gu.simplemq.json.BaseJsonEncoder;
import net.gdface.facelog.CommonConstant;
import net.gdface.facelog.ServiceHeartbeatPackage;
import net.gdface.utils.JcifsUtil;
import net.gdface.utils.MultiCastDispatcher;
import net.gdface.utils.NetworkUtil;

/**
 * 局域网组播(multicast)心跳包侦听器<br>
 * 通过侦听服务心跳包组播数据，记录当前存活(2秒内)的所有局域网内facelog 服务器
 * @author guyadong
 *
 */
public class LanServiceHeartbeatListener {
	private static final Logger logger = LoggerFactory.getLogger(LanServiceHeartbeatListener.class);

	/** 执行组播包接收线程池对象 */
	private static final ExecutorService executor = MoreExecutors.getExitingExecutorService(
			new ThreadPoolExecutor(1, 1,
	                0L, TimeUnit.MILLISECONDS,
	                new LinkedBlockingQueue<Runnable>(),
	                new ThreadFactoryBuilder().setNameFormat("mc-listener-%d").build()));
	private final static Cache<String, ServiceHeartbeatPackage> cache 
		= CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).<String, ServiceHeartbeatPackage>build();
	private static final ConcurrentMap<String, ServiceHeartbeatPackage> cacheMap = cache.asMap();
	private static final Comparator<ServiceHeartbeatPackage> comparator= new Comparator<ServiceHeartbeatPackage>(){

		@Override
		public int compare(ServiceHeartbeatPackage o1, ServiceHeartbeatPackage o2) {
			return (int) (o1.readTimestamp() - o2.readTimestamp());
		}};
	private static final Predicate<byte[]> processor = new Predicate<byte[]>() {

		@Override
		public boolean apply(byte[] input) {
			ServiceHeartbeatPackage serviceHeartbeatPackage = 
					BaseJsonEncoder.getEncoder().fromJson(new String(input), ServiceHeartbeatPackage.class);
			synchronized (cacheMap) {
				cacheMap.put(serviceHeartbeatPackage.getHost(), serviceHeartbeatPackage);
			}
			return true;
		}
	};
	public static final LanServiceHeartbeatListener INSTANCE = new LanServiceHeartbeatListener().start();
	static{
		Runtime.getRuntime().addShutdownHook(new Thread(){

			@Override
			public void run() {
				INSTANCE.stop();
			}});
	}
	private final MultiCastDispatcher multiCastDispatcher;
	private LanServiceHeartbeatListener() {
		try {
			multiCastDispatcher = new MultiCastDispatcher(CommonConstant.MULTICAST_ADDRESS, 512, 
					processor,
					Predicates.<Throwable>alwaysFalse());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public synchronized LanServiceHeartbeatListener start(){
		if(!multiCastDispatcher.isRunning()){
			try {
				executor.execute(multiCastDispatcher.init().running());
			} catch (IOException e) {
				logger.error(e.getMessage());
				throw new RuntimeException(e);
			}
		}
		return this;
	}
	public LanServiceHeartbeatListener stop(){
		multiCastDispatcher.stop();
		return this;
	}
	/**
	 * 返回所有局域网内发现的当前活动的facelog服务器(以服务启动时间升序排序)
	 * @return 没有找到局域网服务则返回空表
	 */
	public List<ServiceHeartbeatPackage> lanServers(){
		synchronized (cacheMap) {
			ArrayList<ServiceHeartbeatPackage> list = Lists.newArrayList(cacheMap.values());
			Collections.sort(list, comparator);
			return list;
		}
	}

	/**
	 * 从服务心跳包中的数据中获取第一个可以访问的服务端IP地址
	 * @param serviceHeartbeatPackage
	 * @return 如果服务心跳中提供的地址都不可访问则返回{@code null}
	 */
	public static String firstReachableAddress(ServiceHeartbeatPackage serviceHeartbeatPackage){
		try {
			String address = JcifsUtil.hostAddressOf(serviceHeartbeatPackage.getHost());
			if(NetworkUtil.isReachable(address,serviceHeartbeatPackage.getPort(),500)){
				return address;
			}
		} catch (UnknownHostException e) {
			for(String address:serviceHeartbeatPackage.readAddresses()){
				if(NetworkUtil.isReachable(address,serviceHeartbeatPackage.getPort(),500)){
					return address;
				}
			}
		}
		return null;
	}
}
