package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Review;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 */
public interface ReviewDao {

  void saveReview(Review review);

  List<Review> listProductReviewByPage(int pid, int start, int count);

  List<Review> listProductReview(int pid);

}
