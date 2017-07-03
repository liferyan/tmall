package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.Property;
import com.liferyan.tmall.data.entity.PropertyValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Created by Ryan on 2017/4/18.
 */
@Component
public class PropertyValueDao extends BaseDao {

  public void initPropertyValueWithProduct(Product product) {
    List<Property> properties = product.getCategory().getProperties();
    for (Property property : properties) {
      int propertyId = property.getId();
      int productId = product.getId();
      //数据库中没有该属性则添加属性
      if (getPropertyValue(propertyId, productId) == null) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("ppid", propertyId);
        parameterMap.put("pid", productId);
        this.getSqlSession().insert("savePropertyValue", parameterMap);
      }
    }
  }

  public void updatePropertyValue(PropertyValue propertyValue) {
    this.getSqlSession().update("updatePropertyValue", propertyValue);
  }

  public List<PropertyValue> listPropertyValue(int productId) {
    return this.getSqlSession().selectList("listPropertyValue", productId);
  }

  private PropertyValue getPropertyValue(int propertyId, int productId) {
    Map<String, Object> parameterMap = new HashMap<>();
    parameterMap.put("ppid", propertyId);
    parameterMap.put("pid", productId);
    return this.getSqlSession().selectOne("getPropertyValue", parameterMap);
  }

}
