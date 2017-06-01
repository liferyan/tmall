package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.PropertyValue;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 */
public interface PropertyValueDao {

  void initPropertyValueWithProduct(Product product);

  void updatePropertyValue(PropertyValue propertyValue);

  List<PropertyValue> listPropertyValue(int productId);

}
