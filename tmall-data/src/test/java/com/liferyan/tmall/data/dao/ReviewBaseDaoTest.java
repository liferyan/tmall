package com.liferyan.tmall.data.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.Review;
import com.liferyan.tmall.data.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ryan on 2017/6/1.
 */
public class ReviewBaseDaoTest extends BaseDaoTest {

  @Autowired
  private ReviewDao reviewDao;

  @Autowired
  private ProductDao productDao;

  @Autowired
  private UserDao userDao;

  private Review review = new Review();
  private User user;
  private Product product;

  @Before
  public void setUp() throws Exception {
    user = userDao.getUserByName("admin");
    product = productDao.getProductById(958);
  }

  @Test
  public void saveReview() throws Exception {
    review.setProduct(product);
    review.setUser(user);
    review.setCreateDate(new Date());
    review.setContent("评价测试!");
    reviewDao.saveReview(review);
    assertThat(review.getId(), not(0));
  }

  @Test
  public void listReview() throws Exception {
    List<Review> reviewList = reviewDao.listProductReview(product.getId());
    List<Review> reviews = reviewDao.listProductReviewByPage(product.getId(), 0, 2);
    assertThat(reviews.size(), Matchers.lessThanOrEqualTo(2));
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