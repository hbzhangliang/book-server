<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="display: none;" id="slideMdl">

    <div>
        <input type="radio" name="visualChs" value="Y">显示
        <input type="radio" name="visualChs" value="N">隐藏
    </div>

    <input type="button" value="新建">
    <table class="table_list">
        <thead>
          <th>排序</th>
          <th>预览图</th>
          <th>文字标题</th>
          <th>url</th>
          <th>操作</th>
        </thead>
    </table>
</div>
<script>

    showSlideMdlWin=function () {
        var $win = $('#slideMdl').window({
            title: '轮播图',
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
        $('#slideMdl').show();
    };

</script>