package com.liferyan.tmall.service;

import com.liferyan.tmall.data.dao.UserDao;
import com.liferyan.tmall.data.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Ryan on 2017/6/8.
 */
@Component
public class UserService {

  @Autowired
  private UserDao userDao;

  public List<User> listUser() {
    return userDao.listUser();
  }

}
