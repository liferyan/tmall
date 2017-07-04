package com.liferyan.tmall.web.controller.portal;

import com.liferyan.tmall.data.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ryan on 2017/7/4.
 */
@Controller
@RequestMapping("/")
public class HomeController {

  private CategoryDao categoryDao;

  @Autowired
  public HomeController(CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  @GetMapping
  public String showHomePage(Model model) {
    model.addAttribute(categoryDao.listCategory());
    return "home";
  }
}
