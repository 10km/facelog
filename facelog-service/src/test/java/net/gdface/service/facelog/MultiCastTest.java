package net.gdface.service.facelog;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.gdface.facelog.CommonConstant;
import net.gdface.utils.MultiCastDispatcher;

public class MultiCastTest {
	private static MultiCastDispatcher dispatcher;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dispatcher = new MultiCastDispatcher(CommonConstant.MULTICAST_ADDRESS, 512,new Predicate<byte[]>() {

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
		Predicates.<Throwable>alwaysFalse()).init();
		new Thread(dispatcher).start();

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		// 在此设置断点暂停，可以看到线程输出心跳包
		dispatcher.close();
	}

}
