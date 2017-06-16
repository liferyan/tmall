package com.liferyan.tmall.data.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by Ryan on 2017/6/13.
 */
@Configuration
public class SessionFactoryConfig {

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(dataSource);
    sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
    return sqlSessionFactory.getObject();
  }

}
