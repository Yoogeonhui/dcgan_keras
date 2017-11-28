
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
    <!-- navbar include하기 -->
    <%@ include file="navbar.jsp" %>
    <script>upper("DCGAN을 활용한 사이트",null,'시작하기');
    $('#parallax_button').attr('href', '${contextPath}/jsp/dcgan.jsp');
    </script>
    <br>
    <div class="container">
        <div class="row">
            <div class="col s6 center">
                <i class="large material-icons teal-text darken-4">group</i>
                <h5>누군가 정해야 한다</h5>
                <p class="light">얼굴을 사진에 실어서 올리기는 그렇고 그렇다고 아예 안올리자니 찜찜하고, 뭔가
                    올려야겠는데 다른사람 얼굴 올리기도 난감한 당신.</p>
            </div>
            <div class="col s6 center">
                <i class="large material-icons teal-text darken-4">sentiment_very_satisfied</i>
                <h5>그런 당신을 위해 준비했습니다!</h5>
                <p class="light">얼굴 사진만을 필요로 한다면 테스트로 여기서 제작한 사진을 올려보는 건 어떨까요?
                </p>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col s12 center">
                <h2>뭘 썼나요?</h2>
                <p class="light">Keras를 활용하여 DCGAN을 제작하고 여기서 나온 모델을 저장하여 Keras-JS를 돌려서
                    웹상에서 돌아가게 해놨습니다.</p>
            </div>
        </div>
        <div class="row">
            <div class="col s4 center">
                <p class="light">근데 그거 아나요?</p>
                <h1>이거 망했어요!!</h1>
                <h7>결과가 진짜 볼품없어서..ㅠㅠ 시간을 더 준다면야 뭔갈 할텐데 결과가 정말 X를 눌러 Joy를 표하고 싶네요..</h7>

            </div>
            <div class="col s8 center">
                <p class="light">괴생명체 한마리가 당신을 반기고 있습니다.</p>
                <img src="${contextPath}/image/mangham.png"/>
                <p class="light"><s>거울을 보는 것 같은 놀라운 체험 어차피 도찐개찐</s></p>
                <p>아몰랑 시간을 더 주세요</p>
            </div>
        </div>
        <br>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>
