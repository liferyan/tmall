package com.liferyan.spittr.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ryan on 2017/6/22.
 */
@ControllerAdvice
public class AppWideExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ModelAndView handlerDataAccessException(Exception ex) {
    ModelAndView mv = new ModelAndView("error");
    mv.addObject("message", ex.getMessage());
    return mv;
  }

}
