<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2017-11-14
  Time: 오후 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding = "utf-8" language="java" %>

<!DOCTYPE html>
<html>
<!-- include 된 곳에서 Active되는 메뉴를 처리하기 위한 변수 -->
<c:set var="page" value="main_page"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<head>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
    <link rel="stylesheet" href="${contextPath}/css/webfont.css">
    <link rel="stylesheet" href="${contextPath}/css/mybackgrounds.css">

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
    <script src="${contextPath}/js/upper_page.js"></script>
</head>

<body>

</body>
</html>
