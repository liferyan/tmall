package com.liferyan.tmall.data.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

import com.liferyan.tmall.data.entity.User;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ryan on 2017/5/21.
 */
public class UserDaoTest {

  private int count;
  private User user = new User();
  private UserDao userDao = DaoFactory.getUserDao();

  @Before
  public void setUp() throws Exception {
    count = userDao.getUserCount();
  }

  @After
  public void tearDown() throws Exception {
    assertThat(userDao.getUserCount(), is(count));
  }

  @Test
  public void crudUser() throws Exception {
    user.setName("mybatis");
    user.setPassword("mybatis");
    userDao.saveUser(user);
    int id = user.getId();
    assertThat(id, not(0));
    user = userDao.getUserById(id);
    assertThat(user, notNullValue());
    assertThat(user.getId(), not(0));
    assertThat(user.getName(), notNullValue());
    userDao.deleteUser(id);
    user = userDao.getUserById(id);
    assertThat(user, nullValue());
  }

  @Test
  public void listUser() throws Exception {
    user = userDao.getUserByName("admin");
    assertThat(user.getId(), not(0));
    assertThat(user.getName(), equalTo("admin"));
    assertThat(userDao.getUserCount(), greaterThan(0));
    user = userDao.getUserByNameAndPassword("admin", "admin");
    assertThat(user, notNullValue());
    List<User> userList = userDao.listUser();
    assertThat(userList.size(), greaterThan(0));
    List<User> userListPages = userDao.listUserByPage(0, 2);
    assertThat(userListPages.size(), is(2));
    for (User user : userList) {
      assertThat(user.getId(), not(0));
      assertThat(user.getName(), notNullValue());
    }
  }

}