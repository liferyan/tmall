package com.liferyan.spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ryan on 2017/6/19.
 */
@Controller
@RequestMapping({"/","/homepage"})
public class HomeController {

  @RequestMapping(method = GET)
  public String home() {
    return "home";
  }

}
