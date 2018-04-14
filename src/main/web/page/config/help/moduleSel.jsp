<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    div.moduleList{
        width: 95%;
        height: 30px;
        background: lightblue;
        margin: 5px auto;
        padding: 2px;
    }
    div.moduleList a{
        width: 60px;
        margin: auto;
        float: right;
        cursor: pointer;
    }
</style>
<button type="button" onclick="showModuleSelWin();" class="ico_add">
    添加模块</button>

   <div id="moduleSel" class="" style="display: none;">
       <ul>请选择相应模块
           <li><input type="radio" name="selMdl" value="SlideShowMdl"/>轮播图</li>
           <li><input type="radio" name="selMdl" value="GroupMdl"/>订购业务</li>
           <li><input type="radio" name="selMdl" value="OptMdl"/>自定义模块</li>
       </ul>
       <div>
           <input type="button" onclick="mdlSave()" value="确定">
           <input type="button" onclick="mdlCancel()" value="取消">
       </div>
   </div>

<script>

    showModuleSelWin=function () {
        var $win = $('#moduleSel').window({
            title: '请选择相应模块',
            width: 700,
            height: 400,
            top: '20%',
            left: '30%',
            shadow: true,
            modal: true,
            iconCls: 'icon-add',
            closed: true,
            minimizable: false,
            maximizable: false,
            collapsible: false
        });
        $win.window('open');
        $('#moduleSel').show();
    };

    function mdlSave() {
        var tmp=$("input[name='selMdl']:checked").val();
        var info="";
        switch (tmp){
            case "SlideShowMdl":
                info= "<div class='moduleList SlideShowMdl'><span>轮播图</span><a onclick='delMdl(this)'>删除</a><a onclick='addMdl(this)'>编辑</a></div>"
                break;
            case "GroupMdl":
                info= "<div class='moduleList GroupMdl'><span>订购业务</span><a onclick='delMdl(this)'>删除</a><a onclick='addMdl(this)'>编辑</a></div>"
                break;
            case "OptMdl":
                info= "<div class='moduleList OptMdl'><span>自定义模块</span><a onclick='delMdl(this)'>删除</a><a onclick='addMdl(this)'>编辑</a></div>"
                break;
        }
        $(".content").append(info);
        $('#moduleSel').window('close');
    }

    function mdlCancel() {
        $('#moduleSel').window('close');
    }

    function delMdl(item) {
        $(item).closest("div").remove();
    }



</script>

