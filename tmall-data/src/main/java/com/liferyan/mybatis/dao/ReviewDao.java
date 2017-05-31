package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.Review;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Ryan on 2017/4/18.
 * ReviewDao接口
 */
public interface ReviewDao {

  @Insert("INSERT INTO review(pid,uid,content,create_date) VALUES(#{pid},#{uid},#{content},#{create_date})")
  @Options(useGeneratedKeys = true)
  void saveReview(Review review);

  @Select("SELECT * FROM review WHERE pid = #{pid} ORDER BY create_date DESC LIMIT #{count} OFFSET #{start}")
  List<Review> listReviewByPage(@Param("pid") int pid, @Param("start") int start,
      @Param("count") int count);

  @Select("SELECT * FROM review WHERE pid = #{pid} ORDER BY create_date DESC")
  List<Review> listReview(@Param("pid") int pid);

}
