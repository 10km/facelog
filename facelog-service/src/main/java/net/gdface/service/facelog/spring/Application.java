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

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Preconditions.*;

/**
 * @author guyadong
 */
@SpringBootApplication
@ComponentScan({"net.gdface.facelog","net.gdface.facelog.db"})
//@ComponentScan(basePackageClasses={IFaceLogSpringController.class})
//@Import({SwaggerConfig.class})
@EnableSwagger2
public class Application {
	private static int httpPort = 8080;
	private static TomcatConnectorCustomizer customizer = new ConnectorCustomizer();
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);

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
	 * @return httpPort
	 */
	public static int getHttpPort() {
		return httpPort;
	}
	/**
	 * @param httpPort 要设置的 httpPort
	 */
	public static void setHttpPort(int httpPort) {
		Application.httpPort = httpPort;
	}
	/**
	 * @return customizer
	 */
	public static TomcatConnectorCustomizer getCustomizer() {
		return customizer;
	}
	/**
	 * @param customizer 要设置的 customizer
	 */
	public static void setCustomizer(TomcatConnectorCustomizer customizer) {
		Application.customizer = checkNotNull(customizer,"customizer is null");
	}

}