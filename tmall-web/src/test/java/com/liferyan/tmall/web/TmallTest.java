package com.liferyan.tmall.web;

import static org.hamcrest.Matchers.is;

import com.liferyan.tmall.data.config.DaoConfig;
import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.web.util.Page;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ryan on 2017/6/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
    @ContextConfiguration(classes = DaoConfig.class),
    @ContextConfiguration(classes = WebConfig.class)
})
@ActiveProfiles("dev")
public class TmallTest {

  @Autowired
  WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac)
        .build();
  }

  @Test
  public void showCategoryList() throws Exception {
    ModelAndView modelAndView = mockMvc.perform(MockMvcRequestBuilders
        .get("/admin"))
        .andExpect(MockMvcResultMatchers.view().name("admin/listCategory"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("category_list"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("page"))
        .andReturn()
        .getModelAndView();

    Page page = (Page) modelAndView.getModel().get("page");
    MatcherAssert.assertThat(page.getStart(), CoreMatchers.is(0));
    MatcherAssert.assertThat(page.getCount(), CoreMatchers.is(5));
    MatcherAssert.assertThat(page.getTotalPage(), CoreMatchers.is(4));
    MatcherAssert.assertThat(page.getParam(), CoreMatchers.nullValue());

    List<Category> categoryList = (List<Category>) modelAndView.getModel().get("category_list");
    MatcherAssert.assertThat(categoryList.size(), is(5));
  }
}
