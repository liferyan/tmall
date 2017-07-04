<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<script>
  $(function () {
    <c:if test="${!empty msg}">
    $("span.errorMessage").html("${msg}");
    $("div.loginErrorMessageDiv").show();
    </c:if>
    $("form.loginForm").submit(function () {
      if (0 == $("#name").val().length || 0 == $("#password").val().length) {
        $("span.errorMessage").html("请输入账号密码");
        $("div.loginErrorMessageDiv").show();
        return false;
      }
      return true;
    });
    $("form.loginForm input").keyup(function () {
      $("div.loginErrorMessageDiv").hide();
    });
    var left = window.innerWidth / 2 + 162;
    $("div.loginSmallDiv").css("left", left);
  })
</script>

<div id="loginDiv" style="position: relative">

    <div class="simpleLogo">
        <a href="${ctx}"><img src="${ctx}/img/site/simpleLogo.png"></a>
    </div>

    <img id="loginBackgroundImg" class="loginBackgroundImg"
         src="${ctx}/img/site/loginBackground.png">

    <sf:form commandName="user" class="loginForm" method="post">
        <div id="loginSmallDiv" class="loginSmallDiv">
            <div class="loginErrorMessageDiv">
                <div class="alert alert-danger">
                    <button type="button" class="close" data-dismiss="alert"
                            aria-label="Close"></button>
                    <span class="errorMessage"></span>
                </div>
            </div>

            <sf:errors path="*" cssClass="text-danger"/>

            <div class="login_acount_text">账户登录</div>
            <div class="loginInput ">
				<span class="loginInputIcon ">
					<span class=" glyphicon glyphicon-user"></span>
				</span>
                <sf:input path="name" id="name" placeholder="手机/会员名/邮箱"/>
            </div>

            <div class="loginInput ">
				<span class="loginInputIcon ">
					<span class=" glyphicon glyphicon-lock"></span>
				</span>
                <sf:input path="password" id="password" type="password" placeholder="密码"/>
            </div>
            <span class="text-danger">不要输入真实的天猫账号密码</span><br><br>

            <div>
                <a class="notImplementLink" href="#nowhere">忘记登录密码</a>
                <a href="${ctx}/user/register" class="pull-right">免费注册</a>
            </div>
            <div style="margin-top:20px">
                <button class="btn btn-block redButton" type="submit">登录</button>
            </div>
        </div>
    </sf:form>
</div>

