<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>

<div class="payedDiv">
    <div class="payedTextDiv">
        <img src="${ctx}/img/site/paySuccess.png">
        <span>您已成功付款</span>
    </div>
    <div class="payedAddressInfo">
        <ul>
            <li>收货地址：${order.address} ${order.receiver} ${order.mobile}</li>
            <li>实付款：<span class="payedInfoPrice">
			￥<fmt:formatNumber type="number" value="${totalMoneyInOrder}" minFractionDigits="2"/>
            </li>
            <li>预计08月08日送达</li>
        </ul>
        <div class="paedCheckLinkDiv">
            您可以
            <a class="payedCheckLink" href="${ctx}/order">查看已买到的宝贝</a>
            <a class="payedCheckLink" href="${ctx}/order">查看交易详情 </a>
        </div>
    </div>
    <div class="payedSeperateLine"></div>
    <div class="warningDiv">
        <img src="${ctx}/img/site/warning.png">
        <b>安全提醒：</b>下单后，<span class="redColor boldWord">用QQ给您发送链接办理退款的都是骗子！</span>天猫不存在系统升级，订单异常等问题，谨防假冒客服电话诈骗！
    </div>
</div>
