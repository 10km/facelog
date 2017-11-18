package net.gdface.facelog.client;

import static org.junit.Assert.*;

import org.junit.Test;

import gu.simplemq.redis.JedisPoolLazy;
import net.gdface.facelog.device.Heartbeat;

/**
 * @author guyadong
 *
 */
public class HeartbeatTest {

	@Test
	public void test() {
		byte[] address = new byte[]{0x20,0x20,0x20,0x20,0x20,0x20};
		Heartbeat.startHeartbeat(address, 12345, JedisPoolLazy.getDefaultInstance());
		System.out.println("Heartbeat thead start");
	}

}
