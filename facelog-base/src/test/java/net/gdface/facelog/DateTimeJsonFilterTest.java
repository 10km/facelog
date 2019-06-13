package net.gdface.facelog;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeJsonFilterTest {

	private static final Logger logger = LoggerFactory.getLogger(DateTimeJsonFilterTest.class);
	@Test
	public void test() {
		try {
			DateTimeJsonFilter filter = new DateTimeJsonFilter();	
			logger.info("filter={}", filter.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
