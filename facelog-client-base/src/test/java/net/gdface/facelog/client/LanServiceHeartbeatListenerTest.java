package net.gdface.facelog.client;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.gdface.facelog.hb.LanServiceHeartbeatListener;

public class LanServiceHeartbeatListenerTest {
	private static final Logger logger = LoggerFactory.getLogger(LanServiceHeartbeatListenerTest.class);
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() throws InterruptedException {
		LanServiceHeartbeatListener.INSTANCE.start();
		Thread.sleep(5000);
		LanServiceHeartbeatListener.INSTANCE.lanServers();
		logger.info("service {}",LanServiceHeartbeatListener.INSTANCE.lanServers());
	}

}
