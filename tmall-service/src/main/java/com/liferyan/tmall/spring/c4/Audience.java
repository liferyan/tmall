package com.liferyan.tmall.spring.c4;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by Ryan on 2017/6/16.
 * 观众 切面类
 */
@Aspect
public class Audience {

  /*@Before("com.liferyan.tmall.spring.c4.PerformancePointCut.performance()")
  public void silenceCellPhones() {
    System.out.println("手机调至静音状态");
  }

  @Before("com.liferyan.tmall.spring.c4.PerformancePointCut.performance()")
  public void takeSeats() {
    System.out.println("入座");
  }

  @AfterReturning("com.liferyan.tmall.spring.c4.PerformancePointCut.performance()")
  public void applause() {
    System.out.println("表演精彩，鼓掌");
  }

  @AfterThrowing("com.liferyan.tmall.spring.c4.PerformancePointCut.performance()")
  public void demandRefund() {
    System.out.println("表演失败，要求退门票");
  }*/

  @Around("com.liferyan.tmall.spring.c4.PerformancePointCut.performance()")
  public void watchPerformance(ProceedingJoinPoint joinPoint){
    try{
      System.out.println("手机调至静音状态");
      System.out.println("入座");
      joinPoint.proceed();
      System.out.println("表演精彩，鼓掌");
    }catch (Throwable e){
      System.out.println("表演失败，要求退门票");
    }
  }

}
