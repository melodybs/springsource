<jsp:directive.page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!-- 변수 설정 -->
<c:set var="titleKey"> 
	<tiles:getAsString name="title" /> 
</c:set>
<c:set var="fileKey"> 
	<tiles:getAsString name="file" /> 
</c:set>

<spring:url value="http://code.jquery.com/jquery-1.10.1.min.js" var="jQuery" />
<spring:url value="/resources/css/${fileKey}.css" var="css" />
<spring:url value="/resources/script/${fileKey}.js" var="js" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Springsource | <spring:message code="${titleKey}" /></title>

<link rel="stylesheet" href="<c:url value="${css}" />" />

<script src="<c:url value="${jQuery}" />"></script>
<script src="<c:url value="${js}" />"></script>
</head>
<body>
	<header>
		<p>header</p>
	</header>
	
	<main>
		<tiles:insertAttribute name="main" />
	</main>
	
	<footer>
		<p>footer</p>
	</footer>
</body>
</html>
