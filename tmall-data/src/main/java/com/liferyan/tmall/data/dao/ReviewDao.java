package com.liferyan.tmall.data.dao;

import com.liferyan.tmall.data.entity.Review;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by Ryan on 2017/4/18.
 */
public class ReviewDao extends BaseDao {

  public void saveReview(Review review) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "saveReview";
      session.insert(statement, review);
    } catch (Exception e) {
      logger.error("保存评价异常：", e);
    }
  }

  public List<Review> listProductReviewByPage(int pid, int start, int count) {
    List<Review> reviewList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listProductReviewByPage";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("pid", pid);
      parameterMap.put("start", start);
      parameterMap.put("count", count);
      reviewList = session.selectList(statement, parameterMap);
    } catch (Exception e) {
      logger.error("获取评价异常：", e);
    }
    return reviewList;
  }

  public List<Review> listProductReview(int pid) {
    List<Review> reviewList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listProductReview";
      reviewList = session.selectList(statement, pid);
    } catch (Exception e) {
      logger.error("获取评价异常：", e);
    }
    return reviewList;
  }

}
