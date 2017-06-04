package com.liferyan.tmall.web.servlet;

import com.liferyan.tmall.data.dao.DaoFactory;
import com.liferyan.tmall.data.dao.OrderDao;
import com.liferyan.tmall.data.dao.OrderItemDao;
import com.liferyan.tmall.data.entity.Category;
import com.liferyan.tmall.data.entity.Order;
import com.liferyan.tmall.data.entity.OrderItem;
import com.liferyan.tmall.data.entity.OrderStatusEnum;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.PropertyValue;
import com.liferyan.tmall.data.entity.Review;
import com.liferyan.tmall.data.entity.User;
import com.liferyan.tmall.web.comparator.ProductAllComparator;
import com.liferyan.tmall.web.comparator.ProductDateComparator;
import com.liferyan.tmall.web.comparator.ProductPriceComparator;
import com.liferyan.tmall.web.comparator.ProductReviewComparator;
import com.liferyan.tmall.web.comparator.ProductSaleCountComparator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Ryan on 2017/5/8.
 * 所有前台Servlet
 */
public class ForeServlet extends BaseForeServlet {

  /**
   * 产品页立即购买
   */
  public String buyone(HttpServletRequest request) {
    int oiid = addOrderItem(request);
    return "@forebuy?oiid=" + oiid;
  }

  /**
   * 产品页加入购物车异步 Ajax
   */
  public String addCart(HttpServletRequest request) {
    addOrderItem(request);
    return "%success";
  }

  /**
   * 根据用户ID跳转到购物车页
   */
  public String cart(HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute("user");
    List<OrderItem> orderItemList = DaoFactory.getOrderItemDao()
        .listOrderItemInCartByUser(user.getId());
    request.setAttribute("order_item_list", orderItemList);
    return "cart.jsp";
  }

  /**
   * 购物车订单项异步修改 Ajax
   * 删除订单项
   */
  public String deleteOrderItem(HttpServletRequest request) {
    int orderItemId = Integer.parseInt(request.getParameter("oiid"));
    DaoFactory.getOrderItemDao().deleteOrderItem(orderItemId);
    return "%success";
  }

  /**
   * 购物车订单项异步修改 Ajax
   * 修改订单项数量
   */
  public String changeOrderItem(HttpServletRequest request) {
    int productId = Integer.parseInt(request.getParameter("pid"));
    int number = Integer.parseInt(request.getParameter("number"));
    OrderItemDao dao = DaoFactory.getOrderItemDao();
    User user = (User) request.getSession().getAttribute("user");
    OrderItem orderItem = dao.getOrderItemInCart(user.getId(), productId);
    orderItem.setNumber(number);
    dao.updateOrderItem(orderItem);
    return "%success";
  }

  /**
   * 增加订单项到数据库
   * 如果购物车已经有相同的订单项则 修改订单项数据
   * 返回订单项ID
   */
  private int addOrderItem(HttpServletRequest request) {
    int pid = Integer.parseInt(request.getParameter("pid"));
    int num = Integer.parseInt(request.getParameter("num"));
    User user = (User) request.getSession().getAttribute("user");
    Product product = DaoFactory.getProductDao().getProductById(pid);
    int oiid;
    OrderItem orderItem;
    //购物车中没有当前的产品
    OrderItem orderItemInCart = DaoFactory.getOrderItemDao().getOrderItemInCart(user.getId(), pid);
    if (null == orderItemInCart) {
      //目前还没有生成订单
      Order order = new Order();
      order.setId(-1);
      orderItem = new OrderItem();
      orderItem.setNumber(num);
      orderItem.setProduct(product);
      orderItem.setUser(user);
      orderItem.setOrder(order);
      DaoFactory.getOrderItemDao().saveOrderItem(orderItem);
      oiid = orderItem.getId();
    } else {
      //否则到购物车增加当前产品数量 然后将购物车进行结算
      num += orderItemInCart.getNumber();
      orderItemInCart.setNumber(num);
      DaoFactory.getOrderItemDao().updateOrderItem(orderItemInCart);
      oiid = orderItemInCart.getId();
    }
    return oiid;
  }

  /**
   * 1.根据orderItemId添加订单项信息
   * 2.跳转到结算页
   */
  public String buy(HttpServletRequest request) {
    String[] oiidStrs = request.getParameterValues("oiid");
    int oiid;
    float productPrice;
    float totalOrderMoney = 0;
    Product product;
    OrderItem orderItem;
    List<OrderItem> orderItemList = new ArrayList<>();
    for (String oiidStr : oiidStrs) {
      oiid = Integer.parseInt(oiidStr);
      orderItem = DaoFactory.getOrderItemDao().getOrderItemById(oiid);
      if (orderItem != null) {
        product = DaoFactory.getProductDao().getProductById(orderItem.getProduct().getId());
        orderItem.setProduct(product);
        productPrice = product.getPromotePrice();
        totalOrderMoney += orderItem.getNumber() * productPrice;
        orderItemList.add(orderItem);
      }
    }
    request.setAttribute("total_order_money", totalOrderMoney);
    //将订单项列表放入Session 方便生成订单时 修改这些订单项的所属订单ID
    request.getSession().setAttribute("order_item_list", orderItemList);
    return "buy.jsp";
  }

  /**
   * 1.生成订单
   * 2.修改订单项中订单ID
   * 3.跳转到支付页
   */
  public String createOrder(HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute("user");

    List<OrderItem> orderItemList = (List<OrderItem>) request.getSession()
        .getAttribute("order_item_list");
    if (orderItemList.isEmpty()) {
      return "@login.jsp";
    }

    String address = request.getParameter("address");
    String post = request.getParameter("post");
    String receiver = request.getParameter("receiver");
    String mobile = request.getParameter("mobile");
    String userMessage = request.getParameter("userMessage");
    Date createDate = new Date();
    String now = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(createDate);
    String orderCode = now + RandomStringUtils.randomNumeric(4);
    OrderStatusEnum orderStatus = OrderStatusEnum.WAIT_PAY;
    Order order = new Order();
    order.setAddress(address);
    order.setPost(post);
    order.setReceiver(receiver);
    order.setMobile(mobile);
    order.setUserMessage(userMessage);
    order.setCreateDate(createDate);
    order.setOrderCode(orderCode);
    order.setOrderStatus(orderStatus);
    order.setUser(user);
    DaoFactory.getOrderDao().saveOrder(order);

    //将购物车里的订单项所属订单ID(-1)修改为实际的订单ID 并计算订单总金额
    float total = 0;
    for (OrderItem orderItem : orderItemList) {
      orderItem.setOrder(order);
      DaoFactory.getOrderItemDao().updateOrderItem(orderItem);
      total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
    }
    return "@forealipay?oid=" + order.getId() + "&total=" + total;
  }

  /**
   * 获取订单号和总金额
   * 跳转到支付页面
   */
  public String alipay(HttpServletRequest request) {
    int orderId = Integer.parseInt(request.getParameter("oid"));
    float total = Float.parseFloat(request.getParameter("total"));
    request.setAttribute("oid", orderId);
    request.setAttribute("total", total);
    return "alipay.jsp";
  }

  /**
   * 支付并跳转值支付成功页
   * 修改订单状态 WAIT_DELIVERY
   */
  public String payed(HttpServletRequest request) {
    int orderId = Integer.parseInt(request.getParameter("oid"));
    float total = Float.parseFloat(request.getParameter("total"));
    OrderDao dao = DaoFactory.getOrderDao();
    Order order = dao.getOrderById(orderId);
    order.setPayDate(new Date());
    order.setOrderStatus(OrderStatusEnum.WAIT_DELIVERY);
    dao.updateOrder(order);
    request.setAttribute("order", order);
    request.setAttribute("total", total);
    return "payed.jsp";
  }

  /**
   * 根据用户信息跳转值我的订单页
   */
  public String bought(HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute("user");
    List<Order> orderList = DaoFactory.getOrderDao().listOrderByUser(user.getId());
    request.setAttribute("order_list", orderList);
    return "bought.jsp";
  }

  /**
   * 删除订单 异步Ajax
   */
  public String deleteOrder(HttpServletRequest request) {
    int orderId = Integer.parseInt(request.getParameter("oid"));
    DaoFactory.getOrderDao().deleteOrder(orderId);
    return "%success";
  }

  /**
   * 跳转到确认收货页面
   */
  public String confirmPay(HttpServletRequest request) {
    int orderId = Integer.parseInt(request.getParameter("oid"));
    Order order = DaoFactory.getOrderDao().getOrderById(orderId);
    request.setAttribute("order", order);
    return "confirmPay.jsp";
  }

  /**
   * 订单已经确认收货，跳转到已确认页
   */
  public String orderConfirmed(HttpServletRequest request) {
    int orderId = Integer.parseInt(request.getParameter("oid"));
    OrderDao dao = DaoFactory.getOrderDao();
    Order order = dao.getOrderById(orderId);
    order.setConfirmDate(new Date());
    order.setOrderStatus(OrderStatusEnum.WAIT_REVIEW);
    dao.updateOrder(order);
    return "orderConfirmed.jsp";
  }

  /**
   * 根据产品ID获取评价列表并跳转值评价页
   */
  public String review(HttpServletRequest request) {
    int orderId = Integer.parseInt(request.getParameter("oid"));
    int productId = Integer.parseInt(request.getParameter("pid"));
    int orderItemId = Integer.parseInt(request.getParameter("oiid"));

    //修改订单项为已评价
    OrderItemDao orderItemDao = DaoFactory.getOrderItemDao();
    OrderItem orderItem = orderItemDao.getOrderItemById(orderItemId);
    orderItem.setHasReview(true);
    orderItemDao.updateOrderItem(orderItem);

    Order order = DaoFactory.getOrderDao().getOrderById(orderId);
    Product product = DaoFactory.getProductDao().getProductById(productId);
    List<Review> reviewList = DaoFactory.getReviewDao().listProductReview(product.getId());
    request.setAttribute("order_item", orderItem);
    request.setAttribute("order", order);
    request.setAttribute("product", product);
    request.setAttribute("review_list", reviewList);
    return "review.jsp";
  }

  /**
   * 添加新的评价
   */
  public String doreview(HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute("user");
    int orderId = Integer.parseInt(request.getParameter("oid"));
    int orderItemId = Integer.parseInt(request.getParameter("oiid"));

    OrderDao orderDao = DaoFactory.getOrderDao();
    Order order = orderDao.getOrderById(orderId);

    //如果订单下的订单项都已评价 则将订单状态改为FINISHED
    List<OrderItem> orderItemList = DaoFactory.getOrderItemDao().listOrderItemByOrder(orderId);
    boolean orderCompleted = true;
    boolean orderItemHasReview;
    for (OrderItem orderItem : orderItemList) {
      orderItemHasReview = orderItem.getHasReview();
      if (!orderItemHasReview) {
        orderCompleted = false;
        break;
      }
    }
    if (orderCompleted) {
      order.setOrderStatus(OrderStatusEnum.FINISHED);
      orderDao.updateOrder(order);
    }

    Review review = new Review();
    review.setCreateDate(new Date());
    review.setUser(user);

    int productId = Integer.parseInt(request.getParameter("pid"));
    Product product = DaoFactory.getProductDao().getProductById(productId);
    review.setProduct(product);

    String content = request.getParameter("content");
    review.setContent(content);

    DaoFactory.getReviewDao().saveReview(review);

    request.setAttribute("order", order);
    request.setAttribute("product", product);
    List<Review> reviewList = DaoFactory.getReviewDao().listProductReview(product.getId());
    request.setAttribute("review_list", reviewList);
    return "@forereview?oid=" + orderId + "&pid=" + productId + "&oiid=" + orderItemId
        + "&showonly=true";
  }

  /**
   * 根据关键字搜索产品
   */
  public String search(HttpServletRequest request) {
    String keyword = request.getParameter("keyword");
    List<Product> productList = DaoFactory.getProductDao().searchProduct(keyword, 0, 20);
    request.setAttribute("product_list", productList);
    return "searchResult.jsp";
  }

  /**
   * 根据分类ID和排序方式跳转到指定的产品分类页
   */
  public String category(HttpServletRequest request) {
    String sort = request.getParameter("sort");
    int categoryId = Integer.parseInt(request.getParameter("cid"));
    Category category = DaoFactory.getCategoryDao().getCategoryById(categoryId);
    Comparator<Product> comparator = null;
    if (sort != null) {
      switch (sort) {
        //人气
        case "review":
          comparator = new ProductReviewComparator();
          break;
        //新品
        case "date":
          comparator = new ProductDateComparator();
          break;
        //销量
        case "saleCount":
          comparator = new ProductSaleCountComparator();
          break;
        //价格
        case "price":
          comparator = new ProductPriceComparator();
          break;
        //综合
        case "all":
          comparator = new ProductAllComparator();
          break;
      }
    } else {
      comparator = new ProductAllComparator();
    }
    category.getProducts().sort(comparator);
    request.setAttribute("category", category);
    return "category.jsp";
  }

  /**
   * 跳转到主页
   */
  public String home(HttpServletRequest request) {
    List<Category> categoryList = DaoFactory.getCategoryDao().listCategory();
    request.setAttribute("category_list", categoryList);
    return "home.jsp";
  }

  /**
   * 根据PID跳转到产品页
   */
  public String product(HttpServletRequest request) {
    int productId = Integer.parseInt(request.getParameter("pid"));
    Product product = DaoFactory.getProductDao().getProductById(productId);
    List<PropertyValue> propertyValueList = DaoFactory.getPropertyValueDao()
        .listPropertyValue(productId);
    List<Review> reviewList = DaoFactory.getReviewDao().listProductReview(productId);
    request.setAttribute("pv_list", propertyValueList);
    request.setAttribute("review_list", reviewList);
    request.setAttribute("product", product);
    return "product.jsp";
  }

  /**
   * 用户注册
   * 成功后跳转到注册成功页
   */
  public String register(HttpServletRequest request) {
    String name = request.getParameter("name");
    if (DaoFactory.getUserDao().getUserByName(name) == null) {
      return "error.jsp";
    }
    String password = request.getParameter("password");
    User user = new User();
    user.setName(name);
    user.setPassword(password);
    DaoFactory.getUserDao().saveUser(user);
    return "registerSuccess.jsp";
  }

  /**
   * 用户登陆
   * 失败则跳转到登陆页
   * 成功则跳转到主页
   */
  public String login(HttpServletRequest request) {
    User user = getUser(request);
    if (user == null) {
      request.setAttribute("msg", "账号密码错误");
      return "login.jsp";
    }
    //将用户设置到Session中
    HttpSession httpSession = request.getSession();
    httpSession.setAttribute("user", user);
    String toSeePageUri;
    if (null != (toSeePageUri = (String) httpSession.getAttribute("to_see_page"))) {
      //跳转到未登陆前访问的地址
      httpSession.removeAttribute("to_see_page");
      return "@" + StringUtils.remove(toSeePageUri, request.getContextPath() + "/");
    }
    return "@forehome";
  }

  /**
   * 用户登出
   * 跳转到主页
   */
  public String logout(HttpServletRequest request) {
    request.getSession().removeAttribute("user");
    return "@forehome";
  }

  /**
   * 检查登陆状态 异步Ajax
   */
  public String checkLogin(HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
      return "%fail";
    }
    return "%success";
  }

  /**
   * 模态窗口登陆验证 异步Ajax
   */
  public String loginAjax(HttpServletRequest request) {
    User user = getUser(request);
    if (user == null) {
      return "%fail";
    }
    request.getSession().setAttribute("user", user);
    return "%success";
  }

  private User getUser(HttpServletRequest request) {
    String name = request.getParameter("name");
    String password = request.getParameter("password");
    return DaoFactory.getUserDao().getUserByNameAndPassword(name, password);
  }
}
