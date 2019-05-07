package net.gdface.service.facelog;

import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Slf4JLoggerFactory;

import com.google.common.util.concurrent.Service;

import gu.simplemq.Channel;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.GlobalConfig;
import net.gdface.facelog.ServiceEvent;

import static net.gdface.facelog.CommonConstant.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
	public static void main(String[] args) {
		Logo.textLogo();
		serviceConfig.parseCommandLine(args);
		SyslogConfig.log4jConfig();
		// 设置slf4j记录日志,否则会有警告
		InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
		@SuppressWarnings("unused")
		Service service = FaceLogService.buildService().startAsync();
		RedisPublisher publisher = RedisFactory.getPublisher();
		Channel<ServiceEvent> channel = new Channel<>(FACELOG_EVT_CHANNEL, ServiceEvent.class);
		if(GlobalConfig.getConfig().getBoolean(XHR_START, true)){
			@SuppressWarnings("unused")
			Service httpService = FaceLogService.buildHttpService().startAsync();
		}
		publisher.publish(channel, ServiceEvent.ONLINE);
		waitquit();
	}	
}
