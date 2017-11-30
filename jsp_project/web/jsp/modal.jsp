<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2017-11-28
  Time: 오후 5:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<!-- Modal Structure -->
<div id="modal1" class="modal">
    <div class="modal-content">
        <h4 id="modal-title">Modal Header</h4>
        <p id="modal-text">A bunch of text</p>
    </div>
    <div class="modal-footer">
        <a class="modal-action modal-close waves-effect waves-green btn-flat">확인</a>
    </div>
</div>
<script>
    $(document).ready(function(){
        // the "href" attribute of the modal trigger must specify the modal ID that wants to be triggered
        $('.modal').modal();
    });
    function load_modal(title,text){
        $('#modal-title').text(title);
        $('#modal-text').text(text);
        $('#modal1').modal('open');
    }

</script>
</html>
