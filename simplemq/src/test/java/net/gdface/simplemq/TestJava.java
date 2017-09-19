package net.gdface.simplemq;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestJava {

	@Test
	public void testBreak() {
		int count =0;
		l1:{
			if(count++<10)
			System.out.println("break");
			break l1;
		}
	}

}
