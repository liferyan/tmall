package com.liferyan.tmall.spring.c2.soundsystem;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Ryan on 2017/6/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig.class)
public class CDPlayerTest {

  @Autowired
  private CompactDisc disc;

  @Test
  public void cdShouldNotBeNull() {
    disc.play();
    Assert.assertThat(disc, Matchers.notNullValue());
  }

}