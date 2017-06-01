package com.liferyan.mybatis;

import com.liferyan.mybatis.dao.CategoryDaoTest;
import com.liferyan.mybatis.dao.OrderDaoTest;
import com.liferyan.mybatis.dao.OrderItemDaoTest;
import com.liferyan.mybatis.dao.ProductDaoTest;
import com.liferyan.mybatis.dao.ProductImageDaoTest;
import com.liferyan.mybatis.dao.PropertyDaoTest;
import com.liferyan.mybatis.dao.PropertyValueDaoTest;
import com.liferyan.mybatis.dao.ReviewDaoTest;
import com.liferyan.mybatis.dao.UserDaoTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Ryan on 2017/5/27.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserDaoTest.class,
    CategoryDaoTest.class,
    PropertyDaoTest.class,
    ProductDaoTest.class,
    PropertyValueDaoTest.class,
    ProductImageDaoTest.class,
    ReviewDaoTest.class,
    OrderDaoTest.class,
    OrderItemDaoTest.class
})
public class TestSuite {

}
