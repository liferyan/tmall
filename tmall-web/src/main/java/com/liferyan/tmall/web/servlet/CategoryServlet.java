package com.liferyan.tmall.web.servlet;

import com.liferyan.tmall.data.dao.CategoryDao;
import com.liferyan.tmall.data.dao.DaoFactory;
import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.web.util.ImageUtil;
import com.liferyan.tmall.web.util.Page;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Ryan on 2017/4/20.
 * 产品类别Servlet
 */
public class CategoryServlet extends BaseBackServlet {

  private CategoryDao dao;

  public CategoryServlet() {
    dao = DaoFactory.getCategoryDao();
  }

  /**
   * 1.解析上传的图片数据
   * 2.机上分类信息存入数据库
   * 3.将图片保存在img/category/中
   */
  @Override
  public String add(HttpServletRequest request) {
    Map<String, String> params = new HashMap<>();
    InputStream inputStream;
    try {
      inputStream = parseUpload(request, params);
    } catch (Exception e) {
      logger.error("上传图片异常：" + e);
      return "@error.jsp";
    }

    String categoryName = params.get("name");
    Category category = new Category();
    category.setName(categoryName);
    logger.info("添加分类：{}", category);
    dao.saveCategory(category);

    if (inputStream != null) {
      try {
        saveImg(inputStream, category.getId());
      } catch (IOException e) {
        logger.error("保存图片异常：" + e);
        return "@error.jsp";
      }
    }
    return "@admin_category_list";
  }

  @Override
  public String delete(HttpServletRequest request) {
    int categoryId = Integer.parseInt(request.getParameter("id"));
    dao.deleteCategory(categoryId);

    //删除对应的分类图片
    File imgFile = new File(getServletContext().getRealPath("img/category"), categoryId + ".jpg");
    if (imgFile.exists()) {
      imgFile.delete();
      logger.info("删除图片：{}", imgFile);
    }
    return "@admin_category_list";
  }

  @Override
  public String update(HttpServletRequest request) {
    Map<String, String> params = new HashMap<>();
    InputStream inputStream;
    try {
      inputStream = parseUpload(request, params);
    } catch (Exception e) {
      logger.error("上传图片异常：" + e);
      return "@error.jsp";
    }
    int categoryId = Integer.parseInt(params.get("id"));
    Category category = dao.getCategoryById(categoryId);
    String categoryName = params.get("name");
    if (StringUtils.isNotEmpty(categoryName) && !categoryName.equals(category.getName())) {
      category.setName(categoryName);
      logger.info("修改分类：{}", category);
      dao.updateCategory(category);
    }
    if (inputStream != null) {
      try {
        saveImg(inputStream, categoryId);
      } catch (IOException e) {
        logger.error("保存图片异常：" + e);
        return "@error.jsp";
      }
    }
    return "@admin_category_list";
  }

  @Override
  public String list(HttpServletRequest request, Page page) {
    List<Category> categoryList = dao.listCategoryByPage(page.getStart(), page.getCount());
    page.setTotal(dao.getCategoryCount());
    request.setAttribute("category_list", categoryList);
    request.setAttribute("page", page);
    return "admin/listCategory.jsp";
  }

  @Override
  public String edit(HttpServletRequest request) {
    int categoryId = Integer.parseInt(request.getParameter("id"));
    Category category = dao.getCategoryById(categoryId);
    request.setAttribute("category", category);
    return "admin/editCategory.jsp";
  }

  private void saveImg(InputStream inputStream, int categoryId) throws IOException {
    String imgFolder = getServletContext().getRealPath("img/category");
    String fileName = categoryId + ".jpg";
    File imgFile = new File(imgFolder, fileName);
    imgFile.getParentFile().mkdirs();
    try (FileOutputStream fos = new FileOutputStream(imgFile)) {
      byte[] buff = new byte[8 * 1024];
      int len;
      while ((len = inputStream.read(buff)) != -1) {
        fos.write(buff, 0, len);
      }
      fos.flush();
      //统一将图片转换为jpg格式
      BufferedImage img = ImageUtil.change2jpg(imgFile);
      ImageIO.write(img, "jpg", imgFile);
    }
  }
}
