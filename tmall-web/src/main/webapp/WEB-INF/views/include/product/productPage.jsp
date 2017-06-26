<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>

<title>模仿天猫官网 ${product.name}</title>
<div class="categoryPictureInProductPageDiv">
    <a href="forecategory?cid=${product.category.id}"><img class="categoryPictureInProductPage"
                                                           src="img/category/${product.category.id}.jpg"></a>
</div>

<div class="productPageDiv">
    <%@ include file="imgAndInfo.jsp" %>
    <%@ include file="productReview.jsp" %>
    <%@ include file="productDetail.jsp" %>
</div>