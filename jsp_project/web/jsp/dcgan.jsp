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
<c:set var="page" value="dcgan_page"/>
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
    <script src="${contextPath}/js/keras.js"></script>
</head>

<body>
    <%@ include file="navbar.jsp" %>
    <script>upper("DCGAN",null,null,null);</script>
    <script>

        const model = new KerasJS.Model({
            filepaths: {
                model: '${contextPath}/model/model.json',
                weights: '${contextPath}/model/model_weights.buf',
                metadata: '${contextPath}/model/model_metadata.json'
            },
            gpu: true
        });

        model.ready()
            .then(() => {
            // input data object keyed by names of the input layers
            // or `input` for Sequential models
            // values are the flattened Float32Array data
            // (input tensor shapes are specified in the model config)
            const inputData = {
                'input_1': new Float32Array(data)
            }

            // make predictions
            return model.predict(inputData)
        })
        .then(outputData => {
            // outputData is an object keyed by names of the output layers
            // or `output` for Sequential models
            // e.g.,
            // outputData['fc1000']
        })
        .catch(err => {
            // handle error
        });
    </script>
    <div class="container">
        <div class="row">
            <div class="col s4">


            </div>
            <div class="col s8 center">
                
            </div>
        </div>
    </div>
</body>
</html>
