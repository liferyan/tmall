package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Ryan on 2017/5/18.
 */
public interface UserDao {

  @Insert("INSERT INTO user(name,password) VALUES(#{name},#{password})")
  @Options(useGeneratedKeys = true)
  void saveUser(User user);

  @Delete("DELETE FROM user WHERE id = #{id}")
  void deleteUser(int id);

  @Select("SELECT * FROM user WHERE id = #{id}")
  User getUserById(int id);

  @Select("SELECT * FROM user WHERE name = #{name}")
  User getUserByName(String name);

  @Select("SELECT * FROM user WHERE name = #{name} AND password = #{password}")
  User getUserByNameAndPassword(@Param("name") String name, @Param("password") String password);

  @Select("SELECT * FROM user ORDER BY id LIMIT #{count} OFFSET #{start}")
  List<User> listUserByPage(@Param("start") int start, @Param("count") int count);

  @Select("SELECT * FROM user ORDER BY id")
  List<User> listUser();

  @Select("SELECT COUNT(*) FROM user")
  int getUserCount();

}
