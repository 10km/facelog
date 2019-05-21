package net.gdface.service.facelog.spring;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import net.gdface.facelog.IFaceLog;
import net.gdface.facelog.IFaceLogSpringController;
import net.gdface.facelog.IFaceLogSpringController.InstanceSupplier;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Preconditions.*;

/**
 * 将facelog接口封装为RESTful接口spring web应用
 * @author guyadong
 */
@SpringBootApplication
@ComponentScan({"net.gdface.facelog"})
//@ComponentScan(basePackageClasses={IFaceLogSpringController.class})
@Import({SwaggerConfig.class})
@EnableSwagger2
public class RestfulService {
	public static final int DEFAULT_HTTP_PORT = 8080;
	private static int httpPort = DEFAULT_HTTP_PORT;
	private static TomcatConnectorCustomizer customizer = new ConnectorCustomizer();
	/** test only  */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(RestfulService.class, args);
	}
	public static void run(){
		SpringApplication.run(RestfulService.class, new String[]{});
	}
	@Bean
	public EmbeddedServletContainerFactory getTomcatEmbeddedServletContainerFactory(){
		TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();  
		tomcatFactory.addConnectorCustomizers(customizer);  
		return tomcatFactory; 

	}
	private static class ConnectorCustomizer implements TomcatConnectorCustomizer  
	{  
		public void customize(Connector connector)  
		{  
			Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();  
			protocol.setPort(httpPort);
			//设置最大连接数  
			protocol.setMaxConnections(2000);  
			//设置最大线程数  
			protocol.setMaxThreads(2000);  
			protocol.setConnectionTimeout(30000);  
		}  
	}
	/**
	 * 返回HTTP端口
	 * @return httpPort
	 */
	public static int getHttpPort() {
		return httpPort;
	}
	/**
	 * 设置HTTP端口,默认{@value #DEFAULT_HTTP_PORT}
	 * @param httpPort 要设置的 httpPort
	 */
	public static void setHttpPort(int httpPort) {
		RestfulService.httpPort = httpPort;
	}
	/**
	 * 返回tomcat参数定义接口实例
	 * @return customizer
	 */
	public static TomcatConnectorCustomizer getCustomizer() {
		return customizer;
	}
	/**
	 * 设置tomcat参数定义接口实例
	 * @param customizer 要设置的 customizer，不可为{@code null}
	 */
	public static void setCustomizer(TomcatConnectorCustomizer customizer) {
		RestfulService.customizer = checkNotNull(customizer,"customizer is null");
	}

	/**
	 * 设置{@link IFaceLog}实例
	 * @param facelogInstance {@link IFaceLog}实例，不可为{@code null}
	 */
	public static void setFacelogInstance(final IFaceLog facelogInstance){
		checkArgument(facelogInstance != null ,"facelogInstance is null");
		IFaceLogSpringController.setInstanceSupplier(new InstanceSupplier(){
			@Override
			public IFaceLog instanceOfIFaceLog() {
				return facelogInstance;
			}});
	}
}