package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.Category;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 */
public interface CategoryDao {

  void saveCategory(Category category);

  void deleteCategory(int id);

  void updateCategory(Category category);

  Category getCategoryById(int id);

  List<Category> listCategoryByPage(int start, int count);

  List<Category> listCategory();

  int getCategoryCount();

}
