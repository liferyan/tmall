package com.liferyan.tmall.spring.c4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by Ryan on 2017/6/16.
 */
@Configuration
@EnableAspectJAutoProxy
//@ImportResource(locations = "classpath:aop.xml")
public class AOPConfig {

  @Bean
  public Audience audience() {
    return new Audience();
  }

  @Bean
  public Worker worker() {
    return new Worker(System.out);
  }

  @Bean
  public Performance performance() {
    return new Movie();
  }
}
