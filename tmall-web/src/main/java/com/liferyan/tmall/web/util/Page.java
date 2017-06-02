package com.liferyan.tmall.web.util;

/**
 * Created by Ryan on 2017/4/20.
 * 分页
 */
public class Page {

  /**
   * 当前页的起始位置
   */
  public int start;

  /**
   * 当前页显示的数量
   */
  public int count;

  /**
   * 总记录条数
   */
  private int total;

  /**
   * 参数
   */
  public String param;


  public Page(int start, int count) {
    super();
    this.start = start;
    this.count = count;
  }

  /**
   * ${page.start}
   */
  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  /**
   * ${page.count}
   */
  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  /**
   * ${page.param}
   */
  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }

  /**
   * 当前页之前是否还有页 ${page.hasPrevious}
   */
  public boolean isHasPrevious() {
    return start != 0;
  }

  /**
   * 当前页之后是否还有页 ${page.hasNext}
   */
  public boolean isHasNext() {
    return start != getLastPageStart();
  }

  /**
   * 总页数 ${page.totalPage}
   */
  public int getTotalPage() {
    int totalPage;
    if (total == 0) {
      return 1;
    }
    if (total % count == 0) {
      totalPage = total / count;
    } else {
      totalPage = total / count + 1;
    }
    return totalPage;
  }

  /**
   * 最后一页的pageStart ${page.lastPageStart}
   */
  public int getLastPageStart() {
    int lastPageStart;
    if (total == 0) {
      return 0;
    }
    if (total == count) {
      return count;
    }
    if (total % count == 0) {
      lastPageStart = count * (total / count - 1);
    } else {
      lastPageStart = count * (total / count);
    }
    return lastPageStart;
  }

}
