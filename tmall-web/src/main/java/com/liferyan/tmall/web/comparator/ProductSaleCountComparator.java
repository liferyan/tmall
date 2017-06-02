package com.liferyan.tmall.web.comparator;


import com.liferyan.tmall.data.entity.Product;
import java.util.Comparator;

/**
 * Created by Ryan on 2017/5/9.
 * 销量比较器
 */
public class ProductSaleCountComparator implements Comparator<Product> {

  @Override
  public int compare(Product o1, Product o2) {
    return o2.getSaleCount() - o1.getSaleCount();
  }
}
