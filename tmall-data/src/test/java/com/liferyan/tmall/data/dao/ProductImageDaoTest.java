package com.liferyan.tmall.data.dao;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.liferyan.tmall.data.entity.ImageTypeEnum;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.ProductImage;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ryan on 2017/6/1.
 */
public class ProductImageDaoTest {

  private ProductImage productImage = new ProductImage();
  private ProductImageDao productImageDao = DaoFactory.getProductImageDao();
  private Product product;

  @Before
  public void setUp() throws Exception {
    product = DaoFactory.getProductDao().getProductById(958);
  }

  @Test
  public void crudProductImage() throws Exception {
    productImage.setProduct(null);
    productImage.setImageType(ImageTypeEnum.SINGLE);
    productImageDao.saveProductImage(productImage);
    assertThat(productImage.getId(), is(0));

    productImage.setProduct(product);
    productImage.setImageType(null);
    productImageDao.saveProductImage(productImage);
    assertThat(productImage.getId(), is(0));

    productImage.setImageType(ImageTypeEnum.SINGLE);
    productImageDao.saveProductImage(productImage);
    int id = productImage.getId();
    assertThat(id, not(0));

    productImage = productImageDao.getProductImageById(id);
    assertThat(productImage, notNullValue());

    productImageDao.deleteProductImage(id);
    productImage = productImageDao.getProductImageById(id);
    assertThat(productImage, nullValue());
  }

  @Test
  public void listProductImage() throws Exception {
    List<ProductImage> singleProductImgs = productImageDao
        .listProductImage(product, ImageTypeEnum.SINGLE);
    assertThat(singleProductImgs, notNullValue());
    assertThat(singleProductImgs.size(), greaterThan(0));
    List<ProductImage> detailProductImgs = productImageDao
        .listProductImage(product, ImageTypeEnum.DETAIL);
    assertThat(detailProductImgs, notNullValue());
    assertThat(detailProductImgs.size(), greaterThan(0));
    List<List<ProductImage>> productImgsList = new ArrayList<>();
    productImgsList.add(singleProductImgs);
    productImgsList.add(detailProductImgs);
    for (List<ProductImage> productCollection : productImgsList) {
      for (ProductImage productImg : productCollection) {
        assertThat(productImg.getImageType(), isOneOf(ImageTypeEnum.SINGLE, ImageTypeEnum.DETAIL));
        Product pd = productImg.getProduct();
        assertThat(pd, notNullValue());
        assertThat(pd.getName(), notNullValue());
        assertThat(pd.getSubTitle(), notNullValue());
        assertThat(pd.getOriginalPrice(), notNullValue());
        assertThat(pd.getPromotePrice(), notNullValue());
        assertThat(pd.getStock(), notNullValue());
        assertThat(pd.getCreateDate(), notNullValue());
      }
    }
  }

}