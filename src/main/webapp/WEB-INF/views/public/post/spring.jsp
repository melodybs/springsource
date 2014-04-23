<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- header section --%>
<header>
	<br />
	
	<c:choose>
		<c:when test="${post.id_main == 0}">
			<div class="title"> Part ${post.title} </div>
		</c:when>
		<c:otherwise>
			<div class="title"> ${post.title} </div>
			<div class="title_main"><%-- add by script --%></div>
		</c:otherwise>
	</c:choose>
	
	<div>
		<span class="btn_prev"> <a accesskey="p" href="/prevNext/spring/${post.id_prev}">이전</a> </span>
		<span class="btn_next"> <a accesskey="n" href="/prevNext/spring/${post.id_next}">다음</a> </span>
	</div>
	
	<hr /><br />
</header>

<%-- main sectio --%>
<main>
	
	<h1 class="title_part">Part ${post.title}</h1>
	
	<div class="contents_ko"> 
		${post.contents_ko} 
	</div>
	
	<br /><hr /> 
	
	<div> <a class="btn_contents_en" href="#">영문 보기</a> </div> 
	<div class="contents_en">
		<hr />
		${post.contents_en}
	</div>
	
	<hr />
	
	<div class="writer">
		<span class="writer">이 글을 번역한 사람들 : ${post.writer} </span>
	</div>
	
	<c:if test="${not empty post.examples}">
	<br /><hr />
	<div class="examples">
		<span>샘플 프로젝트 : <a href="${post.examples}" target="_blank">${post.examples}</a> </span>
	</div>
	</c:if>
</main>

<%-- footer section --%>
<footer>
	<hr />
	
	<div>
		<span class="btn_prev"> <a accesskey="p" href="/prevNext/spring/${post.id_prev}">이전</a> </span>
		<span class="btn_next"> <a accesskey="n" href="/prevNext/spring/${post.id_next}">다음</a> </span>
	</div>
	
	<div>
		<span class="desc_prev"><%-- add by script --%></span>
		<span class="desc_next"><%-- add by script --%></span>
	</div>
	
	<div class="div_btns">	
		<span class="btn_edit"> <a accesskey="e" href="/secured/admin/modify/${post.id}">수정</a> </span> 
		<span> | </span>
		<span class="btn_write"> <a accesskey="w" href="/secured/admin/write">새글</a> </span> 
		<span> | </span>
		<span class="btn_home"> <a accesskey="h" href="/">홈</a> </span>
		<span> | </span>
		<span class="btn_top"> <a accesskey="t" href="#top"> 위로 </a></span>
	</div>
</footer>


<%-- using external javascript --%>
<script>
	var prevId = "${post.id_prev}",
		nextId = "${post.id_next}",
		mainId = "${post.id_main}";
</script>