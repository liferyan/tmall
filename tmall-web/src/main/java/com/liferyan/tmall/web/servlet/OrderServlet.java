package com.liferyan.tmall.web.servlet;

import com.liferyan.tmall.data.entity.Order;
import com.liferyan.tmall.data.entity.OrderItem;
import com.liferyan.tmall.data.entity.OrderStatusEnum;
import com.liferyan.tmall.data.entity.Product;
import com.liferyan.tmall.web.util.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ryan on 2017/5/6.
 * 订单Servlet
 */
public class OrderServlet extends BaseBackServlet {

  @Override
  public String add(HttpServletRequest request) {
    return null;
  }

  @Override
  public String delete(HttpServletRequest request) {
    return null;
  }

  @Override
  public String update(HttpServletRequest request) {
    return null;
  }

  @Override
  public String list(HttpServletRequest request, Page page) {
    if (page.getCount() == 5) {
      page.setCount(15);
    }
    List<Order> orderList = orderDao.listOrderByPage(page.getStart(), page.getCount());
    page.setTotal(orderDao.getOrderCount());
    request.setAttribute("order_list", orderList);
    request.setAttribute("page", page);
    return "admin/listOrder.jsp";
  }

  @Override
  public String edit(HttpServletRequest request) {
    return null;
  }

  /**
   * 后台发货
   */
  public String delivery(HttpServletRequest request) {
    int oid = Integer.parseInt(request.getParameter("oid"));
    Order order = orderDao.getOrderById(oid);
    order.setDeliveryDate(new Date());
    order.setOrderStatus(OrderStatusEnum.WAIT_CONFIRM);
    logger.info("发货订单：{}", order);
    orderDao.updateOrder(order);

    //发货后将每个订单项的产品库存减少
    List<OrderItem> orderItemList = order.getOrderItems();
    Product product;
    int deliveryNumber;
    int stock;
    for (OrderItem orderItem : orderItemList) {
      deliveryNumber = orderItem.getNumber();
      product = orderItem.getProduct();
      stock = product.getStock();
      stock -= deliveryNumber;
      if (stock >= 0) {
        logger.info("发货前的产品：{}", product);
        product.setStock(stock);
        logger.info("发货后的产品：{}", product);
        productDao.updateProduct(product);
      }
    }
    return "@admin_order_list";
  }
}
