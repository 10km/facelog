package net.gdface.facelog;

import java.util.concurrent.Executor;

import gu.simplemq.BasePublishTask;
import gu.simplemq.Channel;
import gu.simplemq.IPublisher;

/**
 * 将REDIS发布消息的动作封装为{@link Runnable}任务对象提交到线程池{@link #GLOBAL_EXCEUTOR}执行
 * @author guyadong
 *
 * @param <T> 发布数据类型
 */
public class RedisPublishTask<T> extends BasePublishTask<T> implements ServiceConstant{

	/**
	 * 构造函数
	 * @param channel 发布消息的频道
	 * @param input 发布数据对象
	 * @param publisher 
	 */
	public RedisPublishTask(Channel<T> channel,T input,IPublisher publisher) {
		super(channel, input, publisher);
	}

	@Override
	protected Executor getExecutor() {
		return GLOBAL_EXCEUTOR;
	}
}
