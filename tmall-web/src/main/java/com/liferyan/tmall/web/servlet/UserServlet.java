package com.liferyan.tmall.web.servlet;

import com.liferyan.tmall.data.dao.DaoFactory;
import com.liferyan.tmall.data.dao.UserDao;
import com.liferyan.tmall.data.entity.User;
import com.liferyan.tmall.web.util.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ryan on 2017/5/6.
 * 用户管理Servlet
 */
public class UserServlet extends BaseBackServlet {

  private UserDao dao;

  public UserServlet() {
    dao = DaoFactory.getUserDao();
  }

  @Override
  public String add(HttpServletRequest request) {
    return null;
  }

  @Override
  public String delete(HttpServletRequest request) {
    return null;
  }

  @Override
  public String update(HttpServletRequest request) {
    return null;
  }

  @Override
  public String list(HttpServletRequest request, Page page) {
    if (page.getCount() == 5) {
      page.setCount(15);
    }
    List<User> userList = dao.listUserByPage(page.getStart(), page.getCount());
    page.setTotal(dao.getUserCount());
    request.setAttribute("user_list", userList);
    request.setAttribute("page", page);
    return "admin/listUser.jsp";
  }

  @Override
  public String edit(HttpServletRequest request) {
    return null;
  }
}
