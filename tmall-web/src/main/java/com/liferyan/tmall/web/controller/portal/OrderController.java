package com.liferyan.tmall.web.controller.portal;

import com.liferyan.tmall.data.dao.OrderDao;
import com.liferyan.tmall.data.dao.OrderItemDao;
import com.liferyan.tmall.data.entity.Order;
import com.liferyan.tmall.data.entity.OrderItem;
import com.liferyan.tmall.data.entity.OrderStatusEnum;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Ryan on 2017/7/5.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

  private OrderDao orderDao;

  private OrderItemDao orderItemDao;

  @Autowired
  public OrderController(OrderDao orderDao, OrderItemDao orderItemDao) {
    this.orderDao = orderDao;
    this.orderItemDao = orderItemDao;
  }

  @PostMapping("/add")
  public String createOrder(@Valid Order order, BindingResult result,
      HttpSession session, Model model) {
    model.addAttribute("totalMoneyInOrder", order.getTotal());
    if (result.hasErrors()) {
      return "buy";
    }
    User user = (User) session.getAttribute("user");
    List<OrderItem> orderItemList = (List<OrderItem>) session.getAttribute("orderItemList");

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
    model.addAttribute(order);
    return "alipay";
  }

  @PostMapping("/pay/{orderId}")
  public String orderPay(@PathVariable("orderId") int orderId,
      @RequestParam("totalMoneyInOrder") float totalMoneyInOrder, Model model) {
    Order order = orderDao.getOrderById(orderId);
    order.setOrderStatus(OrderStatusEnum.WAIT_DELIVERY);
    order.setPayDate(new Date());
    orderDao.updateOrder(order);
    model.addAttribute("totalMoneyInOrder", totalMoneyInOrder);
    model.addAttribute(order);
    return "payed";
  }
}
