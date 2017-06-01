package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.OrderItem;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 */
public interface OrderItemDao {

  void saveOrderItem(OrderItem orderItem);

  void deleteOrderItem(int id);

  void updateOrderItem(OrderItem orderItem);

  OrderItem getOrderItemById(int id);

  List<OrderItem> listOrderItemByOrder(int oid);

  OrderItem getOrderItemInCart(int uid, int pid);

  List<OrderItem> listOrderItemInCartByUser(int uid);

}
