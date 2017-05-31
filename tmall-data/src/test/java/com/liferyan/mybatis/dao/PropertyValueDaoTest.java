package com.liferyan.mybatis.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.liferyan.mybatis.entity.Category;
import com.liferyan.mybatis.entity.Product;
import com.liferyan.mybatis.entity.PropertyValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ryan on 2017/5/27.
 */
public class PropertyValueDaoTest {

  private int count;
  private PropertyValueDao propertyValueDao = DaoFactory.getPropertyValueDao();
  private Product product;

  @Before
  public void setUp() throws Exception {
    List<Category> categoryList = DaoFactory.getCategoryDao().listCategory();
    assertThat("分类数为0", categoryList.size(), greaterThan(0));
    int categoryId = categoryList.iterator().next().getId();
    List<Product> productList = DaoFactory.getProductDao().listProductByCategory(categoryId);
    assertThat("产品数为0", productList.size(), greaterThan(0));
    product = productList.iterator().next();
    count = propertyValueDao.listPropertyValue(product.getId()).size();
  }

  @After
  public void tearDown() throws Exception {
    assertThat(propertyValueDao.listPropertyValue(product.getId()).size(), is(count));
  }

  @Test
  public void crudPropertyValue() throws Exception {
    int pid = product.getId();
    propertyValueDao.initPropertyValueWithProduct(product);
    List<PropertyValue> propertyValueList = propertyValueDao.listPropertyValue(pid);
    Map<Integer, String> propertyValueMap = new HashMap<>();
    for (PropertyValue propertyValue : propertyValueList) {
      assertThat(propertyValue.getId(), not(0));
      assertThat(propertyValue.getProperty(), notNullValue());
      assertThat(propertyValue.getProduct(), notNullValue());
      propertyValueMap.put(propertyValue.getId(), propertyValue.getValue());
      propertyValue.setValue("TEST");
      propertyValueDao.updatePropertyValue(propertyValue);
    }
    propertyValueList = propertyValueDao.listPropertyValue(pid);
    for (PropertyValue propertyValue : propertyValueList) {
      assertThat(propertyValue.getValue(), equalTo("TEST"));
    }
    propertyValueList = propertyValueDao.listPropertyValue(pid);
    for (PropertyValue propertyValue : propertyValueList) {
      String value = propertyValueMap.get(propertyValue.getId());
      propertyValue.setValue(value);
      propertyValueDao.updatePropertyValue(propertyValue);
    }
  }

}