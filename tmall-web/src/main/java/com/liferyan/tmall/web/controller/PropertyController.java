package com.liferyan.tmall.web.controller;

import com.liferyan.tmall.data.dao.CategoryDao;
import com.liferyan.tmall.data.dao.PropertyDao;
import com.liferyan.tmall.data.entity.Property;
import com.liferyan.tmall.web.util.Page;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Ryan on 2017/6/27.
 */
@Controller
@RequestMapping("/admin")
public class PropertyController {

  private CategoryDao categoryDao;

  private PropertyDao propertyDao;

  @Autowired
  public PropertyController(CategoryDao categoryDao,
      PropertyDao propertyDao) {
    this.categoryDao = categoryDao;
    this.propertyDao = propertyDao;
  }

  @GetMapping("/properties/{categoryId}")
  public String showCategoryProperties(
      @RequestParam(name = "page.start", defaultValue = "0") int pageStart,
      @RequestParam(name = "page.count", defaultValue = "5") int pageCount,
      @PathVariable("categoryId") int categoryId, Model model) {
    if (!model.containsAttribute("property")) {
      model.addAttribute(new Property());
    }
    Page page = new Page(pageStart, pageCount);
    page.setTotal(propertyDao.getPropertyCount(categoryId));
    model.addAttribute(categoryDao.getCategoryById(categoryId));
    model.addAttribute(propertyDao.listPropertyByPage(categoryId, pageStart, pageCount));
    model.addAttribute(page);
    return "admin/listProperty";
  }

  @PostMapping("/properties/{categoryId}")
  public String saveProperty(@Valid Property property, BindingResult result,
      @PathVariable("categoryId") int categoryId,
      RedirectAttributes redirectAttributes, Model model) {
    if (result.hasErrors()) {
      return showCategoryProperties(0, 5, categoryId, model);
    }
    propertyDao.saveProperty(property);
    redirectAttributes.addFlashAttribute("success", Boolean.TRUE);
    return "redirect:/admin/properties/{categoryId}";
  }

  @GetMapping("/property/{propertyId}")
  public String showProperty(@PathVariable("propertyId") int propertyId, Model model) {
    model.addAttribute(propertyDao.getPropertyById(propertyId));
    return "admin/editProperty";
  }

  @PostMapping("/property/{propertyId}")
  public String updateProperty(@Valid Property property, BindingResult result,
      RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      return "admin/editProperty";
    }
    int categoryId = property.getCategory().getId();
    propertyDao.updateProperty(property);
    redirectAttributes.addFlashAttribute("success", Boolean.TRUE);
    return "redirect:/admin/properties/" + categoryId;
  }

  @GetMapping("/property/{propertyId}/delete")
  public String deleteProperty(@PathVariable("propertyId") int propertyId,
      RedirectAttributes redirectAttributes) {
    int categoryId = propertyDao.getPropertyById(propertyId).getCategory().getId();
    propertyDao.deleteProperty(propertyId);
    redirectAttributes.addFlashAttribute("success", Boolean.TRUE);
    return "redirect:/admin/properties/" + categoryId;
  }
}
