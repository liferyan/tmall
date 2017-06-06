package com.liferyan.tmall.web.servlet;

import com.liferyan.tmall.data.dao.DaoFactory;
import com.liferyan.tmall.data.dao.ProductImageDao;
import com.liferyan.tmall.data.entity.ImageTypeEnum;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.ProductImage;
import com.liferyan.tmall.web.util.ImageUtil;
import com.liferyan.tmall.web.util.Page;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ryan on 2017/5/5.
 * 产品图片Servlet
 */
public class ProductImageServlet extends BaseBackServlet {

  private ProductImageDao dao;

  public ProductImageServlet() {
    dao = DaoFactory.getProductImageDao();
  }

  @Override
  public String add(HttpServletRequest request) {
    InputStream inputStream;
    Map<String, String> params = new HashMap<>();
    try {
      inputStream = parseUpload(request, params);
    } catch (Exception e) {
      logger.error("上传图片异常：" + e);
      return "@error.jsp";
    }
    ProductImage productImg = new ProductImage();
    ImageTypeEnum imageType = ImageTypeEnum.getEnumFromCode(params.get("type"));
    int pid = Integer.parseInt(params.get("pid"));
    Product product = DaoFactory.getProductDao().getProductById(pid);
    productImg.setImageType(imageType);
    productImg.setProduct(product);
    logger.info("保存图片：{}", productImg);
    dao.saveProductImage(productImg);
    int productImgId = productImg.getId();
    if (inputStream != null) {
      try {
        saveImg(inputStream, productImgId, imageType);
      } catch (IOException e) {
        logger.error("保存图片异常：" + e);
        return "@error.jsp";
      }
    }
    return "@admin_productImage_list?pid=" + pid;
  }

  @Override
  public String delete(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    ProductImage productImg = dao.getProductImageById(id);
    dao.deleteProductImage(id);
    //删除指定目录下的图片
    String fileName = id + ".jpg";
    if (productImg.getImageType() == ImageTypeEnum.SINGLE) {
      File imgFile = new File(getServletContext().getRealPath("img/productSingle"), fileName);
      File imgFileSmall = new File(getServletContext().getRealPath("img/productSingle_small"),
          fileName);
      File imgFileMiddle = new File(getServletContext().getRealPath("img/productSingle_middle"),
          fileName);
      imgFile.delete();
      logger.info("删除单张标准图片：{}", imgFile);
      imgFileSmall.delete();
      logger.info("删除单张小图片：{}", imgFileSmall);
      imgFileMiddle.delete();
      logger.info("删除单张中图片：{}", imgFileMiddle);
    } else {
      File imgFile = new File(getServletContext().getRealPath("img/productDetail"), fileName);
      imgFile.delete();
      logger.info("删除详情图片：{}", imgFile);
    }
    int pid = productImg.getProduct().getId();
    return "@admin_productImage_list?pid=" + pid;
  }

  @Override
  public String update(HttpServletRequest request) {
    return null;
  }

  @Override
  public String list(HttpServletRequest request, Page page) {
    int pid = Integer.parseInt(request.getParameter("pid"));
    Product product = DaoFactory.getProductDao().getProductById(pid);
    //List<ProductImage> singleProductImgList = dao.listProductImage(product, ImageTypeEnum.SINGLE);
    //List<ProductImage> detailProductImgList = dao.listProductImage(product, ImageTypeEnum.DETAIL);
    request.setAttribute("category", product.getCategory());
    request.setAttribute("product", product);
    request.setAttribute("single_img_list", product.getSingleProductImageList());
    request.setAttribute("detail_img_list", product.getDetailProductImageList());
    return "admin/listProductImg.jsp";
  }

  @Override
  public String edit(HttpServletRequest request) {
    return null;
  }

  private void saveImg(InputStream inputStream, int productImgId, ImageTypeEnum imageType)
      throws IOException {
    boolean isSingle = imageType == ImageTypeEnum.SINGLE;
    String imgFolder;
    String imgFolderSmall = null;
    String imgFolderMiddle = null;
    String fileName = productImgId + ".jpg";
    if (isSingle) {
      //单个图片(保存原始大小、中等大小和小图片)
      imgFolder = getServletContext().getRealPath("img/productSingle");
      imgFolderSmall = getServletContext().getRealPath("img/productSingle_small");
      imgFolderMiddle = getServletContext().getRealPath("img/productSingle_middle");
    } else {
      //详情图片
      imgFolder = getServletContext().getRealPath("img/productDetail");
    }
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
      if (isSingle) {
        File imgFileSmall = new File(imgFolderSmall, fileName);
        File imgFileMiddle = new File(imgFolderMiddle, fileName);
        imgFileSmall.getParentFile().mkdirs();
        imgFileMiddle.getParentFile().mkdirs();
        ImageUtil.resizeImage(imgFile, 56, 56, imgFileSmall);
        ImageUtil.resizeImage(imgFile, 217, 190, imgFileMiddle);
      }
    }
  }
}
