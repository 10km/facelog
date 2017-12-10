package net.gdface.facelog.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 提供全局线程池对象<br>
 * 线程池对象在应用程序结束时自动退出
 * @author guyadong
 *
 */
class ExecutorProvider implements ServiceConstant {
	/** 全局线程池(自动退出封装) */
	private static final ExecutorService GLOBAL_EXCEUTOR = createCachedPool();
	/** 根据配置文件指定的参数创建通用任务线程池对象 */
	private static final ExecutorService createCachedPool(){
		int corePoolSize = CONFIG.getInt(EXECUTOR_CACHEDPOOL_COREPOOLSIZE,Runtime.getRuntime().availableProcessors());
		int maximumPoolSize = CONFIG.getInt(EXECUTOR_CACHEDPOOL_MAXIMUMPOOLSIZE);
		long keepAliveTime = CONFIG.getLong(EXECUTOR_CACHEDPOOL_KEEPALIVETIME);
		int queueCapacity = CONFIG.getInt(EXECUTOR_CACHEDPOOL_QUEUECAPACITY);
		String nameFormat = CONFIG.getString(EXECUTOR_CACHEDPOOL_NAMEFORMAT);
		ExecutorService executor = MoreExecutors.getExitingExecutorService(
				new ThreadPoolExecutor(
						corePoolSize, 
						maximumPoolSize,
						keepAliveTime, TimeUnit.SECONDS,
	                    new LinkedBlockingQueue<Runnable>(queueCapacity),
	                    new ThreadFactoryBuilder().setNameFormat(nameFormat).build())
				);
		return executor;
	}
	/** 定时任务线程池对象(自动退出封装) */
	private static final ScheduledExecutorService TIMER_EXECUTOR = createScheduledPool();
	/** 根据配置文件指定的参数创建定时任务线程池对象 */
	private static final ScheduledExecutorService createScheduledPool(){
		int corePoolSize = CONFIG.getInt(EXECUTOR_TIMERPOOL_COREPOOLSIZE);
		String nameFormat = CONFIG.getString(EXECUTOR_TIMERPOOL_NAMEFORMAT);
		ScheduledExecutorService timerExecutor = MoreExecutors.getExitingScheduledExecutorService(
				new ScheduledThreadPoolExecutor(
					corePoolSize,
					new ThreadFactoryBuilder().setNameFormat(nameFormat).build()));
		return timerExecutor;
	}
	ExecutorProvider() {
	}
	/** 返回全局线程池 */
	static ExecutorService getGlobalExceutor() {
		return GLOBAL_EXCEUTOR;
	}
	/** 返回定时任务线程池 */
	static ScheduledExecutorService getTimerExecutor() {
		return TIMER_EXECUTOR;
	}

}
