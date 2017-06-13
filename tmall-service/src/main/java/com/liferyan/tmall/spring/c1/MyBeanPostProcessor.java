package com.liferyan.tmall.spring.c1;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by Ryan on 2017/6/12.
 * Bean后处理器
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    System.out.println(bean);
    if (beanName.equals("person")){
      ((Person)bean).setName("Spring");
    }
    System.out
        .println("BeanPostProcessor接口方法postProcessBeforeInitialization对属性进行更改！");
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName)
      throws BeansException {
    System.out.println(bean);
    if (beanName.equals("person")){
      ((Person)bean).setAddress("ShangHai");
    }
    System.out
        .println("BeanPostProcessor接口方法postProcessAfterInitialization对属性进行更改！");
    return bean;
  }
}
