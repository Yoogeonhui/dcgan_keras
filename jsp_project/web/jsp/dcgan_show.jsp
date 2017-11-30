<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2017-09-28
  Time: 오후 2:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding = "utf-8" language="java" %>

<!DOCTYPE html>
<html>
<!-- include 된 곳에서 Active되는 메뉴를 처리하기 위한 변수 -->
<c:set var="page" value="gan_show"/>
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
<!-- navbar include하기 -->
<%@ include file="navbar.jsp" %>
<%@ include file="modal.jsp" %>
<script>upper("DCGAN으로 만들어진 이미지",'놀랍군요!',null);
</script>
<br>
<div class="container">
    <div class="row">
        <div class="col s12">
            <%
                List<File> res = (List<File>)request.getAttribute("result");
                if(res==null){
                    %>
                <script>load_modal('에리','올라와있는 이미지가 없습니다.')</script>
            <%
                }else{
                    for(File temp : res){%>
                        <div class="card horizontal">
                            <div class="card-image">
                                <img src="${contextPath}/ganout/<%=temp.getName()%>" height="128" width="128">
                            </div>
                            <div class="card-stacked">
                                <div class="card-content">
                                        <h5>Z분포</h5><p>후에 그래프를 불러올 방침입니다.</p>
                                    </div>
                                <div class="card-action">
                                    <a href="uploadface.do?filename=<%=temp.getName()%>">페이스북 공유</a>
                                </div>
                            </div>
                        </div>
            <%
                    }
                }
            %>
        </div>
    </div>
    <br>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
