<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<title>模仿天猫官网-${category.name}</title>
<div id="category">
    <div class="categoryPageDiv">
        <img src="${ctx}/img/category/${category.id}.jpg">
        <%@ include file="sortBar.jsp" %>
        <%@ include file="productsByCategory.jsp" %>
    </div>

</div>
