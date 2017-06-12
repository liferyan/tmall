package sia.knights;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sia.knights.config.KnightConfig;

public class KnightMain {

  public static void main(String[] args) throws Exception {
    useSpringWithXml();
  }

  private static void noSpring() {
    Knight knight = new DamselRescuingKnight();
    knight.embarkOnQuest();
  }

  private static void useSpringWithXml() {
    ClassPathXmlApplicationContext context =
        new ClassPathXmlApplicationContext("META-INF/spring/knight.xml");
    Knight knight = context.getBean(Knight.class);
    knight.embarkOnQuest();
    context.close();
  }

  private static void useSpringWithJavaConfig() {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(KnightConfig.class);
    Knight knight = context.getBean(Knight.class);
    knight.embarkOnQuest();
    context.close();
  }
}
