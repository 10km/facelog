package net.gdface.facelog.message;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Producer<T> implements IQueueComponent<T>{
	protected BlockingQueue<T> queue;
	public Producer() {
		super();
	}

	public Producer(BlockingQueue<T> queue) {
		super();
		this.queue = queue;
	}
	
	public boolean push(T t,boolean fifo){
		if(null == queue)
			throw new NullPointerException("the field 'queue' not be initialized");
		if(! fifo ){
			if(queue instanceof BlockingDeque)
				return ((BlockingDeque<T>)queue).offerFirst(t);
			else
				throw new UnsupportedOperationException(" queue must be instance of  BlockingDeque");
		}else
			return queue.offer(t);
	}
	
	public boolean push(T t){
		if(null == queue)
			throw new NullPointerException("the field 'queue' not be initialized");
		return queue.offer(t);
	}
	
	@Override
	public BlockingQueue<T> getQueue() {
		return queue;
	}

	public Producer<T> setQueue(BlockingQueue<T> queue) {
		this.queue = queue;
		return this;
	}

	@Override
	public String getQueueName() {
		return "unknow";
	}

}
