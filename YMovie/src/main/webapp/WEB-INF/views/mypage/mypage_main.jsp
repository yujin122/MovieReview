<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href="${path }/resources/css/mypage.css">
<style type="text/css">
	#side_bar ul li:nth-child(1) a {font-weight: bold; color : #ff3131; text-decoration: underline;}
</style>
</head>
<body>
	
	<%@ include file="../include/my_info.jsp"%>
	
	<div id = "mypage_menu">
		
	</div>
</div>

	<%@ include file = "../include/bottom.jsp" %>