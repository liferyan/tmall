package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.Product;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Ryan on 2017/4/18.
 * ProductDao接口
 */
public interface ProductDao {

  @Insert("INSERT INTO product("
      + "cid,name,subtitle,original_price,promote_price,stock,create_date) VALUES("
      + "#{cid},#{name},#{subtitle},#{original_price},#{promote_price},#{stock},#{create_date})")
  @Options(useGeneratedKeys = true)
  void saveProduct(Product product);

  @Delete("DELETE FROM product WHERE id = #{id}")
  void deleteProduct(int id);

  @Update("UPDATE product set "
      + "cid = #{cid},name = #{name},subtitle = #{subtitle},original_price = #{original_price},"
      + "promote_price = #{promote_price},stock = #{stock},create_date = #{create_date} WHERE id = #{id}")
  void updateProduct(Product product);

  @Select("SELECT * FROM product WHERE id = #{id}")
  Product getProductById(int id);

  @Select("SELECT * FROM product WHERE cid = #{cid} ORDER BY id LIMIT #{count} OFFSET #{start}")
  List<Product> listProductByPage(@Param("cid") int cid, @Param("start") int start,
      @Param("count") int count);

  @Select("SELECT * FROM product WHERE cid = #{cid} ORDER BY id")
  List<Product> listProductByCategory(@Param("cid") int cid);

  @Select("SELECT COUNT(*) FROM product WHERE cid = #{cid}")
  int getProductCountByCategory(@Param("cid") int cid);

  @Select("SELECT * FROM product WHERE name REGEXP #{keyword} LIMIT #{count} OFFSET #{start}")
  List<Product> search(@Param("keyword") String keyword, @Param("start") int start,
      @Param("count") int count);

}
