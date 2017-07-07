<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false" %>

<script>
  $(function () {
    //JQuery 当li是disabled状态时，不允许点击链接跳转
    //$("selector1 selector2") 选择 selector1下的selector2元素
    $("ul.pagination li.disabled a").click(function () {
      return false;
    })
  });
</script>

<%--BootStrap分页--%>
<nav aria-label="Page navigation">
    <ul class="pagination">
        <li <c:if test="${!page.hasPrevious}">class="disabled"</c:if>>
            <a href="?page.start=0${page.param}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <li <c:if test="${!page.hasPrevious}">class="disabled"</c:if>>
            <a href="?page.start=${page.start - page.count}${page.param}"
               aria-label="Previous">
                <span aria-hidden="true">&lsaquo;</span>
            </a>
        </li>

        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
            <%--forEach varStatus(varStatus.index从0开始的迭代索引;varStatus.count从1开始的迭代索引)--%>
            <%--当前页前后各显示3页--%>
            <c:if test="${(page.start - status.index*page.count < 3*page.count) &&
             (status.index*page.count - page.start < 3*page.count)}">
                <li>
                    <a href="?page.start=${status.index*page.count}${page.param}">${status.count}</a>
                </li>
            </c:if>
        </c:forEach>

        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
            <a href="?page.start=${page.start + page.count}${page.param}"
               aria-label="Next">
                <span aria-hidden="true">&rsaquo;</span>
            </a>
        </li>
        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
            <a href="?page.start=${page.lastPageStart}${page.param}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>
