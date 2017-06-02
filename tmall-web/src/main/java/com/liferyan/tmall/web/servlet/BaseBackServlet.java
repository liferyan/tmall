package com.liferyan.tmall.web.servlet;

import com.liferyan.tmall.web.util.Page;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Created by Ryan on 2017/4/20.
 * 所有后台Servlet的基类
 * 通过反射调用具体方法
 * service()完成三个任务
 * 1.调用对应业务方法
 * 2.分页
 * 3.跳转
 */
public abstract class BaseBackServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    //1.获取分页信息
    int pageStart;
    int pageCount;
    try {
      pageStart = Integer.parseInt(req.getParameter("page.start"));
    } catch (Exception ex) {
      pageStart = 0;
    }
    try {
      pageCount = Integer.parseInt(req.getParameter("page.count"));
    } catch (Exception ex) {
      pageCount = 5;
    }
    Page page = new Page(pageStart, pageCount);

    //2.通过反射调用具体方法
    String redirect;
    try {
      Method servletMethod;
      String methodName = (String) req.getAttribute("method");
      if ("list".equals(methodName)) {
        servletMethod = this.getClass()
            .getMethod(methodName, HttpServletRequest.class, Page.class);
        redirect = (String) servletMethod.invoke(this, req, page);
      } else {
        servletMethod = this.getClass()
            .getMethod(methodName, HttpServletRequest.class);
        redirect = (String) servletMethod.invoke(this, req);
      }
      if (redirect == null) {
        redirect = "error.jsp";
      }
    } catch (Exception ex) {
      req.setAttribute("uri", req.getRequestURI());
      req.setAttribute("msg", getStackTrace(ex));
      redirect = "error.jsp";
    }

    //3.跳转
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

  /**
   * 解析上传的图片数据
   */
  InputStream parseUpload(HttpServletRequest request, Map<String, String> params)
      throws Exception {
    InputStream inputStream = null;
    DiskFileItemFactory factory = new DiskFileItemFactory(10 * 1024 * 1024,
        (File) getServletContext().getAttribute("javax.servlet.context.tempdir"));
    ServletFileUpload upload = new ServletFileUpload(factory);
    List<FileItem> items = upload.parseRequest(request);
    for (FileItem item : items) {
      if (item.isFormField()) {
        //普通表单域
        params.put(item.getFieldName(), item.getString("UTF-8"));
      } else {
        inputStream = item.getInputStream();
        //没有上传数据
        if (inputStream.available() == 0) {
          inputStream = null;
        }
      }
    }
    return inputStream;
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

  /**
   * 增加
   */

  public abstract String add(HttpServletRequest request);

  /**
   * 删除
   */
  public abstract String delete(HttpServletRequest request);

  /**
   * 修改
   */
  public abstract String update(HttpServletRequest request);

  /**
   * 查询
   * page 分页信息
   */
  public abstract String list(HttpServletRequest request,
      Page page);

  /**
   * 编辑
   */
  public abstract String edit(HttpServletRequest request);
}
