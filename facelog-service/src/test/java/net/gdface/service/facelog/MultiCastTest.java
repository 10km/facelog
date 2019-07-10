package net.gdface.service.facelog;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.gdface.facelog.CommonConstant;

import static net.gdface.utils.NetworkUtil.*;

public class MultiCastTest {
	private static AtomicBoolean stop = new AtomicBoolean(false);
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		new Thread(){

			@Override
			public void run() {
				try {
					recevieMultiCastLoop(CommonConstant.MULTICAST_ADDRESS, 512,new Predicate<byte[]>() {

						@Override
						public boolean apply(byte[] input) {
							try {
								System.out.write(input);
								System.out.println();
							} catch (IOException e) {
								e.printStackTrace();
							}
							return true;
						}
					},
					Predicates.<Throwable>alwaysFalse(),
					stop);
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
			
		}.start();

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		stop.set(true);
	}

}
