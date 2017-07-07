<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<script>
  $(function () {
    <c:if test="${!empty errorMsg}">
    $("span.errorMessage").html("${errorMsg}");
    $("div.registerErrorMessageDiv").css("visibility", "visible");
    </c:if>

    $(".registerForm").submit(function () {
      if (0 == $("#name").val().length) {
        $("span.errorMessage").html("请输入用户名");
        $("div.registerErrorMessageDiv").css("visibility", "visible");
        return false;
      }
      if (0 == $("#password").val().length) {
        $("span.errorMessage").html("请输入密码");
        $("div.registerErrorMessageDiv").css("visibility", "visible");
        return false;
      }
      if (0 == $("#repeatpassword").val().length) {
        $("span.errorMessage").html("请输入重复密码");
        $("div.registerErrorMessageDiv").css("visibility", "visible");
        return false;
      }
      if ($("#password").val() != $("#repeatpassword").val()) {
        $("span.errorMessage").html("重复密码不一致");
        $("div.registerErrorMessageDiv").css("visibility", "visible");
        return false;
      }
      return true;
    });
  })
</script>

<sf:form commandName="user" method="post" class="registerForm">
    <div class="registerDiv">
        <div class="registerErrorMessageDiv">
            <div class="alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="alert"
                        aria-label="Close"></button>
                <span class="errorMessage"></span>
            </div>
        </div>
        <table class="registerTable" align="center">
            <tr>
                <td colspan="2" align="center"><sf:errors path="*"
                                                          cssClass="text-danger"/></td>
            </tr>
            <tr>
                <td class="registerTip registerTableLeftTD">设置会员名</td>
                <td></td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">登陆名</td>
                <td class="registerTableRightTD"><sf:input path="name" id="name"
                                                           placeholder="会员名一旦设置成功，无法修改"/></td>
            </tr>
            <tr>
                <td class="registerTip registerTableLeftTD">设置登陆密码</td>
                <td class="registerTableRightTD">登陆时验证，保护账号信息</td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">登陆密码</td>
                <td class="registerTableRightTD"><sf:input path="password" id="password"
                                                           type="password"
                                                           placeholder="设置你的登陆密码"/></td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">密码确认</td>
                <td class="registerTableRightTD"><input id="repeatpassword" name="repeatPassword"
                                                        type="password" placeholder="请再次输入你的密码"/>
                </td>
            </tr>

            <tr>
                <td colspan="2" class="registerButtonTD">
                    <button>提 交</button>
                </td>
            </tr>
        </table>
    </div>
</sf:form>

