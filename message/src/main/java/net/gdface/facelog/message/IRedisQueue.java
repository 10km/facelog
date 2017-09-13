package net.gdface.facelog.message;

import java.util.concurrent.BlockingDeque;

public interface IRedisQueue<E> extends BlockingDeque<E>,IQueueComponent<E>,IRedisComponent {	
}
