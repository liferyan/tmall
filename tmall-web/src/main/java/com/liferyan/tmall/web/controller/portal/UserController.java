package com.liferyan.tmall.web.controller.portal;

import com.liferyan.tmall.data.dao.OrderItemDao;
import com.liferyan.tmall.data.dao.UserDao;
import com.liferyan.tmall.data.entity.User;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ryan on 2017/7/4.
 */
@Controller
@RequestMapping("/user")
public class UserController {

  private UserDao userDao;

  private OrderItemDao orderItemDao;

  @Autowired
  public UserController(UserDao userDao, OrderItemDao orderItemDao) {
    this.userDao = userDao;
    this.orderItemDao = orderItemDao;
  }

  @GetMapping("/register")
  public String showRegisterPage(Model model) {
    if (!model.containsAttribute("user")) {
      model.addAttribute(new User());
    }
    return "register";
  }

  @PostMapping("/register")
  public String register(
      @RequestParam("repeatPassword") String repeatPassword,
      @Valid User user, BindingResult result) {
    if (result.hasErrors()) {
      return "register";
    }
    if (StringUtils.isEmpty(repeatPassword) || !repeatPassword.equals(user.getPassword())) {
      result.addError(new FieldError("user", "password", "密码输入不一致!"));
      return "register";
    }
    userDao.saveUser(user);
    return "registerSuccess";
  }

  @GetMapping("/login")
  public String showLoginPage(Model model) {
    if (!model.containsAttribute("user")) {
      model.addAttribute(new User());
    }
    return "login";
  }

  @PostMapping("/login")
  public String login(@Valid User user, BindingResult result, HttpSession session) {
    if (result.hasErrors()) {
      return "login";
    }
    user = userDao.getUserByNameAndPassword(user.getName(), user.getPassword());
    if (user == null) {
      result.addError(new FieldError("user", "name", "用户登陆失败!"));
      return "login";
    }
    int cartItemCount = orderItemDao.listOrderItemInCartByUser(user.getId()).size();
    session.setAttribute("user", user);
    session.setAttribute("cartItemCount", cartItemCount);
    return "redirect:/";
  }

  @GetMapping("/logout")
  public String showlogoutPage(HttpSession session) {
    session.removeAttribute("user");
    return "redirect:/";
  }

  @ResponseBody
  @GetMapping("/checkLoginAjax")
  public String checkLoginAjax(HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user != null) {
      return "success";
    }
    return "fail";
  }

  @ResponseBody
  @GetMapping("/loginAjax")
  public String loginAjax(HttpSession session,
      @RequestParam("name") String name, @RequestParam("password") String password) {
    User user = userDao.getUserByNameAndPassword(name, password);
    if (user != null) {
      int cartItemCount = orderItemDao.listOrderItemInCartByUser(user.getId()).size();
      session.setAttribute("user", user);
      session.setAttribute("cartItemCount", cartItemCount);
      return "success";
    }
    return "fail";
  }
}
