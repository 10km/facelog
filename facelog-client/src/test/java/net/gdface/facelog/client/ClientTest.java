package net.gdface.facelog.client;

import java.io.File;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.common.hash.Hashing;

import static org.junit.Assert.*;

import net.gdface.facelog.client.thrift.Token;
import net.gdface.utils.FaceUtilits;

/**
 * @author guyadong
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientTest implements CommonConstant {

	private static IFaceLogClient facelogClient;
	private static Token rootToken;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		facelogClient = ClientFactory.builder().setHostAndPort("127.0.0.1", DEFAULT_PORT).build();
		rootToken = facelogClient.applyRootToken("guyadong", false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		facelogClient.releaseRootToken(rootToken);
	}

	@Test
	public void test1SavePerson() {
		PersonBean newPerson = PersonBean.builder().name("guyadong").build();
		try {
			newPerson = facelogClient.savePerson(newPerson,rootToken);
			logger.info("person = {}", newPerson.toString());
			PersonBean person = facelogClient.getPerson(newPerson.getId());
			logger.info("person = {}", person.toString());
		} catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
			assertTrue(false);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
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
			assertTrue(false);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);

		}
	}
	@Test
	public void test3GetDeviceIdOfFeature(){
		try{
			Integer deviceId = facelogClient.getDeviceIdOfFeature(null);
			logger.info("{}",deviceId);
			List<Integer> devices = facelogClient.loadDeviceIdByWhere(null);
			for(Integer id:devices){
				logger.info(id.toString());
			}
		}catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
			assertTrue(false);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	@Test
	public void test5AddImage(){
		try{
			byte[] imgBytes = FaceUtilits.getBytesNotEmpty(new File("d:\\tmp\\guyadong-12.jpg"));
			String md5 = Hashing.md5().hashBytes(imgBytes).toString();
			facelogClient.deleteImage(md5, rootToken);
			facelogClient.addImage(FaceUtilits.getBytesNotEmpty(new File("d:\\tmp\\guyadong-12.jpg")), null, null, 0, rootToken);		
		}catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
			assertTrue(false);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void test5DeleteAll(){
		try{
			List<Integer> persons = facelogClient.loadAllPerson();			
			facelogClient.deletePersons(persons, rootToken);
			int count = facelogClient.countPersonByWhere(null);
			assertTrue(0 == count);
		}catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
			assertTrue(false);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
}
