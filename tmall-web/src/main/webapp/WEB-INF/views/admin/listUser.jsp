<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<title>用户管理</title>

<div class="workingArea">
    <p class="bg-success success">操作成功！</p>
    <div style="margin-bottom: 20px">
        <span class="label label-info">用户管理</span>
    </div>
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed"
               style="margin-bottom: 0">
            <thead>
            <tr class="bg-success">
                <th>ID</th>
                <th>用户名称</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${user_list}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="pageDiv">
    <%@ include file="../include/admin/adminPage.jsp" %>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>