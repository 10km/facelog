package net.gdface.facelog.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPubSub;

public class SubscriberHandle extends JedisPubSub {
	private static final Logger logger = LoggerFactory.getLogger(SubscriberHandle.class);
	private IOnMessage onMessageHandle;
	public SubscriberHandle(IOnMessage onMessageHandle) {
		super();
		this.onMessageHandle = onMessageHandle;
	}

	public SubscriberHandle() {
	}

	@Override
	public void onMessage(String channel, String message) {
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
