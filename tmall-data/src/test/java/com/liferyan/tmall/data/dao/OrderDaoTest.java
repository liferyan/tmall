package com.liferyan.tmall.data.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.liferyan.tmall.data.entity.Order;
import com.liferyan.tmall.data.entity.OrderStatusEnum;
import com.liferyan.tmall.data.entity.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ryan on 2017/6/1.
 */
public class OrderDaoTest {

  private Order order = new Order();
  private OrderDao orderDao = DaoFactory.getOrderDao();
  private User user;

  @Before
  public void setUp() throws Exception {
    user = DaoFactory.getUserDao().getUserByName("test");
    Date createDate = new Date();
    String now = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(createDate);
    String orderCode = now + RandomStringUtils.randomNumeric(4);
    order.setOrderCode(orderCode);
    order.setAddress("上海市徐汇区");
    order.setPost("200000");
    order.setReceiver("测试");
    order.setMobile("13544556677");
    order.setUserMessage("快递小哥辛苦了!");
    order.setCreateDate(createDate);
    order.setOrderStatus(OrderStatusEnum.WAIT_PAY);
    order.setUser(user);
  }

  @Test
  public void crudOrder() throws Exception {
    order.setCreateDate(null);
    orderDao.saveOrder(order);
    assertThat(order.getId(), is(0));

    order.setCreateDate(new Date());
    order.setOrderStatus(null);
    orderDao.saveOrder(order);
    assertThat(order.getId(), is(0));

    order.setOrderStatus(OrderStatusEnum.WAIT_PAY);
    orderDao.saveOrder(order);
    int id = order.getId();
    assertThat(id, not(0));

    assertThat(order.getPayDate(), nullValue());
    order.setPayDate(new Date());
    orderDao.updateOrder(order);
    order = orderDao.getOrderById(id);
    assertThat(order.getPayDate(), notNullValue());

    orderDao.deleteOrder(id);
    order = orderDao.getOrderById(id);
    assertThat(order, notNullValue());
    assertThat(order.getOrderStatus(), equalTo(OrderStatusEnum.DELETED));
  }

  @Test
  public void listOrder() throws Exception {
    List<Order> orderList = orderDao.listOrderByPage(0, Short.MAX_VALUE);
    int size = orderDao.getOrderCount();
    assertThat(orderList.size(), is(size));
    List<Order> ordersByUser = orderDao.listOrderByUser(user.getId());
    List<Order> ordersByUserAndPage = orderDao
        .listOrderByUserAndPage(user.getId(), 0, Short.MAX_VALUE);
    List<List<Order>> ordersList = new ArrayList<>();
    ordersList.add(orderList);
    ordersList.add(ordersByUser);
    ordersList.add(ordersByUserAndPage);
    for (List<Order> OrderCollection : ordersList) {
      for (Order order : OrderCollection) {
        assertThat(order.getOrderCode(), notNullValue());
        assertThat(order.getCreateDate(), notNullValue());
        assertThat(order.getOrderStatus(), notNullValue());
        assertThat(order.getUser(), notNullValue());
        assertThat(order.getUser().getName(), notNullValue());
        assertThat(order.getTotal(), greaterThanOrEqualTo(0f));
        assertThat(order.getTotalNumber(), greaterThanOrEqualTo(0));
      }
    }
  }
}