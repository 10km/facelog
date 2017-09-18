package net.gdface.facelog.message;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import net.gdface.facelog.message.IMessageAdapter.UnsubscribeException;

/**
 * 基于阻塞队列 {@link BlockingQueue} 实现单个消费者模型<br>
 * 默认配置为队列模型,设置{@link #isFifo}为false,则为栈模型,为栈模型时 {@link #queue}必须为双向队列{@link BlockingDeque}<br>
 * 需要显式调用 {@link #open()}开启消费线程<br>
 * 对象可以复用(反复打开关闭) <br>
 * 应用程序结束时要调用 {@link #close()} 才能结束消费线程<br>
 * 当设置为{@link #daemon}为true时,无需{@link #close()}关闭
 * @author guyadong
 *
 * @param <T> 消费数据类型
 */
public class ConsumerSingle<T> extends AbstractConsumer implements IQueueComponent<T>{
	protected BlockingQueue<T> queue = new LinkedBlockingDeque<T>();
	private final Runnable customRunnable = new Runnable(){
		@Override
		public void run() {
			try {
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
						adapter.onSubscribe(t);
					} catch (UnsubscribeException e) {
						logger.info("consumer thread finished because UnsubscribeException");
						close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	public ConsumerSingle() {
	}
	public ConsumerSingle(BlockingQueue<T> queue) {
		super();
		this.queue = queue;
	}
	/** 消息处理器 */
	private IMessageAdapter<T> adapter = new IMessageAdapter<T>(){
		@Override
		public void onSubscribe(T t) throws net.gdface.facelog.message.IMessageAdapter.UnsubscribeException {
		}};
	
	public ConsumerSingle<T> setAdapter(IMessageAdapter<T> adapter) {
		if(null != adapter){
			this.adapter = adapter;
		}
		return this;
	}
	
	@Override
	protected Runnable getCustomRunnable(){
		return customRunnable;
	}
	
	@Override
	public BlockingQueue<T> getQueue() {
		return queue;
	}
	
	public ConsumerSingle<T> setQueue(BlockingQueue<T> queue) {
		if(null != queue){
			this.queue = queue;
		}
		return this;
	}
	
	@Override
	public String getQueueName() {
		return "unknow";
	}

	/**
	 * 改为public访问 
	 */
	@Override
	public void open() {
		super.open();
	}
	
	
}
