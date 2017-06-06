package com.liferyan.tmall.web.servlet;

import com.liferyan.tmall.data.dao.DaoFactory;
import com.liferyan.tmall.data.dao.ProductDao;
import com.liferyan.tmall.data.dao.PropertyValueDao;
import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.PropertyValue;
import com.liferyan.tmall.web.util.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ryan on 2017/5/5.
 * 产品管理Servlet
 */
public class ProductServlet extends BaseBackServlet {

  private ProductDao dao;

  public ProductServlet() {
    dao = DaoFactory.getProductDao();
  }

  @Override
  public String add(HttpServletRequest request) {
    int cid = Integer.parseInt(request.getParameter("cid"));
    Product product = new Product();
    product.setName(request.getParameter("name"));
    product.setSubTitle(request.getParameter("subtitle"));
    product.setOriginalPrice(Float.parseFloat(request.getParameter("original_price")));
    product.setPromotePrice(Float.parseFloat(request.getParameter("promote_price")));
    product.setStock(Integer.parseInt(request.getParameter("stock")));
    product.setCategory(DaoFactory.getCategoryDao().getCategoryById(cid));
    product.setCreateDate(new Date());
    logger.info("添加产品：{}", product);
    dao.saveProduct(product);
    return "@admin_product_list?cid=" + cid;
  }

  @Override
  public String delete(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    int cid = dao.getProductById(id).getCategory().getId();
    dao.deleteProduct(id);
    return "@admin_product_list?cid=" + cid;
  }

  @Override
  public String update(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    int cid = Integer.parseInt(request.getParameter("cid"));
    Product product = dao.getProductById(id);
    product.setName(request.getParameter("name"));
    product.setSubTitle(request.getParameter("subtitle"));
    product.setOriginalPrice(Float.parseFloat(request.getParameter("original_price")));
    product.setPromotePrice(Float.parseFloat(request.getParameter("promote_price")));
    product.setStock(Integer.parseInt(request.getParameter("stock")));
    logger.info("修改产品：{}", product);
    dao.updateProduct(product);
    return "@admin_product_list?cid=" + cid;
  }

  @Override
  public String list(HttpServletRequest request, Page page) {
    int cid = Integer.parseInt(request.getParameter("cid"));
    page.setParam("&cid=" + cid);
    page.setTotal(dao.getProductCountByCategory(cid));
    request.setAttribute("page", page);
    List<Product> productList = dao.listProductByPage(cid, page.getStart(), page.getCount());
    if (productList.size() == 0) {
      logger.error("分类下没有产品！");
      return "error.jsp";
    }
    request.setAttribute("category", productList.get(0).getCategory());
    request.setAttribute("product_list", productList);
    return "admin/listProduct.jsp";
  }

  @Override
  public String edit(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    Product product = dao.getProductById(id);
    request.setAttribute("product", product);
    return "admin/editProduct.jsp";
  }

  /**
   * 设置产品属性值
   */
  public String editPropertyValue(HttpServletRequest request) {
    //产品ID
    int pid = Integer.parseInt(request.getParameter("pid"));
    Product product = DaoFactory.getProductDao().getProductById(pid);
    Category category = product.getCategory();
    //属性值列表
    PropertyValueDao propertyValueDao = DaoFactory.getPropertyValueDao();
    //初始化属性值
    propertyValueDao.initPropertyValueWithProduct(product);
    List<PropertyValue> propertyValueList = propertyValueDao.listPropertyValue(pid);
    request.setAttribute("category", category);
    request.setAttribute("product", product);
    request.setAttribute("property_value_list", propertyValueList);
    return "admin/editPropertyValue.jsp";
  }

  /**
   * 更新属性值
   */
  public String updatePropertyValue(HttpServletRequest request) {
    int propertyValueId = Integer.parseInt(request.getParameter("pvid"));
    String value = request.getParameter("value");
    PropertyValue propertyValue = new PropertyValue();
    propertyValue.setId(propertyValueId);
    propertyValue.setValue(value);
    logger.info("修改属性值：{}", propertyValue);
    DaoFactory.getPropertyValueDao().updatePropertyValue(propertyValue);
    return "%success";
  }
}
