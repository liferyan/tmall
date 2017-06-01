package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.Product;
import com.liferyan.mybatis.entity.PropertyValue;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 */
public interface PropertyValueDao {

  void initPropertyValueWithProduct(Product product);

  void updatePropertyValue(PropertyValue propertyValue);

  List<PropertyValue> listPropertyValue(int productId);

}
