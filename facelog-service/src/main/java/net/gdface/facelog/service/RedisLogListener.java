package net.gdface.facelog.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import gu.simplemq.Channel;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.service.CommonConstant;

/**
 * 验证日志表({@code fl_log})增加侦听器<br>
 * 当{@code fl_log}记录增加时发布 redis 订阅消息
 * @author guyadong
 *
 */
class RedisLogListener extends TableListener.Adapter<LogBean> implements CommonConstant{

	private final RedisPublisher publisher;
	private final Channel<LogBean> channel;
	
	/**
	 * 使用{@link JedisPoolLazy}默认实例
	 * @param logMonitorChannel
	 * @see #RedisLogListener(String, JedisPoolLazy)
	 */
	public RedisLogListener(String logMonitorChannel) {
		this(logMonitorChannel,JedisPoolLazy.getDefaultInstance());
	}
	/**
	 * 构造方法
	 * @param logMonitorChannel 人员验证实时监控通道名
	 * @param jedisPoolLazy
	 */
	public RedisLogListener(String logMonitorChannel,JedisPoolLazy jedisPoolLazy) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(logMonitorChannel),"INVALID logMonitorChannel %s",logMonitorChannel);
		this.channel = new Channel<LogBean>(logMonitorChannel){};
		this.publisher = RedisFactory.getPublisher(Preconditions.checkNotNull(jedisPoolLazy,"jedisPoolLazy is null"));
	}
	@Override
	public void afterInsert(final LogBean bean) {
		new RedisPublishTask<LogBean>(
				channel, 
				bean, 
				publisher)
		.execute();		
	}
}
