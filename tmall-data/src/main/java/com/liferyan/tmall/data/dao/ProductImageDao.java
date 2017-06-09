package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.ImageTypeEnum;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.ProductImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by Ryan on 2017/4/18.
 */
public class ProductImageDao extends BaseDao {

  public void saveProductImage(ProductImage productImage) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "saveProductImage";
      session.insert(statement, productImage);
    } catch (Exception e) {
      logger.error("保存产品图片异常：", e);
    }
  }

  public void deleteProductImage(int id) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "deleteProductImage";
      session.delete(statement, id);
    } catch (Exception e) {
      logger.error("删除产品图片异常：", e);
    }
  }

  public ProductImage getProductImageById(int id) {
    ProductImage productImg = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getProductImageById";
      productImg = session.selectOne(statement, id);
    } catch (Exception e) {
      logger.error("获取产品图片异常：", e);
    }
    return productImg;
  }

  public List<ProductImage> listProductImage(Product product, ImageTypeEnum imageType) {
    List<ProductImage> productImgList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listProductImage";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("product", product);
      parameterMap.put("imageType", imageType);
      productImgList = session.selectList(statement, parameterMap);
    } catch (Exception e) {
      logger.error("获取产品图片异常：", e);
    }
    return productImgList;
  }

}
