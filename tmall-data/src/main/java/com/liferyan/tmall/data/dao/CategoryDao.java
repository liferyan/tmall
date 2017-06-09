package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.data.entity.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by Ryan on 2017/4/18.
 */
public class CategoryDao extends BaseDao {

  private static final int PRODUCT_NUMBER_EACH_ROW = 2;

  public void saveCategory(Category category) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "saveCategory";
      session.insert(statement, category);
    } catch (Exception e) {
      logger.error("保存分类异常：", e);
    }
  }

  public void deleteCategory(int id) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "deleteCategory";
      session.delete(statement, id);
    } catch (Exception e) {
      logger.error("删除分类异常：", e);
    }
  }

  public void updateCategory(Category category) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "updateCategory";
      session.update(statement, category);
    } catch (Exception e) {
      logger.error("更新分类异常：", e);
    }
  }

  public Category getCategoryById(int id) {
    Category category = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getCategoryById";
      category = session.selectOne(statement, id);
      setProductsByRow(category);
    } catch (Exception e) {
      logger.error("获取分类异常：", e);
    }
    return category;
  }

  public List<Category> listCategoryByPage(int start, int count) {
    List<Category> categoryList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listCategoryByPage";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("start", start);
      parameterMap.put("count", count);
      categoryList = session.selectList(statement, parameterMap);
    } catch (Exception e) {
      logger.error("获取分类异常：", e);
    }
    return categoryList;
  }

  public List<Category> listCategory() {
    List<Category> categoryList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listCategory";
      categoryList = session.selectList(statement);
      for (Category category : categoryList) {
        setProductsByRow(category);
      }
    } catch (Exception e) {
      logger.error("获取分类异常：", e);
    }
    return categoryList;
  }

  public int getCategoryCount() {
    int count = 0;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "getCategoryCount";
      count = session.selectOne(statement);
    } catch (Exception e) {
      logger.error("获取分类总数异常：", e);
    }
    return count;
  }

  private void setProductsByRow(Category category) {
    List<Product> productList = category.getProducts();
    List<List<Product>> productsByRow = new ArrayList<>();
    int fromIndex, toIndex;
    for (fromIndex = 0; fromIndex < productList.size(); fromIndex += PRODUCT_NUMBER_EACH_ROW) {
      toIndex = fromIndex + PRODUCT_NUMBER_EACH_ROW;
      toIndex = toIndex > productList.size() ? productList.size() : toIndex;
      //java.io.NotSerializableException: java.util.ArrayList$SubList
      //List<Product> productsOfEachRow = productList.subList(fromIndex, toIndex);
      ArrayList<Product> productsOfEachRow = new ArrayList<>(
          productList.subList(fromIndex, toIndex));
      productsByRow.add(productsOfEachRow);
    }
    category.setProductsByRow(productsByRow);
  }

}
