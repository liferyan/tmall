package com.liferyan.mybatis.dao.impl;

import com.liferyan.mybatis.dao.ProductImageDao;
import com.liferyan.mybatis.entity.ImageTypeEnum;
import com.liferyan.mybatis.entity.Product;
import com.liferyan.mybatis.entity.ProductImage;
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
public class ProductImageDaoImpl implements ProductImageDao {

  private static final Logger logger = LoggerFactory.getLogger("ProductImageDaoImpl");

  private SqlSessionFactory sqlSessionFactory;

  private static ProductImageDaoImpl INSTANCE;

  private ProductImageDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public static ProductImageDaoImpl getInstance(SqlSessionFactory sqlSessionFactory) {
    if (INSTANCE == null) {
      INSTANCE = new ProductImageDaoImpl(sqlSessionFactory);
    }
    return INSTANCE;
  }

  @Override
  public void saveProductImage(ProductImage productImage) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "saveProductImage";
      session.insert(statement, productImage);
    } catch (Exception e) {
      logger.error("保存产品图片异常：{}", e.getMessage());
    }
  }

  @Override
  public void deleteProductImage(int id) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "deleteProductImage";
      session.delete(statement, id);
    } catch (Exception e) {
      logger.error("删除产品图片异常：{}", e.getMessage());
    }
  }

  @Override
  public ProductImage getProductImageById(int id) {
    ProductImage productImg = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getProductImageById";
      productImg = session.selectOne(statement, id);
    } catch (Exception e) {
      logger.error("获取产品图片异常：{}", e.getMessage());
    }
    return productImg;
  }

  @Override
  public List<ProductImage> listProductImage(Product product, ImageTypeEnum imageType) {
    List<ProductImage> productImgList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listProductImage";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("product", product);
      parameterMap.put("imageType", imageType);
      productImgList = session.selectList(statement, parameterMap);
    } catch (Exception e) {
      logger.error("获取产品图片异常：{}", e.getMessage());
    }
    return productImgList;
  }
}
