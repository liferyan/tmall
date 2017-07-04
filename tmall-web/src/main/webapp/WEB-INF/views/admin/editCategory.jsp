<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<script>
  $(function () {
    //检查分类修改页面的分类名称是否为空
    $("#editForm").submit(function () {
      return checkEmpty("name", "分类名称");
    });
  });
</script>

<title>编辑分类</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="${ctx}/admin/categories">所有分类</a></li>
        <li class="active">编辑分类</li>
    </ol>
    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑分类</div>
        <div class="panel-body">
            <sf:form commandName="category" method="post" id="editForm"
                     enctype="multipart/form-data">
                <table class="editTable">
                    <tr>
                        <td colspan="2" align="center"><sf:errors path="*"
                                                                  cssClass="text-danger"/></td>
                    </tr>
                    <tr>
                        <td><label for="name">分类名称</label></td>
                        <td><sf:input path="name" value="${name}"
                                      class="form-control"/></td>
                    </tr>
                    <tr>
                        <td><label for="category_img">分类图片</label></td>
                        <td>
                            <input id="category_img" accept="image/*" type="file"
                                   name="category_image"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                                <%--隐藏域--%>
                            <sf:hidden path="id" value="${id}"/>
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </sf:form>
        </div>
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>