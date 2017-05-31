package com.liferyan.mybatis.dao.impl;

import com.liferyan.mybatis.dao.ReviewDao;
import com.liferyan.mybatis.entity.Review;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ryan on 2017/5/26.
 */
public class ReviewDaoImpl implements ReviewDao {

  private static final Logger logger = LoggerFactory.getLogger("ReviewDaoImpl");

  private SqlSessionFactory sqlSessionFactory;

  private static ReviewDaoImpl INSTANCE;

  private ReviewDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public static ReviewDaoImpl getInstance(SqlSessionFactory sqlSessionFactory) {
    if (INSTANCE == null) {
      INSTANCE = new ReviewDaoImpl(sqlSessionFactory);
    }
    return INSTANCE;
  }

  @Override
  public void saveReview(Review review) {
    try (SqlSession session = sqlSessionFactory.openSession(true)) {
      String statement = "saveReview";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("pid", review.getProduct().getId());
      parameterMap.put("uid", review.getUser().getId());
      parameterMap.put("review", review);
      /*parameterMap.put("content", review.getContent());
      parameterMap.put("create_date", review.getCreateDate());*/
      session.insert(statement, parameterMap);
    } catch (Exception e) {
      logger.error("保存评价异常：{}", e.getMessage());
    }
  }

  @Override
  public List<Review> listReviewByPage(int pid, int start, int count) {
    List<Review> reviewList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listReviewByPage";
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("pid", pid);
      parameterMap.put("start", start);
      parameterMap.put("count", count);
      reviewList = session.selectList(statement, parameterMap);
    } catch (Exception e) {
      logger.error("获取评价异常：{}", e.getMessage());
    }
    return reviewList;
  }

  @Override
  public List<Review> listReview(int pid) {
    List<Review> reviewList = null;
    try (SqlSession session = sqlSessionFactory.openSession()) {
      String statement = "listReview";
      reviewList = session.selectList(statement, pid);
    } catch (Exception e) {
      logger.error("获取评价异常：{}", e.getMessage());
    }
    return reviewList;
  }
}
