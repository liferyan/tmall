<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8"
         isELIgnored="false" %>
<%@ include file="../include/backend/backendHeader.jsp" %>
<%@ include file="../include/backend/backendNavigator.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
  $(function () {
    $("#addFormSingle").submit(function () {
      return checkEmpty("single_img_file", "单个图片");
    });
  });
  $(function () {
    $("#addFormDetail").submit(function () {
      return checkEmpty("detail_img_file", "详情图片");
    });
  });
</script>

<title>产品图片管理</title>

<div class="workingArea">
    <p class="bg-success success">操作成功！</p>
    <ol class="breadcrumb">
        <li><a href="${ctx}/backend/categories">所有分类</a></li>
        <li><a href="${ctx}/backend/products/${category.id}">${category.name}</a></li>
        <li>${product.name}</li>
        <li>产品图片管理</li>
    </ol>
    <table class="addPictureTable" align="center">
        <tr>
            <%--单个图片--%>
            <td class="addPictureTableTD">
                <div class="panel panel-warning addPictureDiv">
                    <div class="panel-heading">新增产品<b class="text-primary"> 单个 </b>图片</div>
                    <div class="panel-body">
                        <form method="post" id="addFormSingle" enctype="multipart/form-data">
                            <table class="addTable">
                                <tr>
                                    <td align="center"><span
                                            class="text-danger">${singleImageError}</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>请选择本地图片 尺寸400X400 为佳</td>
                                </tr>
                                <tr>
                                    <td>
                                        <input name="imageFile" id="single_img_file"
                                               type="file"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <input type="hidden" name="type" value="type_single"/>
                                        <input type="hidden" name="productId"
                                               value="${product.id}"/>
                                        <button type="submit" class="btn btn-success">提 交</button>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead style="text-align: center">
                    <tr class="bg-success">
                        <th>ID</th>
                        <th>产品单个图片缩略图</th>
                        <th>删除</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${singleImageList}" var="single_img">
                        <tr>
                            <td>${single_img.id}</td>
                            <td><a title="点击查看图片"
                                   href="${ctx}/img/productSingle/${single_img.id}.jpg"><img
                                    height="50px"
                                    src="${ctx}/img/productSingle/${single_img.id}.jpg"></a>
                            </td>
                            <td>
                                <a href="${ctx}/backend/productImage/${single_img.id}/delete?productId=${product.id}"><span
                                        class="glyphicon glyphicon-trash"></span></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </td>
            <%--详情图片--%>
            <td class="addPictureTableTD">
                <div class="panel panel-warning addPictureDiv">
                    <div class="panel-heading">新增产品<b class="text-primary"> 详情 </b>图片</div>
                    <div class="panel-body">
                        <form method="post" id="addFormDetail" enctype="multipart/form-data">
                            <table class="addTable">
                                <tr>
                                    <td align="center"><span
                                            class="text-danger">${detailImageError}</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>请选择本地图片 宽度790 为佳</td>
                                </tr>
                                <tr>
                                    <td>
                                        <input id="detail_img_file" type="file"
                                               name="imageFile"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <input type="hidden" name="type" value="type_detail"/>
                                        <input type="hidden" name="pid" value="${product.id}"/>
                                        <button type="submit" class="btn btn-success">提 交</button>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr class="bg-success">
                        <th>ID</th>
                        <th>产品详情图片缩略图</th>
                        <th>删除</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${detailImageList}" var="detail_img">
                        <tr>
                            <td>${detail_img.id}</td>
                            <td><a title="点击查看图片"
                                   href="${ctx}/img/productDetail/${detail_img.id}.jpg"><img
                                    height="50px"
                                    src="${ctx}/img/productDetail/${detail_img.id}.jpg"></a>
                            </td>
                            <td>
                                <a href="${ctx}/backend/productImage/${detail_img.id}/delete?productId=${product.id}"><span
                                        class="glyphicon glyphicon-trash"></span></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </td>
        </tr>
    </table>
</div>

<%@ include file="../include/backend/backendFooter.jsp" %>
