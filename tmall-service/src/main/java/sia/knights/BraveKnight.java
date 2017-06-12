package sia.knights;

/**
 * 勇敢的骑士
 */
public class BraveKnight implements Knight {

  private Quest quest;

  /**
   * 通过Ioc容器注入依赖的对象
   * BraveKnight依赖于抽象的接口Quest
   * 使得BraveKnight与Quest对象松耦合
   */
  public BraveKnight(Quest quest) {
    this.quest = quest;
  }

  public void embarkOnQuest() {
    quest.embark();
  }

}
