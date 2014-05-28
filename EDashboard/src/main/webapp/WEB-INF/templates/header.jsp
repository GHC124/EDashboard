<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<jsp:directive.page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" />

<spring:url value="/index.jsp" var="home" />

<div class="header">
	<div class="logo">
		<a href="${home}"> Home </a>
	</div>
	<div class="nav">
		<ul>
			<li><a href="${home}"><spring:message code="nav.home" /></a></li>
		</ul>
		<ul style="float: right;">

		</ul>
	</div>
</div>