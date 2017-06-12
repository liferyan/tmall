package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.Property;
import com.liferyan.tmall.data.entity.PropertyValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by Ryan on 2017/4/18.
 */
public class PropertyValueDao extends SqlSessionDaoSupport {

  public void initPropertyValueWithProduct(Product product) {
    List<Property> properties = product.getCategory().getProperties();
    for (Property property : properties) {
      //数据库中没有该属性则添加属性
      if (getPropertyValue(property.getId(), product.getId()) == null) {
        this.getSqlSession().insert("savePropertyValue", product);
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
