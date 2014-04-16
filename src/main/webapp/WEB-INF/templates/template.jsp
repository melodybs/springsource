<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%-- 변수 설정 --%>
<c:set var="titleKey"> 
	<tiles:getAsString name="title" /> 
</c:set>
<c:set var="fileKey"> 
	<tiles:getAsString name="file" /> 
</c:set>

<spring:url value="http://code.jquery.com/jquery-1.10.1.min.js" var="jQuery" />
<spring:url value="/resources/css/template.css" var="templateCss" />
<spring:url value="/resources/css/${fileKey}.css" var="css" />
<spring:url value="/resources/script/${fileKey}.js" var="js" />
<spring:url value="/resources/ckeditor/ckeditor.js" var="ckeditor" />
<%--<spring:url value="/resources/ckeditor/adapters/jquery.js" var="ckeditorAdapter" />--%>
<spring:url value="/resources/script/html5shiv.js" var="shivJS" />

<%-- TEST --%>
<spring:url value="/resources/script/qunit-1.14.0.min.js" var="qunitJS" />
<spring:url value="/resources/css/qunit-1.14.0.min.css" var="qunitCSS" />
<spring:url value="/resources/script/${fileKey}.test.js" var="testJS" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<%-- 
<meta name="description" content="Free Web tutorials">
<meta name="keywords" content="HTML,CSS,XML,JavaScript">
<meta name="application-name" content="Specifies the name of the Web application that the page represents">
<meta name="generator" content="FrontPage 4.0">
<meta name="author" content="Hege Refsnes">
<meta http-equiv="refresh" content="30;http://www.naver.com">
<meta http-equiv="refresh" content="30">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="default-style" content="the document's preferred stylesheet">
<meta name="date" content="2009-01-02" scheme="YYYY-MM-DD">
<meta name="identifier" content="0-2345-6634-6" scheme="ISBN">
<meta content="Defines the format (or points to an URI that contains the information) of the value inside the content attribute" scheme="format|URI"> 
--%>
<title>Springsource | <spring:message code="${titleKey}" /></title>

<link rel="stylesheet" href="<c:url value="${templateCss}" />" />
<link rel="stylesheet" href="<c:url value="${css}" />" />
<%-- TEST --%>
<link rel="stylesheet" href="<c:url value="${qunitCSS}" />" />

<%--[if lt IE 9]> <script src="${shivJS}"></script> <![endif]--%>
<script src="<c:url value="${jQuery}" />"></script>
</head>

<body>
	<%-- TEST --%>
	<div id="qunit"></div>
	<div id="qunit-fixture"></div>
	
	<tiles:insertAttribute name="main" />
	
	<%-- <header>
		<tiles:insertAttribute name="header" />
	</header>

	<main>
		<tiles:insertAttribute name="main" />
	</main>

	<footer>
		<tiles:insertAttribute name="footer" />
	</footer> --%>
</body>

<script src="<c:url value="${ckeditor}" />"></script>
<%--<script src="<c:url value="${ckeditorAdapter}" />"></script>--%>
<script src="<c:url value="${js}" />"></script>

<%-- TEST --%>
<script src="<c:url value="${qunitJS}" />"></script>
<script src="<c:url value="${testJS}" />"></script>
</html>