package com.liferyan.tmall.spring.c1.sia.knights;

import java.io.PrintStream;

/**
 * 杀死巨龙的探险
 */
public class SlayDragonQuest implements Quest {

  private PrintStream stream;

  /**
   * 通过Ioc容器注入依赖的对象
   * SlayDragonQuest依赖于父类PrintStream
   * 使得SlayDragonQuest与PrintStream对象松耦合
   */
  public SlayDragonQuest(PrintStream stream) {
    this.stream = stream;
  }

  public void embark() {
    stream.println("Embarking on quest to slay the dragon!");
  }

}
