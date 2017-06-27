package com.liferyan.tmall.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ryan on 2017/4/18.
 * 商品
 */
public class Product implements Serializable {

  private static final long serialVersionUID = -5524226068789285108L;
  /**
   * 商品ID
   */
  private int id;

  /**
   * 商品名
   */
  @Size(min = 1, max = 8, message = "{product.name.size}")
  private String name;

  /**
   * 商品小标题
   */
  @Size(min = 1, max = 8, message = "{product.subtitle.size}")
  private String subTitle;

  /**
   * 商品原始价格
   */
  @NotNull(message = "{product.original_price.empty}")
  @DecimalMin(value = "0", inclusive = false, message = "{product.original_price.min}")
  private Float originalPrice;

  /**
   * 商品优惠价格
   */
  @NotNull(message = "{product.promote_price.empty}")
  @DecimalMin(value = "0", inclusive = false, message = "{product.promote_price.min}")
  private Float promotePrice;

  /**
   * 商品库存
   */
  @NotNull(message = "{product.stock.empty}")
  @Min(value = 1, message = "{product.stock.min}")
  private Integer stock;

  /**
   * 商品创建时间
   */
  private Date createDate;

  /**
   * 商品所属类别
   */
  private Category category;

  /**
   * 第一张产品图片
   */
  private ProductImage firstProductImage;

  /**
   * 单个产品图片列表
   */
  private List<ProductImage> singleProductImageList;

  /**
   * 详情产品图片列表
   */
  private List<ProductImage> detailProductImageList;

  /**
   * 销量数
   */
  private int saleCount;

  /**
   * 评价数
   */
  private int reviewCount;

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

  public String getSubTitle() {
    return subTitle;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public Float getOriginalPrice() {
    return originalPrice;
  }

  public void setOriginalPrice(Float originalPrice) {
    this.originalPrice = originalPrice;
  }

  public Float getPromotePrice() {
    return promotePrice;
  }

  public void setPromotePrice(Float promotePrice) {
    this.promotePrice = promotePrice;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public int getSaleCount() {
    return saleCount;
  }

  public void setSaleCount(int saleCount) {
    this.saleCount = saleCount;
  }

  public int getReviewCount() {
    return reviewCount;
  }

  public void setReviewCount(int reviewCount) {
    this.reviewCount = reviewCount;
  }

  public ProductImage getFirstProductImage() {
    return firstProductImage;
  }

  public void setFirstProductImage(ProductImage firstProductImage) {
    this.firstProductImage = firstProductImage;
  }

  public List<ProductImage> getSingleProductImageList() {
    return singleProductImageList;
  }

  public void setSingleProductImageList(
      List<ProductImage> singleProductImageList) {
    this.singleProductImageList = singleProductImageList;
  }

  public List<ProductImage> getDetailProductImageList() {
    return detailProductImageList;
  }

  public void setDetailProductImageList(
      List<ProductImage> detailProductImageList) {
    this.detailProductImageList = detailProductImageList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Product product = (Product) o;

    return new EqualsBuilder()
        .append(id, product.id)
        .append(name, product.name)
        .append(subTitle, product.subTitle)
        .append(originalPrice, product.originalPrice)
        .append(promotePrice, product.promotePrice)
        .append(stock, product.stock)
        .append(createDate, product.createDate)
        .append(category, product.category)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(name)
        .append(subTitle)
        .append(originalPrice)
        .append(promotePrice)
        .append(stock)
        .append(createDate)
        .append(category)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .append("subTitle", subTitle)
        .append("originalPrice", originalPrice)
        .append("promotePrice", promotePrice)
        .append("stock", stock)
        .append("createDate", createDate)
        .append("category", category)
        .toString();
  }
}
