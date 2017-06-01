package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Property;
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
