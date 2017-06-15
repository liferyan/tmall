package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.data.entity.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Created by Ryan on 2017/4/18.
 */
@Component
public class CategoryDao extends BaseDao {

  public static final int PRODUCT_NUMBER_EACH_ROW = 2;

  public void saveCategory(Category category) {
    this.getSqlSession().insert("saveCategory", category);
  }

  public void deleteCategory(int id) {
    this.getSqlSession().delete("deleteCategory", id);
  }

  public void updateCategory(Category category) {
    this.getSqlSession().update("updateCategory", category);
  }

  public Category getCategoryById(int id) {
    Category category = this.getSqlSession().selectOne("getCategoryById", id);
    if (category != null) {
      setProductsByRow(category);
    }
    return category;
  }

  public List<Category> listCategoryByPage(int start, int count) {
    Map<String, Object> parameterMap = new HashMap<>();
    parameterMap.put("start", start);
    parameterMap.put("count", count);
    return this.getSqlSession().selectList("listCategoryByPage", parameterMap);
  }

  public List<Category> listCategory() {
    String statement = "listCategory";
    List<Category> categoryList = this.getSqlSession().selectList(statement);
    for (Category category : categoryList) {
      setProductsByRow(category);
    }
    return categoryList;
  }

  public int getCategoryCount() {
    return this.getSqlSession().selectOne("getCategoryCount");
  }

  private void setProductsByRow(Category category) {
    List<Product> productList = category.getProducts();
    List<List<Product>> productsByRow = new ArrayList<>();
    int fromIndex, toIndex;
    for (fromIndex = 0; fromIndex < productList.size(); fromIndex += PRODUCT_NUMBER_EACH_ROW) {
      toIndex = fromIndex + PRODUCT_NUMBER_EACH_ROW;
      toIndex = toIndex > productList.size() ? productList.size() : toIndex;
      //SubList()不能序列化
      //java.io.NotSerializableException: java.util.ArrayList$SubList
      //List<Product> productsOfEachRow = productList.subList(fromIndex, toIndex);
      ArrayList<Product> productsOfEachRow = new ArrayList<>(
          productList.subList(fromIndex, toIndex));
      productsByRow.add(productsOfEachRow);
    }
    category.setProductsByRow(productsByRow);
  }

}
