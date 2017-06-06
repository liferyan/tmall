package com.liferyan.tmall.web.servlet;

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

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
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
