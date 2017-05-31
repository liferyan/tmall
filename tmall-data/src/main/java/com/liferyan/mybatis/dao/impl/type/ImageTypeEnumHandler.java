package com.liferyan.mybatis.dao.impl.type;

import com.liferyan.mybatis.entity.ImageTypeEnum;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * Created by Ryan on 2017/5/26.
 * 自定义枚举转换器
 */
public class ImageTypeEnumHandler extends BaseTypeHandler<ImageTypeEnum> {

  public ImageTypeEnumHandler(Class<ImageTypeEnum> type) {
    if (type == null) {
      throw new IllegalArgumentException("Type argument cannot be null");
    }
    if (type.getEnumConstants() == null) {
      throw new IllegalArgumentException(type.getSimpleName()
          + " does not represent an enum type.");
    }
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, ImageTypeEnum parameter,
      JdbcType jdbcType) throws SQLException {
    if (jdbcType == null) {
      ps.setString(i, parameter.getCode());
    } else {
      ps.setObject(i, parameter.getCode(), jdbcType.TYPE_CODE);
    }
  }

  @Override
  public ImageTypeEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String s = rs.getString(columnName);
    return s == null ? null : ImageTypeEnum.getEnumFromCode(s);
  }

  @Override
  public ImageTypeEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String s = rs.getString(columnIndex);
    return s == null ? null : ImageTypeEnum.getEnumFromCode(s);
  }

  @Override
  public ImageTypeEnum getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    String s = cs.getString(columnIndex);
    return s == null ? null : ImageTypeEnum.getEnumFromCode(s);
  }
}
