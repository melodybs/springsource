<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:url value="" var="writeUrl" />
<spring:url value="" var="saveUrl" />
<spring:url value="/public/authentication/login"  var="login" />
<spring:url value="/j_spring_security_logout"  var="logout" />

<form:form name="form_spring" id="form_spring" commandName="springPostsForm">
<fieldset>
	<legend>새글</legend>

	<button class="btn_save">저장</button>
	<button class="btn_send">전송</button>
	<br /><br />
	
	<label for="main_title">메인 글</label>
	<select name="main_title" id="main_title">
		<option value="">(필수) 메인글을 선택해 주세요.</option>
		<option value="0"> 없음(새 글이 메인글이 됩니다.) </option>
		<%-- add by script --%>
	</select>
	<form:errors path="main_title" cssClass="error"></form:errors>
	<br /><br />
	
	<label for="prev_title">이전 글</label>
	<select name="prev_title" id="prev_title">
		<option style="" value="">(필수) 이전 글을 선택 해주세요.</option>
	</select>
	<form:errors path="prev_title" cssClass="error"></form:errors>
	<br /><br />
	
	<label for="title">제목</label>
	<input type="text" name="title" id="title" />
	<form:errors path="title" cssClass="error"></form:errors>
	<br /><br />
	
	<label for="editor">번역</label>
	<textarea name="editor" id="editor"></textarea>
	<form:errors path="editor" cssClass="error"></form:errors>
	<br /><br />
	
	<label for="editor_e">본문</label>
	<textarea name="editor_e" id="editor_e"></textarea>
	<form:errors path="editor_e" cssClass="error"></form:errors>
	<br /><br />
	
	<label for="inside_text">내부 제목</label>
	<input type="text" class="inside_input" />
	<button class="btn_input_add">+</button>
	<button class="btn_input_reset">R</button>
	<br />
	
	<label for="inside_text02">내부 제목 02</label>
	<input type="text" class="inside_input02" />
	<button class="btn_input_add02">+</button>
	<button class="btn_input_reset02">R</button>
	<br />
	
	<textarea name="inside_text" id="inside_text" readonly="readonly"></textarea>
	<form:errors path="inside_text" cssClass="error"></form:errors>
	<button class="btn_text_reset">R</button>
	<button class="btn_text_one">-1</button>
	<br /><br />
	
	<label for="see">참조</label>
	<input type="text" name="see" id="see" />
	<form:errors path="see" cssClass="error"></form:errors>
	<br /><br />
	
	<button class="btn_save">저장</button>
	<button class="btn_send">전송</button>
 
 	<span class="btn_home"> <a accesskey="h" href="/">홈</a> </span>
	<span> | </span>
	<span class="btn_top"> <a accesskey="t" href="#top"> 위로 </a></span>
	<span> | </span>
		<sec:authorize access = "isAnonymous()">
			<span class="btn_login"> <a accesskey="i" href="${login}"> 로그인 </a></span>
		</sec:authorize>
		<sec:authorize access = "isAuthenticated()">
			<span class="btn_logout"> <a accesskey="o" href="${logout}"> 로그아웃 </a></span>
			
			<span> | </span>
			
			<span class="text_login_info"> <sec:authentication property="name" /> </span>
		</sec:authorize>
 </fieldset>
 </form:form>
 