package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.OrderItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by Ryan on 2017/4/18.
 */
public class OrderItemDao extends SqlSessionDaoSupport {

  public void saveOrderItem(OrderItem orderItem) {
    this.getSqlSession().insert("saveOrderItem", orderItem);
  }

  public void deleteOrderItem(int id) {
    this.getSqlSession().delete("deleteOrderItem", id);
  }

  public void updateOrderItem(OrderItem orderItem) {
    this.getSqlSession().update("updateOrderItem", orderItem);
  }

  public OrderItem getOrderItemById(int id) {
    return this.getSqlSession().selectOne("getOrderItemById", id);
  }

  public List<OrderItem> listOrderItemByOrder(int oid) {
    return this.getSqlSession().selectList("listOrderItemByOrder", oid);
  }

  public OrderItem getOrderItemInCart(int uid, int pid) {
    Map<String, Object> parameterMap = new HashMap<>();
    parameterMap.put("uid", uid);
    parameterMap.put("pid", pid);
    return this.getSqlSession().selectOne("getOrderItemInCart", parameterMap);
  }

  public List<OrderItem> listOrderItemInCartByUser(int uid) {
    return this.getSqlSession().selectList("listOrderItemInCartByUser", uid);
  }

}
