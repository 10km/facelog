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

/**
 * @author guyadong
 *
 */
@SpringBootApplication
@ComponentScan({"net.gdface.facelog"})
@EnableSwagger2
public class Application {
	private static final int DEFAULT_PORT = 4444;
//	private static final Server jettyServer = new Server(DEFAULT_PORT);
//	private static final Tomcat tomcat = new Tomcat();
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);

    }
//  @Bean
//  EmbeddedServletContainerFactory getJettyEmbeddedServletContainerFactory(){
//		return new EmbeddedServletContainerFactory(){
//
//			@Override
//			public EmbeddedServletContainer getEmbeddedServletContainer(ServletContextInitializer... initializers) {
//				return new JettyEmbeddedServletContainer(jettyServer);
//			}};
//  	
//  }
@Bean
EmbeddedServletContainerFactory getTomcatEmbeddedServletContainerFactory(){
	  TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();  
    tomcatFactory.setPort(DEFAULT_PORT);  
    tomcatFactory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());  
    return tomcatFactory; 
	
}
class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer  
{  
    public void customize(Connector connector)  
    {  
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();  
        //设置最大连接数  
        protocol.setMaxConnections(2000);  
        //设置最大线程数  
        protocol.setMaxThreads(2000);  
        protocol.setConnectionTimeout(30000);  
    }  
}
	  
}