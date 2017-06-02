package com.liferyan.tmall.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ryan on 2017/4/27.
 * 字符转码过滤器(将POST表单的数据转换为指定编码)
 */
public class EncodingFilter implements Filter {

  private String encodeCharset;

  public void init(FilterConfig config) throws ServletException {
    encodeCharset = config.getInitParameter("charset");
  }

  public void doFilter(ServletRequest req, ServletResponse resp,
      FilterChain chain) throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;
    //对POST表单中的数据用指定的编码
    request.setCharacterEncoding(encodeCharset);
    chain.doFilter(request, response);
  }

  public void destroy() {
  }

}
