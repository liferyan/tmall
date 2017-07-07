<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ include file="../include/backend/header.jsp" %>
<%@ include file="../include/backend/navigator.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<script>
  $(function () {
    $("#addForm").submit(function () {
      return checkEmpty("name", "属性名称");
    });
  });
</script>

<title>属性管理</title>

<div class="workingArea">
    <p class="bg-success success">操作成功！</p>
    <ol class="breadcrumb">
        <li><a href="${ctx}/backend/categories">所有分类</a></li>
        <li><a href="${category.id}">${category.name}</a></li>
        <li>属性管理</li>
    </ol>
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
            <tr class="bg-success">
                <th>ID</th>
                <th>属性名称</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${propertyList}" var="property">
                <tr>
                    <td>${property.id}</td>
                    <td>${property.name}</td>
                    <td><a href="${ctx}/backend/property/${property.id}"><span
                            class="glyphicon glyphicon-edit"></span></a></td>
                    <td><a deleteLink="true"
                           href="${ctx}/backend/property/${property.id}/delete"><span
                            class="glyphicon glyphicon-trash"></span></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<div class="pageDiv">
    <%@include file="../include/backend/pages.jsp" %>
</div>

<div class="addDiv">
    <div class="panel panel-warning">
        <div class="panel-heading">新增属性</div>
        <div class="panel-body">
            <sf:form commandName="property" method="post" id="addForm">
                <table class="addTable">
                    <tr>
                        <td colspan="2" align="center"><sf:errors path="*"
                                                                  cssClass="text-danger"/></td>
                    </tr>
                    <tr>
                        <td><label for="name">属性名称</label></td>
                        <td><sf:input path="name" id="name" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <sf:hidden path="category.id" value="${category.id}"/>
                            <button type="submit" class="btn btn-success">提 交
                            </button>
                        </td>
                    </tr>
                </table>
            </sf:form>
        </div>
    </div>
</div>


<%@include file="../include/backend/footer.jsp" %>
