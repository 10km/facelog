package net.gdface.facelog.client;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import com.google.common.hash.Hashing;

import net.gdface.facelog.client.thrift.ServiceSecurityException;
import net.gdface.facelog.client.thrift.Token;

/**
 * 令牌相关测试
 * @author guyadong
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TokenTest implements CommonConstant {

	private static IFaceLogClient facelogClient;
	private static Token rootToken;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		facelogClient = ClientFactory.builder().setHostAndPort("127.0.0.1", DEFAULT_PORT).build();
		rootToken = facelogClient.applyRootToken("guyadong", false);

	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}
	@Test
	public void test1CheckPersonName(){
		try{
			PersonBean person = PersonBean.builder()
					.name("root").build();
			facelogClient.savePerson(person, rootToken);
			assertTrue("root用户名保留机制无效",false);
		}catch(ServiceRuntimeException e){
			ExceptionType type = ExceptionType.values()[e.getType()];
			assertTrue("异常类型分类错误",type == ExceptionType.UNCLASSIFIED);
		}
	}
	@Test
	public void test2CheckMd5Password(){
		try{
			// 创建普通用户
			String adminpwd = "password";
			PersonBean newPerson = PersonBean.builder()
					.name("黄小明")
					.password(adminpwd)
					.build();
			facelogClient.savePerson(newPerson, rootToken);
			assertTrue("存入密码明文应该抛出异常",false);
		}catch(ServiceRuntimeException e){
			logger.error(e.getMessage());
			ExceptionType type = ExceptionType.values()[e.getType()];
			assertTrue("异常类型分类错误",type == ExceptionType.UNCLASSIFIED);
		}
	}
	@Test
	public void test3PersonToken() {
		String adminpwd = "adminPassword";
		String md5OfPwd= Hashing.md5().hashBytes(adminpwd.getBytes()).toString();
		PersonBean newPerson = PersonBean.builder()
				.name("admin")
				.rank(PersonRank.admin.rank)
				.password(md5OfPwd)
				.remark("管理员1")
				.build();
		try {
			// 增加一个admin帐户
			newPerson = facelogClient.savePerson(newPerson,rootToken);
			logger.info("person = {}", newPerson.toString());
			PersonBean reRead = facelogClient.getPerson(newPerson.getId());
			assertTrue("数据写入一致性错误",reRead.equals(newPerson));
			assertTrue("时间戳字段没有更新",null != reRead.getCreateTime());
			assertTrue("存入密码没有加盐",!md5OfPwd.equals(reRead.getPassword()));
			// 申请admin令牌
			Token adminToken = facelogClient.applyPersonToken(newPerson.getId(),adminpwd, false);
			Token adminToken2 = facelogClient.applyPersonToken(newPerson.getId(),md5OfPwd, true);
			// 先申请的令牌自动失效
			assertTrue("两次申请令牌不应该一样",!adminToken.equals(adminToken2));
			try{
				facelogClient.releasePersonToken(adminToken);
				assertTrue("无效令牌应该抛出异常",false);
			}catch(ServiceSecurityException e){
				assertTrue(true);
			}
			facelogClient.releasePersonToken(adminToken2);
		} catch(ServiceRuntimeException e){
			e.printServiceStackTrace();
			assertTrue(e.getMessage(),false);
		}catch (ServiceSecurityException e) {
			logger.error(e.getMessage());
			assertTrue(e.getServiceStackTraceMessage(),false);
		}
	}

}
