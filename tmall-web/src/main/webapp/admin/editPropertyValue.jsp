<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
  $(function () {
    $("input.pvValue").keyup(function () {
      var value = $(this).val();
      var page = "admin_product_updatePropertyValue";
      var pvid = $(this).attr("pvid");
      var parentSpan = $(this).parent("span");
      parentSpan.css("border", "1px solid yellow");
      $.post(
          page,
          {"value": value, "pvid": pvid},
          function (result) {
            if ("success" == result)
              parentSpan.css("border", "1px solid green");
            else
              parentSpan.css("border", "1px solid red");
          }
      );
    });
  });
</script>

<title>编辑产品属性</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?cid=${category.id}">${category.name}</a></li>
        <li>${product.name}</li>
        <li>编辑产品属性</li>
    </ol>
    <div class="editPVDiv">
        <c:forEach items="${property_value_list}" var="property_value">
            <div class="eachPV">
                <span class="pvName">${property_value.property.name}</span>
                <span class="pvValue"><input class="pvValue" pvid="${property_value.id}" type="text"
                                             value="${property_value.value}"
                                             style="width:198px"></span>
            </div>
        </c:forEach>
        <div style="clear:both"></div>
    </div>

</div>

<%@include file="../include/admin/adminFooter.jsp" %>