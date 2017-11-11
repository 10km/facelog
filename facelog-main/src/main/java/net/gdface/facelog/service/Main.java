package net.gdface.facelog.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.gdface.facelog.FaceLogImpl;

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
		server.getServer().start();
		}finally{
//			pool.shutdown();
		}
	}	
}
