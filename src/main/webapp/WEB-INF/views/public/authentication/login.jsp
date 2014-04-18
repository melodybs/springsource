<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:url value="/j_spring_security_check"  var="login" />
<spring:url value="/j_spring_security_logout"  var="logout" />

<sec:authorize access = "isAnonymous()">
<div>	
	<form action="${login}" method="POST">
		<label for="email" >
			<spring:message code="public.authentication.login.email" />
		</label>
		<input type="text" name="j_username" />
		
		<br />
		
		<label for="password" >
			<spring:message code="public.authentication.login.password" />
		</label>
		<input type="password" name="j_password" />
		
		<c:if test="${not empty param.authenticationOk}">
			
			<br />
			
			<font color="red">
				<spring:message code="public.authentication.login.failed" 
						arguments="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
			</font>
		</c:if>
		
		<br />
		
		<button type="submit">전송</button>
	</form>
	
	<a href="">Sign in with FaceBook</a>
	<a href="">Sign in with Twitter</a>
</div>
</sec:authorize>

<sec:authorize access = "isAuthenticated()">
	<sec:authentication property="name" />
	
	<br />
	
	<a href="${logout}">Logout</a>
</sec:authorize>