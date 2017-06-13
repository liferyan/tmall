package com.liferyan.tmall.spring.c1.sia.knights.config;

import com.liferyan.tmall.spring.c1.sia.knights.BraveKnight;
import com.liferyan.tmall.spring.c1.sia.knights.Knight;
import com.liferyan.tmall.spring.c1.sia.knights.Quest;
import com.liferyan.tmall.spring.c1.sia.knights.SlayDragonQuest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用Java-based configuration配置Spring
 */
@Configuration
public class KnightConfig {

  @Bean
  public Knight knight() {
    return new BraveKnight(quest());
  }

  @Bean
  public Quest quest() {
    return new SlayDragonQuest(System.out);
  }

}
