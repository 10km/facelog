package net.gdface.facelog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import net.gdface.utils.DefaultExecutorProvider;

/**
 * 基于配置文件指定的参数提供全局线程池对象<br>
 * 线程池对象在应用程序结束时自动退出
 * @author guyadong
 *
 */
class ExecutorProvider extends DefaultExecutorProvider implements ServiceConstant {
	private static class Singleton{
		private static final ExecutorProvider INSTANCE = new ExecutorProvider();
	}
	/** 根据配置文件指定的参数创建通用任务线程池对象 */
	@Override
	protected final ExecutorService createExitingCachedPool(){
		int corePoolSize = CONFIG.getInt(EXECUTOR_CACHEDPOOL_COREPOOLSIZE,Runtime.getRuntime().availableProcessors());
		int maximumPoolSize = CONFIG.getInt(EXECUTOR_CACHEDPOOL_MAXIMUMPOOLSIZE);
		long keepAliveTime = CONFIG.getLong(EXECUTOR_CACHEDPOOL_KEEPALIVETIME);
		int queueCapacity = CONFIG.getInt(EXECUTOR_CACHEDPOOL_QUEUECAPACITY);
		String nameFormat = CONFIG.getString(EXECUTOR_CACHEDPOOL_NAMEFORMAT);
		return createCachedPool(corePoolSize, maximumPoolSize, keepAliveTime, queueCapacity, nameFormat);
	}
	/** 根据配置文件指定的参数创建定时任务线程池对象 */
	@Override
	protected final ScheduledExecutorService createExitingScheduledPool(){
		int corePoolSize = CONFIG.getInt(EXECUTOR_TIMERPOOL_COREPOOLSIZE);
		String nameFormat = CONFIG.getString(EXECUTOR_TIMERPOOL_NAMEFORMAT);
		return createScheduledPool(corePoolSize, nameFormat);
	}
	private ExecutorProvider() {
	}
	/** 返回全局线程池 */
	public static ExecutorService getGlobalExceutor() {
		return Singleton.INSTANCE.globalExecutor;
	}
	/** 返回定时任务线程池 */
	public static ScheduledExecutorService getTimerExecutor() {
		return Singleton.INSTANCE.timerExecutor;
	}

}
