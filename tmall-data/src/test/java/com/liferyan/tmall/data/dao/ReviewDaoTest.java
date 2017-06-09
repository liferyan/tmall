package com.liferyan.tmall.data.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.Review;
import com.liferyan.tmall.data.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Ryan on 2017/6/1.
 */
public class ReviewDaoTest {

  private static ReviewDao reviewDao;
  private static ProductDao productDao;
  private static UserDao userDao;
  private Review review = new Review();
  private User user;
  private Product product;

  @BeforeClass
  public static void init() {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    productDao = (ProductDao) ctx.getBean("productDao");
    reviewDao = (ReviewDao) ctx.getBean("reviewDao");
    userDao = (UserDao) ctx.getBean("userDao");
  }

  @Before
  public void setUp() throws Exception {
    user = userDao.getUserByName("test");
    product = productDao.getProductById(958);
  }

  @Test
  public void saveReview() throws Exception {
    review.setProduct(product);
    review.setUser(user);
    review.setContent(null);
    review.setCreateDate(new Date());
    reviewDao.saveReview(review);
    assertThat(review.getId(), is(0));

    review.setContent("评价测试!");
    review.setUser(null);
    reviewDao.saveReview(review);
    assertThat(review.getId(), is(0));

    review.setUser(user);
    review.setProduct(null);
    reviewDao.saveReview(review);
    assertThat(review.getId(), is(0));

    review.setProduct(product);
    reviewDao.saveReview(review);
    assertThat(review.getId(), not(0));
  }

  @Test
  public void listReview() throws Exception {
    List<Review> reviewList = reviewDao.listProductReview(product.getId());
    List<Review> reviews = reviewDao.listProductReviewByPage(product.getId(), 0, 2);
    assertThat(reviews.size(), is(2));
    List<List<Review>> listsList = new ArrayList<>();
    listsList.add(reviewList);
    listsList.add(reviews);
    for (List<Review> listCollection : listsList) {
      for (Review review : listCollection) {
        assertThat(review.getId(), not(0));
        assertThat(review.getContent(), notNullValue());
        assertThat(review.getCreateDate(), notNullValue());
        assertThat(review.getUser(), notNullValue());
        assertThat(review.getUser().getName(), notNullValue());
        assertThat(review.getUser().getAnonymousName(), notNullValue());
        assertThat(review.getProduct(), notNullValue());
        assertThat(review.getProduct().getName(), notNullValue());
      }
    }
  }
}