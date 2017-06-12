package com.liferyan.tmall;

import com.liferyan.tmall.data.entity.User;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Ryan on 2017/6/12.
 */
public class JdbcTest {

  private static final Logger logger = LoggerFactory.getLogger("JdbcTest");

  private UserDao userDao;

  private ClassPathXmlApplicationContext context;

  @Before
  public void setUp() throws Exception {
    context = new ClassPathXmlApplicationContext("user.xml");
  }

  @After
  public void tearDown() throws Exception {
    context.close();
  }

  @Test
  public void testJdbc() throws Exception {
    userDao = new UserDao();
    List<User> userList = userDao.listUserUseJdbc(0, 100);
    logger.info("User List:{}", userList);
    Assert.assertThat(userList.size(), Matchers.greaterThan(0));
  }

  @Test
  public void testJdbcTemplate() throws Exception {
    userDao = context.getBean(UserDao.class);
    List<User> userList = userDao.listUserUseJdbcTemplate(0,100);
    logger.info("User List:{}", userList);
    Assert.assertThat(userList.size(), Matchers.greaterThan(0));
  }

}
