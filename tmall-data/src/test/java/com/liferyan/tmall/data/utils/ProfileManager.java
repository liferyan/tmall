package com.liferyan.tmall.data.utils;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;
import static org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME;

import com.liferyan.tmall.data.config.DaoConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Ryan on 2017/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoConfig.class)
@ActiveProfiles("dev")
public class ProfileManager {

  @Autowired
  Environment environment;

  @Test
  public void getActiveProfiles() {
    String activeProfile = environment.getProperty(ACTIVE_PROFILES_PROPERTY_NAME);
    String path = environment.getProperty("prod");
    String defaultProfile =environment.getProperty(DEFAULT_PROFILES_PROPERTY_NAME);
    System.out.println("prod:" + path);
    System.out.println("activeProfile:" + activeProfile);
    System.out.println("defaultProfile:" + defaultProfile);
  }

}
