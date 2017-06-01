package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.Property;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 */
public interface PropertyDao {

  void saveProperty(Property property);

  void deleteProperty(int id);

  void updateProperty(Property property);

  Property getPropertyById(int id);

  List<Property> listPropertyByPage(int cid, int start, int count);

  List<Property> listProperty(int cid);

  int getPropertyCount(int cid);

}
