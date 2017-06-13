package com.liferyan.tmall.data.config;

import com.liferyan.tmall.data.dao.BaseDao;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Ryan on 2017/6/13.
 * tmall-data Spring配置
 */
@Configuration
@ComponentScan(basePackageClasses = BaseDao.class)
@Import({DataSourceConfig.class, SessionFactoryConfig.class})
public class DaoConfig {

}
