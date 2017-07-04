<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<div class="productDetailDiv">
    <div class="productDetailTopPart">
        <a href="#nowhere" class="productDetailTopPartSelectedLink selected">商品详情</a>
        <a href="#nowhere" class="productDetailTopReviewLink">累计评价 <span
                class="productDetailTopReviewLinkNumber">${product.reviewCount}</span> </a>
    </div>


    <div class="productParamterPart">
        <div class="productParamter">产品参数：</div>

        <div class="productParamterList">
            <c:forEach items="${propertyValueList}" var="pv">
                <span>${pv.property.name}:  ${fn:substring(pv.value, 0, 10)} </span>
            </c:forEach>
        </div>
        <div style="clear:both"></div>
    </div>

    <div class="productDetailImagesPart">
        <c:forEach items="${product.detailProductImageList}" var="pi">
            <img src="${ctx}/img/productDetail/${pi.id}.jpg">
        </c:forEach>
    </div>
</div>