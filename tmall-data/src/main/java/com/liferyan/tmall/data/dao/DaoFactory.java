package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.dao.impl.CategoryDaoImpl;
import com.liferyan.tmall.data.dao.impl.OrderDaoImpl;
import com.liferyan.tmall.data.dao.impl.OrderItemDaoImpl;
import com.liferyan.tmall.data.dao.impl.ProductDaoImpl;
import com.liferyan.tmall.data.dao.impl.ProductImageDaoImpl;
import com.liferyan.tmall.data.dao.impl.PropertyDaoImpl;
import com.liferyan.tmall.data.dao.impl.PropertyValueDaoImpl;
import com.liferyan.tmall.data.dao.impl.ReviewDaoImpl;
import com.liferyan.tmall.data.dao.impl.UserDaoImpl;
import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ryan on 2017/4/19.
 * Dao工厂
 */
public class DaoFactory {

  private static final Logger logger = LoggerFactory.getLogger("DaoFactory");

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

  private DaoFactory() {
  }

  public static UserDao getUserDao() {
    return UserDaoImpl.getInstance(sqlSessionFactory);
  }

  public static CategoryDao getCategoryDao() {
    return CategoryDaoImpl.getInstance(sqlSessionFactory);
  }

  public static PropertyDao getPropertyDao() {
    return PropertyDaoImpl.getInstance(sqlSessionFactory);
  }

  public static ProductDao getProductDao() {
    return ProductDaoImpl.getInstance(sqlSessionFactory);
  }

  public static PropertyValueDao getPropertyValueDao() {
    return PropertyValueDaoImpl.getInstance(sqlSessionFactory);
  }

  public static ProductImageDao getProductImageDao() {
    return ProductImageDaoImpl.getInstance(sqlSessionFactory);
  }

  public static ReviewDao getReviewDao() {
    return ReviewDaoImpl.getInstance(sqlSessionFactory);
  }

  public static OrderDao getOrderDao() {
    return OrderDaoImpl.getInstance(sqlSessionFactory);
  }

  public static OrderItemDao getOrderItemDao() {
    return OrderItemDaoImpl.getInstance(sqlSessionFactory);
  }
}
