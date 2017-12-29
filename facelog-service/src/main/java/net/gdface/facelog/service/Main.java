package net.gdface.facelog.service;

import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Slf4JLoggerFactory;

/**
 * 启动服务
 * @author guyadong
 *
 */
public class Main implements ServiceConstant {	
	public Main() {
	}

	public static void main(String[] args) {
		Logo.textLogo();
		SyslogConfig.log4jConfig();
		// 设置slf4j记录日志,否则会有警告
		InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
		FaceLogService.buildService().startAsync();
	}	
}
