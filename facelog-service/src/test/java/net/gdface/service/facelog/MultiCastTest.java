package net.gdface.service.facelog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.gdface.facelog.CommonConstant;
import net.gdface.utils.MultiCastDispatcher;

/**
 * 服务心跳组播包侦听测试
 * @author guyadong
 *
 */
public class MultiCastTest {
	private static MultiCastDispatcher dispatcher;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String multiAddr = MoreObjects.firstNonNull(System.getProperty("address"), CommonConstant.MULTICAST_ADDRESS);
		dispatcher = new MultiCastDispatcher(multiAddr, 512,new Predicate<byte[]>() {

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
	
	private static void waitquit(){
		System.out.println("PRESS 'quit' OR 'CTRL-C' to exit");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		try{
			while(!"quit".equalsIgnoreCase(reader.readLine())){				
			}
			System.exit(0);
		} catch (IOException e) {

		}finally {

		}
	}
	@Test
	public void test() {
		System.out.printf("Listen multicast IP %s:%d\n",dispatcher.getGroup().getHostAddress(),dispatcher.getPort());
		waitquit();
		dispatcher.stop();
	}

}
