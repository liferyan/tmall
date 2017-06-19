package com.liferyan.spittr.config;

import com.liferyan.spittr.data.SpittleRepository;
import com.liferyan.spittr.data.SpittleRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ryan on 2017/6/19.
 */
@Configuration
public class RootConfig {

  @Bean
  public SpittleRepository spittleRepository() {
    return new SpittleRepositoryImpl();
  }

}
