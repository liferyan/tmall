package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Order;
import com.liferyan.tmall.data.entity.OrderItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by Ryan on 2017/4/18.
 */
public class OrderDao extends BaseDao {

  public void saveOrder(Order order) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "saveOrder";
      session.insert(statement, order);
    } catch (Exception e) {
      logger.error("保存订单异常：", e);
    }
  }

  public void deleteOrder(int id) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "deleteOrder";
      session.delete(statement, id);
    } catch (Exception e) {
      logger.error("删除订单异常：", e);
    }
  }

  public void updateOrder(Order order) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "updateOrder";
      session.update(statement, order);
    } catch (Exception e) {
      logger.error("更新订单异常：", e);
    }
  }

  public Order getOrderById(int id) {
    Order order = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getOrderById";
      order = session.selectOne(statement, id);
    } catch (Exception e) {
      logger.error("获取订单异常：", e);
    }
    return order;
  }

  public List<Order> listOrderByPage(int start, int count) {
    List<Order> orderList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listOrderByPage";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("start", start);
      parameterMap.put("count", count);
      orderList = session.selectList(statement, parameterMap);
      fillOrderItemsToOrderList(orderList);
    } catch (Exception e) {
      logger.error("获取订单异常：", e);
    }
    return orderList;
  }

  public List<Order> listOrderByUserAndPage(int uid, int start, int count) {
    List<Order> orderList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listOrderByUser";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("uid", uid);
      parameterMap.put("start", start);
      parameterMap.put("count", count);
      orderList = session.selectList(statement, parameterMap);
      fillOrderItemsToOrderList(orderList);
    } catch (Exception e) {
      logger.error("获取订单异常：", e);
    }
    return orderList;
  }

  public List<Order> listOrderByUser(int uid) {
    List<Order> orderList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listOrderByUser";
      orderList = session.selectList(statement, uid);
      fillOrderItemsToOrderList(orderList);
    } catch (Exception e) {
      logger.error("获取订单异常：", e);
    }
    return orderList;
  }

  public int getOrderCount() {
    int count = 0;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getOrderCount";
      count = session.selectOne(statement);
    } catch (Exception e) {
      logger.error("获取订单总数异常：", e);
    }
    return count;
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
