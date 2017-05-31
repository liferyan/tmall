package com.liferyan.mybatis.entity;

/**
 * Created by Ryan on 2017/5/27.
 */
public enum ImageTypeEnum {

  SINGLE("type_single", "单张图"), DETAIL("type_detail", "详情图");

  private String code;
  private String description;

  private ImageTypeEnum(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static ImageTypeEnum getEnumFromCode(String code) {
    for (ImageTypeEnum typeEnum : ImageTypeEnum.values()) {
      if (typeEnum.code.equals(code)) {
        return typeEnum;
      }
    }
    return null;
  }
}
