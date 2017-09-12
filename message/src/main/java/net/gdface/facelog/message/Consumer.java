package net.gdface.facelog.message;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Consumer<T>implements AutoCloseable{
	public static class BreakException extends RuntimeException{
		private static final long serialVersionUID = 1L;		
	}
	
	public static final int TIME_OUT = 2000;
	
	public static interface Action<T>{
		void onMessage(T t)throws BreakException;
	}
	
	private BlockingDeque<T> queue;
	
	public Consumer(BlockingDeque<T> queue) {
		super();
		this.queue = queue;
	}

	public final Action<T> nullAction=new Action<T>(){
		@Override
		public void onMessage(T t) {
		}};
	private Action<T> action=nullAction;
	private int timeoutMills = TIME_OUT;
	private boolean isOpened = false;
	private boolean isClosed = false;
	private boolean isFifo = true;
	private void checkState(){
		if(isClosed || isOpened)
			throw new IllegalStateException();	
	}
	public synchronized Consumer<T> open(ExecutorService executorService){
		checkState();
		Runnable run = new Runnable(){
			@Override
			public void run() {
				try {
					while (!isClosed) {
						T t;
						if(isFifo)
							t = queue.poll(timeoutMills, TimeUnit.MILLISECONDS);
						else
							t = queue.pollLast(timeoutMills, TimeUnit.MILLISECONDS);
						if(null != t)
							action.onMessage(t);
					}
				} catch (InterruptedException e) {
				} catch (BreakException e) {
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		if(null != executorService)
			executorService.submit(run);
		else
			new Thread(run).start();
		isOpened = true;
		return this;
	}
	
	public Consumer<T> open(){
		return this.open(null);
	}
	
	@Override
	public void close() throws Exception {
		if(!isOpened)
			throw new IllegalStateException();	
		this.isClosed = true;
		this.queue = null;
	}

	public Consumer<T> setAction(Action<T> action) {
		checkState();
		this.action = null == action?nullAction:action;
		return this;
	}
	
	public Consumer<T> setTimeoutMills(int timeoutMills) {
		checkState();
		if(timeoutMills>0)
			this.timeoutMills = timeoutMills;
		return this;
	}
	
	public Consumer<T> setFifo(boolean fifo) {
		checkState();
		this.isFifo = fifo;
		return this;
	}

}
