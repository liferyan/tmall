package com.liferyan.spittr.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.liferyan.spittr.Spitter;
import com.liferyan.spittr.data.SpitterRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * Created by Ryan on 2017/6/20.
 */
public class SpitterControllerTest {

  private SpitterRepository mockRepository;

  private MockMvc mockMvc;

  @Before
  public void setupMock() throws Exception {
    mockRepository = mock(SpitterRepository.class);
    SpitterController spitterController = new SpitterController(mockRepository);
    mockMvc = standaloneSetup(spitterController)
        .build();
  }

  @Test
  public void shouldshowRegistration() throws Exception {
    ResultActions resultActions = mockMvc.perform(get("/spitter/register"));

    resultActions
        .andExpect(view().name("registerForm"))
        .andExpect(model().attributeExists("spitter"));
  }

  @Test
  public void shouldProcessingRegistration() throws Exception {
    Spitter unsaved = new Spitter("jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
    Spitter saved = new Spitter(24L, "jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
    when(mockRepository.save(unsaved)).thenReturn(saved);
    when(mockRepository.findByUsername("jbauer")).thenReturn(saved);

    ResultActions resultActions = mockMvc.perform(
        post("/spitter/register")
            .param("firstName", "Jack")
            .param("lastName", "Bauer")
            .param("username", "jbauer")
            .param("password", "24hours")
            .param("email", "liferyan@126.com"));

    verify(mockRepository).save(unsaved);
    resultActions
        .andExpect(view().name("redirect:/spitter/jbauer"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/spitter/jbauer"));

    ResultActions redirectResultActions = mockMvc.perform(get("/spitter/jbauer"));

    verify(mockRepository).findByUsername("jbauer");
    redirectResultActions
        .andExpect(view().name("profile"))
        .andExpect(model().attributeExists("spitter"))
        .andExpect(model().attribute("spitter", saved));
  }

  @Test
  public void shouldFailValidationWithNoData() throws Exception {
    ResultActions resultActions = mockMvc.perform(post("/spitter/register"));

    resultActions
        .andExpect(view().name("registerForm"))
        .andExpect(status().isOk())
        .andExpect(model().errorCount(5))
        .andExpect(model().attributeHasFieldErrors(
            "spitter", "firstName", "lastName", "username", "password", "email"));
  }

}