package com.liferyan.spittr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.liferyan.spittr.config.RootConfig;
import com.liferyan.spittr.web.SpittleForm;
import com.liferyan.spittr.web.WebConfig;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ryan on 2017/6/19.
 * Spittr 集成测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
    @ContextConfiguration(classes = RootConfig.class),
    @ContextConfiguration(classes = WebConfig.class)}
)
public class SpittrTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setupMock() throws Exception {
    //准备测试环境
    mockMvc = webAppContextSetup(wac).build();
  }

  @Test
  public void testRegisterSpitter() throws Exception {
    //准备测试数据
    List<Spitter> spitterList = new ArrayList<>();
    spitterList.add(new Spitter("spring", "24hours", "Jack", "Bauer", "liferyan@126.com"));
    spitterList.add(new Spitter("mybatis", "24hours", "Jack", "Bauer", "liferyan@126.com"));
    spitterList.add(new Spitter("hibernate", "24hours", "Jack", "Bauer", "liferyan@126.com"));
    spitterList.add(new Spitter("liferyan", "24hours", "Jack", "Bauer", "liferyan@126.com"));

    mockMvc.perform(
        get("/spitter/register"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("spitter"));

    //调用被测系统
    for (Spitter spitter : spitterList) {
      mockMvc.perform(
          post("/spitter/register")
              .param("firstName", spitter.getFirstName())
              .param("lastName", spitter.getLastName())
              .param("username", spitter.getUsername())
              .param("password", spitter.getPassword())
              .param("email", spitter.getEmail()))
          .andExpect(status().isFound());
    }

    //验证结果
    for (Spitter spitter : spitterList) {
      ModelAndView modelAndView = mockMvc.perform(
          get("/spitter/" + spitter.getUsername()))
          .andExpect(model().attributeExists("spitter"))
          .andReturn()
          .getModelAndView();

      spitter = (Spitter) modelAndView.getModel().get("spitter");

      assertThat(spitter.getId(), not(0L));
      assertThat(spitter.getUsername(), not(isEmptyOrNullString()));
      assertThat(spitter.getPassword(), nullValue());
      assertThat(spitter.getFirstName(), not(isEmptyOrNullString()));
      assertThat(spitter.getLastName(), not(isEmptyOrNullString()));
    }
  }

  @Test
  public void testSaveSpittle() throws Exception {
    List<SpittleForm> spittleFormList = new ArrayList<>();
    spittleFormList.add(new SpittleForm("刮风☔！", 88.88, 88.88));
    spittleFormList.add(new SpittleForm("🔎大雨！", 77.77, 77.77));
    spittleFormList.add(new SpittleForm("🌧好大呀！", 66.66, 66.66));

    mockMvc.perform(
        get("/spittles"))
        .andExpect(model().attributeExists("spittleForm"));

    for (SpittleForm spittleForm : spittleFormList) {
      mockMvc.perform(
          post("/spittles")
              .param("message", spittleForm.getMessage())
              .param("longitude", spittleForm.getLongitude().toString())
              .param("latitude", spittleForm.getLatitude().toString()))
          .andExpect(status().isFound());
    }

    ModelAndView modelAndView = mockMvc.perform(
        get("/spittles"))
        .andExpect(model().attributeExists("spittleForm"))
        .andReturn()
        .getModelAndView();

    List<Spittle> spittleList = (List<Spittle>) modelAndView.getModel().get("spittleList");
    for (Spittle spittle : spittleList) {
      assertThat(spittle.getId(), not(0L));
      assertThat(spittle.getMessage(), not(isEmptyOrNullString()));
      assertThat(spittle.getTime(), notNullValue());
      assertThat(spittle.getLongitude(), not(0.0));
      assertThat(spittle.getLatitude(), not(0.0));
    }
  }

}
