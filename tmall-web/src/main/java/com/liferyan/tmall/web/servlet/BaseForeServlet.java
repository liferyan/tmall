package com.liferyan.tmall.web.servlet;

import com.liferyan.tmall.data.dao.CategoryDao;
import com.liferyan.tmall.data.dao.OrderDao;
import com.liferyan.tmall.data.dao.OrderItemDao;
import com.liferyan.tmall.data.dao.ProductDao;
import com.liferyan.tmall.data.dao.PropertyValueDao;
import com.liferyan.tmall.data.dao.ReviewDao;
import com.liferyan.tmall.data.dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Created by Ryan on 2017/5/8.
 * 所有后台Servlet的基类
 * 通过反射调用具体方法
 * service()完成两个任务
 * 1.调用对应业务方法
 * 2.跳转
 */
public abstract class BaseForeServlet extends HttpServlet {

  protected static final Logger logger = LoggerFactory.getLogger("ForeServlet");

  protected UserDao userDao;
  protected CategoryDao categoryDao;
  //protected PropertyDao propertyDao = (PropertyDao) ctx.getBean("propertyDao");
  protected ProductDao productDao;
  protected PropertyValueDao propertyValueDao;
  //protected ProductImageDao productImageDao = (ProductImageDao) ctx.getBean("productImageDao");
  protected ReviewDao reviewDao;
  protected OrderDao orderDao;
  protected OrderItemDao orderItemDao;

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    WebApplicationContext ctx = WebApplicationContextUtils
        .getRequiredWebApplicationContext(getServletContext());
    userDao = (UserDao) ctx.getBean("userDao");
    categoryDao = (CategoryDao) ctx.getBean("categoryDao");
    productDao = (ProductDao) ctx.getBean("productDao");
    propertyValueDao = (PropertyValueDao) ctx.getBean("propertyValueDao");
    reviewDao = (ReviewDao) ctx.getBean("reviewDao");
    orderDao = (OrderDao) ctx.getBean("orderDao");
    orderItemDao = (OrderItemDao) ctx.getBean("orderItemDao");

    //1.通过反射调用具体方法
    String redirect;
    try {
      Method servletMethod;
      String methodName = (String) req.getAttribute("method");
      servletMethod = this.getClass()
          .getMethod(methodName, HttpServletRequest.class);
      redirect = (String) servletMethod.invoke(this, req);
      if (redirect == null) {
        logger.error("跳转页面为空！");
        redirect = "error.jsp";
      }
    } catch (Exception ex) {
      logger.error("调用后台Servlet方法异常：{}", ex);
      req.setAttribute("uri", req.getRequestURI());
      req.setAttribute("msg", getStackTrace(ex));
      redirect = "error.jsp";
    }

    //2.跳转
    if (redirect.startsWith("@")) {
      //客户端跳转
      resp.sendRedirect(redirect.substring(1));
    } else if (redirect.startsWith("%")) {
      //输出字符串
      resp.getWriter().print(redirect.substring(1));
    } else {
      //服务端跳转
      req.getRequestDispatcher("/" + redirect).forward(req, resp);
    }
  }

  private static String getStackTrace(Exception e) {
    StringWriter sw = null;
    PrintWriter pw = null;
    try {
      sw = new StringWriter();
      pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      pw.flush();
      sw.flush();
    } finally {
      if (sw != null) {
        try {
          sw.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
      if (pw != null) {
        pw.close();
      }
    }
    return sw.toString();
  }

}
