package com.liferyan.tmall.web.controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Ryan on 2017/6/26.
 */
public class CategoryForm {

  @NotNull
  @Size(min = 1, max = 8, message = "{category.name.size}")
  private String name;

  @NotNull(message = "{category.image.empty}")
  private MultipartFile categoryImage;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MultipartFile getCategoryImage() {
    return categoryImage;
  }

  public void setCategoryImage(MultipartFile categoryImage) {
    this.categoryImage = categoryImage;
  }
}
