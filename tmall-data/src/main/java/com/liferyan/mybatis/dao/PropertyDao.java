package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.Property;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Ryan on 2017/4/18.
 * PropertyDao接口
 */
public interface PropertyDao {

  @Insert("INSERT INTO property(cid,name) VALUES(#{cid},#{name})")
  @Options(useGeneratedKeys = true)
  void saveProperty(Property property);

  @Delete("DELETE FROM property WHERE id = #{id}")
  void deleteProperty(int id);

  @Update("UPDATE property set cid = #{cid},name = #{name} WHERE id = #{name}")
  void updateProperty(Property property);

  @Select("SELECT * FROM property WHERE id = #{id}")
  Property getPropertyById(int id);

  @Select("SELECT * FROM property WHERE cid = #{cid} ORDER BY id LIMIT #{count} OFFSET #{start}")
  List<Property> listPropertyByPage(@Param("cid") int cid, @Param("start") int start,
      @Param("count") int count);

  @Select("SELECT * FROM property WHERE cid = #{cid} ORDER BY id")
  List<Property> listProperty(@Param("cid") int cid);

  @Select("SELECT COUNT(*) FROM property WHERE cid = #{cid}")
  int getPropertyCount(@Param("cid") int cid);

}
