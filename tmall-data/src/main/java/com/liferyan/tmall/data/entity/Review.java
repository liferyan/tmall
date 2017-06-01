package com.liferyan.tmall.data.entity;

import java.util.Date;

/**
 * Created by Ryan on 2017/4/18.
 * 用户商品评价
 */
public class Review {

  /**
   * 用户商品评价ID
   */
  private int id;

  /**
   * 用户商品评价内容
   */
  private String content;

  /**
   * 用户评价创建时间
   */
  private Date createDate;

  /**
   * 用户评价所属商品
   */
  private Product product;

  /**
   * 评价用户
   */
  private User user;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
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

  @Override
  public String toString() {
    return "Review{" +
        "content='" + content + '\'' +
        ", createDate=" + createDate +
        ", product=" + product +
        ", user=" + user +
        '}';
  }
}
