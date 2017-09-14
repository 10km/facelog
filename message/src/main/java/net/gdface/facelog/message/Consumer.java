package net.gdface.facelog.message;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer<T>implements AutoCloseable,Constant,IQueueComponent<T>{
	protected static final Logger logger = LoggerFactory.getLogger(Subcriber.class);

	public static class BreakException extends RuntimeException{
		private static final long serialVersionUID = 1L;		
	}	
	
	public static interface Action<T>{
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
	private int timeoutMills = DEFAULT_CONSUMER_CHECK_INTERVAL;
	private enum State{INIT,OPENED,CLOSED}
	private State state = State.INIT;
	private boolean isFifo = true;
	private ExecutorService executorService;
	/**
	 * @param executorService 指定运行的线程池,为null则创建一个新线程
	 * @return
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
						if(null != t)
							action.consume(t);
					}
					state =State.INIT;
				} catch (InterruptedException e) {
				} catch (BreakException e) {
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
	public void close() throws Exception {
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
