package com.liferyan.spittr;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.liferyan.spittr.config.RootConfig;
import com.liferyan.spittr.web.WebConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Ryan on 2017/6/19.
 * Spittr集成测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, RootConfig.class})
@WebAppConfiguration
public class SpittrTest {

  @Autowired
  private WebApplicationContext ctx;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    mockMvc = webAppContextSetup(ctx).build();
  }

  @Test
  public void spittr() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(view().name("home"));
    mockMvc.perform(get("/spittles"))
        .andExpect(view().name("spittles"));
    mockMvc.perform(post("/spitter/register")
        .param("lastName", "Bauer")
        .param("username", "jbauer")
        .param("password", "24hours")
        .param("email", "email"))
        .andExpect(status().isOk())
        .andExpect(view().name("registerForm"))
        .andExpect(model().errorCount(2))
        .andExpect(model().attributeHasFieldErrors("spitter", "firstName", "email"))
        .andDo(print());
  }

}
