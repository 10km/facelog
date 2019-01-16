package net.gdface.service.facelog;

import java.util.concurrent.TimeUnit;

import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Slf4JLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.facebook.swift.service.ThriftServerConfig;
import com.facebook.swift.service.ThriftServerService;

import io.airlift.units.Duration;
import net.gdface.facelog.IFaceLog;
import net.gdface.facelog.decorator.IFaceLogThriftDecorator;


/**
 * 启动 FaceDb thrift 服务
 * @author guyadong
 *
 */
public class FacelogServiceMain implements FacelogServiceConstants{
	private static final Logger logger = LoggerFactory.getLogger(FacelogServiceMain.class);

	private static final FacelogServiceConfig serviceConfig = FacelogServiceConfig.getInstance();

	public FacelogServiceMain() {
	}
	public static void main(String ...args){
		serviceConfig.parseCommandLine(args);
		// 设置slf4j记录日志,否则会有警告
		InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
		ThriftServerConfig config = new ThriftServerConfig()
				.setPort(serviceConfig.getServicePort())
				.setWorkerThreads(serviceConfig.getWorkThreads())
				.setConnectionLimit(serviceConfig.getConnectionLimit())
				.setIdleConnectionTimeout(new Duration(serviceConfig.getIdleConnectionTimeout(),TimeUnit.SECONDS));
		IFaceLog facelog = serviceConfig.getFacelog();
		ThriftServerService service = ThriftServerService.bulider()
				.withServices(new IFaceLogThriftDecorator(facelog))
				.setThriftServerConfig(config)
				.build();
		logger.info("FaceLog instance:{}",facelog);
		service.startAsync();
	}
	
}
