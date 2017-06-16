package com.liferyan.tmall.spring.c4;

import java.io.PrintStream;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by Ryan on 2017/6/16.
 * 服务人员 切面类
 */
@Aspect
public class Worker {

  private PrintStream printStream;

  public Worker(PrintStream printStream) {
    this.printStream = printStream;
  }

  /*@Before("com.liferyan.tmall.spring.c4.PerformancePointCut.performance()")
  public void logBeforePerform() {
    printStream.println("准备表演场地");
  }

  @After("com.liferyan.tmall.spring.c4.PerformancePointCut.performance()")
  public void logAfterPerform() {
    printStream.println("清理表演场地");
  }*/

  @Around("com.liferyan.tmall.spring.c4.PerformancePointCut.performance()")
  public void logPerform(ProceedingJoinPoint joinPoint) {
    try {
      printStream.println("准备表演场地");
      joinPoint.proceed();
      printStream.println("中场休息，重新准备表演场地");
      joinPoint.proceed();
      printStream.println("清理表演场地");
    } catch (Throwable ex) {
      printStream.println("表演失败，立即清理");
    }
  }
}
