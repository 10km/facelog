package net.gdface.facelog.simplemq;

import java.util.concurrent.BlockingQueue;

/**
 * 队列组件接口
 * @author guyadong
 *
 * @param <T> 队列元素类型
 */
public interface IQueueComponent<T> {
	/**
	 * 返回队列名称
	 * @return
	 */
	public String getQueueName();
	/**
	 * 返回队列对象
	 * @return
	 */
	public BlockingQueue<T> getQueue();
}
