package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by Ryan on 2017/5/18.
 */
public class UserDao extends SqlSessionDaoSupport {

  public void saveUser(User user) {
    this.getSqlSession().insert("saveUser", user);
  }

  public void deleteUser(int id) {
    this.getSqlSession().delete("deleteUser", id);
  }

  public User getUserById(int id) {
    return this.getSqlSession().selectOne("getUserById", id);
  }

  public User getUserByName(String name) {
    return this.getSqlSession().selectOne("getUserByName", name);
  }

  public List<User> listUserByPage(int start, int count) {
    Map<String, Object> parameterMap = new HashMap<>();
    parameterMap.put("start", start);
    parameterMap.put("count", count);
    return this.getSqlSession().selectList("listUserByPage", parameterMap);
  }

  public List<User> listUser() {
    return this.getSqlSession().selectList("listUser");
  }

  public User getUserByNameAndPassword(String name, String password) {
    User newUser = new User();
    newUser.setName(name);
    newUser.setPassword(password);
    return this.getSqlSession().selectOne("getUserByNameAndPassword", newUser);
  }

  public int getUserCount() {
    return this.getSqlSession().selectOne("getUserCount");
  }

}
