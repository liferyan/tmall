package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.Property;
import com.liferyan.tmall.data.entity.PropertyValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by Ryan on 2017/4/18.
 */
public class PropertyValueDao extends BaseDao {

  public void initPropertyValueWithProduct(Product product) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "savePropertyValue";
      List<Property> properties = product.getCategory().getProperties();
      for (Property property : properties) {
        //数据库中没有该属性则添加属性
        if (getPropertyValue(property.getId(), product.getId()) == null) {
          session.insert(statement, product);
        }
      }
    } catch (Exception e) {
      logger.error("保存属性值异常：", e);
    }
  }

  public void updatePropertyValue(PropertyValue propertyValue) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "updatePropertyValue";
      session.update(statement, propertyValue);
    } catch (Exception e) {
      logger.error("更新属性值异常：", e);
    }
  }

  public List<PropertyValue> listPropertyValue(int productId) {
    List<PropertyValue> propertyValueList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listPropertyValue";
      propertyValueList = session.selectList(statement, productId);
    } catch (Exception e) {
      logger.error("获取属性值异常：", e);
    }
    return propertyValueList;
  }

  private PropertyValue getPropertyValue(int propertyId, int productId) {
    PropertyValue propertyValue = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getPropertyValue";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("ppid", propertyId);
      parameterMap.put("pid", productId);
      propertyValue = session.selectOne(statement, parameterMap);
    } catch (Exception e) {
      logger.error("获取属性值异常：", e);
    }
    return propertyValue;
  }

}
