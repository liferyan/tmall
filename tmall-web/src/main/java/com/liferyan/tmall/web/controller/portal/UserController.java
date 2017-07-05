package com.liferyan.tmall.web.controller.portal;

import com.liferyan.tmall.data.dao.UserDao;
import com.liferyan.tmall.data.entity.User;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Ryan on 2017/7/4.
 */
@Controller
@RequestMapping("/user")
public class UserController {

  private UserDao userDao;

  public UserController(UserDao userDao) {
    this.userDao = userDao;
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
    return "redirect:/user/login";
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
    session.setAttribute("user", user);
    return "redirect:/";
  }

  @GetMapping("/logout")
  public String showlogoutPage(HttpSession session) {
    session.removeAttribute("user");
    return "redirect:/";
  }
}
