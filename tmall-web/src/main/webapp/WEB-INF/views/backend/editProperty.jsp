<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ include file="../include/backend/header.jsp" %>
<%@ include file="../include/backend/navigator.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<script>
  $(function () {
    //检查属性修改页面的属性名称是否为空
    $("#editForm").submit(function () {
      return checkEmpty("name", "属性名称");
    });
  });
</script>

<title>编辑属性</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="${ctx}/backend/properties/${property.category.id}">所有属性</a></li>
        <li class="active">编辑属性</li>
    </ol>
    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑属性</div>
        <div class="panel-body">
            <sf:form commandName="property" method="post" id="editForm">
                <table class="editTable">
                    <tr>
                        <td colspan="2" align="center"><sf:errors path="*"
                                                                  cssClass="text-danger"/></td>
                    </tr>
                    <tr>
                        <td><label for="name">属性名称</label></td>
                        <td><sf:input path="name" id="name" value="${property.name}"
                                      class="form-control"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                                <%--隐藏域--%>
                            <sf:hidden path="id" value="${property.id}"/>
                            <sf:hidden path="category.id" value="${property.category.id}"/>
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </sf:form>
        </div>
    </div>
</div>

<%@include file="../include/backend/footer.jsp" %>