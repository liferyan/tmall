package com.liferyan.tmall.data.utils;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created by Ryan on 2017/6/14.
 */
public class TransformMysqlToH2 {

  @Test
  public void test() throws Exception {
    Resource resource = new ClassPathResource("input.sql");
    File file = resource.getFile();
    String content = new Scanner(file).useDelimiter("\\Z").next();

    content = "SET MODE MYSQL;\n\n" + content;

    content = content.replaceAll("`", "");
    content = content.replaceAll("COLLATE.*(?=D)", "");
    content = content.replaceAll("COMMENT.*'(?=,)", "");
    content = content.replaceAll("\\).*ENGINE.*(?=;)", ")");
    content = content.replaceAll("DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
        " AS CURRENT_TIMESTAMP");

    content = uniqueKey(content);

    System.out.println(content);
  }

  /**
   * h2的索引名必须全局唯一
   *
   * @param content sql建表脚本
   * @return 替换索引名为全局唯一
   */
  private static String uniqueKey(String content) {
    int inc = 0;
    Pattern pattern = Pattern.compile("(?<=KEY )(.*?)(?= \\()");
    Matcher matcher = pattern.matcher(content);
    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      matcher.appendReplacement(sb, matcher.group() + inc++);
    }
    matcher.appendTail(sb);
    content = sb.toString();
    return content;
  }

}
