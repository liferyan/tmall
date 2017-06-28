package com.liferyan.tmall.web.util;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Created by Ryan on 2017/6/28.
 */
public class PageTest {

  @Test
  public void test() throws Exception {
    Page page = new Page(0,5);
    page.setTotal(5);
    MatcherAssert.assertThat(page.getLastPageStart(),CoreMatchers.is(0));
    MatcherAssert.assertThat(page.isHasNext(),CoreMatchers.equalTo(false));
  }

}