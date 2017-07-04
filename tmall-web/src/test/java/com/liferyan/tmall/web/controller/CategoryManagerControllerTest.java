package com.liferyan.tmall.web.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.liferyan.tmall.data.dao.CategoryDao;
import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.web.controller.backend.CategoryManagerController;
import com.liferyan.tmall.web.util.Page;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ryan on 2017/6/26.
 */
public class CategoryManagerControllerTest {

  private ServletContext mockServletContext;

  private CategoryDao mockCategoryDao;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    mockServletContext = mock(ServletContext.class);
    mockCategoryDao = mock(CategoryDao.class);
    CategoryManagerController categoryManagerController = new CategoryManagerController(mockCategoryDao);
    categoryManagerController.setServletContext(mockServletContext);
    mockMvc = MockMvcBuilders.standaloneSetup(categoryManagerController)
        .build();
  }

  @Test
  public void listCategoryWithNoParam() throws Exception {
    List<Category> categoryList = createCategoryList(5);
    when(mockCategoryDao.listCategoryByPage(0, 5)).thenReturn(categoryList);

    ModelAndView modelAndView = mockMvc.perform(
        MockMvcRequestBuilders.get("/admin/categories"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("admin/listCategory"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("categoryList"))
        .andExpect(MockMvcResultMatchers.model().attribute("categoryList", categoryList))
        .andReturn()
        .getModelAndView();

    List<Category> categories = (List<Category>) modelAndView.getModel().get("categoryList");
    MatcherAssert.assertThat(categories.size(), CoreMatchers.is(categoryList.size()));

    Page page = (Page) modelAndView.getModel().get("page");
    MatcherAssert.assertThat(page.getStart(), CoreMatchers.is(0));
    MatcherAssert.assertThat(page.getCount(), CoreMatchers.is(5));
    MatcherAssert.assertThat(page.getTotalPage(), CoreMatchers.is(1));
    MatcherAssert.assertThat(page.getParam(), CoreMatchers.nullValue());
  }

  @Test
  public void listCategory() throws Exception {
    List<Category> categoryList = createCategoryList(3);
    when(mockCategoryDao.listCategoryByPage(7, 5)).thenReturn(categoryList);

    ModelAndView modelAndView = mockMvc.perform(
        MockMvcRequestBuilders
            .get("/admin/categories")
            .param("page.start", "7")
            .param("page.count", "5"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("admin/listCategory"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("categoryList"))
        .andExpect(MockMvcResultMatchers.model().attribute("categoryList", categoryList))
        .andReturn()
        .getModelAndView();

    List<Category> categories = (List<Category>) modelAndView.getModel().get("categoryList");
    MatcherAssert.assertThat(categories.size(), CoreMatchers.is(categoryList.size()));

    Page page = (Page) modelAndView.getModel().get("page");
    MatcherAssert.assertThat(page.getStart(), CoreMatchers.is(7));
    MatcherAssert.assertThat(page.getCount(), CoreMatchers.is(5));
    MatcherAssert.assertThat(page.getTotalPage(), CoreMatchers.is(1));
    MatcherAssert.assertThat(page.getParam(), CoreMatchers.nullValue());
  }

  @Test
  public void addCategory() throws Exception {
    when(mockServletContext.getRealPath("img/category"))
        .thenReturn("/Users/Ryan/Developer/Tmall/upload");

    Category category = new Category();
    category.setName("奶粉");
    FileInputStream contentStream = new FileInputStream(
        "/Users/Ryan/Developer/Tmall/tmall_milk.jpg");
    MockMultipartFile uploadFile = new MockMultipartFile("category_image", contentStream);

    Mockito.doNothing().when(mockCategoryDao).saveCategory(category);

    mockMvc.perform(MockMvcRequestBuilders
        .fileUpload("/admin/categories")
        .file(uploadFile)
        .param("name", category.getName()))
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
  }

  private List<Category> createCategoryList(int categorySize) {
    List<Category> categoryList = new ArrayList<>();
    Category category;
    for (int i = 1; i <= categorySize; i++) {
      category = new Category();
      category.setId(i);
      category.setName("Category" + i);
      categoryList.add(category);
    }
    return categoryList;
  }


}