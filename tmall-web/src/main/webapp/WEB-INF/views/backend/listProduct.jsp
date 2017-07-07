<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ include file="../include/backend/backendHeader.jsp" %>
<%@ include file="../include/backend/backendNavigator.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<script>
  $(function () {
    $("#addForm").submit(function () {
      if (!checkEmpty("name", "产品名称")) {
        return false;
      }
      if (!checkEmpty("subtitle", "产品小标题")) {
        return false;
      }
      if (!checkEmpty("original_price", "原价格")) {
        return false;
      }
      if (!checkEmpty("promote_price", "优惠价格")) {
        return false;
      }
      if (!checkEmpty("stock", "库存")) {
        return false;
      }
      return true;
    });
  });
</script>

<title>产品管理</title>

<div class="workingArea">
    <p class="bg-success success">操作成功！</p>
    <ol class="breadcrumb">
        <li><a href="${ctx}/backend/categories">所有分类</a></li>
        <li><a href="${category.id}">${category.name}</a></li>
        <li>产品管理</li>
    </ol>
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
            <tr class="bg-success">
                <th>ID</th>
                <th>图片</th>
                <th>产品名称</th>
                <th>产品小标题</th>
                <th width="53px">原价格</th>
                <th width="80px">优惠价格</th>
                <th width="80px">库存数量</th>
                <th width="80px">图片管理</th>
                <th width="80px">设置属性</th>
                <th width="42px">编辑</th>
                <th width="42px">删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${productList}" var="product">
                <tr>
                    <td>${product.id}</td>
                    <td><img src="${ctx}/img/productSingle/${product.firstProductImage.id}.jpg"
                             width="40px">
                    </td>
                    <td>${product.name}</td>
                    <td>${product.subTitle}</td>
                    <td>${product.originalPrice}</td>
                    <td>${product.promotePrice}</td>
                    <td>${product.stock}</td>
                    <td><a href="${ctx}/backend/productImages/${product.id}"><span
                            class="glyphicon glyphicon-picture"></span></a></td>
                    <td><a href="${ctx}/backend/propertyValues/${product.id}"><span
                            class="glyphicon glyphicon-th-list"></span></a></td>
                    <td><a href="${ctx}/backend/product/${product.id}"><span
                            class="glyphicon glyphicon-edit"></span></a></td>
                    <td><a deleteLink="true" href="${ctx}/backend/product/${product.id}/delete"><span
                            class="glyphicon glyphicon-trash"></span></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<div class="pageDiv">
    <%@include file="../include/backend/backendPage.jsp" %>
</div>

<div class="addDiv">
    <div class="panel panel-warning">
        <div class="panel-heading">新增产品</div>
        <div class="panel-body">
            <sf:form commandName="product" method="post" id="addForm">
                <table class="addTable">
                    <tr>
                        <td colspan="2" align="center"><sf:errors path="*"
                                                                  cssClass="text-danger"/></td>
                    </tr>
                    <tr>
                        <td><label for="name">产品名称</label></td>
                        <td><sf:input path="name" id="name" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td><label for="subtitle">产品小标题</label></td>
                        <td><sf:input path="subTitle" id="subtitle" class="form-control"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="original_price">原价格</label></td>
                        <td><sf:input path="originalPrice" id="original_price"
                                      class="form-control"/></td>
                    </tr>
                    <tr>
                        <td><label for="promote_price">优惠价格</label></td>
                        <td><sf:input path="promotePrice" id="promote_price"
                                      class="form-control"/></td>
                    </tr>
                    <tr>
                        <td><label for="stock">库存</label></td>
                        <td><sf:input path="stock" id="stock" class="form-control"/></td>
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


<%@include file="../include/backend/backendFooter.jsp" %>
