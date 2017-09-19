package net.gdface.simplemq.redis;

import java.util.concurrent.BlockingDeque;

import net.gdface.simplemq.IQueueComponent;

public interface IRedisQueue<E> extends BlockingDeque<E>,IQueueComponent<E>,IRedisComponent {	
}
