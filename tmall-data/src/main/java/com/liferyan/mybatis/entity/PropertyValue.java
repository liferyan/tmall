package com.liferyan.mybatis.entity;

/**
 * Created by Ryan on 2017/4/18.
 * 属性值
 */
public class PropertyValue {

  /**
   * 属性值ID
   */
  private int id;

  /**
   * 属性值
   */
  private String value;

  /**
   * 所属属性
   */
  private Property property;

  /**
   * 所属商品
   */
  private Product product;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Property getProperty() {
    return property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @Override
  public String toString() {
    return "PropertyValue{" +
        "value='" + value + '\'' +
        ", property=" + property +
        ", product=" + product +
        '}';
  }
}
