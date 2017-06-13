package com.liferyan.tmall.data.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ryan on 2017/6/13.
 */
public class BaseDao {

  private SqlSession sqlSession;

  @Autowired
  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
      this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
  }

  public SqlSession getSqlSession() {
    return this.sqlSession;
  }

}
