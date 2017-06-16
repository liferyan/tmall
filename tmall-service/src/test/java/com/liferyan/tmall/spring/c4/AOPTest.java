package com.liferyan.tmall.spring.c4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Ryan on 2017/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AOPConfig.class)
public class AOPTest {

  @Autowired
  Performance performance;

  @Test
  public void performance() throws Exception {
    performance.perform();
  }

}