package net.gdface.facelog.simplemq;

import java.util.concurrent.BlockingDeque;

public interface IRedisQueue<E> extends BlockingDeque<E>,IQueueComponent<E>,IRedisComponent {	
}
