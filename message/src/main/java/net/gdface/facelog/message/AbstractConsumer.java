package net.gdface.facelog.message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实现消费者模型抽象类<br>
 * 对象可以复用(反复打开关闭) <br>
 * 应用程序结束时要调用 {@link #close()} 才能结束消费线程<br>
 * 当设置为{@link #daemon}为true时,无需{@link #close()}关闭
 * @author guyadong
 *
 */
public abstract class AbstractConsumer implements AutoCloseable,Constant{
	protected static final Logger logger = LoggerFactory.getLogger(AbstractConsumer.class);

	public AbstractConsumer() {
	}

	private boolean daemon=false;
	/** 获取队列的超时参数 */
	protected int timeoutMills = DEFAULT_CONSUMER_CHECK_INTERVAL;
	private enum State{INIT,OPENED,CLOSED}
	/** 消费线程状态 */
	private State state = State.INIT;
	/** 是否为先进先出队列 */
	protected boolean isFifo = true;
	/** 执行消费线程的线程池对象 */
	private ExecutorService executorService;
	/**
	 * 消费线程实现,不可包含循环语句，循环由父类控制<br>
	 * 可调用 {@link #close()}结束线程
	 * @return
	 */
	protected abstract Runnable getRunnable();
	
	/**
	 * 创建消费线程,如果指定了{@link #executorService} ，则消费线程在线程池中执行<br>
	 * 否则创建新线程
	 */
	protected synchronized void open(){
		switch( state ){
		case OPENED:{
			try {
				synchronized(state){
					state.wait();
					// 在消费线程一个循环结束后判断状态
					if(state == State.OPENED)return;
				}
			} catch (InterruptedException e) {}
		}
		case CLOSED:
		case INIT:
		}
			
		Runnable run = new Runnable(){
			@Override
			public void run() {
				synchronized(state){
					// 通知主线程 子线程已经启动
					state.notifyAll();
				}
				while(state != State.CLOSED){
					try{
						getRunnable().run();
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						synchronized(state){
							// 每一个循环结束都通知阻塞的主线程
							state.notifyAll();
						}
					}
				}
			}			
		};
		try{
			if(null != executorService){
				try{
					executorService.submit(run);
					return ;
				}catch(RejectedExecutionException e){
					executorService = null;
					logger.warn("RejectedExecutionException: {}",e.getMessage());			
				}
			}
			Thread thread=new Thread(run);
			thread.setDaemon(this.daemon);
			thread.start();
			return ;
		}finally{
			synchronized(state){
				try {
					// 等待子线程启动
					state.wait();
				} catch (InterruptedException e) {
				}
			}
			state = State.OPENED;
		}
	}
	
	@Override
	public void close(){
		state = State.CLOSED;	
	}

	/**
	 * @param timeoutMills (milliseconds)
	 * @return
	 */
	public AbstractConsumer  setTimeoutMills(int timeoutMills) {
		if(timeoutMills>0)
			this.timeoutMills = timeoutMills;
		return this;
	}
	
	public AbstractConsumer  setFifo(boolean fifo) {
		this.isFifo = fifo;
		return this;
	}
	
	public AbstractConsumer  setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
		return this;
	}
	
	public AbstractConsumer  setDaemon(boolean daemon) {
		this.daemon = daemon;
		return this;
	}

	public boolean isOpened(){
		return this.state == State.OPENED;
	}
}
