package net.gdface.facelog;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

import net.gdface.utils.NetworkUtil;

public class HttpTest {
	private static final Logger logger = LoggerFactory.getLogger(HttpTest.class);
	
    @Test
	public void test() {
		try {
			URL url = new URL("http://localhost:6380");
			boolean connected = NetworkUtil.testHttpConnectChecked(url,new Predicate<String>() {

				@Override
				public boolean apply(String input) {
					return null == input ? false : input.startsWith("webredis");
				}
			});
			logger.info("conect {} -- {}",url,connected);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
