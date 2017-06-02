package com.liferyan.tmall.data.dao.impl;

import com.liferyan.tmall.data.dao.PropertyDao;
import com.liferyan.tmall.data.entity.Property;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ryan on 2017/5/23.
 */
public class PropertyDaoImpl implements PropertyDao {

  public static final Logger logger = LoggerFactory.getLogger("PropertyDaoImpl");

  private SqlSessionFactory sqlSessionFactory;

  private static PropertyDaoImpl INSTANCE;

  private PropertyDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public static PropertyDaoImpl getInstance(SqlSessionFactory sqlSessionFactory) {
    if (INSTANCE == null) {
      INSTANCE = new PropertyDaoImpl(sqlSessionFactory);
    }
    return INSTANCE;
  }

  @Override
  public void saveProperty(Property property) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "saveProperty";
      session.insert(statement, property);
    } catch (Exception e) {
      logger.error("保存属性异常：", e);
    }
  }

  @Override
  public void deleteProperty(int id) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "deleteProperty";
      session.delete(statement, id);
    } catch (Exception e) {
      logger.error("删除属性异常：", e);
    }
  }

  @Override
  public void updateProperty(Property property) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "updateProperty";
      session.update(statement, property);
    } catch (Exception e) {
      logger.error("更新属性异常：", e);
    }
  }

  @Override
  public Property getPropertyById(int id) {
    Property property = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getPropertyById";
      property = session.selectOne(statement, id);
    } catch (Exception e) {
      logger.error("获取属性异常：", e);
    }
    return property;
  }

  @Override
  public List<Property> listPropertyByPage(int cid, int start, int count) {
    List<Property> propertyList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listPropertyByPage";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("cid", cid);
      parameterMap.put("start", start);
      parameterMap.put("count", count);
      propertyList = session.selectList(statement, parameterMap);
    } catch (Exception e) {
      logger.error("获取属性异常：", e);
    }
    return propertyList;
  }

  @Override
  public List<Property> listProperty(int cid) {
    List<Property> propertyList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listProperty";
      propertyList = session.selectList(statement, cid);
    } catch (Exception e) {
      logger.error("获取属性异常：", e);
    }
    return propertyList;
  }

  @Override
  public int getPropertyCount(int cid) {
    int count = 0;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getPropertyCount";
      count = session.selectOne(statement, cid);
    } catch (Exception e) {
      logger.error("获取属性总数异常：", e);
    }
    return count;
  }
}
