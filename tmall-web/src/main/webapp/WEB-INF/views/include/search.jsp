<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<a href="${contextPath}">
    <img id="logo" src="img/site/logo.gif" class="logo">
</a>

<form action="foresearch" method="post">
    <div class="searchDiv">
        <input name="keyword" type="text" placeholder="时尚男鞋  太阳镜 ">
        <button type="submit" class="searchButton">搜索</button>
        <div class="searchBelow">
            <c:forEach items="${category_list}" var="category" varStatus="st">
                <c:if test="${st.count>=5 and st.count<=8}">
						<span>
							<a href="forecategory?cid=${category.id}">
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

