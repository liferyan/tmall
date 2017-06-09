package com.liferyan.tmall.service;

import com.liferyan.tmall.data.dao.UserDao;
import com.liferyan.tmall.data.entity.User;
import java.util.List;

/**
 * Created by Ryan on 2017/6/8.
 */
public class UserService {

  private UserDao userDao;

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public List<User> listUser() {
    return userDao.listUser();
  }

}
