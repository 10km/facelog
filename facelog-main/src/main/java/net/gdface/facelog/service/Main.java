package net.gdface.facelog.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Slf4JLoggerFactory;

/**
 * @author guyadong
 *
 */
public class Main {	
	public Main() {
	}

	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		try{
		Server server = Server.bulider()
				.withServices(new FaceLogImpl())
				.setExecutor(pool).build();
		// 设置slf4j记录日志,否则会有警告
		InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
		server.getServer().start();
		}finally{
		}
	}	
}
