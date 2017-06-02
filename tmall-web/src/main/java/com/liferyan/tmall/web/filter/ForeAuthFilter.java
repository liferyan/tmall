package com.liferyan.tmall.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Ryan on 2017/5/10.
 * 前台登陆认证过滤器
 */
public class ForeAuthFilter implements Filter {

  private List<String> noNeedAuthPage;

  public void init(FilterConfig config) throws ServletException {
    noNeedAuthPage = Arrays.asList(config.getInitParameter("noNeedAuthPage").split("\\|"));
  }

  public void doFilter(ServletRequest req, ServletResponse resp,
      FilterChain chain) throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;
    String contextPath = request.getServletContext().getContextPath();
    String servletUri = StringUtils.remove(request.getRequestURI(), contextPath);
    if (servletUri.startsWith("/fore") && !servletUri.startsWith("/foreServlet")) {
      String servletMethod = StringUtils.substringAfter(servletUri, "fore");
      if (noNeedAuthPage != null && !noNeedAuthPage.contains(servletMethod)) {
        if (null == request.getSession().getAttribute("user")) {
          request.getSession().setAttribute("to_see_page", request.getRequestURI());
          response.sendRedirect("login.jsp");
          return;
        }
      }
    }
    chain.doFilter(req, resp);
  }

  public void destroy() {
  }
}
