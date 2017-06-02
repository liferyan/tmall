<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8"
         isELIgnored="false" %>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>
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
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?cid=${category.id}">${category.name}</a></li>
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
                        <form method="post" id="addFormSingle" action="admin_productImage_add"
                              enctype="multipart/form-data">
                            <table class="addTable">
                                <tr>
                                    <td>请选择本地图片 尺寸400X400 为佳</td>
                                </tr>
                                <tr>
                                    <td>
                                        <input id="single_img_file" type="file"
                                               name="single_img_file"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <input type="hidden" name="type" value="TYPE_SINGLE"/>
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
                    <tr class="success">
                        <th>ID</th>
                        <th>产品单个图片缩略图</th>
                        <th>删除</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${single_img_list}" var="single_img">
                        <tr>
                            <td>${single_img.id}</td>
                            <td><a title="点击查看图片" href="img/productSingle/${single_img.id}.jpg"><img
                                    height="50px"
                                    src="img/productSingle/${single_img.id}.jpg"></a>
                            </td>
                            <td>
                                <a href="admin_productImage_delete?id=${single_img.id}"><span
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
                        <form method="post" id="addFormDetail" action="admin_productImage_add"
                              enctype="multipart/form-data">
                            <table class="addTable">
                                <tr>
                                    <td>请选择本地图片 宽度790 为佳</td>
                                </tr>
                                <tr>
                                    <td>
                                        <input id="detail_img_file" type="file"
                                               name="detail_img_file"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <input type="hidden" name="type" value="TYPE_DETAIL"/>
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
                    <tr class="success">
                        <th>ID</th>
                        <th>产品详情图片缩略图</th>
                        <th>删除</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${detail_img_list}" var="detail_img">
                        <tr>
                            <td>${detail_img.id}</td>
                            <td><a title="点击查看图片" href="img/productDetail/${detail_img.id}.jpg"><img
                                    height="50px" src="img/productDetail/${detail_img.id}.jpg"></a>
                            </td>
                            <td>
                                <a href="admin_productImage_delete?id=${detail_img.id}"><span
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

<%@ include file="../include/admin/adminFooter.jsp" %>
