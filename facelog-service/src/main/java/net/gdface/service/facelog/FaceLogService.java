package net.gdface.service.facelog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.CombinedConfiguration;

import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftServerConfig;
import com.facebook.swift.service.ThriftServerService;
import io.airlift.units.Duration;
import net.gdface.facelog.CommonConstant;
import net.gdface.facelog.FaceLogImpl;
import net.gdface.facelog.GlobalConfig;
import net.gdface.facelog.decorator.IFaceLogThriftDecorator;

import static com.google.common.base.Preconditions.*;
/**
 * {@link FaceLogImpl}服务封装
 * @author guyadong
 *
 */
public class FaceLogService extends ThriftServerService implements CommonConstant {
	private static FaceLogService service;
	private static FaceLogService httpService;
	// 封装为thrift服务的facelog接口静态实例
	private static final IFaceLogThriftDecorator FACELOG = new IFaceLogThriftDecorator(new FaceLogImpl());
	
	/**
	 * 从配置文件中读取参数创建{@link ThriftServerConfig}实例
	 * @return
	 */
	public static ThriftServerConfig makeThriftServerConfig(){
		ThriftServerConfig thriftServerConfig = new ThriftServerConfig();
		CombinedConfiguration config = GlobalConfig.getConfig();
		int intValue ;
		thriftServerConfig.setPort(config.getInt(SERVER_PORT,DEFAULT_PORT));
		if((intValue  = config.getInt(SERVER_CONNECTION_LIMIT,0)) >0){
			thriftServerConfig.setConnectionLimit(intValue);
		}
		if((intValue = config.getInt(SERVER_IDLE_CONNECTION_TIMEMOUT,0))>0){
			Duration timeout = new Duration(intValue,TimeUnit.SECONDS);
			thriftServerConfig.setIdleConnectionTimeout(timeout);
		}
		if((intValue = config.getInt(SERVER_WORKER_THREAD_COUNT,0))>0){
			thriftServerConfig.setWorkerThreads(intValue);
		}
		return thriftServerConfig;
	}
	/**
	 * 创建服务实例(frame,binary)
	 * @return
	 */
	public static synchronized final FaceLogService buildService(){
		return service = buildService(service,makeThriftServerConfig());
	}
	/**
	 * 创建HTTP服务实例(http,json)
	 * @return
	 */
	public static synchronized final FaceLogService buildHttpService(){
		return httpService = buildService(httpService,
				makeThriftServerConfig()
					.setPort(DEFAULT_PORT_XHR)
					.setIdleConnectionTimeout(Duration.valueOf("1ms"))
					.setTransportName(ThriftServerService.HTTP_TRANSPORT)
					.setProtocolName(ThriftServerService.JSON_PROTOCOL));
	}

	/**
	 * 创建服务实例<br>
	 * @param service 服务实例,如果为{@code null}或服务已经停止则创建新的服务实例
	 * @param config thrift服务配置
	 * @return 返回{@code service}或创建的新的服务实例
	 */
	private static FaceLogService buildService(FaceLogService service,ThriftServerConfig config){
		if(null == service || State.TERMINATED == service.state() || State.FAILED == service.state()){		
			service = ThriftServerService.bulider()
						.withServices(FACELOG)	
						.setEventHandlers(TlsHandler.INSTANCE)
						.setThriftServerConfig(config)
						.build(FaceLogService.class);	
		}
		checkState(State.NEW == service.state(),"INVALID service state %s ",service.toString());
		return service;
	}
	public FaceLogService(List<?> services, 
			List<ThriftEventHandler> eventHandlers,
			ThriftServerConfig thriftServerConfig) {
		super(services, eventHandlers, thriftServerConfig);
	}
}
