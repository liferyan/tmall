<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>

    <%--Bootstrap 不支持 IE 古老的兼容模式。为了让 IE 浏览器运行最新的渲染模式下，建议加入下面标签--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <%--导入JQuery--%>
    <script src="${ctx}/js/jquery/jquery-3.2.1.min.js"></script>
    <%--导入BootStrap CSS--%>
    <link href="${ctx}/css/bootstrap/3.3.7/bootstrap.css" rel="stylesheet">
    <%--导入BootStrap JS--%>
    <script src="${ctx}/js/bootstrap/3.3.7/bootstrap.min.js"></script>
    <%--导入自定义 CSS--%>
    <link href="${ctx}/css/portal/style.css" rel="stylesheet">

    <%--页面通用JS函数--%>
    <script>
      function formatMoney(num) {
        num = num.toString().replace(/\$|\,/g, '');
        if (isNaN(num))
          num = "0";
        sign = (num == (num = Math.abs(num)));
        num = Math.floor(num * 100 + 0.50000000001);
        cents = num % 100;
        num = Math.floor(num / 100).toString();
        if (cents < 10)
          cents = "0" + cents;
        for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
          num = num.substring(0, num.length - (4 * i + 3)) + ',' +
              num.substring(num.length - (4 * i + 3));
        return (((sign) ? '' : '-') + num + '.' + cents);
      }
      function checkEmpty(id, name) {
        var value = $("#" + id).val();
        if (value.length == 0) {

          $("#" + id)[0].focus();
          return false;
        }
        return true;
      }

      $(function () {

        $("a.productDetailTopReviewLink").click(function () {
          $("div.productReviewDiv").show();
          $("div.productDetailDiv").hide();
        });
        $("a.productReviewTopPartSelectedLink").click(function () {
          $("div.productReviewDiv").hide();
          $("div.productDetailDiv").show();
        });

        $("span.leaveMessageTextareaSpan").hide();
        $("img.leaveMessageImg").click(function () {

          $(this).hide();
          $("span.leaveMessageTextareaSpan").show();
          $("div.orderItemSumDiv").css("height", "100px");
        });

        $("div#footer a[href$=#nowhere]").click(function () {
          alert("模仿天猫的连接，并没有跳转到实际的页面");
        });

        $("a.wangwanglink").click(function () {
          alert("模仿旺旺的图标，并不会打开旺旺");
        });
        $("a.notImplementLink").click(function () {
          alert("这个功能没做，蛤蛤~");
        });

      });

    </script>

</head>
<body>
