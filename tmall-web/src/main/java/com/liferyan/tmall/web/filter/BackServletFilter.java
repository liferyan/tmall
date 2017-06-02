package com.liferyan.tmall.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Ryan on 2017/4/20.
 * 如果访问/mall/admin_category_list
 * 则通过反射调用CategoryServlet.list()方法
 */
public class BackServletFilter implements Filter {

  public void destroy() {
  }

  public void doFilter(ServletRequest req, ServletResponse resp,
      FilterChain chain) throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) req;
    String servletUri = StringUtils.remove(request.getRequestURI(), request.getContextPath());
    if (servletUri.startsWith("/admin_")) {
      String servletName = StringUtils.substringBetween(servletUri, "_", "_") + "Servlet";
      String servletMethod = StringUtils.substringAfterLast(servletUri, "_");
      request.setAttribute("method", servletMethod);
      request.getRequestDispatcher("/" + servletName).forward(req, resp);
      return;
    }
    //非后台页面直接往后传递
    chain.doFilter(req, resp);
  }

  public void init(FilterConfig config) throws ServletException {
  }

}
