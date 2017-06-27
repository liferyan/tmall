<%@page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%--BootStrap导航条--%>
<nav class="navbar navbar-fixed-top navbar-inverse">
    <img class="pull-left" src="${ctx}/img/site/tmallbuy.png" height="45px"
         style="margin-left: 10px">
    <%--<a class="navbar-brand" href="#nowhere">天猫后台</a>--%>
    <a class="navbar-brand" href="${ctx}/admin/categories">分类管理</a>
    <a class="navbar-brand" href="${ctx}/admin/users">用户管理</a>
    <a class="navbar-brand" href="${ctx}/admin/orders">订单管理</a>
</nav>