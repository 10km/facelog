package net.gdface.facelog.service;

import java.util.List;

import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftServerConfig;
import com.facebook.swift.service.ThriftServerService;
import com.google.common.util.concurrent.MoreExecutors;

import static com.google.common.base.Preconditions.*;

/**
 * {@link FaceLogImpl}服务封装
 * @author guyadong
 *
 */
public class FaceLogService extends ThriftServerService {
	private static FaceLogService service;

	/**
	 * 创建服务实例
	 * @return
	 */
	public static synchronized final FaceLogService buildService(){
		if(null == service || State.TERMINATED == service.state() || State.FAILED == service.state()){
			 service = ThriftServerService.bulider()
						.withServices(new FaceLogImpl())
						.setEventHandlers(TlsHandler.INSTANCE)
						.setThriftServerConfig(GlobalConfig.makeThriftServerConfig())
						.build(FaceLogService.class);	
			 service.addListener(new Listener(){
					@Override
					public void starting() {
						GlobalConfig.logThriftServerConfig(service.thriftServerConfig);
					}			
				}, MoreExecutors.directExecutor());
		}
		checkState(State.NEW == service.state(),"INVALID service state %s ",service.toString());
		return service;
	}
	
	protected FaceLogService(List<?> services, 
			List<ThriftEventHandler> eventHandlers,
			ThriftServerConfig thriftServerConfig) {
		super(services, eventHandlers, thriftServerConfig);
	}
}
