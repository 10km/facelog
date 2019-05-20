package net.gdface.service.facelog.spring;

import java.util.List;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@ComponentScan(basePackages = { "net.gdface.facelog" })
public class SwaggerConfig /*extends WebMvcConfigurationSupport*/{                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
          .select()
          .apis(RequestHandlerSelectors.any())
//          .apis(RequestHandlerSelectors.basePackage("net.gdface.facelog"))
          .paths(PathSelectors.any())
          .build();                                           
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("**平台对外接口")
                .description("1.提供**后台使用的接口 2.提供对其他服务调用的服务")
//                .contact(new Contact("xtj332", "https://blog.csdn.net/xtj332", "xtj332111@163.com"))
                .version("1.0")
                .build();
    }

//	@Override
//	public Validator mvcValidator() {
//		return new NoOpValidator();
//	}
//	@Override
//	protected Validator getValidator() {
//		ReloadableResourceBundleMessageSource messageSource =new ReloadableResourceBundleMessageSource();
//		messageSource.setBasename("classpath:validatemessages");
//		messageSource.setDefaultEncoding("utf-8");
//
//		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
//		localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
//		localValidatorFactoryBean.setValidationMessageSource(messageSource);
//		return localValidatorFactoryBean;
//	}
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        registry.addResourceHandler("swagger-ui.html")
//            .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//            .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//    
//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new MappingJackson2HttpMessageConverter());
//    }
//    private static final class NoOpValidator implements Validator {
//
//		@Override
//		public boolean supports(Class<?> clazz) {
//			return false;
//		}
//
//		@Override
//		public void validate(Object target, Errors errors) {
//		}
//
//	}
}
