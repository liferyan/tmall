package com.liferyan.mybatis.dao;

import com.liferyan.mybatis.entity.Category;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Ryan on 2017/4/18.
 * CategoryDao接口
 */
public interface CategoryDao {

  @Insert("INSERT INTO category(name) VALUES(#{name})")
  @Options(useGeneratedKeys = true)
  void saveCategory(Category category);

  @Delete("DELETE FROM category WHERE id = #{id}")
  void deleteCategory(int id);

  @Update("UPDATE category set name = #{name} WHERE id = #{id}")
  void updateCategory(Category category);

  @Select("SELECT * FROM category WHERE id = #{id}")
  Category getCategoryById(int id);

  @Select("SELECT * FROM category ORDER BY id LIMIT #{count} OFFSET #{start}")
  List<Category> listCategoryByPage(@Param("start") int start, @Param("count") int count);

  @Select("SELECT * FROM category ORDER BY id")
  List<Category> listCategory();

  @Select("SELECT COUNT(*) FROM category")
  int getCategoryCount();

}
