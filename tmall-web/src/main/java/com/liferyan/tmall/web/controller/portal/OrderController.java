package com.liferyan.tmall.web.controller.portal;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.liferyan.tmall.data.dao.OrderDao;
import com.liferyan.tmall.data.dao.OrderItemDao;
import com.liferyan.tmall.data.dao.ProductDao;
import com.liferyan.tmall.data.dao.ReviewDao;
import com.liferyan.tmall.data.entity.Order;
import com.liferyan.tmall.data.entity.OrderItem;
import com.liferyan.tmall.data.entity.OrderStatusEnum;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.Review;
import com.liferyan.tmall.data.entity.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ryan on 2017/7/5.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

  private OrderDao orderDao;

  private OrderItemDao orderItemDao;

  private ProductDao productDao;

  private ReviewDao reviewDao;

  @Autowired
  public OrderController(OrderDao orderDao, OrderItemDao orderItemDao,
      ProductDao productDao, ReviewDao reviewDao) {
    this.orderDao = orderDao;
    this.orderItemDao = orderItemDao;
    this.productDao = productDao;
    this.reviewDao = reviewDao;
  }


  @GetMapping
  public String showUserOrder(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "redirect:/user/login";
    }
    model.addAttribute(orderDao.listOrderByUser(user.getId()));
    return "bought";
  }

  @ResponseBody
  @PostMapping("/deleteAjax")
  public String deleteOrder(@RequestParam("oid") int deleteOrderId) {
    orderDao.deleteOrder(deleteOrderId);
    return "success";
  }

  @GetMapping("/confirmPay")
  public String showOrderConfirm(@RequestParam("oid") int orderId, Model model) {
    model.addAttribute(orderDao.getOrderById(orderId));
    return "confirmPay";
  }

  @GetMapping("/orderConfirm")
  public String orderConfirm(@RequestParam("oid") int orderId) {
    Order order = orderDao.getOrderById(orderId);
    order.setOrderStatus(OrderStatusEnum.WAIT_REVIEW);
    order.setConfirmDate(new Date());
    orderDao.updateOrder(order);
    return "orderConfirmed";
  }

  @GetMapping("/review")
  public String showProductReviewPage(
      @RequestParam("oid") int orderId,
      @RequestParam("pid") int productId,
      @RequestParam("oiid") int orderItemId,
      Model model) {
    model.addAttribute(orderDao.getOrderById(orderId));
    model.addAttribute(productDao.getProductById(productId));
    model.addAttribute(orderItemDao.getOrderItemById(orderItemId));
    return "review";
  }

  @PostMapping("/doreview")
  public String doReview(
      @RequestParam("content") String reviewContent,
      @RequestParam("oid") int orderId,
      @RequestParam("pid") int productId,
      @RequestParam("oiid") int orderItemId,
      HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "redirect:/user/login";
    }

    //修改订单项为已评价
    OrderItem orderItem = orderItemDao.getOrderItemById(orderItemId);
    orderItem.setHasReview(true);
    orderItemDao.updateOrderItem(orderItem);

    //如果订单下的订单项都已评价 则将订单状态改为FINISHED
    Order order = orderDao.getOrderById(orderId);
    List<OrderItem> orderItemList = order.getOrderItems();
    boolean orderCompleted = true;
    for (OrderItem oItem : orderItemList) {
      if (!oItem.getHasReview()) {
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
    Product product = productDao.getProductById(productId);
    review.setProduct(product);
    review.setContent(reviewContent);
    reviewDao.saveReview(review);
    model.addAttribute(product);
    model.addAttribute(order);
    model.addAttribute(reviewDao.listProductReview(productId));
    model.addAttribute("showReviewList", Boolean.TRUE);
    return "review";
  }

  @PostMapping("/add")
  public String createOrder(@Valid Order order, BindingResult result,
      HttpSession session, Model model) {
    model.addAttribute("totalMoneyInOrder", order.getTotal());
    if (result.hasErrors()) {
      return "buy";
    }
    User user = (User) session.getAttribute("user");
    List<OrderItem> orderItemList = (List<OrderItem>) session.getAttribute("orderItems");
    session.removeAttribute("orderItems");

    Date createDate = new Date();
    order.setCreateDate(createDate);
    String now = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(createDate);
    String orderCode = now + RandomStringUtils.randomNumeric(4);
    order.setOrderCode(orderCode);
    order.setOrderStatus(OrderStatusEnum.WAIT_PAY);
    order.setUser(user);
    orderDao.saveOrder(order);

    //将购物车里的订单项所属订单ID(-1)修改为实际的订单ID
    for (OrderItem orderItem : orderItemList) {
      orderItem.setOrder(order);
      orderItemDao.updateOrderItem(orderItem);
    }

    //生成订单后修改购物车商品数量
    int cartItemCount = (int) session.getAttribute("cartItemCount");
    cartItemCount -= orderItemList.size();
    session.setAttribute("cartItemCount", cartItemCount);

    model.addAttribute(order);
    return "alipay";
  }

  @RequestMapping(path = "/pay/{orderId}", method = {GET, POST})
  public String orderPay(@PathVariable("orderId") int orderId,
      @RequestParam("totalMoneyInOrder") float totalMoneyInOrder, Model model) {
    int deliveryNumber, productStock;
    Product deliveryProduct;
    List<OrderItem> orderItemList = orderDao.getOrderById(orderId).getOrderItems();
    //修改每个订单项里产品的库存
    for (OrderItem orderItem : orderItemList) {
      deliveryNumber = orderItem.getNumber();
      deliveryProduct = orderItem.getProduct();
      productStock = deliveryProduct.getStock();
      if (deliveryNumber <= productStock) {
        productStock -= deliveryNumber;
        deliveryProduct.setStock(productStock);
        productDao.updateProduct(deliveryProduct);
      } else {
        throw new RuntimeException("库存不足！");
      }
    }

    Order order = orderDao.getOrderById(orderId);
    order.setOrderStatus(OrderStatusEnum.WAIT_DELIVERY);
    order.setPayDate(new Date());
    orderDao.updateOrder(order);

    model.addAttribute("totalMoneyInOrder", totalMoneyInOrder);
    model.addAttribute(order);
    return "payed";
  }
}
