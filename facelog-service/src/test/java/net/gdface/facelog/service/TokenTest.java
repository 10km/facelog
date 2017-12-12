package net.gdface.facelog.service;

import static org.junit.Assert.*;

import org.junit.Test;

import net.gdface.facelog.db.PersonBean;
import net.gdface.utils.FaceUtilits;

public class TokenTest implements ServiceConstant{

	@Test
	public void testApplyRootToken() {
		FaceLogImpl instance = new FaceLogImpl();
		try {
			String password = CONFIG.getString(ROOT_PASSWORD);
			String passwordMd5 = FaceUtilits.getMD5String(password.getBytes());
			Token token = instance.applyRootToken(passwordMd5);
			logger.info(token.toString());
		} catch (ServiceRuntimeException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ServiceSecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	@Test
	public void testSavePerson(){
		FaceLogImpl instance = new FaceLogImpl();
		try {
			String password = "do you know ?";
			String passwordMd5 = FaceUtilits.getMD5String(password.getBytes());
			PersonBean person = PersonBean.builder().name("顾亚东").password(passwordMd5).build();
			instance.savePerson(person, null);
			logger.info(person.toString(true));
			assertTrue("password check not pass",instance.isValidPassword(Integer.toString(person.getId()), password, true, null));
		} catch (ServiceRuntimeException e) {
			e.printStackTrace();
		} catch (ServiceSecurityException e) {
			e.printStackTrace();
		}
	}

}
