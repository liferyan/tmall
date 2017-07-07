<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ include file="../include/backend/header.jsp" %>
<%@ include file="../include/backend/navigator.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<script>
  $(function () {
    //检查产品修改页面的产品名称是否为空
    $("#editForm").submit(function () {
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

<title>编辑产品</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="${ctx}/backend/products/${product.category.id}">所有产品</a></li>
        <li class="active">编辑产品</li>
    </ol>
    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑产品</div>
        <div class="panel-body">
            <sf:form commandName="product" method="post" id="editForm">
                <table class="editTable">
                    <tr>
                        <td colspan="2" align="center"><sf:errors path="*"
                                                                  cssClass="text-danger"/></td>
                    </tr>
                    <tr>
                        <td><label for="name">产品名称</label></td>
                        <td><sf:input path="name" id="name" class="form-control"
                                      value="${name}"/></td>
                    </tr>
                    <tr>
                        <td><label for="subtitle">产品小标题</label></td>
                        <td><sf:input path="subTitle" id="subtitle" class="form-control"
                                      value="${subTitle}"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="original_price">原价格</label></td>
                        <td><sf:input path="originalPrice" id="original_price"
                                      class="form-control" value="${originalPrice}"/></td>
                    </tr>
                    <tr>
                        <td><label for="promote_price">优惠价格</label></td>
                        <td><sf:input path="promotePrice" id="promote_price" class="form-control"
                                      value="${promotePrice}"/></td>
                    </tr>
                    <tr>
                        <td><label for="stock">库存</label></td>
                        <td><sf:input path="stock" id="stock" class="form-control"
                                      value="${stock}"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                                <%--隐藏域--%>
                            <sf:hidden path="id" value="${id}"/>
                            <sf:hidden path="category.id" value="${category.id}"/>
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </sf:form>
        </div>
    </div>
</div>

<%@include file="../include/backend/footer.jsp" %>