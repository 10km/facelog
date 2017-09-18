package net.gdface.facelog.message;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * 基于阻塞队列 {@link BlockingQueue} 实现生产者模型<br>
 * @author guyadong
 *
 * @param <T>
 */
public class Producer<T> extends IProducer.AbstractHandler<T> implements IQueueComponent<T>{
	protected BlockingQueue<T> queue;
	public Producer() {
		super();
	}

	public Producer(BlockingQueue<T> queue) {
		super();
		this.queue = queue;
	}
	
	@Override
	public boolean produce(T t,boolean offerLast){
		if(! offerLast ){
			if(queue instanceof BlockingDeque)
				return ((BlockingDeque<T>)queue).offerFirst(t);
			else
				throw new UnsupportedOperationException(" queue must be instance of  BlockingDeque");
		}else
			return queue.offer(t);
	}
	
	@Override
	public boolean produce(T t){
		if(this.offerLast)
			return queue.offer(t);
		else
			return produce(t,offerLast);
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
