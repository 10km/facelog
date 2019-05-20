package net.gdface.service.facelog;

import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Slf4JLoggerFactory;

import com.google.common.util.concurrent.Service;

import net.gdface.facelog.GlobalConfig;
import net.gdface.facelog.hb.ServiceHeartbeat;

import static net.gdface.facelog.CommonConstant.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * 启动 IFacelog 服务
 * @author guyadong
 *
 */
public class FacelogServiceMain {	
	private static final FacelogServiceConfig serviceConfig = FacelogServiceConfig.getInstance();

	public FacelogServiceMain() {
	}
	private static void waitquit(){
		System.out.println("PRESS 'quit' OR 'CTRL-C' to exit");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		try{
			while(!"quit".equalsIgnoreCase(reader.readLine())){				
			}
			System.exit(0);
		} catch (IOException e) {

		}finally {

		}
	}
	private static Integer portOf(FaceLogService instance){
		return null == instance ? null : instance.getThriftServerConfig().getPort();
	}
	public static void main(String[] args) {
		Logo.textLogo();
		serviceConfig.parseCommandLine(args);
		SyslogConfig.log4jConfig();
		// 设置slf4j记录日志,否则会有警告
		InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
		@SuppressWarnings("unused")
		Service service = FaceLogService.buildService().startAsync();
		if(GlobalConfig.getConfig().getBoolean(XHR_START, true)){
			@SuppressWarnings("unused")
			Service httpService = FaceLogService.buildHttpService().startAsync();
		}
		if(GlobalConfig.getConfig().getBoolean(RESTFUL_START, true)){
			FaceLogService.startRestfulService();
		}
		// 启动服务心跳
		ServiceHeartbeat.makeHeartbeat((int)System.currentTimeMillis(), 
				portOf(FaceLogService.getService()), 
				portOf(FaceLogService.getHttpService()))
			.setInterval(GlobalConfig.getConfig().getInt(SERVER_HBINTERVAL, DEFAULT_HEARTBEAT_PERIOD), TimeUnit.SECONDS)
			.start();
		waitquit();
	}	
}
