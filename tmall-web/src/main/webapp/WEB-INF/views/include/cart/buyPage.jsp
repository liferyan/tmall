<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="buyPageDiv">
    <sf:form commandName="order" action="${ctx}/order/add" method="post">
        <div class="buyFlow">
            <img class="pull-left" src="${ctx}/img/site/simpleLogo.png">
            <img class="pull-right" src="${ctx}/img/site/buyflow.png">
            <div style="clear:both"></div>
        </div>
        <div class="address">
            <div class="addressTip">输入收货地址</div>
            <div>
                <table class="addressTable">
                    <tr>
                        <td class="firstColumn">详细地址<span class="redStar">*</span></td>
                        <td><sf:input path="address"/>
                            <sf:errors path="address" cssClass="text-danger errors"/>
                        </td>
                    </tr>
                    <tr>
                        <td>邮政编码</td>
                        <td><sf:input path="post"/></td>
                    </tr>
                    <tr>
                        <td>收货人姓名<span class="redStar">*</span></td>
                        <td><sf:input path="receiver"/>
                            <sf:errors path="receiver" cssClass="text-danger errors"/>
                        </td>
                    </tr>
                    <tr>
                        <td>手机号码 <span class="redStar">*</span></td>
                        <td><sf:input path="mobile"/>
                            <sf:errors path="mobile" cssClass="text-danger errors"/>
                        </td>
                    </tr>
                </table>

            </div>
        </div>
        <div class="productList">
            <div class="productListTip">确认订单信息</div>
            <table class="productListTable">
                <thead>
                <tr>
                    <th colspan="2" class="productListTableFirstColumn">
                        <img class="tmallbuy" src="${ctx}/img/site/tmallbuy.png">
                        <a class="marketLink" href="#nowhere">店铺：天猫店铺</a>
                        <a class="wangwanglink" href="#nowhere"> <span class="wangwangGif"></span>
                        </a>
                    </th>
                    <th>单价</th>
                    <th>数量</th>
                    <th>小计</th>
                    <th>配送方式</th>
                </tr>
                <tr class="rowborder">
                    <td colspan="2"></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </thead>
                <tbody class="productListTableTbody">
                <c:forEach items="${orderItemList}" var="orderItem" varStatus="st">
                    <tr class="orderItemTR">
                        <td class="orderItemFirstTD"><img class="orderItemImg"
                                                          src="${ctx}/img/productSingle_middle/${orderItem.product.firstProductImage.id}.jpg">
                        </td>
                        <td class="orderItemProductInfo">
                            <a href="${ctx}/product/${orderItem.product.id}"
                               class="orderItemProductLink">
                                    ${orderItem.product.name}
                            </a>
                            <img src="${ctx}/img/site/creditcard.png" title="支持信用卡支付">
                            <img src="${ctx}/img/site/7day.png" title="消费者保障服务,承诺7天退货">
                            <img src="${ctx}/img/site/promise.png" title="消费者保障服务,承诺如实描述">

                        </td>
                        <td>
                            <span class="orderItemProductPrice">￥<fmt:formatNumber type="number"
                                                                                   value="${orderItem.product.promotePrice}"
                                                                                   minFractionDigits="2"/></span>
                        </td>
                        <td>
                            <span class="orderItemProductNumber">${orderItem.number}</span>
                        </td>
                        <td><span class="orderItemUnitSum">
						￥<fmt:formatNumber type="number"
                                           value="${orderItem.number*orderItem.product.promotePrice}"
                                           minFractionDigits="2"/>
						</span></td>
                        <c:if test="${st.count==1}">
                            <td rowspan="5" class="orderItemLastTD">
                                <label class="orderItemDeliveryLabel">
                                    <input type="radio" value="" checked="checked">
                                    普通配送
                                </label>

                                <select class="orderItemDeliverySelect" class="form-control">
                                    <option>快递 免邮费</option>
                                </select>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
            <div class="orderItemSumDiv">
                <div class="pull-left">
                    <span class="leaveMessageText">给卖家留言:</span>
                    <span>
					<img class="leaveMessageImg" src="${ctx}/img/site/leaveMessage.png">
				</span>
                    <span class="leaveMessageTextareaSpan">
					<sf:textarea path="userMessage" class="leaveMessageTextarea"/>
					<div>
						<span>还可以输入200个字符</span>
					</div>
				</span>
                </div>
                <span class="pull-right">店铺合计(含运费): ￥
                    <fmt:formatNumber type="number" value="${totalMoneyInOrder}"
                                      minFractionDigits="2"/></span>
            </div>


        </div>

        <div class="orderItemTotalSumDiv">
            <div class="pull-right">
                <span>实付款：</span>
                <span class="orderItemTotalSumSpan">￥
                    <fmt:formatNumber type="number" value="${totalMoneyInOrder}"
                                      minFractionDigits="2"/></span>
            </div>
        </div>

        <sf:hidden path="total" value="${totalMoneyInOrder}"/>

        <div class="submitOrderDiv">
            <button type="submit" class="submitOrderButton">提交订单</button>
        </div>
    </sf:form>
</div>
