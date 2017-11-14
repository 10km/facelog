package gu.simplemq.redis;

import java.util.concurrent.BlockingDeque;

import gu.simplemq.IQueueComponent;

/**
 * @author guyadong
 *
 * @param <E>
 */
public interface IRedisQueue<E> extends BlockingDeque<E>,IQueueComponent<E>,IRedisComponent {	
}
