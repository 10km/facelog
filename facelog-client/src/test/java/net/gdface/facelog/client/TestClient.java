package net.gdface.facelog.client;

import org.apache.thrift.TApplicationException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.facebook.swift.service.RuntimeTApplicationException;

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
		PersonBean newPerson = PersonBean.builder().name("guyadong").build();
		try {
			newPerson = facelogClient.savePerson(newPerson);
			logger.info("person = {}", newPerson.toString());
			PersonBean person = facelogClient.getPerson(newPerson.getId());
			logger.info("person = {}", person.toString());
		} catch(ServiceRuntime e){
			e.printServerStackTrace();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	@Test
	public void testGetDeviceIdOfFeature(){
		try{
			Integer deviceId = facelogClient.getDeviceIdOfFeature("hello");
			System.out.println(deviceId);
		}catch(RuntimeTApplicationException e){
			Throwable cause = e.getCause();
			if (cause instanceof TApplicationException  && ((TApplicationException) cause).getType() ==   TApplicationException.MISSING_RESULT){
				System.out.println("return null");
			}
		}catch(ServiceRuntime e){
			e.printServerStackTrace();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
