package com.liferyan.tmall.web.controller;

import com.liferyan.tmall.data.dao.CategoryDao;
import com.liferyan.tmall.data.dao.ProductDao;
import com.liferyan.tmall.data.entity.Product;
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

/**
 * Created by Ryan on 2017/6/27.
 */
@Controller
@RequestMapping("/admin")
public class ProductController {

  private CategoryDao categoryDao;

  private ProductDao productDao;

  @Autowired
  public ProductController(CategoryDao categoryDao, ProductDao productDao) {
    this.categoryDao = categoryDao;
    this.productDao = productDao;
  }

  @GetMapping("/products/{categoryId}")
  public String showCategoryProducts(
      @RequestParam(name = "page.start", defaultValue = "0") int pageStart,
      @RequestParam(name = "page.count", defaultValue = "5") int pageCount,
      @PathVariable("categoryId") int categoryId, Model model) {
    if (!model.containsAttribute("product")) {
      model.addAttribute(new Product());
    }
    Page page = new Page(pageStart, pageCount);
    page.setTotal(productDao.getProductCountByCategory(categoryId));
    model.addAttribute(categoryDao.getCategoryById(categoryId));
    model.addAttribute(productDao.listProductByPage(categoryId, pageStart, pageCount));
    model.addAttribute(page);
    return "admin/listProduct";
  }

  @PostMapping("/products/{categoryId}")
  public String saveProduct(@Valid Product product, BindingResult result,
      @PathVariable("categoryId") int categoryId, Model model) {
    if (result.hasErrors()) {
      return showCategoryProducts(0, 5, categoryId, model);
    }
    productDao.saveProduct(product);
    return "redirect:/admin/products/{categoryId}";
  }

  @GetMapping("/product/{productId}")
  public String showProduct(@PathVariable("productId") int productId, Model model) {
    model.addAttribute(productDao.getProductById(productId));
    return "admin/editProduct";
  }

  @PostMapping("/product/{productId}")
  public String updateProduct(@Valid Product product, BindingResult result) {
    if (result.hasErrors()) {
      return "admin/editProduct";
    }
    int categoryId = product.getCategory().getId();
    productDao.updateProduct(product);
    return "redirect:/admin/products/" + categoryId;
  }

  @GetMapping("/product/{productId}/delete")
  public String deleteProduct(@PathVariable("productId") int productId) {
    int categoryId = productDao.getProductById(productId).getCategory().getId();
    productDao.deleteProduct(productId);
    return "redirect:/admin/products/" + categoryId;
  }
}
