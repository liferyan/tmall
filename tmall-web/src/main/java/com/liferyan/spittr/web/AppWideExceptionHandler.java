package com.liferyan.spittr.web;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Ryan on 2017/6/22.
 */
@ControllerAdvice
public class AppWideExceptionHandler {

  @ExceptionHandler(DataAccessException.class)
  public String handlerDuplicateSpitter() {
    return "error/data_access_error";
  }

}
