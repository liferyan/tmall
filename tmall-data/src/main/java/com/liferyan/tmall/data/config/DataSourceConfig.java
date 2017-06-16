package com.liferyan.tmall.data.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Created by Ryan on 2017/6/13.
 */
@Configuration
@PropertySource(value = {"classpath:jdbc.properties"}, ignoreResourceNotFound = true)
public class DataSourceConfig {

  private final Environment environment;

  @Autowired
  public DataSourceConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean
  @Profile({"test", "prod"})
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(environment.getRequiredProperty("db_driver"));
    dataSource.setUrl(environment.getRequiredProperty("db_url"));
    dataSource.setUsername(environment.getRequiredProperty("db_username"));
    dataSource.setPassword(environment.getRequiredProperty("db_password"));
    return dataSource;
  }

  @Bean
  @Profile("dev")
  public DataSource embeddedDataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScript("classpath:tmall.sql")
        .build();
  }

}
