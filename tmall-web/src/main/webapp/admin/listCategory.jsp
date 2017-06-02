<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
  $(function () {
    //表单提交前检查是否为空
    $("#addForm").submit(function () {
      if (!checkEmpty("name", "分类名称")) {
        return false;
      }
      return checkEmpty("category_img", "分类图片");
    });
  });
</script>

<title>分类管理</title>

<div class="workingArea">
    <div style="margin-bottom: 20px">
        <span class="label label-info">分类管理</span>
    </div>
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed"
               style="margin-bottom: 0">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>图片</th>
                <th>分类名称</th>
                <th>属性管理</th>
                <th>产品管理</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${category_list}" var="category">
                <tr>
                    <td>${category.id}</td>
                    <td><img src="img/category/${category.id}.jpg" height="40px"/></td>
                    <td>${category.name}</td>
                    <td><a href="admin_property_list?cid=${category.id}"><span
                            class="glyphicon glyphicon-th-list"></span></a></td>
                    <td><a href="admin_product_list?cid=${category.id}"><span
                            class="glyphicon glyphicon-shopping-cart"></span></a></td>
                    <td><a href="admin_category_edit?id=${category.id}"><span
                            class="glyphicon glyphicon-edit"></span></a></td>
                    <td><a deleteLink="true" href="admin_category_delete?id=${category.id}"><span
                            class="glyphicon glyphicon-trash"></span></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="pageDiv">
    <%@ include file="../include/admin/adminPage.jsp" %>
</div>

<div class="addDiv">
    <div class="panel panel-warning">
        <div class="panel-heading">新增分类</div>
        <div class="panel-body">
            <form method="post" id="addForm" action="admin_category_add"
                  enctype="multipart/form-data">
                <table class="addTable">
                    <tr>
                        <td><label for="name">分类名称</label></td>
                        <td><input id="name" type="text" name="name" class="form-control"></td>
                    </tr>
                    <tr>
                        <td><label for="category_img">分类图片</label></td>
                        <td><input id="category_img" type="file" name="category_file"
                                   accept="image/*">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
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