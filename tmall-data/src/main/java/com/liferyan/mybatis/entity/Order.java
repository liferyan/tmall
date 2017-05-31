package com.liferyan.mybatis.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 * 订单
 */
public class Order {

  /**
   * 订单ID
   */
  private int id;

  /**
   * 订单号
   */
  private String orderCode;

  /**
   * 收货地址
   */
  private String address;

  /**
   * 邮编
   */
  private String post;

  /**
   * 收货人姓名
   */
  private String receiver;

  /**
   * 收货人手机号码
   */
  private String mobile;

  /**
   * 用户备注信息
   */
  private String userMessage;

  /**
   * 订单创建时间
   */
  private Date createDate;

  /**
   * 订单支付时间
   */
  private Date payDate;

  /**
   * 订单发货时间
   */
  private Date deliveryDate;

  /**
   * 订单确认收货时间
   */
  private Date confirmDate;

  /**
   * 订单状态
   */
  private OrderStatusEnum orderStatus;

  /**
   * 订单所属用户
   */
  private User user;

  /**
   * 订单包含的订单项列表
   */
  private List<OrderItem> orderItems;

  /**
   * 订单总价
   */
  private float total;
  /**
   * 订单项数量
   */
  private int totalNumber;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPost() {
    return post;
  }

  public void setPost(String post) {
    this.post = post;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getUserMessage() {
    return userMessage;
  }

  public void setUserMessage(String userMessage) {
    this.userMessage = userMessage;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getPayDate() {
    return payDate;
  }

  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }

  public Date getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(Date deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public Date getConfirmDate() {
    return confirmDate;
  }

  public void setConfirmDate(Date confirmDate) {
    this.confirmDate = confirmDate;
  }

  public OrderStatusEnum getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatusEnum orderStatus) {
    this.orderStatus = orderStatus;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public float getTotal() {
    return total;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  public int getTotalNumber() {
    return totalNumber;
  }

  public void setTotalNumber(int totalNumber) {
    this.totalNumber = totalNumber;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", orderCode='" + orderCode + '\'' +
        ", address='" + address + '\'' +
        ", post='" + post + '\'' +
        ", receiver='" + receiver + '\'' +
        ", mobile='" + mobile + '\'' +
        ", userMessage='" + userMessage + '\'' +
        ", createDate=" + createDate +
        ", payDate=" + payDate +
        ", deliveryDate=" + deliveryDate +
        ", confirmDate=" + confirmDate +
        ", orderStatus=" + orderStatus +
        ", user=" + user +
        ", orderItems=" + orderItems +
        ", total=" + total +
        ", totalNumber=" + totalNumber +
        '}';
  }
}
