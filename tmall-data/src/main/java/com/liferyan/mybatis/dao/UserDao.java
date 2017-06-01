package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.User;
import java.util.List;

/**
 * Created by Ryan on 2017/5/18.
 */
public interface UserDao {

  void saveUser(User user);

  void deleteUser(int id);

  User getUserById(int id);

  User getUserByName(String name);

  User getUserByNameAndPassword(String name, String password);

  List<User> listUserByPage(int start, int count);

  List<User> listUser();

  int getUserCount();

}
