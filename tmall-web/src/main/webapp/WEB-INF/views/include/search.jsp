<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<a href="${ctx}">
    <img id="logo" src="${ctx}/img/site/logo.gif" class="logo">
</a>

<form action="foresearch" method="post">
    <div class="searchDiv">
        <input name="keyword" type="text" placeholder="时尚男鞋  太阳镜 ">
        <button type="submit" class="searchButton">搜索</button>
        <div class="searchBelow">
            <c:forEach items="${categoryList}" var="category" varStatus="st">
                <c:if test="${st.count>=5 and st.count<=8}">
						<span>
							<a href="category/${category.id}">
                                    ${category.name}
                            </a>
							<c:if test="${st.count!=8}">
                                <span>|</span>
                            </c:if>
						</span>
                </c:if>
            </c:forEach>
        </div>
    </div>
</form>

