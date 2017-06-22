package com.liferyan.spittr.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by Ryan on 2017/6/19.
 */
public class HomeControllerTest {

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    HomeController homeController = new HomeController();
    mockMvc = standaloneSetup(homeController).build();
  }

  @Test
  public void home() throws Exception {
    mockMvc.perform(
        get("/"))
        .andExpect(view().name("home"));
    mockMvc.perform(
        get("/homepage"))
        .andExpect(view().name("home"));
  }

}