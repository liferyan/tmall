package com.liferyan.tmall.web.controller.backend;

import com.liferyan.tmall.data.dao.ProductDao;
import com.liferyan.tmall.data.dao.ProductImageDao;
import com.liferyan.tmall.data.entity.ImageTypeEnum;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.ProductImage;
import com.liferyan.tmall.web.util.ImageUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Ryan on 2017/6/28.
 */
@Controller
@RequestMapping("/backend")
public class ProductImageManagerController {

  private ProductDao productDao;

  private ProductImageDao productImageDao;

  private ServletContext servletContext;

  @Autowired
  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  public ProductImageManagerController(ProductDao productDao,
      ProductImageDao productImageDao) {
    this.productDao = productDao;
    this.productImageDao = productImageDao;
  }

  @GetMapping("/productImages/{productId}")
  public String showProductImages(@PathVariable("productId") int productId, Model model) {
    Product product = productDao.getProductById(productId);
    model.addAttribute(product.getCategory());
    model.addAttribute(product);
    model.addAttribute("singleImageList",
        productImageDao.listProductImage(product, ImageTypeEnum.SINGLE));
    model.addAttribute("detailImageList",
        productImageDao.listProductImage(product, ImageTypeEnum.DETAIL));
    return "backend/listProductImg";
  }

  @PostMapping("/productImages/{productId}")
  public String saveProductImage(@RequestPart("imageFile") MultipartFile imageFile,
      @RequestParam("type") String imageTypeCode,
      @PathVariable("productId") int productId,
      RedirectAttributes redirectAttributes) throws IOException {
    ImageTypeEnum imageType = ImageTypeEnum.getEnumFromCode(imageTypeCode);
    if (imageFile.isEmpty()) {
      String errors = "产品" + imageType.getDescription() + "不能为空";
      String attrName = imageType == ImageTypeEnum.SINGLE ? "singleImageError" : "detailImageError";
      redirectAttributes.addFlashAttribute(attrName, errors);
    } else {
      Product product = productDao.getProductById(productId);
      ProductImage productImage = new ProductImage();
      productImage.setProduct(product);
      productImage.setImageType(imageType);
      productImageDao.saveProductImage(productImage);
      saveOrDeleteProductImage(imageFile, imageType, productImage.getId());
      redirectAttributes.addFlashAttribute("success", Boolean.TRUE);
    }
    return "redirect:/backend/productImages/{productId}";
  }

  @GetMapping("/productImage/{productImageId}/delete")
  public String deleteProductImage(@PathVariable("productImageId") int productImageId,
      @RequestParam("productId") int productId,RedirectAttributes redirectAttributes) throws IOException {
    ImageTypeEnum imageType = productImageDao.getProductImageById(productImageId).getImageType();
    productImageDao.deleteProductImage(productImageId);
    saveOrDeleteProductImage(null, imageType, productImageId);
    redirectAttributes.addFlashAttribute("success", Boolean.TRUE);
    return "redirect:/backend/productImages/" + productId;
  }

  private void saveOrDeleteProductImage(MultipartFile imageFile, ImageTypeEnum imageType,
      int productImageId)
      throws IOException {
    boolean isSingle = imageType == ImageTypeEnum.SINGLE;
    String imgPath, smallImgPath = null, middleImgPath = null;
    String imgName = productImageId + ".jpg";
    if (isSingle) {
      //单个图片(保存原始大小、中等大小和小图片)
      imgPath = servletContext.getRealPath("img/productSingle");
      smallImgPath = servletContext.getRealPath("img/productSingle_small");
      middleImgPath = servletContext.getRealPath("img/productSingle_middle");
    } else {
      imgPath = servletContext.getRealPath("img/productDetail");
    }
    File imgFile = new File(imgPath, imgName);
    if (imageFile == null) {
      //delete file
      if (isSingle) {
        File[] singleFiles = new File[]{imgFile, new File(smallImgPath, imgName),
            new File(middleImgPath, imgName)};
        for (File singleFile : singleFiles) {
          singleFile.delete();
        }
      } else {
        imgFile.delete();
      }
      return;
    }
    imgFile.getParentFile().mkdirs();
    imageFile.transferTo(imgFile);
    //统一将图片转换为jpg格式
    BufferedImage img = ImageUtil.change2jpg(imgFile);
    ImageIO.write(img, "jpg", imgFile);
    if (isSingle) {
      File smallImgFile = new File(smallImgPath, imgName);
      smallImgFile.getParentFile().mkdirs();
      ImageUtil.resizeImage(imgFile, 56, 56, smallImgFile);
      File middleImgFile = new File(middleImgPath, imgName);
      middleImgFile.getParentFile().mkdirs();
      ImageUtil.resizeImage(imgFile, 217, 190, middleImgFile);
    }
  }

}
