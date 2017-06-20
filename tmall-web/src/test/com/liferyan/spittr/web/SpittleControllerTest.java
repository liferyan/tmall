package com.liferyan.spittr.web;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.liferyan.spittr.Spittle;
import com.liferyan.spittr.data.SpittleRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

/**
 * Created by Ryan on 2017/6/19.
 */
public class SpittleControllerTest {

  @Test
  public void testSpittle() throws Exception{
    Spittle spittle = new Spittle("spittle",new Date());
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    when(mockRepository.findOne(12345)).thenReturn(spittle);

    SpittleController spittleController = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(spittleController)
        .build();

    mockMvc.perform(get("/spittles/12345"))
        .andExpect(view().name("spittle"))
        .andExpect(model().attributeExists("spittle"))
        .andExpect(model().attribute("spittle",spittle));
  }

  @Test
  public void shouldShowPageSpittles() throws Exception {
    List<Spittle> expectedSpittles = createSpittles(50);
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    when(mockRepository.findSpittles(23890, 50)).thenReturn(expectedSpittles);

    SpittleController spittleController = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(spittleController)
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittr/spittles.jsp"))
        .build();

    mockMvc.perform(get("/spittles?max=23890&count=50"))
        .andExpect(view().name("spittles"))
        .andExpect(model().attributeExists("spittleList"))
        .andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())));
  }

  @Test
  public void shouldShowRecentSpittles() throws Exception {
    //mock findSpittles()返回数据
    List<Spittle> expectedSpittles = createSpittles(20);
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    //stubbing
    when(mockRepository.findSpittles(Long.MAX_VALUE, 20)).thenReturn(expectedSpittles);

    SpittleController spittleController = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(spittleController)
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittr/spittles.jsp"))
        .build();

    mockMvc.perform(get("/spittles"))
        .andExpect(view().name("spittles"))
        .andExpect(model().attributeExists("spittleList"))
        .andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())));
  }

  private List<Spittle> createSpittles(int count) {
    List<Spittle> spittles = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      spittles.add(new Spittle("Spittle" + i, new Date()));
    }
    return spittles;
  }

}