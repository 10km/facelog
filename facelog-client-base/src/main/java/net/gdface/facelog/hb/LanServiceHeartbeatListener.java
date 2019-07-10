package net.gdface.facelog.hb;

import static net.gdface.utils.NetworkUtil.recevieMultiCastLoop;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import gu.simplemq.json.BaseJsonEncoder;
import net.gdface.facelog.CommonConstant;
import net.gdface.facelog.ServiceHeartbeatPackage;

public class LanServiceHeartbeatListener {
	/** 执行消息线程的线程池对象 */
	private static final ExecutorService executor = MoreExecutors.getExitingExecutorService(
			new ThreadPoolExecutor(1, 1,
	                0L, TimeUnit.MILLISECONDS,
	                new LinkedBlockingQueue<Runnable>(),
	                new ThreadFactoryBuilder().setNameFormat("mc-listener-%d").build()));
	private final static Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			try {
				recevieMultiCastLoop(CommonConstant.MULTICAST_ADDRESS, 512,new Predicate<byte[]>() {

					@Override
					public boolean apply(byte[] input) {
						ServiceHeartbeatPackage serviceHeartbeatPackage = 
								BaseJsonEncoder.getEncoder().fromJson(new String(input), ServiceHeartbeatPackage.class);
						
						return true;
					}
				},
				Predicates.<Throwable>alwaysFalse(),
				null);
			} catch (IOException e) {					
				e.printStackTrace();
			}			
		}
	};
	private LanServiceHeartbeatListener() {
	
	}
	public static void start(){
		executor.execute(runnable);
	}
}
