package net.gdface.facelog.client;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestClient implements CommonConstant {

	private static IFaceLogClient facelogClient;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		facelogClient = ClientFactory.builder().setHostAndPort("127.0.0.1", DEFAULT_PORT).build();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Test
	public void test() {
		PersonBean newPerson = new PersonBean();
		newPerson.setName("guyadong");
		try {
			facelogClient.savePerson(newPerson);
			logger.info("person = {}", newPerson.toString());
			PersonBean person = facelogClient.getPerson(newPerson.getId());
			logger.info("person = {}", person.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
