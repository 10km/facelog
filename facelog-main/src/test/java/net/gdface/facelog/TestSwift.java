package net.gdface.facelog;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSwift {
	@Test
	public void test() {
		 TestBean bean = new TestBean();
		 System.out.println(bean.readBlob());
	}

}
