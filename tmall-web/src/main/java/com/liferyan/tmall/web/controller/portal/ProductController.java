package com.liferyan.tmall.web.controller.portal;

import com.liferyan.tmall.data.dao.ProductDao;
import com.liferyan.tmall.data.dao.PropertyValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ryan on 2017/7/4.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

  private ProductDao productDao;

  private PropertyValueDao propertyValueDao;

  @Autowired
  public ProductController(ProductDao productDao,
      PropertyValueDao propertyValueDao) {
    this.productDao = productDao;
    this.propertyValueDao = propertyValueDao;
  }

  @GetMapping("/{productId}")
  public String showProductPage(@PathVariable("productId") int productId, Model model) {
    model.addAttribute(propertyValueDao.listPropertyValue(productId));
    model.addAttribute(productDao.getProductById(productId));
    return "product";
  }
}
