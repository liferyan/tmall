<%@page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="${ctx}/css/backend/style.css" rel="stylesheet">

    <%--页面通用JS函数--%>
    <script>
      //检查ID对应的元素是否为空
      function checkEmpty(id, name) {
        var value = $("#" + id).val();
        if (value.length == 0) {
          alert(name + "不能为空");
          $("#" + id)[0].focus();
          return false;
        }
        return true;
      }
      //检查ID对应的元素是否为数字
      function checkNumber(id, name) {
        var value = $("#" + id).val();
        if (value.length == 0) {
          alert(name + "不能为空");
          $("#" + id)[0].focus();
          return false;
        }
        if (isNaN(value)) {
          alert(name + "不是数字");
          $("#" + id)[0].focus();
          return false;
        }
        return true;
      }
      //检查ID对应的元素是否为整数
      function checkInt(id, name) {
        var value = $("#" + id).val();
        if (value.length == 0) {
          alert(name + "不能为空");
          $("#" + id)[0].focus();
          return false;
        }
        if (parseInt(value) != value) {
          alert(name + "必须是整数");
          $("#" + id)[0].focus();
          return false;
        }
        return true;
      }
      //在删除链接点击后弹出确认删除
      $(function () {
        $("a").click(function () {
          var deleteLink = $(this).attr("deleteLink");
          if ("true" == deleteLink) {
            var confirmDelete = confirm("确认要删除");
            if (confirmDelete)
              return true;
            return false;
          }
        });
      });
      //显示操作成功
      $(function () {
        <c:if test="${success ne null}">
        $("p.bg-success").show().delay(3000).hide(0);
        </c:if>
      });
    </script>
</head>
<body>
