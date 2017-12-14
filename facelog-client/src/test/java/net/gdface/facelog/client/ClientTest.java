package net.gdface.facelog.client;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author guyadong
 *
 */
public class ClientTest implements CommonConstant {

	private static IFaceLogClient facelogClient;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		facelogClient = ClientFactory.builder().setHostAndPort("127.0.0.1", DEFAULT_PORT).build();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Test
	public void test1SavePerson() {
		PersonBean newPerson = PersonBean.builder().name("guyadong").build();
		try {
			newPerson = facelogClient.savePerson(newPerson,null);
			logger.info("person = {}", newPerson.toString());
			PersonBean person = facelogClient.getPerson(newPerson.getId());
			logger.info("person = {}", person.toString());
		} catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	@Test
	public void test2List(){
		try{
			List<Integer> persons = facelogClient.loadAllPerson();
			for(Integer id:persons){
				System.out.println(facelogClient.getPerson(id).toString(true, false));
			}
		}catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	@Test
	public void test3GetDeviceIdOfFeature(){
		try{
			Integer deviceId = facelogClient.getDeviceIdOfFeature(null);
			System.out.println(deviceId);
			List<Integer> devices = facelogClient.loadDeviceIdByWhere(null);
			for(Integer id:devices){
				System.out.println(id);
			}
		}catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
