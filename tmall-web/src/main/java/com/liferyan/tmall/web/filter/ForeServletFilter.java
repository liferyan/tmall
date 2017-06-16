package com.liferyan.tmall.web.filter;

import com.liferyan.tmall.data.dao.OrderItemDao;
import com.liferyan.tmall.data.entity.OrderItem;
import com.liferyan.tmall.data.entity.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Created by Ryan on 2017/5/8.
 * 前台过滤器,将内容分发到Servlet
 * 如果访问/mall/forehome
 * 则通过反射调用ForeServlet.home()
 */
public class ForeServletFilter implements Filter {

  @Autowired
  private OrderItemDao orderItemDao;

  public void init(FilterConfig config) throws ServletException {
    SpringBeanAutowiringSupport
        .processInjectionBasedOnServletContext(this, config.getServletContext());
  }

  public void doFilter(ServletRequest req, ServletResponse resp,
      FilterChain chain) throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) req;

    //set cartItemCount 购物车数量
    int cartItemCount = 0;
    User user = (User) request.getSession().getAttribute("user");
    if (null != user) {
      List<OrderItem> orderItemList = orderItemDao.listOrderItemInCartByUser(user.getId());
      cartItemCount = orderItemList.size();
    }
    request.setAttribute("cartItemCount", cartItemCount);

    //set contextPath to applicationScope
    String contextPath = request.getServletContext().getContextPath();
    request.getServletContext().setAttribute("contextPath", contextPath);
    String servletUri = StringUtils.remove(request.getRequestURI(), contextPath);
    if (servletUri.startsWith("/fore") && !servletUri.startsWith("/foreServlet")) {
      String servletMethod = StringUtils.substringAfter(servletUri, "fore");
      request.setAttribute("method", servletMethod);
      request.getRequestDispatcher("/foreServlet").forward(req, resp);
      return;
    }
    chain.doFilter(req, resp);
  }

  public void destroy() {
  }

}
