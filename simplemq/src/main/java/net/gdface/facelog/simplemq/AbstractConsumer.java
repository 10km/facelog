package net.gdface.facelog.simplemq;

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
	protected abstract Runnable getCustomRunnable();
	/** 消费循环对象 */
	private final Runnable customeLoop = new Runnable(){
		@Override
		public void run() {
			final Runnable customRun = getCustomRunnable();
			while(isOpened()){
				try{
					if(null != customRun)
						customRun.run();	
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			reset();
		}			
	};
	/**
	 * 创建消费线程,如果指定了{@link #executorService} ，则消费线程在线程池中执行,
	 * 否则创建新线程(线程同步)<br>
	 * 此方法保证在多线程环境下只创建一个消费线程<br>
	 * {@link State#OPENED}是瞬态,所以当状态为{@link State#OPENED}时线程要等待状态切换为{@link State#INIT},
	 * 参见{@link #reset()}方法
	 */
	protected void open(){
		// Double Checked Locking
		// check 1
		try{
			if(state == State.OPENED)return;
			if(state == State.CLOSED){
				synchronized(this){
					while(state != State.INIT){
						// 等待状态变为State.INIT 参见reset()方法
						this.wait();
					}
				}
			}
			// 当前状态为 State.INIT
			synchronized(this){
				// check 2
				while(state != State.INIT){
					if(state == State.OPENED)return;
					if(state == State.CLOSED){
						// 等待状态变为State.INIT 参见reset()方法
						this.wait();
					}
				}
				state = State.OPENED;	
				if(null != executorService){
					try{
						executorService.submit(customeLoop);
						return ;
					}catch(RejectedExecutionException e){
						executorService = null;
						logger.warn("RejectedExecutionException: {}",e.getMessage());			
					}
				}
				Thread thread=new Thread(customeLoop);
				thread.setDaemon(this.daemon);
				thread.start();
				return ;
			}
		} catch (InterruptedException e) {}
	}
	
	/**
	 * 结束消费线程(线程同步)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close(){
		synchronized( this ){
			if(state == State.OPENED)
				state = State.CLOSED;
		}
	}

	private boolean isOpened(){
		return this.state == State.OPENED;
	}

	/** 状态置为 {@link State#INIT} */
	private void reset(){
		synchronized( this ){
			this.state = State.INIT;
			this.notifyAll();
		}
	}

	/**
	 * @param timeoutMills (milliseconds)
	 * @return
	 * @see #timeoutMills
	 */
	public AbstractConsumer  setTimeoutMills(int timeoutMills) {
		if(timeoutMills>0)
			this.timeoutMills = timeoutMills;
		return this;
	}
	
	/**
	 * @see #isFifo
	 * @param fifo  
	 * @return
	 */
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
}
