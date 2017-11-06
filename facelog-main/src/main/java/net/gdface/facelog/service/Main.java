package net.gdface.facelog.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.gdface.facelog.FaceLogImpl;

public class Main {
	static ExecutorService pool = Executors.newCachedThreadPool();
	public Main() {
	}

	public static void main(String[] args) {
		Server server = Server.bulider()
				.withServices(new FaceLogImpl())
				.setExecutor(pool).build();
		server.getServer().start();
	}

}
