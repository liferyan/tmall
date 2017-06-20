<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Spitter</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/style.css" />">
</head>
<body>
<h1>Register</h1>

<form:form modelAttribute="spitter" method="POST">

    First Name: <form:input path="firstName" type="text" name="firstName"/>
    <form:errors path="firstName" cssStyle="color:red"/><br/>

    Last Name: <form:input path="lastName" type="text" name="lastName"/>
    <form:errors path="lastName" cssStyle="color:red"/><br/>

    Email: <form:input path="email" type="email" name="email"/>
    <form:errors path="email" cssStyle="color:red"/><br/>

    Username: <form:input path="username" type="text" name="username"/>
    <form:errors path="username" cssStyle="color:red"/><br/>

    Password: <form:input path="password" type="password" name="password"/>
    <form:errors path="password" cssStyle="color:red"/><br/>

    <input type="submit" value="Register"/>
</form:form>
</body>
</html>
