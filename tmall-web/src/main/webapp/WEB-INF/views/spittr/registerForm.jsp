<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Spitter</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/style.css" />">
</head>
<body>
<h1>Register</h1>

<sf:form commandName="spitter" method="POST">
    <sf:errors path="*" element="div" cssClass="errors"/>

    <sf:label path="firstName" cssErrorClass="error">First Name:</sf:label>
    <sf:input path="firstName" cssErrorClass="error"/><br/>

    <sf:label path="lastName" cssErrorClass="error">Last Name:</sf:label>
    <sf:input path="lastName" cssErrorClass="error"/><br/>

    <sf:label path="email" cssErrorClass="error">Email:</sf:label>
    <sf:input path="email" cssErrorClass="error" type="email"/><br/>

    <sf:label path="username" cssErrorClass="error">Username:</sf:label>
    <sf:input path="username" cssErrorClass="error"/><br/>

    <sf:label path="password" cssErrorClass="error">Password:</sf:label>
    <sf:input path="password" cssErrorClass="error"/><br/>

    <input type="submit" value="Register"/>
</sf:form>
</body>
</html>
