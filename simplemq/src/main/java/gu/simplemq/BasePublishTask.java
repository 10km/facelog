package gu.simplemq;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.Executor;

import gu.simplemq.Channel;
import gu.simplemq.IPublisher;

/**
 * 实现向指定{@link Channel}发布消息的任务封装<br>
 * 将发布消息的动作封装为{@link Runnable}任务对象提交到线程池执行
 * 创建对象后调用 {@link #execute()}执行发布
 * @author guyadong
 *
 * @param <T> 发布数据类型
 */
public abstract class BasePublishTask<T> implements Runnable,Constant{
	private final IPublisher publisher;
	private final Channel<T> channel;
	private final T input;

	/**
	 * 构造函数
	 * @param channel 发布消息的频道
	 * @param input 发布数据对象
	 * @param publisher 
	 */
	public BasePublishTask(Channel<T> channel,T input,IPublisher publisher) {
		this.publisher = checkNotNull(publisher);
		this.channel = checkNotNull(channel);
		this.input = checkNotNull(input);
	}
	@Override
	public void run() {
		try{
			publisher.publish(channel, input);
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}
	/** 将当前任务提交到线程池执行 */
	public void execute(){
		getExecutor().execute(this);
	}
	/** 子类提供线程池对象 */
	protected abstract Executor getExecutor();
}
