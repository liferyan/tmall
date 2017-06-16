package com.liferyan.tmall.spring.c4;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Ryan on 2017/6/16.
 */
@Aspect
public class PerformancePointCut {

  @Pointcut("execution(* *.perform(..))")
  public void performance() {
  }

}
