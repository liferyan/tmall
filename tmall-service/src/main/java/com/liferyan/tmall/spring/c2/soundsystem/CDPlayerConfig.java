package com.liferyan.tmall.spring.c2.soundsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ryan on 2017/6/12.
 */
@Configuration
public class CDPlayerConfig {

  @Bean
  public CompactDisc sgtPeppers(){
    return new SgtPeppers();
  }

}
