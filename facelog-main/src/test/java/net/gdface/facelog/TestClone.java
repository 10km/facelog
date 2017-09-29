package net.gdface.facelog;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestClone {
	public static class A implements Cloneable{
		final String string;

		public A(String string) {
			super();
			this.string = string;
		}
		@Override
		public A clone(){
			try {
				return (A) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
		}
/*		@Override
		protected Object clone()  {
			return new A(string);
		} */
	}
	@Test
	public void test()  {
		A a = new A("hello");
		A cloneA= a.clone();
		System.out.println(cloneA.string);
	}

}
