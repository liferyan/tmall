package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Property;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by Ryan on 2017/4/18.
 */
public class PropertyDao extends SqlSessionDaoSupport {

  public void saveProperty(Property property) {
    this.getSqlSession().insert("saveProperty", property);
  }

  public void deleteProperty(int id) {
    this.getSqlSession().delete("deleteProperty", id);
  }

  public void updateProperty(Property property) {
    this.getSqlSession().update("updateProperty", property);
  }

  public Property getPropertyById(int id) {
    return this.getSqlSession().selectOne("getPropertyById", id);
  }

  public List<Property> listPropertyByPage(int cid, int start, int count) {
    Map<String, Object> parameterMap = new HashMap<>();
    parameterMap.put("cid", cid);
    parameterMap.put("start", start);
    parameterMap.put("count", count);
    return this.getSqlSession().selectList("listPropertyByPage", parameterMap);
  }

  public List<Property> listProperty(int cid) {
    return this.getSqlSession().selectList("listProperty", cid);
  }

  public int getPropertyCount(int cid) {
    return this.getSqlSession().selectOne("getPropertyCount", cid);
  }

}
