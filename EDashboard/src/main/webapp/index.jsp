<!DOCTYPE HTML>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()">
		<jsp:forward page="/secured/dashboard" /> 
</sec:authorize>
<sec:authorize access="isAnonymous()">
	<jsp:forward page="/public/main" />
</sec:authorize>
