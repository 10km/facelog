package net.gdface.facelog;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 * @author guyadong
 *
 */
public class CloneTest {
	public static class ClassA implements Cloneable{
		final String string;

		public ClassA(String string) {
			super();
			this.string = string;
		}
		@Override
		public ClassA clone(){
			try {
				return (ClassA) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	@Test
	public void test() throws ParseException  {
		ByteBuffer buffer = ByteBuffer.wrap(new byte[]{1,2,3,4,5,6,7,8,9,0,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f});
		System.out.println(Long.toHexString(buffer.getLong()));
		System.out.println(Long.toHexString(buffer.getLong()));
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//Date date = format.parse("2050-12-31");
		//Date date = DateFormat.getDateTimeInstance().parse("2050-12-31 12:12:1.2");
		//Date date = java.text.DateFormat.getDateInstance().parse("2050-12-31",new java.text.ParsePosition(0));
		//java.text.DateFormat.getDateTimeInstance().parse("2017-12-31 12:12:12.0",new java.text.ParsePosition(0));
		Date date = java.sql.Timestamp.valueOf("2017-12-31 12:12:12.0");
		System.out.println(date);
		ClassA a = new ClassA("hello");
		ClassA cloneA= a.clone();
		System.out.println(cloneA.string);
	}

}
