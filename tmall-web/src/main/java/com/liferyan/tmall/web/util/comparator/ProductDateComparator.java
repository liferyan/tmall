package com.liferyan.tmall.web.util.comparator;


import com.liferyan.tmall.data.entity.Product;
import java.util.Comparator;

/**
 * Created by Ryan on 2017/5/9.
 * 新品比较器
 */
public class ProductDateComparator implements Comparator<Product> {

  @Override
  public int compare(Product o1, Product o2) {
    return o2.getCreateDate().compareTo(o1.getCreateDate());
  }
}
