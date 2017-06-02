package com.liferyan.tmall.data.dao.impl;

import com.liferyan.tmall.data.dao.OrderItemDao;
import com.liferyan.tmall.data.entity.OrderItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ryan on 2017/5/26.
 */
public class OrderItemDaoImpl implements OrderItemDao {

  private static final Logger logger = LoggerFactory.getLogger("OrderItemDaoImpl");

  private SqlSessionFactory sqlSessionFactory;

  private static OrderItemDaoImpl INSTANCE;

  private OrderItemDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public static OrderItemDaoImpl getInstance(SqlSessionFactory sqlSessionFactory) {
    if (INSTANCE == null) {
      INSTANCE = new OrderItemDaoImpl(sqlSessionFactory);
    }
    return INSTANCE;
  }

  @Override
  public void saveOrderItem(OrderItem orderItem) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "saveOrderItem";
      session.insert(statement, orderItem);
    } catch (Exception e) {
      logger.error("保存订单项异常：", e);
    }
  }

  @Override
  public void deleteOrderItem(int id) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "deleteOrderItem";
      session.delete(statement, id);
    } catch (Exception e) {
      logger.error("删除订单项异常：", e);
    }
  }

  @Override
  public void updateOrderItem(OrderItem orderItem) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "updateOrderItem";
      session.update(statement, orderItem);
    } catch (Exception e) {
      logger.error("更新订单项异常：", e);
    }
  }

  @Override
  public OrderItem getOrderItemById(int id) {
    OrderItem orderItem = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getOrderItemById";
      orderItem = session.selectOne(statement, id);
    } catch (Exception e) {
      logger.error("获取订单项异常：", e);
    }
    return orderItem;
  }

  @Override
  public List<OrderItem> listOrderItemByOrder(int oid) {
    List<OrderItem> orderItemList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listOrderItemByOrder";
      orderItemList = session.selectList(statement, oid);
    } catch (Exception e) {
      logger.error("获取订单里的所有订单项异常：", e);
    }
    return orderItemList;
  }

  @Override
  public OrderItem getOrderItemInCart(int uid, int pid) {
    OrderItem orderItem = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getOrderItemInCart";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("uid", uid);
      parameterMap.put("pid", pid);
      orderItem = session.selectOne(statement, parameterMap);
    } catch (Exception e) {
      logger.error("获取购物车里指定产品的订单项异常：", e);
    }
    return orderItem;
  }

  @Override
  public List<OrderItem> listOrderItemInCartByUser(int uid) {
    List<OrderItem> orderItemList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listOrderItemInCartByUser";
      orderItemList = session.selectList(statement, uid);
    } catch (Exception e) {
      logger.error("获取用户购物车里的所有订单项异常：", e);
    }
    return orderItemList;
  }
}
