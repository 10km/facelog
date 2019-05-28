package net.gdface.facelog.client;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.hash.Hashing;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import static org.junit.Assert.*;

import net.gdface.facelog.CommonConstant;
import net.gdface.facelog.Token;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.thrift.IFaceLogThriftClient;
import net.gdface.thrift.ClientFactory;
import net.gdface.thrift.exception.ServiceRuntimeException;
import net.gdface.utils.FaceUtilits;
import net.gdface.utils.NetworkUtil;

/**
 * @author guyadong
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientTest implements CommonConstant {
    public static final Logger logger = LoggerFactory.getLogger(ClientTest.class);

	private static IFaceLogClient facelogClient;
	private static Token rootToken;

	private static DeviceBean device;

	private static Token deviceToken;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// docker test
//		facelogClient = ClientFactory.builder().setHostAndPort("192.168.99.100", DEFAULT_PORT).build();
//		rootToken = facelogClient.applyRootToken("root", false);
		facelogClient = ClientFactory.builder()
				.setHostAndPort("127.0.0.1", DEFAULT_PORT)
				.setDecorator(RefreshTokenDecorator.makeDecoratorFunction(new TokenHelperTestImpl()))
				.build(IFaceLogThriftClient.class, IFaceLogClient.class);
		rootToken = facelogClient.applyRootToken("guyadong", false);
		byte[] address = new byte[]{0x20,0x20,0x20,0x20,0x20,0x20};
		device = DeviceBean.builder().mac(NetworkUtil.formatMac(address, null)).serialNo("12322333").version("MTFSDKARM512").build();
		logger.info(device.toString(true,false));
		// 注册设备 
		device = facelogClient.registerDevice(device);
		logger.info("registered device {}",device.toString(true, false));
		// 申请设备令牌
		deviceToken = facelogClient.online(device);
		logger.info("device token = {}",deviceToken);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		facelogClient.unregisterDevice(device.getId(), deviceToken);
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
	public void test2SavePersonWithImage() {
		PersonBean newPerson = PersonBean.builder().name("person1").build();
		try {			
			byte[] imgdata = FaceUtilits.getBytes(ClientTest.class.getResourceAsStream("/images/guyadong-3.jpg"));
			newPerson = facelogClient.savePerson(newPerson,imgdata,rootToken);
			logger.info("person = {}", newPerson.toString());
			PersonBean person = facelogClient.getPerson(newPerson.getId());
			logger.info("person = {}", person.toString());
			facelogClient.deletePerson(person.getId(), rootToken);
		} catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
			assertTrue(false);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	@Test
	public void test3SavePersonWithImageAndFeature() {
		
		try {			
			PersonBean newPerson = PersonBean.builder().name("person2").build();
			byte[] imgdata = FaceUtilits.getBytes(ClientTest.class.getResourceAsStream("/images/guyadong-3.jpg"));
//			FaceBean facebean = FaceBean.builder().faceLeft(0).faceTop(100).faceWidth(200).faceHeight(200)
//					.imageMd5(FaceUtilits.getMD5String(imgdata))
//					.build();
			byte[] feature = new byte[]{1,1,3,1,1,};
			newPerson = facelogClient.savePerson(newPerson,imgdata,feature,null,(List<FaceBean>)null, deviceToken);
			logger.info("person = {}", newPerson.toString());
			PersonBean person = facelogClient.getPerson(newPerson.getId());
			logger.info("person = {}", person.toString());
			FeatureBean featureBean = facelogClient.getFeature(FaceUtilits.getMD5String(feature));
			logger.info("{}",featureBean);
			{
				// 添加一个新的特征
				byte[] feature2 = new byte[]{1,1,3,1,3,44};
				facelogClient.savePerson(person, null, feature2, null,(List<FaceBean>)null, deviceToken);
			}
			facelogClient.deletePerson(person.getId(), rootToken);
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
			URL url = this.getClass().getResource("/images/guyadong-3.jpg");
			byte[] imgBytes = FaceUtilits.getBytesNotEmpty(url);
			String md5 = Hashing.md5().hashBytes(imgBytes).toString();
			facelogClient.deleteImage(md5, rootToken);
			ImageBean imageBean = facelogClient.addImage(FaceUtilits.getBytesNotEmpty(url), null, null, 0, rootToken);
			logger.info("image added {}",imageBean.toString(true, false));
		}catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
			assertTrue(false);
		}catch (Exception e) {
			e.printStackTrace();
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
	@Test
	public void test6Version(){
		logger.info("service version:{}",facelogClient.version());
		logger.info("version detail:{}",facelogClient.versionInfo().toString());
		assertTrue("version ok",true);
	}
	@Test
	public void testMultiThread() throws InterruptedException{
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
			newPerson = facelogClient.savePerson(newPerson,rootToken);
			logger.info("person = {}", newPerson.toString());
		} catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
			assertTrue(false);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
		final Integer id = newPerson.getId();
		for(int i=0;i<200;++i){
			executor.execute(new Runnable(){
				@Override
				public void run() {
					try {
						PersonBean person = facelogClient.getPerson(id);
						logger.info("person = {}", person.toString());
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}});
		}
		executor.shutdown();
		while(!executor.isTerminated()){
			executor.awaitTermination(10, TimeUnit.SECONDS);
		}
	}
	public static class TokenHelperTestImpl extends TokenHelper {

		public TokenHelperTestImpl() {
		}

		@Override
		public String passwordOf(int id) {
			return "guyadong";
		}

		@Override
		public boolean isHashedPwd() {
			return false;
		}

	}

}
