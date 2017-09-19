package net.gdface.facelog.simplemq.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.gdface.facelog.simplemq.IMessageDispatcher;
import net.gdface.facelog.simplemq.utils.Judge;
import redis.clients.jedis.JedisPubSub;

public class RedisSubHandle extends JedisPubSub {
	private static final Logger logger = LoggerFactory.getLogger(RedisSubHandle.class);
	private IMessageDispatcher dispatcher;
	public RedisSubHandle(IMessageDispatcher dispatcher) {
		super();
		this.dispatcher = dispatcher;
	}

	public RedisSubHandle() {
	}

	@Override
	public void onMessage(String channel, String message) {
		if(Judge.isEmpty(channel) || Judge.isEmpty(message))return;
		try {
			if (null != dispatcher)
				dispatcher.dispatch(channel, message);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void setDispatcher(IMessageDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
}
