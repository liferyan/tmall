package com.liferyan.tmall.web.comparator;

import com.liferyan.tmall.data.entity.Product;
import java.util.Comparator;

/**
 * Created by Ryan on 2017/5/9.
 * 综合比较器 销量 + 评价
 */
public class ProductAllComparator implements Comparator<Product> {

  @Override
  public int compare(Product o1, Product o2) {
    int v1, v2;
    v1 = o1.getSaleCount() * o1.getReviewCount();
    v2 = o2.getSaleCount() * o2.getReviewCount();
    return v2 - v1;
  }
}
