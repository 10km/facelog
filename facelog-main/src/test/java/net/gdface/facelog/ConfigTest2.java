package net.gdface.facelog;

import static org.junit.Assert.*;

import org.junit.Test;

import net.gdface.facelog.service.GlobalConfig;
import net.gdface.facelog.service.ServiceConstant;

/**
 * @author guyadong
 *
 */
public class ConfigTest2 implements ServiceConstant {

	@Test
	public void test() {
	    System.out.println(GlobalConfig.getConfig().getBoolean(ServiceConstant.TOKEN_DEVICE_VALIDATE));
	    System.out.println(GlobalConfig.getConfig().getInt(ServiceConstant.TOKEN_PERSON_EXPIRE));
	    System.out.println(GlobalConfig.getConfig().getString(ServiceConstant.TOKEN_PERSON_EXPIRE_DESC));
	    System.out.println(GlobalConfig.getConfig().getString(ServiceConstant.ROOT_PASSWORD));
	}

}
