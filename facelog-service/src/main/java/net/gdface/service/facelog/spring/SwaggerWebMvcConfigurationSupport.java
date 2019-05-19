package net.gdface.service.facelog.spring;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class SwaggerWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

	public SwaggerWebMvcConfigurationSupport() {
		super();
	}
	@Override
	protected Validator getValidator() {
		ReloadableResourceBundleMessageSource messageSource =new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:validatemessages");
		messageSource.setDefaultEncoding("utf-8");

		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
		localValidatorFactoryBean.setValidationMessageSource(messageSource);
		return localValidatorFactoryBean;
	}
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
