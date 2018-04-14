<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
.floatPannel{
	background-color:#0066FF;
	width:120px;
	height:auto;
	left:1px;
	top:1px;
	position:absolute;
	z-index:10
}
.l button{-moz-border-radius:15px;border-radius:15px;color:dodgerblue; border:none; line-height:26px; padding:0 20px 0 20px; margin-left: 15px; margin-right:10px;font-weight: bolder;}

</style>
<div class="head">
  <div class="banner auto">
    <ul class="user">
      <li class="t1"></li>
      <li class="t2">欢迎访问&nbsp;&nbsp;&nbsp;&nbsp;</li>
      <li class="t3"></li>
    </ul>
    <div class="l">
      <button onclick="targetGate();" class="mainButton" >
        主页</button>

    </div>
    <div class="date r"></div>
  </div>
</div>
<script>
  var today = new Date();
  var info = "今天是：";
  var x = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
  info += today.getFullYear() + "年" + (today.getMonth() + 1) + "月" + today.getDate() + "日   " + x[today.getDay()];
  $('.date').html(info);

  function targetGate() {
      window.location.href = base+'/gate.htm';
  }

</script>