package net.gdface.service.facelog;

import static org.junit.Assert.*;

import org.junit.Test;

import net.gdface.facelog.ServiceConstant;
import net.gdface.service.facelog.Logo;
import net.gdface.service.facelog.SyslogConfig;

public class LogoTest implements ServiceConstant {

	@Test
	public void test1Logo() {
		Logo.textLogo();
	}
	@Test
	public void test2LogConfig(){
		try{
		SyslogConfig.log4jConfig();
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
}
