package net.gdface.facelog;

import android.support.test.runner.AndroidJUnit4;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import net.gdface.facelog.client.ClientFactory;
import net.gdface.facelog.client.CommonConstant;
import net.gdface.facelog.client.IFaceLogClient;
import net.gdface.facelog.client.PersonBean;
import net.gdface.facelog.client.ServiceRuntimeException;
import net.gdface.facelog.client.thrift.ServiceSecurityException;
import net.gdface.facelog.client.thrift.Token;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author guyadong
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ClientTest implements CommonConstant {

	private static IFaceLogClient facelogClient;
	private static Token rootToken;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		facelogClient = ClientFactory.builder().setHostAndPort("10.0.2.2", DEFAULT_PORT).build();
		rootToken = facelogClient.applyRootToken("guyadong", false);
	}

	@AfterClass
	public static void tearDownAfterClass() {
		if(null  != rootToken){
            try {
                facelogClient.releaseRootToken(rootToken);
            } catch (ServiceSecurityException e) {
                throw new RuntimeException(e);
            }
        }
	}

	@Test
	public void test1SavePerson() {
		try {
            PersonBean newPerson = PersonBean.builder().name("guyadong").build();
            newPerson = facelogClient.savePerson(newPerson,rootToken);
			logger.info("person = {}", newPerson.toString());
			PersonBean person = facelogClient.getPerson(newPerson.getId());
			logger.info("person = {}", person.toString());
		} catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
			fail();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			fail();
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
			fail();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			fail();
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
			fail();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			fail();
		}
	}
/*	@Test
	public void test5AddImage(){
		try{
			URL url = this.getClass().getResource("/images/guyadong-3.jpg");
            byte[] imgBytes = FaceUtilits.getBytesNotEmpty(url);
			String md5 = Hashing.md5().hashBytes(imgBytes).toString();
			facelogClient.deleteImage(md5, rootToken);
			ImageBean imageBean = facelogClient.addImage(FaceUtilits.getByteBufferNotEmpty(url), null, null, 0, rootToken);
			logger.info("image added {}",imageBean.toString(true, false));
		}catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
			assertTrue(false);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}*/

	@Test
	public void test5DeleteAll(){
		try{
			List<Integer> persons = facelogClient.loadAllPerson();
			facelogClient.deletePersons(persons, rootToken);
			int count = facelogClient.countPersonByWhere(null);
			assertEquals(0 , count);
		}catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
			fail();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			fail();
		}
	}
	@Test
	public void test6Version(){
	    try {
            logger.info("service version:{}", facelogClient.version());
            logger.info("version detail:{}", facelogClient.versionInfo().toString());
            assertTrue("version ok", true);
        } catch (Exception e) {
            fail();
        }
	}
	@Test
	public void testMultiThread() {
        try {
            ExecutorService executor = new ThreadPoolExecutor(
                    1,
                    1,
                    0,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(),
                    new ThreadFactoryBuilder()
                            .setNameFormat("cached-pool-%d")
                            .build());
            PersonBean newPerson = PersonBean.builder().name("guyadong").build();
            try {
                newPerson = facelogClient.savePerson(newPerson, rootToken);
                logger.info("person = {}", newPerson.toString());
            } catch (ServiceRuntimeException e) {
                e.printServiceStackTrace();
                fail();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                fail();
            }
            final Integer id = newPerson.getId();
            for (int i = 0; i < 200; ++i) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            PersonBean person = facelogClient.getPerson(id);
                            //logger.info("person = {}", person.toString());
                            System.out.printf("person = %s\n",person.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                executor.awaitTermination(10, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

	}
}
