package net.gdface.facelog;

import static org.junit.Assert.*;

import org.junit.Test;

import gu.simplemq.redis.JedisPoolLazy;
import net.gdface.facelog.device.Heartbeat;

public class TestHeartbeat {

	@Test
	public void test() {
		Heartbeat.startHeartbeat("12345", JedisPoolLazy.getDefaultInstance());
		System.out.println("Heartbeat thead start");
	}

}
