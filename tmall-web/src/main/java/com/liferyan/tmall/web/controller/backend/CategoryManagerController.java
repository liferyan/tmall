package com.liferyan.tmall.web.controller.backend;

import com.liferyan.tmall.data.dao.CategoryDao;
import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.web.util.ImageUtil;
import com.liferyan.tmall.web.util.Page;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Ryan on 2017/6/26.
 */
@Controller
@RequestMapping("/backend")
public class CategoryManagerController {

  private CategoryDao categoryDao;

  private ServletContext servletContext;

  @Autowired
  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  @Autowired
  public CategoryManagerController(CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  @GetMapping("/categories")
  public String showCategoryList(
      @RequestParam(name = "page.start", defaultValue = "0") int pageStart,
      @RequestParam(name = "page.count", defaultValue = "5") int pageCount, Model model) {
    if (!model.containsAttribute("category")) {
      model.addAttribute(new Category());
    }
    Page page = new Page(pageStart, pageCount);
    page.setTotal(categoryDao.getCategoryCount());
    model.addAttribute(categoryDao.listCategoryByPage(pageStart, pageCount));
    model.addAttribute(page);
    return "backend/listCategory";
  }

  @PostMapping("/categories")
  public String saveCategory(
      @Valid Category category, BindingResult result,
      @RequestPart(name = "category_image") MultipartFile categoryImage,
      RedirectAttributes redirectAttributes, Model model) throws IOException {
    if (categoryImage.isEmpty()) {
      result.addError(new FieldError("category", "category_image", "分类图片不能为空"));
    }
    if (result.hasErrors()) {
      return showCategoryList(0, 5, model);
    }
    categoryDao.saveCategory(category);
    saveCategoryImage(category, categoryImage);
    redirectAttributes.addFlashAttribute("success", Boolean.TRUE);
    return "redirect:/backend/categories";
  }


  @GetMapping("/category/{categoryId}")
  public String showCategory(@PathVariable("categoryId") int id, Model model) {
    model.addAttribute("category", categoryDao.getCategoryById(id));
    return "backend/editCategory";
  }

  @PostMapping("/category/{categoryId}")
  public String updateCategory(
      @Valid Category category, BindingResult result,
      @RequestPart(name = "category_image", required = false) MultipartFile categoryImage,
      RedirectAttributes redirectAttributes)
      throws IOException {
    if (result.hasErrors()) {
      return "backend/editCategory";
    }
    categoryDao.updateCategory(category);
    if (!categoryImage.isEmpty()) {
      saveCategoryImage(category, categoryImage);
    }
    redirectAttributes.addFlashAttribute("success", Boolean.TRUE);
    return "redirect:/backend/categories";
  }

  @GetMapping("/category/{categoryId}/delete")
  public String deleteCategory(@PathVariable("categoryId") int categoryId,
      RedirectAttributes redirectAttributes) throws IOException {
    categoryDao.deleteCategory(categoryId);
    deleteCategoryImage(categoryId);
    redirectAttributes.addFlashAttribute("success", Boolean.TRUE);
    return "redirect:/backend/categories";
  }

  private void saveCategoryImage(Category category, MultipartFile categoryImage)
      throws IOException {
    String imgPath =
        servletContext.getRealPath("img/category") + File.separator + category.getId() + ".jpg";
    File imgFile = new File(imgPath);
    categoryImage.transferTo(imgFile);
    //统一将图片转换为jpg格式
    BufferedImage img = ImageUtil.change2jpg(imgFile);
    ImageIO.write(img, "jpg", imgFile);
  }

  private void deleteCategoryImage(int categoryId) throws IOException {
    String imgPath =
        servletContext.getRealPath("img/category") + File.separator + categoryId + ".jpg";
    File imgFile = new File(imgPath);
    imgFile.getParentFile().mkdirs();
    if (imgFile.exists()) {
      imgFile.delete();
    }
  }

}
