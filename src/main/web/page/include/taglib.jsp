<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=8"><!--使IE8支持JSON对象-->
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <c:set var="base" value="<%=request.getContextPath()%>" scope="session"/>
  <c:if test="${base=='/'}">
    <c:set var="base" value=""/>
  </c:if>
  <script>
    var base = '${base}';
  </script>
<c:set var="resources" value="${base}/web-resources"/>