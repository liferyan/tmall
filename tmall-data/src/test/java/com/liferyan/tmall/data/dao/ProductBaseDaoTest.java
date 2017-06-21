package com.liferyan.tmall.data.dao;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.Property;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ryan on 2017/5/26.
 */
public class ProductBaseDaoTest extends BaseDaoTest {

  @Autowired
  private ProductDao productDao;

  @Autowired
  private CategoryDao categoryDao;

  private int count;
  private Product product = new Product();
  private int categoryId;

  @Before
  public void before() throws Exception {
    List<Category> categoryList = categoryDao.listCategory();
    assertThat("分类数为0", categoryList.size(), greaterThan(0));
    categoryId = categoryList.iterator().next().getId();
    count = productDao.getProductCountByCategory(categoryId);
  }

  @After
  public void tearDown() throws Exception {
    assertThat(productDao.getProductCountByCategory(categoryId), is(count));
  }

  @Test
  public void crudProduct() throws Exception {
    int id;
    Category category = new Category();
    category.setId(categoryId);
    product.setCategory(category);
    product.setSubTitle("subtitle");
    product.setOriginalPrice(123.4f);
    product.setPromotePrice(100.0f);
    product.setStock(20);
    product.setCreateDate(new Date());
    product.setName("name");
    productDao.saveProduct(product);
    id = product.getId();
    assertThat(id, not(0));
    product.setName("new_name");
    product.setPromotePrice(50.0f);
    productDao.updateProduct(product);
    assertThat(product.getName(), equalTo("new_name"));
    assertThat((double) (product.getPromotePrice()), closeTo(50.0, 1.0));
    productDao.deleteProduct(id);
    product = productDao.getProductById(id);
    assertThat(product, nullValue());
  }

  @Test
  public void listProduct() throws Exception {
    List<Product> productList = productDao.listProductByCategory(categoryId);
    int size = productDao.getProductCountByCategory(categoryId);
    assertThat(size, is(productList.size()));
    List<Product> products = productDao.listProductByPage(categoryId, 0, 3);
    assertThat(products.size(), is(3));
    List<Product> searchedProducts = productDao.searchProduct("车", 0, 10);
    assertThat(searchedProducts.size(), greaterThan(3));
    List<List<Product>> productsList = new ArrayList<>();
    productsList.add(productList);
    //productsList.add(products);
    for (List<Product> productsCollection : productsList) {
      for (Product product : productsCollection) {
        assertThat(product.getFirstProductImage(), notNullValue());
        assertThat(product.getFirstProductImage().getProduct().getId(), not(0));
        assertThat(product.getFirstProductImage().getImageType(), notNullValue());
        assertThat(product.getSingleProductImageList(), notNullValue());
        assertThat(product.getDetailProductImageList(), notNullValue());
        assertThat(product.getCategory(), notNullValue());
        assertThat(product.getCategory().getId(), not(0));
        assertThat(product.getCategory().getName(), notNullValue());
        assertThat(product.getCategory().getProperties(), notNullValue());
        assertThat(product.getCategory().getProperties().size(), greaterThan(0));
        for (Property property : product.getCategory().getProperties()) {
          assertThat(property.getId(), not(0));
          assertThat(property.getName(), notNullValue());
        }
        assertThat(product.getName(), notNullValue());
        assertThat(product.getSubTitle(), notNullValue());
        assertThat(product.getOriginalPrice(), notNullValue());
        assertThat(product.getPromotePrice(), notNullValue());
        assertThat(product.getStock(), notNullValue());
        assertThat(product.getCreateDate(), notNullValue());
      }
    }
  }
}