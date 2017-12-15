package net.gdface.facelog.service;

import static org.junit.Assert.*;

import org.junit.Test;

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
