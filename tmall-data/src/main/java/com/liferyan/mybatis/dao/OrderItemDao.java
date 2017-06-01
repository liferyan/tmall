package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.OrderItem;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Ryan on 2017/4/18.
 * OrderItemDao接口
 */
public interface OrderItemDao {

  @Insert("INSERT INTO order_item(pid,uid,oid,number,has_review) VALUES"
      + "(#{pid},#{uid},#{oid},#{number},#{has_review})")
  void saveOrderItem(OrderItem orderItem);

  @Delete("DELETE FROM order_item WHERE id = #{id}")
  void deleteOrderItem(int id);

  @Update("UPDATE order_item set pid = #{pid},uid = #{uid},oid = #{oid},number = #{number},"
      + "has_review = #{has_review} WHERE id = #{id}")
  void updateOrderItem(OrderItem orderItem);

  @Select("SELECT * FROM order_item WHERE id = #{id}")
  OrderItem getOrderItemById(int id);

  @Select("SELECT * FROM order_item WHERE oid = #{oid} ORDER BY id")
  List<OrderItem> listOrderItemByOrder(@Param("oid") int oid);

  @Select("SELECT * FROM order_item WHERE oid = -1 AND uid = #{uid} AND pid = #{pid}")
  OrderItem getOrderItemInCart(@Param("uid") int uid, @Param("pid") int pid);

  @Select("SELECT * FROM order_item WHERE oid = -1 AND uid = #{uid}")
  List<OrderItem> listOrderItemInCartByUser(@Param("uid") int uid);

}
