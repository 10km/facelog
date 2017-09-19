package gu.simplemq.redis;

import java.util.concurrent.BlockingDeque;

import gu.simplemq.IQueueComponent;

public interface IRedisQueue<E> extends BlockingDeque<E>,IQueueComponent<E>,IRedisComponent {	
}
