package net.gdface.facelog;

import static org.junit.Assert.*;

import org.junit.Test;

import net.gdface.facelog.ServiceSecurityException.FieldJsonTransformer;
import net.gdface.facelog.ServiceSecurityException.SecurityExceptionType;

public class FieldJsonTransformerTest {

	@Test
	public void test() {
		System.out.println(FieldJsonTransformer.class.getName());
		ServiceSecurityException exp = new ServiceSecurityException(SecurityExceptionType.INVALID_DEVICE_ID);
		System.out.println(exp.toString());
	}

}
