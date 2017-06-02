package com.liferyan.tmall.data;

import com.liferyan.tmall.data.dao.DaoFactory;
import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ryan on 2017/6/2.
 * MyBatis缓存测试类
 */
public class MyBatisCacheTest {

  private static final Logger logger = LoggerFactory.getLogger("MyBatisCacheTest");

  private SqlSession session;

  //单例
  private static SqlSessionFactory sqlSessionFactory;

  static {
    String resource = "mybatis-config.xml";
    try {
      Reader reader = Resources.getResourceAsReader(resource);
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    } catch (IOException e) {
      logger.error("读取Mybatis配置文件异常：", e);
    }
  }

  @Before
  public void setUp() throws Exception {
    session = sqlSessionFactory.openSession();
  }

  @After
  public void tearDown() throws Exception {
    session.close();
  }

  @Test
  /**
   * MyBatis一级缓存测试
   */
  public void sessionCache() throws Exception {
    long first = System.currentTimeMillis();
    session.selectList("listCategory");
    logger.info("一级缓存测试：");
    logger.info("第一次查询分类列表执行时间：{} ms", System.currentTimeMillis() - first);
    long second = System.currentTimeMillis();
    session.selectList("listCategory");
    logger.info("第二次查询分类列表执行时间：{} ms", System.currentTimeMillis() - second);
    long third = System.currentTimeMillis();
    session.selectList("listCategory");
    logger.info("第三次查询分类列表执行时间：{} ms", System.currentTimeMillis() - third);
  }

  @Test
  public void applicationCache() throws Exception {
    long first = System.currentTimeMillis();
    DaoFactory.getCategoryDao().listCategory();
    logger.info("二级缓存测试：");
    logger.info("第一次查询分类列表执行时间：{} ms", System.currentTimeMillis() - first);
    long second = System.currentTimeMillis();
    DaoFactory.getCategoryDao().listCategory();
    logger.info("第二次查询分类列表执行时间：{} ms", System.currentTimeMillis() - second);
    long third = System.currentTimeMillis();
    DaoFactory.getCategoryDao().listCategory();
    logger.info("第三次查询分类列表执行时间：{} ms", System.currentTimeMillis() - third);
  }
}
