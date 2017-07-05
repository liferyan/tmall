package com.liferyan.tmall.web.controller.portal;

import com.liferyan.tmall.data.dao.OrderItemDao;
import com.liferyan.tmall.data.dao.ProductDao;
import com.liferyan.tmall.data.entity.Order;
import com.liferyan.tmall.data.entity.OrderItem;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.data.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ryan on 2017/7/5.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

  private OrderItemDao orderItemDao;

  private ProductDao productDao;

  @Autowired
  public CartController(OrderItemDao orderItemDao, ProductDao productDao) {
    this.orderItemDao = orderItemDao;
    this.productDao = productDao;
  }

  @GetMapping("/add/{productId}")
  public String buyOne(@PathVariable("productId") int productId,
      @RequestParam("num") int productNum, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "redirect:/user/login";
    }
    OrderItem orderItem = addOrderItem(user, productId, productNum);
    return "forward:/cart/settle?oiid=" + orderItem.getId();
  }

  @ResponseBody
  @GetMapping("/addAjax")
  public String addCartAjax(@RequestParam("productId") int productId,
      @RequestParam("num") int productNum, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "fail";
    }
    OrderItem orderItem = addOrderItem(user, productId, productNum);
    return "success";
  }

  @GetMapping("/settle")
  public String showSettlePage(@RequestParam("oiid") int[] orderItemIds,
      Model model, HttpSession session) {
    float totalMoneyInOrder = 0;
    Product productInOrderItem;
    List<OrderItem> orderItemList = new ArrayList<>();
    for (int orderItemId : orderItemIds) {
      OrderItem orderItem = orderItemDao.getOrderItemById(orderItemId);
      if (orderItem != null) {
        productInOrderItem = orderItem.getProduct();
        totalMoneyInOrder += productInOrderItem.getPromotePrice() * orderItem.getNumber();
        orderItemList.add(orderItem);
      }
    }
    if (!model.containsAttribute("order")) {
      model.addAttribute(new Order());
    }
    session.setAttribute("orderItemList", orderItemList);
    model.addAttribute("totalMoneyInOrder", totalMoneyInOrder);
    return "buy";
  }

  private OrderItem addOrderItem(User user, int productId, int productNum) {
    Product product = productDao.getProductById(productId);
    Order order = new Order();
    order.setId(-1);
    OrderItem orderItem = new OrderItem();
    orderItem.setOrder(order);
    orderItem.setProduct(product);
    orderItem.setUser(user);
    orderItem.setNumber(productNum);
    orderItem.setHasReview(false);
    orderItemDao.saveOrderItem(orderItem);
    return orderItem;
  }
}
