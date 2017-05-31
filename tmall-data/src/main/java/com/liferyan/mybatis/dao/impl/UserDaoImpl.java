package com.liferyan.mybatis.dao.impl;

import com.liferyan.mybatis.dao.UserDao;
import com.liferyan.mybatis.entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ryan on 2017/5/18.
 */
public class UserDaoImpl implements UserDao {

  private static final Logger logger = LoggerFactory.getLogger("UserDaoImpl");

  private SqlSessionFactory sqlSessionFactory;

  private static UserDaoImpl INSTANCE;

  private UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public static UserDaoImpl getInstance(SqlSessionFactory sqlSessionFactory) {
    if (INSTANCE == null) {
      INSTANCE = new UserDaoImpl(sqlSessionFactory);
    }
    return INSTANCE;
  }

  @Override
  public void saveUser(User user) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "saveUser";
      session.insert(statement, user);
    } catch (Exception e) {
      logger.error("保存用户异常：{}", e.getMessage());
    }
  }

  @Override
  public void deleteUser(int id) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "deleteUser";
      session.delete(statement, id);
    } catch (Exception e) {
      logger.error("删除用户异常：{}", e.getMessage());
    }
  }

  @Override
  public User getUserById(int id) {
    User user = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getUserById";
      user = session.selectOne(statement, id);
    } catch (Exception e) {
      logger.error("获取用户异常：{}", e.getMessage());
    }
    return user;
  }

  @Override
  public User getUserByName(String name) {
    User user = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getUserByName";
      user = session.selectOne(statement, name);
    } catch (Exception e) {
      logger.error("获取用户异常：{}", e.getMessage());
    }
    return user;
  }

  @Override
  public List<User> listUserByPage(int start, int count) {
    List<User> userList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listUserByPage";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("start", start);
      parameterMap.put("count", count);
      userList = session.selectList(statement, parameterMap);
    } catch (Exception e) {
      logger.error("获取用户异常：{}", e.getMessage());
    }
    return userList;
  }

  @Override
  public List<User> listUser() {
    List<User> userList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listUser";
      userList = session.selectList(statement);
    } catch (Exception e) {
      logger.error("获取用户异常：{}", e.getMessage());
    }
    return userList;
  }

  @Override
  public User getUserByNameAndPassword(String name, String password) {
    User user = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getUserByNameAndPassword";
      User newUser = new User();
      newUser.setName(name);
      newUser.setPassword(password);
      user = session.selectOne(statement, newUser);
    } catch (Exception e) {
      logger.error("获取用户异常：{}", e.getMessage());
    }
    return user;
  }

  @Override
  public int getUserCount() {
    int count = 0;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getUserCount";
      count = session.selectOne(statement);
    } catch (Exception e) {
      logger.error("获取用户总数异常：{}", e.getMessage());
    }
    return count;
  }
}
