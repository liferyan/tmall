package com.liferyan.mybatis.dao.impl;

import com.liferyan.mybatis.dao.OrderDao;
import com.liferyan.mybatis.entity.Order;
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
public class OrderDaoImpl implements OrderDao {

  private static final Logger logger = LoggerFactory.getLogger("OrderDaoImpl");

  private SqlSessionFactory sqlSessionFactory;

  private static OrderDaoImpl INSTANCE;

  private OrderDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public static OrderDaoImpl getInstance(SqlSessionFactory sqlSessionFactory) {
    if (INSTANCE == null) {
      INSTANCE = new OrderDaoImpl(sqlSessionFactory);
    }
    return INSTANCE;
  }

  @Override
  public void saveOrder(Order order) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "saveOrder";
      session.insert(statement, order);
    } catch (Exception e) {
      logger.error("保存订单异常：{}", e.getMessage());
    }
  }

  @Override
  public void deleteOrder(int id) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "deleteOrder";
      session.delete(statement, id);
    } catch (Exception e) {
      logger.error("删除订单异常：{}", e.getMessage());
    }
  }

  @Override
  public void updateOrder(Order order) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "updateOrder";
      session.update(statement, order);
    } catch (Exception e) {
      logger.error("更新订单异常：{}", e.getMessage());
    }
  }

  @Override
  public Order getOrderById(int id) {
    Order order = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getOrderById";
      order = session.selectOne(statement, id);
    } catch (Exception e) {
      logger.error("获取订单异常：{}", e.getMessage());
    }
    return order;
  }

  @Override
  public List<Order> listOrderByPage(int start, int count) {
    List<Order> orderList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listOrderByPage";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("start", start);
      parameterMap.put("count", count);
      orderList = session.selectList(statement, parameterMap);
    } catch (Exception e) {
      logger.error("获取订单异常：{}", e.getMessage());
    }
    return orderList;
  }

  @Override
  public List<Order> listOrderByUserAndPage(int uid, int start, int count) {
    List<Order> orderList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listOrderByUser";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("uid", uid);
      parameterMap.put("start", start);
      parameterMap.put("count", count);
      orderList = session.selectList(statement, parameterMap);
    } catch (Exception e) {
      logger.error("获取订单异常：{}", e.getMessage());
    }
    return orderList;
  }

  @Override
  public List<Order> listOrderByUser(int uid) {
    List<Order> orderList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listOrderByUser";
      orderList = session.selectList(statement, uid);
    } catch (Exception e) {
      logger.error("获取订单异常：{}", e.getMessage());
    }
    return orderList;
  }

  @Override
  public int getOrderCount() {
    int count = 0;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getOrderCount";
      count = session.selectOne(statement);
    } catch (Exception e) {
      logger.error("获取订单总数异常：{}", e.getMessage());
    }
    return count;
  }
}
