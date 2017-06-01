package com.liferyan.tmall.data.entity;

/**
 * Created by Ryan on 2017/4/18. 订单状态
 */
public enum OrderStatusEnum {

  WAIT_PAY("WAIT_PAY", "待付款"),

  WAIT_DELIVERY("WAIT_DELIVERY", "待发货"),

  WAIT_CONFIRM("WAIT_CONFIRM", "待收货"),

  WAIT_REVIEW("WAIT_REVIEW", "待评价"),

  FINISHED("FINISHED", "已完成"),

  DELETED("DELETED", "已删除");

  private String code;
  private String description;

  private OrderStatusEnum(String code, String description) {
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

  public static OrderStatusEnum getEnumFromCode(String code) {
    for (OrderStatusEnum statusEnum : OrderStatusEnum.values()) {
      if (statusEnum.code.equals(code)) {
        return statusEnum;
      }
    }
    return null;
  }

}
