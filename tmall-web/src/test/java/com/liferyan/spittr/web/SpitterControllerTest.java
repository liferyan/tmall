package com.liferyan.spittr.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.liferyan.spittr.Spitter;
import com.liferyan.spittr.data.SpitterRepository;
import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * Created by Ryan on 2017/6/20.
 */
public class SpitterControllerTest {

  private SpitterRepository mockSpitterRepository;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    mockSpitterRepository = mock(SpitterRepository.class);
    mockMvc = standaloneSetup(new SpitterController(mockSpitterRepository))
        .build();
  }

  @Test
  public void showRegistrationForm() throws Exception {
    ResultActions resultActions = mockMvc.perform(
        get("/spitter/register"));

    resultActions
        .andExpect(view().name("registerForm"))
        .andExpect(model().attributeExists("spitter"));
  }

  @Test
  public void registerSpitterAndShowProfile() throws Exception {
    Spitter unsaved = new Spitter("jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
    Spitter saved = new Spitter(24L, "jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
    when(mockSpitterRepository.save(unsaved)).thenReturn(saved);
    when(mockSpitterRepository.findByUsername("jbauer")).thenReturn(saved);

    //for upload
    InputStream contentStream = new FileInputStream("/Users/Ryan/Downloads/head2016.jpg");
    //profilePicture to RequestPart name
    MockMultipartFile imgFile = new MockMultipartFile("profilePicture", contentStream);

    ResultActions resultActions = mockMvc.perform(
        fileUpload("/spitter/register")
            .file(imgFile)
            .param("firstName", "Jack")
            .param("lastName", "Bauer")
            .param("username", "jbauer")
            .param("password", "24hours")
            .param("email", "liferyan@126.com"));

    verify(mockSpitterRepository).save(unsaved);
    resultActions
        .andExpect(view().name("redirect:/spitter/jbauer"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/spitter/jbauer"));

    ResultActions redirectResultActions = mockMvc.perform(
        get("/spitter/jbauer"));

    verify(mockSpitterRepository).findByUsername("jbauer");
    redirectResultActions
        .andExpect(view().name("profile"))
        .andExpect(model().attributeExists("spitter"))
        .andExpect(model().attribute("spitter", saved));
  }

  @Test
  public void registerSpitterFailValidationWithNoData() throws Exception {
    ResultActions resultActions = mockMvc.perform(
        fileUpload("/spitter/register"));

    resultActions
        .andExpect(view().name("registerForm"))
        .andExpect(status().isOk())
        .andExpect(model().errorCount(5))
        .andExpect(model().attributeHasFieldErrors(
            "spitter", "firstName", "lastName", "username", "password", "email"));
  }

}