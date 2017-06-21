package com.liferyan.spittr.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by Ryan on 2017/6/19.
 * SpringMVC 配置
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = HomeController.class)
public class WebConfig extends WebMvcConfigurerAdapter {

  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/spittr/");
    resolver.setSuffix(".jsp");
    resolver.setExposeContextBeansAsAttributes(true);
    return resolver;
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setDefaultEncoding("utf-8");
    messageSource.setBasename("classpath:message");
    return messageSource;
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

}
