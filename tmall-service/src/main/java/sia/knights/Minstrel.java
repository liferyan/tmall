package sia.knights;

import java.io.PrintStream;

/**
 * 吟游诗人
 * 切面类 Aspect
 */
public class Minstrel {

  private PrintStream stream;

  /**
   * 通过Ioc容器注入依赖的对象
   * Minstrel依赖于父类PrintStream
   * 使得Minstrel与PrintStream对象松耦合
   */
  public Minstrel(PrintStream stream) {
    this.stream = stream;
  }

  /**
   * 前置通知
   * before advice
   * 探险之前记载
   */
  public void singBeforeQuest() {
    stream.println("Fa la la, the knight is so brave!");
  }

  /**
   * 后置通知
   * after advice
   * 探险之后记载
   */
  public void singAfterQuest() {
    stream.println("Tee hee hee, the brave knight " +
        "did embark on a quest!");
  }

}
