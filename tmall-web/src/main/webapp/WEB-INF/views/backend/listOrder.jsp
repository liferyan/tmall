<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ page import="com.liferyan.tmall.data.entity.OrderStatusEnum" %>
<%@ include file="../include/backend/adminHeader.jsp" %>
<%@ include file="../include/backend/adminNavigator.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
  $(function () {
    $("button.orderPageCheckOrderItems").click(function () {
      var oid = $(this).attr("oid");
      $("tr.orderPageOrderItemTR[oid=" + oid + "]").toggle();
    });
  });
</script>

<title>订单管理</title>

<div class="workingArea">
    <p class="bg-success success">操作成功！</p>
    <div style="margin-bottom: 20px">
        <span class="label label-info">订单管理</span>
    </div>
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed"
               style="margin-bottom: 0">
            <thead>
            <tr class="bg-success">
                <th>ID</th>
                <th>状态</th>
                <th>金额</th>
                <th width="100px">商品数量</th>
                <th width="100px">买家名称</th>
                <th>创建时间</th>
                <th>支付时间</th>
                <th>发货时间</th>
                <th>确认收货时间</th>
                <th width="120px">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orderList}" var="order">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.orderStatus.description}</td>
                    <td>￥<fmt:formatNumber value="${order.total}" minFractionDigits="2"
                                           type="number"/></td>
                    <td align="center">${order.totalNumber}</td>
                    <td align="center">${order.user.name}</td>
                    <td><fmt:formatDate value="${order.createDate}"
                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${order.payDate}"
                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${order.deliveryDate}"
                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${order.confirmDate}"
                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>
                        <button oid="${order.id}"
                                class="orderPageCheckOrderItems btn btn-primary btn-xs">查看详情
                        </button>
                        <c:set var="statusDelivery" value="<%=OrderStatusEnum.WAIT_DELIVERY%>"/>
                        <c:if test="${order.orderStatus eq statusDelivery}">
                            <a href="orderDelivery/${order.id}">
                                <button class="btn btn-primary btn-xs">发货</button>
                            </a>
                        </c:if>
                    </td>
                </tr>
                <tr class="orderPageOrderItemTR" oid="${order.id}">
                    <td colspan="10" align="center">
                        <div class="orderPageOrderItem">
                            <table width="800px" align="center" class="orderPageOrderItemTable">
                                <c:forEach items="${order.orderItems}" var="orderItem">
                                    <tr>
                                        <td align="left">
                                            <img width="40px" height="40px"
                                                 src="${ctx}/img/productSingle/${orderItem.product.firstProductImage.id}.jpg">
                                        </td>
                                        <td>
                                            <a href="${ctx}/product/${orderItem.product.id}">
                                                <span>${orderItem.product.name}</span>
                                            </a>
                                        </td>
                                        <td align="right">
                                            <span class="text-muted">${orderItem.number}个</span>
                                        </td>
                                        <td align="right">
                                            <span class="text-muted">单价：￥${orderItem.product.promotePrice}</span>
                                        </td>

                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="pageDiv">
    <%@ include file="../include/backend/adminPage.jsp" %>
</div>

<%@include file="../include/backend/adminFooter.jsp" %>