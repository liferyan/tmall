package com.liferyan.spittr.web;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.liferyan.spittr.Spittle;
import com.liferyan.spittr.data.SpittleRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.view.InternalResourceView;

/**
 * Created by Ryan on 2017/6/19.
 */
public class SpittleControllerTest {

  private SpittleRepository mockSpittleRepository;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    //准备测试环境
    mockSpittleRepository = mock(SpittleRepository.class);
    mockMvc = standaloneSetup(new SpittleController(mockSpittleRepository))
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittr/spittles.jsp"))
        .build();
  }

  @Test
  public void showSpittleListWithNoParam() throws Exception {
    List<Spittle> expectedSpittles = createSpittles(20);
    when(mockSpittleRepository.findSpittles(Long.MAX_VALUE, 20)).thenReturn(expectedSpittles);

    ResultActions resultActions = mockMvc.perform(
        get("/spittles"));

    verify(mockSpittleRepository).findSpittles(Long.MAX_VALUE, 20);
    resultActions
        .andExpect(view().name("spittles"))
        .andExpect(model().attributeExists("spittleList"))
        .andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())));
  }

  @Test
  public void showSpittleList() throws Exception {
    List<Spittle> expectedSpittles = createSpittles(50);
    when(mockSpittleRepository.findSpittles(23890, 50)).thenReturn(expectedSpittles);

    ResultActions resultActions = mockMvc.perform(
        get("/spittles?max=23890&count=50"));

    verify(mockSpittleRepository).findSpittles(23890, 50);
    resultActions
        .andExpect(view().name("spittles"))
        .andExpect(model().attributeExists("spittleList"))
        .andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())));
  }

  @Test
  public void showSpittle() throws Exception {
    //准备测试数据
    Spittle spittle = new Spittle("spittle", new Date());
    when(mockSpittleRepository.findOne(12345)).thenReturn(spittle);

    //调用被测系统
    ResultActions resultActions = mockMvc.perform(
        get("/spittles/12345"));

    //验证
    verify(mockSpittleRepository).findOne(12345);
    resultActions
        .andExpect(view().name("spittle"))
        .andExpect(model().attributeExists("spittle"))
        .andExpect(model().attribute("spittle", spittle));
  }

  @Test
  public void saveSpittle() throws Exception {
    Spittle spittle = new Spittle(1L, "Hello World!", new Date(), -81.5811668, 28.4159649);

    ResultActions saveSpittleResult = mockMvc.perform(
        post("/spittles")
            .param("message", spittle.getMessage())
            .param("longitude", spittle.getLongitude().toString())
            .param("latitude", spittle.getLatitude().toString()));

    verify(mockSpittleRepository).save(spittle);
    saveSpittleResult
        .andExpect(view().name("redirect:/spittles"));
  }

  @Test
  public void saveSpittleFailValidationWithNoData() throws Exception {
    Spittle spittle = new Spittle(1L, "Hello Spring MVC!", new Date(), -81.5811668, 28.4159649);
    List<Spittle> expectedSpittles = new ArrayList<>();
    expectedSpittles.add(spittle);
    when(mockSpittleRepository.findSpittles(Long.MAX_VALUE, 20)).thenReturn(expectedSpittles);

    ResultActions errorResultActions = mockMvc.perform(
        post("/spittles")
            .param("longitude", spittle.getLongitude().toString())
            .param("latitude", spittle.getLatitude().toString()));

    errorResultActions
        .andExpect(status().isOk())
        .andExpect(view().name("spittles"))
        .andExpect(model().errorCount(1))
        .andExpect(model().attributeHasFieldErrors("spittleForm", "message"))
        .andExpect(model().attributeExists("spittleList"));
  }

  /**
   * 生成测试模拟数据
   */
  private List<Spittle> createSpittles(int count) {
    List<Spittle> spittles = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      spittles.add(new Spittle("Spittle" + i, new Date()));
    }
    return spittles;
  }

}