package com.liferyan.tmall;

import com.liferyan.tmall.service.UserService;
import com.liferyan.tmall.service.config.ServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Ryan on 2017/6/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class DaoTest {

  @Autowired
  private UserService userService;

  @Test
  public void testMybatisWithSpring() throws Exception {
    System.out.println(userService.listUser());
  }

}
