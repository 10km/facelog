package net.gdface.facelog.service;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftServer;
import com.facebook.swift.service.ThriftServerConfig;
import com.facebook.swift.service.ThriftService;
import com.facebook.swift.service.ThriftServiceProcessor;
import com.facebook.swift.service.ThriftServiceProcessorCustom;
import com.facebook.swift.service.metadata.ThriftServiceMetadata;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * 创建thrift服务实例{@link ThriftServer}
 * @author guyadong
 *
 */
class Server implements ServiceConstant{
	public static class Builder {
		private List<?> services = ImmutableList.of();
		private ThriftServerConfig thriftServerConfig= new ThriftServerConfig();
		private List<ThriftEventHandler> eventHandlers = ImmutableList.of();
		private Builder() {
		}

		public Builder withServices(Object... services) {
			return withServices(ImmutableList.copyOf(services));
		}

		public Builder withServices(List<?> services) {
			this.services = checkNotNull(services);
			return this;
		}
		public Builder setEventHandlers(List<ThriftEventHandler> eventHandlers){
			this.eventHandlers = checkNotNull(eventHandlers);
			return this;
		}
		public Builder setEventHandlers(ThriftEventHandler...eventHandlers){
			return setEventHandlers(ImmutableList.copyOf(eventHandlers));
		}
		/**
		 * 设置服务端口
		 * @param servicePort
		 * @return
		 * @see {@link ThriftServerConfig#setPort(int)}
		 */
		public Builder setServerPort(int servicePort) {
			this.thriftServerConfig.setPort(servicePort);
			return this;
		}

		/**
		 * 设置服务器配置参数对象
		 * @param thriftServerConfig
		 * @return
		 */
		public Builder setThriftServerConfig(ThriftServerConfig thriftServerConfig) {
			this.thriftServerConfig = checkNotNull(thriftServerConfig,"thriftServerConfig is null");
			return this;
		}

		public Server build() {
			return new Server(services, eventHandlers, thriftServerConfig);
		}
	}

	public static final Builder bulider() {
		return new Builder();
	}

	private final ThriftServer server;
	private final ThriftServiceProcessor processor;
	private final ThriftServerConfig thriftServerConfig;

	/**
	 * 构造函数<br>
	 * @param services 服务对象列表
	 * @param eventHandlers 事件侦听器列表
	 * @param thriftServerConfig 服务配置对象
	 * @see ThriftServiceProcessor#ThriftServiceProcessor(ThriftCodecManager, List, List)
	 * @see ThriftServer#ThriftServer(com.facebook.nifty.processor.NiftyProcessor, ThriftServerConfig)
	 */
	public Server(final List<?> services, 
			List<ThriftEventHandler> eventHandlers, 
			ThriftServerConfig thriftServerConfig) {
		checkArgument(null != services && !services.isEmpty());
		this.thriftServerConfig = checkNotNull(thriftServerConfig,"thriftServerConfig is null");
		int port = this.thriftServerConfig.getPort();
		checkArgument(port > 0 && port < 65535,  "INVALID service port %d", port);

		this.processor = new ThriftServiceProcessorCustom(
				new ThriftCodecManager(), 
				checkNotNull(eventHandlers,"eventHandlers is null"),
				services);
		this.server =  new ThriftServer(processor,thriftServerConfig);
		// Arrange to stop the server at shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.info(" {} service shutdown(服务关闭) ",
						Joiner.on(",").join(Lists.transform(services, new Function<Object,String>(){
							@Override
							public String apply(Object input) {
								return getServiceName(input);
							}})));
				server.close();
			}
		});
	}
	/** 返回创建的服务实例{@link ThriftServer}	 */
	public ThriftServer getServer() {
		return server;
	}
	/** 返回初始化参数对象 */
	public ThriftServerConfig getThriftServerConfig() {
		return thriftServerConfig;
	}
	
	/** 
	 * 返回注释{@link ThriftService}定义的服务名称
	 * @see  {@link ThriftServiceMetadata#getThriftServiceAnnotation(Class)}
	 */
	public static final String getServiceName(Class<?> serviceClass){
		ThriftService thriftService = ThriftServiceMetadata.getThriftServiceAnnotation(
				checkNotNull(serviceClass,"serviceClass is null"));
		return Strings.isNullOrEmpty(thriftService.value())
				? serviceClass.getSimpleName()
				: thriftService.value();
	}
	/** @see #getServiceName(Class) */
	public static final String getServiceName(Object serviceInstance){
		return getServiceName(serviceInstance.getClass());
	}
}
