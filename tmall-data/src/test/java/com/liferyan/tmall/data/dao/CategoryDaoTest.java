package com.liferyan.tmall.data.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import com.liferyan.tmall.data.DaoTestSuite;
import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.data.entity.Product;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ryan on 2017/5/23.
 */
public class CategoryDaoTest extends DaoTestSuite {

  @Autowired
  private CategoryDao categoryDao;

  private int count;
  private Category category = new Category();

  @Before
  public void setUp() throws Exception {
    count = categoryDao.getCategoryCount();
  }

  @After
  public void tearDown() throws Exception {
    assertThat(categoryDao.getCategoryCount(), is(count));
  }

  @Test
  public void crudCategory() throws Exception {
    int id;
    category.setId(0);
    category.setName("123");
    categoryDao.saveCategory(category);
    id = category.getId();
    assertThat(id, not(0));
    category.setName("abc");
    categoryDao.updateCategory(category);
    category = categoryDao.getCategoryById(id);
    assertThat(category, notNullValue());
    assertThat(category.getName(), equalTo("abc"));
    categoryDao.deleteCategory(id);
    category = categoryDao.getCategoryById(id);
    assertThat(category, nullValue());
  }


  @Test
  public void listCategory() throws Exception {
    List<Category> categoryList = categoryDao.listCategory();
    int size = categoryDao.getCategoryCount();
    assertThat(size, is(categoryList.size()));
    List<Category> categories = categoryDao.listCategoryByPage(0, 3);
    assertThat(categories.size(), is(3));
    List<List<Category>> categoriesList = new ArrayList<>();
    categoriesList.add(categoryList);
    categoriesList.add(categories);
    for (List<Category> categoryCollection : categoriesList) {
      for (Category category : categoryCollection) {
        assertThat(category.getName(), notNullValue());
        List<Product> categoryProducts = category.getProducts();
        if (categoryProducts != null) {
          assertThat(category.getProducts().size(), greaterThan(0));
          List<List<Product>> listProductsByRow = category.getProductsByRow();
          assertThat(listProductsByRow, notNullValue());
          assertThat(category.getProductsByRow().size(), greaterThan(0));
          for (List<Product> products : listProductsByRow) {
            int rowSize = products.size();
            assertThat(rowSize, lessThanOrEqualTo(CategoryDao.PRODUCT_NUMBER_EACH_ROW));
          }
          /*List<Product> productList = category.getProducts();
          for (Product product : productList) {
            assertThat(product.getOriginalPrice(), not(0));
            assertThat(product.getPromotePrice(), not(0));
          }*/
        }
      }
    }
  }

}