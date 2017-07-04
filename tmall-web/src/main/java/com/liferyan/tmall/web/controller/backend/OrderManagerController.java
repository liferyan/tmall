package com.liferyan.tmall.web.controller.backend;

import com.liferyan.tmall.data.dao.OrderDao;
import com.liferyan.tmall.data.dao.ProductDao;
import com.liferyan.tmall.data.entity.Order;
import com.liferyan.tmall.data.entity.OrderItem;
import com.liferyan.tmall.data.entity.OrderStatusEnum;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.web.util.Page;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Ryan on 2017/7/4.
 */
@Controller
@RequestMapping("/admin")
public class OrderManagerController {

  private OrderDao orderDao;

  private ProductDao productDao;

  @Autowired
  public OrderManagerController(OrderDao orderDao, ProductDao productDao) {
    this.orderDao = orderDao;
    this.productDao = productDao;
  }

  @GetMapping("/orders")
  public String showOrderList(
      @RequestParam(name = "page.start", defaultValue = "0") int pageStart,
      @RequestParam(name = "page.count", defaultValue = "5") int pageCount, Model model) {
    if (pageCount == 5) {
      pageCount = 15;
    }
    Page page = new Page(pageStart, pageCount);
    page.setTotal(orderDao.getOrderCount());
    model.addAttribute(orderDao.listOrderByPage(pageStart, pageCount));
    model.addAttribute(page);
    return "admin/listOrder";
  }

  @GetMapping("/orderDelivery/{orderId}")
  public String orderDelivery(@PathVariable("orderId") int orderId) {
    Order order = orderDao.getOrderById(orderId);
    order.setDeliveryDate(new Date());
    order.setOrderStatus(OrderStatusEnum.WAIT_CONFIRM);
    orderDao.updateOrder(order);

    List<OrderItem> orderItemList = order.getOrderItems();
    int deliveryNumber, productStock;
    Product deliveryProduct;
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
    return "redirect:/orders";
  }
}
