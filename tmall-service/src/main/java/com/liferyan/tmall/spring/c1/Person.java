package com.liferyan.tmall.spring.c1;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Ryan on 2017/6/12.
 */
public class Person implements BeanNameAware, BeanFactoryAware, ApplicationContextAware,
    InitializingBean, DisposableBean {

  private String name;
  private String address;
  private int phone;

  private BeanFactory beanFactory;
  private String beanName;
  private ApplicationContext applicationContext;

  public Person() {
    System.out.println("【构造器】调用Person的构造器实例化");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    System.out.println("【注入属性】注入属性name");
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    System.out.println("【注入属性】注入属性address");
    this.address = address;
  }

  public int getPhone() {
    return phone;
  }

  public void setPhone(int phone) {
    System.out.println("【注入属性】注入属性phone");
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", address='" + address + '\'' +
        ", phone=" + phone +
        ", beanFactory=" + beanFactory +
        ", beanName='" + beanName + '\'' +
        '}';
  }

  // 这是BeanNameAware接口方法
  @Override
  public void setBeanName(String beanName) {
    System.out.println("【BeanNameAware接口】调用BeanNameAware.setBeanName()");
    this.beanName = beanName;
  }

  // 这是BeanFactoryAware接口方法
  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    System.out
        .println("【BeanFactoryAware接口】调用BeanFactoryAware.setBeanFactory()");
    this.beanFactory = beanFactory;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    System.out
        .println("【ApplicationContextAware接口】调用ApplicationContextAware.setApplicationContext()");
    this.applicationContext = applicationContext;
  }

  // 这是InitializingBean接口方法
  @Override
  public void afterPropertiesSet() throws Exception {
    System.out
        .println("【InitializingBean接口】调用InitializingBean.afterPropertiesSet()");
  }

  // 通过<bean>的init-method属性指定的初始化方法
  public void myInit() {
    System.out.println("【init-method】调用<bean>的init-method属性指定的初始化方法");
  }

  // 这是DiposibleBean接口方法
  @Override
  public void destroy() throws Exception {
    System.out.println("【DisposableBean接口】调用DisposableBean.destory()");
  }

  // 通过<bean>的destroy-method属性指定的初始化方法
  public void myDestory() {
    System.out.println("【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
  }

}
