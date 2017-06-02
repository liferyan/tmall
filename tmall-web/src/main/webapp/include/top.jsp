<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<nav class="top ">
    <a href="${applicationScope.contextPath}">
        <span style="color:#C40000;margin:0" class=" glyphicon glyphicon-home redColor"></span>
        天猫首页
    </a>

    <span>喵，欢迎来天猫</span>

    <c:if test="${!empty sessionScope.user}">
        <a href="#nowhere">${sessionScope.user.name}</a>
        <a href="forelogout">退出</a>
    </c:if>

    <c:if test="${empty sessionScope.user}">
        <a href="login.jsp">请登录</a>
        <a href="register.jsp">免费注册</a>
    </c:if>


    <span class="pull-right">
			<a href="forebought">我的订单</a>
			<a href="forecart">
			<span style="color:#C40000;margin:0"
                  class=" glyphicon glyphicon-shopping-cart redColor"></span>
			购物车<strong>${requestScope.cartItemCount}</strong>件</a>
		</span>


</nav>


