package com.liferyan.tmall.data.dao;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ryan on 2017/6/2.
 * MyBatis缓存测试类
 */
public class MyBatisCacheTestBase extends BaseDaoTest {

  private static final Logger logger = LoggerFactory.getLogger("MyBatisCacheTestBase");

  @Autowired
  private CategoryDao categoryDao;

  @Test
  /**
   * MyBatis二级缓存测试
   */
  public void applicationCache() throws Exception {
    long first = System.currentTimeMillis();
    categoryDao.listCategory();
    logger.info("二级缓存测试：");
    logger.info("第一次查询分类列表执行时间：{} ms", System.currentTimeMillis() - first);
    long second = System.currentTimeMillis();
    categoryDao.listCategory();
    logger.info("第二次查询分类列表执行时间：{} ms", System.currentTimeMillis() - second);
    long third = System.currentTimeMillis();
    categoryDao.listCategory();
    logger.info("第三次查询分类列表执行时间：{} ms", System.currentTimeMillis() - third);
  }
}
