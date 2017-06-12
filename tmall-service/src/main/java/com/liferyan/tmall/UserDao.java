package com.liferyan.tmall;

import com.liferyan.tmall.data.entity.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by Ryan on 2017/6/12.
 * 使用SpringJdbcTemplate简化JDBC操作
 */
public class UserDao extends JdbcDaoSupport {

  private static final Logger logger = LoggerFactory.getLogger("UserDao");

  private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/mall?characterEncoding=UTF-8&useSSL=false";

  public List<User> listUserUseJdbc(int start, int count) {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      logger.error("JDBC驱动异常：{}", e.getMessage());
    }
    List<User> userList = new ArrayList<>();
    String listSQL = "SELECT * FROM user ORDER BY id LIMIT ? OFFSET ?";
    try (Connection conn = DriverManager.getConnection(DB_URL, "root", "admin");
        PreparedStatement pstmt = conn.prepareStatement(listSQL)) {
      pstmt.setInt(1, count);
      pstmt.setInt(2, start);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        userList.add(user);
      }
    } catch (SQLException e) {
      logger.error("获取用户异常：{}", e);
    }
    return userList;
  }

  public List<User> listUserUseJdbcTemplate(int start, int count) {
    return this.getJdbcTemplate().query("SELECT * FROM user ORDER BY id LIMIT ? OFFSET ?",
        (rs, rowNum) -> {
          User user = new User();
          user.setId(rs.getInt("id"));
          user.setName(rs.getString("name"));
          user.setPassword(rs.getString("password"));
          return user;
        }, count, start);
  }

}
