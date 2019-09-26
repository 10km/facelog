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
import net.gdface.facelog.IFaceLog;
import net.gdface.facelog.IFaceLogSpringController;
import net.gdface.facelog.Version;
import net.gdface.facelog.IFaceLogSpringController.InstanceSupplier;
import net.gdface.facelog.ImageContolller;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Preconditions.*;

/**
 * 将facelog接口封装为RESTful接口spring web应用
 * @author guyadong
 */
@SpringBootApplication
@ComponentScan({"net.gdface.facelog"})
@EnableSwagger2
public class RestfulService {
	public static final int DEFAULT_HTTP_PORT = 8080;
	/** web服务端口 */
	private static int httpPort = DEFAULT_HTTP_PORT;
	/** tomcat连接参数 */
	private static TomcatConnectorCustomizer customizer = new ConnectorCustomizer();
	/** 是否显示在线swagger文档 */
	private static boolean swaggerEnable = true;
	/** test only  */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(RestfulService.class, args);
	}
	/**
	 * 启动spring boot应用
	 */
	public static void run(){
		SpringApplication.run(RestfulService.class, new String[]{});
	}
    @Bean
    public Docket faceLogApi() { 
        return new Docket(DocumentationType.SWAGGER_2)
        	.enable(swaggerEnable)
        	.apiInfo(apiInfo())
        	.select()
        	.apis(RequestHandlerSelectors.basePackage("net.gdface.facelog"))
        	.paths(PathSelectors.any())
        	.build();    
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Facelog Document")
                .description(IFaceLogSpringController.DESCRIPTION)
                .contact(new Contact("10km", "https://gitee.com/l0km/facelog", "10km0811@sohu.com"))
                .version(Version.VERSION)
                .build();
    }
	@Bean
	public EmbeddedServletContainerFactory getTomcatEmbeddedServletContainerFactory(){
		TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();  
		tomcatFactory.addConnectorCustomizers(customizer);  
		return tomcatFactory; 

	}
	/**
	 * 默认的tomcat连接参数实例
	 * @author guyadong
	 *
	 */
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
		ImageContolller.setFacelogInstance(facelogInstance);
	}
	/**
	 * @return swaggerEnable
	 */
	public static boolean isSwaggerEnable() {
		return swaggerEnable;
	}
	/**
	 * 设置是否显示在线swagger文档
	 * @param swaggerEnable 
	 */
	public static void setSwaggerEnable(boolean swaggerEnable) {
		RestfulService.swaggerEnable = swaggerEnable;
	}
}