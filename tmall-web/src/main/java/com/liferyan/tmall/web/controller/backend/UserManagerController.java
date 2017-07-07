package com.liferyan.tmall.web.controller.backend;

import com.liferyan.tmall.data.dao.UserDao;
import com.liferyan.tmall.web.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Ryan on 2017/7/4.
 */
@Controller
@RequestMapping("/backend")
public class UserManagerController {

  private UserDao userDao;

  @Autowired
  public UserManagerController(UserDao userDao) {
    this.userDao = userDao;
  }

  @GetMapping("/users")
  public String showUserList(
      @RequestParam(name = "page.start", defaultValue = "0") int pageStart,
      @RequestParam(name = "page.count", defaultValue = "5") int pageCount, Model model) {
    if (pageCount == 5) {
      pageCount = 15;
    }
    Page page = new Page(pageStart, pageCount);
    page.setTotal(userDao.getUserCount());
    model.addAttribute(userDao.listUserByPage(pageStart, pageCount));
    model.addAttribute(page);
    return "backend/listUser";
  }
}
