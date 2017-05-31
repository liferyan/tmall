package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.Product;
import com.liferyan.mybatis.entity.PropertyValue;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 * PropertyValueDao接口
 */
public interface PropertyValueDao {

  /**
   * 初始化产品对应的属性值(添加属性值)
   */
  void initPropertyValueWithProduct(Product product);

  /**
   * 更新属性值
   */
  void updatePropertyValue(PropertyValue propertyValue);

  /**
   * 根据产品ID获取所有属性值
   */
  List<PropertyValue> listPropertyValue(int productId);

}
