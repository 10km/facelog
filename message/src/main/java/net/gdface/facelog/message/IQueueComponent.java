package net.gdface.facelog.message;

import java.util.concurrent.BlockingQueue;

public interface IQueueComponent<T> {
	public String getQueueName();
	public BlockingQueue<T> getQueue();
}
