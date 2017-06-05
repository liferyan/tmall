package com.liferyan.tmall.web.servlet;

import com.liferyan.tmall.data.dao.DaoFactory;
import com.liferyan.tmall.data.dao.PropertyDao;
import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.data.entity.Property;
import com.liferyan.tmall.web.util.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ryan on 2017/5/5.
 * 属性管理Servlet
 */
public class PropertyServlet extends BaseBackServlet {

  private PropertyDao dao;

  public PropertyServlet() {
    dao = DaoFactory.getPropertyDao();
  }

  @Override
  public String add(HttpServletRequest request) {
    int cid = Integer.parseInt(request.getParameter("cid"));
    Category category = DaoFactory.getCategoryDao().getCategoryById(cid);
    String propertyName = request.getParameter("name");
    Property property = new Property();
    property.setName(propertyName);
    property.setCategory(category);
    dao.saveProperty(property);
    return "@admin_property_list?cid=" + cid;
  }

  @Override
  public String delete(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    int cid = dao.getPropertyById(id).getCategory().getId();
    dao.deleteProperty(id);
    return "@admin_property_list?cid=" + cid;
  }

  @Override
  public String update(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    int cid = Integer.parseInt(request.getParameter("cid"));
    Property property = dao.getPropertyById(id);
    String propertyName = request.getParameter("name");
    if (propertyName != null && !propertyName.equals(property.getName())) {
      property.setName(propertyName);
      dao.updateProperty(property);
    }
    return "@admin_property_list?cid=" + cid;
  }

  @Override
  public String list(HttpServletRequest request, Page page) {
    int cid = Integer.parseInt(request.getParameter("cid"));
    List<Property> propertyList = dao.listPropertyByPage(cid, page.getStart(), page.getCount());
    if (propertyList.size() == 0) {
      return "error.jsp";
    }
    request.setAttribute("category", propertyList.get(0).getCategory());
    request.setAttribute("property_list", propertyList);
    page.setTotal(dao.getPropertyCount(cid));
    page.setParam("&cid=" + cid);
    request.setAttribute("page", page);
    return "admin/listProperty.jsp";
  }

  @Override
  public String edit(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    Property property = dao.getPropertyById(id);
    request.setAttribute("property", property);
    return "admin/editProperty.jsp";
  }
}
