package net.gdface.service.facelog;

import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Slf4JLoggerFactory;

import com.google.common.util.concurrent.Service;

import net.gdface.facelog.GlobalConfig;
import static net.gdface.facelog.CommonConstant.*;

/**
 * 启动 IFacelog 服务
 * @author guyadong
 *
 */
public class FacelogServiceMain {	
	private static final FacelogServiceConfig serviceConfig = FacelogServiceConfig.getInstance();

	public FacelogServiceMain() {
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
	}	
}
