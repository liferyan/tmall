package com.liferyan.mybatis.dao.impl;

import com.liferyan.mybatis.dao.ProductDao;
import com.liferyan.mybatis.entity.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ryan on 2017/5/26.
 */
public class ProductDaoImpl implements ProductDao {

  private static final Logger logger = LoggerFactory.getLogger("ProductDaoImpl");

  private SqlSessionFactory sqlSessionFactory;

  private static ProductDaoImpl INSTANCE;

  private ProductDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public static ProductDaoImpl getInstance(SqlSessionFactory sqlSessionFactory) {
    if (INSTANCE == null) {
      INSTANCE = new ProductDaoImpl(sqlSessionFactory);
    }
    return INSTANCE;
  }

  @Override
  public void saveProduct(Product product) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "saveProduct";
      session.insert(statement, product);
    } catch (Exception e) {
      logger.error("保存产品异常：{}", e.getMessage());
    }
  }

  @Override
  public void deleteProduct(int id) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "deleteProduct";
      session.insert(statement, id);
    } catch (Exception e) {
      logger.error("删除产品异常：{}", e.getMessage());
    }
  }

  @Override
  public void updateProduct(Product product) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "updateProduct";
      session.insert(statement, product);
    } catch (Exception e) {
      logger.error("更新产品异常：{}", e.getMessage());
    }
  }

  @Override
  public Product getProductById(int id) {
    Product product = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getProductById";
      product = session.selectOne(statement, id);
    } catch (Exception e) {
      logger.error("获取产品异常：{}", e.getMessage());
    }
    return product;
  }

  @Override
  public List<Product> listProductByPage(int cid, int start, int count) {
    List<Product> productList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listProductByPage";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("cid", cid);
      parameterMap.put("start", start);
      parameterMap.put("count", count);
      productList = session.selectList(statement, parameterMap);
    } catch (Exception e) {
      logger.error("获取产品异常：{}", e.getMessage());
    }
    return productList;
  }

  @Override
  public List<Product> listProductByCategory(int cid) {
    List<Product> productList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listProductByCategory";
      productList = session.selectList(statement, cid);
    } catch (Exception e) {
      logger.error("获取产品异常：{}", e.getMessage());
    }
    return productList;
  }

  @Override
  public int getProductCountByCategory(int cid) {
    int count = 0;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getProductCountByCategory";
      count = session.selectOne(statement, cid);
    } catch (Exception e) {
      logger.error("获取产品数异常：{}", e.getMessage());
    }
    return count;
  }

  @Override
  public List<Product> searchProduct(String keyword, int start, int count) {
    List<Product> productList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "searchProduct";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("keyword", keyword);
      parameterMap.put("start", start);
      parameterMap.put("count", count);
      productList = session.selectList(statement, parameterMap);
    } catch (Exception e) {
      logger.error("搜索产品异常：{}", e.getMessage());
    }
    return productList;
  }
}
