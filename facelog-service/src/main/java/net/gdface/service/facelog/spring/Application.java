package net.gdface.service.facelog.spring;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11NioProtocol;
import org.eclipse.jetty.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

/**
 * @author guyadong
 *
 */
//@EnableAutoConfiguration
@Configuration
//@Import({Swagger2DocumentationConfiguration.class,SwaggerWebMvcConfigurerAdapter.class})
@Import({SwaggerConfig.class})
@ComponentScan(basePackages={"net.gdface.facelog"})
public class Application {
	private static final int DEFAULT_PORT = 8080;
//	private static final Server jettyServer = new Server(DEFAULT_PORT);
//	private static final Tomcat tomcat = new Tomcat();
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
//        SpringApplication.run(Swagger2DocumentationConfiguration.class, args);

    }
//    @Bean
//    EmbeddedServletContainerFactory getJettyEmbeddedServletContainerFactory(){
//		return new EmbeddedServletContainerFactory(){
//
//			@Override
//			public EmbeddedServletContainer getEmbeddedServletContainer(ServletContextInitializer... initializers) {
//				return new JettyEmbeddedServletContainer(jettyServer);
//			}};
//    	
//    }
  @Bean
  EmbeddedServletContainerFactory getTomcatEmbeddedServletContainerFactory(){
	  TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();  
      tomcatFactory.setPort(8080);  
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
//  @Bean
//  public WebMvcConfigurationSupport getWebMvcConfigurationSupport(){
//	  return new SwaggerWebMvcConfigurationSupport();
//  }
	  
}