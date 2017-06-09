package com.liferyan.tmall.pojo;

import com.liferyan.tmall.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Ryan on 2017/6/7.
 */
public class DaoTest {

  public static final Logger logger = LoggerFactory.getLogger("DaoTest");

  ApplicationContext context;

  @Before
  public void setUp() throws Exception {
    context = new ClassPathXmlApplicationContext("services.xml");
  }

  @Test
  public void testMybatisWithSpring() throws Exception {
    UserService userService = (UserService) context.getBean("userService");
    System.out.println(userService.listUser());
  }

}
