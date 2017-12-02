package net.gdface.facelog.service;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.base.MoreObjects;

import net.gdface.facelog.service.DuplicateRecordException;
import net.gdface.facelog.service.ServiceSecurityException;
import net.gdface.facelog.service.ServiceSecurityException.SecurityExceptionType;

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
	public String toJsonString(ServiceSecurityException exp){
		HashMap<String,Object> fields = new HashMap<String,Object>();
		fields.put("type", exp.getType());
		fields.put("deviceID", exp.getDeviceID());
		return JSON.toJSONString(fields);
	}
	@Test
	public void test() {
		try{
			{
				System.out.println("["+Long.toHexString(System.currentTimeMillis())+"]");
				System.out.printf("[%06x]\n", System.currentTimeMillis());
			}
			{
				ServiceSecurityException exp = new ServiceSecurityException("hello").setType(SecurityExceptionType.INVALID_PERSON_ID).setDeviceID(10);
				System.out.println(JSON.toJSONString(exp));
				JSON.parseObject(JSON.toJSONString(exp), ServiceSecurityException.class);
				String jsonStr = toJsonString(exp);
				System.out.println(jsonStr);
				System.out.println(exp.toString());
				JSONObject obj = JSON.parseObject(exp.toString());
				ServiceSecurityException newExp = TypeUtils.castToJavaBean(obj, ServiceSecurityException.class);
				byte[] array = new byte[8];
				ByteBuffer buffer = ByteBuffer.wrap(array);
				buffer.remaining();
				long nano = 0x12345678900a0b0cL;//System.nanoTime();
				System.out.printf("%s\n",Long.toHexString(nano));
				buffer.asLongBuffer().put(nano);

				System.out.println(Arrays.deepToString(new Object[]{buffer.array()}));
				System.out.println(MoreObjects.toStringHelper(buffer.array()).add("array",buffer.array()).toString());
			}
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
