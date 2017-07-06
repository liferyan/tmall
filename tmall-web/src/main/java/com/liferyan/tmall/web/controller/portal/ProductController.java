package com.liferyan.tmall.web.controller.portal;

import com.liferyan.tmall.data.dao.ProductDao;
import com.liferyan.tmall.data.dao.PropertyValueDao;
import com.liferyan.tmall.data.dao.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Ryan on 2017/7/4.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

  private ProductDao productDao;

  private PropertyValueDao propertyValueDao;

  private ReviewDao reviewDao;

  @Autowired
  public ProductController(ProductDao productDao,
      PropertyValueDao propertyValueDao, ReviewDao reviewDao) {
    this.productDao = productDao;
    this.propertyValueDao = propertyValueDao;
    this.reviewDao = reviewDao;
  }

  @GetMapping("/{productId}")
  public String showProductPage(@PathVariable("productId") int productId, Model model) {
    model.addAttribute(reviewDao.listProductReview(productId));
    model.addAttribute(propertyValueDao.listPropertyValue(productId));
    model.addAttribute(productDao.getProductById(productId));
    return "product";
  }

  @PostMapping("/search")
  public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
    model.addAttribute(productDao.searchProduct(keyword, 0, 20));
    return "searchResult";
  }
}
