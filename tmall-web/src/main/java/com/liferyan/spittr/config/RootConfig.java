package com.liferyan.spittr.config;

import com.liferyan.spittr.data.SpitterRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Ryan on 2017/6/19.
 */
@Configuration
@Import(DataConfig.class)
@ComponentScan(basePackageClasses = SpitterRepository.class)
public class RootConfig {

}
