package com.liferyan.tmall.data.utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Ryan on 2017/6/15.
 */
public class EnvMap {
  public static void main (String[] args) {
    Map<String ,String > myEnv = new TreeMap<>();
    myEnv.putAll(System.getenv());
    for (String envName : myEnv.keySet()) {
      System.out.format("%s=%s%n",
          envName,
          myEnv.get(envName));
    }
  }
}
