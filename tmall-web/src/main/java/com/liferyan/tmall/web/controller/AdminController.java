package com.liferyan.tmall.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Ryan on 2017/6/27.
 */
@Controller
public class AdminController {

  @GetMapping("/admin")
  public String admin() {
    return "redirect:/admin/categories";
  }

}
