package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.ImageTypeEnum;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.ProductImage;
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
