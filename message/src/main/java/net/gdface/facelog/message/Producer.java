package net.gdface.facelog.message;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * 基于阻塞队列 {@link BlockingQueue} 实现生产者模型<br>
 * @author guyadong
 *
 * @param <T>
 */
public class Producer<T> implements IQueueComponent<T>{
	protected BlockingQueue<T> queue;
	public Producer() {
		super();
	}

	public Producer(BlockingQueue<T> queue) {
		super();
		this.queue = queue;
	}
	
	/**
	 * 向队列{@link #queue}中压入数据
	 * @param t
	 * @param fifo 为false则为栈模型(向队列头部添加数据),<br>
	 *                      为栈模型时 {@link #queue}必须为双向队列{@link BlockingDeque}<br>
	 * @return
	 */
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
	
	/**
	 * 向队列尾部添加数据
	 * @param t
	 * @return
	 */
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
