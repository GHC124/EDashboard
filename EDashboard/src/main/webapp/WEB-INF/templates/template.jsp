<!DOCTYPE HTML>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Bookstore -</title>

</head>

<body class="tundra">
	<div id="wrap">
		<tiles:insertAttribute name="header" />

		<div class="center_content">
			<div class="left_content">
				<tiles:insertAttribute name="content" />
			</div>
			<!--end of left content-->

			<div class="right_content">
				<div class="right_box">
					<div class="title"></div>
				</div>
				<div class="clear"></div>
			</div>
			<!--end of center content-->
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
</body>
</html>