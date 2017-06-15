package com.liferyan.tmall.data.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import com.liferyan.tmall.data.DaoTestSuite;
import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.data.entity.Property;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ryan on 2017/5/23.
 */
public class PropertyDaoTest extends DaoTestSuite {

  @Autowired
  private PropertyDao propertyDao;

  @Autowired
  private CategoryDao categoryDao;

  private int count;
  private Property property = new Property();
  private int categoryId;

  @Before
  public void before() throws Exception {
    List<Category> categoryList = categoryDao.listCategory();
    assertThat("分类数为0", categoryList.size(), greaterThan(0));
    categoryId = categoryList.iterator().next().getId();
    count = propertyDao.getPropertyCount(categoryId);
  }

  @After
  public void tearDown() throws Exception {
    assertThat(propertyDao.getPropertyCount(categoryId), is(count));
  }

  /*
    1.no Category
    2.Category no id
    3.Category have error id
    4.no name
    5.success
     */
  @Test
  public void crudProperty() throws Exception {
    int id;
    property.setName("123");
    Category category = new Category();
    category.setId(categoryId);
    property.setCategory(category);
    propertyDao.saveProperty(property);
    id = property.getId();
    assertThat(id, not(0));

    property = propertyDao.getPropertyById(id);
    assertThat(property, notNullValue());
    property.setName("456");
    propertyDao.updateProperty(property);
    assertThat(property.getName(), equalTo("456"));
    propertyDao.deleteProperty(id);
    property = propertyDao.getPropertyById(id);
    assertThat(property, nullValue());
  }

  @Test
  public void listProperty() throws Exception {
    List<Property> propertyList = propertyDao.listProperty(categoryId);
    int size = propertyDao.getPropertyCount(categoryId);
    assertThat(size, is(propertyList.size()));
    List<Property> properties = propertyDao.listPropertyByPage(categoryId, 0, 10);
    assertThat(properties.size(), is(10));
    List<List<Property>> propertiesList = new ArrayList<>();
    propertiesList.add(propertyList);
    propertiesList.add(properties);
    for (List<Property> propertyCollection : propertiesList) {
      for (Property property : propertyCollection) {
        assertThat(property.getName(), notNullValue());
        assertThat(property.getCategory(), notNullValue());
        assertThat(property.getCategory().getId(), not(0));
        assertThat(property.getCategory().getName(), notNullValue());
      }
    }
  }
}