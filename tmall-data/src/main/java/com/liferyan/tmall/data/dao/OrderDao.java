package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Order;
import com.liferyan.tmall.data.entity.OrderItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by Ryan on 2017/4/18.
 */
public class OrderDao extends SqlSessionDaoSupport {

  public void saveOrder(Order order) {
    this.getSqlSession().insert("saveOrder", order);
  }

  public void deleteOrder(int id) {
    this.getSqlSession().delete("deleteOrder", id);
  }

  public void updateOrder(Order order) {
    this.getSqlSession().update("updateOrder", order);
  }

  public Order getOrderById(int id) {
    return this.getSqlSession().selectOne("getOrderById", id);
  }

  public List<Order> listOrderByPage(int start, int count) {
    Map<String, Object> parameterMap = new HashMap<>();
    parameterMap.put("start", start);
    parameterMap.put("count", count);
    List<Order> orderList = this.getSqlSession().selectList("listOrderByPage", parameterMap);
    fillOrderItemsToOrderList(orderList);
    return orderList;
  }

  public List<Order> listOrderByUserAndPage(int uid, int start, int count) {
    Map<String, Object> parameterMap = new HashMap<>();
    parameterMap.put("uid", uid);
    parameterMap.put("start", start);
    parameterMap.put("count", count);
    List<Order> orderList = this.getSqlSession().selectList("listOrderByUser", parameterMap);
    fillOrderItemsToOrderList(orderList);
    return orderList;
  }

  public List<Order> listOrderByUser(int uid) {
    List<Order> orderList = this.getSqlSession().selectList("listOrderByUser", uid);
    fillOrderItemsToOrderList(orderList);
    return orderList;
  }

  public int getOrderCount() {
    return this.getSqlSession().selectOne("getOrderCount");
  }

  private void fillOrderItemsToOrderList(List<Order> orderList) {
    for (Order order : orderList) {
      float total = 0;
      int totalNumber = 0;
      List<OrderItem> orderItemList = order.getOrderItems();
      for (OrderItem orderItem : orderItemList) {
        total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
        totalNumber += orderItem.getNumber();
      }
      order.setTotal(total);
      order.setTotalNumber(totalNumber);
    }
  }

}
