package com.liferyan.tmall.web.controller.portal;

import com.liferyan.tmall.data.dao.CategoryDao;
import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.web.util.comparator.ProductAllComparator;
import com.liferyan.tmall.web.util.comparator.ProductDateComparator;
import com.liferyan.tmall.web.util.comparator.ProductPriceComparator;
import com.liferyan.tmall.web.util.comparator.ProductReviewComparator;
import com.liferyan.tmall.web.util.comparator.ProductSaleCountComparator;
import java.util.Collections;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Ryan on 2017/7/5.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

  private CategoryDao categoryDao;

  @Autowired
  public CategoryController(CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  @GetMapping("/{categoryId}")
  public String showCategoryPage(@PathVariable("categoryId") int categoryId,
      @RequestParam(name = "sort", defaultValue = "all") String sort, Model model) {
    Category category = categoryDao.getCategoryById(categoryId);
    Comparator<Product> comparator = null;
    switch (sort) {
      //人气
      case "review":
        comparator = new ProductReviewComparator();
        break;
      //新品
      case "date":
        comparator = new ProductDateComparator();
        break;
      //销量
      case "saleCount":
        comparator = new ProductSaleCountComparator();
        break;
      //价格
      case "price":
        comparator = new ProductPriceComparator();
        break;
      //综合
      case "all":
        comparator = new ProductAllComparator();
        break;
    }
    Collections.sort(category.getProducts(), comparator);
    model.addAttribute(category);
    return "category";
  }
}
