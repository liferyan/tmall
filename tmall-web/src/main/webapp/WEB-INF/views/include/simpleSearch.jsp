<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<div>
    <a href="${ctx}">
        <img id="simpleLogo" class="simpleLogo" src="${ctx}/img/site/simpleLogo.png">
    </a>

    <form action="foresearch" method="post">
        <div class="simpleSearchDiv pull-right">
            <input type="text" placeholder="平衡车 原汁机" name="keyword">
            <button class="searchButton" type="submit">搜天猫</button>
            <div class="searchBelow">
                <c:forEach items="${categoryList}" var="category" varStatus="st">
                    <c:if test="${st.count>=8 and st.count<=11}">
					<span>
						<a href="category/${category.id}">
                                ${category.name}
                        </a>
						<c:if test="${st.count!=11}">
                            <span>|</span>
                        </c:if>
					</span>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </form>
    <div style="clear:both"></div>
</div>

