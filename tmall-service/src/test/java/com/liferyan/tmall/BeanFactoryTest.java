package com.liferyan.tmall;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.beans.factory.support.AbstractBeanDefinition.AUTOWIRE_NO;

import com.liferyan.tmall.data.entity.Category;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Created by Ryan on 2017/6/7.
 */
public class BeanFactoryTest {

  public static final Logger logger = LoggerFactory.getLogger("BeanFactoryTest");

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