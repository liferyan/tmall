package com.liferyan.tmall.spring.c4;

/**
 * Created by Ryan on 2017/6/16.
 * 电影表演
 */
public class Movie implements Performance {

  @Override
  public void perform() {
    System.out.println("电影表演中...");
    //int i = 1 / 0;  //throw a Exception
  }
}
