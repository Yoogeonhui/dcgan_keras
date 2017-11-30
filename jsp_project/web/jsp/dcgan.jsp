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
<c:set var="page" value="gan"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script src="http://d3js.org/d3.v3.min.js"></script>

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
    <%@ include file="modal.jsp" %>
    <script>upper("DCGAN",null,null);</script>
    <script>

        let z_dis = [];
        function upload_image(img_loc){
            var ajax = new XMLHttpRequest();
            ajax.open("POST","./upload.do",false);
            ajax.setRequestHeader("Content-Type", "application/upload");
            ajax.send(img_loc);
        }
        function upload_image_to_facebook(img_loc){
            var ajax = new XMLHttpRequest();
            ajax.open("POST","./uploadface.do",false);
            ajax.setRequestHeader("Content-Type", "application/upload");
            ajax.send(img_loc);
        }
        let imageIDs = 0;
        const model = new KerasJS.Model({
            filepaths: {
                model: '${contextPath}/model/model.json',
                weights: '${contextPath}/model/model_weights.buf',
                metadata: '${contextPath}/model/model_metadata.json'
            },
            gpu: true
        });
        let model_ready = false;
        $(document).ready(()=>{



            model.ready()
                .then(() => {
                    console.log('ready_done')
                    // input data object keyed by names of the input layers
                    // or `input` for Sequential models
                    // values are the flattened Float32Array data
                    // (input tensor shapes are specified in the model config)

                    model_ready = true;
                    // make predictions
                    //return model.predict(inputData)
                })
                .catch(err => {
                    console.log(err)
                    // handle error
                });

        });
        function get_image(){
            let data=[];

            for(let i=0;i<100;i++){
                data[i] = Math.random()*2-1;
            }
            if(!model_ready){
                load_modal('아직 모델이 로드되지 않았습니다.', '그러합니다');
            }else{
                const inputData = {
                    'input': new Float32Array(data)
                };
                model.predict(inputData).then(outputData=>{

                    if(outputData['output']==null){
                        load_modal('결과가 없습니다. 모델 오류일 가능성이 높습니다.');
                    }
                    else{
                        let result = outputData['output'].map(function(x) {return Math.floor((Math.min(1,Math.max(-1,x))*127.5)+127.5);})
                        /*for(let i=0;i<64*64*3;i++){

                            result[i] = result[i]*128+128;
                            console.log(result[i])
                        }*/
                        //128*128
                        console.log(result);
                        let c = document.getElementById("myCanvas");
                        c.height = 64;
                        c.width = 64;
                        let ctx = c.getContext("2d");
                        let imgData = ctx.createImageData(64,64);
                        let data = imgData.data;
                        let len = data.length;

                        let t=0;
                        for(let i=0;i<len;i+=4){
                            //BGR to RGB
                            data[i] = result[t+2];
                            data[i+1] = result[t+1];
                            data[i+2] = result[t];
                            data[i+3] = 255;
                            t+=3;
                        }
                        ctx.putImageData(imgData,0,0);
                        let imageLoc = c.toDataURL('image/png');
                        $('#images').append(
                            '<div class="card horizontal">' +
                            '   <div class="card-image">\n' +
                            '        <img src="'+imageLoc+'" height="128" width="128">\n' +
                            '      </div>\n' +
                            '      <div class="card-stacked">\n' +
                            '        <div class="card-content">\n' +
                            '          <h5>Z분포</h5><p>후에 그래프를 불러올 방침입니다.</p>\n' +
                            '        </div>\n' +
                            '        <div class="card-action">\n' +
                            '          <a onclick="upload_image(\''+imageLoc+'\')">서버에 올리기.</a>\n' +
                            '        </div>\n' +
                            '      </div>\n' +
                            '    </div>\n' +
                            '  </div>\n');


                    }
                })
                    .catch(err=>{
                        load_modal('에러', err.toString());
                    });
            }

        }
    </script>
    <div class="container">
        <div class="row">
            <div class="col s12 center">
                <canvas id="myCanvas" width="64" height="64"></canvas>
                <a class="waves-effect waves-light btn" onclick="get_image()">이미지 얻기</a>
            </div>
        </div>
        <div class="row" id="images">

        </div>
    </div>
</body>
</html>
