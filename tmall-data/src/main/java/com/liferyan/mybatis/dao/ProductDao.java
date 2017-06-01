package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.Product;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 */
public interface ProductDao {

  void saveProduct(Product product);

  void deleteProduct(int id);

  void updateProduct(Product product);

  Product getProductById(int id);

  List<Product> listProductByPage(int cid, int start, int count);

  List<Product> listProductByCategory(int cid);

  int getProductCountByCategory(int cid);

  List<Product> searchProduct(String keyword, int start, int count);

}
