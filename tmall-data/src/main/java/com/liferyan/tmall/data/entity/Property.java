package com.liferyan.tmall.data.entity;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ryan on 2017/4/18.
 * 属性
 */
public class Property implements Serializable {

  private static final long serialVersionUID = -5870899683980831026L;
  /**
   * 属性ID
   */
  private int id;

  /**
   * 属性名
   */
  @NotNull
  @Size(min = 1, max = 8, message = "{property.name.size}")
  private String name;

  /**
   * 属性所属类别
   */
  private Category category;

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

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Property that = (Property) o;

    return new EqualsBuilder()
        .append(id, that.id)
        .append(name, that.name)
        .append(category, that.category)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(name)
        .append(category)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .append("category", category)
        .toString();
  }
}
