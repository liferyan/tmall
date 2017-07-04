<%@ page language="java" contentType="text/html;charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false" %>

<div class="categoryMenu">
    <c:forEach items="${categoryList}" var="category">
        <div cid="${category.id}" class="eachCategory">
            <span class="glyphicon glyphicon-link"></span>
            <a href="category/${category.id}">
                    ${category.name}
            </a>
        </div>
    </c:forEach>
</div>