package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.ImageTypeEnum;
import com.liferyan.mybatis.entity.Product;
import com.liferyan.mybatis.entity.ProductImage;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 */
public interface ProductImageDao {

  void saveProductImage(ProductImage productImage);

  void deleteProductImage(int id);

  ProductImage getProductImageById(int id);

  List<ProductImage> listProductImage(Product product, ImageTypeEnum imageType);

}
