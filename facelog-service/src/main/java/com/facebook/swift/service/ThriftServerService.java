package com.facebook.swift.service;

import java.lang.reflect.Constructor;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Throwables;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.*;

import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftServer;
import com.facebook.swift.service.ThriftServerConfig;
import com.facebook.swift.service.ThriftService;
import com.facebook.swift.service.ThriftServiceProcessor;
import com.facebook.swift.service.metadata.ThriftServiceMetadata;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AbstractIdleService;

/**
 * 创建thrift服务实例{@link ThriftServer},封装为{@link com.google.common.util.concurrent.Service}
 * @author guyadong
 *
 */
public class ThriftServerService extends AbstractIdleService{
    private static final Logger logger = LoggerFactory.getLogger(ThriftServerService.class);

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
		 * @see ThriftServerConfig#setPort(int)
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

		/**
		 * 根据参数构造 {@link ThriftServerService}实例
		 * @return
		 */
		public ThriftServerService build() {
			return new ThriftServerService(services, eventHandlers, thriftServerConfig);
		}
		/**
		 * 根据参数构造 {@link ThriftServerService}子类实例
		 * @param subServiceClass
		 * @return
		 */
		public <T extends ThriftServerService> T build(Class<T> subServiceClass) {
			try {
				Constructor<T> constructor= checkNotNull(subServiceClass,"subServiceClass is null")
						.getDeclaredConstructor(List.class,List.class,ThriftServerConfig.class);
				return constructor.newInstance(services,eventHandlers,thriftServerConfig);
			} catch (Exception e) {
				Throwables.throwIfUnchecked(e);
				throw new RuntimeException(e);
			}
		}
	}

	public static final Builder bulider() {
		return new Builder();
	}

	protected final ThriftServer thriftServer;
	protected final ThriftServiceProcessor processor;
	protected final ThriftServerConfig thriftServerConfig;
	protected final String serviceName;
	/**
	 * 构造函数<br>
	 * @param services 服务对象列表
	 * @param eventHandlers 事件侦听器列表
	 * @param thriftServerConfig 服务配置对象
	 * @see ThriftServiceProcessor#ThriftServiceProcessor(ThriftCodecManager, List, List)
	 * @see ThriftServer#ThriftServer(com.facebook.nifty.processor.NiftyProcessor, ThriftServerConfig)
	 */
	public ThriftServerService(final List<?> services, 
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
		this.thriftServer =  new ThriftServer(processor,thriftServerConfig);
		this.serviceName = Joiner.on(",").join(Lists.transform(services, new Function<Object,String>(){
			@Override
			public String apply(Object input) {
				return getServiceName(input);
			}}));
		// Arrange to stop the server at shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				shutDown();
			}
		});
	}
	
	/** 
	 * 返回注释{@link ThriftService}定义的服务名称
	 * @see  {@link ThriftServiceMetadata#getThriftServiceAnnotation(Class)}
	 */
	private static final String getServiceName(Class<?> serviceClass){
		ThriftService thriftService = ThriftServiceMetadata.getThriftServiceAnnotation(
				checkNotNull(serviceClass,"serviceClass is null"));
		return Strings.isNullOrEmpty(thriftService.value())
				? serviceClass.getSimpleName()
				: thriftService.value();
	}
	/** @see #getServiceName(Class) */
	private static final String getServiceName(Object serviceInstance){
		return getServiceName(serviceInstance.getClass());
	}
	@Override
	protected String serviceName() {
		return this.serviceName;
	}

	@Override
	protected final void startUp() throws Exception {
		thriftServer.start();
		logger.info("{} service is running(服务启动)",serviceName());
	}
	@Override
	protected final void shutDown() {
		logger.info(" {} service shutdown(服务关闭) ",	serviceName());
		thriftServer.close();
	}
}
