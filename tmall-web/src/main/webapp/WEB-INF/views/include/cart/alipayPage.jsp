<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>

<script>
  $(function () {
    //检查分类修改页面的分类名称是否为空
    $("#button").click(function () {
      $("#form").submit();
    });
  });
</script>

<div class="aliPayPageDiv">
    <div class="aliPayPageLogo">
        <img class="pull-left" src="${ctx}/img/site/simpleLogo.png">
        <div style="clear:both"></div>
    </div>

    <div>
        <span class="confirmMoneyText">扫一扫付款（元）</span>
        <span class="confirmMoney">
		￥<fmt:formatNumber type="number" value="${totalMoneyInOrder}" minFractionDigits="2"/></span>

    </div>
    <div>
        <img class="aliPayImg" src="${ctx}/img/site/alipay2wei.png">
    </div>


    <div>
        <form id="form" action="${ctx}/order/pay/${order.id}" method="post">
            <input type="hidden" name="totalMoneyInOrder" value="${totalMoneyInOrder}"/>
            <button id="button" class="confirmPay">确认支付</button>
        </form>
    </div>

</div>
