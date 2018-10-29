<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="baseurl" value="${pageContext.request.contextPath}/"></c:set>

<%String contextPath = request.getContextPath();%>
<script type="text/javascript">
    rootURl = '<%=contextPath%>';
</script>