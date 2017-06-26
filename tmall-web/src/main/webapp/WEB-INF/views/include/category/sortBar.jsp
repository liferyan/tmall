<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>

<script>
  $(function () {
    $("input.sortBarPrice").keyup(function () {
      var num = $(this).val();
      if (num.length == 0) {
        $("div.productUnit").show();
        return;
      }

      num = parseInt(num);
      if (isNaN(num))
        num = 1;
      if (num <= 0)
        num = 1;
      $(this).val(num);

      var begin = $("input.beginPrice").val();
      var end = $("input.endPrice").val();
      if (!isNaN(begin) && !isNaN(end)) {
        console.log(begin);
        console.log(end);
        $("div.productUnit").hide();
        $("div.productUnit").each(function () {
          var price = $(this).attr("price");
          price = new Number(price);

          if (price <= end && price >= begin)
            $(this).show();
        });
      }

    });
  });
</script>
<div class="categorySortBar">
    <table class="categorySortBarTable categorySortTable">
        <tr>
            <td <c:if test="${empty param.sort or param.sort eq 'all'}">class="grayColumn"</c:if>><a
                    href="?cid=${category.id}&sort=all">综合<span
                    class="glyphicon glyphicon-arrow-down"></span></a></td>
            <td <c:if test="${param.sort eq 'review'}">class="grayColumn"</c:if>><a
                    href="?cid=${category.id}&sort=review">人气<span
                    class="glyphicon glyphicon-arrow-down"></span></a></td>
            <td <c:if test="${param.sort eq 'date'}">class="grayColumn"</c:if>><a
                    href="?cid=${category.id}&sort=date">新品<span
                    class="glyphicon glyphicon-arrow-down"></span></a></td>
            <td <c:if test="${param.sort eq 'saleCount'}">class="grayColumn"</c:if>><a
                    href="?cid=${category.id}&sort=saleCount">销量<span
                    class="glyphicon glyphicon-arrow-down"></span></a></td>
            <td <c:if test="${param.sort eq 'price'}">class="grayColumn"</c:if>><a
                    href="?cid=${category.id}&sort=price">价格<span
                    class="glyphicon glyphicon-resize-vertical"></span></a></td>
        </tr>
    </table>
    <table class="categorySortBarTable">
        <tr>
            <td><input class="sortBarPrice beginPrice" type="text" placeholder="请输入"></td>
            <td class="grayColumn priceMiddleColumn">-</td>
            <td><input class="sortBarPrice endPrice" type="text" placeholder="请输入"></td>
        </tr>
    </table>
</div>

