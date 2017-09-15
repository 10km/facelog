package net.gdface.facelog.message;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于阻塞队列 {@link BlockingQueue} 实现消费者模型<br>
 * 默认配置为队列模型,设置{@link #isFifo}为false,则为栈模型,为栈模型时 {@link #queue}必须为双向队列{@link BlockingDeque}<br>
 * 对象可以复用(反复打开关闭) <br>
 * 应用程序结束时要调用 {@link #close()} 才能结束消费线程<br>
 * 当设置为{@link #daemon}为true时,无需{@link #close()}关闭
 * @author guyadong
 *
 * @param <T> 消费数据类型
 */
public class Consumer<T>implements AutoCloseable,Constant,IQueueComponent<T>{
	protected static final Logger logger = LoggerFactory.getLogger(Subcriber.class);

	public static class BreakException extends RuntimeException{
		private static final long serialVersionUID = 1L;		
	}	
	
	/**
	 * 消费数据处理接口
	 * @author guyadong
	 *
	 * @param <T>
	 */
	public static interface Action<T>{
		/**
		 * @param t
		 * @throws BreakException 抛出时中止消费线程
		 */
		void consume(T t)throws BreakException;
	}
	
	protected BlockingQueue<T> queue;
	
	public Consumer() {
	}
	public Consumer(BlockingQueue<T> queue) {
		super();
		this.queue = queue;
	}

	public final Action<T> nullAction=new Action<T>(){
		@Override
		public void consume(T t) {
		}};
	private Action<T> action=nullAction;
	private boolean daemon=false;
	/** 获取队列的超时参数 */
	private int timeoutMills = DEFAULT_CONSUMER_CHECK_INTERVAL;
	private enum State{INIT,OPENED,CLOSED}
	private State state = State.INIT;
	/** 是否为先进先出队列 */
	private boolean isFifo = true;
	/** 执行消息线程的线程池对象 */
	private ExecutorService executorService;
	/**
	 * 创建消费线程,如果指定了{@link #executorService} ，则消费线程在线程池中执行<br>
	 * 否则创建新线程
	 */
	public synchronized void open(){
		if( state != State.INIT)return;
		if(null == queue)
			throw new NullPointerException("the field 'queue' not be initialized");
		Runnable run = new Runnable(){
			@Override
			public void run() {
				try {
					while (state != State.CLOSED) {
						T t;
						if(isFifo)
							t = queue.poll(timeoutMills, TimeUnit.MILLISECONDS);
						else{
							if(queue instanceof BlockingDeque)
								t = ((BlockingDeque<T>)queue).pollLast(timeoutMills, TimeUnit.MILLISECONDS);
							else
								throw new UnsupportedOperationException(" queue must be instance of  BlockingDeque");
						}
						if(null != t){
							try{
								action.consume(t);
							} catch (BreakException e) {
								logger.info("consumer thread finished because BreakException");
								break;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					state =State.INIT;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		if(null != executorService){
			try{
				executorService.submit(run);
				return ;
			}catch(RejectedExecutionException e){
				executorService = null;
				logger.warn("RejectedExecutionException: {}",e.getMessage());			}
		}
		Thread thread=new Thread(run);
		thread.setDaemon(this.daemon);
		thread.start();
		state = State.OPENED;
		return ;
	}
	
	@Override
	public void close(){
		if(state == State.OPENED){
			state = State.CLOSED;	
		} 
	}

	public Consumer<T> setAction(Action<T> action) {
		this.action = null == action?nullAction:action;
		return this;
	}
	
	/**
	 * @param timeoutMills (milliseconds)
	 * @return
	 */
	public Consumer<T> setTimeoutMills(int timeoutMills) {
		if(timeoutMills>0)
			this.timeoutMills = timeoutMills;
		return this;
	}
	
	public Consumer<T> setFifo(boolean fifo) {
		this.isFifo = fifo;
		return this;
	}
	
	@Override
	public BlockingQueue<T> getQueue() {
		return queue;
	}
	
	public Consumer<T> setQueue(BlockingQueue<T> queue) {
		this.queue = queue;
		return this;
	}
	
	@Override
	public String getQueueName() {
		return "unknow";
	}
	
	public Consumer<T> setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
		return this;
	}
	
	public Consumer<T> setDaemon(boolean daemon) {
		this.daemon = daemon;
		return this;
	}

}
