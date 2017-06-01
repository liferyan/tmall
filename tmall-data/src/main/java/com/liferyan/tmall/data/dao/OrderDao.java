package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Order;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 */
public interface OrderDao {

  void saveOrder(Order order);

  void deleteOrder(int id);

  void updateOrder(Order order);

  Order getOrderById(int id);

  List<Order> listOrderByPage(int start, int count);

  List<Order> listOrderByUserAndPage(int uid, int start, int count);

  List<Order> listOrderByUser(int uid);

  int getOrderCount();

}
