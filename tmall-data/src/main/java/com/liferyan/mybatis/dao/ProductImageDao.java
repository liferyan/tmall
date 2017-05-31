package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.ImageTypeEnum;
import com.liferyan.mybatis.entity.Product;
import com.liferyan.mybatis.entity.ProductImage;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Ryan on 2017/4/18.
 * ProductImgDao接口
 */
public interface ProductImageDao {

  @Insert("INSERT INTO product_image(pid,type) VALUES(#{pid},#{type})")
  @Options(useGeneratedKeys = true)
  void saveProductImage(ProductImage productImage);

  @Delete("DELETE FROM product_image WHERE id = {id}")
  void deleteProductImage(int id);

  @Select("SELECT * FROM product_image WHERE id = #{id}")
  ProductImage getProductImageById(int id);

  @Select("SELECT * FROM product_image WHERE pid = #{pid} AND type = #{type} ORDER BY id DESC")
  List<ProductImage> listProductImage(Product product, ImageTypeEnum imageType);

}
