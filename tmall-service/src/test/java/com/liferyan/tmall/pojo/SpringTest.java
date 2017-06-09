package com.liferyan.tmall.pojo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.beans.factory.support.AbstractBeanDefinition.AUTOWIRE_NO;

import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.data.entity.Product;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Ryan on 2017/6/7.
 */
public class SpringTest {

  public static final Logger logger = LoggerFactory.getLogger("SpringTest");

  @Test
  public void testApplicationContextUseXML() throws Exception {
    //1.通过读取XML文件 构造Bean并建立其依赖关系
    ApplicationContext container = new ClassPathXmlApplicationContext("services.xml");
    //2.从BeanFactory中获取Bean
    Product product = (Product) container.getBean("product");
    logger.info("Product From Spring：{}", product);
    assertThat(product.getId(), is(100));
    assertThat(product.getName(), equalTo("Nexus"));
    assertThat(product.getCategory(), notNullValue());
    Category category = product.getCategory();
    logger.info("Category From Spring：{}", category);
    assertThat(category.getId(), is(100));
    assertThat(category.getName(), equalTo("婴儿奶粉"));
  }

  @Test
  public void testBeanFactoryUseXML() throws Exception {
    //1.通过读取XML文件 构造Bean并建立其依赖关系
    BeanDefinitionRegistry beanRegistry = new DefaultListableBeanFactory();
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanRegistry);
    reader.loadBeanDefinitions("services.xml");
    //2.从BeanFactory中获取Bean
    Category category = (Category) ((BeanFactory) beanRegistry).getBean("category");
    logger.info("Category From Spring：{}", category);
    assertThat(category.getId(), is(100));
    assertThat(category.getName(), equalTo("婴儿奶粉"));
  }

  @Test
  public void testBeanFactoryUseCode() throws Exception {
    //1.构造Bean 并添加依赖
    AbstractBeanDefinition categoryBean = new RootBeanDefinition(Category.class, AUTOWIRE_NO,
        false);
    MutablePropertyValues propertyValues = new MutablePropertyValues();
    propertyValues.addPropertyValue("id", 200);
    propertyValues.addPropertyValue("name", "智能手机");
    categoryBean.setPropertyValues(propertyValues);
    //2.注册Bean
    BeanDefinitionRegistry beanRegistry = new DefaultListableBeanFactory();
    beanRegistry.registerBeanDefinition("category", categoryBean);
    //3.从BeanFactory中获取Bean
    Category category = (Category) ((BeanFactory) beanRegistry).getBean("category");
    logger.info("Category From Spring：{}", category);
    assertThat(category.getId(), is(200));
    assertThat(category.getName(), equalTo("智能手机"));
  }

}