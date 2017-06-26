<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>

<div class="searchProducts">
    <c:forEach items="${product_list}" var="product">
        <div class="productUnit" price="${product.promotePrice}">
            <a href="foreproduct?pid=${product.id}">
                <img class="productImage"
                     src="img/productSingle/${product.firstProductImage.id}.jpg">
            </a>
            <span class="productPrice">¥<fmt:formatNumber type="number"
                                                          value="${product.promotePrice}"
                                                          minFractionDigits="2"/></span>
            <a class="productLink" href="foreproduct?pid=${product.id}">
                    ${fn:substring(product.name, 0, 50)}
            </a>

            <a class="tmallLink" href="foreproduct?pid=${product.id}">天猫专卖</a>

            <div class="show1 productInfo">
            <span class="monthDeal ">月成交 <span
                    class="productDealNumber">${product.saleCount}笔</span></span>
                <span class="productReview">评价<span
                        class="productReviewNumber">${product.reviewCount}</span></span>
                <span class="wangwang"><img src="img/site/wangwang.png"></span>
            </div>
        </div>
    </c:forEach>
    <c:if test="${empty product_list}">
    <div class="noMatch">没有满足条件的产品
        <div>
            </c:if>

            <div style="clear:both"></div>
        </div>
    </div>
</div>