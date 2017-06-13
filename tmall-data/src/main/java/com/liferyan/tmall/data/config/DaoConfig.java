package com.liferyan.tmall.data.config;

import com.liferyan.tmall.data.dao.BaseDao;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by Ryan on 2017/6/13.
 * tmall-data Spring配置
 */
@Configuration
@ComponentScan(basePackageClasses = BaseDao.class)
@PropertySource(value = {"classpath:jdbc.properties"}, ignoreResourceNotFound = true)
public class DaoConfig {

  private final Environment environment;

  @Autowired
  public DaoConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(environment.getProperty("db_driver", "com.mysql.jdbc.Driver"));
    dataSource.setUrl(environment.getProperty("db_url",
        "jdbc:mysql://127.0.0.1:3306/mall?characterEncoding=UTF-8&useSSL=false"));
    dataSource.setUsername(environment.getProperty("db_username", "root"));
    dataSource.setPassword(environment.getProperty("db_password", "admin"));
    return dataSource;
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(dataSource);
    sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
    return sqlSessionFactory.getObject();
  }

}
