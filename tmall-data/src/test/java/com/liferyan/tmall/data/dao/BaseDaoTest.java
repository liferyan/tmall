package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.config.DaoConfig;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Ryan on 2017/6/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoConfig.class)
@ActiveProfiles("dev")
@Ignore
public class BaseDaoTest {

}
