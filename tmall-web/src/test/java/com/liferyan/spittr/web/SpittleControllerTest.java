package com.liferyan.spittr.web;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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

  private SpittleRepository mockRepository;

  private MockMvc mockMvc;

  @Before
  public void setupMock() throws Exception {
    //准备测试环境
    mockRepository = mock(SpittleRepository.class);
    SpittleController spittleController = new SpittleController(mockRepository);
    mockMvc = standaloneSetup(spittleController)
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittr/spittles.jsp"))
        .build();
  }

  @Test
  public void shouldShowRecentSpittles() throws Exception {
    List<Spittle> expectedSpittles = createSpittles(20);
    when(mockRepository.findSpittles(Long.MAX_VALUE, 20)).thenReturn(expectedSpittles);

    ResultActions resultActions = mockMvc.perform(get("/spittles"));

    verify(mockRepository).findSpittles(Long.MAX_VALUE, 20);
    resultActions
        .andExpect(view().name("spittles"))
        .andExpect(model().attributeExists("spittleList"))
        .andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())));
  }

  @Test
  public void shouldShowPageSpittles() throws Exception {
    List<Spittle> expectedSpittles = createSpittles(50);
    when(mockRepository.findSpittles(23890, 50)).thenReturn(expectedSpittles);

    ResultActions resultActions = mockMvc.perform(get("/spittles?max=23890&count=50"));

    verify(mockRepository).findSpittles(23890, 50);
    resultActions
        .andExpect(view().name("spittles"))
        .andExpect(model().attributeExists("spittleList"))
        .andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())));
  }

  @Test
  public void shouldShowSingleSpittle() throws Exception {
    //准备测试数据
    Spittle spittle = new Spittle("spittle", new Date());
    when(mockRepository.findOne(12345)).thenReturn(spittle);

    //调用被测系统
    ResultActions resultActions = mockMvc.perform(get("/spittles/12345"));

    //验证
    verify(mockRepository).findOne(12345);
    resultActions
        .andExpect(view().name("spittle"))
        .andExpect(model().attributeExists("spittle"))
        .andExpect(model().attribute("spittle", spittle));
  }

  @Test
  public void saveSpittle() throws Exception {
    Spittle mockSpittle = mock(Spittle.class);
    ResultActions errorResultActions = mockMvc.perform(post("/spittles")
        .param("longitude", "-81.5811668")
        .param("latitude", "28.4159649"));

    verify(mockRepository, never()).save(mockSpittle);
    errorResultActions
        .andExpect(view().name("spittles"))
        .andExpect(model().errorCount(1))
        .andExpect(model().attributeHasFieldErrors("spittleForm", "message"));

    ResultActions resultActions = mockMvc.perform(post("/spittles")
        .param("message", "Hello World!")
        .param("longitude", "-81.5811668")
        .param("latitude", "28.4159649"));

    //verify(mockRepository).save(mockSpittle);
    resultActions
        .andExpect(view().name("redirect:/spittles"));
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