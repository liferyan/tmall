package com.liferyan.tmall.data.entity;

/**
 * Created by Ryan on 2017/4/18.
 * 订单项
 */
public class OrderItem {

  /**
   * 订单ID
   */
  private int id;

  /**
   * 订单项购买数量
   */
  private int number;

  /**
   * 是否已评价
   */
  private boolean hasReview;

  /**
   * 订单项所属商品
   */
  private Product product;

  /**
   * 订单项所属用户
   */
  private User user;

  /**
   * 订单项所属订单
   */
  private Order order;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public boolean isHasReview() {
    return hasReview;
  }

  public void setHasReview(boolean hasReview) {
    this.hasReview = hasReview;
  }

  @Override
  public String toString() {
    return "OrderItem{" +
        "id=" + id +
        ", number=" + number +
        ", hasReview=" + hasReview +
        ", product=" + product +
        ", user=" + user +
        ", order=" + order +
        '}';
  }
}
