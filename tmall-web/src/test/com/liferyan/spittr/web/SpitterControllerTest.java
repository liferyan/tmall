package com.liferyan.spittr.web;

import static org.mockito.Mockito.atLeastOnce;
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
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by Ryan on 2017/6/20.
 */
public class SpitterControllerTest {

  @Test
  public void shouldshowRegistration() throws Exception {
    SpitterRepository mockRepository = mock(SpitterRepository.class);
    SpitterController spitterController = new SpitterController(mockRepository);
    MockMvc mockMvc = standaloneSetup(spitterController)
        .build();

    mockMvc.perform(get("/spitter/register"))
        .andExpect(view().name("registerForm"));
  }

  @Test
  public void processingRegistration() throws Exception {
    SpitterRepository mockRepository = mock(SpitterRepository.class);
    Spitter unsaved = new Spitter("jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
    Spitter saved = new Spitter(24L, "jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
    //stubbing
    when(mockRepository.save(unsaved)).thenReturn(saved);
    when(mockRepository.findByUsername("jbauer")).thenReturn(saved);

    SpitterController spitterController = new SpitterController(mockRepository);
    MockMvc mockMvc = standaloneSetup(spitterController)
        .build();

    mockMvc.perform(post("/spitter/register")
        .param("firstName", "Jack")
        .param("lastName", "Bauer")
        .param("username", "jbauer")
        .param("password", "24hours")
        .param("email", "liferyan@126.com"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/spitter/jbauer"));
    verify(mockRepository, atLeastOnce()).save(unsaved);

    mockMvc.perform(get("/spitter/jbauer"))
        .andExpect(view().name("profile"))
        .andExpect(model().attributeExists("spitter"))
        .andExpect(model().attribute("spitter", saved));
    verify(mockRepository, atLeastOnce()).findByUsername("jbauer");
  }

  @Test
  public void shouldFailValidationWithNoData() throws Exception {
    SpitterRepository mockRepository = mock(SpitterRepository.class);
    SpitterController controller = new SpitterController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();

    mockMvc.perform(post("/spitter/register"))
        .andExpect(status().isOk())
        .andExpect(view().name("registerForm"))
        .andExpect(model().errorCount(5))
        .andExpect(model().attributeHasFieldErrors(
            "spitter", "firstName", "lastName", "username", "password", "email"));
  }

}