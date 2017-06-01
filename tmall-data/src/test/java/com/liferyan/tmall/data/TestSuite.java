package com.liferyan.tmall.data;

import com.liferyan.tmall.data.dao.CategoryDaoTest;
import com.liferyan.tmall.data.dao.OrderDaoTest;
import com.liferyan.tmall.data.dao.OrderItemDaoTest;
import com.liferyan.tmall.data.dao.ProductDaoTest;
import com.liferyan.tmall.data.dao.ProductImageDaoTest;
import com.liferyan.tmall.data.dao.PropertyDaoTest;
import com.liferyan.tmall.data.dao.PropertyValueDaoTest;
import com.liferyan.tmall.data.dao.ReviewDaoTest;
import com.liferyan.tmall.data.dao.UserDaoTest;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Ryan on 2017/5/27.
 */
@Ignore
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
