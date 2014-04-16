<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:url value="/secured/admin/write"  var="write" />
<spring:url value="/public/authentication/login"  var="login" />
<spring:url value="/j_spring_security_logout"  var="logout" />

<!-- header section -->
<header>
	<br />
	
	<div class="title"> 스프링 프레임워크 참조 문서 </div>
	
	<div>
		<span class="btn_next"> <a accesskey="n" href="/public/post/spring/1">다음</a> </span>
	</div>
	
	<br /><hr  /><br />
</header>

<!-- body section -->
<main>
	<h1>스프링 프레임워크 참조 문서</h1>
	
	<hr />
	
	<h2>저자</h2>
	<p>Rod Johnson, Juergen Hoeller, Keith Donald, Colin Sampaleanu, Rob Harrop, Thomas Risberg, Alef Arendsen, Darren Davison, Dmitriy Kopylenko, Mark Pollack, Thierry Templier, Erwin Vervaet, Portia Tung, Ben Hale, Adrian Colyer, John Lewis, Costin Leau, Mark Fisher, Sam Brannen, Ramnivas Laddad, Arjen Poutsma, Chris Beams, Tareq Abedrabbo, Andy Clement, Dave Syer, Oliver Gierke, Rossen Stoyanchev, Phillip Webb</p>
	
	<br />
	<h3>4.0.3.RELEASE</h3>
	<br />
	
	<p>
		<b>Copyright © 2004-2013</b> 
		<br /><br />
		Copies of this document may be made for your own use and for distribution to others, provided that you do not charge any fee for such copies and further provided that each copy contains this Copyright Notice, whether distributed in print or electronically.
	</p>
	
	<hr />
	
	<span><b>Table of Contents</b></span>
	
	<div class="post_list">
	<c:forEach items="${springPostsList}" var="springPost">
		<c:if test="${springPost.id_main == 0}">		
			<span class="part">
				<a href="public/post/spring/${springPost.id}"> ${springPost.title} </a>
			</span>
		</c:if>
		
		<c:if test="${springPost.id_main != 0}">		
			<span class="chapter">
				<a href="public/post/spring/${springPost.id}"> ${springPost.title} </a> 
			</span>
		</c:if>
		<br />
	</c:forEach>
	</div>
</main>

<!-- footer section -->
<footer>
	<hr />
	
	<div>
		<span class="btn_next"> <a accesskey="n" href="/public/post/spring/1">다음</a> </span>
	</div>
	
	<br />
	
	<div>
		<span class="desc_next"><%-- add by script --%></span>
	</div>
	
	<div>
		<span class="btn_write"> <a accesskey="w" href="${write}">새글</a> </span> 
		<span> | </span>
		<span class="btn_top"> <a accesskey="t" href="#top"> 위로 </a></span>
		<span> | </span>
		<sec:authorize access = "isAnonymous()">
			<span class="btn_login"> <a accesskey="i" href="${login}"> 로그인 </a></span>
		</sec:authorize>
		<sec:authorize access = "isAuthenticated()">
			<span class="btn_logout"> <a accesskey="o" href="${logout}"> 로그아웃 </a></span>
			
			<span> | </span>
			
			<span class="text_login_info"> <sec:authentication property="name" /> (${pageContext.request.remoteAddr}) </span>
		</sec:authorize>
	</div>
</footer>