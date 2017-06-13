package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Created by Ryan on 2017/4/18.
 */
@Component
public class ProductDao extends BaseDao {

  public void saveProduct(Product product) {
    this.getSqlSession().insert("saveProduct", product);
  }

  public void deleteProduct(int id) {
    this.getSqlSession().insert("deleteProduct", id);
  }

  public void updateProduct(Product product) {
    this.getSqlSession().update("updateProduct", product);
  }

  public Product getProductById(int id) {
    return this.getSqlSession().selectOne("getProductById", id);
  }

  public List<Product> listProductByPage(int cid, int start, int count) {
    Map<String, Object> parameterMap = new HashMap<>();
    parameterMap.put("cid", cid);
    parameterMap.put("start", start);
    parameterMap.put("count", count);
    return this.getSqlSession().selectList("listProductByPage", parameterMap);
  }

  public List<Product> listProductByCategory(int cid) {
    return this.getSqlSession().selectList("listProductByCategory", cid);
  }

  public int getProductCountByCategory(int cid) {
    return this.getSqlSession().selectOne("getProductCountByCategory", cid);
  }

  public List<Product> searchProduct(String keyword, int start, int count) {
    Map<String, Object> parameterMap = new HashMap<>();
    parameterMap.put("keyword", keyword);
    parameterMap.put("start", start);
    parameterMap.put("count", count);
    return this.getSqlSession().selectList("searchProduct", parameterMap);
  }

}
