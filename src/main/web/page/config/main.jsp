<%--
  Created by IntelliJ IDEA.
  User: liang_zhang
  Date: 2017/10/16
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<%@ include file="../include/resource.jsp" %>
<%@ include file="../include/skin_default.jsp" %>
<html>
<head>
    <title>页面配置</title>
    <style type="text/css">
        body{
            margin: 0px;
            padding: 0px;
            width: 100%;
            height: 100%;
            min-width: 1120px;
        }
        div{
            margin: 0px;padding: 0px;
        }
        div.top{
            width: 100%;height: 20%; background: darkgray;
        }
        div.left{
            width: 22%; height: 80%;background: lightgray;float: left;
        }
        div.content{
            width: 78%; height: 80%;background: lightslategray;
            float: left;
        }
    </style>
</head>
<body>

    <div class="top">
    </div>
    <div class="left">
    </div>
    <div class="content">
        <div class="an">
            <c:import url="help/moduleSel.jsp"></c:import>
        </div>


        <c:import url="help/slide.jsp"></c:import>

    </div>


    </div>


</body>
<script>

    $(function () {

        //初始化页面
        init_page();

    });

    function init_page() {
        //设置页面高度
        var height=$(window).height();
        var width=$(window).width();
        $("div.top").css("height",height*0.2);
        $("div.left").css("height",height*0.8);
        $("div.content").css("height",height*0.8);
    }





    function addMdl(item) {
        if($(item).closest("div").hasClass("SlideShowMdl")){
            showSlideMdlWin();
        }
        else if($(item).closest("div").hasClass("GroupMdl")){

        }
        else{

        }
    }
</script>

</html>
