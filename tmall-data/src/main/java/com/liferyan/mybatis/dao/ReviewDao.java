package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.Review;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 */
public interface ReviewDao {

  void saveReview(Review review);

  List<Review> listProductReviewByPage(int pid, int start, int count);

  List<Review> listProductReview(int pid);

}
