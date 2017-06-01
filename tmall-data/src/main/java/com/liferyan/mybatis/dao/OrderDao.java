package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.Order;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Ryan on 2017/4/18.
 * OrderDao接口
 */
public interface OrderDao {

  @Insert("INSERT INTO order_("
      + "uid,order_code,address,post,receiver,mobile,user_message,"
      + "create_date,pay_date,delivery_date,confirm_date,status) "
      + "VALUES(#{uid},#{order_code},#{address},#{post},#{receiver},#{mobile},#{user_message},"
      + "#{create_date},#{pay_date},#{delivery_date},#{confirm_date},#{status})")
  @Options(useGeneratedKeys = true)
  void saveOrder(Order order);

  @Delete("UPDATE order_ SET status = 'DELETED' WHERE id = #{id}")
  void deleteOrder(int id);

  @Update("UPDATE order_ SET "
      + "uid=#{uid},order_code=#{order_code},address=#{address},post=#{post},receiver=#{receiver},"
      + "mobile=#{mobile},user_message=#{user_message},create_date=#{create_date},pay_date=#{pay_date},"
      + "delivery_date=#{delivery_date},confirm_date=#{confirm_date},status=#{status} WHERE id=#{id}")
  void updateOrder(Order order);

  @Select("SELECT * FROM order_ WHERE id = #{id}")
  Order getOrderById(int id);

  @Select("SELECT * FROM order_ ORDER BY id LIMIT #{count} OFFSET #{start}")
  List<Order> listOrderByPage(@Param("start") int start, @Param("count") int count);

  @Select("SELECT COUNT(*) FROM order_")
  int getOrderCount();

  @Select("SELECT * FROM order_ WHERE uid = #{uid} AND status != 'DELETED' ORDER BY id LIMIT #{count} OFFSET #{start}")
  List<Order> listOrderByUserAndPage(@Param("uid") int uid, @Param("start") int start,
      @Param("count") int count);

  @Select("SELECT * FROM order_ WHERE uid = #{uid} AND status != 'DELETED' ORDER BY id")
  List<Order> listOrderByUser(@Param("uid") int uid);
}
