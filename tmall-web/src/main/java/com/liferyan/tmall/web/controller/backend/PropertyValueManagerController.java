package com.liferyan.tmall.web.controller.backend;

import com.liferyan.tmall.data.dao.ProductDao;
import com.liferyan.tmall.data.dao.PropertyValueDao;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ryan on 2017/6/28.
 */
@Controller
@RequestMapping("/admin")
public class PropertyValueManagerController {

  private PropertyValueDao propertyValueDao;

  private ProductDao productDao;

  @Autowired
  public PropertyValueManagerController(PropertyValueDao propertyValueDao,
      ProductDao productDao) {
    this.propertyValueDao = propertyValueDao;
    this.productDao = productDao;
  }

  @GetMapping("/propertyValues/{productId}")
  public String initAndShowProductPropertyValues(@PathVariable("productId") int productId,
      Model model) {
    Product product = productDao.getProductById(productId);
    propertyValueDao.initPropertyValueWithProduct(product);
    model.addAttribute(product.getCategory());
    model.addAttribute(product);
    model.addAttribute(propertyValueDao.listPropertyValue(productId));
    return "admin/editPropertyValue";
  }

  @ResponseBody
  @PostMapping("/propertyValues/{productId}")
  public String editProductPropertyValue(PropertyValue propertyValue) {
     propertyValueDao.updatePropertyValue(propertyValue);
    return "success";
  }
}
