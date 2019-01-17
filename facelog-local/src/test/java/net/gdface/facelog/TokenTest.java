package net.gdface.facelog;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.hash.Hashing;

import net.gdface.exception.ServiceRuntimeException;
import net.gdface.facelog.db.PersonBean;

public class TokenTest implements ServiceConstant{

	@Test
	public void testApplyRootToken() {
		FaceLogImpl instance = new FaceLogImpl();
		try {
			String password = CONFIG.getString(ROOT_PASSWORD);
			String passwordMd5 = Hashing.md5().hashBytes(password.getBytes()).toString();
			Token token = instance.applyRootToken(passwordMd5, true);
			logger.info(token.toString());
		} catch (ServiceRuntimeException e) {
			e.printStackTrace();
		} catch (ServiceSecurityException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testSavePerson(){
		FaceLogImpl instance = new FaceLogImpl();
		try {
			String password = "do you know ?";
			String passwordMd5 = Hashing.md5().hashBytes(password.getBytes()).toString();
			PersonBean person = PersonBean.builder().name("顾亚东").password(passwordMd5).build();
			instance.savePerson(person, null);
			logger.info(person.toString(true, false));
			assertTrue("password check not pass",instance.isValidPassword(Integer.toString(person.getId()), password, true, null));
		} catch (ServiceRuntimeException e) {
			e.printStackTrace();
		} catch (ServiceSecurityException e) {
			e.printStackTrace();
		}
	}

}
