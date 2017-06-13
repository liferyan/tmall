package com.liferyan.tmall.spring.c1.sia.knights;

/**
 * 解救落难姑娘的骑士
 */
public class DamselRescuingKnight implements Knight {

  private RescueDamselQuest quest;
  private Minstrel minstrel;

  /**
   * 通过硬编码的方式建立对象间的依赖关系
   * 使得DamselRescuingKnight强耦合于RescueDamselQuest
   */
  public DamselRescuingKnight() {
    this.quest = new RescueDamselQuest();
    this.minstrel = new Minstrel(System.out);
  }

  public void embarkOnQuest() {
    minstrel.singBeforeQuest();
    quest.embark();
    minstrel.singAfterQuest();
  }

}
