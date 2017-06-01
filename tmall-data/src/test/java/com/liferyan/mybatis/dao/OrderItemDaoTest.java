package com.liferyan.mybatis.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.liferyan.mybatis.entity.Order;
import com.liferyan.mybatis.entity.OrderItem;
import com.liferyan.mybatis.entity.Product;
import com.liferyan.mybatis.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ryan on 2017/6/1.
 */
public class OrderItemDaoTest {

  private OrderItem orderItem = new OrderItem();
  private OrderItemDao orderItemDao = DaoFactory.getOrderItemDao();
  private Product product;
  private User user;
  private Order order = new Order();

  @Before
  public void setUp() throws Exception {
    product = DaoFactory.getProductDao().getProductById(958);
    user = DaoFactory.getUserDao().getUserByName("test");
    order.setId(-1);
  }

  @Test
  public void crudOrderItem() throws Exception {
    orderItem.setOrder(null);
    orderItem.setProduct(product);
    orderItem.setUser(user);
    orderItem.setHasReview(true);
    orderItem.setNumber(3);
    orderItemDao.saveOrderItem(orderItem);
    assertThat(orderItem.getId(), is(0));

    orderItem.setOrder(order);
    orderItemDao.saveOrderItem(orderItem);
    assertThat(orderItem.getId(), not(0));

    orderItem.setNumber(5);
    orderItem.setHasReview(false);
    orderItemDao.updateOrderItem(orderItem);
    int id = orderItem.getId();
    orderItem = orderItemDao.getOrderItemById(id);
    assertThat(orderItem.getNumber(), is(5));
    assertThat(orderItem.isHasReview(), is(false));

    order.setId(30);
    orderItem.setOrder(order);
    orderItemDao.updateOrderItem(orderItem);
    orderItem = orderItemDao.getOrderItemById(id);
    assertThat(orderItem.getOrder().getId(), is(30));

    orderItemDao.deleteOrderItem(id);
    orderItem = orderItemDao.getOrderItemById(id);
    assertThat(orderItem, nullValue());
  }

  @Test
  public void listOrderItem() throws Exception {
    List<OrderItem> orderItemListByOrder = orderItemDao.listOrderItemByOrder(48);
    List<OrderItem> orderItemsInCartByUser = orderItemDao.listOrderItemInCartByUser(user.getId());
    List<List<OrderItem>> orderItemsList = new ArrayList<>();
    orderItemsList.add(orderItemListByOrder);
    orderItemsList.add(orderItemsInCartByUser);
    for (List<OrderItem> orderItemCollection : orderItemsList) {
      for (OrderItem orderItem : orderItemCollection) {
        assertThat(orderItem.getNumber(), greaterThan(0));
        assertThat(orderItem.getProduct(), notNullValue());
        assertThat(orderItem.getProduct().getName(), notNullValue());
        assertThat(orderItem.getUser(), notNullValue());
        assertThat(orderItem.getUser().getName(), notNullValue());
        assertThat(orderItem.getOrder(), notNullValue());
        assertThat(orderItem.getOrder().getOrderCode(), notNullValue());
        assertThat(orderItem.getOrder().getCreateDate(), notNullValue());
      }
    }
  }

}