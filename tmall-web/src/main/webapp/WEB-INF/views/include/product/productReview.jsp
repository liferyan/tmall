<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>

<div class="productReviewDiv">
    <div class="productReviewTopPart">
        <a href="#nowhere" class="productReviewTopPartSelectedLink">商品详情</a>
        <a href="#nowhere" class="selected">累计评价 <span
                class="productReviewTopReviewLinkNumber">${product.reviewCount}</span> </a>
    </div>

    <div class="productReviewContentPart">
        <c:forEach items="${review_list}" var="review">
            <div class="productReviewItem">

                <div class="productReviewItemDesc">
                    <div class="productReviewItemContent">
                            ${review.content }
                    </div>
                    <div class="productReviewItemDate"><fmt:formatDate value="${review.createDate}"
                                                                       pattern="yyyy-MM-dd"/></div>
                </div>
                <div class="productReviewItemUserInfo">

                        ${review.user.anonymousName}<span class="userInfoGrayPart">（匿名）</span>
                </div>

                <div style="clear:both"></div>

            </div>
        </c:forEach>
    </div>

</div>