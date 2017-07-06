<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>

<div class="reviewDiv">
    <div class="reviewProductInfoDiv">
        <div class="reviewProductInfoImg"><img width="400px" height="400px"
                                               src="${ctx}/img/productSingle/${product.firstProductImage.id}.jpg">
        </div>
        <div class="reviewProductInfoRightDiv">
            <div class="reviewProductInfoRightText">
                ${product.name}
            </div>
            <table class="reviewProductInfoTable">
                <tr>
                    <td width="75px">价格:</td>
                    <td><span class="reviewProductInfoTablePrice">￥<fmt:formatNumber type="number"
                                                                                     value="${product.originalPrice}"
                                                                                     minFractionDigits="2"/></span>
                        元
                    </td>
                </tr>
                <tr>
                    <td>配送</td>
                    <td>快递: 0.00</td>
                </tr>
                <tr>
                    <td>月销量:</td>
                    <td><span class="reviewProductInfoTableSellNumber">${product.saleCount}</span> 件
                    </td>
                </tr>
            </table>

            <div class="reviewProductInfoRightBelowDiv">
                <span class="reviewProductInfoRightBelowImg"><img
                        src="${ctx}/img/site/reviewLight.png"/></span>
                <span class="reviewProductInfoRightBelowText">现在查看的是 您所购买商品的信息
于<fmt:formatDate value="${order.createDate}" pattern="yyyy年MM月dd"/>下单购买了此商品 </span>
            </div>
        </div>
        <div style="clear:both"></div>
    </div>
    <div class="reviewStasticsDiv">
        <div class="reviewStasticsLeft">
            <div class="reviewStasticsLeftTop"></div>
            <div class="reviewStasticsLeftContent">累计评价 <span
                    class="reviewStasticsNumber"> ${product.reviewCount}</span></div>
            <div class="reviewStasticsLeftFoot"></div>
        </div>
        <div class="reviewStasticsRight">
            <div class="reviewStasticsRightEmpty"></div>
            <div class="reviewStasticsFoot"></div>
        </div>
    </div>

    <c:if test="${!empty showReviewList}">
        <div class="reviewDivlistReviews">
            <c:forEach items="${reviewList}" var="review">
                <div class="reviewDivlistReviewsEach">
                    <div class="reviewDate"><fmt:formatDate value="${review.createDate}"
                                                            pattern="yyyy-MM-dd"/></div>
                    <div class="reviewContent">${review.content}</div>
                    <div class="reviewUserInfo pull-right">${review.user.anonymousName}<span
                            class="reviewUserInfoAnonymous">(匿名)</span></div>
                </div>
            </c:forEach>
        </div>
    </c:if>

    <c:if test="${empty showReviewList}">
        <div class="makeReviewDiv">
            <form method="post" action="${ctx}/order/doreview">
                <div class="makeReviewText">其他买家，需要你的建议哦！</div>
                <table class="makeReviewTable">
                    <tr>
                        <td class="makeReviewTableFirstTD">评价商品</td>
                        <td><textarea name="content"></textarea></td>
                    </tr>
                </table>
                <div class="makeReviewButtonDiv">
                    <input type="hidden" name="oid" value="${order.id}">
                    <input type="hidden" name="pid" value="${product.id}">
                    <input type="hidden" name="oiid" value="${orderItem.id}">
                    <button type="submit">提交评价</button>
                </div>
            </form>
        </div>
    </c:if>
</div>
