package net.gdface.facelog.client;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

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
		Heartbeat hb = Heartbeat.makeHeartbeat(address, 12345, JedisPoolLazy.getDefaultInstance()).setMonitorChannel("hb_monitor");
		hb.startTimer();
		System.out.println("Heartbeat thead start");
		hb.setInterval(2, TimeUnit.SECONDS).startTimer();
	}

}
