<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2017-11-08
  Time: 오후 3:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<div class="navbar-fixed">
    <nav>
        <div class="nav-wrapper teal lighten-1">
            <a href="#" class="brand-logo"><img src="${contextPath}/image/logo.png" width="50" style="margin-top: 3px"> </a>
            <a href="#" data-activates="side_bar" class="button-collapse"><i class="material-icons">menu</i></a>
            <ul class="right hide-on-med-and-down">
                <li class="main_page waves-effect"><a href="#">Home</a></li>
                <li class="waves-effect" ><a href="#">Home</a></li>
                <li class="waves-effect"><a href="#">Home</a></li>
                <li class="waves-effect"><a href="#">Home</a></li>
            </ul>
            <ul class="side-nav teal" id="side_bar">
                <li class="main_page"><a href="#">Home</a></li>
                <li><a href="index.jsp">Home</a></li>
                <li><a href="#">Home</a></li>
                <li><a href="#">Home</a></li>
            </ul>
        </div>
    </nav>
</div>
<div class="parallax-container valign-wrapper center">
    <div class="container">
        <div class="row center">
            <h2 class="header white-text" id="parallax_title"></h2>
            <a class="waves-effect waves-light btn-large" id="parallax_button"></a>
            <p class="white-text light" id="parallax_desc"></p>
        </div>
    </div>
    <div class="parallax">
        <img src="${contextPath}/image/cute_back.jpg">
    </div>
</div>
<script>
    $(document).ready(
        function(){
            $(".button-collapse").sideNav();
        }
    );
    $(".${page}").each(
        function(){
            $(this).addClass("active").removeClass("${page}");
        }
    );
    $('.parallax').parallax();
</script>