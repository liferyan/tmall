package com.liferyan.tmall.web.comparator;


import com.liferyan.tmall.data.entity.Product;
import java.util.Comparator;

/**
 * Created by Ryan on 2017/5/9.
 * 人气比较器
 */
public class ProductReviewComparator implements Comparator<Product> {

  @Override
  public int compare(Product o1, Product o2) {
    return o2.getReviewCount() - o1.getReviewCount();
  }
}
