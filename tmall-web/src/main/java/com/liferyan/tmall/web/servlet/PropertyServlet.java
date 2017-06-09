package com.liferyan.tmall.web.servlet;

import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.data.entity.Property;
import com.liferyan.tmall.web.util.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Ryan on 2017/5/5.
 * 属性管理Servlet
 */
public class PropertyServlet extends BaseBackServlet {

  @Override
  public String add(HttpServletRequest request) {
    int cid = Integer.parseInt(request.getParameter("cid"));
    Category category = categoryDao.getCategoryById(cid);
    String propertyName = request.getParameter("name");
    Property property = new Property();
    property.setName(propertyName);
    property.setCategory(category);
    logger.info("添加属性：{}", property);
    propertyDao.saveProperty(property);
    return "@admin_property_list?cid=" + cid;
  }

  @Override
  public String delete(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    int cid = propertyDao.getPropertyById(id).getCategory().getId();
    propertyDao.deleteProperty(id);
    return "@admin_property_list?cid=" + cid;
  }

  @Override
  public String update(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    int cid = Integer.parseInt(request.getParameter("cid"));
    Property property = propertyDao.getPropertyById(id);
    String propertyName = request.getParameter("name");
    if (StringUtils.isEmpty(propertyName) && !propertyName.equals(property.getName())) {
      property.setName(propertyName);
      logger.info("修改属性：{}", property);
      propertyDao.updateProperty(property);
    }
    return "@admin_property_list?cid=" + cid;
  }

  @Override
  public String list(HttpServletRequest request, Page page) {
    int cid = Integer.parseInt(request.getParameter("cid"));
    List<Property> propertyList = propertyDao
        .listPropertyByPage(cid, page.getStart(), page.getCount());
    if (propertyList.size() == 0) {
      logger.error("分类下没有属性！");
      request.setAttribute("msg", "分类下没有属性！");
      return null;
    }
    request.setAttribute("category", propertyList.get(0).getCategory());
    request.setAttribute("property_list", propertyList);
    page.setTotal(propertyDao.getPropertyCount(cid));
    page.setParam("&cid=" + cid);
    request.setAttribute("page", page);
    return "admin/listProperty.jsp";
  }

  @Override
  public String edit(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    Property property = propertyDao.getPropertyById(id);
    request.setAttribute("property", property);
    return "admin/editProperty.jsp";
  }
}
