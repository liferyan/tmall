package com.liferyan.tmall.web.comparator;


import com.liferyan.tmall.data.entity.Product;
import java.util.Comparator;

/**
 * Created by Ryan on 2017/5/9.
 * 价格比较器
 */
public class ProductPriceComparator implements Comparator<Product> {

  @Override
  public int compare(Product o1, Product o2) {
    return Float.compare(o1.getPromotePrice(), o2.getPromotePrice());
  }
}
