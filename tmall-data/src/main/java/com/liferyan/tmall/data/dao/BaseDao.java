package com.liferyan.tmall.data.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ryan on 2017/6/9.
 */
public abstract class BaseDao {

  public static final Logger logger = LoggerFactory.getLogger("Dao");

  public SqlSessionFactory sqlSessionFactory;

  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

}
