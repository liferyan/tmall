<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>

<script>
  var deleteOrder = false;
  var deleteOrderid = 0;
  $(function () {
    $("a[orderStatus]").click(function () {
      var orderStatus = $(this).attr("orderStatus");
      if ('ALL' == orderStatus) {
        $("table[orderStatus]").show();
      }
      else {
        $("table[orderStatus]").hide();
        $("table[orderStatus=" + orderStatus + "]").show();
      }
      $("div.orderType div").removeClass("selectedOrderType");
      $(this).parent("div").addClass("selectedOrderType");
    });

    $("a.deleteOrderLink").click(function () {
      deleteOrderid = $(this).attr("oid");
      deleteOrder = false;
      $("#deleteConfirmModal").modal("show");
    });

    $("button.deleteConfirmButton").click(function () {
      deleteOrder = true;
      $("#deleteConfirmModal").modal('hide');
    });

    $('#deleteConfirmModal').on('hidden.bs.modal', function (e) {
      if (deleteOrder) {
        var page = "foredeleteOrder";
        $.post(
            page,
            {"oid": deleteOrderid},
            function (result) {
              if ("success" == result) {
                $("table.orderListItemTable[oid=" + deleteOrderid + "]").hide();
              }
              else {
                location.href = "../../WEB-INF/views/login.jsp";
              }
            }
        );
      }
    })

    $(".ask2delivery").click(function () {
      var link = $(this).attr("link");
      $(this).hide();
      page = link;
      $.ajax({
        url: page,
        success: function (result) {
          alert("卖家已秒发，刷新当前页面，即可进行确认收货")
        }
      });
    });
  });

</script>

<div class="boughtDiv">
    <div class="orderType">
        <div class="selectedOrderType"><a orderStatus="ALL" href="#nowhere">所有订单</a></div>
        <div><a orderStatus="WAIT_PAY" href="#nowhere">待付款</a></div>
        <div><a orderStatus="WAIT_DELIVERY" href="#nowhere">待发货</a></div>
        <div><a orderStatus="WAIT_CONFIRM" href="#nowhere">待收货</a></div>
        <div><a orderStatus="WAIT_REVIEW" href="#nowhere" class="noRightborder">待评价</a></div>
        <div class="orderTypeLastOne"><a class="noRightborder">&nbsp;</a></div>
    </div>
    <div style="clear:both"></div>
    <div class="orderListTitle">
        <table class="orderListTitleTable">
            <tr>
                <td>宝贝</td>
                <td width="100px">单价</td>
                <td width="100px">数量</td>
                <td width="120px">实付款</td>
                <td width="100px">交易操作</td>
            </tr>
        </table>
    </div>

    <div class="orderListItem">
        <c:forEach items="${order_list}" var="order">
            <table class="orderListItemTable" orderStatus="${order.orderStatus.code}"
                   oid="${order.id}">
                <tr class="orderListItemFirstTR">
                    <td colspan="2">
                        <b><fmt:formatDate value="${order.createDate}"
                                           pattern="yyyy-MM-dd HH:mm:ss"/></b>
                        <span>订单号: ${order.orderCode}
					</span>
                    </td>
                    <td colspan="2"><img width="13px" src="img/site/orderItemTmall.png">天猫商场</td>
                    <td colspan="1">
                        <a class="wangwanglink" href="#nowhere">
                            <div class="orderItemWangWangGif"></div>
                        </a>
                    </td>
                    <td class="orderItemDeleteTD">
                        <a class="deleteOrderLink" oid="${order.id}" href="#nowhere">
                            <span class="orderListItemDelete glyphicon glyphicon-trash"></span>
                        </a>
                    </td>
                </tr>
                <c:forEach items="${order.orderItems}" var="orderItem" varStatus="st">
                    <tr class="orderItemProductInfoPartTR">
                        <td class="orderItemProductInfoPartTD">
                            <img width="80" height="80"
                                 src="img/productSingle_middle/${orderItem.product.firstProductImage.id}.jpg">
                        </td>
                        <td class="orderItemProductInfoPartTD">
                            <div class="orderListItemProductLinkOutDiv">
                                <a href="foreproduct?pid=${orderItem.product.id}">
                                        ${orderItem.product.name}</a>
                                <div class="orderListItemProductLinkInnerDiv">
                                    <img src="img/site/creditcard.png" title="支持信用卡支付">
                                    <img src="img/site/7day.png" title="消费者保障服务,承诺7天退货">
                                    <img src="img/site/promise.png" title="消费者保障服务,承诺如实描述">
                                </div>
                            </div>
                        </td>

                        <td class="orderItemProductInfoPartTD" width="100px">
                            <div class="orderListItemProductOriginalPrice">￥<fmt:formatNumber
                                    type="number" value="${orderItem.product.originalPrice}"
                                    minFractionDigits="2"/></div>
                            <div class="orderListItemProductPrice">
                                ￥<fmt:formatNumber type="number"
                                                   value="${orderItem.product.promotePrice}"
                                                   minFractionDigits="2"/></div>
                        </td>
                        <td valign="top"
                            class="orderListItemNumberTD orderItemOrderInfoPartTD"
                            width="100px">
                            <span class="orderListItemNumber">${orderItem.number}</span>
                        </td>
                        <td valign="top" width="120px"
                            class="orderListItemProductRealPriceTD orderItemOrderInfoPartTD">
                            <div class="orderListItemProductRealPrice">￥<fmt:formatNumber
                                    minFractionDigits="2" maxFractionDigits="2" type="number"
                                    value="${orderItem.number * orderItem.product.promotePrice}"/></div>
                            <div class="orderListItemPriceWithTransport">(含运费：￥0.00)</div>
                        </td>
                        <c:if test="${orderItem.hasReview eq true}">
                            <td valign="top"
                                class="orderListItemButtonTD orderItemOrderInfoPartTD"
                                width="100px">
                                <span>已评价</span>
                            </td>
                        </c:if>
                        <c:if test="${order.orderStatus.code eq 'WAIT_REVIEW' and orderItem.hasReview eq false}">
                            <td valign="top"
                                class="orderListItemButtonTD orderItemOrderInfoPartTD"
                                width="100px">
                                <a href="forereview?oid=${order.id}&pid=${orderItem.product.id}&oiid=${orderItem.id}">
                                    <button class="orderListItemReview">评价</button>
                                </a>
                            </td>
                        </c:if>
                        <c:if test="${st.count eq 1 and order.orderStatus.code ne 'WAIT_REVIEW'
                        and order.orderStatus.code ne 'FINISHED'}">
                            <td valign="top" rowspan="${fn:length(order.orderItems)}"
                                class="orderListItemButtonTD orderItemOrderInfoPartTD"
                                width="100px">
                                <c:if test="${order.orderStatus.code eq 'WAIT_PAY'}">
                                    <a href="forealipay?oid=${order.id}&total=${order.total}">
                                        <button class="orderListItemConfirm">付款</button>
                                    </a>
                                </c:if>
                                <c:if test="${order.orderStatus.code eq 'WAIT_DELIVERY'}">
                                    <span>待发货</span>
                                </c:if>
                                <c:if test="${order.orderStatus.code eq 'WAIT_CONFIRM'}">
                                    <a href="foreconfirmPay?oid=${order.id}">
                                        <button class="orderListItemConfirm">确认收货</button>
                                    </a>
                                </c:if>
                                <c:if test="${order.orderStatus.code eq 'WAIT_REVIEW'}">
                                    <a href="forereview?oid=${order.id}">
                                        <button class="orderListItemReview">评价</button>
                                    </a>
                                </c:if>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:forEach>
    </div>
</div>
