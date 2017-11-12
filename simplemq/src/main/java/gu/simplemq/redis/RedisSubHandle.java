package gu.simplemq.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import gu.simplemq.IMessageDispatcher;
import redis.clients.jedis.JedisPubSub;

class RedisSubHandle extends JedisPubSub {
	private static final Logger logger = LoggerFactory.getLogger(RedisSubHandle.class);
	private IMessageDispatcher dispatcher;
	RedisSubHandle(IMessageDispatcher dispatcher) {
		super();
		this.dispatcher = dispatcher;
	}

	RedisSubHandle() {
	}

	@Override
	public void onMessage(String channel, String message) {
		if(Strings.isNullOrEmpty(channel) || Strings.isNullOrEmpty(message)){
			return;
		}
		try {
			if (null != dispatcher){
				dispatcher.dispatch(channel, message);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void setDispatcher(IMessageDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
}
