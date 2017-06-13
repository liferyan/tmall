package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Review;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Created by Ryan on 2017/4/18.
 */
@Component
public class ReviewDao extends BaseDao {

  public void saveReview(Review review) {
    this.getSqlSession().insert("saveReview", review);
  }

  public List<Review> listProductReviewByPage(int pid, int start, int count) {
    Map<String, Object> parameterMap = new HashMap<>();
    parameterMap.put("pid", pid);
    parameterMap.put("start", start);
    parameterMap.put("count", count);
    return this.getSqlSession().selectList("listProductReviewByPage", parameterMap);
  }

  public List<Review> listProductReview(int pid) {
    return this.getSqlSession().selectList("listProductReview", pid);
  }

}
