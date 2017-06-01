package com.liferyan.tmall.data.dao.impl;

import com.liferyan.tmall.data.dao.PropertyValueDao;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.Property;
import com.liferyan.tmall.data.entity.PropertyValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ryan on 2017/5/26.
 */
public class PropertyValueDaoImpl implements PropertyValueDao {

  private static final Logger logger = LoggerFactory.getLogger("PropertyValueDaoImpl");

  private SqlSessionFactory sqlSessionFactory;

  private static PropertyValueDaoImpl INSTANCE;

  private PropertyValueDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public static PropertyValueDaoImpl getInstance(SqlSessionFactory sqlSessionFactory) {
    if (INSTANCE == null) {
      INSTANCE = new PropertyValueDaoImpl(sqlSessionFactory);
    }
    return INSTANCE;
  }

  @Override
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
      logger.error("保存属性值异常：{}", e.getMessage());
    }
  }

  @Override
  public void updatePropertyValue(PropertyValue propertyValue) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "updatePropertyValue";
      session.update(statement, propertyValue);
    } catch (Exception e) {
      logger.error("更新属性值异常：{}", e.getMessage());
    }
  }

  @Override
  public List<PropertyValue> listPropertyValue(int productId) {
    List<PropertyValue> propertyValueList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listPropertyValue";
      propertyValueList = session.selectList(statement, productId);
    } catch (Exception e) {
      logger.error("获取属性值异常：{}", e.getMessage());
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
      logger.error("获取属性值异常：{}", e.getMessage());
    }
    return propertyValue;
  }
}
