<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false"
         pageEncoding="utf-8" isErrorPage="true" %>
<%@ include file="include/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
<h1 style="text-align: center;" class="bg-danger">Server Error</h1>
<div style="margin: 10px 30px">
    <div class="panel panel-danger">
        <div class="panel-heading">uri: ${uri}</div>
        <div class="panel-body">
            ${msg}
        </div>
    </div>
</div>
</body>
</html>
