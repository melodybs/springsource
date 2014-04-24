<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

1: ${firstName}
<br />
2: ${lastName}
<br />
3: ${username}
<br />
4: ${password}
<br />
5: ${profile.firstName}
<br />
6: ${profile.lastName}
<br />
7: ${profile.username}
<br />
8: ${profile.password}
<br />
<sec:authentication property="name" />
