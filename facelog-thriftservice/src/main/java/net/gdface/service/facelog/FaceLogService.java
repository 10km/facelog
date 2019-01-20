package net.gdface.service.facelog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.CombinedConfiguration;

import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftServerConfig;
import com.facebook.swift.service.ThriftServerService;
import com.google.common.util.concurrent.MoreExecutors;

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
	 * 创建服务实例
	 * @return
	 */
	public static synchronized final FaceLogService buildService(){
		if(null == service || State.TERMINATED == service.state() || State.FAILED == service.state()){
			 service = ThriftServerService.bulider()
						.withServices(new IFaceLogThriftDecorator(new FaceLogImpl()))
						.setEventHandlers(TlsHandler.INSTANCE)
						.setThriftServerConfig(makeThriftServerConfig())
						.build(FaceLogService.class);	
			 service.addListener(new Listener(){
					@Override
					public void starting() {
						logThriftServerConfig(service.thriftServerConfig);
					}			
				}, MoreExecutors.directExecutor());
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
