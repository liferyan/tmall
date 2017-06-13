package com.liferyan.tmall.service.config;

import com.liferyan.tmall.data.config.DaoConfig;
import com.liferyan.tmall.service.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Ryan on 2017/6/13.
 */
@Configuration
@Import(DaoConfig.class)
@ComponentScan(basePackageClasses = UserService.class)
public class ServiceConfig {

}
