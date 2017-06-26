package com.liferyan.tmall.data.entity;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ryan on 2017/4/18.
 * 商品分类
 */
public class Category implements Serializable {

  private static final long serialVersionUID = -8060813009244565097L;
  /**
   * 分类ID
   */
  private int id;

  /**
   * 分类名
   */
  @NotNull
  @Size(min = 1, max = 8, message = "{category.name.size}")
  private String name;

  /**
   * 类别下的所有商品
   */
  private List<Product> products;

  /**
   * 纵向导航栏分类推荐列表
   */
  private List<List<Product>> productsByRow;

  /**
   * 分类的所有属性
   */
  private List<Property> properties;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public List<List<Product>> getProductsByRow() {
    return productsByRow;
  }

  public void setProductsByRow(List<List<Product>> productsByRow) {
    this.productsByRow = productsByRow;
  }

  public List<Property> getProperties() {
    return properties;
  }

  public void setProperties(List<Property> properties) {
    this.properties = properties;
  }

  @Override
  public boolean equals(Object otherObj) {
    if (this == otherObj) {
      return true;
    }
    if (otherObj == null || getClass() != otherObj.getClass()) {
      return false;
    }

    Category category = (Category) otherObj;

    return new EqualsBuilder()
        .append(id, category.id)
        .append(name, category.name)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(name)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .append("products", products)
        .append("productsByRow", productsByRow)
        .append("properties", properties)
        .toString();
  }
}
