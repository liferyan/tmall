package com.liferyan.tmall.data.entity;

import java.io.Serializable;

/**
 * Created by Ryan on 2017/4/17.
 * 用户
 */
public class User implements Serializable {

  private static final long serialVersionUID = -4517084657242009151L;
  /**
   * 用户ID
   */
  private int id;
  /**
   * 用户名
   */
  private String name;
  /**
   * 密码
   */
  transient private String password;

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    if (!name.equals(user.name)) {
      return false;
    }
    return password.equals(user.password);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + password.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        ", password='" + password + '\'' +
        '}';
  }

  /**
   * 获取匿名姓名
   * 巴拉克奥巴马 -> 巴****马
   * 张三 -> 张*
   * 李 -> 李
   */
  public String getAnonymousName() {
    int len = name.length();
    if (len == 1) {
      return name;
    } else if (len == 2) {
      return name.substring(0, 1) + "*";
    } else {
      StringBuilder replace = new StringBuilder();
      for (int i = 1; i <= len - 2; i++) {
        replace.append("*");
      }
      return name.substring(0, 1) + replace.toString() + name.substring(len - 1, len);
    }
  }
}
