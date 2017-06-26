<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
  $(function () {
    $("#addForm").submit(function () {
      return checkEmpty("name", "属性名称");
    });
  });
</script>

<title>属性管理</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_property_list?cid=${category.id}">${category.name}</a></li>
        <li>属性管理</li>
    </ol>
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>属性名称</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${property_list}" var="property">
                <tr>
                    <td>${property.id}</td>
                    <td>${property.name}</td>
                    <td><a href="admin_property_edit?id=${property.id}"><span
                            class="glyphicon glyphicon-edit"></span></a></td>
                    <td><a deleteLink="true" href="admin_property_delete?id=${property.id}"><span
                            class="glyphicon glyphicon-trash"></span></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<div class="pageDiv">
    <%@include file="../include/admin/adminPage.jsp" %>
</div>

<div class="addDiv">
    <div class="panel panel-warning">
        <div class="panel-heading">新增属性</div>
        <div class="panel-body">
            <form method="post" id="addForm" action="admin_property_add">
                <table class="addTable">
                    <tr>
                        <td><label for="name">属性名称</label></td>
                        <td><input id="name" type="text" name="name" class="form-control"></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="hidden" name="cid" value="${category.id}">
                            <button type="submit" class="btn btn-success">提 交
                            </button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>


<%@include file="../include/admin/adminFooter.jsp" %>
