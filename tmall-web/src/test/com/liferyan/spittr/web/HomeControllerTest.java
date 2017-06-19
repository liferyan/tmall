package com.liferyan.spittr.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by Ryan on 2017/6/19.
 */
public class HomeControllerTest {

  @Test
  public void testHomePage() throws Exception {
    HomeController homeController = new HomeController();
    MockMvc mockMvc = standaloneSetup(homeController).build();
    mockMvc.perform(get("/"))
        .andExpect(view().name("home"));
    mockMvc.perform(get("/homepage"))
        .andExpect(view().name("home"));
  }

}