package com.liferyan.tmall.data.entity;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ryan on 2017/4/18.
 * 商品图片
 */
public class ProductImage implements Serializable {

  private static final long serialVersionUID = 6323423457671110456L;
  /**
   * 商品图片ID
   */
  private int id;

  /**
   * 商品图片类型
   */
  private ImageTypeEnum imageType;

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

  public ImageTypeEnum getImageType() {
    return imageType;
  }

  public void setImageType(ImageTypeEnum imageType) {
    this.imageType = imageType;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ProductImage that = (ProductImage) o;

    return new EqualsBuilder()
        .append(id, that.id)
        .append(imageType, that.imageType)
        .append(product, that.product)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(imageType)
        .append(product)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("imageType", imageType)
        .append("product", product)
        .toString();
  }
}
