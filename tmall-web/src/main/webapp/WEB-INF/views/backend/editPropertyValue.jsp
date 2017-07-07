<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ include file="../include/backend/header.jsp" %>
<%@ include file="../include/backend/navigator.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<script>
  $(function () {
    $("input.pvValue").change(function () {
      var value = $(this).val();
      var page = "${ctx}/backend/propertyValues/${product.id}";
      var pvid = $(this).attr("pvid");
      var parentSpan = $(this).parent("span");
      parentSpan.css("border", "1px solid yellow");
      $.post(
          page,
          {"value": value, "id": pvid},
          function (result) {
            if ("success" === result) {
              parentSpan.css("border", "1px solid green");
              $("p.bg-success").show().delay(3000).hide(0);
            }
            else {
              parentSpan.css("border", "1px solid red");
            }
          }
      );
    });
  });
</script>

<title>编辑产品属性</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="${ctx}/backend/categories">所有分类</a></li>
        <li><a href="${ctx}/backend/products/${category.id}">${category.name}</a></li>
        <li>${product.name}</li>
        <li>编辑产品属性</li>
    </ol>
    <div class="editPVDiv">
        <c:forEach items="${propertyValueList}" var="property_value">
            <div class="eachPV">
                <span class="pvName">${property_value.property.name}</span>
                <span class="pvValue"><input class="pvValue" pvid="${property_value.id}" type="text"
                                             value="${property_value.value}"
                                             style="width:198px"></span>
            </div>
        </c:forEach>
        <div style="clear:both"></div>
    </div>

    <p class="bg-success success" style="margin:20px">操作成功！</p>

</div>

<%@include file="../include/backend/footer.jsp" %>