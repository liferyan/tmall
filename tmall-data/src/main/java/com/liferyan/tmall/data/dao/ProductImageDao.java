package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.ImageTypeEnum;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.ProductImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by Ryan on 2017/4/18.
 */
public class ProductImageDao extends SqlSessionDaoSupport {

  public void saveProductImage(ProductImage productImage) {
    this.getSqlSession().insert("saveProductImage", productImage);
  }

  public void deleteProductImage(int id) {
    this.getSqlSession().delete("deleteProductImage", id);
  }

  public ProductImage getProductImageById(int id) {
    return this.getSqlSession().selectOne("getProductImageById", id);
  }

  public List<ProductImage> listProductImage(Product product, ImageTypeEnum imageType) {
    Map<String, Object> parameterMap = new HashMap<>();
    parameterMap.put("product", product);
    parameterMap.put("imageType", imageType);
    return this.getSqlSession().selectList("listProductImage", parameterMap);
  }

}
