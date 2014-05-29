<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="userLocale">
	<c:set var="plocale">${pageContext.response.locale}</c:set>
	<c:out value="${fn:replace(plocale, '_', '-')}" default="en" />
</c:set>
<spring:message code="application.name" var="app_name" htmlEscape="false" />
<title><spring:message code="application.welcome" arguments="${app_name}" /></title>
