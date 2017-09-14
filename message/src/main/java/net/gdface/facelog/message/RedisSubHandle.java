package net.gdface.facelog.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPubSub;

public class RedisSubHandle extends JedisPubSub {
	private static final Logger logger = LoggerFactory.getLogger(RedisSubHandle.class);
	private IOnMessage onMessageHandle;
	public RedisSubHandle(IOnMessage onMessageHandle) {
		super();
		this.onMessageHandle = onMessageHandle;
	}

	public RedisSubHandle() {
	}

	@Override
	public void onMessage(String channel, String message) {
		if(Judge.isEmpty(channel) || Judge.isEmpty(message))return;
		try {
			if (null != onMessageHandle)
				onMessageHandle.onMessage(channel, message);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void setOnMessageHandle(IOnMessage onMessageHandle) {
		this.onMessageHandle = onMessageHandle;
	}
}
