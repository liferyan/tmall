package com.liferyan.tmall.spring.c2.soundsystem;

import org.springframework.stereotype.Component;

/**
 * Created by Ryan on 2017/6/12.
 */
@Component
public class SgtPeppers implements CompactDisc {

  private String title = "《痴心绝对》";
  private String artist = "李圣杰";

  @Override
  public void play() {
    System.out.println("Playing " + title + " By " + artist);
  }
}
