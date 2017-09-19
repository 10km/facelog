package net.gdface.facelog.simplemq.redis;

import java.util.concurrent.BlockingDeque;

import net.gdface.facelog.simplemq.IQueueComponent;

public interface IRedisQueue<E> extends BlockingDeque<E>,IQueueComponent<E>,IRedisComponent {	
}
