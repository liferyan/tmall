package com.liferyan.tmall.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.liferyan.tmall.data.dao.CategoryDao;
import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.web.util.ImageUtil;
import com.liferyan.tmall.web.util.Page;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Ryan on 2017/6/26.
 */
@Controller
public class CategoryController {

  private CategoryDao categoryDao;

  private ServletContext servletContext;

  @Autowired
  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  @Autowired
  public CategoryController(CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  @RequestMapping(path = {"/admin_category_list", "/admin"})
  public String listCategory(
      @RequestParam(name = "page.start", defaultValue = "0") int pageStart,
      @RequestParam(name = "page.count", defaultValue = "5") int pageCount,
      Model model) {
    List<Category> categoryList = categoryDao.listCategoryByPage(pageStart, pageCount);
    Page page = new Page(pageStart, pageCount);
    page.setTotal(categoryDao.getCategoryCount());
    if (!model.containsAttribute("category")) {
      model.addAttribute(new Category());
    }
    model.addAttribute("category_list", categoryList);
    model.addAttribute("page", page);
    return "admin/listCategory";
  }

  @RequestMapping(path = "/admin_category_add", method = POST)
  public String addCategory(
      @Valid Category category,
      Errors errors,
      @RequestPart(name = "categoryImage") MultipartFile categoryImage,
      Model model
  ) throws IOException {
    if (errors.hasErrors()) {
      return listCategory(0, 5, model);
    }
    categoryDao.saveCategory(category);
    if (!categoryImage.isEmpty()) {
      String imgPath =
          servletContext.getRealPath("img/category") + File.separator + category.getId()
              + ".jpg";
      File imgFile = new File(imgPath);
      categoryImage.transferTo(imgFile);
      //统一将图片转换为jpg格式
      BufferedImage img = ImageUtil.change2jpg(imgFile);
      ImageIO.write(img, "jpg", imgFile);
    }
    return "redirect:/admin_category_list";
  }

}
